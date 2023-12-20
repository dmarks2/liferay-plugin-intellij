package de.dm.intellij.liferay.language.osgi;

import com.intellij.lang.HelpID;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MetaConfigurationFindUsagesProvider implements FindUsagesProvider {

    @Override
    public boolean canFindUsagesFor(@NotNull PsiElement psiElement) {
        return psiElement.getParent() instanceof PsiLiteralExpression;
    }

    @Override
    public @Nullable @NonNls String getHelpId(@NotNull PsiElement psiElement) {
        return HelpID.FIND_OTHER_USAGES;
    }

    @Override
    public @Nls @NotNull String getType(@NotNull PsiElement element) {
        return "Metadata Object Class Definition (OCD)";
    }

    @Override
    public @Nls @NotNull String getDescriptiveName(@NotNull PsiElement element) {
        return element.getText();
    }

    @Override
    public @Nls @NotNull String getNodeText(@NotNull PsiElement element, boolean useFullName) {
        return getDescriptiveName(element);
    }
}
