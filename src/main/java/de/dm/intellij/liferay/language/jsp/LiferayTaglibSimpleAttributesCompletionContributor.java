package de.dm.intellij.liferay.language.jsp;

import com.intellij.patterns.PatternCondition;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

public class LiferayTaglibSimpleAttributesCompletionContributor extends AbstractLiferayTaglibSimpleAttributesCompletionContributor {

    protected PsiElementPattern.Capture<PsiElement> getTaglibPattern(Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> taglibMap) {
        return
                PlatformPatterns.psiElement().with(new PatternCondition<>("liferayTaglib") {
                    @Override
                    public boolean accepts(@NotNull PsiElement psiElement, ProcessingContext context) {
                        XmlAttributeValue xmlAttributeValue = PsiTreeUtil.getParentOfType(psiElement, XmlAttributeValue.class);

                        if (xmlAttributeValue == null) {
                            return false;
                        }

                        XmlAttribute xmlAttribute = PsiTreeUtil.getParentOfType(xmlAttributeValue, XmlAttribute.class);

                        if (xmlAttribute == null) {
                            return false;
                        }

                        XmlTag xmlTag = xmlAttribute.getParent();

                        if (xmlTag != null) {
                            String namespace = xmlTag.getNamespace();
                            String localName = xmlTag.getLocalName();
                            String attributeName = xmlAttribute.getLocalName();

                            if (taglibMap.containsKey(namespace)) {
                                Collection<AbstractMap.SimpleEntry<String, String>> entries = taglibMap.get(namespace);

                                Stream<AbstractMap.SimpleEntry<String, String>> entriesStream = entries.stream();

                                return entriesStream.anyMatch(
                                        entry -> {
                                            String key = entry.getKey();
                                            String value = entry.getValue();

                                            return key.equals(localName) && value.equals(attributeName);

                                        }
                                );
                            }
                        }

                        return false;
                    }
                });
    }

}
