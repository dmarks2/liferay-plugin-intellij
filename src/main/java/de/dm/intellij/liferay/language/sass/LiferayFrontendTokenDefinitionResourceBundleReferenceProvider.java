package de.dm.intellij.liferay.language.sass;

import com.intellij.lang.properties.references.PropertyReference;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class LiferayFrontendTokenDefinitionResourceBundleReferenceProvider extends PsiReferenceProvider {

    @Override
    public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        String text = StringUtil.unquoteString(element.getText());

        return new PsiReference[] {
                new PropertyReference(text, element, null, true)
        };
    }
}
