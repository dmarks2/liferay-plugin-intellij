package de.dm.intellij.liferay.resourcesimporter;

import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.SoftFileReferenceSet;
import com.intellij.util.ProcessingContext;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LiferayResourcesImporterPlaceholderReferenceContributor extends PsiReferenceContributor {

    //TODO: JOURNAL_ARTICLE_PATTERN
    //TODO: LAYOUT_PATTERN inside fragment-composition-definition.json
    //TODO: FILE_ENTRY_PATTERN inside fragment-composition-definition.json
    private static final Pattern FILE_ENTRY_PATTERN = Pattern.compile("\\[\\$FILE=([^$@]+)(@?)([^$]*)\\$]");
    private static final Pattern LAYOUT_PATTERN = Pattern.compile("\\[\\$LAYOUT=([^$@]+)(@?)([^$]*)\\$]");

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(
                PlatformPatterns.psiElement(PsiElement.class).and(new LiferayResourcesImporterPlaceholderReferenceFilterPattern()),
                new PsiReferenceProvider() {

                    @Override
                    public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                        List<PsiReference> psiReferences = new ArrayList<>();

                        TextRange originalRange = element.getTextRange();

                        TextRange valueRange = TextRange.create(0, originalRange.getLength());

                        String valueString = valueRange.substring(element.getText());

                        processFileEntries(element, psiReferences, valueString);

                        processLayoutEntries(element, psiReferences, valueString);

                        return psiReferences.toArray(new PsiReference[0]);
                    }
                }
        );
    }

    private void processFileEntries(@NotNull PsiElement element, List<PsiReference> psiReferences, String valueString) {
        Matcher matcher = FILE_ENTRY_PATTERN.matcher(valueString);

        while (matcher.find()) {
            String fileName = matcher.group(1);

            boolean groupPresent = (matcher.group(2) != null && !matcher.group(2).trim().isEmpty());

            if (! groupPresent) {
                int startInElement = matcher.start() + "[$FILE=".length();

                FileReferenceSet fileReferenceSet = new SoftFileReferenceSet(fileName, element, startInElement, null, SystemInfo.isFileSystemCaseSensitive, false);

                fileReferenceSet.addCustomization(
                        FileReferenceSet.DEFAULT_PATH_EVALUATOR_OPTION,
                        file -> {
                            PsiFile originalFile = element.getContainingFile().getOriginalFile();

                            Collection<PsiFileSystemItem> result = new ArrayList<>();

                            PsiFileSystemItem journalDirectory = LiferayFileUtil.getParent(originalFile, "journal");

                            if (journalDirectory != null) {
                                PsiFileSystemItem resourcesImporterBaseDirectory = journalDirectory.getParent();

                                if (resourcesImporterBaseDirectory != null) {
                                    PsiFileSystemItem documentsDirectory = LiferayFileUtil.getChild(resourcesImporterBaseDirectory, "document_library/documents");

                                    if (documentsDirectory != null) {
                                        result.add(documentsDirectory);
                                    }
                                }
                            }

                            return result;
                        }
                );

                psiReferences.addAll(Arrays.asList(fileReferenceSet.getAllReferences()));
            }
        }
    }

    private void processLayoutEntries(@NotNull PsiElement element, List<PsiReference> psiReferences, String valueString) {
        Matcher matcher = LAYOUT_PATTERN.matcher(valueString);

        while (matcher.find()) {
            boolean groupPresent = (matcher.group(2) != null && !matcher.group(2).trim().isEmpty());

            if (! groupPresent) {
                int startInElement = matcher.start(1);
                int endInElement = matcher.end(1);

                TextRange valueRange = TextRange.create(startInElement, endInElement);

                psiReferences.add(new LiferayResourcesImporterPlaceholderLayoutReference(element, valueRange));
            }
        }
    }
}
