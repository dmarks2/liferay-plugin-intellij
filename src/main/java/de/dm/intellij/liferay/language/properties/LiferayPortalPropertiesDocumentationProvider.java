package de.dm.intellij.liferay.language.properties;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.lang.properties.IProperty;
import com.intellij.lang.properties.PropertiesCommenter;
import com.intellij.lang.properties.PropertiesHighlighter;
import com.intellij.lang.properties.psi.impl.PropertyImpl;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.ui.GuiUtils;
import com.intellij.util.containers.ContainerUtil;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.util.LiferayVersions;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap;
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
            PsiFile containingFile = element.getContainingFile();
            containingFile = containingFile.getOriginalFile();

            Project project = containingFile.getProject();

            final Module module = ModuleUtil.findModuleForPsiElement(containingFile);
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

                    if (docCommentText != null && docCommentText.trim().length() > 0) {
                        @NonNls String info = "";

                        TextAttributes attributes = EditorColorsManager.getInstance().getGlobalScheme().getAttributes(PropertiesHighlighter.PropertiesComponent.PROPERTY_COMMENT.getTextAttributesKey()).clone();
                        Color background = attributes.getBackgroundColor();
                        if (background != null) {
                            info +="<div bgcolor=#"+ GuiUtils.colorToHex(background)+">";
                        }

                        String doc = StringUtil.join(ContainerUtil.map(StringUtil.split(docCommentText, "\n"), s -> {
                            final String trim = s.trim();
                            final String trimHash = StringUtil.trimStart(trim, PropertiesCommenter.HASH_COMMENT_PREFIX);
                            final String trimExclamation = StringUtil.trimStart(trimHash, PropertiesCommenter.EXCLAMATION_COMMENT_PREFIX);
                            return trimExclamation.trim();
                        }), "<br>");

                        if (doc.startsWith("<br>")) {
                            doc = doc.substring(4);
                        }

                        final Color foreground = attributes.getForegroundColor();
                        info += foreground != null ? "<font color=#" + GuiUtils.colorToHex(foreground) + ">" + doc + "</font>" : doc;
                        info += "\n<br>";
                        if (background != null) {
                            info += "</div>";
                        }

                        info += "\n<b>" + text + "</b>=\"" + renderPropertyValue(((IProperty)element)) + "\"";
                        info += getLocationString(element);
                        return info;
                    }
                }
            } catch (IOException e) {
                //??
            }
        }

        return null;
    }

    private static String getLocationString(PsiElement element) {
        PsiFile file = element.getContainingFile();
        return file != null ? " [" + file.getName() + "]" : "";
    }

    @NotNull
    private static String renderPropertyValue(IProperty prop) {
        String raw = prop.getValue();
        if (raw == null) {
            return "<i>empty</i>";
        }
        return StringUtil.escapeXmlEntities(raw);
    }
}
