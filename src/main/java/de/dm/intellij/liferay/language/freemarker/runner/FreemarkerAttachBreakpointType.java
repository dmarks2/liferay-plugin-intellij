package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.freemarker.psi.files.FtlFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.xdebugger.breakpoints.XLineBreakpointType;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FreemarkerAttachBreakpointType extends XLineBreakpointType<FreemarkerAttachBreakpointProperties> {

    public FreemarkerAttachBreakpointType() {
        super("ftl", "Freemarker Breakpoints");
    }

    @Nullable
    @Override
    public FreemarkerAttachBreakpointProperties createBreakpointProperties(@NotNull VirtualFile file, int line) {
        return null;
    }

    @Override
    public boolean canPutAt(@NotNull VirtualFile file, int line, @NotNull Project project) {
        boolean isFtl = FtlFileType.INSTANCE.equals(file.getFileType());
        if (isFtl) {
            PsiFile psiFile = PsiManager.getInstance(project).findFile(file);

            if (psiFile != null) {
                if (
                    (LiferayFileUtil.isThemeTemplateFile(psiFile)) ||
                    (LiferayFileUtil.isLayoutTemplateFile(psiFile)) ||
                    (LiferayFileUtil.isJournalTemplateFile(psiFile)) ||
                    (LiferayFileUtil.isApplicationDisplayTemplateFile(psiFile))
                ) {
                    return true;
                }
            }
        }

        return false;
    }
}
