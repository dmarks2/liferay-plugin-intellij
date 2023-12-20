package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.xdebugger.XSourcePosition;
import com.intellij.xdebugger.frame.XExecutionStack;
import com.intellij.xdebugger.frame.XStackFrame;
import freemarker.debug.DebuggedEnvironment;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FreemarkerAttachExecutionStack extends XExecutionStack {

    private final FreemarkerAttachExecutionStackFrame topFrame;

    protected FreemarkerAttachExecutionStack(DebuggedEnvironment debuggedEnvironment, XSourcePosition xSourcePosition) {
        super("Freemarker");

        this.topFrame = new FreemarkerAttachExecutionStackFrame(debuggedEnvironment, xSourcePosition);
    }

    @Nullable
    @Override
    public XStackFrame getTopFrame() {
        return topFrame;
    }

    @Override
    public void computeStackFrames(int firstFrameIndex, XStackFrameContainer container) {
        List<FreemarkerAttachExecutionStackFrame> frames = new ArrayList<>();
        frames.add(topFrame);

        container.addStackFrames(frames, true);
    }
}
