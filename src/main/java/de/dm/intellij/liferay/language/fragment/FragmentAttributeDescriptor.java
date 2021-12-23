package de.dm.intellij.liferay.language.fragment;

import com.intellij.psi.PsiElement;
import com.intellij.psi.meta.PsiPresentableMetaData;
import com.intellij.psi.xml.XmlElement;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.XmlAttributeDescriptor;
import com.intellij.xml.impl.XmlAttributeDescriptorEx;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class FragmentAttributeDescriptor implements XmlAttributeDescriptor, XmlAttributeDescriptorEx, PsiPresentableMetaData {

    private final String attributeName;
    private final FragmentAttributeInformationHolder fragmentAttributeInformationHolder;
    private final XmlTag xmlTag;

    public FragmentAttributeDescriptor(String attributeName, FragmentAttributeInformationHolder fragmentAttributeInformationHolder, XmlTag xmlTag) {
        this.attributeName = attributeName;
        this.fragmentAttributeInformationHolder = fragmentAttributeInformationHolder;
        this.xmlTag = xmlTag;
    }

    @Override
    public boolean isRequired() {
        return fragmentAttributeInformationHolder.isRequired();
    }

    @Override
    public boolean isFixed() {
        return false;
    }

    @Override
    public boolean hasIdType() {
        return fragmentAttributeInformationHolder.isIdType();
    }

    @Override
    public boolean hasIdRefType() {
        return fragmentAttributeInformationHolder.isIdRefType();
    }

    @Override
    public @Nullable String getDefaultValue() {
        if (fragmentAttributeInformationHolder.getAttributeValues().length > 0) {
            return fragmentAttributeInformationHolder.getAttributeValues()[0];
        }

        return null;
    }

    @Override
    public boolean isEnumerated() {
        return false;
    }

    @Override
    public String @Nullable [] getEnumeratedValues() {
        return fragmentAttributeInformationHolder.getAttributeValues();
    }

    @Override
    public @Nullable String validateValue(XmlElement context, String value) {
        return null;
    }

    @Override
    public PsiElement getDeclaration() {
        return xmlTag;
    }

    @Override
    public @NonNls String getName(PsiElement context) {
        return attributeName;
    }

    @Override
    public String getName() {
        return attributeName;
    }

    @Override
    public void init(PsiElement element) {
    }

    @Override
    public Object @NotNull [] getDependencies() {
        return new Object[] { getDeclaration() };
    }

    @Override
    public @Nullable @Nls String getTypeName() {
        return fragmentAttributeInformationHolder.getTypeName();
    }

    @Override
    public @Nullable Icon getIcon() {
        return Icons.LIFERAY_ICON;
    }

    @Override
    public String toString() {
        return getName();
    }
}
