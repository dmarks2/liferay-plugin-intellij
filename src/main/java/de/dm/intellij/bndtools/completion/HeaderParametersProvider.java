package de.dm.intellij.bndtools.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class HeaderParametersProvider extends CompletionProvider<CompletionParameters> {

    public HeaderParametersProvider(String... names) {
        _names = names;
    }

    @Override
    public void addCompletions(
        @NotNull CompletionParameters completionParameters, @NotNull ProcessingContext processingContext,
        @NotNull CompletionResultSet completionResultSet) {

        for (String name : _names) {
            boolean directive = StringUtil.endsWithChar(name, ':');

            InsertHandler<LookupElement> insertHandler = _attributeHandler;

            if (directive) {
                name = name.substring(0, name.length() - 1);
                insertHandler = _directiveHandler;
            }

            completionResultSet.addElement(
                LookupElementBuilder.create(
                    name
                ).withCaseSensitivity(
                    false
                ).withInsertHandler(
                    insertHandler
                ));
        }
    }

    private static final InsertHandler<LookupElement> _attributeHandler = (context, lookupElement) -> {
        context.setAddCompletionChar(false);

        EditorModificationUtil.insertStringAtCaret(context.getEditor(), "=");
        context.commitDocument();
    };

    private static final InsertHandler<LookupElement> _directiveHandler = (context, lookupElement) -> {
        context.setAddCompletionChar(false);

        EditorModificationUtil.insertStringAtCaret(context.getEditor(), ":=");
        context.commitDocument();
    };

    private final String[] _names;

}