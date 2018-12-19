package de.dm.intellij.liferay.language.jsp;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.JavaClassReferenceProvider;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlToken;
import com.intellij.psi.xml.XmlTokenType;
import de.dm.intellij.liferay.util.LiferayTaglibAttributes;
import javafx.util.Pair;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

public class LiferayTaglibClassNameReferenceContributor extends AbstractLiferayTaglibReferenceContributor {

    @Override
    protected PsiReferenceProvider getReferenceProvider() {
        JavaClassReferenceProvider provider = new JavaClassReferenceProvider();

        provider.setOption(JavaClassReferenceProvider.ADVANCED_RESOLVE, Boolean.TRUE);
        provider.setOption(JavaClassReferenceProvider.RESOLVE_QUALIFIED_CLASS_NAME, Boolean.TRUE);

        return provider;
    }

    @Override
    protected String[] getAttributeNames() {
        return new String[] {
            "assetCategoryClassName",
            "assetTagClassName",
            "className",
            "exception",
            "model",
            "modelResource",
            "portletProviderClassName"
        };
    }

    @Override
    protected boolean isSuitableAttribute(XmlAttribute xmlAttribute) {
        if (containsTextOnly(xmlAttribute)) {
            XmlTag xmlTag = xmlAttribute.getParent();
            if (xmlTag != null) {
                if (LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_CLASS_NAME.containsKey(xmlTag.getNamespace())) {
                    Collection<Pair<String, String>> entries = LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_CLASS_NAME.get(xmlTag.getNamespace());

                    Stream<Pair<String, String>> entriesStream = entries.stream();

                    return entriesStream.anyMatch(
                        entry -> {
                            String key = entry.getKey();
                            String value = entry.getValue();

                            return key.equals(xmlTag.getLocalName()) && value.equals(xmlAttribute.getLocalName());

                        }
                    );
                }
            }
        }
        return false;

    }

    private boolean containsTextOnly(XmlAttribute xmlAttribute) {
        return Stream.of(xmlAttribute)
            .map(XmlAttribute::getValueElement)
            .filter(Objects::nonNull)
            .map(PsiElement::getChildren)
            .flatMap(Stream::of)
            .filter(child -> child instanceof XmlToken)
            .map(xmlToken -> (XmlToken)xmlToken)
            .map(XmlToken::getTokenType)
            .anyMatch(XmlTokenType.XML_ATTRIBUTE_VALUE_TOKEN::equals);
    }

}
