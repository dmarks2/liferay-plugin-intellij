package de.dm.intellij.liferay.index;

import com.intellij.ide.highlighter.JavaClassFileType;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.project.IndexNotReadyException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiAnnotationParameterList;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiConstantEvaluationHelper;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaCodeReferenceElement;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.PsiNameValuePair;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtil;
import com.intellij.util.indexing.DataIndexer;
import com.intellij.util.indexing.DefaultFileTypeSpecificInputFilter;
import com.intellij.util.indexing.FileBasedIndex;
import com.intellij.util.indexing.FileBasedIndexExtension;
import com.intellij.util.indexing.FileContent;
import com.intellij.util.indexing.ID;
import com.intellij.util.indexing.PsiDependentIndex;
import com.intellij.util.io.DataExternalizer;
import com.intellij.util.io.EnumeratorStringDescriptor;
import com.intellij.util.io.KeyDescriptor;
import com.intellij.util.io.VoidDataExternalizer;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * FileBasedIndexer to quickly find all action commands names
 */
public class ActionCommandIndex extends FileBasedIndexExtension<CommandKey, Void> implements PsiDependentIndex {

    @NonNls
    public static final ID<CommandKey, Void> NAME = ID.create("ActionCommandIndex");

    private final ActionCommandIndexer actionCommandIndexer = new ActionCommandIndexer();

    private static final Collection<String> ACTION_NAME_EXCEPTIONS = Arrays.asList(
        "callActionMethod",
        "processAction"
    );

    @NotNull
    @Override
    public ID<CommandKey, Void> getName() {
        return NAME;
    }

    @NotNull
    @Override
    public DataIndexer<CommandKey, Void, FileContent> getIndexer() {
        return actionCommandIndexer;
    }

    @NotNull
    @Override
    public KeyDescriptor<CommandKey> getKeyDescriptor() {
        return new KeyDescriptor<CommandKey>() {
            @Override
            public int getHashCode(CommandKey value) {
                return value.hashCode();
            }

            @Override
            public boolean isEqual(CommandKey val1, CommandKey val2) {
                return val1.equals(val2);
            }

            @Override
            public void save(@NotNull DataOutput out, CommandKey value) throws IOException {
                EnumeratorStringDescriptor.INSTANCE.save(out, value.getPortletName());
                EnumeratorStringDescriptor.INSTANCE.save(out, value.getCommandName());
            }

            @Override
            public CommandKey read(@NotNull DataInput in) throws IOException {
                return new CommandKey(EnumeratorStringDescriptor.INSTANCE.read(in), EnumeratorStringDescriptor.INSTANCE.read(in));
            }
        };
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
        return new DefaultFileTypeSpecificInputFilter(JavaFileType.INSTANCE, JavaClassFileType.INSTANCE);
    }

    @Override
    public boolean dependsOnFileContent() {
        return true;
    }

    public static List<String> getActionCommands(@NotNull String portletName, GlobalSearchScope scope) {
        return ReadAction.compute(
            () -> {
                final List<String> result = new ArrayList<>();

                try {
                    FileBasedIndex.getInstance().processAllKeys(
                        NAME,
                        commandKey -> {
                            if (portletName.equals(commandKey.getPortletName())) {
                                result.add(commandKey.getCommandName());
                            }
                            return true;
                        },
                        scope,
                        null
                    );

                } catch (IndexNotReadyException e) {
                    //ignore
                }

                return result;
            }
        );
    }

    public static List<PsiFile> getPortletClasses(Project project, String portletName, String actionCommand, GlobalSearchScope scope) {
        return ReadAction.compute(
            () -> {
                List<PsiFile> result = new ArrayList<>();

                try {
                    Collection<VirtualFile> containingFiles = FileBasedIndex.getInstance().getContainingFiles(NAME, new CommandKey(portletName, actionCommand), scope);

                    PsiManager psiManager = PsiManager.getInstance(project);

                    for (VirtualFile virtualFile : containingFiles) {
                        if (! virtualFile.isValid()) {
                            continue;
                        }

                        PsiFile psiFile = psiManager.findFile(virtualFile);

                        if (psiFile != null) {
                            result.add(psiFile);
                        }

                    }
                } catch (IndexNotReadyException e) {
                    //ignore
                }

                return result;
            }
        );
    }

