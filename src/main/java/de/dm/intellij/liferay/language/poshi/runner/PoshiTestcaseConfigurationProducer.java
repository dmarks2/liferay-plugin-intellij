package de.dm.intellij.liferay.language.poshi.runner;

import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.ConfigurationTypeUtil;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import de.dm.intellij.liferay.language.poshi.constants.PoshiConstants;
import de.dm.intellij.liferay.language.poshi.psi.PoshiTestDefinition;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

public class PoshiTestcaseConfigurationProducer extends RunConfigurationProducer<PoshiTestcaseConfiguration> {

    public PoshiTestcaseConfigurationProducer() {
        super(false);
    }

    @Override
    protected boolean setupConfigurationFromContext(@NotNull PoshiTestcaseConfiguration configuration, @NotNull ConfigurationContext context, @NotNull Ref<PsiElement> sourceElement) {
        PsiElement psiElement = context.getPsiLocation();

        if (psiElement != null && psiElement.getContainingFile() != null) {
            PsiFile psiFile = (psiElement.getContainingFile());

            if (psiFile.getName().endsWith(PoshiConstants.TESTCASE_EXTENSION)) {
                if (psiElement instanceof PoshiTestDefinition poshiTestDefinition) {
                    String testName = poshiTestDefinition.getName();

                    VirtualFile virtualFile = psiFile.getVirtualFile();

                    if (virtualFile != null && virtualFile.exists() && "testcase".equals(virtualFile.getExtension())) {
                        configuration.setScriptName(new File(virtualFile.getPath()).getAbsolutePath());
                        configuration.setTestName(testName);

                        configuration.setName(virtualFile.getNameWithoutExtension() + "#" + testName);

                        return true;
                    }
                }
            }


        }
        return false;
    }

    @Override
    public boolean isConfigurationFromContext(@NotNull PoshiTestcaseConfiguration configuration, @NotNull ConfigurationContext context) {
        PsiElement psiElement = context.getPsiLocation();

        if (psiElement != null && psiElement.getContainingFile() != null) {
            PsiFile psiFile = (psiElement.getContainingFile());

            VirtualFile virtualFile = psiFile.getVirtualFile();

            if (virtualFile != null) {
                if (psiElement instanceof PoshiTestDefinition poshiTestDefinition) {
                    String testName = poshiTestDefinition.getName();

                    return
                            new File(virtualFile.getPath()).getAbsolutePath().equals(configuration.getScriptName()) &&
                                    Objects.equals(testName, configuration.getTestName());
                }
            }
        }

        return false;
    }

    @Override
    public @NotNull ConfigurationFactory getConfigurationFactory() {
        ConfigurationType configurationType = ConfigurationTypeUtil.findConfigurationType(PoshiTestcaseConfigurationType.ID);

        return new PoshiTestcaseConfigurationFactory(configurationType);
    }
}
