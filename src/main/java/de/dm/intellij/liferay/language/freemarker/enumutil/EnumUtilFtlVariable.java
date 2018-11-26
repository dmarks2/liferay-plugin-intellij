package de.dm.intellij.liferay.language.freemarker.enumutil;

import com.intellij.psi.PsiElement;
import de.dm.intellij.liferay.language.freemarker.AbstractTemplateNodeClassNameFtlVariable;
import org.jetbrains.annotations.NotNull;

public class EnumUtilFtlVariable extends AbstractTemplateNodeClassNameFtlVariable {

    public static final String VARIABLE_NAME = "enumUtil";

    public EnumUtilFtlVariable(@NotNull PsiElement parent) {
        super(VARIABLE_NAME, parent);
    }
}
