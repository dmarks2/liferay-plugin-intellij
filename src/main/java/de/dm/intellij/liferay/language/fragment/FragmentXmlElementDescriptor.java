package de.dm.intellij.liferay.language.fragment;

import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.XmlAttributeDescriptor;
import com.intellij.xml.XmlElementDescriptor;
import com.intellij.xml.XmlElementsGroup;
import com.intellij.xml.XmlNSDescriptor;
import com.intellij.xml.impl.XmlElementDescriptorEx;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

public class FragmentXmlElementDescriptor implements XmlElementDescriptor, XmlElementDescriptorEx {

    private final XmlTag xmlTag;

    public FragmentXmlElementDescriptor(XmlTag xmlTag) {
        this.xmlTag = xmlTag;
    }

    @Override
    public PsiElement getDeclaration() {
        return xmlTag;
    }

    @Override
    public @NonNls String getName(PsiElement context) {
        return getName();
    }

    @Override
    public String getName() {
        return xmlTag.getName();
    }

    @Override
    public void init(PsiElement element) {
    }

    @Override
    public String getDefaultName() {
        return xmlTag.getName();
    }

    @Override
    public @NonNls String getQualifiedName() {
        return null;
    }

    /**
     * Returns an array of child tag descriptors.
     * @param context the parent tag.
     * @return an array of child tag descriptors, or empty array if no child tag allowed.
     */
    @Override
    public XmlElementDescriptor[] getElementsDescriptors(XmlTag context) {
        return EMPTY_ARRAY;
    }

    @Override
    public @Nullable XmlElementDescriptor getElementDescriptor(XmlTag childTag, XmlTag contextTag) {
        return null;
    }

    @Override
    public XmlAttributeDescriptor[] getAttributesDescriptors(@Nullable XmlTag context) {
        return FragmentAttributeDescriptorUtil.getFragmentTagsAttributesDescriptors(context);
    }

    @Override
    public @Nullable XmlAttributeDescriptor getAttributeDescriptor(@NonNls String attributeName, @Nullable XmlTag context) {
        return FragmentAttributeDescriptorUtil.getFragmentTagsAttributeDescriptor(attributeName, context);
    }

    @Override
    public @Nullable XmlAttributeDescriptor getAttributeDescriptor(XmlAttribute attribute) {
        return getAttributeDescriptor(attribute.getName(), attribute.getParent());
    }

    @Override
    public @Nullable XmlNSDescriptor getNSDescriptor() {
        return null;
    }

    @Override
    public @Nullable XmlElementsGroup getTopGroup() {
        return null;
    }

    @Override
    public int getContentType() {
        return CONTENT_TYPE_ANY;
    }

    @Override
    public @Nullable String getDefaultValue() {
        return null;
    }

    @Override
    public String toString() {
        return getName();
    }
}
