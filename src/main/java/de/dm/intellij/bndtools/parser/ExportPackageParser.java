package de.dm.intellij.bndtools.parser;

import com.intellij.codeInsight.daemon.JavaErrorMessages;
import com.intellij.lang.ASTNode;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.containers.ContainerUtil;
import de.dm.intellij.bndtools.psi.Attribute;
import de.dm.intellij.bndtools.psi.Clause;
import de.dm.intellij.bndtools.psi.Directive;
import de.dm.intellij.bndtools.psi.util.BndPsiUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.lang.manifest.psi.Header;
import org.jetbrains.lang.manifest.psi.HeaderValue;
import org.jetbrains.lang.manifest.psi.HeaderValuePart;
import org.jetbrains.lang.manifest.psi.ManifestToken;
import org.jetbrains.lang.manifest.psi.ManifestTokenType;
import org.osgi.framework.Constants;

import java.util.List;

public class ExportPackageParser extends BasePackageParser {

    public static final ExportPackageParser INSTANCE = new ExportPackageParser();

    private static final TokenSet TOKEN_SET = TokenSet.create(ManifestTokenType.HEADER_VALUE_PART);

    @NotNull
    @Override
    public PsiReference[] getReferences(@NotNull HeaderValuePart headerValuePart) {
        PsiElement parent = headerValuePart.getParent();

        if (parent instanceof Clause) {
            PsiElement originalElement = headerValuePart.getOriginalElement();

            PsiElement prevSibling = originalElement.getPrevSibling();

            if (
                ! (prevSibling instanceof ManifestToken) ||
                ((ManifestToken)prevSibling).getTokenType() != ManifestTokenType.SEMICOLON
            ) {
                return BndPsiUtil.getPackageReferences(headerValuePart);
            }
        } else if (parent instanceof Attribute) {
            Attribute attribute = (Attribute)parent;

            if (Constants.USES_DIRECTIVE.equals(attribute.getName())) {
                List<PsiReference> psiReferences = ContainerUtil.newSmartList();

                ASTNode headerValuePartNode = headerValuePart.getNode();
                ASTNode[] childNodes = headerValuePartNode.getChildren(TOKEN_SET);

                for (ASTNode astNode : childNodes) {
                    if (astNode instanceof ManifestToken) {
                        ManifestToken manifestToken = (ManifestToken)astNode;

                        ContainerUtil.addAll(psiReferences, BndPsiUtil.getPackageReferences(manifestToken));
                    }
                }

                return psiReferences.toArray(new PsiReference[0]);
            }
        }

        return PsiReference.EMPTY_ARRAY;
    }

    @Override
    public boolean annotate(@NotNull Header header, @NotNull AnnotationHolder holder) {
        if (super.annotate(header, holder)) {
            return true;
        }

        boolean annotated = false;

        for (HeaderValue headerValue : header.getHeaderValues()) {
            if (headerValue instanceof Clause) {
                Clause clause = (Clause)headerValue;

                Directive usesDirective = clause.getDirective(Constants.USES_DIRECTIVE);

                if (usesDirective != null) {
                    HeaderValuePart valueElement = usesDirective.getValueElement();

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

                                holder.createErrorAnnotation(highlightTextRange, "Invalid reference");

                                annotated = true;

                                continue;
                            }

                            PsiDirectory[] psiDirectories = BndPsiUtil.resolvePackage(header, packageName);

                            if (psiDirectories.length == 0) {
                                TextRange highlightTextRange = BndPsiUtil.adjustTextRangeWithoutWhitespaces(textRange, text).shiftRight(offset);

                                holder.createErrorAnnotation(
                                    highlightTextRange,
                                    JavaErrorMessages.message("cannot.resolve.package", packageName)
                                );

                                annotated = true;
                            }
                        }
                    }
                }
            }
        }

        return annotated;
    }
}
