package de.dm.intellij.liferay.language.clientextension;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.ArrayUtil;
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider;

import java.util.Arrays;
import java.util.Collection;

public class ClientExtensionYamlSchemaFileProviderFactory {

	private static final String BATCH = "batch";
	private static final String CUSTOM_ELEMENT = "customElement";
	private static final String EDITOR_CONFIG_CONTRIBUTOR = "editorConfigContributor";
	private static final String FDS_CELL_RENDERER = "fdsCellRenderer";
	private static final String FDS_FILTER = "fdsFilter";
	private static final String GLOBAL_CSS = "globalCSS";
	private static final String GLOBAL_JS = "globalJS";
	private static final String IFRAME = "iframe";
	private static final String INSTANCE_SETTINGS = "instanceSettings";
	private static final String JS_IMPORT_MAPS_ENTRY = "jsImportMapsEntry";
	private static final String NOTIFICATION_TYPE = "notificationType";
	private static final String OAUTH_APPLICATION_HEADLESS_SERVER = "oAuthApplicationHeadlessServer";
	private static final String OAUTH_APPLICATION_USER_AGENT = "oAuthApplicationUserAgent";
	private static final String OBJECT_ACTION = "objectAction";
	private static final String OBJECT_VALIDATION_RULE = "objectValidationRule";
	private static final String SITE_INITIALIZER = "siteInitializer";
	private static final String STATIC_CONTENT = "staticContent";
	private static final String THEME_CSS = "themeCSS";
	private static final String THEME_FAVICON = "themeFavicon";
	private static final String THEME_JS = "themeJS";
	private static final String THEME_SPRITEMAP = "themeSpritemap";
	private static final String WORKFLOW_ACTION = "workflowAction";

	private static final String[] KNOWN_CLIENT_EXTENSION_TYPES = new String[] {
			BATCH, CUSTOM_ELEMENT, EDITOR_CONFIG_CONTRIBUTOR, FDS_CELL_RENDERER, FDS_FILTER, GLOBAL_CSS, GLOBAL_JS,
			IFRAME, INSTANCE_SETTINGS, JS_IMPORT_MAPS_ENTRY, NOTIFICATION_TYPE, OAUTH_APPLICATION_HEADLESS_SERVER,
			OAUTH_APPLICATION_USER_AGENT, OBJECT_ACTION, OBJECT_VALIDATION_RULE, SITE_INITIALIZER, STATIC_CONTENT, THEME_CSS,
			THEME_FAVICON, THEME_JS, THEME_SPRITEMAP, WORKFLOW_ACTION
	};

