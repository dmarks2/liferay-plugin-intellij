package de.dm.intellij.liferay.language.fragment;

import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonValue;
import com.intellij.json.psi.impl.JsonPsiImplUtils;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.filters.position.FilterPattern;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.SoftFileReferenceSet;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;

public class FragmentFragmentJsonFileReferenceContributor extends PsiReferenceContributor {

    private static Collection<String> FILE_REFERENCE_PROPERTIES = Arrays.asList(
            "cssPath",
            "configurationPath",
            "htmlPath",
            "jsPath",
            "thumbnailPath"
    );

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(
                PlatformPatterns.psiElement(PsiElement.class).and(new FragmentJsonFileReferenceFilter()),
                new PsiReferenceProvider() {

                    @NotNull
                    @Override
                    public PsiReference  @NotNull [] getReferencesByElement(@NotNull PsiElement psiElement, @NotNull ProcessingContext context) {
                        JsonValue jsonValue = (JsonValue)psiElement;

                        TextRange originalRange = psiElement.getTextRange();

                        TextRange valueRange = TextRange.create(1, originalRange.getLength() - 1);
                        String valueString = valueRange.substring(jsonValue.getText());

                        FileReferenceSet fileReferenceSet = new SoftFileReferenceSet(valueString, psiElement, 1, null, SystemInfo.isFileSystemCaseSensitive, false);

                        return fileReferenceSet.getAllReferences();
                    }
                }
        );
    }

    private static boolean isFragmentJsonFile(PsiElement psiElement) {
        PsiFile file = psiElement.getContainingFile();

        return Comparing.equal(file.getName(), "fragment.json", SystemInfo.isFileSystemCaseSensitive);
    }

    private static class FragmentJsonFileReferenceFilter extends FilterPattern {

        public FragmentJsonFileReferenceFilter() {
            super(new ElementFilter() {

                @Override
                public boolean isAcceptable(Object element, @Nullable PsiElement context) {
                    if (element instanceof PsiElement) {
                        PsiElement psiElement = (PsiElement) element;

                        if (isFragmentJsonFile(psiElement)) {
                            if (psiElement instanceof JsonValue) {
                                JsonValue jsonValue = (JsonValue)psiElement;

                                JsonProperty jsonProperty = (JsonProperty) jsonValue.getParent();

                                String name = JsonPsiImplUtils.getName(jsonProperty);

                                return FILE_REFERENCE_PROPERTIES.contains(StringUtil.unquoteString(name));
                            }
                        }
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
}
