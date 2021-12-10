package de.dm.intellij.bndtools.parser;

import com.intellij.codeInsight.daemon.JavaErrorBundle;
import com.intellij.lang.ASTNode;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.SmartList;
import com.intellij.util.containers.ContainerUtil;
import de.dm.intellij.bndtools.psi.Attribute;
import de.dm.intellij.bndtools.psi.BndHeader;
import de.dm.intellij.bndtools.psi.BndHeaderValue;
import de.dm.intellij.bndtools.psi.BndHeaderValuePart;
import de.dm.intellij.bndtools.psi.BndToken;
import de.dm.intellij.bndtools.psi.BndTokenType;
import de.dm.intellij.bndtools.psi.Clause;
import de.dm.intellij.bndtools.psi.Directive;
import de.dm.intellij.bndtools.psi.util.BndPsiUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExportPackageParser extends BasePackageParser {

    public static final ExportPackageParser INSTANCE = new ExportPackageParser();

    private static final TokenSet TOKEN_SET = TokenSet.create(BndTokenType.HEADER_VALUE_PART);

    @NotNull
    @Override
    public PsiReference[] getReferences(@NotNull BndHeaderValuePart bndHeaderValuePart) {
        PsiElement parentPsiElement = bndHeaderValuePart.getParent();

        if (parentPsiElement instanceof Clause) {
            PsiElement originalElement = bndHeaderValuePart.getOriginalElement();

            PsiElement prevSibling = originalElement.getPrevSibling();

            if (
                ! (prevSibling instanceof BndToken) ||
                ((BndToken)prevSibling).getTokenType() != BndTokenType.SEMICOLON
            ) {
                return BndPsiUtil.getPackageReferences(bndHeaderValuePart);
            }
        } else if (isUsesDirectiveAttributeOrDirective(parentPsiElement)) {
            List<PsiReference> psiReferences = new SmartList<>();

            ASTNode headerValuePartNode = bndHeaderValuePart.getNode();

            ASTNode[] childNodes = headerValuePartNode.getChildren(TOKEN_SET);

            for (ASTNode childNode : childNodes) {
                if (childNode instanceof BndToken) {
                    BndToken bndToken = (BndToken) childNode;

                    ContainerUtil.addAll(psiReferences, BndPsiUtil.getPackageReferences(bndToken));
                }
            }

            return psiReferences.toArray(new PsiReference[0]);
        }

        return PsiReference.EMPTY_ARRAY;
    }

    @Override
    public boolean annotate(@NotNull BndHeader bndHeader, @NotNull AnnotationHolder annotationHolder) {
        if (super.annotate(bndHeader, annotationHolder)) {
            return true;
        }

        boolean annotated = false;

        for (BndHeaderValue bndHeaderValue : bndHeader.getBndHeaderValues()) {
            if (bndHeaderValue instanceof Clause) {
                Clause clause = (Clause)bndHeaderValue;

                Directive usesDirective = clause.getDirective(OsgiConstants.USES_DIRECTIVE);

                if (usesDirective != null) {
                    BndHeaderValuePart valueElement = usesDirective.getValueElement();

                    if (valueElement != null) {
                        String text = StringUtil.trimTrailing(valueElement.getText());

                        int start;
                        if (StringUtil.startsWithChar(text, '"')) {
                            start = 1;
                        } else {
                            start = 0;
                        }

                        int length;
                        if (StringUtil.endsWithChar(text, '"')) {
                            length = text.length() -1;
                        } else {
                            length = text.length();
                        }

                        int offset = valueElement.getTextOffset();

                        while (start < length) {
                            int end = text.indexOf(',', start);

                            if (end < 0) {
                                end = length;
                            }

                            TextRange textRange = new TextRange(start, end);

                            start = end + 1;

                            String packageName = textRange.substring(text);

                            packageName = packageName.replaceAll("\\s", "");

                            if (StringUtil.isEmptyOrSpaces(packageName)) {
                                TextRange highlightTextRange = textRange.shiftRight(offset);

                                annotationHolder.newAnnotation(
                                        HighlightSeverity.ERROR, "Invalid reference"
                                ).range(
                                        highlightTextRange
                                ).create();

                                annotated = true;

                                continue;
                            }

                            PsiDirectory[] psiDirectories = BndPsiUtil.resolvePackage(bndHeader, packageName);

                            if (psiDirectories.length == 0) {
                                TextRange highlightTextRange = BndPsiUtil.adjustTextRangeWithoutWhitespaces(textRange, text).shiftRight(offset);

                                annotationHolder.newAnnotation(
                                        HighlightSeverity.ERROR, JavaErrorBundle.message("cannot.resolve.package", packageName)
                                ).range(
                                        highlightTextRange
                                ).create();

                                annotated = true;
                            }
                        }
                    }
                }
            }
        }

        return annotated;
    }

    private boolean isUsesDirectiveAttributeOrDirective(PsiElement psiElement) {
        if (psiElement instanceof Attribute) {
            Attribute attribute = (Attribute)psiElement;

            return (OsgiConstants.USES_DIRECTIVE.equals(attribute.getName()));
        }
        if (psiElement instanceof Directive) {
            Directive directive = (Directive)psiElement;

            return (OsgiConstants.USES_DIRECTIVE.equals(directive.getName()));
        }

        return false;
    }
}
