package de.dm.intellij.liferay.language.jsp;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlTag;
import de.dm.intellij.liferay.util.LiferayTaglibs;
import org.jetbrains.annotations.Nullable;

public class LiferayTaglibSearchContainerJavaBeanReferenceProvider extends AbstractLiferayTaglibJavaBeanReferenceProvider {

    @Nullable
    @Override
    protected String getClassName(PsiElement element) {
        PsiElement classNameElement = PsiTreeUtil.findFirstParent(element, psiElement -> {
            if (psiElement instanceof XmlTag xmlTag) {
				String namespace = xmlTag.getNamespace();
                String localName = xmlTag.getLocalName();
                if (LiferayTaglibs.TAGLIB_URI_LIFERAY_UI.equals(namespace)) {
					return "search-container-row".equals(localName);
                }
            }
            return false;
        });

        if (classNameElement != null) {
            XmlTag xmlTag = (XmlTag) classNameElement;

            return xmlTag.getAttributeValue("className");
        }

        return null;
    }
}
