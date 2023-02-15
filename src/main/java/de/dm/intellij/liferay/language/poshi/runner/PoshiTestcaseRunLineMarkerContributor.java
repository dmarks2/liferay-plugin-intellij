package de.dm.intellij.liferay.language.poshi.runner;

import com.intellij.execution.lineMarker.ExecutorAction;
import com.intellij.execution.lineMarker.RunLineMarkerContributor;
import com.intellij.icons.AllIcons;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import de.dm.intellij.liferay.language.poshi.constants.PoshiConstants;
import de.dm.intellij.liferay.language.poshi.psi.PoshiTestDefinition;
import de.dm.intellij.liferay.language.poshi.psi.PoshiTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PoshiTestcaseRunLineMarkerContributor extends RunLineMarkerContributor  {
    @Override
    public @Nullable RunLineMarkerContributor.Info getInfo(@NotNull PsiElement element) {
        PsiFile psiFile = element.getContainingFile().getOriginalFile();

        if (psiFile.getName().endsWith(PoshiConstants.TESTCASE_EXTENSION)) {
            if (PoshiTypes.IDENTIFIER.equals(element.getNode().getElementType())) {
                if (element.getParent() instanceof PoshiTestDefinition) {
                    return new RunLineMarkerContributor.Info(AllIcons.RunConfigurations.TestState.Run, ExecutorAction.getActions(1), RUN_TEST_TOOLTIP_PROVIDER);
                }
            }
        }

        return null;
    }
}
