package de.dm.intellij.liferay.language.osgi.config;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LiferayConfigFoldingBuilder extends FoldingBuilderEx {

    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        List<FoldingDescriptor> descriptors = new ArrayList<>();
        
        // Find all array value elements
        PsiTreeUtil.findChildrenOfType(root, LiferayConfigPsiElement.class).forEach(element -> {
            if (element.getNode().getElementType() == LiferayConfigElementTypes.ARRAY_VALUE) {
                // Only fold arrays that span multiple lines
                TextRange range = element.getTextRange();
                int startLine = document.getLineNumber(range.getStartOffset());
                int endLine = document.getLineNumber(range.getEndOffset());
                
                if (endLine > startLine) {
                    descriptors.add(new FoldingDescriptor(element, range));
                }
            }
        });
        
        return descriptors.toArray(FoldingDescriptor[]::new);
    }

    @Nullable
    @Override
    public String getPlaceholderText(@NotNull ASTNode node) {
        if (node.getElementType() == LiferayConfigElementTypes.ARRAY_VALUE) {
            // Count elements in the array
            int elementCount = 0;
            ASTNode child = node.getFirstChildNode();
            while (child != null) {
                if (child.getElementType() == LiferayConfigElementTypes.ARRAY_ELEMENT) {
                    elementCount++;
                }
                child = child.getTreeNext();
            }
            
            if (elementCount == 1) {
                return "[1 element]";
            } else {
                return "[" + elementCount + " elements]";
            }
        }
        return "...";
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return false; // Don't collapse by default
    }
}
