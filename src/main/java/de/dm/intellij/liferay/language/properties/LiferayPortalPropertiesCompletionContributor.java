package de.dm.intellij.liferay.language.properties;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.properties.psi.impl.PropertyKeyImpl;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiFile;
import com.intellij.util.ProcessingContext;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.util.Icons;
import de.dm.intellij.liferay.util.LiferayVersions;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LiferayPortalPropertiesCompletionContributor extends CompletionContributor {

    public LiferayPortalPropertiesCompletionContributor() {
        extend(
                CompletionType.BASIC,
                PlatformPatterns.
                        psiElement(PropertyKeyImpl.class).
                        inVirtualFile(
                                PlatformPatterns.
                                        virtualFile().
                                        withName(
                                                StandardPatterns.
                                                        string().
                                                        startsWith("portal")
                                        )
                        ),
                new CompletionProvider<>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
                        List<LookupElementBuilder> lookups = new ArrayList<LookupElementBuilder>();

                        PsiFile containingFile = parameters.getOriginalFile();

                        final Module module = ModuleUtil.findModuleForPsiElement(containingFile);
                        if (module == null) {
                            return;
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
                            return;
                        }

                        try {
                            Map<String, AbstractMap.SimpleImmutableEntry<String, String>> portalProperties = LiferayPortalPropertiesParser.getPortalProperties(inputStream);

                            for (String key : portalProperties.keySet()) {
                                AbstractMap.SimpleImmutableEntry<String, String> entry = portalProperties.get(key);

                                String entryKey = entry.getKey();

                                if (entryKey.contains("|")) {
                                    entryKey = entryKey.substring(0, entryKey.indexOf("|"));
                                }

                                lookups.add(LookupElementBuilder.create(key).withTypeText(entryKey).withIcon(Icons.LIFERAY_ICON));
                            }
                        } catch (IOException e) {
                            //??
                        }

                        result.addAllElements(lookups);
                        result.stopHere();
                    }
                }

        );
    }
}

