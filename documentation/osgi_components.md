OSGi components
===============

1. [Syntax Highlighting for BND files](#syntax-highlighting-for-bnd-files)
2. [Component annotation code completion](#component-annotation-code-completion)
3. [Implicit usage for Reference annotations](#implicit-usage-for-reference-annotations)
4. [Inspections of service class inheritance](#inspections-of-service-class-inheritance)
5. [Resolve configurationPid properties to configuration interfaces](#resolve-configurationpid-properties-to-configuration-interfaces)
6. [Inspection for Meta Configuration ID](#inspection-for-meta-configuration-id)
7. [Reference unbind lookup](#reference-unbind-lookup)
8. [Service Implementation inspections](service-implementation-inspections)
9. [Syntax Highlighting for config files](#syntax-highlighting-for-config-files)

Syntax Highlighting for BND files
----------------------------------

This plugin adds syntax highlighting and code completions for ```bnd.bnd``` files.

![bnd.bnd file](bnd.png "bnd.bnd file")

For the following situations an additional validation is provided:
* Bundle Versions are validated if they are valid versions (i.e. correct format, no invalid characters etc.)
* `Bundle-Activator` is being checked if it is a valid class and if the class inherits from `org.osgi.framework.BundleActivator`
* `Private-Package`, `Import-Package`, `Ignore-Package`, `Conditional-Package` and `Export-Package` are being validated if they point to a valid java package.
* `-plugin.bundle`, `-plugin.jsp`, `-plugin.npm`, `-plugin.resourcebundle`, `-plugin.sass`, `-plugin.service` and `-plugin.spring` are being validated if they point to a valid java class.
* `Liferay-JS-Config` and `Liferay-Configuration-Path` are being checked if they point to a valid file

Additionally, to the standard bnd headers the following Liferay keywords are detected as valid headers, too:

    -jsp, -sass, -liferay-service-xml, Liferay-Releng-Module-Group-Description, Liferay-Releng-Module-Group-Title,
    Liferay-Require-SchemaVersion, Liferay-Service, DynamicImport-Package, Liferay-Modules-Compat-Adapters
        
For several `bnd.bnd` instructions a basic documentation has been added, so that you can look up the meaning of the instructions.

![documentation lookup in bnd.bnd file](bnd_documentation.png "documentation lookup in bnd.bnd file")

It is possible to add comments in `bnd.bnd` files. Lines starting with `#` are shown as comments. You can
toggle line comments using `Ctrl+/`. 

![comment in bnd.bnd file](bnd_comment.png "comment in in bnd.bnd file")

Automatic formatting of `bnd.bnd` files is also possible. Indents and line breaks are formatted properly by pressing
`Ctrl-Shift-L`.

*Works for Liferay 7.x, works in IntelliJ Community Edition and Ultimate Edition*

Component annotation code completion
------------------------------------

This plugin adds basic code completions for the ```@Component``` annotations, so that known properties for Portlets and many other
Liferay components are provided. For several properties a quick documentation is provided (Ctrl-Q), so that
you can look up the meaning of those properties.

![Component annotation](component.png "Component annotation")

List of supported component classes:

    com.liferay.adaptive.media.handler.AMRequestHandler
    com.liferay.adaptive.media.image.counter.AMImageCounter
    com.liferay.adaptive.media.image.optimizer.AMImageOptimizer
    com.liferay.adaptive.media.image.scaler.AMImageScaler
    com.liferay.application.list.PanelApp
    com.liferay.application.list.PanelCategory
    com.liferay.asset.kernel.model.AssetRendererFactory
    com.liferay.asset.kernel.util.AssetEntryQueryProcessor
    com.liferay.asset.kernel.validator.AssetEntryValidator
    com.liferay.asset.kernel.validator.AssetEntryValidatorExclusionRule
    com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType
    com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor
    com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer
    com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRequestParameterRetriever
    com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderer
    com.liferay.dynamic.data.mapping.util.DDMDisplay
    com.liferay.dynamic.data.mapping.util.DDMStructurePermissionSupport
    com.liferay.dynamic.data.mapping.util.DDMTemplatePermissionSupport
    com.liferay.expando.kernel.model.CustomAttributesDisplay
    com.liferay.exportimport.content.processor.ExportImportContentProcessor
    com.liferay.exportimport.kernel.controller.ExportImportController
    com.liferay.exportimport.kernel.lar.PortletDataHandler
    com.liferay.exportimport.kernel.lar.StagedModelDataHandler
    com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor
    com.liferay.exportimport.resources.importer.portlet.preferences.PortletPreferencesTranslator
    com.liferay.frontend.image.editor.capability.ImageEditorCapability
    com.liferay.frontend.taglib.form.navigator.context.FormNavigatorContextProvider
    com.liferay.item.selector.ItemSelectorView
    com.liferay.knowledge.base.web.internal.selector.KBArticleSelector
    com.liferay.mentions.matcher.MentionsMatcher
    com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener
    com.liferay.portal.deploy.hot.CustomJspBag
    com.liferay.portal.editor.configuration.EditorOptionsProvider
    com.liferay.portal.kernel.atom.AtomCollectionAdapter
    com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor
    com.liferay.portal.kernel.cache.configurator.PortalCacheConfiguratorSettings
    com.liferay.portal.kernel.captcha.Captcha
    com.liferay.portal.kernel.editor.configuration.EditorConfigContributor
    com.liferay.portal.kernel.events.LifecycleAction
    com.liferay.portal.kernel.messaging.Destination
    com.liferay.portal.kernel.messaging.MessageListener
    com.liferay.portal.kernel.model.ModelListener
    com.liferay.portal.kernel.model.LayoutTypeController
    com.liferay.portal.kernel.notifications.UserNotificationDefinition
    com.liferay.portal.kernel.notifications.UserNotificationHandler
    com.liferay.portal.kernel.portlet.AddPortletProvider
    com.liferay.portal.kernel.portlet.BrowsePortletProvider
    com.liferay.portal.kernel.portlet.ConfigurationAction
    com.liferay.portal.kernel.portlet.EditPortletProvider
    com.liferay.portal.kernel.portlet.FriendlyURLMapper
    com.liferay.portal.kernel.portlet.ManagePortletProvider
    com.liferay.portal.kernel.portlet.PortletLayoutFinder
    com.liferay.portal.kernel.portlet.PortletLayoutListener
    com.liferay.portal.kernel.portlet.PreviewPortletProvider
    com.liferay.portal.kernel.portlet.ViewPortletProvider
    com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand
    com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand
    com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand
    com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon
    com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor
    com.liferay.portal.kernel.poller.PollerProcessor
    com.liferay.portal.kernel.repository.RepositoryFactory
    com.liferay.portal.kernel.scheduler.messaging.SchedulerEventMessageListener
    com.liferay.portal.kernel.search.Indexer
    com.liferay.portal.kernel.search.IndexerPostProcessor
    com.liferay.portal.kernel.search.IndexSearcher
    com.liferay.portal.kernel.search.IndexWriter
    com.liferay.portal.kernel.search.hits.HitsProcessor
    com.liferay.portal.kernel.search.suggest.QuerySuggester
    com.liferay.portal.kernel.security.auth.Authenticator
    com.liferay.portal.kernel.security.auth.AuthFailure
    com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker
    com.liferay.portal.kernel.security.permission.PermissionUpdateHandler
    com.liferay.portal.kernel.security.permission.ResourcePermissionChecker
    com.liferay.portal.kernel.servlet.taglib.DynamicInclude
    com.liferay.portal.kernel.servlet.taglib.TagDynamicIdFactory
    com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorCategory
    com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry
    com.liferay.portal.kernel.social.SocialActivityManager
    com.liferay.portal.kernel.struts.StrutsAction
    com.liferay.portal.kernel.struts.StrutsPortletAction
    com.liferay.portal.kernel.template.TemplateContextContributor
    com.liferay.portal.kernel.template.TemplateHandler
    com.liferay.portal.kernel.templateparser.TransformerListener
    com.liferay.portal.kernel.trash.TrashHandler
    com.liferay.portal.kernel.workflow.WorkflowEngineManager
    com.liferay.portal.kernel.workflow.WorkflowHandler
    com.liferay.portal.kernel.upgrade.UpgradeStep
    com.liferay.portal.kernel.util.ResourceBundleLoader
    com.liferay.portal.kernel.webdav.WebDAVStorage
    com.liferay.portal.language.LanguageResources
    com.liferay.portal.output.stream.container.OutputStreamContainerFactory
    com.liferay.portal.search.analysis.FieldQueryBuilderFactory
    com.liferay.portal.search.buffer.IndexerRequestBufferExecutor
    com.liferay.portal.search.buffer.IndexerRequestBufferOverflowHandler
    com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchConnection
    com.liferay.portal.security.ldap.configuration.ConfigurationProvider
    com.liferay.portal.security.sso.openid.OpenIdProviderRegistry
    com.liferay.portal.struts.FindActionHelper
    com.liferay.portal.template.TemplateResourceParser
    com.liferay.portal.template.TemplateManager
    com.liferay.portal.verify.VerifyProcess
    com.liferay.portal.workflow.kaleo.definition.parser.NodeValidator
    com.liferay.portal.workflow.kaleo.runtime.assignment.TaskAssignmentSelector
    com.liferay.portal.workflow.kaleo.runtime.notification.NotificationSender
    com.liferay.portal.workflow.kaleo.runtime.notification.recipient.NotificationRecipientBuilder
    com.liferay.portlet.documentlibrary.store.Store
    com.liferay.portlet.documentlibrary.store.StoreWrapper
    com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry
    com.liferay.push.notifications.sender.PushNotificationsSender
    com.liferay.social.kernel.model.SocialActivityInterpreter
    com.liferay.social.kernel.model.SocialRequestInterpreter
    com.liferay.sync.internal.jsonws.SyncDLObject
    com.liferay.wiki.importer.WikiImporter
    java.lang.Object
    java.util.ResourceBundle
    javax.management.DynamicMBean
    javax.portlet.Portlet
    javax.portlet.filter.PortletFilter
    javax.servlet.Filter
    javax.servlet.Servlet
    javax.websocket.Endpoint
    org.eclipse.osgi.service.urlconversion.URLConverter
    org.osgi.service.http.context.ServletContextHelper

*Works for Liferay 7.x, works in IntelliJ Community Edition and Ultimate Edition*

Implicit usage for Reference annotations
----------------------------------------

Injected component references using the ```@Reference``` annotation are marked as "unused" by IntelliJ by default.
This plugin tells IntelliJ that those are injected references, so that they are actually "written" somehow, and
so they are not "unused".

List of supported annotations:
    
    org.osgi.service.component.annotations.Reference
    org.osgi.service.component.annotations.Activate
    org.osgi.service.component.annotations.Deactivate
    org.osgi.service.component.annotations.Modified
    com.liferay.portal.spring.extender.service.ServiceReference
    com.liferay.portal.kernel.bean.BeanReference
    com.liferay.arquillian.containter.remote.enricher.Inject
    com.liferay.arquillian.portal.annotation.PortalURL
    org.jboss.arquillian.core.api.annotation.Inject

*Works for Liferay 7.x, works in IntelliJ Community Edition and Ultimate Edition*

Inspections of service class inheritance
----------------------------------------

To implement an OSGi service the class should inherit from the given service class.

An inspection checks if these conditions are given and offers a quick fix.

![OSGi inspection](osgi_inspection.png "OSGi inspection")

*Works for Liferay 7.x, works in IntelliJ Community Edition and Ultimate Edition*

Resolve configurationPid properties to configuration interfaces
---------------------------------------------------------------

For classes using configurations you can use a `configurationPid` OSGi property. The plugin is 
able to resolve those IDs against configuration interfaces, so you can resolve or code complete
those properties.

Inspection for Meta Configuration ID
------------------------------------

According to the Liferay documentation the ID of a Meta Configuration must match
the full qualified classname of the Interface class. An Inspection checks if this is the
case and offers a Quick Fix to rename the ID.

Reference unbind lookup
-----------------------

When using `@Reference` annotations for a method, you can specify which method to call
when the reference is being removed by using the `unbind` property.

The plugin offers code completion for the `unbind` property to look up method names
in the current class.

```java
@Reference(
  target = "(javax.portlet.name=" + NotificationsPortletKeys.NOTIFICATIONS + ")",
  unbind = "unsetPanelApp"
)
protected void setPanelApp(PanelApp panelApp) {
  _panelApp = panelApp;
}

protected void unsetPanelApp(PanelApp panelApp) {
	_panelApp = null;
}
```

Service Implementation inspections
----------------------------------

For Service Builder Implementations the following inspections are provided:

* `setUserId()` should not be used in `update` methods in `LocalServiceImpl` classes (avoid unexpected permission expansions)

Syntax Highlighting for config files
------------------------------------

OSGi configurations can be exported into `.config` files. 

This plugin offers syntax highlighting for those files. OSGi `.config` files are detected automatically if they
are placed in a `osgi/configs` folder or if the folder is referenced by `Liferay-Configuration-Path`
in a `bnd.bnd` file. For `.config` files located elsewhere you can assign the file type manually.

If arrays are used in the `.config` files you will get bracket matching and folding support, too.

```LiferayConfig
logLevel="warn"
checkInterval=I"1"
addDefaultStructures=B"false"
childrenTypes=[ \
"Foo", \
"Bar", \
"Baz" \
]
```
