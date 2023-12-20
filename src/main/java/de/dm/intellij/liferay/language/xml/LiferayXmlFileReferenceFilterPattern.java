package de.dm.intellij.liferay.language.xml;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.filters.position.FilterPattern;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlElement;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlText;
import de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiferayXmlFileReferenceFilterPattern extends FilterPattern {

    private static final Map<String, Collection<String>> XML_FILEREFERENCE_TAGS = new HashMap<>();

    private static final Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> XML_FILEREFERENCE_ATTRIBUTES = new HashMap<>();

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
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_HOOK_7_1_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_HOOK_6_1_0));
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_HOOK_7_2_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_HOOK_6_1_0));
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_HOOK_7_3_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_HOOK_6_1_0));

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
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_7_1_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_6_1_0));
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_7_2_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_6_1_0));
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_7_3_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_6_1_0));

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
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_1_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_1_0));
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_2_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_1_0));
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_3_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_1_0));

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
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PORTLET_APP_7_2_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PORTLET_APP_6_1_0));
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PORTLET_APP_7_3_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PORTLET_APP_6_1_0));

        XML_FILEREFERENCE_ATTRIBUTES.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_6_1_0,
				List.of(
						new AbstractMap.SimpleEntry<>("resource", "file")
				)
        );
        XML_FILEREFERENCE_ATTRIBUTES.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_6_2_0, XML_FILEREFERENCE_ATTRIBUTES.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_6_1_0));
        XML_FILEREFERENCE_ATTRIBUTES.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_0_0, XML_FILEREFERENCE_ATTRIBUTES.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_6_1_0));
        XML_FILEREFERENCE_ATTRIBUTES.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_1_0, XML_FILEREFERENCE_ATTRIBUTES.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_6_1_0));
        XML_FILEREFERENCE_ATTRIBUTES.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_2_0, XML_FILEREFERENCE_ATTRIBUTES.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_6_1_0));
        XML_FILEREFERENCE_ATTRIBUTES.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_3_0, XML_FILEREFERENCE_ATTRIBUTES.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_6_1_0));

        XML_FILEREFERENCE_ATTRIBUTES.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_1_0,
				List.of(
						new AbstractMap.SimpleEntry<>("service-builder-import", "file")
				)
        );
        XML_FILEREFERENCE_ATTRIBUTES.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_1_0, XML_FILEREFERENCE_ATTRIBUTES.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_1_0));
        XML_FILEREFERENCE_ATTRIBUTES.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_2_0, XML_FILEREFERENCE_ATTRIBUTES.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_1_0));
        XML_FILEREFERENCE_ATTRIBUTES.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_0_0, XML_FILEREFERENCE_ATTRIBUTES.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_1_0));
        XML_FILEREFERENCE_ATTRIBUTES.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_1_0, XML_FILEREFERENCE_ATTRIBUTES.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_1_0));
        XML_FILEREFERENCE_ATTRIBUTES.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_2_0, XML_FILEREFERENCE_ATTRIBUTES.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_1_0));
        XML_FILEREFERENCE_ATTRIBUTES.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_3_0, XML_FILEREFERENCE_ATTRIBUTES.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_1_0));

        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_THEME_LOADER_6_1_0,
				List.of(
						"themes-path"
				)
        );
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_THEME_LOADER_6_2_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_THEME_LOADER_6_1_0));
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_THEME_LOADER_7_0_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_THEME_LOADER_6_1_0));
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_THEME_LOADER_7_1_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_THEME_LOADER_6_1_0));
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_THEME_LOADER_7_2_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_THEME_LOADER_6_1_0));
        XML_FILEREFERENCE_TAGS.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_THEME_LOADER_7_3_0, XML_FILEREFERENCE_TAGS.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_THEME_LOADER_6_1_0));


        XML_FILEREFERENCE_TAGS.put("portlet-display-templates.xml",
                Collections.singletonList(
                        "script-file"
                )
        );
    }

    public LiferayXmlFileReferenceFilterPattern() {
        super(new ElementFilter() {
            @Override
            public boolean isAcceptable(Object element, @Nullable PsiElement context) {
                if (element instanceof XmlElement xmlElement) {
					if (xmlElement.getParent() instanceof XmlText xmlText) {
						XmlTag xmlTag = xmlText.getParentTag();
                        if (xmlTag != null) {
                            String namespace = xmlTag.getNamespace();

                            if (StringUtil.isEmpty(namespace)) {
                                PsiFile containingFile = xmlTag.getContainingFile();

                                if (containingFile != null) {
                                    namespace = containingFile.getName();
                                }
                            }
                            if (XML_FILEREFERENCE_TAGS.containsKey(namespace)) {
                                for (String tagName : XML_FILEREFERENCE_TAGS.get(namespace)) {
                                    if (tagName.equals(xmlTag.getLocalName())) {
                                        return true;
                                    }
                                }
                            }
                        }
                    } else if (xmlElement instanceof XmlAttributeValue xmlAttributeValue) {
						if (xmlAttributeValue.getParent() instanceof XmlAttribute xmlAttribute) {
							XmlTag xmlTag = xmlAttribute.getParent();
                            if (xmlTag != null) {
                                if (XML_FILEREFERENCE_ATTRIBUTES.containsKey(xmlTag.getNamespace())) {
                                    for (AbstractMap.SimpleEntry<String, String> pair : XML_FILEREFERENCE_ATTRIBUTES.get(xmlTag.getNamespace())) {
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
