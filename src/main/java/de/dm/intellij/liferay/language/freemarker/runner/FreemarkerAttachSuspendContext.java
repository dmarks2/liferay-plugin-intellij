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
    private XSourcePosition sourcePosition;

    private FreemarkerAttachExecutionStack executionStack;

    public FreemarkerAttachSuspendContext(@NotNull DebuggedEnvironment debuggedEnvironment, XLineBreakpoint<FreemarkerAttachBreakpointProperties> xLineBreakpoint) {
        this(debuggedEnvironment, xLineBreakpoint.getSourcePosition());
    }

    public FreemarkerAttachSuspendContext(@NotNull DebuggedEnvironment debuggedEnvironment, XSourcePosition sourcePosition) {
        this.debuggedEnvironment = debuggedEnvironment;

        this.executionStack = new FreemarkerAttachExecutionStack(debuggedEnvironment, sourcePosition);
        this.sourcePosition = sourcePosition;
    }

    @NotNull
    public DebuggedEnvironment getDebuggedEnvironment() {
        return debuggedEnvironment;
    }

    public XSourcePosition getSourcePosition() {
        return sourcePosition;
    }

    @Nullable
    @Override
    public XExecutionStack getActiveExecutionStack() {
        return executionStack;
    }

}
