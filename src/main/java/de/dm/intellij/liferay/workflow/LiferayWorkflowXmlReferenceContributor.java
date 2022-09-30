package de.dm.intellij.liferay.workflow;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.filters.position.FilterPattern;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlText;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LiferayWorkflowXmlReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(
                PlatformPatterns.psiElement(PsiElement.class).and(new FilterPattern(new ElementFilter() {
                    @Override
                    public boolean isAcceptable(Object element, @Nullable PsiElement context) {
                        if (element instanceof PsiElement) {
                            PsiElement psiElement = (PsiElement) element;

                            if (psiElement.getParent() instanceof XmlText) {
                                XmlText xmlText = (XmlText) psiElement.getParent();

                                XmlTag xmlTag = xmlText.getParentTag();

                                if (xmlTag != null) {
                                    if (xmlTag.getLocalName().equals("target")) {
                                        if (LiferayWorkflowContextVariablesUtil.WORKFLOW_NAMESPACES.contains(xmlTag.getNamespace())) {
                                            return true;
                                        }
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
                })),
                new PsiReferenceProvider() {
                    @Override
                    public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                        return new PsiReference[] {
                                new LiferayWorkflowXmlTargetReference(element)
                        };

                    }
                }
        );
    }
}
