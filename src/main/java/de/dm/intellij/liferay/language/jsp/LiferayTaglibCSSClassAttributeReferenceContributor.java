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
import de.dm.intellij.liferay.util.LiferayTaglibs;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * Adds support for attributes like &quot;cssClass&quot; and &quot;iconCssClass&quot; on Liferay and AlloyUI Tags, so that Code Completion for (S)CSS classes is available.
 */
public class LiferayTaglibCSSClassAttributeReferenceContributor extends PsiReferenceContributor {

    private static final String CSS_CLASS = "cssClass";
    private static final String ICON_CSS_CLASS = "iconCssClass";
    private static final String IMAGE_CSS_CLASS = "imageCssClass";
    private static final String LINK_CSS_CLASS = "linkCssClass";
    private static final String TRIGGER_CSS_CLASS = "triggerCssClass";
    private static final String EMPTY_RESULTS_MESSAGE_CSS_CLASS = "emptyResultsMessageCssClass";
    private static final String ELEMENT_CLASSES = "elementClasses";
    private static final String DRAGGABLE_IMAGE = "draggableImage";
    private static final String USER_ICON_CSS_CLASS = "userIconCssClass";

    private static final List<String> TAGLIB_NAMESPACES = Arrays.asList(
            LiferayTaglibs.TAGLIB_URI_AUI,
            LiferayTaglibs.TAGLIB_URI_AUI_OLD,
            LiferayTaglibs.TAGLIB_URI_LIFERAY_UI,
            LiferayTaglibs.TAGLIB_URI_LIFERAY_FLAGS,
            LiferayTaglibs.TAGLIB_URI_LIFERAY_FRONTEND,
            LiferayTaglibs.TAGLIB_URI_LIFERAY_ITEM_SELECTOR
    );

    private static final List<String> ATTRIBUTE_NAMES = Arrays.asList(
            CSS_CLASS,
            ICON_CSS_CLASS,
            IMAGE_CSS_CLASS,
            LINK_CSS_CLASS,
            TRIGGER_CSS_CLASS,
            ELEMENT_CLASSES,
            DRAGGABLE_IMAGE,
            EMPTY_RESULTS_MESSAGE_CSS_CLASS,
            USER_ICON_CSS_CLASS
    );


    public LiferayTaglibCSSClassAttributeReferenceContributor() {
    }

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
                XmlTag parent = xmlAttribute.getParent();
                if (TAGLIB_NAMESPACES.contains(parent.getNamespace())) {
                    return ATTRIBUTE_NAMES.contains(attrName);
                }
                return false;
            }
        };
        XmlUtil.registerXmlAttributeValueReferenceProvider(registrar, ATTRIBUTE_NAMES.toArray(new String[ATTRIBUTE_NAMES.size()]), htmlClassOrIdReferenceProvider.getFilter(), false, htmlClassOrIdReferenceProvider);
    }

}