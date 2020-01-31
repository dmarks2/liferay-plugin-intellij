package de.dm.intellij.bndtools.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import de.dm.intellij.bndtools.BndLanguage;
import de.dm.intellij.bndtools.parser.BndHeaderParsers;
import de.dm.intellij.bndtools.psi.BndHeader;
import de.dm.intellij.bndtools.psi.BndTokenType;
import de.dm.intellij.bndtools.psi.Directive;
import org.jetbrains.annotations.NotNull;
import org.osgi.framework.Constants;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class BndCompletionContributor extends CompletionContributor {

    public BndCompletionContributor() {
        extend(
            CompletionType.BASIC, _header(Constants.EXPORT_PACKAGE),
            new HeaderParametersProvider(Constants.VERSION_ATTRIBUTE, Constants.USES_DIRECTIVE + ':'));

        extend(
            CompletionType.BASIC, _header(Constants.IMPORT_PACKAGE),
            new HeaderParametersProvider(Constants.VERSION_ATTRIBUTE, Constants.RESOLUTION_DIRECTIVE + ':'));

        extend(
            CompletionType.BASIC, _directive(Constants.RESOLUTION_DIRECTIVE),
            new SimpleProvider(Constants.RESOLUTION_MANDATORY, Constants.RESOLUTION_OPTIONAL));

        extend(CompletionType.BASIC,
            PlatformPatterns.psiElement(BndTokenType.HEADER_NAME).withLanguage(BndLanguage.INSTANCE),
            new CompletionProvider<CompletionParameters>() {
                @Override
                public void addCompletions(@NotNull CompletionParameters parameters,
                                           @NotNull ProcessingContext context,
                                           @NotNull CompletionResultSet resultSet) {
                    for (String header : BndHeaderParsers.PARSERS_MAP.keySet()) {
                        resultSet.addElement(LookupElementBuilder.create(header).withInsertHandler(HEADER_INSERT_HANDLER));
                    }
                }
            }
        );

    }

    private static ElementPattern<PsiElement> _directive(String name) {
        PsiElementPattern.Capture<PsiElement> element = psiElement(BndTokenType.HEADER_VALUE_PART);

        PsiElementPattern.Capture<Directive> directiveElement = psiElement(Directive.class);

        return element.withSuperParent(2, directiveElement.withName(name));
    }

    private static ElementPattern<PsiElement> _header(String name) {
        PsiElementPattern.Capture<PsiElement> psiElementCapture = psiElement(BndTokenType.HEADER_VALUE_PART);

        psiElementCapture.afterLeaf(";");

        PsiElementPattern.Capture<BndHeader> headerElement = psiElement(BndHeader.class);

        return psiElementCapture.withSuperParent(3, headerElement.withName(name));
    }

    private static final InsertHandler<LookupElement> HEADER_INSERT_HANDLER = new InsertHandler<LookupElement>() {
        @Override
        public void handleInsert(@NotNull InsertionContext context, @NotNull LookupElement item) {
            context.setAddCompletionChar(false);
            EditorModificationUtil.insertStringAtCaret(context.getEditor(), ": ");
            context.commitDocument();
        }
    };


}