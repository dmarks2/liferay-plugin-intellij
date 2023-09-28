package de.dm.intellij.liferay.language.xml;

import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_DISPLAY_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_DISPLAY_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_DISPLAY_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_DISPLAY_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_DISPLAY_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_DISPLAY_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_DISPLAY_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_HOOK_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_HOOK_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_HOOK_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_HOOK_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_HOOK_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_HOOK_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_HOOK_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PORTLET_APP_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PORTLET_APP_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PORTLET_APP_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PORTLET_APP_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PORTLET_APP_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PORTLET_APP_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_PORTLET_APP_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SOCIAL_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SOCIAL_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SOCIAL_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SOCIAL_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SOCIAL_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SOCIAL_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_SOCIAL_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_THEME_LOADER_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_THEME_LOADER_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_THEME_LOADER_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_THEME_LOADER_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_THEME_LOADER_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_THEME_LOADER_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_THEME_LOADER_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_DISPLAY_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_DISPLAY_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_DISPLAY_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_DISPLAY_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_DISPLAY_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_DISPLAY_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_DISPLAY_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_HOOK_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_HOOK_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_HOOK_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_HOOK_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_HOOK_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_HOOK_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_HOOK_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_PORTLET_APP_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_PORTLET_APP_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_PORTLET_APP_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_PORTLET_APP_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_PORTLET_APP_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_PORTLET_APP_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_PORTLET_APP_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_SOCIAL_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_SOCIAL_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_SOCIAL_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_SOCIAL_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_SOCIAL_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_SOCIAL_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_SOCIAL_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_THEME_LOADER_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_THEME_LOADER_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_THEME_LOADER_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_THEME_LOADER_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_THEME_LOADER_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_THEME_LOADER_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_THEME_LOADER_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_4_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_URN_LIFERAY_WORKFLOW_DEFINITION_6_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_URN_LIFERAY_WORKFLOW_DEFINITION_6_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_URN_LIFERAY_WORKFLOW_DEFINITION_7_0_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_URN_LIFERAY_WORKFLOW_DEFINITION_7_1_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_URN_LIFERAY_WORKFLOW_DEFINITION_7_2_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_URN_LIFERAY_WORKFLOW_DEFINITION_7_3_0;
import static de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider.XML_URN_LIFERAY_WORKFLOW_DEFINITION_7_4_0;

public class LiferayXmlDeprecations {

	public record PublicIdDtdUri(String publicId, String dtdUri) {}
	public record UrnSchemaLocation(String urn, String schemaLocation) {}

	public record DtdDeprecation(float majorLiferayVersion, PublicIdDtdUri[] dtds, PublicIdDtdUri newDtd) {}
	public record NamespaceDeprecation(float majorLiferayVersion, UrnSchemaLocation[] namespaces, UrnSchemaLocation newNamespace) {}

