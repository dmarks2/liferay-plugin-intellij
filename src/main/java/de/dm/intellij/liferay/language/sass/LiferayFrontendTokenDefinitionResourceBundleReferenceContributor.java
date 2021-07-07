package de.dm.intellij.liferay.language.sass;

import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonValue;
import com.intellij.json.psi.impl.JsonPsiImplUtils;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.filters.position.FilterPattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LiferayFrontendTokenDefinitionResourceBundleReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(
                PlatformPatterns.psiElement(PsiElement.class).and(new FilterPattern(new ElementFilter() {
                            @Override
                            public boolean isAcceptable(Object element, @Nullable PsiElement context) {
                                if (element instanceof PsiElement) {
                                    PsiElement psiElement = (PsiElement) element;

                                    PsiFile containingFile = psiElement.getContainingFile();

                                    containingFile = containingFile.getOriginalFile();

                                    if ("frontend-token-definition.json".equals(containingFile.getName())) {
                                        if (psiElement instanceof JsonValue) {
                                            JsonValue jsonValue = (JsonValue) psiElement;

                                            JsonProperty jsonProperty = (JsonProperty) jsonValue.getParent();

                                            String name = JsonPsiImplUtils.getName(jsonProperty);

                                            if ("label".equals(StringUtil.unquoteString(name))) {
                                                return true;
                                            }
                                        }
                                    }
                                }

                                return false;
                            }

                            @Override
                            public boolean isClassAcceptable(Class hintClass) {
                                return true;
                            }
                        })
                ),
                new LiferayFrontendTokenDefinitionResourceBundleReferenceProvider()
        );
    }
}
