package de.dm.intellij.liferay.language.jsp;

import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.lang.javascript.JavascriptLanguage;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.Trinity;
import com.intellij.psi.ElementManipulators;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlText;
import com.intellij.psi.xml.XmlToken;
import com.intellij.psi.xml.XmlTokenType;
import de.dm.intellij.liferay.language.javascript.AbstractLiferayJavascriptLanguageInjector;
import de.dm.intellij.liferay.util.LiferayTaglibs;
import org.intellij.plugins.intelliLang.inject.InjectedLanguage;
import org.intellij.plugins.intelliLang.inject.InjectorUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Injects JavaScript language into Liferay specific taglibs (like <aui:script> or <aui:a onClick="">)
 */
public class LiferayTaglibJavascriptLanguageInjector extends AbstractLiferayJavascriptLanguageInjector<XmlTag, XmlAttribute> {

    @Nullable
    @Override
    protected XmlTag getTag(@NotNull PsiElement psiElement) {
        XmlTag xmlTag;
        if (psiElement instanceof XmlAttribute) {
            xmlTag = ((XmlAttribute)psiElement).getParent();
        } else {
            xmlTag = (XmlTag) psiElement;
        }
        return xmlTag;
    }

    @Override
    protected String getNamespace(XmlTag tag) {
        return tag.getNamespace();
    }

    @Override
    protected String getLocalName(XmlTag tag) {
        return tag.getLocalName();
    }

    @Override
    protected String getAttributeName(XmlAttribute attribute) {
        return attribute.getLocalName();
    }

    @Override
    protected boolean isContextSuitableForBodyInjection(PsiElement context) {
        return context instanceof XmlTag;
    }

    @NotNull
    @Override
    protected XmlAttribute[] getAttributes(@NotNull PsiElement psiElement) {
        if (psiElement instanceof XmlAttribute) {
            return new XmlAttribute[]{(XmlAttribute)psiElement};
        }
        return new XmlAttribute[0];
    }

    @NotNull
    public List<? extends Class<? extends PsiElement>> elementsToInjectIn() {
        return Arrays.asList(XmlTag.class, XmlAttribute.class);
    }

    @Override
    protected void injectIntoAttribute(@NotNull MultiHostRegistrar registrar, XmlAttribute xmlAttribute) {
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

    @Override
    protected void injectIntoBody(@NotNull MultiHostRegistrar registrar, XmlTag xmlTag) {
        InjectedLanguage javascriptLanguage = InjectedLanguage.create(JavascriptLanguage.INSTANCE.getID(), "", "", true);

        //special handling for aui:validator
        String namespace = xmlTag.getNamespace();
        String localName = xmlTag.getLocalName();
        if (LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI.equals(namespace)) {
            if ("validator".equals(localName)) {
                String validatorName = xmlTag.getAttributeValue("name");
                if (validatorName != null) {
                    if ("custom".equals(validatorName)) {
                        //special handling for aui:validator
                        javascriptLanguage = InjectedLanguage.create(JavascriptLanguage.INSTANCE.getID(), "var a = ", "", true);
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
        }

        List<Trinity<PsiLanguageInjectionHost, InjectedLanguage, TextRange>> list = new ArrayList<Trinity<PsiLanguageInjectionHost, InjectedLanguage, TextRange>>();

        PsiElement[] myChildren = xmlTag.getChildren();
        for (PsiElement child : myChildren) {
            if (child instanceof XmlText) {
                //only inject if <aui:script> contains reqular content
                list.add(
                    Trinity.create(
                        ((PsiLanguageInjectionHost)child),
                        javascriptLanguage,
                        ElementManipulators.getManipulator(child).getRangeInElement(child)
                    )
                );
            }
        }
        if (!list.isEmpty()) {
            InjectorUtils.registerInjection(
                JavascriptLanguage.INSTANCE,
                list,
                xmlTag.getContainingFile(),
                registrar
            );
        }
    }

}
