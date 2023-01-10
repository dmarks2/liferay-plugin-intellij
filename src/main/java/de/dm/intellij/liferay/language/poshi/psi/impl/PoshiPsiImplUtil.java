package de.dm.intellij.liferay.language.poshi.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import de.dm.intellij.liferay.language.poshi.psi.PoshiCommandBlock;
import de.dm.intellij.liferay.language.poshi.psi.PoshiTypes;
import de.dm.intellij.liferay.language.poshi.psi.PoshiVariableAssignment;

public class PoshiPsiImplUtil {

    public static String getName(PoshiVariableAssignment poshiVariableAssignment) {
        return poshiVariableAssignment.getIdentifier().getText();
    }

    public static PsiElement setName(PoshiVariableAssignment poshiVariableAssignment, String newName) {
        ASTNode identifierNode = poshiVariableAssignment.getNode().findChildByType(PoshiTypes.IDENTIFIER);

        if (identifierNode != null) {
            //TODO replace child?
        }

        return poshiVariableAssignment;
    }

    public static String getName(PoshiCommandBlock poshiCommandBlock) {
        return poshiCommandBlock.getIdentifier().getText();
    }

    public static PsiElement setName(PoshiCommandBlock poshiCommandBlock, String newName) {
        ASTNode identifierNode = poshiCommandBlock.getNode().findChildByType(PoshiTypes.IDENTIFIER);

        if (identifierNode != null) {
            //TODO replace child?
        }

        return poshiCommandBlock;
    }
}
