package de.dm.intellij.liferay.language.groovy;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiType;
import com.intellij.psi.ResolveState;
import com.intellij.psi.impl.light.LightFieldBuilder;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlText;
import de.dm.intellij.liferay.workflow.LiferayWorkflowContextVariablesUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.lang.psi.impl.synthetic.GroovyScriptClass;
import org.jetbrains.plugins.groovy.lang.resolve.NonCodeMembersContributor;
import org.jetbrains.plugins.groovy.lang.resolve.ResolveUtil;

import java.util.HashMap;
import java.util.Map;

public class GroovyScriptingImplicitMembersContributor extends NonCodeMembersContributor {

    private static final Map<String, String> IMPLICIT_VARIABLES = new HashMap<>();
    static {
        IMPLICIT_VARIABLES.put("out", "java.io.PrintWriter");
        IMPLICIT_VARIABLES.put("actionRequest", "javax.portlet.ActionRequest");
        IMPLICIT_VARIABLES.put("actionResponse", "javax.portlet.ActionResponse");
        IMPLICIT_VARIABLES.put("portletConfig", "javax.portlet.PortletConfig");
        IMPLICIT_VARIABLES.put("portletContext", "javax.portlet.PortletContext");
        IMPLICIT_VARIABLES.put("portletRequest", "javax.portlet.PortletRequest");
        IMPLICIT_VARIABLES.put("portletResponse", "javax.portlet.PortletResponse");
        IMPLICIT_VARIABLES.put("preferences", "javax.portlet.PortletPreferences");
        IMPLICIT_VARIABLES.put("renderRequest", "javax.portlet.RenderRequest");
        IMPLICIT_VARIABLES.put("renderResponse", "javax.portlet.RenderResponse");
        IMPLICIT_VARIABLES.put("resourceRequest", "javax.portlet.ResourceRequest");
        IMPLICIT_VARIABLES.put("resourceResponse", "javax.portlet.ResourceResponse");
        IMPLICIT_VARIABLES.put("userInfo", "java.util.Map<String, String>");
    }

    @Override
    public void processDynamicElements(@NotNull PsiType qualifierType, PsiClass aClass, @NotNull PsiScopeProcessor processor, @NotNull PsiElement place, @NotNull ResolveState state) {
        String name = ResolveUtil.getNameHint(processor);

        if (isInsideWorkflowDefinitionScriptTag(place)) {
            PsiFile containingFile = place.getContainingFile();

            XmlTag xmlTag = PsiTreeUtil.getParentOfType(containingFile.getContext(), XmlTag.class);

            Map<String, String> scriptContextVariables = LiferayWorkflowContextVariablesUtil.getWorkflowScriptVariables(xmlTag);

            if (name == null) {
                for (Map.Entry<String, String> scriptContextVariable : scriptContextVariables.entrySet()) {
                    LightFieldBuilder lightField = new LightFieldBuilder(scriptContextVariable.getKey(), scriptContextVariable.getValue(), place);

                    boolean continueProcessing = processor.execute(lightField, state);

                    if (!continueProcessing) {
                        return;
                    }
                }
            } else {
                if (scriptContextVariables.containsKey(name)) {
                    String type = scriptContextVariables.get(name);

                    LightFieldBuilder lightField = new LightFieldBuilder(name, type, place);

                    processor.execute(lightField, state);
                }
            }
        } else {
            if (aClass instanceof GroovyScriptClass) {
                if (name == null) {

                    for (Map.Entry<String, String> implicitVariable : IMPLICIT_VARIABLES.entrySet()) {
                        LightFieldBuilder lightField = new LightFieldBuilder(implicitVariable.getKey(), implicitVariable.getValue(), place);

                        boolean continueProcessing = processor.execute(lightField, state);

                        if (!continueProcessing) {
                            return;
                        }
                    }
                } else {
                    if (IMPLICIT_VARIABLES.containsKey(name)) {
                        String type = IMPLICIT_VARIABLES.get(name);

                        LightFieldBuilder lightField = new LightFieldBuilder(name, type, place);

                        processor.execute(lightField, state);
                    }
                }
            }
        }
    }

    private boolean isInsideWorkflowDefinitionScriptTag(@NotNull PsiElement place) {
        PsiFile containingFile = place.getContainingFile();

        if (containingFile != null) {
            PsiElement context = containingFile.getContext();

            if (context instanceof XmlText) {
                //probably inside workflow definition file

                XmlTag xmlTag = PsiTreeUtil.getParentOfType(context, XmlTag.class);

                if (xmlTag != null) {
                    return (LiferayWorkflowContextVariablesUtil.isWorkflowScriptTag(xmlTag));
                }
            }
        }

        return false;
    }

}
