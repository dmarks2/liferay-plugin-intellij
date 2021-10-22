package de.dm.intellij.liferay.resourcesimporter;

import com.intellij.psi.PsiElement;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.filters.position.FilterPattern;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlElement;
import com.intellij.psi.xml.XmlText;
import org.jetbrains.annotations.Nullable;

public class LiferayResourcesImporterPlaceholderReferenceFilterPattern extends FilterPattern {

    public LiferayResourcesImporterPlaceholderReferenceFilterPattern() {
        super(new ElementFilter() {
            @Override
            public boolean isAcceptable(Object element, @Nullable PsiElement context) {
                if (element instanceof XmlElement) {
                    return PsiTreeUtil.getParentOfType((PsiElement) element, XmlText.class) != null;
                }

                return false;
            }

            @Override
            public boolean isClassAcceptable(Class hintClass) {
                return true;
            }
        });
    }
}
