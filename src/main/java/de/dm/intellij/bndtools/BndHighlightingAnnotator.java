package de.dm.intellij.bndtools;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.psi.PsiElement;
import de.dm.intellij.bndtools.parser.BndHeaderParser;
import de.dm.intellij.bndtools.parser.BndHeaderParsers;
import de.dm.intellij.bndtools.psi.AssignmentExpression;
import de.dm.intellij.bndtools.psi.Attribute;
import de.dm.intellij.bndtools.psi.BndHeader;
import de.dm.intellij.bndtools.psi.BndHeaderValuePart;
import de.dm.intellij.bndtools.psi.BndToken;
import de.dm.intellij.bndtools.psi.BndTokenType;
import de.dm.intellij.bndtools.psi.Clause;
import de.dm.intellij.bndtools.psi.Directive;
import org.jetbrains.annotations.NotNull;

public class BndHighlightingAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        if (psiElement instanceof BndHeader) {
            BndHeader bndHeader = (BndHeader)psiElement;
            String name = bndHeader.getName();
            BndHeaderParser bndHeaderParser = BndHeaderParsers.PARSERS_MAP.get(name);
            if (bndHeaderParser != null) {
                bndHeaderParser.annotate(bndHeader, annotationHolder);
            }
        } else if (psiElement instanceof BndHeaderValuePart) {
            PsiElement parentPsiElement = psiElement.getParent();

            if (parentPsiElement instanceof AssignmentExpression) {
                AssignmentExpression assignmentExpression = (AssignmentExpression)parentPsiElement;

                BndHeaderValuePart nameElement = assignmentExpression.getNameElement();

                if (parentPsiElement instanceof Attribute) {
                    if (psiElement == nameElement) {
                        _annotate(psiElement, OsgiManifestColorsAndFonts.ATTRIBUTE_NAME_KEY, annotationHolder);
                    }
                    else {
                        _annotate(psiElement, OsgiManifestColorsAndFonts.ATTRIBUTE_VALUE_KEY, annotationHolder);
                    }
                }
                else if (parentPsiElement instanceof Directive) {
                    if (psiElement == nameElement) {
                        _annotate(psiElement, OsgiManifestColorsAndFonts.DIRECTIVE_NAME_KEY, annotationHolder);
                    }
                    else {
                        _annotate(psiElement, OsgiManifestColorsAndFonts.DIRECTIVE_VALUE_KEY, annotationHolder);
                    }
                }
            }
        }
        else if (psiElement instanceof BndToken) {
            BndToken manifestToken = (BndToken)psiElement;

            BndTokenType type = manifestToken.getTokenType();

            if ((psiElement.getParent() instanceof Attribute) && (type == BndTokenType.EQUALS)) {
                _annotate(psiElement, OsgiManifestColorsAndFonts.ATTRIBUTE_ASSIGNMENT_KEY, annotationHolder);
            }
            else if ((psiElement.getParent() instanceof Directive) &&
                ((type == BndTokenType.COLON) || (type == BndTokenType.EQUALS))) {

                _annotate(psiElement, OsgiManifestColorsAndFonts.DIRECTIVE_ASSIGNMENT_KEY, annotationHolder);
            }
            else if ((psiElement.getParent() instanceof Clause) && (type == BndTokenType.SEMICOLON)) {
                _annotate(psiElement, OsgiManifestColorsAndFonts.PARAMETER_SEPARATOR_KEY, annotationHolder);
            }
            else if ((psiElement.getParent() instanceof BndHeader) && (type == BndTokenType.COMMA)) {
                _annotate(psiElement, OsgiManifestColorsAndFonts.CLAUSE_SEPARATOR_KEY, annotationHolder);
            }
        }
    }

    private static void _annotate(
        PsiElement psiElement, TextAttributesKey textAttributesKey, AnnotationHolder annotationHolder) {

        if (psiElement != null) {
            Annotation annotation = annotationHolder.createInfoAnnotation(psiElement, null);

            annotation.setTextAttributes(textAttributesKey);
        }
    }

}
