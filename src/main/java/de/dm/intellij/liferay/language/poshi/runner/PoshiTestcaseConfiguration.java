package de.dm.intellij.liferay.language.poshi.runner;

import com.intellij.execution.CommonJavaRunConfigurationParameters;
import com.intellij.execution.ExecutionBundle;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.LocatableConfigurationBase;
import com.intellij.execution.configurations.RefactoringListenerProvider;
import com.intellij.execution.configurations.RunConfigurationOptions;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.options.SettingsEditorGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.refactoring.listeners.RefactoringElementAdapter;
import com.intellij.refactoring.listeners.RefactoringElementListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class PoshiTestcaseConfiguration extends LocatableConfigurationBase<RunConfigurationOptions> implements CommonJavaRunConfigurationParameters, RefactoringListenerProvider {

    protected PoshiTestcaseConfiguration(@NotNull Project project, @NotNull ConfigurationFactory factory, @Nullable String name) {
        super(project, factory, name);
    }

    @Override
    protected @NotNull PoshiTestcaseConfigurationOptions getOptions() {
        return (PoshiTestcaseConfigurationOptions)super.getOptions();
    }

    @Override
    public @NotNull SettingsEditor<PoshiTestcaseConfiguration> getConfigurationEditor() {
        SettingsEditorGroup<PoshiTestcaseConfiguration> group = new SettingsEditorGroup<PoshiTestcaseConfiguration>();

        String title = ExecutionBundle.message("run.configuration.configuration.tab.title");

        group.addEditor(title, new PoshiTestcaseConfigurationSettingsEditor(getProject()));

        return group;
    }

    @Override
    public @Nullable RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment environment) {
        return new PoshiTestcaseCommandLineState(environment, this);
    }

    public String getScriptName() {
        return getOptions().getScriptName();
    }

    public void setScriptName(String scriptName) {
        getOptions().setScriptName(scriptName);
    }

    public String getTestName() {
        return getOptions().getTestName();
    }

    public void setTestName(String testName) {
        getOptions().setTestName(testName);
    }

    @Override
    public void setVMParameters(@Nullable String value) {

    }

    @Override
    public String getVMParameters() {
        return null;
    }

    @Override
    public boolean isAlternativeJrePathEnabled() {
        return false;
    }

    @Override
    public void setAlternativeJrePathEnabled(boolean enabled) {

    }

    @Override
    public @Nullable String getAlternativeJrePath() {
        return null;
    }

    @Override
    public void setAlternativeJrePath(@Nullable String path) {

    }

    @Override
    public @Nullable String getRunClass() {
        return null;
    }

    @Override
    public @Nullable String getPackage() {
        return null;
    }

    @Override
    public void setProgramParameters(@Nullable String value) {

    }

    @Override
    public @Nullable String getProgramParameters() {
        return null;
    }

    @Override
    public void setWorkingDirectory(@Nullable String value) {

    }

    @Override
    public @Nullable String getWorkingDirectory() {
        return null;
    }

    @Override
    public void setEnvs(@NotNull Map<String, String> envs) {

    }

    @Override
    public @NotNull Map<String, String> getEnvs() {
        return Collections.emptyMap();
    }

    @Override
    public void setPassParentEnvs(boolean passParentEnvs) {

    }

    @Override
    public boolean isPassParentEnvs() {
        return false;
    }

    @Override
    public @Nullable RefactoringElementListener getRefactoringElementListener(PsiElement element) {
        if (element instanceof PsiFile) {
            VirtualFile virtualFile = ((PsiFile)element).getVirtualFile();

            if (virtualFile != null && Objects.equals(new File(virtualFile.getPath()).getAbsolutePath(), new File(getScriptName()).getAbsolutePath())) {
                return new RefactoringElementAdapter() {
                    @Override
                    public void elementRenamedOrMoved(@NotNull PsiElement newElement) {
                        VirtualFile virtualFile = ((PsiFile)newElement).getVirtualFile();
                        if (virtualFile != null) {
                            updateScriptName(virtualFile.getPath());
                        }
                    }

                    @Override
                    public void undoElementMovedOrRenamed(@NotNull PsiElement newElement, @NotNull String oldQualifiedName) {
                        updateScriptName(oldQualifiedName);
                    }

                    private void updateScriptName(String path) {
                        setScriptName(FileUtil.toSystemDependentName(path));
                    }
                };
            }
        }
        return null;
    }
}
