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
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Adds support for attributes like &quot;cssClass&quot; and &quot;iconCssClass&quot; on Liferay and AlloyUI Tags, so that Code Completion for (S)CSS classes is available.
 */
public class LiferayTaglibCSSClassAttributeReferenceContributor extends PsiReferenceContributor {

    private static Map<String, Collection<Pair<String, String>>> TAGLIB_ATTRIBUTES = new HashMap<String, Collection<Pair<String, String>>>();

    static {
        TAGLIB_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_AUI, Arrays.asList(
                new Pair<String, String>("a", "cssClass"),
                new Pair<String, String>("a", "iconCssClass"),
                new Pair<String, String>("alert", "cssClass"),
                new Pair<String, String>("button", "cssClass"),
                new Pair<String, String>("button-row", "cssClass"),
                new Pair<String, String>("col", "cssClass"),
                new Pair<String, String>("container", "cssClass"),
                new Pair<String, String>("field-wrapper", "cssClass"),
                new Pair<String, String>("fieldset", "cssClass"),
                new Pair<String, String>("form", "cssClass"),
                new Pair<String, String>("icon", "cssClass"),
                new Pair<String, String>("input", "cssClass"),
                new Pair<String, String>("input", "helpTextCssClass"),
                new Pair<String, String>("input", "wrapperCssClass"),
                new Pair<String, String>("nav", "cssClass"),
                new Pair<String, String>("nav-bar", "cssClass"),
                new Pair<String, String>("nav-bar-search", "cssClass"),
                new Pair<String, String>("nav-item", "anchorCssClass"),
                new Pair<String, String>("nav-item", "cssClass"),
                new Pair<String, String>("nav-item", "iconCssClass"),
                new Pair<String, String>("option", "cssClass"),
                new Pair<String, String>("row", "cssClass"),
                new Pair<String, String>("select", "cssClass"),
                new Pair<String, String>("select", "wrapperCssClass")
        ));

