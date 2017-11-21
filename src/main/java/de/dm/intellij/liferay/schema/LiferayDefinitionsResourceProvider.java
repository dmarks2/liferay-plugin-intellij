package de.dm.intellij.liferay.schema;

import com.intellij.javaee.ResourceRegistrar;
import com.intellij.javaee.StandardResourceProvider;

/**
 * Provides XML-Schema and DTD-Files for known Liferay XML Files
 */
public class LiferayDefinitionsResourceProvider implements StandardResourceProvider {

    public void registerResources(ResourceRegistrar resourceRegistrar) {
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-ddm-structure_6_2_0.xsd", "/com/liferay/definitions/liferay-ddm-structure_6_2_0.xsd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-display_6_1_0.dtd", "/com/liferay/definitions/liferay-display_6_1_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-display_6_2_0.dtd", "/com/liferay/definitions/liferay-display_6_2_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-display_7_0_0.dtd", "/com/liferay/definitions/liferay-display_7_0_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-friendly-url-routes_6_1_0.dtd", "/com/liferay/definitions/liferay-friendly-url-routes_6_1_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-friendly-url-routes_6_2_0.dtd", "/com/liferay/definitions/liferay-friendly-url-routes_6_2_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-friendly-url-routes_7_0_0.dtd", "/com/liferay/definitions/liferay-friendly-url-routes_7_0_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-hook_6_1_0.dtd", "/com/liferay/definitions/liferay-hook_6_1_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-hook_6_2_0.dtd", "/com/liferay/definitions/liferay-hook_6_2_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-hook_7_0_0.dtd", "/com/liferay/definitions/liferay-hook_7_0_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-layout-templates_6_1_0.dtd", "/com/liferay/definitions/liferay-layout-templates_6_1_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-layout-templates_6_2_0.dtd", "/com/liferay/definitions/liferay-layout-templates_6_2_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-layout-templates_7_0_0.dtd", "/com/liferay/definitions/liferay-layout-templates_7_0_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-look-and-feel_6_1_0.dtd", "/com/liferay/definitions/liferay-look-and-feel_6_1_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-look-and-feel_6_2_0.dtd", "/com/liferay/definitions/liferay-look-and-feel_6_2_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-look-and-feel_7_0_0.dtd", "/com/liferay/definitions/liferay-look-and-feel_7_0_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-plugin-package_6_1_0.dtd", "/com/liferay/definitions/liferay-plugin-package_6_1_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-plugin-package_6_2_0.dtd", "/com/liferay/definitions/liferay-plugin-package_6_2_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-plugin-package_7_0_0.dtd", "/com/liferay/definitions/liferay-plugin-package_7_0_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-plugin-repository_6_1_0.dtd", "/com/liferay/definitions/liferay-plugin-repository_6_1_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-plugin-repository_6_2_0.dtd", "/com/liferay/definitions/liferay-plugin-repository_6_2_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-plugin-repository_7_0_0.dtd", "/com/liferay/definitions/liferay-plugin-repository_7_0_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-portlet-app_6_1_0.dtd", "/com/liferay/definitions/liferay-portlet-app_6_1_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-portlet-app_6_2_0.dtd", "/com/liferay/definitions/liferay-portlet-app_6_2_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-portlet-app_7_0_0.dtd", "/com/liferay/definitions/liferay-portlet-app_7_0_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-portlet-app_7_1_0.dtd", "/com/liferay/definitions/liferay-portlet-app_7_1_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-resource-action-mapping_6_1_0.dtd", "/com/liferay/definitions/liferay-resource-action-mapping_6_1_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-resource-action-mapping_6_2_0.dtd", "/com/liferay/definitions/liferay-resource-action-mapping_6_2_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-resource-action-mapping_7_0_0.dtd", "/com/liferay/definitions/liferay-resource-action-mapping_7_0_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-service-builder_6_1_0.dtd", "/com/liferay/definitions/liferay-service-builder_6_1_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-service-builder_6_2_0.dtd", "/com/liferay/definitions/liferay-service-builder_6_2_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-service-builder_7_0_0.dtd", "/com/liferay/definitions/liferay-service-builder_7_0_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-service-builder_7_1_0.dtd", "/com/liferay/definitions/liferay-service-builder_7_1_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-social_6_1_0.dtd", "/com/liferay/definitions/liferay-social_6_1_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-social_6_2_0.dtd", "/com/liferay/definitions/liferay-social_6_2_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-social_7_0_0.dtd", "/com/liferay/definitions/liferay-social_7_0_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-theme-loader_6_1_0.dtd", "/com/liferay/definitions/liferay-theme-loader_6_1_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-theme-loader_6_2_0.dtd", "/com/liferay/definitions/liferay-theme-loader_6_2_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-theme-loader_7_0_0.dtd", "/com/liferay/definitions/liferay-theme-loader_7_0_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-user-notification-definitions_6_2_0.dtd", "/com/liferay/definitions/liferay-user-notification-definitions_6_2_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-user-notification-definitions_7_0_0.dtd", "/com/liferay/definitions/liferay-user-notification-definitions_7_0_0.dtd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-workflow-definition_6_1_0.xsd", "/com/liferay/definitions/liferay-workflow-definition_6_1_0.xsd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-workflow-definition_6_2_0.xsd", "/com/liferay/definitions/liferay-workflow-definition_6_2_0.xsd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-workflow-definition_7_0_0.xsd", "/com/liferay/definitions/liferay-workflow-definition_7_0_0.xsd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://www.liferay.com/dtd/liferay-workflow-definition_7_1_0.xsd", "/com/liferay/definitions/liferay-workflow-definition_7_1_0.xsd", LiferayDefinitionsResourceProvider.class);
        resourceRegistrar.addStdResource("http://java.sun.com/xml/ns/portlet/portlet-app_2_0.xsd", "/com/liferay/definitions/portlet-app_2_0.xsd", LiferayDefinitionsResourceProvider.class);
    }
}
