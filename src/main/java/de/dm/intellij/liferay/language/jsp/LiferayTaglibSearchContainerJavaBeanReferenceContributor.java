package de.dm.intellij.liferay.language.jsp;

import de.dm.intellij.liferay.util.LiferayTaglibs;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LiferayTaglibSearchContainerJavaBeanReferenceContributor extends AbstractLiferayTaglibReferenceContributor {

    private static final Map<String, Collection<Pair<String, String>>> TAGLIB_ATTRIBUTES = new HashMap<String, Collection<Pair<String, String>>>();

    static {
        TAGLIB_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_UI, Arrays.asList(
                new Pair<String, String>("search-container-column-text", "property"),
                new Pair<String, String>("search-container-column-text", "name"),
                new Pair<String, String>("search-container-column-text", "orderableProperty"),
                new Pair<String, String>("search-container-column-date", "property"),
                new Pair<String, String>("search-container-column-status", "property"),
                new Pair<String, String>("search-container-column-user", "property"),
                new Pair<String, String>("search-container-row", "keyProperty"),
                new Pair<String, String>("search-container-row", "rowIdProperty")
        ));
    }

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
    protected Map<String, Collection<Pair<String, String>>> getTaglibMap() {
        return TAGLIB_ATTRIBUTES;
    }
}
