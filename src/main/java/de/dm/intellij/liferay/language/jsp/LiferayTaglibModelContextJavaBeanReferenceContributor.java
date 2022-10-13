package de.dm.intellij.liferay.language.jsp;

import de.dm.intellij.liferay.util.LiferayTaglibs;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LiferayTaglibModelContextJavaBeanReferenceContributor extends AbstractLiferayTaglibReferenceContributor {

    private static final Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_ATTRIBUTES = new HashMap<>();

    static {
        TAGLIB_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI, Arrays.asList(
                new AbstractMap.SimpleEntry<>("input", "field"),
                new AbstractMap.SimpleEntry<>("input", "name"),
                new AbstractMap.SimpleEntry<>("select", "field"),
                new AbstractMap.SimpleEntry<>("select", "name")
        ));

        TAGLIB_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_UI, Arrays.asList(
                new AbstractMap.SimpleEntry<>("input-field", "field")
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
    protected Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> getTaglibMap() {
        return TAGLIB_ATTRIBUTES;
    }

}
