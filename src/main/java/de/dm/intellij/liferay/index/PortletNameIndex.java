package de.dm.intellij.liferay.index;

import com.intellij.ide.highlighter.JavaClassFileType;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.project.IndexNotReadyException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.GlobalSearchScope;
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
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * FileBasedIndexer to quickly find all portlet names
 */
public class PortletNameIndex extends FileBasedIndexExtension<String, Void> implements PsiDependentIndex {

    @NonNls
    public static final ID<String, Void> NAME = ID.create("PortletNameIndex");

    private static final String PORTLET_NAME_PROPERTY = "javax.portlet.name";

    private final MyPortletIndexer myPortletIndexer = new MyPortletIndexer();

    @NotNull
    @Override
    public ID<String, Void> getName() {
        return NAME;
    }

    @NotNull
    @Override
    public KeyDescriptor<String> getKeyDescriptor() {
        return EnumeratorStringDescriptor.INSTANCE;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public boolean dependsOnFileContent() {
        return true;
    }

    @NotNull
    @Override
    public DataExternalizer<Void> getValueExternalizer() {
        return VoidDataExternalizer.INSTANCE;
    }

    @NotNull
    @Override
    public FileBasedIndex.InputFilter getInputFilter() {
        return new DefaultFileTypeSpecificInputFilter(JavaFileType.INSTANCE, JavaClassFileType.INSTANCE);
    }

    @NotNull
    @Override
    public DataIndexer<String, Void, FileContent> getIndexer() {
        return myPortletIndexer;
    }

    public static List<String> getPortletNames(Project project, GlobalSearchScope scope) {
        return ReadAction.compute(
                () -> {
                    final List<String> result = new ArrayList<>();

                    try {
                        FileBasedIndex.getInstance().processAllKeys(
                                NAME,
                                name -> {
                                    result.add(name);
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

    public static List<PsiFile> getPortletClasses(Project project, String portletName, GlobalSearchScope scope) {
        return ReadAction.compute(
                () -> {
                    List<PsiFile> result = new ArrayList<>();

                    try {
                        Collection<VirtualFile> containingFiles = FileBasedIndex.getInstance().getContainingFiles(NAME, portletName, scope);

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


    private class MyPortletIndexer extends AbstractComponentPropertyIndexer<String> {

        @NotNull
        @Override
        protected String[] getServiceClassNames() {
            return new String[]{"javax.portlet.Portlet"};
        }

        @Override
        protected void processProperties(@NotNull Map<String, Void> map, @NotNull Map<String, Collection<String>> properties, @NotNull PsiClass psiClass, String serviceClassName) {
            Collection<String> portletNames = properties.get(PORTLET_NAME_PROPERTY);

            if (portletNames == null) {
                portletNames = Collections.singletonList(psiClass.getQualifiedName());
            }

            for (String portletName : portletNames) {
                //from PortletTracker.addingService()
                String portletId = StringUtil.replace(portletName, Arrays.asList(".", "$"), Arrays.asList("_", "_"));
                portletId = LiferayFileUtil.getJSSafeName(portletId);

                map.put(portletId, null);
            }

        }

    }
}
