package de.dm.intellij.liferay.language;

import com.intellij.lang.injection.MultiHostInjector;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.lang.javascript.JSTargetedInjector;
import com.intellij.lang.javascript.JavascriptLanguage;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlText;
import com.intellij.psi.xml.XmlToken;
import com.intellij.psi.xml.XmlTokenType;
import de.dm.intellij.liferay.util.LiferayTaglibs;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Injects JavaScript language into Liferay specific taglibs (like <aui:script> or <aui:a onClick="">)
 */
public class AlloyUIScriptLanguageInjector implements MultiHostInjector, JSTargetedInjector {

    public static Map<String, Collection<Pair<String, String>>> TAGLIB_ATTTRIBUTES = new HashMap<String, Collection<Pair<String, String>>>();

    //found by
    // * search for onClick, onXXX-Attributes in TLD

    // nothing found for: liferay-asset.tld, liferay-expando.tld, liferay-flags.tld, liferay-item-selector.tld,
    //                    liferay-journal.tld, liferay-layout.tld, liferay-map.tld, liferay-portlet.tld,
    //                    liferay-portlet-ext.tld, liferay-product-navigation.tld, liferay-security.tld,
    //                    liferay-site-navigation.tld, liferay-theme.tld, liferay-trash.tld, liferay-util.tld

    static {
        TAGLIB_ATTTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_AUI, Arrays.asList(
                new Pair<String, String>("script", ""),
                new Pair<String, String>("validator", ""),
                new Pair<String, String>("a", "onClick"),
                new Pair<String, String>("button", "onClick"),
                new Pair<String, String>("form", "onSubmit"),
                new Pair<String, String>("input", "onChange"),
                new Pair<String, String>("input", "onClick"),
                new Pair<String, String>("select", "onChange"),
                new Pair<String, String>("select", "onClick")
        ));

        TAGLIB_ATTTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_FRONTEND, Arrays.asList(
                new Pair<String, String>("edit-form", "onSubmit"),
                new Pair<String, String>("icon-vertical-card", "onClick"),
                new Pair<String, String>("vertical-card", "onClick")
        ));

        TAGLIB_ATTTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_UI, Arrays.asList(
                new Pair<String, String>("icon", "onClick"),
                new Pair<String, String>("input-checkbox", "onClick"),
                new Pair<String, String>("input-move-boxes", "leftOnChange"),
                new Pair<String, String>("input-move-boxes", "rightOnChange"),
                new Pair<String, String>("page-iterator", "jsCall"),
                new Pair<String, String>("quick-access-entry", "onClick"),
                new Pair<String, String>("tabs", "onClick")
        ));

        TAGLIB_ATTTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_AUI_OLD, TAGLIB_ATTTRIBUTES.get(LiferayTaglibs.TAGLIB_URI_AUI));
    }

    public void getLanguagesToInject(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement context) {
        if (! (context.isValid())) {
            return;
        }
        if (
                !(
                    (context instanceof XmlTag) ||
                    (context instanceof XmlAttribute)
                )
            ) {
            return;
        }

        XmlTag xmlTag;
        if (context instanceof XmlAttribute) {
            xmlTag = ((XmlAttribute)context).getParent();
        } else {
            xmlTag = (XmlTag) context;
        }

        if (TAGLIB_ATTTRIBUTES.containsKey(xmlTag.getNamespace())) {
            for (Pair<String, String> pair : TAGLIB_ATTTRIBUTES.get(xmlTag.getNamespace())) {
                if (pair.getKey().equals(xmlTag.getLocalName())) {
                    if (context instanceof XmlTag) {
                        injectIntoXmlTag(registrar, xmlTag);

                        break;
                    } else {
                        XmlAttribute xmlAttribute = (XmlAttribute)context;
                        if (pair.getValue().equals(xmlAttribute.getLocalName())) {
                            injectIntoXmlAttribute(registrar, xmlAttribute);

                            break;
                        }
                    }
                }
            }
        }
    }

    private void injectIntoXmlAttribute(MultiHostRegistrar registrar, XmlAttribute xmlAttribute) {
        if ( (xmlAttribute.getValue() != null) && (xmlAttribute.getValue().trim().length() > 0) ) {
            XmlAttributeValue valueElement = xmlAttribute.getValueElement();
            if (valueElement != null) {
                boolean needToInject = false;
                PsiElement[] myChildren = valueElement.getChildren();
                for (PsiElement child : myChildren) {
                    if (child instanceof XmlToken) {
                        //only inject if attribute contains regular content (e.g. not for JSP expressions inside the attribute value)
                        XmlToken xmlToken = (XmlToken)child;
                        IElementType tokenType = xmlToken.getTokenType();
                        if (XmlTokenType.XML_ATTRIBUTE_VALUE_TOKEN.equals(tokenType)) {
                            needToInject = true;
                            break;
                        }
                    }
                }
                if (needToInject) {
                    registrar.startInjecting(JavascriptLanguage.INSTANCE);
                    registrar.addPlace(null, null, (PsiLanguageInjectionHost) xmlAttribute.getValueElement(), xmlAttribute.getValueTextRange());
                    registrar.doneInjecting();
                }
            }
        }
    }

    private void injectIntoXmlTag(MultiHostRegistrar registrar, XmlTag xmlTag) {
        boolean needToInject = false;

        PsiElement[] myChildren = xmlTag.getChildren();
        for (PsiElement child : myChildren) {
            if (child instanceof XmlText) {
                //only inject if <aui:script> contains reqular content
                needToInject = true;
                break;
            }
        }

        if (needToInject) {
            registrar.startInjecting(JavascriptLanguage.INSTANCE);

            for (PsiElement child : myChildren) {
                if (child instanceof XmlText) {
                    //Inject language only for regular text inside <aui:script>. Other tags like <portlet:namespace> which can be present should not be treated as JavaScript
                    int length = child.getTextLength();
                    registrar.addPlace(null, null, (PsiLanguageInjectionHost) child, TextRange.create(0, length));
                }
            }

            registrar.doneInjecting();
        }
    }

    @NotNull
    public List<? extends Class<? extends PsiElement>> elementsToInjectIn() {
        return Arrays.asList(XmlTag.class, XmlAttribute.class);
    }
}
