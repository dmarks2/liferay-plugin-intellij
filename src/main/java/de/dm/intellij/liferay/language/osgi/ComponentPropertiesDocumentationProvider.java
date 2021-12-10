package de.dm.intellij.liferay.language.osgi;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ComponentPropertiesDocumentationProvider extends AbstractDocumentationProvider {

    @Nullable
    @Override
    public String generateDoc(PsiElement element, @Nullable PsiElement originalElement) {
        if (ComponentPropertiesPsiElementPatternCapture.instance.accepts(element)) {
            List<String> serviceClassNames = ComponentPropertiesCompletionContributor.getServiceClassNames(originalElement);
            if (! (serviceClassNames.isEmpty()) ) {
                String text = element.getText();
                text = StringUtil.unquoteString(text);

                for (String serviceClassName : serviceClassNames) {
                    String[][] properties = ComponentPropertiesCompletionContributor.COMPONENT_PROPERTIES.get(serviceClassName);
                    if (properties != null) {
                        for (String[] property : properties) {
                            if (text.startsWith(property[0])) {
                                String info = "";
                                info += "<b>" + property[0] + "</b><br/>\n";

                                String componentPropertiesDocumentationBundleKey = serviceClassName + "." + property[0];

                                String description = ComponentPropertiesDocumentationBundle.message(componentPropertiesDocumentationBundleKey);

                                if (description != null) {
                                    description = StringUtil.join(StringUtil.split(description, "\n"), "<br>");

                                    info += description;
                                }
                                return info;
                            }
                        }
                    }
                }
            }
        }

        return null;

    }
}
