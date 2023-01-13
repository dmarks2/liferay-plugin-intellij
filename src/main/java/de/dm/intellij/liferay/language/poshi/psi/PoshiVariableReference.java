package de.dm.intellij.liferay.language.poshi.psi;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.util.PsiTreeUtil;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PoshiVariableReference extends PsiReferenceBase<PsiElement> {

    private final String variableName;

    public PoshiVariableReference(@NotNull PsiElement element, String variableName, TextRange textRange) {
        super(element, textRange);

        this.variableName = variableName;
    }

    @Override
    public @Nullable PsiElement resolve() {
        Collection<PoshiVariable> variables = PsiTreeUtil.findChildrenOfType(getElement().getContainingFile(), PoshiVariable.class);

        //TODO only previous siblings or up the hierarchy...

        return variables.stream().filter(variable -> (this.variableName.equals(variable.getVariableAssignment().getName()))).findFirst().orElse(null);
    }

    @Override
    public Object @NotNull [] getVariants() {
        List<Object> result = new ArrayList<Object>();

        Collection<PoshiVariable> variables = PsiTreeUtil.findChildrenOfType(getElement().getContainingFile(), PoshiVariable.class);

        variables.forEach(variable -> result.add(LookupElementBuilder.create(variable.getVariableAssignment().getName()).withPsiElement(variable).withIcon(Icons.LIFERAY_ICON)));

        return result.toArray(new Object[0]);
    }
}