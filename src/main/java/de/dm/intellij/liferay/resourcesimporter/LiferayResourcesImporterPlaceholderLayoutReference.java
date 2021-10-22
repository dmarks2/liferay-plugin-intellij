package de.dm.intellij.liferay.resourcesimporter;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.json.psi.JsonFile;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonValue;
import com.intellij.json.psi.impl.JsonRecursiveElementVisitor;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.PsiReferenceBase;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LiferayResourcesImporterPlaceholderLayoutReference extends PsiReferenceBase<PsiElement> {

    public LiferayResourcesImporterPlaceholderLayoutReference(PsiElement element, TextRange textRange) {
        super(element, textRange, true);
    }

    @Override
    public @Nullable PsiElement resolve() {
        Map<String, JsonProperty> friendlyURLs = getFriendlyURLs();

        String value = getValue();

        JsonProperty jsonProperty = friendlyURLs.get(value);

        if (jsonProperty != null) {
            return jsonProperty.getValue();
        }

        return null;
    }

    @Override
    public Object @NotNull [] getVariants() {
        List<Object> result = new ArrayList<Object>();

        Map<String, JsonProperty> friendlyURLs = getFriendlyURLs();

        for (String friendlyUrl : friendlyURLs.keySet()) {
            JsonProperty jsonProperty = friendlyURLs.get(friendlyUrl);

            ItemPresentation presentation = jsonProperty.getPresentation();

            if (presentation != null && jsonProperty.getValue() != null) {
                result.add(
                        LookupElementBuilder.createWithSmartPointer(
                                        friendlyUrl,
                                        jsonProperty.getValue()
                                )
                                .withIcon(presentation.getIcon(false))
                                .withTypeText(presentation.getLocationString(), true)
                );
            }
        }


        return result.toArray(new Object[0]);
    }

    private Map<String, JsonProperty> getFriendlyURLs() {
        Map<String, JsonProperty> result = new TreeMap<>();

        PsiFile psiFile = getElement().getContainingFile().getOriginalFile();

        PsiFileSystemItem journalDirectory = LiferayFileUtil.getParent(psiFile, "journal");

        if (journalDirectory != null) {
            PsiFileSystemItem resourcesImporterBaseDirectory = journalDirectory.getParent();

            if (resourcesImporterBaseDirectory != null) {
                PsiFileSystemItem sitemapJsonFile = LiferayFileUtil.getChild(resourcesImporterBaseDirectory, "sitemap.json");

                if (sitemapJsonFile instanceof JsonFile) {
                    JsonFile jsonFile = (JsonFile) sitemapJsonFile;

                    JsonValue topLevelValue = jsonFile.getTopLevelValue();

                    if (topLevelValue != null) {
                        topLevelValue.accept(new JsonRecursiveElementVisitor() {
                            public void visitProperty(@NotNull JsonProperty property) {
                                if ("friendlyURL".equals(property.getName())) {
                                    JsonValue value = property.getValue();
                                    if (value != null) {
                                        String friendlyURL = property.getValue().getText();

                                        friendlyURL = StringUtil.unquoteString(friendlyURL);

                                        result.put(friendlyURL, property);
                                    }
                                }

                                super.visitProperty(property);
                            }
                        });
                    }
                }
            }
        }

        return result;
    }
}
