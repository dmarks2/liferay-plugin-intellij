package de.dm.intellij.liferay.language.freemarker;

import com.intellij.freemarker.psi.FtlCompositeElementTypes;
import com.intellij.freemarker.psi.FtlNameValuePair;
import com.intellij.freemarker.psi.FtlStringLiteral;
import com.intellij.freemarker.psi.directives.FtlMacro;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.filters.position.FilterPattern;
import com.intellij.psi.util.PsiTreeUtil;
import de.dm.intellij.liferay.language.resourcebundle.AbstractLiferayResourceBundleReferenceContributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LiferayFreemarkerResourceBundleReferenceContributor extends AbstractLiferayResourceBundleReferenceContributor<FtlMacro, FtlNameValuePair> {

    @Override
    protected void registerReferenceProvider(PsiReferenceRegistrar registrar, String[] attributeNames, ElementFilter elementFilter, PsiReferenceProvider psiReferenceProvider) {
        //PsiElementPattern.Capture<PsiElement> ftlStringLiteralPattern = PlatformPatterns.psiElement(FtlCompositeElementTypes.STRING_LITERAL);
        PsiElementPattern.Capture<PsiElement> ftlStringLiteralPattern = PlatformPatterns.psiElement();

        registrar
                .registerReferenceProvider(
                        ftlStringLiteralPattern.and(new FilterPattern(elementFilter)),
                        psiReferenceProvider
                );
    }

    @Nullable
    @Override
    protected FtlMacro getTag(@NotNull PsiElement psiElement) {
        FtlNameValuePair attribute = getAttribute(psiElement);
        if (attribute != null) {
            FtlMacro ftlMacro = PsiTreeUtil.getParentOfType(attribute, FtlMacro.class);

            return ftlMacro;
        }
        return null;
    }

    @Nullable
    @Override
    protected FtlNameValuePair getAttribute(@NotNull PsiElement psiElement) {
        if (psiElement instanceof FtlStringLiteral) {
            FtlStringLiteral ftlStringLiteral = (FtlStringLiteral)psiElement;

            FtlNameValuePair ftlNameValuePair = PsiTreeUtil.getParentOfType(ftlStringLiteral, FtlNameValuePair.class);

            return ftlNameValuePair;
        }
        return null;
    }

    @Override
    protected String getNamespace(FtlMacro ftlMacro) {
        return LiferayFreemarkerTaglibs.getNamespace(ftlMacro);
    }

    @Override
    protected String getLocalName(FtlMacro ftlMacro) {
        return LiferayFreemarkerTaglibs.getLocalName(ftlMacro);
    }

    @Override
    protected String getAttributeName(FtlNameValuePair ftlNameValuePair) {
        return ftlNameValuePair.getName();
    }
}
