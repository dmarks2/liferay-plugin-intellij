package de.dm.intellij.bndtools.formatting;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.Block;
import com.intellij.formatting.Indent;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.SpacingBuilder;
import com.intellij.formatting.Wrap;
import com.intellij.formatting.WrapType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.TokenType;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.formatter.common.AbstractBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BndFormatterBlock extends AbstractBlock {

    private final Indent indent;
    private final CodeStyleSettings codeStyleSettings;
    private final SpacingBuilder spacingBuilder;

    public BndFormatterBlock(ASTNode astNode, Alignment alignment, Indent indent, Wrap wrap, CodeStyleSettings codeStyleSettings, SpacingBuilder spacingBuilder) {
        super(astNode, wrap, alignment);

        this.indent = indent;

        this.codeStyleSettings = codeStyleSettings;

        this.spacingBuilder = spacingBuilder;
    }

    @Override
    public Indent getIndent() {
        return indent;
    }

    @Nullable
    @Override
    public Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
        return spacingBuilder.getSpacing(this, child1, child2);
    }

    @Override
    protected List<Block> buildChildren() {
        List<Block> blocks = new ArrayList<>();

        ASTNode childNode = myNode.getFirstChildNode();

        while (childNode != null) {
            if (childNode.getElementType() != TokenType.WHITE_SPACE) {
                Block block = new BndFormatterBlock(childNode, Alignment.createAlignment(), indent, Wrap.createWrap(WrapType.NONE, false), codeStyleSettings, spacingBuilder);

                blocks.add(block);
            }

            childNode = childNode.getTreeNext();
        }

        return blocks;
    }


    @Override
    public boolean isLeaf() {
        return (myNode.getFirstChildNode() == null);
    }
}
