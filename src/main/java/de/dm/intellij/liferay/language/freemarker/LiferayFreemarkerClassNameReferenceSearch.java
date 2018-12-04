package de.dm.intellij.liferay.language.freemarker;

import com.intellij.freemarker.psi.FtlStringLiteral;
import com.intellij.freemarker.psi.files.FtlFileType;
import com.intellij.openapi.application.QueryExecutorBase;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Processor;
import de.dm.intellij.liferay.language.freemarker.enumutil.EnumUtilClassNameCompletionContributor;
import de.dm.intellij.liferay.language.freemarker.servicelocator.ServiceLocatorClassNameCompletionContributor;
import de.dm.intellij.liferay.language.freemarker.staticutil.StaticUtilClassNameCompletionContributor;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class LiferayFreemarkerClassNameReferenceSearch extends QueryExecutorBase<PsiElement, ReferencesSearch.SearchParameters> {

    public LiferayFreemarkerClassNameReferenceSearch() {
        super(true);
    }

    @Override
    public void processQuery(@NotNull ReferencesSearch.SearchParameters queryParameters, @NotNull Processor<? super PsiElement> consumer) {
        final PsiElement elementToSearch = queryParameters.getElementToSearch();
        final SearchScope effectiveSearchScope = queryParameters.getEffectiveSearchScope();

        Project project = elementToSearch.getProject();
        PsiManager psiManager = PsiManager.getInstance(project);

        if (elementToSearch instanceof PsiClass) {
            PsiClass psiClass = (PsiClass)elementToSearch;
            String qualifiedName = psiClass.getQualifiedName();
            if (qualifiedName != null) {
                if (effectiveSearchScope instanceof GlobalSearchScope) {
                    GlobalSearchScope globalSearchScope = (GlobalSearchScope)effectiveSearchScope;

                    Collection<VirtualFile> ftlFiles = FileTypeIndex.getFiles(FtlFileType.INSTANCE, globalSearchScope);
                    for (VirtualFile ftlFile : ftlFiles) {
                        PsiFile psiFile = psiManager.findFile(ftlFile);
                        if (psiFile != null) {
                            Collection<FtlStringLiteral> ftlStringLiterals = PsiTreeUtil.collectElementsOfType(psiFile, FtlStringLiteral.class);
                            for (FtlStringLiteral ftlStringLiteral : ftlStringLiterals) {
                                if (
                                    (ServiceLocatorClassNameCompletionContributor.isServiceLocatorCall(ftlStringLiteral)) ||
                                    (StaticUtilClassNameCompletionContributor.isStaticUtilCall(ftlStringLiteral)) ||
                                    (EnumUtilClassNameCompletionContributor.isEnumUtilCall(ftlStringLiteral))
                                ) {
                                    consumer.process(ftlStringLiteral);
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}
