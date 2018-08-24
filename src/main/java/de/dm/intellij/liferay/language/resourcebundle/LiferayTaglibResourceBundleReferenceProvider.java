package de.dm.intellij.liferay.language.resourcebundle;

import com.intellij.lang.properties.PropertiesReferenceProvider;
import com.intellij.lang.properties.references.PropertyReference;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class LiferayTaglibResourceBundleReferenceProvider extends PropertiesReferenceProvider {

    public LiferayTaglibResourceBundleReferenceProvider() {
        super();
    }

    public LiferayTaglibResourceBundleReferenceProvider(boolean defaultSoft) {
        super(defaultSoft);
    }

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        PsiReference[] referencesByElement = super.getReferencesByElement(element, context);
        if (referencesByElement.length == 1) {
            PsiReference psiReference = referencesByElement[0];
            if (psiReference instanceof PropertyReference) {
                PropertyReference propertyReference = (PropertyReference)psiReference;

                return new PsiReference[]{
                    new LiferayTaglibResourceBundlePropertyReference(
                        propertyReference.getCanonicalText(),
                        propertyReference.getElement(),
                        null,
                        propertyReference.isSoft()
                    )
                };
            }
        }
        return referencesByElement;
    }
}
