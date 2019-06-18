package de.dm.intellij.bndtools.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class SimpleProvider extends CompletionProvider<CompletionParameters> {

    public SimpleProvider(String... items) {
        _items = items;
    }

    @Override
    public void addCompletions(
        @NotNull CompletionParameters completionParameters, @NotNull ProcessingContext processingContext,
        @NotNull CompletionResultSet completeResultSet) {

        for (String item : _items) {
            completeResultSet.addElement(
                LookupElementBuilder.create(
                    item
                ).withCaseSensitivity(
                    false
                ));
        }
    }

    private final String[] _items;

}