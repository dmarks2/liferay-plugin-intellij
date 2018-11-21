package de.dm.intellij.liferay.language.jsp;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.JavaClassReferenceProvider;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlToken;
import com.intellij.psi.xml.XmlTokenType;
import de.dm.intellij.liferay.util.LiferayTaglibAttributes;
import javafx.util.Pair;

import java.util.Collection;

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
                    for (Pair<String, String> entry : entries) {
                        if (
                                (entry.getKey().equals(xmlTag.getLocalName())) &&
                                        (entry.getValue().equals(xmlAttribute.getLocalName()))
                        ) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;

    }

    private boolean containsTextOnly(XmlAttribute xmlAttribute) {
        if ( (xmlAttribute.getValue() != null) && (xmlAttribute.getValue().trim().length() > 0) ) {
            XmlAttributeValue valueElement = xmlAttribute.getValueElement();
            if (valueElement != null) {
                PsiElement[] myChildren = valueElement.getChildren();
                for (PsiElement child : myChildren) {
                    if (child instanceof XmlToken) {
                        //only inject if attribute contains regular content (e.g. not for JSP expressions inside the attribute value)
                        XmlToken xmlToken = (XmlToken) child;
                        IElementType tokenType = xmlToken.getTokenType();
                        if (XmlTokenType.XML_ATTRIBUTE_VALUE_TOKEN.equals(tokenType)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
