package de.dm.intellij.liferay.workflow;

import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LiferayWorkflowContextVariablesUtil {

    public static final Collection<String> WORKFLOW_NAMESPACES = Arrays.asList(
            "urn:liferay.com:liferay-workflow_6.1.0",
            "urn:liferay.com:liferay-workflow_6.2.0",
            "urn:liferay.com:liferay-workflow_7.0.0",
            "urn:liferay.com:liferay-workflow_7.1.0",
            "urn:liferay.com:liferay-workflow_7.2.0",
            "urn:liferay.com:liferay-workflow_7.3.0",
            "urn:liferay.com:liferay-workflow_7.4.0"
    );

    public static final String WORKFLOW_SCRIPT_TAG = "script";

    public static final String WORKFLOW_TEMPLATE_TAG = "template";

    public static final Map<String, String> WORKFLOW_SCRIPT_CONTEXT_VARIABLES = new HashMap<>();
    public static final Map<String, String> WORKFLOW_TEMPLATE_CONTEXT_VARIABLES = new HashMap<>();
    
    private static final Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> WORKFLOW_SCRIPT_OUTPUT_VARIABLES = new HashMap<>();

    private static final Map<String, String> WORKFLOW_COMMON_CONTEXT_VARIABLES = new HashMap<>();

    //workflowContext filled from here https://github.com/liferay/liferay-portal/blob/master/portal-kernel/src/com/liferay/portal/kernel/workflow/WorkflowHandlerRegistryUtil.java#L137
    //additional script context variables from here https://github.com/liferay/liferay-portal/blob/master/modules/apps/portal-workflow/portal-workflow-kaleo-runtime-scripting-impl/src/main/java/com/liferay/portal/workflow/kaleo/runtime/scripting/internal/util/ScriptingContextBuilderImpl.java
    static {
        WORKFLOW_COMMON_CONTEXT_VARIABLES.put("companyId", "java.lang.String");
        WORKFLOW_COMMON_CONTEXT_VARIABLES.put("entryClassName", "java.lang.String");
        WORKFLOW_COMMON_CONTEXT_VARIABLES.put("entryClassPK", "java.lang.String");
        WORKFLOW_COMMON_CONTEXT_VARIABLES.put("entryType", "java.lang.String");
        WORKFLOW_COMMON_CONTEXT_VARIABLES.put("kaleoInstanceToken", "com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken");
        WORKFLOW_COMMON_CONTEXT_VARIABLES.put("kaleoTaskInstanceToken", "com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken");
        WORKFLOW_COMMON_CONTEXT_VARIABLES.put("kaleoTimerInstanceToken", "com.liferay.portal.workflow.kaleo.model.KaleoTimerInstanceToken");
        WORKFLOW_COMMON_CONTEXT_VARIABLES.put("groupId", "java.lang.String");
        WORKFLOW_COMMON_CONTEXT_VARIABLES.put("serviceContext", "com.liferay.portal.kernel.service.ServiceContext");
        WORKFLOW_COMMON_CONTEXT_VARIABLES.put("taskComments", "java.lang.String");
        WORKFLOW_COMMON_CONTEXT_VARIABLES.put("taskName", "java.lang.String");
        WORKFLOW_COMMON_CONTEXT_VARIABLES.put("userId", "java.lang.Long");
        WORKFLOW_COMMON_CONTEXT_VARIABLES.put("workflowContext", "java.util.Map<java.lang.String, java.io.Serializable>");
        WORKFLOW_COMMON_CONTEXT_VARIABLES.put("workflowTaskAssignees", "java.util.List<com.liferay.portal.kernel.workflow.WorkflowTaskAssignee>");
    }

    static {
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.putAll(WORKFLOW_COMMON_CONTEXT_VARIABLES);
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("arrayUtil", "com.liferay.portal.kernel.util.ArrayUtil_IW");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("auditMessageFactoryUtil", "com.liferay.portal.security.audit.internal.AuditMessageFactoryUtil");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("auditRouterUtil", "com.liferay.portal.kernel.audit.AuditRouter");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("browserSniffer", "com.liferay.portal.servlet.BrowserSniffer");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("calendarFactory", "com.liferay.portal.util.CalendarFactory");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("commonPermission", "com.liferay.portal.service.permission.CommonPermission");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("dateFormats", "com.liferay.portal.kernel.util.FastDateFormatFactory");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("dateFormatFactory", "com.liferay.portal.kernel.util.FastDateFormatFactory");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("dateUtil", "com.liferay.portal.kernel.util.DateUtil_IW");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("expandoColumnLocalService", "com.liferay.portlet.expando.service.ExpandoColumnLocalService");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("expandoRowLocalService", "com.liferay.portlet.expando.service.ExpandoRowLocalService");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("expandoTableLocalService", "com.liferay.portlet.expando.service.ExpandoTableLocalService");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("expandoValueLocalService", "com.liferay.portlet.expando.service.ExpandoValueLocalService");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("enumUtil", "freemarker.template.TemplateHashModel");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("freeMarkerPortletPreferences", "com.liferay.portal.template.TemplatePortletPreferences");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("getterUtil", "com.liferay.portal.kernel.util.GetterUtil_IW");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("groupPermission", "com.liferay.portal.service.permission.GroupPermission");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("htmlUtil", "com.liferay.portal.util.HtmlUtil");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("httpUtil", "com.liferay.portal.kernel.util.Http");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("httpUtilUnsafe", "com.liferay.portal.kernel.util.Http");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("languageUtil", "com.liferay.portal.language.LanguageUtil");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("layoutPermission", "com.liferay.portal.service.permission.LayoutPermission");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("localeUtil", "com.liferay.portal.kernel.util.LocaleUtil");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("locationPermission", "com.liferay.portal.service.permission.OrganizationPermission");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("imageToken", "com.liferay.portal.kernel.webserver.WebServerServletToken");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("imageToolUtil", "com.liferay.portal.image.ImageToolUtil");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("jsonFactoryUtil", "com.liferay.portal.json.JSONFactoryUtil");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("objectUtil", "com.liferay.portal.template.freemarker.internal.LiferayObjectConstructor");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("organizationPermission", "com.liferay.portal.service.permission.OrganizationPermission");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("paramUtil", "com.liferay.portal.kernel.util.ParamUtil_IW");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("passwordPolicyPermission", "com.liferay.portal.service.permission.PasswordPolicyPermission");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("portal", "com.liferay.portal.util.Portal");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("portalPermission", "com.liferay.portal.service.permission.PortalPermission");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("portalUtil", "com.liferay.portal.kernel.util.PortalUtil");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("propsUtil", "com.liferay.portal.util.PropsUtil");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("portletModeFactory", "com.liferay.portal.kernel.portlet.PortletModeFactory_IW");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("portletProviderAction", "java.util.HashMap");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("portletPermission", "com.liferay.portal.service.permission.PortletPermission");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("portletURLFactory", "com.liferay.portal.kernel.portlet.PortletURLFactory");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("prefsPropsUtil", "com.liferay.portal.util.PrefsPropsUtil");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("random", "java.util.Random");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("rolePermission", "com.liferay.portal.service.permission.RolePermission");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("saxReaderUtil", "com.liferay.portal.kernel.xml.SAXReader");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("serviceLocator", "com.liferay.portal.template.ServiceLocator");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("sessionClicks", "com.liferay.portal.kernel.util.SessionClicks_IW");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("staticFieldGetter", "com.liferay.portal.kernel.util.StaticFieldGetter");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("staticUtil", "freemarker.ext.beans.StaticModels");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("timeZoneUtil", "com.liferay.portal.kernel.util.TimeZoneUtil_IW");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("unicodeFormatter", "com.liferay.portal.kernel.util.UnicodeFormatter_IW");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("unicodeLanguageUtil", "com.liferay.portal.language.UnicodeLanguageUtil");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("urlCodec", "com.liferay.portal.kernel.util.URLCodec_IW");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("userPermission", "com.liferay.portal.service.permission.UserPermission");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("userName", "java.lang.String");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("utilLocator", "com.liferay.portal.template.UtilLocator");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("validator", "com.liferay.portal.kernel.util.Validator_IW");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("windowStateFactory", "com.liferay.portal.kernel.portlet.WindowStateFactory_IW");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("webServerToken", "com.liferay.portal.webserver.WebServerServletToken");
    }

    //workflowContext filled from here https://github.com/liferay/liferay-portal/blob/master/portal-kernel/src/com/liferay/portal/kernel/workflow/WorkflowHandlerRegistryUtil.java#L137
    //additional template context variables from here https://github.com/liferay/liferay-portal/blob/master/modules/apps/portal-workflow/portal-workflow-kaleo-runtime-impl/src/main/java/com/liferay/portal/workflow/kaleo/runtime/internal/notification/TemplateNotificationMessageGenerator.java
    //documentation see https://github.com/rbohl/liferay-docs/blob/workflow-7.3-notificationTemplateVariables_LRDOCS-7311/en/developer/reference/articles/02-workflow/05-notification-templates.markdown
    static {
        WORKFLOW_TEMPLATE_CONTEXT_VARIABLES.putAll(WORKFLOW_COMMON_CONTEXT_VARIABLES);
        WORKFLOW_TEMPLATE_CONTEXT_VARIABLES.put("userName", "java.lang.String");
    }

    static {
        WORKFLOW_SCRIPT_OUTPUT_VARIABLES.put("scripted-assignment", Arrays.asList(
                new AbstractMap.SimpleEntry<>("workflowContext", "java.util.Map<java.lang.String, java.lang.Serializable>"),
                new AbstractMap.SimpleEntry<>("roles", "java.util.List<com.liferay.portal.kernel.model.Role>"),
                new AbstractMap.SimpleEntry<>("user", "com.liferay.portal.kernel.model.User")
        ));

        WORKFLOW_SCRIPT_OUTPUT_VARIABLES.put("scripted-receipient", Arrays.asList(
                new AbstractMap.SimpleEntry<>("workflowContext", "java.util.Map<java.lang.String, java.lang.Serializable>"),
                new AbstractMap.SimpleEntry<>("roles", "java.util.List<com.liferay.portal.kernel.model.Role>"),
                new AbstractMap.SimpleEntry<>("user", "com.liferay.portal.kernel.model.User")
        ));

        WORKFLOW_SCRIPT_OUTPUT_VARIABLES.put("condition", Arrays.asList(
                new AbstractMap.SimpleEntry<>("workflowContext", "java.util.Map<java.lang.String, java.lang.Serializable>"),
                new AbstractMap.SimpleEntry<>("returnValue", "java.lang.String")
        ));
    }

    public static boolean isWorkflowScriptTag(@NotNull XmlTag xmlTag) {
        return WORKFLOW_NAMESPACES.contains(xmlTag.getNamespace()) && WORKFLOW_SCRIPT_TAG.equals(xmlTag.getName());
    }

    public static boolean isWorkflowTemplateTag(@NotNull XmlTag xmlTag) {
        return WORKFLOW_NAMESPACES.contains(xmlTag.getNamespace()) && WORKFLOW_TEMPLATE_TAG.equals(xmlTag.getName());
    }

    public static Map<String, String> getWorkflowScriptVariables(@NotNull XmlTag xmlTag) {
        Map<String, String> result = new HashMap<>(WORKFLOW_SCRIPT_CONTEXT_VARIABLES);

        result.putAll(getOutputVariables(xmlTag));

        return result;
    }

    private static Map<String, String> getOutputVariables(@NotNull XmlTag xmlTag) {
        Map<String, String> result = new HashMap<>();

        XmlTag parentTag = xmlTag.getParentTag();

        if (parentTag != null && WORKFLOW_SCRIPT_OUTPUT_VARIABLES.containsKey(parentTag.getName())) {
            for (AbstractMap.SimpleEntry<String, String> entry : WORKFLOW_SCRIPT_OUTPUT_VARIABLES.get(parentTag.getName())) {
                result.put(entry.getKey(), entry.getValue());
            }
        }

        return result;
    }
}
