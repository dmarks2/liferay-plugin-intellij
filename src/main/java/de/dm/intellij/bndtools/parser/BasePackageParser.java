package de.dm.intellij.bndtools.parser;

import com.intellij.codeInsight.daemon.JavaErrorMessages;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiReference;
import de.dm.intellij.bndtools.psi.Clause;
import de.dm.intellij.bndtools.psi.util.BndPsiUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.lang.manifest.psi.Header;
import org.jetbrains.lang.manifest.psi.HeaderValue;
import org.jetbrains.lang.manifest.psi.HeaderValuePart;

import java.util.ArrayList;
import java.util.List;

public class BasePackageParser extends OsgiHeaderParser {

    public static final BasePackageParser INSTANCE = new BasePackageParser();

    @NotNull
    @Override
    public PsiReference[] getReferences(@NotNull HeaderValuePart headerValuePart) {
        if (headerValuePart.getParent() instanceof Clause) {
            return BndPsiUtil.getPackageReferences(headerValuePart);
        }

        return PsiReference.EMPTY_ARRAY;
    }

    @Override
    public boolean annotate(@NotNull Header header, @NotNull AnnotationHolder holder) {
        boolean annotated = false;

        for (HeaderValue headerValue : header.getHeaderValues()) {
            if (headerValue instanceof Clause) {
                Clause clause = (Clause)headerValue;

                HeaderValuePart headerValuePart = clause.getValue();

                if (headerValuePart != null) {
                    String packageName = headerValuePart.getUnwrappedText();

                    packageName = StringUtil.trimEnd(packageName, ".*");

                    if (StringUtil.isEmptyOrSpaces(packageName)) {
                        holder.createErrorAnnotation(
                            headerValuePart.getHighlightingRange(),
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
                        PsiDirectory[] psiDirectories = BndPsiUtil.resolvePackage(header, packageName);

                        if (psiDirectories.length == 0) {
                            holder.createErrorAnnotation(
                                headerValuePart.getHighlightingRange(),
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
    public Object getConvertedValue(@NotNull Header header) {
        List<HeaderValue> headerValues = header.getHeaderValues();

        if (! headerValues.isEmpty()) {
            List<String> packages = new ArrayList<>();

            for (HeaderValue headerValue : headerValues) {
                if (headerValue instanceof Clause) {
                    Clause clause = (Clause) headerValue;

                    HeaderValuePart headerValuePart = clause.getValue();

                    if (headerValuePart != null) {
                        String packageName = headerValuePart.getText();

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
