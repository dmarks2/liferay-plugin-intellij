package de.dm.intellij.liferay.language.poshi.psi;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiReference;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PoshiMethodCallReferenceSet {

    private static final Pattern METHOD_CALL_REFERENCE_PATTERN = Pattern.compile("^([A-Z][A-Za-z]+)?\\.?([A-Za-z_][A-Za-z]+)?\\.?([A-Za-z_][A-Za-z]+)?");

    private PoshiMethodCall poshiMethodCall;

    public PoshiMethodCallReferenceSet(PoshiMethodCall poshiMethodCall) {
        this.poshiMethodCall = poshiMethodCall;
    }

    public PsiReference[] getAllReferences() {
        List<PsiReference> psiReferences = new ArrayList<>();

        TextRange originalRange = poshiMethodCall.getTextRange();

        TextRange valueRange = TextRange.create(0, originalRange.getLength());

        String valueString = valueRange.substring(poshiMethodCall.getText());

        Matcher matcher = METHOD_CALL_REFERENCE_PATTERN.matcher(valueString);

        if (matcher.find()) {
            if (matcher.group(3) == null && matcher.group(2) == null) {
                TextRange textRange = TextRange.create(matcher.start(1), matcher.end(1));

                psiReferences.add(new PoshiNamespaceReference(poshiMethodCall, matcher.group(1), textRange));
                psiReferences.add(new PoshiClassReference(poshiMethodCall, null, matcher.group(1), textRange));
            } else if (matcher.group(3) == null) {
                TextRange textRange1 = TextRange.create(matcher.start(1), matcher.end(1));
                TextRange textRange2 = TextRange.create(matcher.start(2), matcher.end(2));

                psiReferences.add(new PoshiNamespaceReference(poshiMethodCall, matcher.group(1), textRange1));
                psiReferences.add(new PoshiClassReference(poshiMethodCall, matcher.group(1), matcher.group(2), textRange2));

                psiReferences.add(new PoshiClassReference(poshiMethodCall, null, matcher.group(1), textRange1));
                psiReferences.add(new PoshiMethodReference(poshiMethodCall, null, matcher.group(1), matcher.group(2), textRange2));
            } else {
                TextRange textRange1 = TextRange.create(matcher.start(1), matcher.end(1));
                TextRange textRange2 = TextRange.create(matcher.start(2), matcher.end(2));
                TextRange textRange3 = TextRange.create(matcher.start(3), matcher.end(3));

                psiReferences.add(new PoshiNamespaceReference(poshiMethodCall, matcher.group(1), textRange1));
                psiReferences.add(new PoshiClassReference(poshiMethodCall, matcher.group(1), matcher.group(2), textRange2));
                psiReferences.add(new PoshiMethodReference(poshiMethodCall, matcher.group(1), matcher.group(2), matcher.group(3), textRange3));
            }
        }

        return psiReferences.toArray(new PsiReference[0]);
    }
}
