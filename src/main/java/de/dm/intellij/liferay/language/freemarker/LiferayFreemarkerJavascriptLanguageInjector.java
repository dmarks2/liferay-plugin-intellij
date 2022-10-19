package de.dm.intellij.liferay.language.freemarker;

import com.intellij.freemarker.psi.*;
import com.intellij.freemarker.psi.directives.FtlMacro;
import com.intellij.lang.ASTNode;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.lang.javascript.JavascriptLanguage;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.SmartList;
import de.dm.intellij.liferay.language.javascript.AbstractLiferayJavascriptLanguageInjector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class LiferayFreemarkerJavascriptLanguageInjector extends AbstractLiferayJavascriptLanguageInjector<FtlMacro, FtlCompositeElement> {

    @Nullable
    @Override
    protected FtlMacro getTag(@NotNull PsiElement psiElement) {
        if (psiElement instanceof FtlMacro) {
            return (FtlMacro)psiElement;
        }

        return PsiTreeUtil.getParentOfType(psiElement, FtlMacro.class);
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
    protected String getAttributeName(FtlCompositeElement ftlCompositeElement) {
        if (ftlCompositeElement instanceof FtlNameValuePair) {
            return ftlCompositeElement.getName();
        } else if (ftlCompositeElement instanceof FtlBinaryExpression) {
            FtlBinaryExpression ftlBinaryExpression = (FtlBinaryExpression)ftlCompositeElement;

            FtlExpression leftOperand = ftlBinaryExpression.getLeftOperand();

            if (leftOperand != null) {
                return leftOperand.getText();
            }
        }

        return "";
    }

    @Override
    protected boolean isContextSuitableForBodyInjection(PsiElement context) {
        return context instanceof FtlMacro;
    }

    @NotNull
    @Override
    protected FtlCompositeElement[] getAttributes(@NotNull PsiElement psiElement) {
        FtlCompositeElement ftlCompositeElement = PsiTreeUtil.getParentOfType(psiElement, FtlCompositeElement.class);

        if (ftlCompositeElement != null) {
            return new FtlCompositeElement[]{ftlCompositeElement};
        }

        return new FtlCompositeElement[0];
    }

    @NotNull
    public List<? extends Class<? extends PsiElement>> elementsToInjectIn() {
        return Arrays.asList(FtlMacro.class, FtlStringLiteral.class);
    }

    private PsiElement getValueElement(FtlCompositeElement ftlCompositeElement) {
        if (ftlCompositeElement instanceof FtlNameValuePair) {
            return ((FtlNameValuePair)ftlCompositeElement).getValueElement();
        } else if (ftlCompositeElement instanceof FtlBinaryExpression) {
            FtlBinaryExpression ftlBinaryExpression = (FtlBinaryExpression)ftlCompositeElement;

            FtlExpression rightOperand = ftlBinaryExpression.getRightOperand();

            if (rightOperand instanceof FtlStringLiteral) {
                return rightOperand;
            }
        }

        return null;
    }

    @Override
    protected void injectIntoAttribute(@NotNull MultiHostRegistrar registrar, FtlCompositeElement ftlCompositeElement, PsiElement context) {
        PsiElement valueElement = getValueElement(ftlCompositeElement);

        if (valueElement instanceof FtlStringLiteral) {
            FtlStringLiteral ftlStringLiteral = (FtlStringLiteral)valueElement;

            if (ftlStringLiteral.isValidHost()) {
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

                if (!(ranges.isEmpty())) {
                    registrar.startInjecting(JavascriptLanguage.INSTANCE);
                    for (TextRange textRange : ranges) {
                        registrar.addPlace(null, null, (PsiLanguageInjectionHost) context, textRange);
                    }
                    registrar.doneInjecting();
                }
            }
        }
    }

    @Override
    protected void injectIntoBody(@NotNull MultiHostRegistrar registrar, FtlMacro ftlMacro, PsiElement context) {
        if (ftlMacro.isValidHost()) {
            TextRange injectionRange = ftlMacro.getInjectionRange();
            if (injectionRange != null) {
                if (!(injectionRange.isEmpty())) {
                    registrar.startInjecting(JavascriptLanguage.INSTANCE);
                    registrar.addPlace(null, null, ftlMacro, injectionRange);
                    registrar.doneInjecting();
                }
            } else {
                int startOffset = ftlMacro.getTextRange().getStartOffset();
                List<TextRange> ranges = new SmartList<>();

                ASTNode node = ftlMacro.getNode();
                ASTNode[] children = node.getChildren(TokenSet.create(FtlElementTypes.TEMPLATE_TEXT));
                for (ASTNode child : children) {
                    TextRange textRange = child.getTextRange();
                    TextRange rangeInElement = textRange.shiftLeft(startOffset);
                    ranges.add(rangeInElement);
                }

                if (!(ranges.isEmpty())) {
                    registrar.startInjecting(JavascriptLanguage.INSTANCE);
                    for (TextRange textRange : ranges) {
                        registrar.addPlace(null, null, ftlMacro, textRange);
                    }
                    registrar.doneInjecting();
                }
            }
        }
    }

}
