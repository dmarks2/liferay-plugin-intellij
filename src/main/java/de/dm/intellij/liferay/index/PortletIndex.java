package de.dm.intellij.liferay.index;

import com.intellij.ide.highlighter.JavaClassFileType;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.IndexNotReadyException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiAnnotationParameterList;
import com.intellij.psi.PsiArrayInitializerMemberValue;
import com.intellij.psi.PsiBinaryExpression;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiConstantEvaluationHelper;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiNameValuePair;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
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
import de.dm.intellij.liferay.language.osgi.ComponentPropertiesCompletionContributor;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FileBasedIndexer to quickly find all portlet names
 */
public class PortletIndex extends FileBasedIndexExtension<String, Void> implements PsiDependentIndex {

    @NonNls
    public static final ID<String, Void> NAME = ID.create("PortletIndex");

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


    private class MyPortletIndexer implements DataIndexer<String, Void, FileContent> {

        @NotNull
        @Override
        public Map<String, Void> map(@NotNull FileContent fileContent) {
            Map<String, Void> map = new HashMap<>();

            VirtualFile virtualFile = fileContent.getFile();

            PsiManager psiManager = PsiManager.getInstance(fileContent.getProject());

            PsiFile psiFile = psiManager.findFile(virtualFile);

            DumbService dumbService = DumbService.getInstance(fileContent.getProject());
            try {
                dumbService.setAlternativeResolveEnabled(true);

                //TODO how to handle changes in dependant file?

                if (psiFile instanceof PsiJavaFile) {
                    PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;
                    for (PsiClass psiClass : psiJavaFile.getClasses()) {
                        for (PsiAnnotation psiAnnotation : psiClass.getAnnotations()) {
                            if ("org.osgi.service.component.annotations.Component".equals(psiAnnotation.getQualifiedName())) {
                                PsiAnnotationParameterList psiAnnotationParameterList = psiAnnotation.getParameterList();

                                List<String> serviceClassNames = ComponentPropertiesCompletionContributor.getServiceClassNames(psiAnnotationParameterList);
                                if (!(serviceClassNames.isEmpty())) {
                                    for (String serviceClassName : serviceClassNames) {
                                        if ("javax.portlet.Portlet".equals(serviceClassName)) {
                                            String portletName = null;

                                            outer:
                                            for (PsiNameValuePair psiNameValuePair : psiAnnotationParameterList.getAttributes()) {
                                                if ("property".equals(psiNameValuePair.getName())) {
                                                    PsiAnnotationMemberValue psiNameValuePairValue = psiNameValuePair.getValue();

                                                    if (psiNameValuePairValue instanceof PsiArrayInitializerMemberValue) {
                                                        PsiArrayInitializerMemberValue psiArrayInitializerMemberValue = (PsiArrayInitializerMemberValue) psiNameValuePairValue;

                                                        PsiAnnotationMemberValue[] initializers = psiArrayInitializerMemberValue.getInitializers();
                                                        for (PsiAnnotationMemberValue initializer : initializers) {
                                                            if (initializer instanceof PsiLiteralExpression) {
                                                                portletName = getPortletName((PsiLiteralExpression) initializer);
                                                                if (portletName != null) {
                                                                    break outer;
                                                                }
                                                            } else if (initializer instanceof PsiBinaryExpression) {
                                                                portletName = getPortletName((PsiBinaryExpression) initializer);
                                                                if (portletName != null) {
                                                                    break outer;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            if (portletName == null) {
                                                portletName = psiClass.getQualifiedName();
                                            }

                                            if (portletName != null) {
                                                //from PortletTracker.addingService()
                                                String portletId = StringUtil.replace(portletName, Arrays.asList(".", "$"), Arrays.asList("_", "_"));
                                                portletId = LiferayFileUtil.getJSSafeName(portletId);

                                                map.put(portletId, null);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                //ignore?
            } finally {
                dumbService.setAlternativeResolveEnabled(false);
            }

            return map;
        }

        private String getPortletName(PsiLiteralExpression psiLiteralExpression) {
            String text = psiLiteralExpression.getText();
            text = StringUtil.unquoteString(text);

            if (text.contains("=")) {
                String[] parts = text.split("=");
                if (PORTLET_NAME_PROPERTY.equals(parts[0])) {
                    return parts[1];
                }
            }

            return null;
        }

        private String getPortletName(PsiBinaryExpression psiBinaryExpression) {
            PsiLiteralExpression psiLiteralExpression = PsiTreeUtil.getChildOfType(psiBinaryExpression, PsiLiteralExpression.class);
            if (psiLiteralExpression != null) {
                String text = psiLiteralExpression.getText();
                text = StringUtil.unquoteString(text);

                if (text.contains("=")) {
                    String[] parts = text.split("=");
                    if (PORTLET_NAME_PROPERTY.equals(parts[0])) {
                        PsiReferenceExpression psiReferenceExpression = PsiTreeUtil.getChildOfType(psiBinaryExpression, PsiReferenceExpression.class);
                        if (psiReferenceExpression != null) {
                            PsiConstantEvaluationHelper constantEvaluationHelper = JavaPsiFacade.getInstance(psiBinaryExpression.getProject()).getConstantEvaluationHelper();

                            String portletName = (String)constantEvaluationHelper.computeConstantExpression(psiReferenceExpression);

                            return portletName;
                        }
                    }
                }
            }

            return null;
        }
    }
}
