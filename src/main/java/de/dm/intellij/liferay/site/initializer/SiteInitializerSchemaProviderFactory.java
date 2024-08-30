package de.dm.intellij.liferay.site.initializer;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider;
import de.dm.intellij.liferay.util.LiferayFileUtil;

import java.util.Arrays;
import java.util.List;

public class SiteInitializerSchemaProviderFactory {

	public static List<JsonSchemaFileProvider> getSiteInitializerProviders() {
		//see https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java
		//see https://liferay.dev/de/blogs/-/blogs/introducing-site-initializers
		//see https://github.com/liferay-apps/liferay-initializer/tree/master/modules/liferay-site-initializer-demo
		//see https://github.com/liferay/liferay-portal/tree/master/modules/apps/site-initializer/site-initializer-raylife-ap
		//see https://github.com/liferay/liferay-portal/tree/master/modules/apps/site-initializer/site-initializer-masterclass/src/main/resources/site-initializer
		//see https://github.com/liferay/liferay-portal/blob/master/modules/apps/layout/layout-impl/src/main/resources/com/liferay/layout/internal/importer/validator/dependencies/utility_page_template_json_schema.json

		return Arrays.asList(
				new SiteInitializerJsonSchemaFileProvider("account-group-assignments"),
				new SiteInitializerJsonSchemaFileProvider("accounts-organizations"),
				new SiteInitializerJsonSchemaFileProvider("expando-values"),
				new SiteInitializerJsonSchemaFileProvider("keywords"),
				new SiteInitializerJsonSchemaFileProvider("expando-columns"),
				new SiteInitializerJsonSchemaFileProvider("roles"),
				new SiteInitializerJsonSchemaFileProvider("site-configuration"),
				new SiteInitializerJsonSchemaFileProvider("site-settings"),
				new SiteInitializerJsonSchemaFileProvider("ddm-template",
						(virtualFile ->
								SiteInitializerUtil.isSiteInitializerFile(virtualFile, "ddm-template") && (LiferayFileUtil.getParent(virtualFile, "ddm-templates") != null)
						)
				),
				new SiteInitializerJsonSchemaFileProvider("layout-set",
						(virtualFile ->
								SiteInitializerUtil.isSiteInitializerFile(virtualFile, "metadata") && (LiferayFileUtil.getParent(virtualFile, "layout-set") != null)
						)
				),
				new SiteInitializerJsonSchemaFileProvider("layout",
						(virtualFile ->
								SiteInitializerUtil.isSiteInitializerFile(virtualFile, "page") && (LiferayFileUtil.getParent(virtualFile, "layouts") != null)
						)
				),
				new SiteInitializerJsonSchemaFileProvider("page-definition",
						(virtualFile ->
								(
										SiteInitializerUtil.isSiteInitializerFile(virtualFile, "page-definition") ||
										SiteInitializerUtil.isSiteInitializerFile(virtualFile, "display-page-template")
								)
								&& (
										(LiferayFileUtil.getParent(virtualFile, "segment-experiences") != null) ||
										(LiferayFileUtil.getParent(virtualFile, "layout-page-templates") != null) ||
										(LiferayFileUtil.getParent(virtualFile, "layout-utility-page-entries") != null)
								)
						)
				),
				new SiteInitializerJsonSchemaFileProvider("style-book",
						(virtualFile ->
								SiteInitializerUtil.isSiteInitializerFile(virtualFile, "style-book") && (LiferayFileUtil.getParent(virtualFile, "style-books") != null)
						)
				),
				new SiteInitializerJsonSchemaFileProvider("accounts"),
				new SiteInitializerJsonSchemaFileProvider("client-extension-entries"),
				new SiteInitializerJsonSchemaFileProvider("resource-permissions"),
				new SiteInitializerJsonSchemaFileProvider("user-accounts"),
				new SiteInitializerJsonSchemaFileProvider("document-folder",
						(virtualFile ->
								SiteInitializerUtil.isSiteInitializerFile(virtualFile) &&
								(LiferayFileUtil.getParent(virtualFile, "documents") != null) &&
								(virtualFile.getName().endsWith(".metadata.json")) &&
								(! VfsUtil.getChildren(virtualFile.getParent(), file -> file.getName().equals(StringUtil.trimEnd(virtualFile.getName(), ".metadata.json")) && file.isDirectory()).isEmpty())
						)
				),
				new SiteInitializerJsonSchemaFileProvider("document",
						(virtualFile ->
								SiteInitializerUtil.isSiteInitializerFile(virtualFile) &&
								(LiferayFileUtil.getParent(virtualFile, "documents") != null) &&
								(virtualFile.getName().endsWith(".metadata.json")) &&
								(! VfsUtil.getChildren(virtualFile.getParent(), file -> file.getName().equals(StringUtil.trimEnd(virtualFile.getName(), ".metadata.json")) && !file.isDirectory()).isEmpty())
						)
				),
				new SiteInitializerJsonSchemaFileProvider("list-type-definitions",
						(virtualFile ->
								SiteInitializerUtil.isSiteInitializerFile(virtualFile) &&
								(LiferayFileUtil.getParent(virtualFile, "list-type-definitions") != null) &&
								(!virtualFile.getName().endsWith(".list-type-entries.json"))
						)
				),
				new SiteInitializerJsonSchemaFileProvider("list-type-entries",
						(virtualFile ->
								SiteInitializerUtil.isSiteInitializerFile(virtualFile) &&
								(LiferayFileUtil.getParent(virtualFile, "list-type-definitions") != null) &&
								(virtualFile.getName().endsWith(".list-type-entries.json"))
						)
				),
				new SiteInitializerJsonSchemaFileProvider("workflow-definition",
						(virtualFile ->
								SiteInitializerUtil.isSiteInitializerFile(virtualFile, "workflow-definition") &&
								(LiferayFileUtil.getParent(virtualFile, "workflow-definitions") != null)
						)
				),
				new SiteInitializerJsonSchemaFileProvider("workflow-definition-properties",
						(virtualFile ->
								SiteInitializerUtil.isSiteInitializerFile(virtualFile, "workflow-definition.properties") &&
								(LiferayFileUtil.getParent(virtualFile, "workflow-definitions") != null)
						)
				),
				new SiteInitializerJsonSchemaFileProvider("asset-list-entries"),
				new SiteInitializerJsonSchemaFileProvider("site-navigation-menus"),
				new SiteInitializerJsonSchemaFileProvider("taxonomy-vocabulary",
						(virtualFile ->
								SiteInitializerUtil.isSiteInitializerFile(virtualFile) &&
								virtualFile.getParent() != null &&
								(
										StringUtil.equals(virtualFile.getParent().getName(), "company") ||
										StringUtil.equals(virtualFile.getParent().getName(), "group")
								) &&
								virtualFile.getParent().getParent() != null &&
								StringUtil.equals(virtualFile.getParent().getParent().getName(), "taxonomy-vocabularies")
						)
				),
				new SiteInitializerJsonSchemaFileProvider("taxonomy-category",
						(virtualFile ->
								SiteInitializerUtil.isSiteInitializerFile(virtualFile) &&
								virtualFile.getParent() != null &&
								virtualFile.getParent().getParent() != null &&
								LiferayFileUtil.getParent(virtualFile.getParent().getParent(), "taxonomy-vocabularies") != null
						)
				),
				new SiteInitializerJsonSchemaFileProvider("layout-utility-page-entries",
						(virtualFile ->
								SiteInitializerUtil.isSiteInitializerFile(virtualFile) &&
								virtualFile.getParent() != null &&
								StringUtil.equals(virtualFile.getParent().getName(), "layout-utility-page-entries") &&
								virtualFile.getName().endsWith("page-definition.json")
						)
				),
				new SiteInitializerJsonSchemaFileProvider("utility-page"),
				new SiteInitializerJsonSchemaFileProvider("journal-article",
						(virtualFile ->
								SiteInitializerUtil.isSiteInitializerFile(virtualFile) &&
										(LiferayFileUtil.getParent(virtualFile, "journal-articles") != null) &&
										(!virtualFile.getName().endsWith(".metadata.json"))
						)
				)
		);


		//TODO: Fragment Composition Definition --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L840
		//TODO: Object Definitions --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L1159
		//TODO: Blog Postings --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L1513
		//TODO: Data Definitions --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L1652
		//TODO: Depot Entries --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L1876
		//TODO: Knowledge Base Articles --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L2458
		//TODO: Knowledge Base Folder --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L2515
		//TODO: Layout Content --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L2720
		//TODO: Notification Templates --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L3025
		//TODO: Object Actions --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L3146
		//TODO: Object Entries --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L3203
		//TODO: Object Fields --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L3307
		//TODO: Object Folders --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L3371
		//TODO: Object Relationships --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L3407
		//TODO: Organizations --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L3465
		//TODO: SAP Entries --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L3720
		//TODO: Segment Entries --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L3771
		//TODO: Structured Content Folders --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L4005
		//TODO: SXP Blueprint --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L4044
		//TODO: User Groups --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L4233
		//TODO: Role Assignments --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L4273
		//TODO: Segment Experiences --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L4394
		//TODO: Taxonomy Categories --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L4553
		//TODO: User Group Users --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L4752
		//TODO: User Roles --> https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-initializer/site-initializer-extender/site-initializer-extender/src/main/java/com/liferay/site/initializer/extender/internal/BundleSiteInitializer.java#L4775

	}
}
