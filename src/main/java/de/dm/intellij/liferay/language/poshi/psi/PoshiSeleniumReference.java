package de.dm.intellij.liferay.language.poshi.psi;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import de.dm.intellij.liferay.language.poshi.constants.PoshiConstants;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PoshiSeleniumReference extends PsiReferenceBase<PsiElement>  {

    public PoshiSeleniumReference(@NotNull PsiElement element) {
        super(element, TextRange.create(0, element.getTextRange().getLength()));
    }

    @Override
    public @Nullable PsiElement resolve() {
        TextRange originalRange = getElement().getTextRange();

        TextRange valueRange = TextRange.create(0, originalRange.getLength());

        String valueString = valueRange.substring(getElement().getText());

        if (PoshiConstants.SELENIUM_KEYWORD.equals(valueString)) {
            return new PoshiSeleniumPsiElement(getElement().getProject());
        }

        return null;
    }

    @Override
    public Object @NotNull [] getVariants() {
        List<Object> result = new ArrayList<>();

        result.add(LookupElementBuilder.create(PoshiConstants.SELENIUM_KEYWORD).withIcon(Icons.LIFERAY_ICON));

        return result.toArray(new Object[0]);
    }
}
