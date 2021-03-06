package de.dm.intellij.liferay.language.velocity;

import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.psi.PsiFile;
import com.intellij.velocity.psi.files.VtlFileType;
import com.intellij.velocity.psi.files.VtlSyntaxHighlighter;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LiferayApplicationDisplayTemplateVelocityContextType extends TemplateContextType {

    protected LiferayApplicationDisplayTemplateVelocityContextType() {
        super("LIFERAY APPLICATION DISPLAY TEMPLATES VELOCITY", "Liferay Application Display Templates (Velocity)");
    }

    @Override
    public boolean isInContext(@NotNull PsiFile file, int offset) {
        PsiFile originalFile = file;
        if (file.getOriginalFile() != null) {
            originalFile = file.getOriginalFile();
        }
        return VtlFileType.INSTANCE.equals(file.getFileType()) && LiferayFileUtil.isApplicationDisplayTemplateFile(originalFile);
    }

    @Nullable
    @Override
    public SyntaxHighlighter createHighlighter() {
        return new VtlSyntaxHighlighter();
    }

}
