package de.dm.intellij.liferay.language.groovy;

import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.ConfigurationTypeUtil;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class LiferayServerGroovyConfigurationProducer extends RunConfigurationProducer<LiferayServerGroovyConfiguration> {

    public LiferayServerGroovyConfigurationProducer() {
        super(false);
    }

    @Override
    protected boolean setupConfigurationFromContext(@NotNull LiferayServerGroovyConfiguration configuration, @NotNull ConfigurationContext context, @NotNull Ref<PsiElement> sourceElement) {
        PsiElement psiElement = context.getPsiLocation();

        if (psiElement != null && psiElement.getContainingFile() != null) {
            PsiFile psiFile = (psiElement.getContainingFile());

            VirtualFile virtualFile = psiFile.getVirtualFile();

            if (virtualFile != null && virtualFile.exists() && "groovy".equals(virtualFile.getExtension())) {
                configuration.setScriptName(new File(virtualFile.getPath()).getAbsolutePath());

                configuration.setName(virtualFile.getNameWithoutExtension());

                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isConfigurationFromContext(@NotNull LiferayServerGroovyConfiguration configuration, @NotNull ConfigurationContext context) {
        PsiElement psiElement = context.getPsiLocation();

        if (psiElement != null && psiElement.getContainingFile() != null) {
            PsiFile psiFile = (psiElement.getContainingFile());

            VirtualFile virtualFile = psiFile.getVirtualFile();

            if (virtualFile != null) {
                return new File(virtualFile.getPath()).getAbsolutePath().equals(configuration.getScriptName());
            }
        }

        return false;
    }

    @Override
    public @NotNull ConfigurationFactory getConfigurationFactory() {
        ConfigurationType configurationType = ConfigurationTypeUtil.findConfigurationType(LiferayServerGroovyConfigurationType.ID);

        return new LiferayServerGroovyConfigurationFactory(configurationType);
    }
}
