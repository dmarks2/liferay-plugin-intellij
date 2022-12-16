package de.dm.intellij.liferay.language.freemarker;

import com.intellij.codeInsight.template.TemplateActionContext;
import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.freemarker.psi.files.FtlFileType;
import com.intellij.freemarker.psi.files.FtlSyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.psi.PsiFile;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LiferayJournalTemplateFreemarkerContextType extends TemplateContextType {

    protected LiferayJournalTemplateFreemarkerContextType() {
        super("Liferay journal templates (freemarker)");
    }

    @Override
    public boolean isInContext(@NotNull TemplateActionContext templateActionContext) {
        PsiFile originalFile = templateActionContext.getFile().getOriginalFile();

        return FtlFileType.INSTANCE.equals(templateActionContext.getFile().getFileType()) && LiferayFileUtil.isJournalTemplateFile(originalFile);
    }

    @Nullable
    @Override
    public SyntaxHighlighter createHighlighter() {
        return new FtlSyntaxHighlighter();
    }

}
