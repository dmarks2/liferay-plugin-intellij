package de.dm.intellij.liferay.language.jsp;

import com.intellij.lang.properties.PropertiesReferenceProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.util.XmlUtil;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LiferayTaglibResourceBundleReferenceContributor extends PsiReferenceContributor {

    private static final String TAGLIB_URI_LIFERAY_UI = "http://liferay.com/tld/ui";
    private static final String TAGLIB_URI_AUI = "http://liferay.com/tld/aui";
    private static final String TAGLIB_URI_AUI_OLD = "http://alloy.liferay.com/tld/aui";
    private static final String TAGLIB_URI_LIFERAY_ASSET = "http://liferay.com/tld/asset";
    private static final String TAGLIB_URI_LIFERAY_EXPANDO = "http://liferay.com/tld/expando";
    private static final String TAGLIB_URI_LIFERAY_FRONTEND = "http://liferay.com/tld/frontend";
    private static final String TAGLIB_URI_LIFERAY_TRASH = "http://liferay.com/tld/trash";

    private static Map<String, Collection<Pair<String, String>>> TAGLIB_ATTTRIBUTES = new HashMap<String, Collection<Pair<String, String>>>();

    //found by
    // * search for <liferay-ui:message key="..."
    // * search for LanguageUtil.get(key...)

    // nothing found for: "liferay-portlet.tld", "liferay-security.tld", "liferay-theme.tld", "liferay-util.tld", "liferay-product-navigation.tld",
    //                    "liferay-journal.tld", "liferay-flags.tld", "liferay-layout.tld", "liferay-site-navigation.tld", "liferay-map.tld",
    //                    "liferay-item-selector.tld",
    //
    // not examined yet: "liferay-portlet-ext.tld"
    static {
        TAGLIB_ATTTRIBUTES.put(TAGLIB_URI_LIFERAY_UI, Arrays.asList(
                new Pair<String, String>("app-view-search-entry", "containerType"),
                new Pair<String, String>("asset-addon-entry-selector", "title"),
                new Pair<String, String>("asset-metadata", "metadataField"),
                new Pair<String, String>("asset-tags-summary", "message"),
                new Pair<String, String>("custom-attribute", "name"),
                new Pair<String, String>("diff-html", "infoMessage"),
                new Pair<String, String>("drop-here-info", "message"),
                new Pair<String, String>("empty-result-message", "message"),
                new Pair<String, String>("error", "message"),
                new Pair<String, String>("form-navigator", "categoryLabels"),
                new Pair<String, String>("form-navigator", "categorySectionLabels"),
                new Pair<String, String>("header", "title"),
                new Pair<String, String>("icon", "message"),
                new Pair<String, String>("icon-delete", "confirmation"),
                new Pair<String, String>("icon-delete", "message"),
                new Pair<String, String>("icon-help", "message"),
                new Pair<String, String>("icon-menu", "message"),
                new Pair<String, String>("input-field", "placeholder"),
                new Pair<String, String>("input-localized", "placeholder"),
                new Pair<String, String>("input-resource", "title"),
                new Pair<String, String>("input-move-boxes", "leftTitle"),
                new Pair<String, String>("input-move-boxes", "rightTitle"),
                new Pair<String, String>("message", "key"),
                new Pair<String, String>("panel", "title"),
                new Pair<String, String>("progress", "message"),
                new Pair<String, String>("quick-access-entry", "label"),
                new Pair<String, String>("search-container", "emptyResultsMessage"),
                new Pair<String, String>("search-container", "headerNames"),
                new Pair<String, String>("search-container-column-button", "name"),
                new Pair<String, String>("search-container-column-date", "name"),
                new Pair<String, String>("search-container-column-text", "name"),
                new Pair<String, String>("search-toggle", "buttonLabel"),
                new Pair<String, String>("success", "message"),
                new Pair<String, String>("tabs", "names")
        ));

        TAGLIB_ATTTRIBUTES.put(TAGLIB_URI_AUI, Arrays.asList(
                new Pair<String, String>("a", "title"),
                new Pair<String, String>("a", "label"),
                new Pair<String, String>("button", "value"),
                new Pair<String, String>("field-wrapper", "label"),
                new Pair<String, String>("fieldset", "label"),
                new Pair<String, String>("icon", "label"),
                new Pair<String, String>("input", "helpMessage"),
                new Pair<String, String>("input", "label"),
                new Pair<String, String>("input", "labelOff"),
                new Pair<String, String>("input", "labelOn"),
                new Pair<String, String>("input", "title"),
                new Pair<String, String>("input", "placeholder"),
                new Pair<String, String>("input", "prefix"),
                new Pair<String, String>("input", "suffix"),
                new Pair<String, String>("nav-bar", "selectedItemName"),
                new Pair<String, String>("nav-item", "label"),
                new Pair<String, String>("option", "label"),
                new Pair<String, String>("panel", "label"),
                new Pair<String, String>("select", "label"),
                new Pair<String, String>("select", "prefix"),
                new Pair<String, String>("select", "suffix"),
                new Pair<String, String>("select", "title"),
                new Pair<String, String>("workflow-status", "statusMessage")
        ));
        TAGLIB_ATTTRIBUTES.put(TAGLIB_URI_AUI_OLD, TAGLIB_ATTTRIBUTES.get(TAGLIB_URI_AUI));

        TAGLIB_ATTTRIBUTES.put(TAGLIB_URI_LIFERAY_ASSET, Arrays.asList(
                new Pair<String, String>("asset-addon-entry-selector", "title"),
                new Pair<String, String>("asset-metadata", "metadataField"),
                new Pair<String, String>("asset-tags-summary", "message")
        ));

        TAGLIB_ATTTRIBUTES.put(TAGLIB_URI_LIFERAY_EXPANDO, Arrays.asList(
                new Pair<String, String>("custom-attribute", "name")
        ));

        TAGLIB_ATTTRIBUTES.put(TAGLIB_URI_LIFERAY_FRONTEND, Arrays.asList(
                new Pair<String, String>("email-notification-settings", "bodyLabel"),
                new Pair<String, String>("email-notification-settings", "helpMessage"),
                new Pair<String, String>("management-bar-button", "label"),
                new Pair<String, String>("management-bar-filter", "label")
        ));

        TAGLIB_ATTTRIBUTES.put(TAGLIB_URI_LIFERAY_TRASH, Arrays.asList(
                new Pair<String, String>("empty", "confirmMessage"),
                new Pair<String, String>("empty", "emptyMessage"),
                new Pair<String, String>("empty", "infoMessage")
        ));

    }

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        Set<String> attributeNames = new HashSet<String>();
        for (Map.Entry<String, Collection<Pair<String, String>>> taglib : TAGLIB_ATTTRIBUTES.entrySet()) {
            for (Pair<String, String> entry : taglib.getValue()) {
                attributeNames.add(entry.getValue());
            }
        }

        XmlUtil.registerXmlAttributeValueReferenceProvider(
                registrar,
                attributeNames.toArray(new String[attributeNames.size()]),
                new LiferayTaglibFilter(),
                false,
                new PropertiesReferenceProvider(true)  //do not show as error if no valid language reference is used
        );
    }

    class LiferayTaglibFilter implements ElementFilter {
        public boolean isAcceptable(Object element, PsiElement context) {
            PsiElement psiElement = (PsiElement)element;
            PsiElement parent = psiElement.getParent();
            if (parent instanceof XmlAttribute) {
                XmlAttribute xmlAttribute = (XmlAttribute)parent;
                XmlTag xmlTag = xmlAttribute.getParent();

                if (TAGLIB_ATTTRIBUTES.containsKey(xmlTag.getNamespace())) {
                    Collection<Pair<String, String>> entries = TAGLIB_ATTTRIBUTES.get(xmlTag.getNamespace());
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

        public boolean isClassAcceptable(Class hintClass) {
            return true;
        }
    }
}
