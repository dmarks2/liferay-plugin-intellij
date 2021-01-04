package de.dm.intellij.bndtools.formatting;

import com.intellij.formatting.Alignment;
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
import de.dm.intellij.bndtools.psi.BndElementType;
import org.jetbrains.annotations.NotNull;

public class BndFormattingModelBuilder implements FormattingModelBuilder {

    @NotNull
    @Override
    //TODO ScheduledForRemoval(inVersion = "2021.1") - use createModel(FormattingContext.create(element, settings)) - available since 2020.3
    public FormattingModel createModel(PsiElement element, CodeStyleSettings settings) {
        ASTNode astNode = element.getNode();

        PsiFile psiFile = element.getContainingFile();

        SpacingBuilder spacingBuilder = new SpacingBuilder(settings, psiFile.getLanguage())
            .between(BndTokenType.COLON, BndElementType.CLAUSE).spaces(1)
            .between(BndTokenType.NEWLINE, BndTokenType.HEADER_VALUE_PART).spaces(settings.getIndentSize(BndFileType.INSTANCE));


        BndFormatterBlock bndFormatterBlock = new BndFormatterBlock(
            astNode,
            Alignment.createAlignment(),
            Indent.getNoneIndent(),
            Wrap.createWrap(WrapType.NONE, false),
            settings,
            spacingBuilder
        );

        FormattingModel formattingModel = FormattingModelProvider.createFormattingModelForPsiFile(
            psiFile,
            bndFormatterBlock,
            settings);

        return formattingModel;
    }
}
