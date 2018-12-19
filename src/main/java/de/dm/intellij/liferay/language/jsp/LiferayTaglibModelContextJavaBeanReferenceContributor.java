package de.dm.intellij.liferay.language.jsp;

import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import de.dm.intellij.liferay.util.LiferayTaglibs;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class LiferayTaglibModelContextJavaBeanReferenceContributor extends AbstractLiferayTaglibReferenceContributor {

    private static final Map<String, Collection<Pair<String, String>>> TAGLIB_ATTRIBUTES = new HashMap<String, Collection<Pair<String, String>>>();

    static {
        TAGLIB_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI, Arrays.asList(
                new Pair<String, String>("input", "field"),
                new Pair<String, String>("input", "name"),
                new Pair<String, String>("select", "field"),
                new Pair<String, String>("select", "name")
        ));

        TAGLIB_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_UI, Arrays.asList(
                new Pair<String, String>("input-field", "field")
        ));

    }

    @Override
    protected AbstractLiferayTaglibJavaBeanReferenceProvider getReferenceProvider() {
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

            if (TAGLIB_ATTRIBUTES.containsKey(namespace)) {
                Collection<Pair<String, String>> entries = TAGLIB_ATTRIBUTES.get(namespace);

                Stream<Pair<String, String>> entriesStream = entries.stream();

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
