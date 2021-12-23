package de.dm.intellij.liferay.language.fragment;

import com.intellij.psi.xml.XmlTag;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.xml.XmlAttributeDescriptor;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;

public class FragmentAttributeDescriptorUtil {

    public static XmlAttributeDescriptor[] getFragmentTagsAttributesDescriptors(@Nullable XmlTag context) {
        if (context != null) {
            if (FragmentTagNameProvider.FRAGMENT_TAG_NAMES.containsKey(context.getName())) {
                Collection<AbstractMap.SimpleEntry<String, FragmentAttributeInformationHolder>> entries = FragmentTagNameProvider.FRAGMENT_TAG_NAMES.get(context.getName());

                return getAttributesDescriptors(entries, context);
            }
        }
        return XmlAttributeDescriptor.EMPTY;
    }

    public static XmlAttributeDescriptor[] getCommonAttributesDescriptors(@Nullable XmlTag context) {
        if (context != null) {
            return getAttributesDescriptors(FragmentTagNameProvider.COMMON_FRAGMENT_ATTRIBUTES, context);
        }
        return XmlAttributeDescriptor.EMPTY;
    }

    public static @Nullable XmlAttributeDescriptor getFragmentTagsAttributeDescriptor(@NonNls String attributeName, @Nullable XmlTag context) {
        return ContainerUtil.find(getFragmentTagsAttributesDescriptors(context), descriptor -> attributeName.equals(descriptor.getName()));
    }

    public static @Nullable XmlAttributeDescriptor getCommonAttributeDescriptor(@NonNls String attributeName, @Nullable XmlTag context) {
        return ContainerUtil.find(getCommonAttributesDescriptors(context), descriptor -> attributeName.equals(descriptor.getName()));
    }

    private static XmlAttributeDescriptor[] getAttributesDescriptors(Collection<AbstractMap.SimpleEntry<String, FragmentAttributeInformationHolder>> entries, XmlTag xmlTag) {
        Collection<XmlAttributeDescriptor> result = new ArrayList<>();

        for (AbstractMap.SimpleEntry<String, FragmentAttributeInformationHolder> entry : entries) {
            result.add(new FragmentAttributeDescriptor(entry.getKey(), entry.getValue(), xmlTag));
        }

        return result.toArray(new XmlAttributeDescriptor[0]);
    }
}
