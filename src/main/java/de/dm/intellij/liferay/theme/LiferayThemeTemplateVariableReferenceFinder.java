package de.dm.intellij.liferay.theme;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import de.dm.intellij.liferay.language.TemplateVariableReferenceFinder;
import de.dm.intellij.liferay.util.LiferayFileUtil;

import java.util.HashMap;
import java.util.Map;

public class LiferayThemeTemplateVariableReferenceFinder implements TemplateVariableReferenceFinder {

    public static final Map<String, String> THEME_TEMPLATE_VARIABLE_DIRECTORY_REFERENCES = new HashMap<String, String>();

    static {
        THEME_TEMPLATE_VARIABLE_DIRECTORY_REFERENCES.put("images_folder", LiferayLookAndFeelXmlParser.IMAGES_PATH);
        THEME_TEMPLATE_VARIABLE_DIRECTORY_REFERENCES.put("css_folder", LiferayLookAndFeelXmlParser.CSS_PATH);
        THEME_TEMPLATE_VARIABLE_DIRECTORY_REFERENCES.put("full_css_path", LiferayLookAndFeelXmlParser.CSS_PATH);
        THEME_TEMPLATE_VARIABLE_DIRECTORY_REFERENCES.put("javascript_folder", LiferayLookAndFeelXmlParser.JAVASCRIPT_PATH);
        THEME_TEMPLATE_VARIABLE_DIRECTORY_REFERENCES.put("templates_folder", LiferayLookAndFeelXmlParser.TEMPLATES_PATH);
        THEME_TEMPLATE_VARIABLE_DIRECTORY_REFERENCES.put("full_templates_path", LiferayLookAndFeelXmlParser.TEMPLATES_PATH);
    }

    @Override
    public PsiElement getNavigationalElement(String templateVariable, PsiFile containingFile) {
        if (THEME_TEMPLATE_VARIABLE_DIRECTORY_REFERENCES.containsKey(templateVariable)) {
            String themeSetting = THEME_TEMPLATE_VARIABLE_DIRECTORY_REFERENCES.get(templateVariable);

            PsiFile psiFile = containingFile;
            if (psiFile.getOriginalFile() != null) {
                psiFile = psiFile.getOriginalFile();
            }
            final Module module = ModuleUtil.findModuleForPsiElement(psiFile);
            if (module != null) {
                VirtualFile virtualFile = LiferayFileUtil.getThemeSettingsDirectory(module, themeSetting);
                if (virtualFile != null) {
                    PsiDirectory psiDirectory = psiFile.getManager().findDirectory(virtualFile);

                    return psiDirectory;
                }
            }
        }

        return null;
    }
}
