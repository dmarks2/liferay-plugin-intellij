package de.dm.intellij.liferay.language.poshi.injection;

import com.intellij.lang.Language;
import com.intellij.lang.injection.MultiHostInjector;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.ElementManipulators;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlText;
import org.intellij.plugins.intelliLang.inject.InjectedLanguage;
import org.intellij.plugins.intelliLang.inject.InjectorUtils;
import org.jetbrains.annotations.NotNull;

import org.intellij.lang.xpath.XPathLanguage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PoshiPathFileXPathInjector implements MultiHostInjector {

    @Override
    public void getLanguagesToInject(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement context) {
        PsiFile psiFile = context.getContainingFile().getOriginalFile();

        if (psiFile.getName().endsWith(".path")) {
            if (context instanceof XmlTag) {
                XmlTag xmlTag = (XmlTag) context;

                String tagName = xmlTag.getName();

                if ("td".equals(tagName)) {
                    XmlTag parent = xmlTag.getParentTag();

                    if (parent != null) {
                        XmlTag[] subTags = parent.getSubTags();

                        List<XmlTag> tags = Arrays.stream(subTags).filter(tag -> "td".equals(tag.getName())).toList();

                        //inject only in second <td>
                        if (tags.indexOf(xmlTag) == 1) {
                            InjectedLanguage xPathLanguage = InjectedLanguage.create(XPathLanguage.ID, "", "", true);

                            List<InjectorUtils.InjectionInfo> list = new ArrayList<>();

                            PsiElement[] myChildren = xmlTag.getChildren();

                            for (PsiElement child : myChildren) {
                                if (child instanceof XmlText) {
                                    if (((PsiLanguageInjectionHost)child).isValidHost()) {
                                        TextRange textRange = ElementManipulators.getManipulator(child).getRangeInElement(child);

                                        String text = child.getText();

                                        if (text != null) {
                                            list.add(
                                                    new InjectorUtils.InjectionInfo(
                                                            ((PsiLanguageInjectionHost) child),
                                                            xPathLanguage,
                                                            textRange
                                                    )
                                            );
                                        }
                                    }
                                }
                            }

                            if (!list.isEmpty()) {
                                InjectorUtils.registerInjection(
                                        Language.findLanguageByID(XPathLanguage.ID),
                                        xmlTag.getContainingFile(),
                                        list,
                                        registrar
                                );
                            }
                        }
                    }

                }
            }

        }
    }

    @NotNull
    public List<? extends Class<? extends PsiElement>> elementsToInjectIn() {
        return List.of(XmlTag.class);
    }

}
