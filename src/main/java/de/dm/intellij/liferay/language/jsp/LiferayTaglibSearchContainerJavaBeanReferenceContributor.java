package de.dm.intellij.liferay.language.jsp;

import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import de.dm.intellij.liferay.util.LiferayTaglibs;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

public class LiferayTaglibSearchContainerJavaBeanReferenceContributor extends AbstractLiferayTaglibReferenceContributor {

    private static final Collection<Pair<String, String>> ATTRIBUTES = Arrays.asList(
        new Pair<String, String>("search-container-column-text", "property"),
        new Pair<String, String>("search-container-column-text", "name"),
        new Pair<String, String>("search-container-column-text", "orderableProperty"),
        new Pair<String, String>("search-container-column-date", "property"),
        new Pair<String, String>("search-container-column-status", "property"),
        new Pair<String, String>("search-container-column-user", "property"),
        new Pair<String, String>("search-container-row", "keyProperty"),
        new Pair<String, String>("search-container-row", "rowIdProperty")
    );

    @Override
    protected AbstractLiferayTaglibJavaBeanReferenceProvider getReferenceProvider() {
        return new LiferayTaglibSearchContainerJavaBeanReferenceProvider();
    }

    @Override
    protected String[] getAttributeNames() {
        return new String[] {
            "property",
            "name",
            "orderableProperty",
            "keyProperty",
            "rowIdProperty"
        };
    }

    @Override
    protected boolean isSuitableAttribute(XmlAttribute xmlAttribute) {
        XmlTag xmlTag = xmlAttribute.getParent();

        if (xmlTag != null) {
            String namespace = xmlTag.getNamespace();
            String localName = xmlTag.getLocalName();
            String attributeName = xmlAttribute.getLocalName();

            if (LiferayTaglibs.TAGLIB_URI_LIFERAY_UI.equals(namespace)) {
                Stream<Pair<String, String>> entriesStream = ATTRIBUTES.stream();

                return entriesStream.anyMatch(
                    entry -> {
                        String key = entry.getKey();
                        String value = entry.getValue();

                        return key.equals(localName) && value.equals(attributeName);
                    }
                );
            }
        }
        return false;
    }
}