	public static DtdDeprecation[] XML_DTD_DEPRECATIONS = new DtdDeprecation[] {
			new DtdDeprecation(7.0f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_6_1_0, XML_NAMESPACE_LIFERAY_DISPLAY_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_6_2_0, XML_NAMESPACE_LIFERAY_DISPLAY_6_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_7_0_0, XML_NAMESPACE_LIFERAY_DISPLAY_7_0_0)),
			new DtdDeprecation(7.1f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_6_1_0, XML_NAMESPACE_LIFERAY_DISPLAY_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_6_2_0, XML_NAMESPACE_LIFERAY_DISPLAY_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_7_0_0, XML_NAMESPACE_LIFERAY_DISPLAY_7_0_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_7_1_0, XML_NAMESPACE_LIFERAY_DISPLAY_7_1_0)),
			new DtdDeprecation(7.2f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_6_1_0, XML_NAMESPACE_LIFERAY_DISPLAY_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_6_2_0, XML_NAMESPACE_LIFERAY_DISPLAY_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_7_0_0, XML_NAMESPACE_LIFERAY_DISPLAY_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_7_1_0, XML_NAMESPACE_LIFERAY_DISPLAY_7_1_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_7_2_0, XML_NAMESPACE_LIFERAY_DISPLAY_7_2_0)),
			new DtdDeprecation(7.3f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_6_1_0, XML_NAMESPACE_LIFERAY_DISPLAY_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_6_2_0, XML_NAMESPACE_LIFERAY_DISPLAY_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_7_0_0, XML_NAMESPACE_LIFERAY_DISPLAY_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_7_1_0, XML_NAMESPACE_LIFERAY_DISPLAY_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_7_2_0, XML_NAMESPACE_LIFERAY_DISPLAY_7_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_7_3_0, XML_NAMESPACE_LIFERAY_DISPLAY_7_3_0)),
			new DtdDeprecation(7.4f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_6_1_0, XML_NAMESPACE_LIFERAY_DISPLAY_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_6_2_0, XML_NAMESPACE_LIFERAY_DISPLAY_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_7_0_0, XML_NAMESPACE_LIFERAY_DISPLAY_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_7_1_0, XML_NAMESPACE_LIFERAY_DISPLAY_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_7_2_0, XML_NAMESPACE_LIFERAY_DISPLAY_7_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_7_3_0, XML_NAMESPACE_LIFERAY_DISPLAY_7_3_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_DISPLAY_7_4_0, XML_NAMESPACE_LIFERAY_DISPLAY_7_4_0)),
			
			new DtdDeprecation(7.0f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_6_1_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_6_2_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_6_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_7_0_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_7_0_0)),
			new DtdDeprecation(7.1f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_6_1_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_6_2_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_7_0_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_7_0_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_7_1_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_7_1_0)),
			new DtdDeprecation(7.2f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_6_1_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_6_2_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_7_0_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_7_1_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_7_1_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_7_2_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_7_2_0)),
			new DtdDeprecation(7.3f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_6_1_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_6_2_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_7_0_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_7_1_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_7_2_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_7_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_7_3_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_7_3_0)),
			new DtdDeprecation(7.4f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_6_1_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_6_2_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_7_0_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_7_1_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_7_2_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_7_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_7_3_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_7_3_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_FRIENDLY_URL_ROUTES_7_4_0, XML_NAMESPACE_LIFERAY_FRIENDLY_URL_ROUTES_7_4_0)),
			
			new DtdDeprecation(7.0f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_6_1_0, XML_NAMESPACE_LIFERAY_HOOK_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_6_2_0, XML_NAMESPACE_LIFERAY_HOOK_6_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_7_0_0, XML_NAMESPACE_LIFERAY_HOOK_7_0_0)),
			new DtdDeprecation(7.1f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_6_1_0, XML_NAMESPACE_LIFERAY_HOOK_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_6_2_0, XML_NAMESPACE_LIFERAY_HOOK_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_7_0_0, XML_NAMESPACE_LIFERAY_HOOK_7_0_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_7_1_0, XML_NAMESPACE_LIFERAY_HOOK_7_1_0)),
			new DtdDeprecation(7.2f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_6_1_0, XML_NAMESPACE_LIFERAY_HOOK_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_6_2_0, XML_NAMESPACE_LIFERAY_HOOK_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_7_0_0, XML_NAMESPACE_LIFERAY_HOOK_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_7_1_0, XML_NAMESPACE_LIFERAY_HOOK_7_1_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_7_2_0, XML_NAMESPACE_LIFERAY_HOOK_7_2_0)),
			new DtdDeprecation(7.3f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_6_1_0, XML_NAMESPACE_LIFERAY_HOOK_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_6_2_0, XML_NAMESPACE_LIFERAY_HOOK_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_7_0_0, XML_NAMESPACE_LIFERAY_HOOK_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_7_1_0, XML_NAMESPACE_LIFERAY_HOOK_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_7_2_0, XML_NAMESPACE_LIFERAY_HOOK_7_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_7_3_0, XML_NAMESPACE_LIFERAY_HOOK_7_3_0)),
			new DtdDeprecation(7.4f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_6_1_0, XML_NAMESPACE_LIFERAY_HOOK_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_6_2_0, XML_NAMESPACE_LIFERAY_HOOK_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_7_0_0, XML_NAMESPACE_LIFERAY_HOOK_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_7_1_0, XML_NAMESPACE_LIFERAY_HOOK_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_7_2_0, XML_NAMESPACE_LIFERAY_HOOK_7_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_7_3_0, XML_NAMESPACE_LIFERAY_HOOK_7_3_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_HOOK_7_4_0, XML_NAMESPACE_LIFERAY_HOOK_7_4_0)),
			
			new DtdDeprecation(7.0f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_6_1_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_6_2_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_6_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_7_0_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_7_0_0)),
			new DtdDeprecation(7.1f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_6_1_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_6_2_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_7_0_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_7_0_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_7_1_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_7_1_0)),
			new DtdDeprecation(7.2f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_6_1_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_6_2_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_7_0_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_7_1_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_7_1_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_7_2_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_7_2_0)),
			new DtdDeprecation(7.3f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_6_1_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_6_2_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_7_0_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_7_1_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_7_2_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_7_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_7_3_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_7_3_0)),
			new DtdDeprecation(7.4f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_6_1_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_6_2_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_7_0_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_7_1_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_7_2_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_7_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_7_3_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_7_3_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LAYOUT_TEMPLATES_7_4_0, XML_NAMESPACE_LIFERAY_LAYOUT_TEMPLATES_7_4_0)),
			
			new DtdDeprecation(7.0f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_6_1_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_6_2_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_7_0_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_0_0)),
			new DtdDeprecation(7.1f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_6_1_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_6_2_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_7_0_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_0_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_7_1_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_1_0)),
			new DtdDeprecation(7.2f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_6_1_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_6_2_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_7_0_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_7_1_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_1_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_7_2_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_2_0)),
			new DtdDeprecation(7.3f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_6_1_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_6_2_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_7_0_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_7_1_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_7_2_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_7_3_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_3_0)),
			new DtdDeprecation(7.4f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_6_1_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_6_2_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_7_0_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_7_1_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_7_2_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_7_3_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_3_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_LOOK_AND_FEEL_7_4_0, XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_4_0)),
			
			new DtdDeprecation(7.0f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_6_1_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_6_2_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_6_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_7_0_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_7_0_0)),
			new DtdDeprecation(7.1f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_6_1_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_6_2_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_7_0_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_7_0_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_7_1_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_7_1_0)),
			new DtdDeprecation(7.2f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_6_1_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_6_2_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_7_0_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_7_1_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_7_1_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_7_2_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_7_2_0)),
			new DtdDeprecation(7.3f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_6_1_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_6_2_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_7_0_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_7_1_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_7_2_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_7_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_7_3_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_7_3_0)),
			new DtdDeprecation(7.4f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_6_1_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_6_2_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_7_0_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_7_1_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_7_2_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_7_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_7_3_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_7_3_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_PACKAGE_7_4_0, XML_NAMESPACE_LIFERAY_PLUGIN_PACKAGE_7_4_0)),
			
			new DtdDeprecation(7.0f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_6_1_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_6_2_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_6_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_7_0_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_7_0_0)),
			new DtdDeprecation(7.1f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_6_1_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_6_2_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_7_0_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_7_0_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_7_1_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_7_1_0)),
			new DtdDeprecation(7.2f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_6_1_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_6_2_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_7_0_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_7_1_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_7_1_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_7_2_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_7_2_0)),
			new DtdDeprecation(7.3f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_6_1_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_6_2_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_7_0_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_7_1_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_7_2_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_7_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_7_3_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_7_3_0)),
			new DtdDeprecation(7.4f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_6_1_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_6_2_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_7_0_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_7_1_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_7_2_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_7_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_7_3_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_7_3_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PLUGIN_REPOSITORY_7_4_0, XML_NAMESPACE_LIFERAY_PLUGIN_REPOSITORY_7_4_0)),
			
			new DtdDeprecation(7.0f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_6_1_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_6_2_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_6_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_7_0_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_7_0_0)),
			new DtdDeprecation(7.1f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_6_1_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_6_2_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_7_0_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_7_0_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_7_1_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_7_1_0)),
			new DtdDeprecation(7.2f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_6_1_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_6_2_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_7_0_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_7_1_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_7_1_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_7_2_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_7_2_0)),
			new DtdDeprecation(7.3f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_6_1_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_6_2_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_7_0_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_7_1_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_7_2_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_7_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_7_3_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_7_3_0)),
			new DtdDeprecation(7.4f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_6_1_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_6_2_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_7_0_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_7_1_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_7_2_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_7_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_7_3_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_7_3_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_PORTLET_APP_7_4_0, XML_NAMESPACE_LIFERAY_PORTLET_APP_7_4_0)),
			
			new DtdDeprecation(7.0f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_6_1_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_6_2_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_6_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_7_0_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_0_0)),
			new DtdDeprecation(7.1f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_6_1_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_6_2_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_7_0_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_0_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_7_1_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_1_0)),
			new DtdDeprecation(7.2f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_6_1_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_6_2_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_7_0_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_7_1_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_1_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_7_2_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_2_0)),
			new DtdDeprecation(7.3f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_6_1_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_6_2_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_7_0_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_7_1_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_7_2_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_7_3_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_3_0)),
			new DtdDeprecation(7.4f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_6_1_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_6_2_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_7_0_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_7_1_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_7_2_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_7_3_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_3_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_RESOURCE_ACTION_MAPPING_7_4_0, XML_NAMESPACE_LIFERAY_RESOURCE_ACTION_MAPPING_7_4_0)),

			new DtdDeprecation(7.0f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_6_1_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_6_2_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_7_0_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_0_0)),
			new DtdDeprecation(7.1f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_6_1_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_6_2_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_7_0_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_0_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_7_1_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_1_0)),
			new DtdDeprecation(7.2f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_6_1_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_6_2_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_7_0_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_7_1_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_1_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_7_2_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_2_0)),
			new DtdDeprecation(7.3f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_6_1_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_6_2_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_7_0_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_7_1_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_7_2_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_7_3_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_3_0)),
			new DtdDeprecation(7.4f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_6_1_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_6_2_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_7_0_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_7_1_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_7_2_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_7_3_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_3_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SERVICE_BUILDER_7_4_0, XML_NAMESPACE_LIFERAY_SERVICE_BUILDER_7_4_0)),

			new DtdDeprecation(7.0f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_6_1_0, XML_NAMESPACE_LIFERAY_SOCIAL_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_6_2_0, XML_NAMESPACE_LIFERAY_SOCIAL_6_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_7_0_0, XML_NAMESPACE_LIFERAY_SOCIAL_7_0_0)),
			new DtdDeprecation(7.1f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_6_1_0, XML_NAMESPACE_LIFERAY_SOCIAL_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_6_2_0, XML_NAMESPACE_LIFERAY_SOCIAL_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_7_0_0, XML_NAMESPACE_LIFERAY_SOCIAL_7_0_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_7_1_0, XML_NAMESPACE_LIFERAY_SOCIAL_7_1_0)),
			new DtdDeprecation(7.2f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_6_1_0, XML_NAMESPACE_LIFERAY_SOCIAL_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_6_2_0, XML_NAMESPACE_LIFERAY_SOCIAL_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_7_0_0, XML_NAMESPACE_LIFERAY_SOCIAL_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_7_1_0, XML_NAMESPACE_LIFERAY_SOCIAL_7_1_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_7_2_0, XML_NAMESPACE_LIFERAY_SOCIAL_7_2_0)),
			new DtdDeprecation(7.3f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_6_1_0, XML_NAMESPACE_LIFERAY_SOCIAL_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_6_2_0, XML_NAMESPACE_LIFERAY_SOCIAL_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_7_0_0, XML_NAMESPACE_LIFERAY_SOCIAL_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_7_1_0, XML_NAMESPACE_LIFERAY_SOCIAL_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_7_2_0, XML_NAMESPACE_LIFERAY_SOCIAL_7_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_7_3_0, XML_NAMESPACE_LIFERAY_SOCIAL_7_3_0)),
			new DtdDeprecation(7.4f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_6_1_0, XML_NAMESPACE_LIFERAY_SOCIAL_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_6_2_0, XML_NAMESPACE_LIFERAY_SOCIAL_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_7_0_0, XML_NAMESPACE_LIFERAY_SOCIAL_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_7_1_0, XML_NAMESPACE_LIFERAY_SOCIAL_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_7_2_0, XML_NAMESPACE_LIFERAY_SOCIAL_7_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_7_3_0, XML_NAMESPACE_LIFERAY_SOCIAL_7_3_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_SOCIAL_7_4_0, XML_NAMESPACE_LIFERAY_SOCIAL_7_4_0)),

			new DtdDeprecation(7.0f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_6_1_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_6_2_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_6_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_7_0_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_7_0_0)),
			new DtdDeprecation(7.1f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_6_1_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_6_2_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_7_0_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_7_0_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_7_1_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_7_1_0)),
			new DtdDeprecation(7.2f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_6_1_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_6_2_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_7_0_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_7_1_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_7_1_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_7_2_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_7_2_0)),
			new DtdDeprecation(7.3f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_6_1_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_6_2_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_7_0_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_7_1_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_7_2_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_7_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_7_3_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_7_3_0)),
			new DtdDeprecation(7.4f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_6_1_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_6_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_6_2_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_7_0_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_7_1_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_7_2_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_7_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_7_3_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_7_3_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_THEME_LOADER_7_4_0, XML_NAMESPACE_LIFERAY_THEME_LOADER_7_4_0)),
			
			new DtdDeprecation(7.0f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_6_2_0, XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_6_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_0_0, XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_0_0)),
			new DtdDeprecation(7.1f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_6_2_0, XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_0_0, XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_0_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_1_0, XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_1_0)),
			new DtdDeprecation(7.2f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_6_2_0, XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_0_0, XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_1_0, XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_1_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_2_0, XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_2_0)),
			new DtdDeprecation(7.3f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_6_2_0, XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_0_0, XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_1_0, XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_2_0, XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_2_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_3_0, XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_3_0)),
			new DtdDeprecation(7.4f, new PublicIdDtdUri[] {
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_6_2_0, XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_6_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_0_0, XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_0_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_1_0, XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_1_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_2_0, XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_2_0),
					new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_3_0, XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_3_0),
			}, new PublicIdDtdUri(XML_PUBLIC_ID_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_4_0, XML_NAMESPACE_LIFERAY_USER_NOTIFICATION_DEFINITIONS_7_4_0)),

	};

	public static NamespaceDeprecation[] XML_NAMESPACE_DEPRECATIONS = new NamespaceDeprecation[] {
			new NamespaceDeprecation(7.0f, new UrnSchemaLocation[]{
					new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_6_1_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_6_1_0),
					new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_6_2_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_6_2_0),
			}, new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_7_0_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_7_0_0)),
			new NamespaceDeprecation(7.1f, new UrnSchemaLocation[]{
					new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_6_1_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_6_1_0),
					new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_6_2_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_6_2_0),
					new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_7_0_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_7_0_0),
			}, new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_7_1_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_7_1_0)),
			new NamespaceDeprecation(7.2f, new UrnSchemaLocation[]{
					new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_6_1_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_6_1_0),
					new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_6_2_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_6_2_0),
					new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_7_0_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_7_0_0),
					new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_7_1_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_7_1_0),
			}, new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_7_2_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_7_2_0)),
			new NamespaceDeprecation(7.3f, new UrnSchemaLocation[]{
					new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_6_1_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_6_1_0),
					new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_6_2_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_6_2_0),
					new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_7_0_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_7_0_0),
					new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_7_1_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_7_1_0),
					new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_7_2_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_7_2_0),
			}, new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_7_3_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_7_3_0)),
			new NamespaceDeprecation(7.4f, new UrnSchemaLocation[]{
					new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_6_1_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_6_1_0),
					new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_6_2_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_6_2_0),
					new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_7_0_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_7_0_0),
					new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_7_1_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_7_1_0),
					new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_7_2_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_7_2_0),
					new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_7_3_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_7_3_0),
			}, new UrnSchemaLocation(XML_URN_LIFERAY_WORKFLOW_DEFINITION_7_4_0, XML_NAMESPACE_LIFERAY_WORKFLOW_DEFINITION_7_4_0)),
	};

}
