package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PatternCondition;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiJavaPatterns;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentPropertiesCompletionContributor extends CompletionContributor {

    //see https://dev.liferay.com/develop/reference/-/knowledge_base/7-0/portlet-descriptor-to-osgi-service-property-map
    private static Map<String, String[][]> COMPONENT_PROPERTIES = new HashMap<String, String[][]>();
    static {
        COMPONENT_PROPERTIES.put("javax.portlet.Portlet",
                new String[][] {
                        {"javax.portlet.description", "String", ""},
                        {"javax.portlet.name", "String", ""},
                        {"javax.portlet.display-name", "String", ""},
                        {"javax.portlet.init-param", "", ""},
                        {"javax.portlet.expiration-cache", "int", ""},
                        {"javax.portlet.mime-type", "String", ""},
                        {"javax.portlet.portlet-mode", "String", ""},
                        {"javax.portlet.window-state", "String", ""},
                        {"javax.portlet.resource-bundle", "String", ""},
                        {"javax.portlet.info.title", "String", ""},
                        {"javax.portlet.info.short-title", "String", ""},
                        {"javax.portlet.info.keywords", "String", ""},
                        {"javax.portlet.preferences", "String", ""},
                        {"javax.portlet.security-role-ref", "String", ""},
                        {"javax.portlet.supported-processing-event", "String", ""},
                        {"javax.portlet.supported-publishing-event", "String", ""},
                        {"javax.portlet.supported-public-render-parameter", "String", ""},
                        {"javax.portlet.supported-locale", "String", ""},
                        {"com.liferay.portlet.icon", "String", ""},
                        {"com.liferay.portlet.virtual-path", "String", ""},
                        {"com.liferay.portlet.struts-path", "String", ""},
                        {"com.liferay.portlet.parent-struts-path", "String", ""},
                        {"com.liferay.portlet.configuration-path", "String", ""},
                        {"com.liferay.portlet.friendly-url-mapping", "String", ""},
                        {"com.liferay.portlet.friendly-url-routes", "String", ""},
                        {"com.liferay.portlet.control-panel-entry-category", "String", ""},
                        {"com.liferay.portlet.control-panel-entry-weight", "double", ""},
                        {"com.liferay.portlet.preferences-company-wide", "boolean", ""},
                        {"com.liferay.portlet.preferences-unique-per-layout", "boolean", ""},
                        {"com.liferay.portlet.preferences-owned-by-group", "boolean", ""},
                        {"com.liferay.portlet.use-default-template", "boolean", ""},
                        {"com.liferay.portlet.show-portlet-access-denied", "boolean", ""},
                        {"com.liferay.portlet.show-portlet-inactive", "boolean", ""},
                        {"com.liferay.portlet.action-url-redirect", "boolean", ""},
                        {"com.liferay.portlet.restore-current-view", "boolean", ""},
                        {"com.liferay.portlet.maximize-edit", "boolean", ""},
                        {"com.liferay.portlet.maximize-help", "boolean", ""},
                        {"com.liferay.portlet.pop-up-print", "boolean", ""},
                        {"com.liferay.portlet.layout-cacheable", "boolean", ""},
                        {"com.liferay.portlet.instanceable", "boolean", ""},
                        {"com.liferay.portlet.remoteable", "boolean", ""},
                        {"com.liferay.portlet.scopeable", "boolean", ""},
                        {"com.liferay.portlet.single-page-application", "boolean", ""},
                        {"com.liferay.portlet.user-principal-strategy", "String", ""},
                        {"com.liferay.portlet.private-request-attributes", "boolean", ""},
                        {"com.liferay.portlet.private-session-attributes", "boolean", ""},
                        {"com.liferay.portlet.autopropagated-parameters", "String", ""},
                        {"com.liferay.portlet.requires-namespaced-parameters", "boolean", ""},
                        {"com.liferay.portlet.action-timeout", "int", ""},
                        {"com.liferay.portlet.render-timeout", "int", ""},
                        {"com.liferay.portlet.render-weight", "int", ""},
                        {"com.liferay.portlet.ajaxable", "boolean", ""},
                        {"com.liferay.portlet.header-portal-css", "String", ""},
                        {"com.liferay.portlet.header-portlet-css", "String", ""},
                        {"com.liferay.portlet.header-portal-javascript", "String", ""},
                        {"com.liferay.portlet.header-portlet-javascript", "String", ""},
                        {"com.liferay.portlet.footer-portal-css", "String", ""},
                        {"com.liferay.portlet.footer-portlet-css", "String", ""},
                        {"com.liferay.portlet.footer-portal-javascript", "String", ""},
                        {"com.liferay.portlet.footer-portlet-javascript", "String", ""},
                        {"com.liferay.portlet.css-class-wrapper", "String", ""},
                        {"com.liferay.portlet.facebook-integration", "String", ""},
                        {"com.liferay.portlet.add-default-resource", "boolean", ""},
                        {"com.liferay.portlet.system", "boolean", ""},
                        {"com.liferay.portlet.active", "boolean", ""},
                        {"com.liferay.portlet.display-category", "String", ""}
                }
        );

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/mvc-action-command
        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand",
                new String[][]{
                        {"javax.portlet.name", "String", ""},
                        {"mvc.command.name", "String", ""}
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/mvc-render-command
        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand",
                new String[][]{
                        {"javax.portlet.name", "String", ""},
                        {"mvc.command.name", "String", ""}
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/mvc-resource-command
        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand",
                new String[][]{
                        {"javax.portlet.name", "String", ""},
                        {"mvc.command.name", "String", ""}
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/making-urls-friendlier
        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.portlet.FriendlyURLMapper",
                new String[][]{
                        {"javax.portlet.name", "String", ""},
                        {"com.liferay.portlet.friendly-url-routes", "String", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/configuring-your-admin-apps-actions-menu
        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon",
                new String[][]{
                        {"javax.portlet.name", "String", ""},
                        {"path", "String", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/overriding-language-keys
        COMPONENT_PROPERTIES.put("java.util.ResourceBundle",
                new String[][]{
                        {"language.id", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.template.TemplateHandler",
                new String[][]{
                        {"javax.portlet.name", "String", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/liferay-websocket-whiteboard
        COMPONENT_PROPERTIES.put("javax.websocket.Endpoint",
                new String[][]{
                        {"org.osgi.http.websocket.endpoint.path", "String", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/rendering-an-asset
        COMPONENT_PROPERTIES.put("com.liferay.asset.kernel.model.AssetRendererFactory",
                new String[][]{
                        {"javax.portlet.name", "String", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/liferays-workflow-framework
        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.workflow.WorkflowHandler",
                new String[][]{
                        {"model.class.name", "String", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/data-handlers
        COMPONENT_PROPERTIES.put("com.liferay.exportimport.kernel.lar.PortletDataHandler",
                new String[][]{
                        {"javax.portlet.name", "String", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/implementing-configuration-actions
        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.portlet.ConfigurationAction",
                new String[][]{
                        {"javax.portlet.name", "String", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/creating-custom-item-selector-views
        COMPONENT_PROPERTIES.put("com.liferay.item.selector.ItemSelectorView",
                new String[][]{
                        {"item.selector.view.order", "Integer", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/changing-adaptive-medias-image-scaling
        COMPONENT_PROPERTIES.put("com.liferay.adaptive.media.image.scaler.AMImageScaler",
                new String[][]{
                        {"mime.type", "String", ""},
                        {"service.ranking", "Integer", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/creating-form-field-types
        COMPONENT_PROPERTIES.put("com.liferay.dynamic.data.mapping.model.DDMFormFieldType",
                new String[][]{
                        {"ddm.form.field.type.display.order", "Integer", ""},
                        {"ddm.form.field.type.icon", "String", ""},
                        {"ddm.form.field.type.js.class", "String", ""},
                        {"ddm.form.field.type.js.module", "String", ""},
                        {"ddm.form.field.type.label", "String", ""},
                        {"ddm.form.field.type.name", "String", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/creating-form-field-types
        COMPONENT_PROPERTIES.put("com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderer",
                new String[][]{
                        {"ddm.form.field.type.name", "String", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/introduction-to-liferay-search
        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.search.IndexerPostProcessor",
                new String[][]{
                        {"indexer.class.name", "String", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/introduction-to-liferay-search
        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.search.hits.HitsProcessor",
                new String[][]{
                        {"sort.order", "int", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/introduction-to-liferay-search
        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.search.IndexSearcher",
                new String[][]{
                        {"search.engine.impl", "String", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/introduction-to-liferay-search
        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.search.suggest.QuerySuggester",
                new String[][]{
                        {"search.engine.impl", "String", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/introduction-to-liferay-search
        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.search.IndexWriter",
                new String[][]{
                        {"search.engine.impl", "String", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/customizing-liferay-search
        COMPONENT_PROPERTIES.put("com.liferay.portal.search.buffer.IndexerRequestBufferOverflowHandler",
                new String[][]{
                        {"mode", "String", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/password-based-authentication-pipelines
        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.security.auth.Authenticator",
                new String[][]{
                        {"key", "String", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/modifying-an-editors-configuration
        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.editor.configuration.EditorConfigContributor",
                new String[][]{
                        {"javax.portlet.name", "String", ""},
                        {"editor.config.key", "String", ""},
                        {"editor.name", "String", ""},
                        {"service.ranking", "Integer", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/form-navigator
        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorCategory",
                new String[][]{
                        {"form.navigator.category.order", "Integer", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/form-navigator
        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry",
                new String[][]{
                        {"form.navigator.entry.order", "Integer", ""},
                        {"service.ranking", "Integer", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/creating-form-navigator-contexts
        COMPONENT_PROPERTIES.put("com.liferay.frontend.taglib.form.navigator.context.FormNavigatorContextProvider",
                new String[][]{
                        {"formNavigatorId", "String", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/customizing-the-product-menu
        COMPONENT_PROPERTIES.put("com.liferay.application.list.PanelCategory",
                new String[][]{
                        {"panel.category.key", "String", ""},
                        {"panel.category.order", "Integer", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/customizing-the-product-menu
        COMPONENT_PROPERTIES.put("com.liferay.application.list.PanelApp",
                new String[][]{
                        {"panel.category.key", "String", ""},
                        {"panel.app.order", "Integer", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/customizing-the-control-menu
        COMPONENT_PROPERTIES.put("com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry",
                new String[][]{
                        {"product.navigation.control.menu.category.key", "String", ""},
                        {"product.navigation.control.menu.category.order", "Integer", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/providing-the-user-personal-bar
        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.portlet.ViewPortletProvider",
                new String[][]{
                        {"model.class.name", "String", ""},
                        {"service.ranking", "Integer", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/providing-the-user-personal-bar
        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.portlet.EditPortletProvider",
                new String[][]{
                        {"model.class.name", "String", ""},
                        {"service.ranking", "Integer", ""},
                });

        //see https://dev.liferay.com/de/develop/tutorials/-/knowledge_base/7-0/providing-the-user-personal-bar
        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.portlet.AddPortletProvider",
                new String[][]{
                        {"model.class.name", "String", ""},
                        {"service.ranking", "Integer", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.language.LanguageResources",
                new String[][]{
                        {"language.id", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.editor.configuration.EditorOptionsProvider",
                new String[][]{
                        {"javax.portlet.name", "String", ""},
                        {"editor.config.key", "String", ""},
                        {"editor.name", "String", ""},
                        {"service.ranking", "Integer", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.exportimport.kernel.controller.ExportImportController",
                new String[][]{
                        {"model.class.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.poller.PollerProcessor",
                new String[][]{
                        {"javax.portlet.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.social.kernel.model.SocialActivityInterpreter",
                new String[][]{
                        {"javax.portlet.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("javax.portlet.filter.PortletFilter",
                new String[][]{
                        {"preinitialized.filter", "boolean", ""},
                        {"service.id", "String", ""},
                        {"javax.portlet.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker",
                new String[][]{
                        {"model.class.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.model.LayoutTypeController",
                new String[][]{
                        {"layout.type", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.struts.StrutsAction",
                new String[][]{
                        {"path", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.struts.StrutsPortletAction",
                new String[][]{
                        {"path", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.upgrade.UpgradeStep",
                new String[][]{
                        {"upgrade.bundle.symbolic.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.social.kernel.model.SocialRequestInterpreter",
                new String[][]{
                        {"javax.portlet.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("javax.servlet.Filter",
                new String[][]{
                        {"after-filter", "String", ""},
                        {"before-filter", "String", ""},
                        {"dispatcher", "String", ""},
                        {"servlet-context-name", "String", ""},
                        {"servlet-filter-name", "String", ""},
                        {"url-pattern", "String", ""},
                        {"init.param", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.deploy.hot.CustomJspBag",
                new String[][]{
                        {"context.id", "String", ""},
                        {"context.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.security.permission.PermissionUpdateHandler",
                new String[][]{
                        {"model.class.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor",
                new String[][]{
                        {"background.task.executor.class.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.asset.kernel.validator.AssetEntryValidator",
                new String[][]{
                        {"model.class.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.portlet.PortletLayoutListener",
                new String[][]{
                        {"javax.portlet.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor",
                new String[][]{
                        {"javax.portlet.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor",
                new String[][]{
                        {"javax.portlet.name", "String", ""},
                        {"mvc.path", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.exportimport.content.processor.ExportImportContentProcessor",
                new String[][]{
                        {"model.class.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.exportimport.kernel.lar.StagedModelDataHandler",
                new String[][]{
                        {"javax.portlet.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.expando.kernel.model.CustomAttributesDisplay",
                new String[][]{
                        {"javax.portlet.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.security.permission.ResourcePermissionChecker",
                new String[][]{
                        {"resource.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.verify.VerifyProcess",
                new String[][]{
                        {"verify.process.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.atom.AtomCollectionAdapter",
                new String[][]{
                        {"model.class.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.cache.configurator.PortalCacheConfiguratorSettings",
                new String[][]{
                        {"portal.cache.manager.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.events.LifecycleAction",
                new String[][]{
                        {"key", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.templateparser.TransformerListener",
                new String[][]{
                        {"javax.portlet.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.trash.TrashHandler",
                new String[][]{
                        {"model.class.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.dynamic.data.mapping.util.DDMDisplay",
                new String[][]{
                        {"javax.portlet.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.notifications.UserNotificationDefinition",
                new String[][]{
                        {"javax.portlet.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.webdav.WebDAVStorage",
                new String[][]{
                        {"javax.portlet.name", "String", ""},
                        {"webdav.storage.token", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.social.SocialActivityManager",
                new String[][]{
                        {"model.class.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.search.analysis.FieldQueryBuilderFactory",
                new String[][]{
                        {"description.fields", "String", ""},
                        {"title.fields", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.search.buffer.IndexerRequestBufferExecutor",
                new String[][]{
                        {"buffered.execution.mode", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.template.TemplateResourceParser",
                new String[][]{
                        {"lang.type", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.template.TemplateManager",
                new String[][]{
                        {"language.type", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.scheduler.messaging.SchedulerEventMessageListener",
                new String[][]{
                        {"destination.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.messaging.MessageListener",
                new String[][]{
                        {"destination.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.messaging.Destination",
                new String[][]{
                        {"destination.name", "String", ""},
                });

        COMPONENT_PROPERTIES.put("com.liferay.portal.kernel.model.ModelListener",
                new String[][]{
                        {"service.ranking", "Integer", ""},
                });

        //OSGi default commands
        COMPONENT_PROPERTIES.put("java.lang.Object",
                new String[][]{
                        {"osgi.command.scope", "String", ""},
                        {"osgi.command.function", "String", ""},
                });
    }
    private Map<String, List<LookupElementBuilder>> KEYWORD_LOOKUPS = new HashMap<String, List<LookupElementBuilder>>();

    public ComponentPropertiesCompletionContributor() {
        for (Map.Entry<String, String[][]> entry : COMPONENT_PROPERTIES.entrySet()) {
            List<LookupElementBuilder> lookups = new ArrayList<LookupElementBuilder>();

            for (String[] keyword : entry.getValue()) {
                lookups.add(LookupElementBuilder.create(keyword[0]).withTypeText(keyword[1]).withIcon(Icons.LIFERAY_ICON));
            };
            KEYWORD_LOOKUPS.put(entry.getKey(), lookups);
        }

        extend(
                CompletionType.BASIC,
                PlatformPatterns
                        .psiElement().inside(PsiJavaPatterns.literalExpression())
                        .with(new PatternCondition<PsiElement>("pattern") {
                            @Override
                            public boolean accepts(@NotNull PsiElement psiJavaToken, ProcessingContext context) {
                                PsiArrayInitializerMemberValue psiArrayInitializerMemberValue = PsiTreeUtil.getParentOfType(psiJavaToken, PsiArrayInitializerMemberValue.class);

                                if (psiArrayInitializerMemberValue != null) {
                                    PsiNameValuePair psiNameValuePair = PsiTreeUtil.getParentOfType(psiArrayInitializerMemberValue, PsiNameValuePair.class);

                                    if (psiNameValuePair != null) {
                                        String name = psiNameValuePair.getName();

                                        if ("property".equals(name)) {
                                            PsiAnnotation psiAnnotation = PsiTreeUtil.getParentOfType(psiNameValuePair, PsiAnnotation.class);

                                            if (psiAnnotation != null) {
                                                String qualifiedName = psiAnnotation.getQualifiedName();
                                                if ("org.osgi.service.component.annotations.Component".equals(qualifiedName)) {
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }

                                return false;
                            }
                        })
                ,
                new CompletionProvider<CompletionParameters>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
                        String serviceClassName = getServiceClassName(parameters);
                        if (serviceClassName != null) {
                            List<LookupElementBuilder> lookups = KEYWORD_LOOKUPS.get(serviceClassName);
                            if (lookups != null) {
                                result.addAllElements(lookups);
                            }
                        }
                    }
                }

        );
    }

    private static String getServiceClassName(CompletionParameters parameters) {
        PsiElement originalPosition = parameters.getOriginalPosition();

        PsiAnnotationParameterList annotationParameterList = PsiTreeUtil.getParentOfType(originalPosition, PsiAnnotationParameterList.class);

        if (annotationParameterList != null) {
            for (PsiNameValuePair psiNameValuePair : PsiTreeUtil.getChildrenOfType(annotationParameterList, PsiNameValuePair.class)) {
                String name = psiNameValuePair.getName();

                if ("service".equals(name) ) {
                    PsiAnnotationMemberValue value = psiNameValuePair.getValue();

                    if (value instanceof PsiClassObjectAccessExpression) {
                        PsiClassObjectAccessExpression psiClassObjectAccessExpression = (PsiClassObjectAccessExpression)value;

                        PsiTypeElement operand = psiClassObjectAccessExpression.getOperand();

                        PsiJavaCodeReferenceElement innermostComponentReferenceElement = operand.getInnermostComponentReferenceElement();

                        if (innermostComponentReferenceElement != null) {
                            String serviceClassName = innermostComponentReferenceElement.getQualifiedName();

                            return serviceClassName;
                        }
                    }
                }
            }
        }

        return null;
    }
}
