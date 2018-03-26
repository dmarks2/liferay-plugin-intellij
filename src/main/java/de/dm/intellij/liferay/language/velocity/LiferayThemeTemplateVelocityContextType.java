package de.dm.intellij.liferay.language.velocity;

import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.psi.PsiFile;
import com.intellij.velocity.psi.files.VtlFileType;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;

public class LiferayThemeTemplateVelocityContextType extends TemplateContextType {

    protected LiferayThemeTemplateVelocityContextType() {
        super("LIFERAY THEME TEMPLATE VELOCITY", "Liferay Theme Template (Velocity)");
    }

    @Override
    public boolean isInContext(@NotNull PsiFile file, int offset) {
        PsiFile originalFile = file;
        if (file.getOriginalFile() != null) {
            originalFile = file.getOriginalFile();
        }
        return VtlFileType.INSTANCE.equals(file.getFileType()) && LiferayFileUtil.isThemeTemplateFile(originalFile);
    }
}
