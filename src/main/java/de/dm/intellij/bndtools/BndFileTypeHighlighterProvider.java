package de.dm.intellij.bndtools;

import com.intellij.ide.highlighter.custom.CustomFileHighlighter;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterProvider;
import com.intellij.openapi.fileTypes.impl.CustomSyntaxTableFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BndFileTypeHighlighterProvider implements SyntaxHighlighterProvider {

    @Nullable
    @Override
    public SyntaxHighlighter create(@NotNull FileType fileType, @Nullable Project project, @Nullable VirtualFile file) {
        if (fileType instanceof BndFileType) {
            return new CustomFileHighlighter(((CustomSyntaxTableFileType) fileType).getSyntaxTable());
        }
        return null;
    }
}
