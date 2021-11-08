package de.dm.intellij.liferay.language.sql;

import com.intellij.lang.injection.MultiHostInjector;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.lang.javascript.JavascriptLanguage;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.Trinity;
import com.intellij.psi.ElementManipulators;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlText;
import com.intellij.sql.psi.SqlLanguage;
import org.intellij.plugins.intelliLang.inject.InjectedLanguage;
import org.intellij.plugins.intelliLang.inject.InjectorUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LiferayCustomSQLLanguageInjector implements MultiHostInjector {

    @Override
    public void getLanguagesToInject(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement context) {
        PsiFile psiFile = context.getContainingFile().getOriginalFile();

        if ( (psiFile.getName().equals("default.xml")) &&
                (psiFile.getParent() != null) &&
                (psiFile.getParent().getName().equals("custom-sql"))
        ) {
            if (context instanceof XmlTag) {
                XmlTag xmlTag = (XmlTag) context;

                String tagName = xmlTag.getName();

                if ("sql".equals(tagName)) {
                    InjectedLanguage sqlLanguage = InjectedLanguage.create(SqlLanguage.INSTANCE.getID(), "", "", true);

                    List<Trinity<PsiLanguageInjectionHost, InjectedLanguage, TextRange>> list = new ArrayList<Trinity<PsiLanguageInjectionHost, InjectedLanguage, TextRange>>();

                    PsiElement[] myChildren = xmlTag.getChildren();

                    for (PsiElement child : myChildren) {
                        if (child instanceof XmlText) {
                            if (((PsiLanguageInjectionHost)child).isValidHost()) {
                                list.add(
                                        Trinity.create(
                                                ((PsiLanguageInjectionHost) child),
                                                sqlLanguage,
                                                ElementManipulators.getManipulator(child).getRangeInElement(child)
                                        )
                                );
                            }
                        }
                    }

                    if (!list.isEmpty()) {
                        InjectorUtils.registerInjection(
                                SqlLanguage.INSTANCE,
                                list,
                                xmlTag.getContainingFile(),
                                registrar
                        );
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
