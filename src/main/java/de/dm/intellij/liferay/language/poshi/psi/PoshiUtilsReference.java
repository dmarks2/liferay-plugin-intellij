package de.dm.intellij.liferay.language.poshi.psi;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.io.FileUtilRt;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiReferenceBase;
import de.dm.intellij.liferay.language.poshi.constants.PoshiConstants;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PoshiUtilsReference extends PsiReferenceBase<PsiElement> {

    public PoshiUtilsReference(@NotNull PsiElement element, TextRange textRange) {
        super(element, textRange);
    }
    @Override
    public @Nullable PsiElement resolve() {
        String valueString = getRangeInElement().substring(getElement().getText());

        return getPoshiUtilsClassFile(valueString, getElement().getProject());
    }

    @Override
    public Object @NotNull [] getVariants() {
        List<Object> result = new ArrayList<Object>();

        for (String resourceFile : PoshiConstants.POSHI_UTILS_CLASSES) {
            String poshiUtilsName = resourceFile.substring(resourceFile.lastIndexOf(File.separator) + 1);

            poshiUtilsName = FileUtilRt.getNameWithoutExtension(poshiUtilsName);

            URL url = PoshiUtilsReference.class.getResource(PoshiConstants.POSHI_UTILS_PATH + "/" + poshiUtilsName + ".java");

            if (url != null) {
                VirtualFile poshiUtilsFile = VfsUtil.findFileByURL(url);

                if (poshiUtilsFile != null) {
                    if (poshiUtilsFile.isValid()) {

                        PsiFile psiFile = PsiManager.getInstance(getElement().getProject()).findFile(poshiUtilsFile);

                        if (psiFile != null) {
                            result.add(LookupElementBuilder.create(poshiUtilsName).withPsiElement(psiFile).withIcon(Icons.LIFERAY_ICON));
                        }
                    }
                }
            }
        }

        return result.toArray(new Object[0]);
    }

    public static PsiFile getPoshiUtilsClassFile(String valueString, Project project) {

        for (String resourceFile : PoshiConstants.POSHI_UTILS_CLASSES) {
            String poshiUtilsName = resourceFile.substring(resourceFile.lastIndexOf(File.separator) + 1);

            poshiUtilsName = FileUtilRt.getNameWithoutExtension(poshiUtilsName);

            if (valueString.equals(poshiUtilsName)) {
                URL url = PoshiUtilsReference.class.getResource(PoshiConstants.POSHI_UTILS_PATH + "/" + poshiUtilsName + ".java");

                if (url != null) {
                    VirtualFile poshiUtilsFile = VfsUtil.findFileByURL(url);

                    if (poshiUtilsFile != null) {
                        if (poshiUtilsFile.isValid()) {
                            return PsiManager.getInstance(project).findFile(poshiUtilsFile);
                        }
                    }
                }
            }
        }

        return null;
    }
}
