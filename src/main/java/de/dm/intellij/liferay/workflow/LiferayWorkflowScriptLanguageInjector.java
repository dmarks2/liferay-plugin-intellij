package de.dm.intellij.liferay.workflow;

import com.intellij.lang.Language;
import com.intellij.lang.injection.MultiHostInjector;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.Trinity;
import com.intellij.psi.ElementManipulators;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlText;
import org.intellij.plugins.intelliLang.inject.InjectedLanguage;
import org.intellij.plugins.intelliLang.inject.InjectorUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiferayWorkflowScriptLanguageInjector implements MultiHostInjector {

    private static final Map<String, String> SCRIPT_LANGUAGE_MAPPING = new HashMap<>();
    static {
        SCRIPT_LANGUAGE_MAPPING.put("javascript", "JavaScript");
        SCRIPT_LANGUAGE_MAPPING.put("groovy", "Groovy");
        SCRIPT_LANGUAGE_MAPPING.put("java", "JAVA");
        SCRIPT_LANGUAGE_MAPPING.put("beanshell", "Beanshell");
        SCRIPT_LANGUAGE_MAPPING.put("drl", "Drools");
        SCRIPT_LANGUAGE_MAPPING.put("python", "Python");
        SCRIPT_LANGUAGE_MAPPING.put("ruby", "ruby");
    }

    private static final Map<String, String> TEMPLATE_LANGUAGE_MAPPING = new HashMap<>();
    static {
        TEMPLATE_LANGUAGE_MAPPING.put("freemarker", "FTL>");
        TEMPLATE_LANGUAGE_MAPPING.put("velocity", "VTL");
    }

    private static final String CDATA_START = "<![CDATA[";
    private static final String CDATA_END = "]]>";

    @Override
    public void getLanguagesToInject(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement context) {
        if (context instanceof XmlTag) {
            XmlTag xmlTag = (XmlTag) context;

            String namespace = xmlTag.getNamespace();

            if (LiferayWorkflowContextVariablesUtil.WORKFLOW_NAMESPACES.contains(namespace)) {
                handleLanguageInjection(registrar, xmlTag, LiferayWorkflowContextVariablesUtil.WORKFLOW_SCRIPT_TAG, null,"script-language", SCRIPT_LANGUAGE_MAPPING);
                handleLanguageInjection(registrar, xmlTag, LiferayWorkflowContextVariablesUtil.WORKFLOW_TEMPLATE_TAG, null, "template-language", TEMPLATE_LANGUAGE_MAPPING);
                handleLanguageInjection(registrar, xmlTag, LiferayWorkflowContextVariablesUtil.WORKFLOW_DESCRIPTION_TAG, LiferayWorkflowContextVariablesUtil.WORKFLOW_NOTIFICATION_TAG, "template-language", TEMPLATE_LANGUAGE_MAPPING);
            }
        }
    }

    @NotNull
    public List<? extends Class<? extends PsiElement>> elementsToInjectIn() {
        return List.of(XmlTag.class);
    }

    private void handleLanguageInjection(@NotNull MultiHostRegistrar registrar, @NotNull XmlTag xmlTag, @NotNull String contentTagName, @Nullable String parentTagName, @NotNull String languageTagName, @NotNull Map<String, String> languageMapping) {
        String tagName = xmlTag.getName();

        if (contentTagName.equals(tagName)) {

            XmlTag parentTag = xmlTag.getParentTag();

            if (parentTag != null) {
                if (parentTagName == null || parentTag.getName().equals(parentTagName)) {
                    String languageName = parentTag.getSubTagText(languageTagName);

                    if (languageName != null) {
                        String mappedLanguage = languageMapping.get(languageName);

                        if (mappedLanguage != null) {
                            Language language = Language.findLanguageByID(mappedLanguage);

                            if (language != null) {
                                InjectedLanguage injectedLanguage = InjectedLanguage.create(mappedLanguage, "", "", true);

                                List<Trinity<PsiLanguageInjectionHost, InjectedLanguage, TextRange>> list = new ArrayList<>();

                                PsiElement[] myChildren = xmlTag.getChildren();
                                for (PsiElement child : myChildren) {
                                    if (child instanceof XmlText) {
                                        if (((PsiLanguageInjectionHost) child).isValidHost()) {
                                            TextRange textRange = ElementManipulators.getManipulator(child).getRangeInElement(child);

                                            String text = child.getText();

                                            if (text != null) {
                                                int cdataStart = text.indexOf(CDATA_START);
                                                int cdataEnd = text.indexOf(CDATA_END);

                                                if (cdataStart > -1 && cdataEnd > -1 && cdataEnd > cdataStart) {
                                                    textRange = new TextRange(cdataStart + CDATA_START.length(), cdataEnd);
                                                }

                                                list.add(
                                                        Trinity.create(
                                                                ((PsiLanguageInjectionHost) child),
                                                                injectedLanguage,
                                                                textRange
                                                        )
                                                );
                                            }
                                        }
                                    }
                                }

                                if (!list.isEmpty()) {
                                    InjectorUtils.registerInjection(
                                            language,
                                            list,
                                            xmlTag.getContainingFile(),
                                            registrar
                                    );
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