        TAGLIB_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_AUI_OLD, TAGLIB_ATTRIBUTES.get(LiferayTaglibs.TAGLIB_URI_AUI));

        TAGLIB_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_UI, Arrays.asList(
                new Pair<String, String>("alert", "cssClass"),
                new Pair<String, String>("app-view-entry", "cssClass"),
                new Pair<String, String>("app-view-entry", "iconCssClass"),
                new Pair<String, String>("app-view-search-entry", "iconCssClass"),
                new Pair<String, String>("empty-result-message", "cssClass"),
                new Pair<String, String>("header", "cssClass"),
                new Pair<String, String>("icon", "cssClass"),
                new Pair<String, String>("icon", "iconCssClass"),
                new Pair<String, String>("icon", "linkCssClass"),
                new Pair<String, String>("icon-delete", "cssClass"),
                new Pair<String, String>("icon-delete", "linkCssClass"),
                new Pair<String, String>("icon-menu", "cssClass"),
                new Pair<String, String>("icon-menu", "triggerCssClass"),
                new Pair<String, String>("input-checkbox", "cssClass"),
                new Pair<String, String>("input-date", "cssClass"),
                new Pair<String, String>("input-editor", "cssClass"),
                new Pair<String, String>("input-field", "cssClass"),
                new Pair<String, String>("input-localized", "cssClass"),
                new Pair<String, String>("input-move-boxes", "cssClass"),
                new Pair<String, String>("input-repeat", "cssClass"),
                new Pair<String, String>("input-resource", "cssClass"),
                new Pair<String, String>("input-search", "cssClass"),
                new Pair<String, String>("input-select", "cssClass"),
                new Pair<String, String>("input-textarea", "cssClass"),
                new Pair<String, String>("input-time", "cssClass"),
                new Pair<String, String>("input-time-zone", "cssClass"),
                new Pair<String, String>("my-sites", "cssClass"),
                new Pair<String, String>("panel", "cssClass"),
                new Pair<String, String>("panel", "iconCssClass"),
                new Pair<String, String>("panel-container", "cssClass"),
                new Pair<String, String>("search-container", "cssClass"),
                new Pair<String, String>("search-container", "emptyResultsMessageCssClass"),
                new Pair<String, String>("search-container-column-button", "cssClass"),
                new Pair<String, String>("search-container-column-date", "cssClass"),
                new Pair<String, String>("search-container-column-icon", "cssClass"),
                new Pair<String, String>("search-container-column-image", "cssClass"),
                new Pair<String, String>("search-container-column-jsp", "cssClass"),
                new Pair<String, String>("search-container-column-status", "cssClass"),
                new Pair<String, String>("search-container-column-text", "cssClass"),
                new Pair<String, String>("search-container-column-user", "cssClass"),
                new Pair<String, String>("search-container-row", "cssClass"),
                new Pair<String, String>("tabs", "cssClass"),
                new Pair<String, String>("user-display", "imageCssClass"),
                new Pair<String, String>("user-display", "userIconCssClass"),
                new Pair<String, String>("user-portrait", "cssClass"),
                new Pair<String, String>("user-portrait", "imageCssClass")
        ));

        TAGLIB_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_FRONTEND, Arrays.asList(
                new Pair<String, String>("horizontal-card", "checkboxCSSClass"),
                new Pair<String, String>("horizontal-card", "cssClass"),
                new Pair<String, String>("icon-vertical-card", "checkboxCSSClass"),
                new Pair<String, String>("icon-vertical-card", "cssClass"),
                new Pair<String, String>("image-card", "cssClass"),
                new Pair<String, String>("image-card", "imageCssClass"),
                new Pair<String, String>("info-bar-button", "cssClass"),
                new Pair<String, String>("info-bar-button", "iconCssClass"),
                new Pair<String, String>("info-bar-sidenav-toggler-button", "cssClass"),
                new Pair<String, String>("info-bar-sidenav-toggler-button", "iconCssClass"),
                new Pair<String, String>("management-bar-button", "cssClass"),
                new Pair<String, String>("management-bar-button", "iconCssClass"),
                new Pair<String, String>("management-bar-sidenav-toggler-button", "cssClass"),
                new Pair<String, String>("management-bar-sidenav-toggler-button", "iconCssClass"),
                new Pair<String, String>("translation-manager", "cssClass"),
                new Pair<String, String>("user-vertical-card", "checkboxCSSClass"),
                new Pair<String, String>("user-vertical-card", "cssClass"),
                new Pair<String, String>("vertical-card", "checkboxCSSClass"),
                new Pair<String, String>("vertical-card", "cssClass"),
                new Pair<String, String>("vertical-card", "imageCSSClass"),
                new Pair<String, String>("vertical-card-small-image", "cssClass")
        ));

        TAGLIB_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_ITEM_SELECTOR, Arrays.asList(
                new Pair<String, String>("image-selector", "draggableImage")
        ));

    }

    private static final List<String> ATTRIBUTE_NAMES = Arrays.asList(
            "cssClass",
            "iconCssClass",
            "imageCssClass",
            "imageCSSClass",
            "linkCssClass",
            "triggerCssClass",
            "elementClasses",
            "draggableImage",
            "emptyResultsMessageCssClass",
            "userIconCssClass",
            "helpTextCssClass",
            "wrapperCssClass",
            "anchorCssClass",
            "userIconCssClass",
            "checkboxCSSClass"
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
                    if (TAGLIB_ATTRIBUTES.containsKey(xmlTag.getNamespace())) {
                        Collection<Pair<String, String>> entries = TAGLIB_ATTRIBUTES.get(xmlTag.getNamespace());
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
                return false;
            }
        };
        XmlUtil.registerXmlAttributeValueReferenceProvider(registrar, ATTRIBUTE_NAMES.toArray(new String[ATTRIBUTE_NAMES.size()]), htmlClassOrIdReferenceProvider.getFilter(), false, htmlClassOrIdReferenceProvider);
    }

}