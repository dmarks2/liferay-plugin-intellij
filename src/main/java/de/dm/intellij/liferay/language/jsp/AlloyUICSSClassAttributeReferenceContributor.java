package de.dm.intellij.liferay.language.jsp;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.css.CssSupportLoader;
import com.intellij.psi.css.impl.util.CssInHtmlClassOrIdReferenceProvider;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.util.XmlUtil;
import de.dm.intellij.liferay.language.AlloyUIScriptLanguageInjector;
import org.jetbrains.annotations.NotNull;

/**
 * Adds support for the attributes &quot;cssClass&quot; and &quot;iconCssClass&quot; on AlloyUI Tags, so that Code Completion for (S)CSS classes is available.
 */
public class AlloyUICSSClassAttributeReferenceContributor extends PsiReferenceContributor {
    public static final String CSS_CLASS_ATTRIBUTE = "cssClass";
    public static final String ICON_CSS_CLASS_ATTRIBUTE = "iconCssClass";

    public AlloyUICSSClassAttributeReferenceContributor() {
    }

    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        CssInHtmlClassOrIdReferenceProvider htmlClassOrIdReferenceProvider = new CssInHtmlClassOrIdReferenceProvider() {

            @Override
            public ElementFilter getFilter() {
                return new ElementFilter() {
                    public boolean isAcceptable(Object element, PsiElement context) {
                        PsiElement psiElement = (PsiElement)element;
                        if (CssSupportLoader.isInFileThatSupportsCssResolve(psiElement)) {
                            PsiElement parent = psiElement.getParent();
                            if (parent instanceof XmlAttribute) {
                                XmlAttribute xmlAttribute = (XmlAttribute)parent;
                                String attrName = xmlAttribute.getName();
                                if (isSuitableAttribute(attrName, xmlAttribute)) {
                                    return true;
                                }
                            }
                        }

                        return false;
                    }

                    public boolean isClassAcceptable(Class hintClass) {
                        return true;
                    }
                };
            }

            protected boolean isSuitableAttribute(String attrName, XmlAttribute xmlAttribute) {
                XmlTag parent = xmlAttribute.getParent();
                if (AlloyUIScriptLanguageInjector.AUI_TAGLIB_NAMESPACES.contains(parent.getNamespace())) {
                    return (
                            (CSS_CLASS_ATTRIBUTE.equals(attrName)) ||
                            (ICON_CSS_CLASS_ATTRIBUTE.equals(attrName))
                    );
                }
                return false;
            }
        };
        XmlUtil.registerXmlAttributeValueReferenceProvider(registrar, new String[]{CSS_CLASS_ATTRIBUTE, ICON_CSS_CLASS_ATTRIBUTE}, htmlClassOrIdReferenceProvider.getFilter(), false, htmlClassOrIdReferenceProvider);
    }

}