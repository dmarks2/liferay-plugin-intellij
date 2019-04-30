package de.dm.intellij.liferay.language.groovy;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiType;
import com.intellij.psi.ResolveState;
import com.intellij.psi.impl.light.LightFieldBuilder;
import com.intellij.psi.scope.PsiScopeProcessor;
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
        IMPLICIT_VARIABLES.put("actionResponse", "javax.portlet.ActionReponse");
        IMPLICIT_VARIABLES.put("portletConfig", "javax.portlet.PortletConfig");
        IMPLICIT_VARIABLES.put("portletContext", "javax.portlet.PortletContext");
        IMPLICIT_VARIABLES.put("preferences", "javax.portlet.PortletPreferences");
        IMPLICIT_VARIABLES.put("userInfo", "java.util.Map<String, String>");
    }

    @Override
    public void processDynamicElements(@NotNull PsiType qualifierType, PsiClass aClass, @NotNull PsiScopeProcessor processor, @NotNull PsiElement place, @NotNull ResolveState state) {
        String name = ResolveUtil.getNameHint(processor);

        if (aClass instanceof GroovyScriptClass) {
            if (name == null) {

                for (Map.Entry<String, String> implicitVariable : IMPLICIT_VARIABLES.entrySet()) {
                    LightFieldBuilder lightField = new LightFieldBuilder(implicitVariable.getKey(), implicitVariable.getValue(), place);

                    processor.execute(lightField, state);
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
