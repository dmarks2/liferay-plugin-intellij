package de.dm.intellij.liferay.language.jsp;

import com.intellij.openapi.util.Condition;
import com.intellij.psi.ElementManipulators;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.util.ClassUtil;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.ProcessingContext;
import de.dm.intellij.liferay.util.LiferayTaglibAttributes;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LiferayTaglibJavaBeanReferenceProvider extends PsiReferenceProvider {

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        List<PsiReference> result = new ArrayList<PsiReference>();

        XmlAttribute xmlAttribute = (XmlAttribute) element.getParent();
        if (xmlAttribute != null) {
            XmlTag xmlTag = xmlAttribute.getParent();
            if (xmlTag != null) {
                String namespace = xmlTag.getNamespace();
                String localName = xmlTag.getLocalName();

                if (LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_BEANS_CLASS_NAMES.containsKey(namespace)) {
                    Collection<Pair<String, Pair<String, String>>> attributes = LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_BEANS_CLASS_NAMES.get(namespace);
                    for (Pair<String, Pair<String, String>> attribute : attributes) {
                        if (attribute.getKey().equals(localName)) {
                            Pair<String, String> classNameAttribute = attribute.getValue();

                            String className = getClassName(element, namespace, classNameAttribute);

                            if (className != null) {
                                PsiManager psiManager = PsiManager.getInstance(element.getProject());

                                PsiClass psiClass = ClassUtil.findPsiClass(psiManager, className);
                                if (psiClass != null) {
                                    result.add(new LiferayTaglibJavaBeanReference((XmlAttributeValue) element, ElementManipulators.getValueTextRange(element), psiClass));
                                }

                            }
                        }
                    }
                }
            }
        }

        return result.toArray(new PsiReference[result.size()]);
    }

    private static String getClassName(PsiElement element, final String classNameNamespace, final Pair<String, String> classNameAttribute) {
        PsiElement parent = PsiTreeUtil.findFirstParent(element, new Condition<PsiElement>() {
            @Override
            public boolean value(PsiElement psiElement) {
                if (psiElement instanceof XmlTag) {
                    XmlTag xmlTag = (XmlTag)psiElement;
                    String namespace = xmlTag.getNamespace();
                    String localName = xmlTag.getLocalName();
                    if (classNameNamespace.equals(namespace)) {
                        if (classNameAttribute.getKey().equals(localName)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        if (parent != null) {
            XmlTag xmlTag = (XmlTag) parent;

            return xmlTag.getAttributeValue(classNameAttribute.getValue());
        }

        return null;
    }


}

