package de.dm.intellij.liferay.language.jsp;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.JavaClassReferenceProvider;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlToken;
import com.intellij.psi.xml.XmlTokenType;
import de.dm.intellij.liferay.util.LiferayTaglibAttributes;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class LiferayTaglibClassNameReferenceContributor extends AbstractLiferayTaglibReferenceContributor {

    @Override
    protected PsiReferenceProvider getReferenceProvider() {
        JavaClassReferenceProvider provider = new JavaClassReferenceProvider();

        provider.setOption(JavaClassReferenceProvider.ADVANCED_RESOLVE, Boolean.TRUE);
        provider.setOption(JavaClassReferenceProvider.RESOLVE_QUALIFIED_CLASS_NAME, Boolean.TRUE);
        provider.setOption(JavaClassReferenceProvider.ALLOW_DOLLAR_NAMES, Boolean.FALSE);

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
            return super.isSuitableAttribute(xmlAttribute);
        }
        return false;

    }

    @Override
    protected Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> getTaglibMap() {
        return LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_CLASS_NAME;
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
