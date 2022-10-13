package de.dm.intellij.liferay.language.freemarker.themereference;

import com.intellij.freemarker.psi.variables.FtlLightVariable;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import de.dm.intellij.liferay.theme.LiferayThemeTemplateVariables;
import de.dm.intellij.liferay.util.Icons;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class ThemeReferenceFtlVariable extends FtlLightVariable {

    public ThemeReferenceFtlVariable(String name, PsiElement parent) {
        super(name, parent, "java.lang.String");
    }

    @Override
    public Icon getIcon(boolean open) {
        return Icons.LIFERAY_ICON;
    }

    @NotNull
    @Override
    public PsiElement getNavigationElement() {
        if (LiferayThemeTemplateVariables.THEME_TEMPLATE_VARIABLE_DIRECTORY_REFERENCES.containsKey(getName())) {
            String themeSetting = LiferayThemeTemplateVariables.THEME_TEMPLATE_VARIABLE_DIRECTORY_REFERENCES.get(getName());

            PsiFile psiFile;
            if (getParent() instanceof PsiFile) {
                psiFile = (PsiFile)getParent();
            } else {
                psiFile = getParent().getContainingFile();
            }
            psiFile = psiFile.getOriginalFile();

            final Module module = ModuleUtil.findModuleForPsiElement(psiFile);
            if (module != null) {
                VirtualFile virtualFile = LiferayFileUtil.getThemeSettingsDirectory(module, themeSetting);
                if (virtualFile != null) {
                    PsiDirectory psiDirectory = psiFile.getContainingFile().getManager().findDirectory(virtualFile);

                    if (psiDirectory != null) {
                        return psiDirectory;
                    }
                }
            }
        }
        return super.getNavigationElement();
    }
}
