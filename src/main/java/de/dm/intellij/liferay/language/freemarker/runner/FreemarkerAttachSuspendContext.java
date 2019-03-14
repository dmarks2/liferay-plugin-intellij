package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.xdebugger.XSourcePosition;
import com.intellij.xdebugger.breakpoints.XLineBreakpoint;
import com.intellij.xdebugger.frame.XExecutionStack;
import com.intellij.xdebugger.frame.XSuspendContext;
import freemarker.debug.DebuggedEnvironment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FreemarkerAttachSuspendContext extends XSuspendContext {

    private DebuggedEnvironment debuggedEnvironment;

    private FreemarkerAttachExecutionStack executionStack;

    public FreemarkerAttachSuspendContext(@NotNull DebuggedEnvironment debuggedEnvironment, XLineBreakpoint<FreemarkerAttachBreakpointProperties> xLineBreakpoint) {
        this(debuggedEnvironment, xLineBreakpoint.getSourcePosition());
    }

    public FreemarkerAttachSuspendContext(@NotNull DebuggedEnvironment debuggedEnvironment, XSourcePosition xSourcePosition) {
        this.debuggedEnvironment = debuggedEnvironment;

        this.executionStack = new FreemarkerAttachExecutionStack(debuggedEnvironment, xSourcePosition);
    }

    public DebuggedEnvironment getDebuggedEnvironment() {
        return debuggedEnvironment;
    }

    @Nullable
    @Override
    public XExecutionStack getActiveExecutionStack() {
        return executionStack;
    }

}
