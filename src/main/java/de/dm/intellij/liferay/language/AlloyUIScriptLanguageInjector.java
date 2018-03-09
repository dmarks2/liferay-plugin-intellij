package de.dm.intellij.liferay.language;

import com.intellij.lang.injection.MultiHostInjector;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.lang.javascript.JavascriptLanguage;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlText;
import de.dm.intellij.liferay.util.LiferayTaglibs;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * Defines that text within <aui:script>-Tags should be treated as JavaScript
 */
public class AlloyUIScriptLanguageInjector implements MultiHostInjector {

    public static List<String> AUI_TAGLIB_NAMESPACES = Arrays.asList(
            LiferayTaglibs.TAGLIB_URI_AUI,
            LiferayTaglibs.TAGLIB_URI_AUI_OLD);

    public void getLanguagesToInject(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement context) {
        if (
                (context == null) ||
                (! (context.isValid())) ||
                (! (context instanceof XmlTag))
           ) {
            return;
        }

        XmlTag scriptTag = (XmlTag)context;
        if (
                AUI_TAGLIB_NAMESPACES.contains(scriptTag.getNamespace()) &&
                "script".equals(scriptTag.getLocalName().toLowerCase())
            ) {
            //we found an <aui:script>-Tag


            boolean needToInject = false;

            PsiElement[] myChildren = scriptTag.getChildren();
            for (PsiElement child : myChildren) {
                if (child instanceof XmlText) {
                    //only inject if <aui:script> contains reqular content
                    needToInject = true;
                    break;
                }
            }

            if (needToInject) {
                registrar.startInjecting(JavascriptLanguage.INSTANCE);

                for (PsiElement child : myChildren) {
                    if (child instanceof XmlText) {
                        //Inject language only for regular text inside <aui:script>. Other tags like <portlet:namespace> which can be present should not be treated as JavaScript
                        int length = child.getTextLength();
                        registrar.addPlace(null, null, (PsiLanguageInjectionHost) child, TextRange.create(0, length));
                    }
                }

                registrar.doneInjecting();
            }
        }

    }

    @NotNull
    public List<? extends Class<? extends PsiElement>> elementsToInjectIn() {
        return Arrays.asList(XmlTag.class);
    }
}
