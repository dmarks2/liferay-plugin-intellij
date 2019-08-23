package de.dm.intellij.liferay.language.jsp;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.util.XmlUtil;
import de.dm.intellij.liferay.language.resourcebundle.AbstractLiferayResourceBundleReferenceContributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Adds code completion features for references to language kays inside Liferay or AlloyUI Taglibs
 */
public class LiferayTaglibResourceBundleReferenceContributor extends AbstractLiferayResourceBundleReferenceContributor<XmlTag, XmlAttribute> {

    @Override
    protected void registerReferenceProvider(PsiReferenceRegistrar registrar, String[] attributeNames, ElementFilter elementFilter, PsiReferenceProvider psiReferenceProvider) {
        XmlUtil.registerXmlAttributeValueReferenceProvider(
            registrar,
            attributeNames,
            elementFilter,
            false,
            psiReferenceProvider
        );
    }

    @Nullable
    @Override
    protected XmlTag getTag(@NotNull PsiElement psiElement) {
        XmlAttribute xmlAttribute = getAttribute(psiElement);
        if (xmlAttribute != null) {
            return xmlAttribute.getParent();
        }
        return null;
    }

    @Nullable
    @Override
    protected XmlAttribute getAttribute(@NotNull PsiElement psiElement) {
        PsiElement parent = psiElement.getParent();
        if (parent instanceof XmlAttribute) {
            return (XmlAttribute)parent;
        }
        return null;
    }

    @Override
    protected String getNamespace(XmlTag tag) {
        return tag.getNamespace();
    }

    @Override
    protected String getLocalName(XmlTag tag) {
        return tag.getLocalName();
    }

    @Override
    protected String getAttributeName(XmlAttribute attribute) {
        return attribute.getLocalName();
    }
}