	public static Collection<JsonSchemaFileProvider> getClientExtensionYamlSchemaFileProviders(Project project) {
		return Arrays.asList(
				new GenericClientExtensionYamlSchemaFileProvider(
						"Batch Client Extension", project, "client-extension-schema-batch.json", type -> StringUtil.equals(type, BATCH)
				),
				new GenericClientExtensionYamlSchemaFileProvider(
						"Custom Element Client Extension", project, "client-extension-schema-custom-element.json", type -> StringUtil.equals(type, CUSTOM_ELEMENT)
				),
				new GenericClientExtensionYamlSchemaFileProvider(
						"Editor Config Contributor Client Extension", project, "client-extension-schema-editor-config-contributor.json", type -> StringUtil.equals(type, EDITOR_CONFIG_CONTRIBUTOR)
				),
				new GenericClientExtensionYamlSchemaFileProvider(
						"Frontend data set cell renderer Client Extension", project, "client-extension-schema-fds-cell-renderer.json", type -> StringUtil.equals(type, FDS_CELL_RENDERER)
				),
				new GenericClientExtensionYamlSchemaFileProvider(
						"Frontend Data Set Filter Client Extension", project, "client-extension-schema-fds-filter.json", type -> StringUtil.equals(type, FDS_FILTER)
				),
				new GenericClientExtensionYamlSchemaFileProvider(
						"CSS Client Extension", project, "client-extension-schema-global-css.json", type -> StringUtil.equals(type, GLOBAL_CSS)
				),
				new GenericClientExtensionYamlSchemaFileProvider(
						"JavaScript Client Extension", project, "client-extension-schema-global-js.json", type -> StringUtil.equals(type, GLOBAL_JS)
				),
				new GenericClientExtensionYamlSchemaFileProvider(
						"IFrame Client Extension", project, "client-extension-schema-iframe.json", type -> StringUtil.equals(type, IFRAME)
				),
				new GenericClientExtensionYamlSchemaFileProvider(
						"Instance Settings Client Extension", project, "client-extension-schema-instance-settings.json", type -> StringUtil.equals(type, INSTANCE_SETTINGS)
				),
				new GenericClientExtensionYamlSchemaFileProvider(
						"JS Import Maps Entry Client Extension", project, "client-extension-schema-js-import-maps-entry.json", type -> StringUtil.equals(type, JS_IMPORT_MAPS_ENTRY)
				),
				new GenericClientExtensionYamlSchemaFileProvider(
						"Notification Type Client Extension", project, "client-extension-schema-notification-type.json", type -> StringUtil.equals(type, NOTIFICATION_TYPE)
				),
				new GenericClientExtensionYamlSchemaFileProvider(
						"OAuth Headless Server Client Extension", project, "client-extension-schema-oauth-application-headless-server.json", type -> StringUtil.equals(type, OAUTH_APPLICATION_HEADLESS_SERVER)
				),
				new GenericClientExtensionYamlSchemaFileProvider(
						"OAuth User Agent Client Extension", project, "client-extension-schema-oauth-application-user-agent.json", type -> StringUtil.equals(type, OAUTH_APPLICATION_USER_AGENT)
				),
				new GenericClientExtensionYamlSchemaFileProvider(
						"Object Action Client Extension", project, "client-extension-schema-object-action.json", type -> StringUtil.equals(type, OBJECT_ACTION)
				),
				new GenericClientExtensionYamlSchemaFileProvider(
						"Object Validation Rule Client Extension", project, "client-extension-schema-object-validation-rule.json", type -> StringUtil.equals(type, OBJECT_VALIDATION_RULE)
				),
				new GenericClientExtensionYamlSchemaFileProvider(
						"Site Initializer Client Extension", project, "client-extension-schema-site-initializer.json", type -> StringUtil.equals(type, SITE_INITIALIZER)
				),
				new GenericClientExtensionYamlSchemaFileProvider(
						"Static content Client Extension", project, "client-extension-schema-static-content.json", type -> StringUtil.equals(type, STATIC_CONTENT)
				),
				new GenericClientExtensionYamlSchemaFileProvider(
						"Theme CSS Client Extension", project, "client-extension-schema-theme-css.json", type -> StringUtil.equals(type, THEME_CSS)
				),
				new GenericClientExtensionYamlSchemaFileProvider(
						"Theme Favicon Client Extension", project, "client-extension-schema-theme-favicon.json", type -> StringUtil.equals(type, THEME_FAVICON)
				),
				new GenericClientExtensionYamlSchemaFileProvider(
						"Theme JavaScript Client Extension", project, "client-extension-schema-theme-js.json", type -> StringUtil.equals(type, THEME_JS)
				),
				new GenericClientExtensionYamlSchemaFileProvider(
						"Theme Sprite Map Client Extension", project, "client-extension-schema-theme-spritemap.json", type -> StringUtil.equals(type, THEME_SPRITEMAP)
				),
				new GenericClientExtensionYamlSchemaFileProvider(
						"Workflow Action Client Extension", project, "client-extension-schema-workflow-action.json", type -> StringUtil.equals(type, WORKFLOW_ACTION)
				),
				new GenericClientExtensionYamlSchemaFileProvider(
						"Unknown Liferay Client Extension", project, "client-extension-schema.json", type -> (type == null || (! ArrayUtil.contains(type, KNOWN_CLIENT_EXTENSION_TYPES)))
				)
		);
	}

}
