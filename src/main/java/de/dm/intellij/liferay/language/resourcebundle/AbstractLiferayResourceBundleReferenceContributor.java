package de.dm.intellij.liferay.language.resourcebundle;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.filters.ElementFilter;
import de.dm.intellij.liferay.util.LiferayTaglibAttributes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractLiferayResourceBundleReferenceContributor<TagType extends PsiElement, AttributeType extends PsiElement> extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registerReferenceProvider(
            registrar,
            getAttributeNames(),
            new LiferayTaglibFilter(),
            new LiferayTaglibResourceBundleReferenceProvider(true)  //do not show as error if no valid language reference is used
        );
    }

    protected abstract void registerReferenceProvider(PsiReferenceRegistrar registrar, String[] attributeNames, ElementFilter elementFilter, PsiReferenceProvider psiReferenceProvider);

    protected String[] getAttributeNames() {
        Set<String> attributeNames = new HashSet<String>();
        for (Map.Entry<String, Collection<AbstractMap.SimpleEntry<String, String>>> taglib : LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.entrySet()) {
            for (AbstractMap.SimpleEntry<String, String> entry : taglib.getValue()) {
                attributeNames.add(entry.getValue());
            }
        }

        return attributeNames.toArray(new String[attributeNames.size()]);
    }

    @Nullable
    protected abstract TagType getTag(@NotNull PsiElement psiElement);

    @Nullable
    protected abstract AttributeType getAttribute(@NotNull PsiElement psiElement);

    protected abstract String getNamespace(TagType tag);

    protected abstract String getLocalName(TagType tag);

    protected abstract String getAttributeName(AttributeType attribute);

    class LiferayTaglibFilter implements ElementFilter {
        public boolean isAcceptable(Object element, PsiElement context) {
            PsiElement psiElement = (PsiElement)element;
            AttributeType attribute = getAttribute(psiElement);
            if (attribute != null) {
                TagType tag = getTag(psiElement);

                if (tag != null) {
                    String namespace = getNamespace(tag);
                    String localName = getLocalName(tag);
                    String attributeName = getAttributeName(attribute);

                    if (LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.containsKey(namespace)) {
                        Collection<AbstractMap.SimpleEntry<String, String>> entries = LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.get(namespace);

                        for (AbstractMap.SimpleEntry<String, String> entry : entries) {
                            if (
                                    (entry.getKey().equals(localName)) &&
                                    (entry.getValue().equals(attributeName))
                            ) {
                                return true;
                            }
                        }
                    }
                }
            }

            return false;
        }

        public boolean isClassAcceptable(Class hintClass) {
            return true;
        }
    }


}
