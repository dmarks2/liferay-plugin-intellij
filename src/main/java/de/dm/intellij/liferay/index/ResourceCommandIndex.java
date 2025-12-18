package de.dm.intellij.liferay.index;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.DataIndexer;
import com.intellij.util.indexing.DefaultFileTypeSpecificInputFilter;
import com.intellij.util.indexing.FileBasedIndex;
import com.intellij.util.indexing.FileBasedIndexExtension;
import com.intellij.util.indexing.FileContent;
import com.intellij.util.indexing.ID;
import com.intellij.util.io.DataExternalizer;
import com.intellij.util.io.KeyDescriptor;
import com.intellij.util.io.VoidDataExternalizer;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FileBasedIndexer to quickly find all resource commands names
 */
public class ResourceCommandIndex extends FileBasedIndexExtension<CommandKey, Void> {

    @NonNls
    public static final ID<CommandKey, Void> NAME = ID.create("ResourceCommandIndex");

    private static final Collection<String> RESOURCE_NAME_EXCEPTIONS = Arrays.asList(
        "callResourceMethod",
        "serveResource"
    );


    private final ResourceCommandIndexer resourceCommandIndexer = new ResourceCommandIndexer();

    @NotNull
    @Override
    public ID<CommandKey, Void> getName() {
        return NAME;
    }

    @NotNull
    @Override
    public DataIndexer<CommandKey, Void, FileContent> getIndexer() {
        return resourceCommandIndexer;
    }

    @NotNull
    @Override
    public KeyDescriptor<CommandKey> getKeyDescriptor() {
        return new CommandKeyDescriptor();
    }

    @NotNull
    @Override
    public DataExternalizer<Void> getValueExternalizer() {
        return VoidDataExternalizer.INSTANCE;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @NotNull
    @Override
    public FileBasedIndex.InputFilter getInputFilter() {
        return new DefaultFileTypeSpecificInputFilter(JavaFileType.INSTANCE);
    }

    @Override
    public boolean dependsOnFileContent() {
        return true;
    }

    public static List<String> getResourceCommands(@NotNull String portletName, Project project, GlobalSearchScope scope) {
        return AbstractCommandKeyIndexer.getCommands(NAME, portletName, project, scope);
    }

    public static List<PsiFile> getPortletClasses(Project project, String portletName, String commandName, GlobalSearchScope scope) {
        return AbstractCommandKeyIndexer.getPortletClasses(NAME, project, portletName, commandName, scope);
    }

    private static class ResourceCommandIndexer extends AbstractCommandKeyIndexer {

        @NotNull
        @Override
        protected String[] getServiceClassNames() {
            return new String[]{"com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand"};
        }

        @NotNull
        @Override
        public Map<CommandKey, Void> map(@NotNull FileContent fileContent) {
            Map<CommandKey, Void> map = new HashMap<>(super.map(fileContent));

            PsiJavaFile psiJavaFile = getPsiJavaFileForPsiDependentIndex(fileContent);

            if (psiJavaFile == null) {
                return map;
            }

            PsiClass[] psiClasses = psiJavaFile.getClasses();

            if (psiClasses != null) {

                for (PsiClass psiClass : psiClasses) {

                    for (PsiMethod psiMethod : psiClass.getMethods()) {

                        PsiModifierList modifierList = psiMethod.getModifierList();

                        if (modifierList.hasModifierProperty(PsiModifier.PUBLIC)) {
                            List<String> methodParameterQualifiedNames = ProjectUtils.getMethodParameterQualifiedNames(psiMethod);
                            if (methodParameterQualifiedNames.size() == 2) {
                                String methodName = psiMethod.getName();
                                if (!RESOURCE_NAME_EXCEPTIONS.contains(methodName)) {
                                    String firstParameterQualifiedName = methodParameterQualifiedNames.get(0);
                                    String secondParameterQualifiedName = methodParameterQualifiedNames.get(1);

                                    if (("javax.portlet.ResourceRequest".equals(firstParameterQualifiedName)) && ("javax.portlet.ResourceResponse".equals(secondParameterQualifiedName))) {
                                        Collection<String> portletNames = getPortletNames(psiClass);

                                        for (String portletName : portletNames) {
                                            map.put(new CommandKey(portletName, methodName), null);
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }

            return map;
        }

        private Collection<String> getPortletNames(PsiClass psiClass) {
            Collection<String> result = new ArrayList<>();

            Map<String, Collection<String>> componentProperties = getComponentProperties(psiClass, "javax.portlet.Portlet");

            if (componentProperties != null) {

                Collection<String> portletNames = componentProperties.get("javax.portlet.name");
                if (portletNames == null) {
                    portletNames = Collections.singletonList(psiClass.getQualifiedName());
                }

                for (String portletName : portletNames) {
                    String portletId = LiferayFileUtil.getPortletId(portletName);

                    result.add(portletId);
                }
            }

            return result;
        }
    }

}
