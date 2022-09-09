package de.dm.intellij.liferay.language.groovy;

import com.intellij.execution.lineMarker.ExecutorAction;
import com.intellij.execution.lineMarker.RunLineMarkerContributor;
import com.intellij.icons.AllIcons;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class LiferayServerGroovyRunLineMarkerProvider extends RunLineMarkerContributor {

    private static final Function<PsiElement, String> TOOLTIP_PROVIDER = psiElement -> "Run Script file";

    @Override
    public @Nullable Info getInfo(@NotNull PsiElement element) {
        if (element instanceof PsiFile) {
            return new Info(AllIcons.RunConfigurations.TestState.Run, ExecutorAction.getActions(1), TOOLTIP_PROVIDER);
        }

        return null;
    }
}
