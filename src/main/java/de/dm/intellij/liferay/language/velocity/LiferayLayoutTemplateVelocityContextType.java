package de.dm.intellij.liferay.language.velocity;

import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.psi.PsiFile;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;

public class LiferayLayoutTemplateVelocityContextType extends TemplateContextType {

    protected LiferayLayoutTemplateVelocityContextType() {
        super("LIFERAY LAYOUT TEMPLATE VELOCITY", "Liferay Layout Template (Velocity)");
    }

    @Override
    public boolean isInContext(@NotNull PsiFile file, int offset) {
        PsiFile originalFile = file;
        if (file.getOriginalFile() != null) {
            originalFile = file.getOriginalFile();
        }
        return LiferayFileUtil.isLayoutTemplateFile(originalFile);
    }
}
