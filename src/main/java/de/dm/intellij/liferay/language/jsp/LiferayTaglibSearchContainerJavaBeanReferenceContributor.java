package de.dm.intellij.liferay.language.jsp;

import de.dm.intellij.liferay.util.LiferayTaglibs;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LiferayTaglibSearchContainerJavaBeanReferenceContributor extends AbstractLiferayTaglibReferenceContributor {

    private static final Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_ATTRIBUTES = new HashMap<>();

    static {
        TAGLIB_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_UI, Arrays.asList(
                new AbstractMap.SimpleEntry<>("search-container-column-text", "property"),
                new AbstractMap.SimpleEntry<>("search-container-column-text", "name"),
                new AbstractMap.SimpleEntry<>("search-container-column-text", "orderableProperty"),
                new AbstractMap.SimpleEntry<>("search-container-column-date", "property"),
                new AbstractMap.SimpleEntry<>("search-container-column-status", "property"),
                new AbstractMap.SimpleEntry<>("search-container-column-user", "property"),
                new AbstractMap.SimpleEntry<>("search-container-row", "keyProperty"),
                new AbstractMap.SimpleEntry<>("search-container-row", "rowIdProperty")
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
    protected Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> getTaglibMap() {
        return TAGLIB_ATTRIBUTES;
    }
}
