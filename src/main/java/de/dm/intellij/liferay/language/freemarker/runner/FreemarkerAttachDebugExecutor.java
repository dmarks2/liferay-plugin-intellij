package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.execution.executors.DefaultDebugExecutor;
import org.jetbrains.annotations.NotNull;

public class FreemarkerAttachDebugExecutor extends DefaultDebugExecutor {

    public static FreemarkerAttachDebugExecutor INSTANCE = new FreemarkerAttachDebugExecutor();

    private FreemarkerAttachDebugExecutor() {
    }

    @NotNull
    @Override
    public String getId() {
        return DefaultDebugExecutor.EXECUTOR_ID;
        //return "FreemarkerAttachDebug";
    }
}
