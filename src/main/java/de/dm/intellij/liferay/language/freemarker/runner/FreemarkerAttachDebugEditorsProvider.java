package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.xdebugger.XExpression;
import com.intellij.xdebugger.XSourcePosition;
import com.intellij.xdebugger.evaluation.EvaluationMode;
import com.intellij.xdebugger.evaluation.XDebuggerEditorsProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FreemarkerAttachDebugEditorsProvider extends XDebuggerEditorsProvider {

    @NotNull
    @Override
    public FileType getFileType() {
        return FileTypes.UNKNOWN;
    }

    @NotNull
    @Override
    public Document createDocument(@NotNull Project project, @NotNull XExpression expression, @Nullable XSourcePosition sourcePosition, @NotNull EvaluationMode mode) {
        return EditorFactory.getInstance().createDocument(expression.getExpression());
    }
}