    private class ActionCommandIndexer extends AbstractComponentPropertyIndexer<CommandKey> {

        @NotNull
        @Override
        protected String[] getServiceClassNames() {
            return new String[] {"com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand"};
        }

        @Override
        protected void processProperties(@NotNull Map<CommandKey, Void> map, @NotNull Map<String, Collection<String>> properties, @NotNull PsiClass psiClass, String serviceClassName) {
            if (! properties.isEmpty()) {
                Collection<String> mvcCommandNames = properties.get("mvc.command.name");
                Collection<String> portletNames = properties.get("javax.portlet.name");

                if ((mvcCommandNames != null) && (portletNames != null)) {
                    for (String mvcCommandName : mvcCommandNames) {
                        for (String portletName : portletNames) {
                            map.put(new CommandKey(portletName, mvcCommandName), null);
                        }
                    }
                }
            }
        }

        @NotNull
        @Override
        public Map<CommandKey, Void> map(@NotNull FileContent fileContent) {
            Map<CommandKey, Void> map = super.map(fileContent);

            PsiJavaFile psiJavaFile = getPsiJavaFileForPsiDependentIndex(fileContent);

            if (psiJavaFile == null) {
                return map;
            }

            PsiClass[] psiClasses = PsiTreeUtil.getChildrenOfType(psiJavaFile, PsiClass.class);

            if (psiClasses != null) {

                for (PsiClass psiClass : psiClasses) {

                    for (PsiMethod psiMethod : psiClass.getMethods()) {

                        for (PsiAnnotation psiAnnotation : psiMethod.getAnnotations()) {
                            PsiJavaCodeReferenceElement nameReferenceElement = psiAnnotation.getNameReferenceElement();

                            if (nameReferenceElement != null) {
                                String qualifiedName = ProjectUtils.getQualifiedNameWithoutResolve(nameReferenceElement);

                                if ("javax.portlet.ProcessAction".equals(qualifiedName)) {
                                    PsiAnnotationParameterList psiAnnotationParameterList = psiAnnotation.getParameterList();

                                    for (PsiNameValuePair psiNameValuePair : psiAnnotationParameterList.getAttributes()) {
                                        if ("name".equals(psiNameValuePair.getName())) {
                                            PsiAnnotationMemberValue psiNameValuePairValue = psiNameValuePair.getValue();

                                            String actionCommand = null;
                                            if (psiNameValuePairValue instanceof PsiLiteralExpression) {
                                                actionCommand = psiNameValuePairValue.getText();

                                                if (actionCommand != null) {
                                                    actionCommand = StringUtil.unquoteString(actionCommand);
                                                }
                                            } else if (psiNameValuePairValue instanceof PsiReferenceExpression) {
                                                PsiReferenceExpression psiReferenceExpression = (PsiReferenceExpression) psiNameValuePairValue;

                                                PsiConstantEvaluationHelper constantEvaluationHelper = JavaPsiFacade.getInstance(psiReferenceExpression.getProject()).getConstantEvaluationHelper();

                                                actionCommand = (String) constantEvaluationHelper.computeConstantExpression(psiReferenceExpression);
                                            }

                                            if (actionCommand != null) {
                                                Collection<String> portletNames = getPortletNames(psiClass);

                                                for (String portletName : portletNames) {
                                                    map.put(new CommandKey(portletName, actionCommand), null);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        PsiModifierList modifierList = psiMethod.getModifierList();
                        if (PsiUtil.getAccessLevel(modifierList) == PsiUtil.ACCESS_LEVEL_PUBLIC) {
                            List<String> methodParameterQualifiedNames = ProjectUtils.getMethodParameterQualifiedNames(psiMethod);
                            if (methodParameterQualifiedNames.size() == 2) {
                                String methodName = psiMethod.getName();
                                if (! ACTION_NAME_EXCEPTIONS.contains(methodName)) {
                                    String firstParameterQualifiedName = methodParameterQualifiedNames.get(0);
                                    String secondParameterQualifiedName = methodParameterQualifiedNames.get(1);

                                    if ( ("javax.portlet.ActionRequest".equals(firstParameterQualifiedName)) && ("javax.portlet.ActionResponse".equals(secondParameterQualifiedName)) ) {
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
