package de.dm.intellij.liferay.language.freemarker;

import com.intellij.freemarker.psi.FtlArgumentList;
import com.intellij.freemarker.psi.FtlElementTypes;
import com.intellij.freemarker.psi.FtlNameValuePair;
import com.intellij.freemarker.psi.FtlStringLiteral;
import com.intellij.freemarker.psi.directives.FtlMacro;
import com.intellij.lang.ASTNode;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.lang.javascript.JavascriptLanguage;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.SmartList;
import de.dm.intellij.liferay.language.javascript.AbstractLiferayJavascriptLanguageInjector;
import de.dm.intellij.liferay.util.LiferayTaglibs;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class LiferayFreemarkerJavascriptLanguageInjector extends AbstractLiferayJavascriptLanguageInjector<FtlMacro, FtlNameValuePair> {

    @Nullable
    @Override
    protected FtlMacro getTag(@NotNull PsiElement psiElement) {
        return (FtlMacro)psiElement;
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

    @Override
    protected boolean isContextSuitableForBodyInjection(PsiElement context) {
        return true;
    }

    @NotNull
    @Override
    protected FtlNameValuePair[] getAttributes(@NotNull PsiElement psiElement) {
        FtlMacro ftlMacro = (FtlMacro)psiElement;

        FtlArgumentList argumentList = ftlMacro.getArgumentList();
        return argumentList.getNamedArguments();
    }

    @NotNull
    public List<? extends Class<? extends PsiElement>> elementsToInjectIn() {
        return Arrays.asList(FtlMacro.class);
    }

    @Override
    protected void injectIntoAttribute(@NotNull MultiHostRegistrar registrar, FtlNameValuePair ftlNameValuePair) {
        PsiElement valueElement = ftlNameValuePair.getValueElement();

        if (valueElement instanceof FtlStringLiteral) {
            FtlStringLiteral ftlStringLiteral = (FtlStringLiteral)valueElement;
            List<TextRange> ranges = new SmartList<TextRange>();
            PsiElement[] children = ftlStringLiteral.getChildren();
            ranges.add(ftlStringLiteral.getValueRange());

            int startOffset = ftlStringLiteral.getTextRange().getStartOffset();

            for (PsiElement child : children) {
                TextRange textRange = child.getTextRange();
                TextRange rangeInElement = textRange.shiftLeft(startOffset);

                TextRange lastRange = ranges.remove(ranges.size() - 1);
                TextRange leftRange = new TextRange(lastRange.getStartOffset(), rangeInElement.getStartOffset());
                if (leftRange.getLength() > 0) {
                    ranges.add(leftRange);
                }

                TextRange rightRange = new TextRange(rangeInElement.getEndOffset(), lastRange.getEndOffset());
                if (rightRange.getLength() > 0) {
                    ranges.add(rightRange);
                }
            }

            if (! (ranges.isEmpty()) ) {
                registrar.startInjecting(JavascriptLanguage.INSTANCE);
                for (TextRange textRange : ranges) {
                    registrar.addPlace(null, null, ftlStringLiteral, textRange);
                }
                registrar.doneInjecting();
            }
        }
    }

    @Override
    protected void injectIntoBody(@NotNull MultiHostRegistrar registrar, FtlMacro ftlMacro) {
        TextRange injectionRange = ftlMacro.getInjectionRange();
        if (injectionRange != null) {
            if (! (injectionRange.isEmpty())) {
                registrar.startInjecting(JavascriptLanguage.INSTANCE);
                registrar.addPlace(null, null, (PsiLanguageInjectionHost) ftlMacro, injectionRange);
                registrar.doneInjecting();
            }
        } else {
            int startOffset = ftlMacro.getTextRange().getStartOffset();
            List<TextRange> ranges = new SmartList<TextRange>();

            ASTNode node = ftlMacro.getNode();
            ASTNode[] children = node.getChildren(TokenSet.create(FtlElementTypes.TEMPLATE_TEXT));
            for (ASTNode child : children) {
                TextRange textRange = child.getTextRange();
                TextRange rangeInElement = textRange.shiftLeft(startOffset);
                ranges.add(rangeInElement);
            }

            if (! (ranges.isEmpty()) ) {
                registrar.startInjecting(JavascriptLanguage.INSTANCE);
                for (TextRange textRange : ranges) {
                    registrar.addPlace(null, null, ftlMacro, textRange);
                }
                registrar.doneInjecting();
            }
        }
    }

}
