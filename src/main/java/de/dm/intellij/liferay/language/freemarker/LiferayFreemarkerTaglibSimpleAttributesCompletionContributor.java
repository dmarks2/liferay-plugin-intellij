package de.dm.intellij.liferay.language.freemarker;

import com.intellij.freemarker.psi.directives.FtlMacro;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import de.dm.intellij.liferay.language.jsp.AbstractLiferayTaglibSimpleAttributesCompletionContributor;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

public class LiferayFreemarkerTaglibSimpleAttributesCompletionContributor extends AbstractLiferayTaglibSimpleAttributesCompletionContributor {

    @Override
    protected PsiElementPattern.Capture<PsiElement> getTaglibPattern(Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> taglibMap) {
        return LiferayFreemarkerUtil.getFtlStringLiteralFilter(
                psiElement -> {
                    String attributeName = LiferayFreemarkerUtil.getAttributeName(psiElement);

                    if (attributeName != null) {
                        FtlMacro ftlMacro = PsiTreeUtil.getParentOfType(psiElement, FtlMacro.class);

                        if (ftlMacro != null) {
                            String namespace = LiferayFreemarkerTaglibs.getNamespace(ftlMacro);
                            String localName = LiferayFreemarkerTaglibs.getLocalName(ftlMacro);

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
                    }

                    return false;
                }
        );
    }

}
