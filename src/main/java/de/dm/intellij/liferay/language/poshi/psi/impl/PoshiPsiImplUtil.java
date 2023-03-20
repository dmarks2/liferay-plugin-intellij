package de.dm.intellij.liferay.language.poshi.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.paths.GlobalPathReferenceProvider;
import com.intellij.openapi.paths.WebReference;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.tree.TokenSet;
import de.dm.intellij.liferay.language.poshi.psi.PoshiAnnotation;
import de.dm.intellij.liferay.language.poshi.psi.PoshiDefinitionBase;
import de.dm.intellij.liferay.language.poshi.psi.PoshiMethodCall;
import de.dm.intellij.liferay.language.poshi.psi.PoshiMethodCallReferenceSet;
import de.dm.intellij.liferay.language.poshi.psi.PoshiPathLocatorReference;
import de.dm.intellij.liferay.language.poshi.psi.PoshiPathReference;
import de.dm.intellij.liferay.language.poshi.psi.PoshiSeleniumReferenceSet;
import de.dm.intellij.liferay.language.poshi.psi.PoshiStringQuotedDouble;
import de.dm.intellij.liferay.language.poshi.psi.PoshiTestDefinition;
import de.dm.intellij.liferay.language.poshi.psi.PoshiTypes;
import de.dm.intellij.liferay.language.poshi.psi.PoshiUtilsReferenceSet;
import de.dm.intellij.liferay.language.poshi.psi.PoshiVariableAssignment;
import de.dm.intellij.liferay.language.poshi.psi.PoshiVariableRef;
import de.dm.intellij.liferay.language.poshi.psi.PoshiVariableReference;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PoshiPsiImplUtil {

    private static final Pattern VARIABLE_REFERENCE_PATTERN = Pattern.compile("\\$\\{([\\w-]*?)\\s*?}");
    private static final Pattern PATH_REFERENCE_PATTERN = Pattern.compile("([A-Z][A-Za-z]+)[#]?");
    private static final Pattern PATH_LOCATOR_REFERENCE_PATTERN = Pattern.compile("[#]([A-Z][A-Z_-]+)");

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

    public static String getName(PoshiDefinitionBase poshiDefinition) {
        if (poshiDefinition instanceof PoshiTestDefinition) {
            return getName((PoshiTestDefinition) poshiDefinition);
        }

        ASTNode identifierNode = poshiDefinition.getNode().findChildByType(PoshiTypes.IDENTIFIER);

        if (identifierNode != null) {
            return identifierNode.getText();
        }

        return null;
    }

    public static String getName(PoshiTestDefinition poshiTestDefinition) {
        ASTNode @NotNull [] identifierNodes = poshiTestDefinition.getNode().getChildren(TokenSet.create(PoshiTypes.IDENTIFIER));

        if (identifierNodes.length > 1) {
            return identifierNodes[1].getText();
        }

        return null;
    }

    public static PsiElement setName(PoshiDefinitionBase poshiDefinition, String newName) {
        ASTNode identifierNode = poshiDefinition.getNode().findChildByType(PoshiTypes.IDENTIFIER);

        if (identifierNode != null) {
            //TODO replace child?
        }

        return poshiDefinition;
    }

    public static PsiReference @NotNull [] getReferences(PoshiVariableRef element) {
        List<PsiReference> psiReferences = new ArrayList<>();

        TextRange originalRange = element.getTextRange();

        TextRange valueRange = TextRange.create(2, originalRange.getLength() - 1);

        String valueString = valueRange.substring(element.getText());

        psiReferences.add(new PoshiVariableReference(element, valueString, valueRange));

        return psiReferences.toArray(new PsiReference[0]);
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

        matcher = PATH_REFERENCE_PATTERN.matcher(valueString);

        if (matcher.find()) {
            String pathName = matcher.group(1);

            int start = matcher.start(1);
            int end = matcher.end(1);

            psiReferences.add(new PoshiPathReference(element, pathName, TextRange.create(start, end)));

            matcher = PATH_LOCATOR_REFERENCE_PATTERN.matcher(valueString);

            if (matcher.find(end)) {
                String locatorName = matcher.group(1);

                psiReferences.add(new PoshiPathLocatorReference(element, pathName, locatorName, TextRange.create(matcher.start(1), matcher.end(1))));
            }
        }

        String text = StringUtil.unquoteString(valueString);

        if (GlobalPathReferenceProvider.isWebReferenceUrl(text)) {
            psiReferences.add(new WebReference(element, new TextRange(valueRange.getStartOffset() + 1, valueRange.getEndOffset() - 1)));
        }

        return psiReferences.toArray(new PsiReference[0]);
    }

    public static PsiReference @NotNull [] getReferences(PoshiMethodCall poshiMethodCall) {
        List<PsiReference> references = new ArrayList<>(List.of((new PoshiMethodCallReferenceSet(poshiMethodCall)).getAllReferences()));

        references.addAll(List.of((new PoshiSeleniumReferenceSet(poshiMethodCall)).getAllReferences()));

        references.addAll(List.of((new PoshiUtilsReferenceSet(poshiMethodCall)).getAllReferences()));

        return references.toArray(new PsiReference[0]);
    }

    public static String getName(PoshiAnnotation annotation) {
        PsiElement annotationName = annotation.getAnnotationName();

        if (annotationName != null) {
            return annotationName.getText();
        }

        return null;
    }

    public static String getValue(PoshiAnnotation annotation) {
        PsiElement stringQuotedDouble = annotation.getStringQuotedDouble();

        if (stringQuotedDouble != null) {
            return stringQuotedDouble.getText();
        }

        return null;
    }
}
