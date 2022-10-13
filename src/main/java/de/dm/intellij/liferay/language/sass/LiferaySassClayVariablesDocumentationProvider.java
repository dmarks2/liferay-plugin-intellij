package de.dm.intellij.liferay.language.sass;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.lang.documentation.DocumentationMarkup;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.scss.psi.SCSSElementGenerator;
import org.jetbrains.plugins.scss.psi.SassScssElement;
import org.jetbrains.plugins.scss.psi.SassScssVariableDeclaration;

/** see https://github.com/pat270/clay-paver/blob/v2-dev/views/partials/functions/printInputs.ejs **/
public class LiferaySassClayVariablesDocumentationProvider extends AbstractDocumentationProvider {

    @Nullable
    @Override
    public String generateDoc(PsiElement element, @Nullable PsiElement originalElement) {
        if (PlatformPatterns.
                psiElement(SassScssElement.class).
                accepts(element)
        ) {
            String name = null;

            if (element instanceof SassScssVariableDeclaration) {
                name = ((SassScssVariableDeclaration) element).getName();
            }

            if (name == null) {
                return null;
            }

            String description = ClayVariablesDocumentationBundle.message(name);

            if (description != null) {
                description = StringUtil.join(StringUtil.split(description, "\n"), "<br>");

                return DocumentationMarkup.DEFINITION_START +
                        name +
                        DocumentationMarkup.DEFINITION_END +
                        DocumentationMarkup.CONTENT_START +
                        description +
                        DocumentationMarkup.CONTENT_END;
            }
        }

        return null;
    }

    @Override
    public @Nullable @Nls String getQuickNavigateInfo(PsiElement element, PsiElement originalElement) {
        return generateDoc(element, originalElement);
    }

    @Override
    public @Nullable PsiElement getDocumentationElementForLookupItem(PsiManager psiManager, Object object, PsiElement element) {
        if (object instanceof String) {
            String lookupString = (String) object;

            SassScssVariableDeclaration variableDeclaration = SCSSElementGenerator.createVariableDeclaration(psiManager.getProject(), lookupString, "foo");

            return variableDeclaration.getNameIdentifier();
        }

        return super.getDocumentationElementForLookupItem(psiManager, object, element);
    }

}
