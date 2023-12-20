package de.dm.intellij.liferay.language;

import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.ElementManipulators;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.filters.position.FilterPattern;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.util.ProcessingContext;
import de.dm.intellij.liferay.theme.LiferayThemeTemplateVariables;
import de.dm.intellij.liferay.util.ThemeSettingsPathFileReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Adds file references for known template variables (Freemarker / Velocity) like ${images_folder}
 */
public class TemplateVariableReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(
                PlatformPatterns.psiElement(PsiElement.class).and(new FilterPattern(new ElementFilter() {
                    @Override
                    public boolean isAcceptable(Object element, @Nullable PsiElement context) {
                        return
                                (
                                        (element instanceof PsiElement) &&
                                        (((PsiElement) element).getParent() instanceof XmlAttribute)
                                );
                    }

                    @Override
                    public boolean isClassAcceptable(Class hintClass) {
                        return true;
                    }
                })),
                new PsiReferenceProvider() {

                    @Override
                    public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                        String text = StringUtil.unquoteString(element.getText());
                        for (Map.Entry<String, String> entry : LiferayThemeTemplateVariables.THEME_TEMPLATE_VARIABLE_DIRECTORY_REFERENCES.entrySet()) {
                            final String variableText = "${" + entry.getKey() + "}";
                            final String themeSetting = entry.getValue();

                            if (text.contains(variableText)) {
                                final TextRange textRange = ElementManipulators.getValueTextRange(element);
                                FileReferenceSet set = new FileReferenceSet(text, element, textRange.getStartOffset(), null, true) {
                                    @Override
                                    public FileReference createFileReference(TextRange range, int index, String text) {
                                        return new ThemeSettingsPathFileReference(this, range, index, text, variableText, themeSetting);
                                    }
                                };

                                return set.getAllReferences();

                            }

                        }
                        return new PsiReference[0];
                    }
                }
        );

    }
}
