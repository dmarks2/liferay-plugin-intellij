package de.dm.intellij.liferay.language.osgi;

import com.intellij.openapi.util.NlsSafe;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.ElementDescriptionLocation;
import com.intellij.psi.ElementDescriptionProvider;
import com.intellij.psi.PsiElement;
import com.intellij.usageView.UsageViewShortNameLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MetaConfigurationElementDescriptionProvider implements ElementDescriptionProvider {

    @Override
    public @Nullable @NlsSafe String getElementDescription(@NotNull PsiElement element, @NotNull ElementDescriptionLocation location) {
        if (location instanceof UsageViewShortNameLocation) {
            if (MetaConfigurationReference.isMetaConfigurationIdElement(element)) {
                return StringUtil.unquoteString(element.getText());
            }
        }

        return null;
    }
}
