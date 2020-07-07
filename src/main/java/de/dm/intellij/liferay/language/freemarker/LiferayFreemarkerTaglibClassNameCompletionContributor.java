package de.dm.intellij.liferay.language.freemarker;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.freemarker.psi.directives.FtlMacro;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import de.dm.intellij.liferay.util.LiferayTaglibAttributes;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.Collection;

public class LiferayFreemarkerTaglibClassNameCompletionContributor extends CompletionContributor {

    private static final PsiElementPattern.Capture<PsiElement> ELEMENT_FILTER =
            LiferayFreemarkerUtil.getFtlStringLiteralFilter(
                    LiferayFreemarkerTaglibClassNameCompletionContributor::isClassNameAttribute
            );

    public static boolean isClassNameAttribute(PsiElement psiElement) {
        String attributeName = LiferayFreemarkerUtil.getAttributeName(psiElement);

        if (attributeName != null) {
            FtlMacro ftlMacro = PsiTreeUtil.getParentOfType(psiElement, FtlMacro.class);

            if (ftlMacro != null) {
                String namespace = LiferayFreemarkerTaglibs.getNamespace(ftlMacro);
                String localName = LiferayFreemarkerTaglibs.getLocalName(ftlMacro);

                if (LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_CLASS_NAME.containsKey(namespace)) {
                    Collection<AbstractMap.SimpleEntry<String, String>> taglibAttributes = LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_CLASS_NAME.get(namespace);
                    for (AbstractMap.SimpleEntry<String, String> pair : taglibAttributes) {
                        if (pair.getKey().equals(localName)) {
                            if (pair.getValue().equals(attributeName)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public LiferayFreemarkerTaglibClassNameCompletionContributor() {
        extend(
                CompletionType.BASIC,
                ELEMENT_FILTER,
                new CompletionProvider<CompletionParameters>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
                        PsiElement originalPosition = parameters.getOriginalPosition();
                        if (originalPosition != null) {
                            PsiFile psiFile = originalPosition.getContainingFile();
                            psiFile = psiFile.getOriginalFile();

                            Module module = ModuleUtil.findModuleForFile(psiFile);

                            if (isClassNameAttribute(originalPosition)) {
                                PsiClass objectClass = ProjectUtils.getClassByName(originalPosition.getProject(), "java.lang.Object", originalPosition);

                                LiferayFreemarkerUtil.addClassInheritorsLookup(objectClass, result, module);

                                result.stopHere();
                            }
                        }
                    }
                }
        );
    }

}
