package de.dm.intellij.liferay.language.freemarker;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.psi.PsiElement;
import de.dm.intellij.liferay.language.TemplateVariable;
import de.dm.intellij.liferay.language.freemarker.structure.StructureFtlVariable;
import org.jetbrains.annotations.Nullable;

public class LiferayFreemarkerDocumentationProvider extends AbstractDocumentationProvider {

    @Override
    public String generateDoc(PsiElement element, @Nullable PsiElement originalElement) {
        if (element instanceof StructureFtlVariable) {
            StructureFtlVariable structureFtlVariable = (StructureFtlVariable)element;

            TemplateVariable templateVariable = structureFtlVariable.getTemplateVariable();

            String result = "<b>" + templateVariable.getName() + "</b>";

            if (templateVariable.getType() != null) {
                result = result + " : " + templateVariable.getType();
            }

            if (templateVariable.getDefaultLanguageId() != null) {
                String label = templateVariable.getLabels().get(templateVariable.getDefaultLanguageId());
                String tip = templateVariable.getTips().get(templateVariable.getDefaultLanguageId());
                if ( (label != null) || (tip != null) ) {
                    result = result + "<hr />";
                }
                if (label != null) {
                    result = result + label + "<br/>";
                }
                if (tip != null) {
                    result = result + tip + "<br/>";
                }
            }
            return result;
        }
        return super.generateDoc(element, originalElement);
    }

}
