package de.dm.intellij.liferay.language.jsp;

import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import de.dm.intellij.liferay.util.LiferayTaglibs;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.Collection;

public class LiferayTaglibModelContextJavaBeanReferenceContributor extends AbstractLiferayTaglibJavaBeanReferenceContributor {

    private static final Collection<Pair<String, String>> ATTRIBUTES_LIFERAY_AUI = Arrays.asList(
            new Pair<String, String>("input", "field"),
            new Pair<String, String>("input", "name"),
            new Pair<String, String>("select", "field"),
            new Pair<String, String>("select", "name")
    );

    private static final Collection<Pair<String, String>> ATTRIBUTES_LIFERAY_UI = Arrays.asList(
            new Pair<String, String>("input-field", "field")
    );

    @Override
    protected AbstractLiferayTaglibJavaBeanReferenceProvider getLiferayTaglibJavaBeanReferenceProvider() {
        return new LiferayTaglibModelContextJavaBeanReferenceProvider();
    }

    @Override
    protected String[] getAttributeNames() {
        return new String[] {
            "name",
            "field"
        };
    }

    @Override
    protected boolean isSuitableAttribute(XmlAttribute xmlAttribute) {
        XmlTag xmlTag = xmlAttribute.getParent();

        if (xmlTag != null) {
            String namespace = xmlTag.getNamespace();
            String localName = xmlTag.getLocalName();
            String attributeName = xmlAttribute.getLocalName();

            if (LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI.equals(namespace)) {
                for (Pair<String, String> entry : ATTRIBUTES_LIFERAY_AUI) {
                    if (
                            (entry.getKey().equals(localName)) &&
                            (entry.getValue().equals(attributeName))
                    ) {
                        return true;
                    }
                }
            } else if (LiferayTaglibs.TAGLIB_URI_LIFERAY_UI.equals(namespace)) {
                for (Pair<String, String> entry : ATTRIBUTES_LIFERAY_UI) {
                    if (
                            (entry.getKey().equals(localName)) &&
                            (entry.getValue().equals(attributeName))
                    ) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
