package de.dm.intellij.liferay.language.jsp;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.css.CssSupportLoader;
import com.intellij.psi.css.impl.util.CssInHtmlClassOrIdReferenceProvider;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.util.XmlUtil;
import de.dm.intellij.liferay.util.LiferayTaglibAttributes;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Adds support for attributes like &quot;cssClass&quot; and &quot;iconCssClass&quot; on Liferay and AlloyUI Tags, so that Code Completion for (S)CSS classes is available.
 */
public class LiferayTaglibCSSClassAttributeReferenceContributor extends PsiReferenceContributor {

    private static final List<String> ATTRIBUTE_NAMES = Arrays.asList(
            "anchorCssClass",
            "bodyClasses",
            "buttonCssClass",
            "cardCssClass",
            "checkboxCSSClass",
            "class",
            "containerCssClass",
            "containerWrapperCssClass",
            "contentCssClasses",
            "cssClass",
            "cssClasses",
            "descriptionCSSClass",
            "draggableImage",
            "elementClasses",
            "emptyResultsMessageCssClass",
            "fieldSetCssClass",
            "headerClasses",
            "helpTextCssClass",
            "icon",
            "iconCssClass",
            "imageCssClass",
            "imageCSSClass",
            "labelCSSClass",
            "linkCssClass",
            "linkClass",
            "menubarCssClass",
            "navCssClass",
            "searchResultCssClass",
            "stickerCssClass",
            "symbol",
            "tableClasses",
            "triggerCssClass",
            "userColorClass",
            "userIconCssClass",
            "wrapperCssClass",
            "wrapperCssClasses"
    );

    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        CssInHtmlClassOrIdReferenceProvider htmlClassOrIdReferenceProvider = new CssInHtmlClassOrIdReferenceProvider() {

            @Override
            public ElementFilter getFilter() {
                return new ElementFilter() {
                    public boolean isAcceptable(Object element, PsiElement context) {
                        PsiElement psiElement = (PsiElement)element;
                        if (CssSupportLoader.isInFileThatSupportsCssResolve(psiElement)) {
                            PsiElement parent = psiElement.getParent();
                            if (parent instanceof XmlAttribute) {
                                XmlAttribute xmlAttribute = (XmlAttribute)parent;
                                String attrName = xmlAttribute.getName();
                                if (isSuitableAttribute(attrName, xmlAttribute)) {
                                    return true;
                                }
                            }
                        }

                        return false;
                    }

                    public boolean isClassAcceptable(Class hintClass) {
                        return true;
                    }
                };
            }

            protected boolean isSuitableAttribute(String attrName, XmlAttribute xmlAttribute) {
                XmlTag xmlTag = xmlAttribute.getParent();
                if (xmlTag != null) {
                    if (LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_CSS.containsKey(xmlTag.getNamespace())) {
                        Collection<AbstractMap.SimpleEntry<String, String>> entries = LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_CSS.get(xmlTag.getNamespace());
                        for (AbstractMap.SimpleEntry<String, String> entry : entries) {
                            if (
                                (entry.getKey().equals(xmlTag.getLocalName())) &&
                                (entry.getValue().equals(xmlAttribute.getLocalName()))
                            ) {
                                return true;
                            }
                        }
                    }

                }
                return false;
            }
        };
        XmlUtil.registerXmlAttributeValueReferenceProvider(registrar, ATTRIBUTE_NAMES.toArray(new String[ATTRIBUTE_NAMES.size()]), htmlClassOrIdReferenceProvider.getFilter(), false, htmlClassOrIdReferenceProvider);
    }

}