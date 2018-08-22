package de.dm.intellij.liferay.util;

import javafx.util.Pair;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LiferayTaglibAttributes {

    public static Map<String, Collection<Pair<String, String>>> TAGLIB_ATTRIBUTES_JAVASCRIPT = new HashMap<String, Collection<Pair<String, String>>>();

    static {
        TAGLIB_ATTRIBUTES_JAVASCRIPT.put(LiferayTaglibs.TAGLIB_URI_AUI, Arrays.asList(
                new Pair<String, String>("script", ""),
                new Pair<String, String>("validator", ""),
                new Pair<String, String>("a", "onClick"),
                new Pair<String, String>("button", "onClick"),
                new Pair<String, String>("form", "onSubmit"),
                new Pair<String, String>("input", "onChange"),
                new Pair<String, String>("input", "onClick"),
                new Pair<String, String>("select", "onChange"),
                new Pair<String, String>("select", "onClick")
        ));

        TAGLIB_ATTRIBUTES_JAVASCRIPT.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_FRONTEND, Arrays.asList(
                new Pair<String, String>("edit-form", "onSubmit"),
                new Pair<String, String>("icon-vertical-card", "onClick"),
                new Pair<String, String>("vertical-card", "onClick")
        ));

        TAGLIB_ATTRIBUTES_JAVASCRIPT.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_UI, Arrays.asList(
                new Pair<String, String>("icon", "onClick"),
                new Pair<String, String>("input-checkbox", "onClick"),
                new Pair<String, String>("input-move-boxes", "leftOnChange"),
                new Pair<String, String>("input-move-boxes", "rightOnChange"),
                new Pair<String, String>("page-iterator", "jsCall"),
                new Pair<String, String>("quick-access-entry", "onClick"),
                new Pair<String, String>("tabs", "onClick")
        ));

        TAGLIB_ATTRIBUTES_JAVASCRIPT.put(LiferayTaglibs.TAGLIB_URI_AUI_OLD, LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_JAVASCRIPT.get(LiferayTaglibs.TAGLIB_URI_AUI));
    }


}
