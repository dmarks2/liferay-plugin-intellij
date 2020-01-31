package de.dm.intellij.bndtools.parser;

import com.intellij.codeInsight.daemon.JavaErrorMessages;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiReference;
import de.dm.intellij.bndtools.psi.BndHeader;
import de.dm.intellij.bndtools.psi.BndHeaderValue;
import de.dm.intellij.bndtools.psi.BndHeaderValuePart;
import de.dm.intellij.bndtools.psi.Clause;
import de.dm.intellij.bndtools.psi.util.BndPsiUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BasePackageParser extends BndHeaderParser {

    public static final BasePackageParser INSTANCE = new BasePackageParser();

    @NotNull
    @Override
    public PsiReference[] getReferences(@NotNull BndHeaderValuePart bndHeaderValuePart) {
        if (bndHeaderValuePart.getParent() instanceof Clause) {
            return BndPsiUtil.getPackageReferences(bndHeaderValuePart);
        }

        return PsiReference.EMPTY_ARRAY;
    }

    @Override
    public boolean annotate(@NotNull BndHeader bndHeader, @NotNull AnnotationHolder holder) {
        boolean annotated = false;

        for (BndHeaderValue bndHeaderValue : bndHeader.getBndHeaderValues()) {
            if (bndHeaderValue instanceof Clause) {
                Clause clause = (Clause)bndHeaderValue;

                BndHeaderValuePart bndHeaderValuePart = clause.getValue();

                if (bndHeaderValuePart != null) {
                    String packageName = bndHeaderValuePart.getUnwrappedText();

                    packageName = StringUtil.trimEnd(packageName, ".*");

                    if (StringUtil.isEmptyOrSpaces(packageName)) {
                        holder.createErrorAnnotation(
                            bndHeaderValuePart.getHighlightingRange(),
                            "Invalid reference"
                        );

                        annotated = true;

                        continue;
                    }

                    packageName = packageName.trim();
                    if (packageName.charAt(0) == '!') {
                        packageName = packageName.substring(1);
                    }

                    if (! "*".equals(packageName)) {
                        PsiDirectory[] psiDirectories = BndPsiUtil.resolvePackage(bndHeader, packageName);

                        if (psiDirectories.length == 0) {
                            holder.createErrorAnnotation(
                                bndHeaderValuePart.getHighlightingRange(),
                                JavaErrorMessages.message("cannot.resolve.package", packageName)
                            );
                            annotated = true;
                        }
                    }
                }
            }
        }

        return annotated;
    }

    @Nullable
    @Override
    public Object getConvertedValue(@NotNull BndHeader bndHeader) {
        List<BndHeaderValue> bndHeaderValues = bndHeader.getBndHeaderValues();

        if (! bndHeaderValues.isEmpty()) {
            List<String> packages = new ArrayList<>();

            for (BndHeaderValue bndHeaderValue : bndHeaderValues) {
                if (bndHeaderValue instanceof Clause) {
                    Clause clause = (Clause) bndHeaderValue;

                    BndHeaderValuePart bndHeaderValuePart = clause.getValue();

                    if (bndHeaderValuePart != null) {
                        String packageName = bndHeaderValuePart.getText();

                        packageName = packageName.replaceAll("\\s+", "");

                        packageName = packageName.trim();
                        if (packageName.charAt(0) == '!') {
                            packageName = packageName.substring(1);
                        }

                        packages.add(packageName);
                    }
                }
            }

            return packages;
        }

        return null;
    }

}
