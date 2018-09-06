package de.dm.intellij.liferay.language.jsp;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlTag;
import de.dm.intellij.liferay.util.LiferayTaglibs;
import org.jetbrains.annotations.Nullable;

public class LiferayTaglibModelContextJavaBeanReferenceProvider extends AbstractLiferayTaglibJavaBeanReferenceProvider {

    @Nullable
    @Override
    protected String getClassName(PsiElement element) {
        XmlTag xmlTag = PsiTreeUtil.getParentOfType(element, XmlTag.class);

        String modelAttributeValue = xmlTag.getAttributeValue("model");
        if (modelAttributeValue != null) {
            return modelAttributeValue;
        }

        xmlTag = (XmlTag) getPrevSiblingOrParent(element, LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI, "model-context");

        if (xmlTag != null) {
            return xmlTag.getAttributeValue("model");
        }

        return null;
    }

    private static PsiElement getPrevSiblingOrParent(PsiElement element, String classNameElementNamespace, String classNameElementLocalName) {
        PsiElement sibling = element.getPrevSibling();
        while ( sibling != null) {
            if (sibling instanceof XmlTag) {
                XmlTag xmlTag = (XmlTag)sibling;
                String namespace = xmlTag.getNamespace();
                String localName = xmlTag.getLocalName();

                if (classNameElementNamespace.equals(namespace)) {
                    if (classNameElementLocalName.equals(localName)) {
                        return sibling;
                    }
                }

            }

            sibling = sibling.getPrevSibling();
        }

        PsiElement parent = element.getParent();
        if (parent != null) {
            return getPrevSiblingOrParent(parent, classNameElementNamespace, classNameElementLocalName);
        }

        return null;
    }


}
