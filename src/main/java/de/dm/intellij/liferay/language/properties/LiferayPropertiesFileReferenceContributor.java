package de.dm.intellij.liferay.language.properties;

import com.intellij.lang.properties.psi.impl.PropertyValueImpl;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.SoftFileReferenceSet;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class LiferayPropertiesFileReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(PsiElement.class).and(new LiferayPropertiesFileReferenceFilter()),
            new PsiReferenceProvider() {

                @NotNull
                @Override
                public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                    PropertyValueImpl propertyValue = (PropertyValueImpl)element;

                    String text = propertyValue.getText();

                    FileReferenceSet fileReferenceSet = new SoftFileReferenceSet(text, element, 0, null, SystemInfo.isFileSystemCaseSensitive, false);

                    return fileReferenceSet.getAllReferences();
                }
            }
        );
    }

}
