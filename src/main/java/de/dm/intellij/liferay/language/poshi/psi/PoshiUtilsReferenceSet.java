package de.dm.intellij.liferay.language.poshi.psi;

import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiReference;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PoshiUtilsReferenceSet {

    private static final Pattern POSHI_UTILS_REFERENCE_PATTERN = Pattern.compile("^([A-Za-z]+)?\\.?([A-Za-z]+)?");

    private PoshiMethodCall poshiMethodCall;

    public PoshiUtilsReferenceSet(PoshiMethodCall poshiMethodCall) {
        this.poshiMethodCall = poshiMethodCall;
    }

    public PsiReference[] getAllReferences() {
        List<PsiReference> psiReferences = new ArrayList<>();

        TextRange originalRange = poshiMethodCall.getTextRange();

        TextRange valueRange = TextRange.create(0, originalRange.getLength());

        String valueString = valueRange.substring(poshiMethodCall.getText());

        Matcher matcher = POSHI_UTILS_REFERENCE_PATTERN.matcher(valueString);

        if (matcher.find()) {
            if (StringUtil.isNotEmpty(matcher.group(1))) {
                TextRange textRange1 = TextRange.create(matcher.start(1), matcher.end(1));

                psiReferences.add(new PoshiUtilsReference(poshiMethodCall, textRange1));

                if (StringUtil.isNotEmpty(matcher.group(2))) {
                    TextRange textRange2 = TextRange.create(matcher.start(2), matcher.end(2));

                    psiReferences.add(new PoshiUtilsMethodReference(poshiMethodCall, textRange2, matcher.group(1)));
                }
            }
        }

        return psiReferences.toArray(new PsiReference[0]);
    }

}
