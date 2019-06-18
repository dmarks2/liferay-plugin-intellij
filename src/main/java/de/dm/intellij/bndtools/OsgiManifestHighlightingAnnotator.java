package de.dm.intellij.bndtools;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.psi.PsiElement;
import de.dm.intellij.bndtools.psi.AssignmentExpression;
import de.dm.intellij.bndtools.psi.Attribute;
import de.dm.intellij.bndtools.psi.Clause;
import de.dm.intellij.bndtools.psi.Directive;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.lang.manifest.psi.Header;
import org.jetbrains.lang.manifest.psi.HeaderValuePart;
import org.jetbrains.lang.manifest.psi.ManifestToken;
import org.jetbrains.lang.manifest.psi.ManifestTokenType;

public class OsgiManifestHighlightingAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        if (psiElement instanceof HeaderValuePart) {
            PsiElement parentPsiElement = psiElement.getParent();

            if (parentPsiElement instanceof AssignmentExpression) {
                AssignmentExpression assignmentExpression = (AssignmentExpression)parentPsiElement;

                HeaderValuePart nameElement = assignmentExpression.getNameElement();

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
        else if (psiElement instanceof ManifestToken) {
            ManifestToken manifestToken = (ManifestToken)psiElement;

            ManifestTokenType type = manifestToken.getTokenType();

            if ((psiElement.getParent() instanceof Attribute) && (type == ManifestTokenType.EQUALS)) {
                _annotate(psiElement, OsgiManifestColorsAndFonts.ATTRIBUTE_ASSIGNMENT_KEY, annotationHolder);
            }
            else if ((psiElement.getParent() instanceof Directive) &&
                ((type == ManifestTokenType.COLON) || (type == ManifestTokenType.EQUALS))) {

                _annotate(psiElement, OsgiManifestColorsAndFonts.DIRECTIVE_ASSIGNMENT_KEY, annotationHolder);
            }
            else if ((psiElement.getParent() instanceof Clause) && (type == ManifestTokenType.SEMICOLON)) {
                _annotate(psiElement, OsgiManifestColorsAndFonts.PARAMETER_SEPARATOR_KEY, annotationHolder);
            }
            else if ((psiElement.getParent() instanceof Header) && (type == ManifestTokenType.COMMA)) {
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
