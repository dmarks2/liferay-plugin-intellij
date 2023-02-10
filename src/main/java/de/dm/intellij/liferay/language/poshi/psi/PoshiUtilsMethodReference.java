package de.dm.intellij.liferay.language.poshi.psi;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiReferenceBase;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PoshiUtilsMethodReference extends PsiReferenceBase<PsiElement> {

    private final String poshiUtilsName;

    public PoshiUtilsMethodReference(@NotNull PsiElement element, TextRange textRange, String poshiUtilsName) {
        super(element, textRange);

        this.poshiUtilsName = poshiUtilsName;
    }

    @Override
    public @Nullable PsiElement resolve() {
        String valueString = getRangeInElement().substring(getElement().getText());

        PsiFile psiFile = PoshiUtilsReference.getPoshiUtilsClassFile(poshiUtilsName, getElement().getProject());

        if (psiFile instanceof PsiJavaFile) {
            PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;

            PsiClass[] classes = psiJavaFile.getClasses();

            if (classes.length > 0) {
                PsiClass poshiUtilsClass = classes[0];

                PsiMethod[] methods = poshiUtilsClass.getMethods();

                for (PsiMethod method : methods) {
                    if (method.hasModifierProperty(PsiModifier.STATIC) && method.hasModifierProperty(PsiModifier.PUBLIC)) {
                        if (valueString.equals(method.getName())) {
                            return method;
                        }
                    }
                }
            }
        }

        return null;
    }

    @Override
    public Object @NotNull [] getVariants() {
        List<Object> result = new ArrayList<Object>();

        PsiFile psiFile = PoshiUtilsReference.getPoshiUtilsClassFile(poshiUtilsName, getElement().getProject());

        if (psiFile instanceof PsiJavaFile) {
            PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;

            PsiClass[] classes = psiJavaFile.getClasses();

            if (classes.length > 0) {
                PsiClass poshiUtilsClass = classes[0];

                PsiMethod[] methods = poshiUtilsClass.getMethods();

                for (PsiMethod method : methods) {
                    if (method.hasModifierProperty(PsiModifier.STATIC) && method.hasModifierProperty(PsiModifier.PUBLIC)) {
                        result.add(LookupElementBuilder.create(method.getName()).withPsiElement(method).withIcon(Icons.LIFERAY_ICON));
                    }
                }
            }
        }

        return result.toArray(new Object[0]);
    }
}
