package de.dm.intellij.liferay.language.poshi.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import de.dm.intellij.liferay.language.poshi.psi.PoshiCommandBlock;
import de.dm.intellij.liferay.language.poshi.psi.PoshiStringQuotedDouble;
import de.dm.intellij.liferay.language.poshi.psi.PoshiTypes;
import de.dm.intellij.liferay.language.poshi.psi.PoshiVariableAssignment;
import de.dm.intellij.liferay.language.poshi.psi.PoshiVariableReference;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PoshiPsiImplUtil {

    private static final Pattern VARIABLE_REFERENCE_PATTERN = Pattern.compile("\\$\\{([\\w-]*?)\\s*?}");

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

    public static PsiReference @NotNull [] getReferences(PoshiStringQuotedDouble element) {
        List<PsiReference> psiReferences = new ArrayList<>();

        TextRange originalRange = element.getTextRange();

        TextRange valueRange = TextRange.create(0, originalRange.getLength());

        String valueString = valueRange.substring(element.getText());

        Matcher matcher = VARIABLE_REFERENCE_PATTERN.matcher(valueString);

        while (matcher.find()) {
            String variableName = matcher.group(1);

            psiReferences.add(new PoshiVariableReference(element, variableName, TextRange.create(matcher.start(1), matcher.end(1))));
        }

        return psiReferences.toArray(new PsiReference[0]);
    }
}
