package de.dm.intellij.liferay.language.xml;

import com.intellij.psi.PsiElement;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.filters.position.FilterPattern;
import com.intellij.psi.xml.*;
import de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider;
import javafx.util.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LiferayXmlFileReferenceFilterPattern extends FilterPattern {

    private static Map<String, Collection<String>> XML_FILEREFERENCE_TAGS = new HashMap<String, Collection<String>>();

    private static Map<String, Collection<Pair<String, String>>> XML_FILEREFERENCE_ATTRIBUTES = new HashMap<String, Collection<Pair<String, String>>>();

    static {
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_HOOK_6_1_0,
                Arrays.asList(
                        "portal-properties",
                        "language-properties",
                        "custom-jsp-dir"
                )
        );
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_HOOK_6_2_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_HOOK_6_1_0));
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_HOOK_7_0_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_HOOK_6_1_0));

        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_6_1_0,
                Arrays.asList(
                        "template-path",
                        "wap-template-path",
                        "thumbnail-path",
                        "screenshot-path"
                )
        );
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_6_2_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_6_1_0));
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_7_0_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_6_1_0));

        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_1_0,
                Arrays.asList(
                        "root-path",
                        "templates-path",
                        "css-path",
                        "images-path",
                        "javascript-path",
                        "color-scheme-images-path",
                        "template-path",
                        "wap-template-path",
                        "thumbnail-path",
                        "portlet-decorator-thumbnail-path" //Liferay 7.0 only
                )
        );
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_2_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_1_0));
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_0_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_1_0));

        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PORTLET_APP_6_1_0,
                Arrays.asList(
                        "friendly-url-routes",
                        "header-portal-css",
                        "header-portlet-css",
                        "header-portal-javascript",
                        "header-portlet-javascript",
                        "footer-portal-css",
                        "footer-portlet-css",
                        "footer-portal-javascript",
                        "footer-portlet-javascript",
                        "icon",
                        "user-notification-definitions" //Liferay 6.2+
                )
        );
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PORTLET_APP_6_2_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PORTLET_APP_6_1_0));
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PORTLET_APP_7_0_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PORTLET_APP_6_1_0));
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PORTLET_APP_7_1_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PORTLET_APP_6_1_0));

        XML_FILEREFERENCE_ATTRIBUTES.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_6_1_0,
                Arrays.asList(
                        new Pair<String, String>("resource", "file")
                )
        );
        XML_FILEREFERENCE_ATTRIBUTES.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_6_2_0, XML_FILEREFERENCE_ATTRIBUTES.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_6_1_0));
        XML_FILEREFERENCE_ATTRIBUTES.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_0_0, XML_FILEREFERENCE_ATTRIBUTES.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_6_1_0));

        XML_FILEREFERENCE_ATTRIBUTES.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_1_0,
                Arrays.asList(
                        new Pair<String, String>("service-builder-import", "file")
                )
        );
        XML_FILEREFERENCE_ATTRIBUTES.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_1_0, XML_FILEREFERENCE_ATTRIBUTES.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_1_0));
        XML_FILEREFERENCE_ATTRIBUTES.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_2_0, XML_FILEREFERENCE_ATTRIBUTES.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_1_0));
        XML_FILEREFERENCE_ATTRIBUTES.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_0_0, XML_FILEREFERENCE_ATTRIBUTES.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_1_0));
        XML_FILEREFERENCE_ATTRIBUTES.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_1_0, XML_FILEREFERENCE_ATTRIBUTES.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_1_0));

        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_THEME_LOADER_6_1_0,
                Arrays.asList(
                        "themes-path"
                )
        );
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_THEME_LOADER_6_2_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_THEME_LOADER_6_1_0));
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_THEME_LOADER_7_0_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_THEME_LOADER_6_1_0));

    }

    public LiferayXmlFileReferenceFilterPattern() {
        super(new ElementFilter() {
            @Override
            public boolean isAcceptable(Object element, @Nullable PsiElement context) {
                if (element instanceof XmlElement) {
                    XmlElement xmlElement = (XmlElement) element;
                    if (xmlElement.getParent() instanceof XmlText) {
                        XmlText xmlText = (XmlText) xmlElement.getParent();
                        XmlTag xmlTag = xmlText.getParentTag();
                        if (xmlTag != null) {
                            if (XML_FILEREFERENCE_TAGS.containsKey(xmlTag.getNamespace())) {
                                for (String tagName : XML_FILEREFERENCE_TAGS.get(xmlTag.getNamespace())) {
                                    if (tagName.equals(xmlTag.getLocalName())) {
                                        return true;
                                    }
                                }
                            }
                        }
                    } else if (xmlElement instanceof XmlAttributeValue) {
                        XmlAttributeValue xmlAttributeValue = (XmlAttributeValue) xmlElement;
                        if (xmlAttributeValue.getParent() instanceof XmlAttribute) {
                            XmlAttribute xmlAttribute = (XmlAttribute) xmlAttributeValue.getParent();
                            XmlTag xmlTag = xmlAttribute.getParent();
                            if (xmlTag != null) {
                                if (XML_FILEREFERENCE_ATTRIBUTES.containsKey(xmlTag.getNamespace())) {
                                    for (Pair<String, String> pair : XML_FILEREFERENCE_ATTRIBUTES.get(xmlTag.getNamespace())) {
                                        if (pair.getKey().equals(xmlTag.getLocalName())) {
                                            if (pair.getValue().equals(xmlAttribute.getLocalName())) {
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return false;
            }

            @Override
            public boolean isClassAcceptable(Class hintClass) {
                return true;
            }
        });
    }
}
