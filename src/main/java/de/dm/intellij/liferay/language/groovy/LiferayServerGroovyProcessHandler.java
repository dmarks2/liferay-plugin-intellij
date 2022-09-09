package de.dm.intellij.liferay.language.groovy;

import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.OutputStream;

public class LiferayServerGroovyProcessHandler extends ProcessHandler {

    @Override
    protected void destroyProcessImpl() {

    }

    @Override
    protected void detachProcessImpl() {

    }

    @Override
    public boolean detachIsDefault() {
        return false;
    }

    @Override
    public @Nullable OutputStream getProcessInput() {
        return null;
    }

    @Override
    public void addProcessListener(@NotNull ProcessListener listener) {
        super.addProcessListener(listener);
    }
}
