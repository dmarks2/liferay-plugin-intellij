package de.dm.intellij.liferay.language.freemarker;

import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.freemarker.psi.files.FtlFileType;
import com.intellij.psi.PsiFile;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;

public class LiferayApplicationDisplayTemplateFreemarkerContextType extends TemplateContextType {

    protected LiferayApplicationDisplayTemplateFreemarkerContextType() {
        super("LIFERAY APPLICATION DISPLAY TEMPLATES FREEMARKER", "Liferay Application Display Templates (Freemarker)");
    }

    @Override
    public boolean isInContext(@NotNull PsiFile file, int offset) {
        PsiFile originalFile = file;
        if (file.getOriginalFile() != null) {
            originalFile = file.getOriginalFile();
        }
        return FtlFileType.INSTANCE.equals(file.getFileType()) && LiferayFileUtil.isApplicationDisplayTemplateFile(originalFile);
    }
}
