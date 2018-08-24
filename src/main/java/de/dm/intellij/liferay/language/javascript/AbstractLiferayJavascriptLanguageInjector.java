package de.dm.intellij.liferay.language.javascript;

import com.intellij.lang.injection.MultiHostInjector;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.lang.javascript.JSTargetedInjector;
import com.intellij.psi.PsiElement;
import de.dm.intellij.liferay.util.LiferayTaglibAttributes;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public abstract class AbstractLiferayJavascriptLanguageInjector<TagType extends PsiElement, AttributeType extends PsiElement> implements MultiHostInjector, JSTargetedInjector {

    public void getLanguagesToInject(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement context) {
        if (! (context.isValid())) {
            return;
        }

        TagType tag = getTag(context);

        if (tag != null) {

            String namespace = getNamespace(tag);
            String localName = getLocalName(tag);

            if (LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_JAVASCRIPT.containsKey(namespace)) {
                Collection<Pair<String, String>> taglibAttributes = LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_JAVASCRIPT.get(namespace);

                for (Pair<String, String> pair : taglibAttributes) {
                    if (pair.getKey().equals(localName)) {
                        if ("".equals(pair.getValue())) {
                            if (isContextSuitableForBodyInjection(context)) {
                                injectIntoBody(registrar, tag);
                            }
                        } else {
                            AttributeType[] attributes = getAttributes(context);
                            for (AttributeType attribute : attributes) {
                                String attributeName = getAttributeName(attribute);
                                if (pair.getValue().equals(attributeName)) {
                                    injectIntoAttribute(registrar, attribute);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    protected abstract void injectIntoAttribute(@NotNull MultiHostRegistrar registrar, AttributeType attribute);

    protected abstract void injectIntoBody(@NotNull MultiHostRegistrar registrar, TagType tag);

    @Nullable
    protected abstract TagType getTag(@NotNull PsiElement psiElement);

    protected abstract String getNamespace(TagType tag);

    protected abstract String getLocalName(TagType tag);

    protected abstract String getAttributeName(AttributeType attribute);

    protected abstract boolean isContextSuitableForBodyInjection(PsiElement context);

    @NotNull
    protected abstract AttributeType[] getAttributes(@NotNull PsiElement psiElement);

}
