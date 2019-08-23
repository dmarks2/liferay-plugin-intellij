package de.dm.intellij.liferay.language.jsp;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.util.XmlUtil;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

public abstract class AbstractLiferayTaglibReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        XmlUtil.registerXmlAttributeValueReferenceProvider(
            registrar,
            getAttributeNames(),
            new LiferayTaglibFilter(),
            false,
            getReferenceProvider()
        );
    }

    protected abstract PsiReferenceProvider getReferenceProvider();

    protected abstract String[] getAttributeNames();

    protected boolean isSuitableAttribute(XmlAttribute xmlAttribute) {
        XmlTag xmlTag = xmlAttribute.getParent();

        if (xmlTag != null) {
            String namespace = xmlTag.getNamespace();
            String localName = xmlTag.getLocalName();
            String attributeName = xmlAttribute.getLocalName();

            Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> taglibMap = getTaglibMap();

            if (taglibMap.containsKey(namespace)) {
                Collection<AbstractMap.SimpleEntry<String, String>> entries = taglibMap.get(namespace);

                Stream<AbstractMap.SimpleEntry<String, String>> entriesStream = entries.stream();

                return entriesStream.anyMatch(
                        entry -> {
                            String key = entry.getKey();
                            String value = entry.getValue();

                            return key.equals(localName) && value.equals(attributeName);

                        }
                );
            }
        }
        return false;
    }

    protected abstract Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> getTaglibMap();

    private class LiferayTaglibFilter implements ElementFilter {
        public boolean isAcceptable(Object element, PsiElement context) {
            PsiElement psiElement = (PsiElement)element;

            PsiElement parent = psiElement.getParent();
            if (parent instanceof XmlAttribute) {
                XmlAttribute xmlAttribute = (XmlAttribute)parent;

                return AbstractLiferayTaglibReferenceContributor.this.isSuitableAttribute(xmlAttribute);
            }

            return false;
        }

        public boolean isClassAcceptable(Class hintClass) {
            return true;
        }
    }
}
