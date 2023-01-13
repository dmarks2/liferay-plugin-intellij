package de.dm.intellij.liferay.language.poshi.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import com.intellij.util.ProcessingContext;
import de.dm.intellij.liferay.language.poshi.psi.PoshiDefinitionBlock;
import de.dm.intellij.liferay.language.poshi.psi.PoshiFile;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PoshiCompletionContributor extends CompletionContributor {
    public PoshiCompletionContributor() {
        extend(
                CompletionType.BASIC,
                PlatformPatterns.psiElement().inFile(PlatformPatterns.psiFile(PoshiFile.class)),
                new CompletionProvider<>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
                        PsiElement position = parameters.getOriginalPosition();

                        if (position == null) {
                            position = parameters.getPosition();
                        }

                        List<LookupElementBuilder> lookups = new ArrayList<>();

                        //immediate Child in file
                        PsiElement parent = position.getParent();

                        if (parent instanceof PsiErrorElement) {
                            parent = parent.getParent();
                        }

                        if (parent instanceof PoshiFile) {
                            lookups.add(LookupElementBuilder.create("definition").withIcon(Icons.LIFERAY_ICON));
                        } else if (parent instanceof PoshiDefinitionBlock) {
                            lookups.add(LookupElementBuilder.create("setUp").withIcon(Icons.LIFERAY_ICON));
                            lookups.add(LookupElementBuilder.create("tearDown").withIcon(Icons.LIFERAY_ICON));
                            lookups.add(LookupElementBuilder.create("test").withIcon(Icons.LIFERAY_ICON));
                            lookups.add(LookupElementBuilder.create("macro").withIcon(Icons.LIFERAY_ICON));
                            lookups.add(LookupElementBuilder.create("function").withIcon(Icons.LIFERAY_ICON));
                        }

                        result.addAllElements(lookups);

                        if (! lookups.isEmpty()) {
                            result.stopHere();
                        }
                    }

                }
        );
    }
}
