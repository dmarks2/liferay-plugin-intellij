package de.dm.intellij.liferay.language.freemarker;

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
        super("LIFERAY JOURNAL TEMPLATES FREEMARKER", "Liferay Journal Templates (Freemarker)");
    }

    @Override
    public boolean isInContext(@NotNull PsiFile file, int offset) {
        PsiFile originalFile = file;
        if (file.getOriginalFile() != null) {
            originalFile = file.getOriginalFile();
        }
        return FtlFileType.INSTANCE.equals(file.getFileType()) && LiferayFileUtil.isJournalTemplateFile(originalFile);
    }

    @Nullable
    @Override
    public SyntaxHighlighter createHighlighter() {
        return new FtlSyntaxHighlighter();
    }

}
