package de.dm.intellij.liferay.language.groovy;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiType;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.lang.psi.impl.synthetic.GrLightVariable;
import org.jetbrains.plugins.groovy.lang.resolve.NonCodeMembersContributor;

import java.util.HashMap;
import java.util.Map;

public class GroovyScriptingImplicitMembersContributor extends NonCodeMembersContributor {

    private static final Map<String, String> IMPLICIT_VARIABLES = new HashMap<>();
    static {
        IMPLICIT_VARIABLES.put("out", "java.io.PrintWriter");
        IMPLICIT_VARIABLES.put("actionRequest", "javax.portlet.ActionRequest");
        IMPLICIT_VARIABLES.put("actionResponse", "javax.portlet.ActionReponse");
        IMPLICIT_VARIABLES.put("portletConfig", "javax.portlet.PortletConfig");
        IMPLICIT_VARIABLES.put("portletContext", "javax.portlet.PortletContext");
        IMPLICIT_VARIABLES.put("preferences", "javax.portlet.PortletPreferences");
        IMPLICIT_VARIABLES.put("userInfo", "java.util.Map<String, String>");
    }

    @Override
    public void processDynamicElements(@NotNull PsiType qualifierType, PsiClass aClass, @NotNull PsiScopeProcessor processor, @NotNull PsiElement place, @NotNull ResolveState state) {
        PsiManager manager = place.getManager();

        for (Map.Entry<String, String> implicitVariable : IMPLICIT_VARIABLES.entrySet()) {
            GrLightVariable grLightVariable = new GrLightVariable(manager, implicitVariable.getKey(), implicitVariable.getValue(), place);
            processor.execute(grLightVariable, state);
        }
    }
}
