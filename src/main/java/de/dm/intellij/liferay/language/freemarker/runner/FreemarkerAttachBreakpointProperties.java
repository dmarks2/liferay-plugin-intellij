package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.xdebugger.breakpoints.XBreakpointProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FreemarkerAttachBreakpointProperties extends XBreakpointProperties<FreemarkerAttachBreakpointProperties> {

    @Nullable
    @Override
    public FreemarkerAttachBreakpointProperties getState() {
        return null;
    }

    @Override
    public void loadState(@NotNull FreemarkerAttachBreakpointProperties state) {
    }
}
