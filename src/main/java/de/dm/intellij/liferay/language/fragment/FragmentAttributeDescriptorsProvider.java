package de.dm.intellij.liferay.language.fragment;

import com.intellij.ide.highlighter.HtmlFileType;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.XmlAttributeDescriptor;
import com.intellij.xml.XmlAttributeDescriptorsProvider;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.Nullable;

public class FragmentAttributeDescriptorsProvider implements XmlAttributeDescriptorsProvider {

    @Override
    public XmlAttributeDescriptor[] getAttributeDescriptors(XmlTag context) {
        PsiFile psiFile = context.getContainingFile();

        if (HtmlFileType.INSTANCE.equals(psiFile.getFileType())) {
            psiFile = psiFile.getOriginalFile();

            if (LiferayFileUtil.isFragmentFile(psiFile)) {
                return FragmentAttributeDescriptorUtil.getCommonAttributesDescriptors(context);
            }
        }

        return XmlAttributeDescriptor.EMPTY;
    }

    @Override
    public @Nullable XmlAttributeDescriptor getAttributeDescriptor(String attributeName, XmlTag context) {
        return FragmentAttributeDescriptorUtil.getCommonAttributeDescriptor(attributeName, context);
    }
}
