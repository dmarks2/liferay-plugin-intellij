package de.dm.intellij.bndtools.parser;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiReference;
import de.dm.intellij.bndtools.psi.BndHeader;
import de.dm.intellij.bndtools.psi.BndHeaderValue;
import de.dm.intellij.bndtools.psi.BndHeaderValuePart;
import de.dm.intellij.bndtools.psi.Clause;
import de.dm.intellij.bndtools.psi.util.BndPsiUtil;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.lang.manifest.ManifestBundle;

public class FileReferenceParser extends BndHeaderParser {

    public static final FileReferenceParser INSTANCE = new FileReferenceParser();

    @Override
    public boolean annotate(@NotNull BndHeader bndHeader, @NotNull AnnotationHolder holder) {
        BndHeaderValue bndHeaderValue = bndHeader.getBndHeaderValue();

        BndHeaderValuePart bndHeaderValuePart = null;

        if (bndHeaderValue instanceof BndHeaderValuePart) {
            bndHeaderValuePart = (BndHeaderValuePart)bndHeaderValue;
        }
        else if (bndHeaderValue instanceof Clause clause) {
			bndHeaderValuePart = clause.getValue();
        }

        if (bndHeaderValuePart == null) {
            return false;
        }

        String filePath = bndHeaderValuePart.getUnwrappedText();

        if (StringUtil.isEmptyOrSpaces(filePath)) {
            holder.newAnnotation(
                    HighlightSeverity.ERROR, ManifestBundle.message("header.reference.invalid")
            ).range(
                    bndHeaderValuePart.getHighlightingRange()
            ).create();

            return true;
        }

        Module module = ModuleUtilCore.findModuleForPsiElement(bndHeader);

        if (module != null) {
            ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);

            VirtualFile[] sourceRoots = moduleRootManager.getSourceRoots(false);

            String relativeFilePath = filePath;

            if (relativeFilePath.startsWith("/")) {
                relativeFilePath = relativeFilePath.substring(1);
            }

            for (VirtualFile sourceRoot : sourceRoots) {
                if (LiferayFileUtil.getChild(sourceRoot, relativeFilePath) != null) {
                    return false;
                }
            }
        }

        String message = "Cannot resolve file '" + filePath + "'";

        holder.newAnnotation(
                HighlightSeverity.ERROR, message
        ).range(
                bndHeaderValuePart.getHighlightingRange()
        ).highlightType(
                ProblemHighlightType.LIKE_UNKNOWN_SYMBOL
        ).create();

        return true;
    }

    @NotNull
    @Override
    public PsiReference[] getReferences(@NotNull BndHeaderValuePart bndHeaderValuePart) {
        if (bndHeaderValuePart.getParent() instanceof Clause) {
            return BndPsiUtil.getFileReferences(bndHeaderValuePart);
        }

        return PsiReference.EMPTY_ARRAY;
    }

}
