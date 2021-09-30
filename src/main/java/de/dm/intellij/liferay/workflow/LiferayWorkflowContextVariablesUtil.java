package de.dm.intellij.liferay.workflow;

import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;

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

    //workflowContext filled from here https://github.com/liferay/liferay-portal/blob/master/portal-kernel/src/com/liferay/portal/kernel/workflow/WorkflowHandlerRegistryUtil.java#L137
    //additional script context variables from here https://github.com/liferay/liferay-portal/blob/master/modules/apps/portal-workflow/portal-workflow-kaleo-runtime-scripting-impl/src/main/java/com/liferay/portal/workflow/kaleo/runtime/scripting/internal/util/ScriptingContextBuilderImpl.java
    static {
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("companyId", "java.lang.String");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("entryClassName", "java.lang.String");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("entryClassPK", "java.lang.String");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("entryType", "java.lang.String");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("groupId", "java.lang.String");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("serviceContext", "com.liferay.portal.kernel.service.ServiceContext");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("taskComments", "java.lang.String");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("userId", "java.lang.String");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("kaleoInstanceToken", "com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("workflowContext", "java.util.Map<java.lang.String, java.io.Serializable>");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("kaleoTaskInstanceToken", "com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("taskName", "java.lang.String");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("workflowTaskAssignees", "java.util.List<com.liferay.portal.kernel.workflow.WorkflowTaskAssignee>");
        WORKFLOW_SCRIPT_CONTEXT_VARIABLES.put("kaleoTimerInstanceToken", "com.liferay.portal.workflow.kaleo.model.KaleoTimerInstanceToken");
    }

    //workflowContext filled from here https://github.com/liferay/liferay-portal/blob/master/portal-kernel/src/com/liferay/portal/kernel/workflow/WorkflowHandlerRegistryUtil.java#L137
    //additional template context variables from here https://github.com/liferay/liferay-portal/blob/master/modules/apps/portal-workflow/portal-workflow-kaleo-runtime-impl/src/main/java/com/liferay/portal/workflow/kaleo/runtime/internal/notification/TemplateNotificationMessageGenerator.java
    static {
        WORKFLOW_TEMPLATE_CONTEXT_VARIABLES.putAll(WORKFLOW_SCRIPT_CONTEXT_VARIABLES);
        WORKFLOW_TEMPLATE_CONTEXT_VARIABLES.put("userName", "java.lang.String");
    }

    public static boolean isWorkflowScriptTag(@NotNull XmlTag xmlTag) {
        return WORKFLOW_NAMESPACES.contains(xmlTag.getNamespace()) && WORKFLOW_SCRIPT_TAG.equals(xmlTag.getName());
    }

    public static boolean isWorkflowTemplateTag(@NotNull XmlTag xmlTag) {
        return WORKFLOW_NAMESPACES.contains(xmlTag.getNamespace()) && WORKFLOW_TEMPLATE_TAG.equals(xmlTag.getName());
    }

}
