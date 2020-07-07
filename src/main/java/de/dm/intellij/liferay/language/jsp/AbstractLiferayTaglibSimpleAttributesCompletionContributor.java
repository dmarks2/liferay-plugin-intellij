package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PatternCondition;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.ProcessingContext;
import de.dm.intellij.liferay.util.Icons;
import de.dm.intellij.liferay.util.LiferayTaglibs;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public abstract class AbstractLiferayTaglibSimpleAttributesCompletionContributor extends CompletionContributor {

    private static final String[] LEXICON_LOOKUP = new String[] {"lexicon"};
    private static final String[] LEFT_RIGHT_LOOKUP = new String[] {"left", "right"};
    private static final String[] TARGET_LOOKUP = new String[] {"blank", "self", "parent", "top", "_blank", "_new"};
    private static final String[] BUTTON_TYPE_LOOKUP = new String[] {"button", "submit", "cancel", "reset"};
    private static final String[] FORM_METHOD_LOOKUP = new String[] {"get", "post"};
    private static final String[] INPUT_TYPE_LOOKUP = new String[] {
            "text", "hidden", "assetCategories", "assetTags", "textarea", "timeZone", "password", "checkbox",
            "radio", "submit", "button", "color", "email", "number", "range", "resource", "url", "editor",
            "toggle-card", "toggle-switch", "image"};
    private static final String[] VALIDATOR_NAME_LOOKUP = new String[] {
            "acceptFiles", "alpha", "alphanum", "custom", "date", "digits", "email", "equalTo", "iri", "max",
            "maxLength", "min", "minLength", "number", "range", "rangeLength", "required", "url"};
    private static final String[] SEARCH_CONTAINER_DISPLAY_STYLE_LOOKUP = new String[] {"list", "descriptive", "icon"};
    private static final String[] DIRECTION_LOOKUP = new String[] {"left", "right", "up", "down"};
    private static final String[] INPUT_EDITOR_TOOLBAR_SET_LOOKUP = new String[] {"bbcode", "creole", "editInPlace", "email", "liferay", "liferayArticle", "phone", "simple", "tablet"};
    private static final String[] STATE_LOOKUP = new String[] {"success", "warning", "info", "default", "primary", "danger"};
    private static final String[] TABS_TYPE_LOOKUP = new String[] {"tabs", "pills"};
    private static final String[] DRAGGABLE_IMAGE_LOOKUP = new String[] {"vertical", "horizontal", "both", "none"};
    private static final String[] BOOKMARKS_DISPLAY_STYLE_LOOKUP = new String[] {"inline", "menu"};

    private static final Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_LEXICON_ATTRIBUTES = new HashMap<>();
    static {
        TAGLIB_LEXICON_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI, Arrays.asList(
                new AbstractMap.SimpleEntry<>("fieldset", "markupView"),
                new AbstractMap.SimpleEntry<>("fieldset-group", "markupView"),
                new AbstractMap.SimpleEntry<>("icon", "markupView"),
                new AbstractMap.SimpleEntry<>("nav-bar", "markupView"),
                new AbstractMap.SimpleEntry<>("workflow-status", "markupView")
        ));
        TAGLIB_LEXICON_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_UI, Arrays.asList(
                new AbstractMap.SimpleEntry<>("app-view-entry", "markupView"),
                new AbstractMap.SimpleEntry<>("form-navigator", "markupView"),
                new AbstractMap.SimpleEntry<>("icon", "markupView"),
                new AbstractMap.SimpleEntry<>("icon-menu", "markupView"),
                new AbstractMap.SimpleEntry<>("input-search", "markupView"),
                new AbstractMap.SimpleEntry<>("page-iterator", "markupView"),
                new AbstractMap.SimpleEntry<>("panel", "markupView"),
                new AbstractMap.SimpleEntry<>("panel-container", "markupView"),
                new AbstractMap.SimpleEntry<>("search-iterator", "markupView"),
                new AbstractMap.SimpleEntry<>("search-paginator", "markupView"),
                new AbstractMap.SimpleEntry<>("search-toggle", "markupView"),
                new AbstractMap.SimpleEntry<>("user-display", "markupView")
        ));
    }

    private static final Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_LEFT_RIGHT_ATTRIBUTES = new HashMap<>();
    static {
        TAGLIB_LEFT_RIGHT_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI, Arrays.asList(
                new AbstractMap.SimpleEntry<>("field-wrapper", "inlineLabel"),
                new AbstractMap.SimpleEntry<>("form", "inlineLabel"),
                new AbstractMap.SimpleEntry<>("input", "inlineLabel"),
                new AbstractMap.SimpleEntry<>("select", "inlineLabel"),
                new AbstractMap.SimpleEntry<>("button", "iconAlign")
        ));
    }

    private static final Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_TARGET_ATTRIBUTES = new HashMap<>();
    static {
        TAGLIB_TARGET_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI, Arrays.asList(
                new AbstractMap.SimpleEntry<>("a", "target"),
                new AbstractMap.SimpleEntry<>("icon", "target"),
                new AbstractMap.SimpleEntry<>("nav-item", "target")
        ));
        TAGLIB_TARGET_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_UI, Arrays.asList(
                new AbstractMap.SimpleEntry<>("icon", "target"),
                new AbstractMap.SimpleEntry<>("page-iterator", "target"),
                new AbstractMap.SimpleEntry<>("search-container-column-text", "target"),
                new AbstractMap.SimpleEntry<>("social-bookmark", "target"),
                new AbstractMap.SimpleEntry<>("social-bookmarks", "target")
        ));
        TAGLIB_TARGET_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_SOCIAL_BOOKMARKS, Arrays.asList(
                new AbstractMap.SimpleEntry<>("bookmarks", "target")
        ));
    }

    private static final Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_BUTTON_TYPE_ATTRIBUTES = new HashMap<>();
    static {
        TAGLIB_BUTTON_TYPE_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI, Arrays.asList(
                new AbstractMap.SimpleEntry<>("button", "type")
        ));
    }

    private static final Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_FORM_METHOD_ATTRIBUTES = new HashMap<>();
    static {
        TAGLIB_FORM_METHOD_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI, Arrays.asList(
                new AbstractMap.SimpleEntry<>("form", "method")
        ));
        TAGLIB_FORM_METHOD_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_UI, Arrays.asList(
                new AbstractMap.SimpleEntry<>("icon", "method"),
                new AbstractMap.SimpleEntry<>("icon-delete", "method")
        ));
        TAGLIB_FORM_METHOD_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_FRONTEND, Arrays.asList(
                new AbstractMap.SimpleEntry<>("edit-form", "method")
        ));
    }

    private static final Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_INPUT_TYPE_ATTRIBUTES = new HashMap<>();
    static {
        TAGLIB_INPUT_TYPE_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI, Arrays.asList(
                new AbstractMap.SimpleEntry<>("input", "type")
        ));
    }

    private static final Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_VALIDATOR_NAME_ATTRIBUTES = new HashMap<>();
    static {
        TAGLIB_VALIDATOR_NAME_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI, Arrays.asList(
                new AbstractMap.SimpleEntry<>("validator", "name")
        ));
    }

    private static final Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_SEARCH_CONTAINER_DISPLAY_STYLE_ATTRIBUTES = new HashMap<>();
    static {
        TAGLIB_VALIDATOR_NAME_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_UI, Arrays.asList(
                new AbstractMap.SimpleEntry<>("search-iterator", "displayStyle")
        ));
    }

    private static final Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_DIRECTION_ATTRIBUTES = new HashMap<>();
    static {
        TAGLIB_DIRECTION_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_UI, Arrays.asList(
                new AbstractMap.SimpleEntry<>("icon-menu", "direction")
        ));
    }

    private static final Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_INPUT_EDITOR_TOOLBAR_SET_ATTRIBUTES = new HashMap<>();
    static {
        TAGLIB_INPUT_EDITOR_TOOLBAR_SET_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_UI, Arrays.asList(
                new AbstractMap.SimpleEntry<>("input-editor", "toolbarSet")
        ));
        TAGLIB_INPUT_EDITOR_TOOLBAR_SET_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_EDITOR, Arrays.asList(
                new AbstractMap.SimpleEntry<>("editor", "toolbarSet")
        ));
    }

    private static final Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_STATE_ATTRIBUTES = new HashMap<>();
    static {
        TAGLIB_STATE_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_UI, Arrays.asList(
                new AbstractMap.SimpleEntry<>("search-container-row", "state")
        ));
    }

    private static final Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_TABS_TYPE_ATTRIBUTES = new HashMap<>();
    static {
        TAGLIB_TABS_TYPE_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_UI, Arrays.asList(
                new AbstractMap.SimpleEntry<>("tabs", "type")
        ));
    }

    private static final Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_DRAGGABLE_IMAGE_ATTRIBUTES = new HashMap<>();
    static {
        TAGLIB_DRAGGABLE_IMAGE_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_ITEM_SELECTOR, Arrays.asList(
                new AbstractMap.SimpleEntry<>("image-selector", "draggableImage")
        ));
    }

    private static final Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_BOOKMARKS_DISPLAY_STYLE_ATTRIBUTES = new HashMap<>();
    static {
        TAGLIB_BOOKMARKS_DISPLAY_STYLE_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_SOCIAL_BOOKMARKS, Arrays.asList(
                new AbstractMap.SimpleEntry<>("bookmarks", "displayStyle")
        ));
    }

    public AbstractLiferayTaglibSimpleAttributesCompletionContributor() {
        extend(
                CompletionType.BASIC,
                getTaglibPattern(TAGLIB_LEXICON_ATTRIBUTES),
                getCompletionProvider(LEXICON_LOOKUP, String.class.getSimpleName())
        );

        extend(
                CompletionType.BASIC,
                getTaglibPattern(TAGLIB_LEFT_RIGHT_ATTRIBUTES),
                getCompletionProvider(LEFT_RIGHT_LOOKUP, String.class.getSimpleName())
        );

        extend(
                CompletionType.BASIC,
                getTaglibPattern(TAGLIB_TARGET_ATTRIBUTES),
                getCompletionProvider(TARGET_LOOKUP, String.class.getSimpleName())
        );

        extend(
                CompletionType.BASIC,
                getTaglibPattern(TAGLIB_BUTTON_TYPE_ATTRIBUTES),
                getCompletionProvider(BUTTON_TYPE_LOOKUP, String.class.getSimpleName())
        );

        extend(
                CompletionType.BASIC,
                getTaglibPattern(TAGLIB_FORM_METHOD_ATTRIBUTES),
                getCompletionProvider(FORM_METHOD_LOOKUP, String.class.getSimpleName())
        );

        extend(
                CompletionType.BASIC,
                getTaglibPattern(TAGLIB_INPUT_TYPE_ATTRIBUTES),
                getCompletionProvider(INPUT_TYPE_LOOKUP, String.class.getSimpleName())
        );

        extend(
                CompletionType.BASIC,
                getTaglibPattern(TAGLIB_VALIDATOR_NAME_ATTRIBUTES),
                getCompletionProvider(VALIDATOR_NAME_LOOKUP, String.class.getSimpleName())
        );

        extend(
                CompletionType.BASIC,
                getTaglibPattern(TAGLIB_SEARCH_CONTAINER_DISPLAY_STYLE_ATTRIBUTES),
                getCompletionProvider(SEARCH_CONTAINER_DISPLAY_STYLE_LOOKUP, String.class.getSimpleName())
        );

        extend(
                CompletionType.BASIC,
                getTaglibPattern(TAGLIB_DIRECTION_ATTRIBUTES),
                getCompletionProvider(DIRECTION_LOOKUP, String.class.getSimpleName())
        );

        extend(
                CompletionType.BASIC,
                getTaglibPattern(TAGLIB_INPUT_EDITOR_TOOLBAR_SET_ATTRIBUTES),
                getCompletionProvider(INPUT_EDITOR_TOOLBAR_SET_LOOKUP, String.class.getSimpleName())
        );

        extend(
                CompletionType.BASIC,
                getTaglibPattern(TAGLIB_STATE_ATTRIBUTES),
                getCompletionProvider(STATE_LOOKUP, String.class.getSimpleName())
        );

        extend(
                CompletionType.BASIC,
                getTaglibPattern(TAGLIB_TABS_TYPE_ATTRIBUTES),
                getCompletionProvider(TABS_TYPE_LOOKUP, String.class.getSimpleName())
        );

        extend(
                CompletionType.BASIC,
                getTaglibPattern(TAGLIB_DRAGGABLE_IMAGE_ATTRIBUTES),
                getCompletionProvider(DRAGGABLE_IMAGE_LOOKUP, String.class.getSimpleName())
        );

        extend(
                CompletionType.BASIC,
                getTaglibPattern(TAGLIB_BOOKMARKS_DISPLAY_STYLE_ATTRIBUTES),
                getCompletionProvider(BOOKMARKS_DISPLAY_STYLE_LOOKUP, String.class.getSimpleName())
        );
    }

    protected abstract PsiElementPattern.Capture<PsiElement> getTaglibPattern(Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> taglibMap);

    private static CompletionProvider<CompletionParameters> getCompletionProvider(String[] values, String typeName) {
        return new CompletionProvider<CompletionParameters>() {

            @Override
            protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
                Stream.of(
                        values
                ).map(
                        lookupElement -> LookupElementBuilder.create(lookupElement).withTypeText(typeName).withIcon(Icons.LIFERAY_ICON)
                ).forEach(
                        result::addElement
                );

                result.stopHere();
            }
        };
    }
}
