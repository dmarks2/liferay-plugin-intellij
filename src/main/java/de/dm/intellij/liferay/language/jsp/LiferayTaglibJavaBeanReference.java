package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.Iconable;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.PsiSubstitutor;
import com.intellij.psi.PsiType;
import com.intellij.psi.util.PropertyUtil;
import com.intellij.psi.util.PsiFormatUtil;
import com.intellij.psi.util.PsiFormatUtilBase;
import com.intellij.psi.xml.XmlAttributeValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LiferayTaglibJavaBeanReference extends PsiReferenceBase<XmlAttributeValue> {

    private PsiClass targetClass;

    public LiferayTaglibJavaBeanReference(XmlAttributeValue xmlAttributeValue, TextRange rangeInElement, PsiClass targetClass) {
        super(xmlAttributeValue, rangeInElement, true);
        this.targetClass = targetClass;
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        return PropertyUtil.findPropertyGetter(targetClass, getValue(), false, true);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        List<Object> result = new ArrayList<Object>();

        Map<String, PsiMethod> allProperties = PropertyUtil.getAllProperties(targetClass, false, true, true);
        for (PsiMethod psiMethod : allProperties.values()) {
            String name = PropertyUtil.getPropertyNameByGetter(psiMethod);
            PsiType returnType = psiMethod.getReturnType();

            result.add(
                LookupElementBuilder.create(name).
                        withIcon(psiMethod.getIcon(Iconable.ICON_FLAG_VISIBILITY))
                        .withTailText(" (" + PsiFormatUtil.formatMethod(psiMethod, PsiSubstitutor.EMPTY, PsiFormatUtilBase.SHOW_CONTAINING_CLASS | PsiFormatUtilBase.SHOW_FQ_NAME | PsiFormatUtilBase.SHOW_NAME | PsiFormatUtilBase.SHOW_PARAMETERS, 0) + ")", true)
                        .withTypeText(PsiSubstitutor.EMPTY.substitute(returnType).getPresentableText())
            );
        }

        return result.toArray(new Object[0]);
    }
}