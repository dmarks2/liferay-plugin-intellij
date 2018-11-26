package de.dm.intellij.liferay.language.freemarker.staticutil;

import com.intellij.psi.PsiElement;
import de.dm.intellij.liferay.language.freemarker.AbstractTemplateNodeClassNameFtlVariable;
import org.jetbrains.annotations.NotNull;

public class StaticUtilFtlVariable extends AbstractTemplateNodeClassNameFtlVariable {

    public static final String VARIABLE_NAME = "staticUtil";

    public StaticUtilFtlVariable(@NotNull PsiElement parent) {
        super(VARIABLE_NAME, parent);
    }
}
