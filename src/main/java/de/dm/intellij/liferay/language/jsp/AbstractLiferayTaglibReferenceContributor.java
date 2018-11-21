package de.dm.intellij.liferay.language.jsp;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.xml.util.XmlUtil;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractLiferayTaglibReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        XmlUtil.registerXmlAttributeValueReferenceProvider(
            registrar,
            getAttributeNames(),
            new LiferayTaglibFilter(),
            false,
            getReferenceProvider()
        );
    }

    protected abstract PsiReferenceProvider getReferenceProvider();

    protected abstract String[] getAttributeNames();

    protected abstract boolean isSuitableAttribute(XmlAttribute xmlAttribute);

    class LiferayTaglibFilter implements ElementFilter {
        public boolean isAcceptable(Object element, PsiElement context) {
            PsiElement psiElement = (PsiElement)element;

            PsiElement parent = psiElement.getParent();
            if (parent instanceof XmlAttribute) {
                XmlAttribute xmlAttribute = (XmlAttribute)parent;

                return AbstractLiferayTaglibReferenceContributor.this.isSuitableAttribute(xmlAttribute);
            }

            return false;
        }

        public boolean isClassAcceptable(Class hintClass) {
            return true;
        }
    }
}
