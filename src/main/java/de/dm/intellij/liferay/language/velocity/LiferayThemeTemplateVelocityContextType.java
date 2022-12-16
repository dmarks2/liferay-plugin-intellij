package de.dm.intellij.liferay.language.velocity;

import com.intellij.codeInsight.template.TemplateActionContext;
import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.psi.PsiFile;
import com.intellij.velocity.psi.files.VtlFileType;
import com.intellij.velocity.psi.files.VtlSyntaxHighlighter;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LiferayThemeTemplateVelocityContextType extends TemplateContextType {

    protected LiferayThemeTemplateVelocityContextType() {
        super("Liferay theme template (velocity)");
    }

    @Override
    public boolean isInContext(@NotNull TemplateActionContext templateActionContext) {
        PsiFile originalFile = templateActionContext.getFile().getOriginalFile();

        return VtlFileType.INSTANCE.equals(templateActionContext.getFile().getFileType()) && LiferayFileUtil.isThemeTemplateFile(originalFile);
    }

    @Nullable
    @Override
    public SyntaxHighlighter createHighlighter() {
        return new VtlSyntaxHighlighter();
    }

}
