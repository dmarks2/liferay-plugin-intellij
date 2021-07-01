package de.dm.intellij.liferay.language.sass;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.ui.GuiUtils;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.sass.highlighting.SassScssHighlightingColors;
import org.jetbrains.plugins.scss.psi.SassScssElement;
import org.jetbrains.plugins.scss.psi.SassScssVariableDeclaration;
import org.jetbrains.plugins.scss.psi.SassScssVariableImpl;

import java.awt.Color;

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

                @NonNls String info = "";

                TextAttributes attributes = EditorColorsManager.getInstance().getGlobalScheme().getAttributes(SassScssHighlightingColors.COMMENT).clone();

                Color background = attributes.getBackgroundColor();
                if (background != null) {
                    info +="<div bgcolor=#"+ GuiUtils.colorToHex(background)+">";
                }

                final Color foreground = attributes.getForegroundColor();
                info += foreground != null ? "<font color=#" + GuiUtils.colorToHex(foreground) + ">" + description + "</font>" : description;
                info += "\n<br>";
                if (background != null) {
                    info += "</div>";
                }

                info += "\n<b>" + name + "</b>";
                info += getLocationString(element);
                return info;
            }
        }

        return null;
    }

    private static String getLocationString(PsiElement element) {
        PsiFile file = element.getContainingFile();
        return file != null ? " [" + file.getName() + "]" : "";
    }

    @NotNull
    private static String renderPropertyValue(SassScssVariableImpl variable) {
        String raw = variable.getName();

        return StringUtil.escapeXmlEntities(raw);
    }
}
