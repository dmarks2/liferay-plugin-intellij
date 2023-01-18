package de.dm.intellij.liferay.language.poshi.formatting;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.FormattingContext;
import com.intellij.formatting.FormattingModel;
import com.intellij.formatting.FormattingModelBuilder;
import com.intellij.formatting.FormattingModelProvider;
import com.intellij.formatting.Indent;
import com.intellij.formatting.SpacingBuilder;
import com.intellij.formatting.Wrap;
import com.intellij.formatting.WrapType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import de.dm.intellij.bndtools.BndFileType;
import de.dm.intellij.bndtools.psi.BndTokenType;
import de.dm.intellij.liferay.language.poshi.psi.PoshiTypes;
import org.jetbrains.annotations.NotNull;

public class PoshiFormattingModelBuilder implements FormattingModelBuilder {

    @Override
    public @NotNull FormattingModel createModel(@NotNull FormattingContext formattingContext) {
        PsiElement element = formattingContext.getPsiElement();
        CodeStyleSettings settings = formattingContext.getCodeStyleSettings();

        ASTNode astNode = element.getNode();

        PsiFile psiFile = element.getContainingFile();

        SpacingBuilder spacingBuilder = new SpacingBuilder(settings, psiFile.getLanguage())
                .between(PoshiTypes.DEFINITION, PoshiTypes.CURLY_LBRACE).spaces(1)
                .between(PoshiTypes.MACRO_DEFINITION, PoshiTypes.CURLY_LBRACE).spaces(1)
                .between(PoshiTypes.FUNCTION_DEFINITION, PoshiTypes.CURLY_LBRACE).spaces(1)
                .between(PoshiTypes.TEST_DEFINITION, PoshiTypes.CURLY_LBRACE).spaces(1)
                .between(PoshiTypes.IDENTIFIER, PoshiTypes.EQUALS).spaces(1)
                .between(PoshiTypes.EQUALS, PoshiTypes.STRING_QUOTED_DOUBLE).spaces(1)
                .between(PoshiTypes.COMMA, PoshiTypes.VARIABLE_ASSIGNMENT).spaces(1)
                .between(PoshiTypes.IF, PoshiTypes.ROUND_LBRACE).spaces(1)
                .between(PoshiTypes.ELSE_IF, PoshiTypes.ROUND_LBRACE).spaces(1)
                .between(PoshiTypes.WHILE, PoshiTypes.ROUND_LBRACE).spaces(1)
                .between(PoshiTypes.TASK, PoshiTypes.ROUND_LBRACE).spaces(1)
                .between(PoshiTypes.ELSE, PoshiTypes.CURLY_LBRACE).spaces(1)
                .between(PoshiTypes.ROUND_RBRACE, PoshiTypes.CURLY_LBRACE).spaces(1)
                .between(BndTokenType.NEWLINE, BndTokenType.HEADER_VALUE_PART).spaces(settings.getIndentSize(BndFileType.INSTANCE));


        PoshiFormatterBlock poshiFormatterBlock = new PoshiFormatterBlock(
                astNode,
                Alignment.createAlignment(),
                Indent.getNoneIndent(),
                Wrap.createWrap(WrapType.NONE, false),
                settings,
                spacingBuilder
        );

        return FormattingModelProvider.createFormattingModelForPsiFile(
                psiFile,
                poshiFormatterBlock,
                settings);
    }
}
