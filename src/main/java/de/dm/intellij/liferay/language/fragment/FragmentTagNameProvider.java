package de.dm.intellij.liferay.language.fragment;

import com.intellij.codeInsight.completion.XmlTagInsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.ide.highlighter.HtmlFileType;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.xml.XmlElementDescriptorProvider;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.XmlElementDescriptor;
import com.intellij.xml.XmlTagNameProvider;
import de.dm.intellij.liferay.util.Icons;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FragmentTagNameProvider implements XmlTagNameProvider, XmlElementDescriptorProvider {

    public static final Map<String, Collection<AbstractMap.SimpleEntry<String, FragmentAttributeInformationHolder>>> FRAGMENT_TAG_NAMES = new TreeMap<>();

    //https://learn.liferay.com/dxp/latest/en/site-building/developer-guide/reference/fragments/fragment-specific-tags-reference.html
    public static final Collection<AbstractMap.SimpleEntry<String, FragmentAttributeInformationHolder>> COMMON_FRAGMENT_ATTRIBUTES = Arrays.asList(
            new AbstractMap.SimpleEntry<>("data-lfr-editable-id", new FragmentAttributeInformationHolder.Builder().idType(true).typeName("String").build()),
            new AbstractMap.SimpleEntry<>("data-lfr-editable-type", new FragmentAttributeInformationHolder.Builder().typeName("String").attributeValues(new String[]{"text", "html", "rich-text", "image", "link", "background-image"}).build()),
            new AbstractMap.SimpleEntry<>("data-lfr-background-image-id", new FragmentAttributeInformationHolder.Builder().typeName("String").idRefType(true).build())
        );

    static {
        FRAGMENT_TAG_NAMES.put(
                "lfr-drop-zone", Arrays.asList(
                        new AbstractMap.SimpleEntry<>("data-lfr-drop-zone-id", new FragmentAttributeInformationHolder.Builder().idType(true).typeName("String").build()),
                        new AbstractMap.SimpleEntry<>("data-lfr-priority", new FragmentAttributeInformationHolder.Builder().typeName("Integer").build()),
                        new AbstractMap.SimpleEntry<>("uuid", new FragmentAttributeInformationHolder.Builder().typeName("String").build())
                )
        );

        FRAGMENT_TAG_NAMES.put("lfr-editable", Arrays.asList(
                new AbstractMap.SimpleEntry<>("id", new FragmentAttributeInformationHolder.Builder().required(true).typeName("String").idType(true).build()),
                new AbstractMap.SimpleEntry<>("type", new FragmentAttributeInformationHolder.Builder().required(true).typeName("String").attributeValues(new String[]{"text", "html", "rich-text", "image", "link", "background-image"}).build()),
                new AbstractMap.SimpleEntry<>("data-lfr-priority", new FragmentAttributeInformationHolder.Builder().typeName("Integer").build())
            )
        );

        //https://help.liferay.com/hc/en-us/articles/360020757071-Introduction-to-Embedding-Widgets-in-Page-Fragments-
        FRAGMENT_TAG_NAMES.put("lfr-widget-asset-list", new ArrayList<>());
        FRAGMENT_TAG_NAMES.put("lfr-widget-breadcrumb", new ArrayList<>());
        FRAGMENT_TAG_NAMES.put("lfr-widget-categories-nav", new ArrayList<>());
        FRAGMENT_TAG_NAMES.put("lfr-widget-commerce-account-portlet", new ArrayList<>());
        FRAGMENT_TAG_NAMES.put("lfr-widget-dynamic-data-list", new ArrayList<>());
        FRAGMENT_TAG_NAMES.put("lfr-widget-form", new ArrayList<>());
        FRAGMENT_TAG_NAMES.put("lfr-widget-iframe", new ArrayList<>());
        FRAGMENT_TAG_NAMES.put("lfr-widget-media-gallery", new ArrayList<>());
        FRAGMENT_TAG_NAMES.put("lfr-widget-nav", new ArrayList<>());
        FRAGMENT_TAG_NAMES.put("lfr-widget-related-assets", new ArrayList<>());
        FRAGMENT_TAG_NAMES.put("lfr-widget-rss", new ArrayList<>());
        FRAGMENT_TAG_NAMES.put("lfr-widget-site-map", new ArrayList<>());
        FRAGMENT_TAG_NAMES.put("lfr-widget-tag-cloud", new ArrayList<>());
        FRAGMENT_TAG_NAMES.put("lfr-widget-tags-nav", new ArrayList<>());
        FRAGMENT_TAG_NAMES.put("lfr-widget-web-content", new ArrayList<>());
    }

    @Override
    public void addTagNameVariants(List<LookupElement> elements, @NotNull XmlTag tag, String prefix) {
        PsiFile psiFile = tag.getContainingFile();

        if (HtmlFileType.INSTANCE.equals(psiFile.getFileType())) {
            psiFile = psiFile.getOriginalFile();

            if (LiferayFileUtil.isFragmentFile(psiFile)) {
                for (String fragmentTagName : FRAGMENT_TAG_NAMES.keySet()) {
                    elements.add(LookupElementBuilder.create(fragmentTagName).withIcon(Icons.LIFERAY_ICON).withInsertHandler(XmlTagInsertHandler.INSTANCE));
                }
            }
        }
    }

    @Override
    public @Nullable XmlElementDescriptor getDescriptor(XmlTag tag) {
        if (FRAGMENT_TAG_NAMES.containsKey(tag.getName())) {
            return new FragmentXmlElementDescriptor(tag);
        }

        return null;
    }
}
