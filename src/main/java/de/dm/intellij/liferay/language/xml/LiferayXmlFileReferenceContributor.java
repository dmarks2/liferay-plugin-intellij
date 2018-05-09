package de.dm.intellij.liferay.language.xml;

import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.SoftFileReferenceSet;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class LiferayXmlFileReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(
                PlatformPatterns.psiElement(PsiElement.class).and(new LiferayXmlFileReferenceFilterPattern()),
                new PsiReferenceProvider() {

                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                        TextRange originalRange = element.getTextRange();
                        String valueString;
                        int startInElement;
                        if (element instanceof XmlAttributeValue) {
                            TextRange valueRange = TextRange.create(1, originalRange.getLength() - 1);
                            valueString = valueRange.substring(element.getText());
                            startInElement = 1;
                        } else {
                            TextRange valueRange = TextRange.create(0, originalRange.getLength());
                            valueString = valueRange.substring(element.getText());
                            startInElement = 0;
                        }

                        FileReferenceSet fileReferenceSet = new SoftFileReferenceSet(valueString, element, startInElement, null, SystemInfo.isFileSystemCaseSensitive, false);

                        return fileReferenceSet.getAllReferences();
                    }
                }
        );
    }
}
