package de.dm.intellij.liferay.language.jsp;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.util.XmlUtil;
import de.dm.intellij.liferay.util.LiferayTaglibAttributes;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LiferayTaglibJavaBeanReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        XmlUtil.registerXmlAttributeValueReferenceProvider(
                registrar,
                getAttributeNames(),
                new LiferayTaglibFilter(),
                false,
                new LiferayTaglibJavaBeanReferenceProvider()
        );

    }

    protected String[] getAttributeNames() {
        Set<String> attributeNames = new HashSet<String>();
        for (Map.Entry<String, Collection<Pair<String, String>>> taglib : LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_BEANS.entrySet()) {
            for (Pair<String, String> entry : taglib.getValue()) {
                attributeNames.add(entry.getValue());
            }
        }

        return attributeNames.toArray(new String[attributeNames.size()]);
    }

    class LiferayTaglibFilter implements ElementFilter {
        public boolean isAcceptable(Object element, PsiElement context) {
            PsiElement psiElement = (PsiElement)element;

            PsiElement parent = psiElement.getParent();
            if (parent instanceof XmlAttribute) {
                XmlAttribute xmlAttribute = (XmlAttribute)parent;

                XmlTag xmlTag = xmlAttribute.getParent();

                if (xmlTag != null) {
                    String namespace = xmlTag.getNamespace();
                    String localName = xmlTag.getLocalName();
                    String attributeName = xmlAttribute.getLocalName();

                    if (LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_BEANS.containsKey(namespace)) {
                        Collection<Pair<String, String>> entries = LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_BEANS.get(namespace);

                        for (Pair<String, String> entry : entries) {
                            if (
                                    (entry.getKey().equals(localName)) &&
                                    (entry.getValue().equals(attributeName))
                            ) {
                                return true;
                            }
                        }
                    }
                }
            }

            return false;
        }

        public boolean isClassAcceptable(Class hintClass) {
            return true;
        }
    }
}
