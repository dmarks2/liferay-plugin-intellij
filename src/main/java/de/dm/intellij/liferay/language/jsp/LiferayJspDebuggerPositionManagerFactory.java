package de.dm.intellij.liferay.language.jsp;

import com.intellij.debugger.PositionManager;
import com.intellij.debugger.PositionManagerFactory;
import com.intellij.debugger.engine.DebugProcess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LiferayJspDebuggerPositionManagerFactory extends PositionManagerFactory {

    @Nullable
    @Override
    public PositionManager createPositionManager(@NotNull DebugProcess process) {
        return new LiferayJspDebuggerPositionManager(process);
    }
}
