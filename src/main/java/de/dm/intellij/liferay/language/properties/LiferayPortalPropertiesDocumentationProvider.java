package de.dm.intellij.liferay.language.properties;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.lang.documentation.DocumentationMarkup;
import com.intellij.lang.properties.IProperty;
import com.intellij.lang.properties.PropertiesCommenter;
import com.intellij.lang.properties.PropertiesFileType;
import com.intellij.lang.properties.psi.PropertiesFile;
import com.intellij.lang.properties.psi.Property;
import com.intellij.lang.properties.psi.impl.PropertyImpl;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiManager;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.util.LiferayVersions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class LiferayPortalPropertiesDocumentationProvider extends AbstractDocumentationProvider {

    @Nullable
    @Override
    public String generateDoc(PsiElement element, @Nullable PsiElement originalElement) {
        if (PlatformPatterns.
                psiElement(PropertyImpl.class).
                inVirtualFile(
                        PlatformPatterns.
                                virtualFile().
                                withName(
                                        StandardPatterns.
                                                string().
                                                startsWith("portal")
                                )
                ).accepts(element)) {
            final Module module = ModuleUtil.findModuleForPsiElement(element);

            if (module == null) {
                return null;
            }

            float portalMajorVersion = -1.0f;

            LiferayModuleComponent component = module.getService(LiferayModuleComponent.class);
            if (component != null) {
                portalMajorVersion = component.getPortalMajorVersion();
            }

            InputStream inputStream = null;

            if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) {
                inputStream = LiferayPortalPropertiesCompletionContributor.class.getResourceAsStream("/com/liferay/properties/70/portal.properties");
            } else if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_1) {
                inputStream = LiferayPortalPropertiesCompletionContributor.class.getResourceAsStream("/com/liferay/properties/71/portal.properties");
            } else if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_2) {
                inputStream = LiferayPortalPropertiesCompletionContributor.class.getResourceAsStream("/com/liferay/properties/72/portal.properties");
            } else if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) {
                inputStream = LiferayPortalPropertiesCompletionContributor.class.getResourceAsStream("/com/liferay/properties/73/portal.properties");
            } else if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_4) {
                inputStream = LiferayPortalPropertiesCompletionContributor.class.getResourceAsStream("/com/liferay/properties/74/portal.properties");
            }

            if (inputStream == null) {
                return null;
            }

            try {
                Map<String, AbstractMap.SimpleImmutableEntry<String, String>> portalProperties = LiferayPortalPropertiesParser.getPortalProperties(inputStream);

                String text = ((PropertyImpl) element).getUnescapedKey();

                if (text == null) {
                    return null;
                }

                if (portalProperties.containsKey(text)) {
                    AbstractMap.SimpleImmutableEntry<String, String> entry = portalProperties.get(text);

                    String docCommentText = entry.getValue();

                    if (docCommentText != null && !docCommentText.trim().isEmpty()) {

                        StringBuilder stringBuilder = new StringBuilder();

                        List<String> lines = StringUtil.split(docCommentText, "\n");

                        String env = "";

                        for (String line : lines) {
                            line = line.trim();
                            line = StringUtil.trimStart(line, PropertiesCommenter.HASH_COMMENT_PREFIX);
                            line = StringUtil.trimStart(line, PropertiesCommenter.EXCLAMATION_COMMENT_PREFIX);
                            line = line.trim();

                            if (line.contains("Env:")) {
                                env = StringUtil.substringAfter(line, "Env:");

                                if (env != null) {
                                    env = env.trim();
                                }
                            } else {
                                stringBuilder.append(line).append("<br>");
                            }
                        }

                        String result = getMessage(stringBuilder, text);

                        result = result + DocumentationMarkup.SECTIONS_START + "<p>";

                        if (StringUtil.isNotEmpty(env)) {
                            result = result + DocumentationMarkup.SECTION_HEADER_START +
                                    "Env: " +
                                    DocumentationMarkup.SECTION_SEPARATOR +
                                    env +
                                    DocumentationMarkup.SECTION_END;
                        }

                        String entryKey = entry.getKey();

                        if (entryKey.contains("|")) {
                            entryKey = entryKey.substring(entryKey.indexOf("|") + 1);
                        }

                        result = result + DocumentationMarkup.SECTION_HEADER_START +
                                "Default: " +
                                DocumentationMarkup.SECTION_SEPARATOR +
                                renderPropertyValue(entryKey) +
                                DocumentationMarkup.SECTION_END;

                        result = result + DocumentationMarkup.SECTIONS_END;

                        return result;
                    }
                }
            } catch (IOException e) {
                //??
            }
        }

        return null;
    }

    @NotNull
    private static String getMessage(StringBuilder stringBuilder, String text) {
        String description = stringBuilder.toString();

        if (description.startsWith("<br>")) {
            description = description.substring(4);
        }

		return DocumentationMarkup.DEFINITION_START +
                text +
                DocumentationMarkup.DEFINITION_END +
                DocumentationMarkup.CONTENT_START +
                description +
                DocumentationMarkup.CONTENT_END;
    }

    @Override
    public @Nullable PsiElement getDocumentationElementForLookupItem(PsiManager psiManager, Object object, PsiElement element) {
        if (object instanceof String lookupString) {
			PropertiesFile propertiesFile = (PropertiesFile) PsiFileFactory.getInstance(psiManager.getProject()).createFileFromText("portal.properties", PropertiesFileType.INSTANCE, lookupString);

            List<IProperty> properties = propertiesFile.getProperties();

            if (!properties.isEmpty()) {
                IProperty iProperty = properties.get(0);

                if (iProperty instanceof Property property) {
					property.putUserData(ModuleUtilCore.KEY_MODULE, ModuleUtil.findModuleForPsiElement(element));

                    return property;
                }
            }
        }

        return super.getDocumentationElementForLookupItem(psiManager, object, element);
    }

    @NotNull
    private static String renderPropertyValue(String value) {
        if (StringUtil.isEmpty(value)) {
            return "<i>empty</i>";
        }
        return StringUtil.escapeXmlEntities(value);
    }
}
