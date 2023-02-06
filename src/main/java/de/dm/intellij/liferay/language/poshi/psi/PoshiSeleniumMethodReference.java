package de.dm.intellij.liferay.language.poshi.psi;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiReferenceBase;
import de.dm.intellij.liferay.language.poshi.constants.PoshiConstants;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PoshiSeleniumMethodReference extends PsiReferenceBase<PsiElement> {

    public PoshiSeleniumMethodReference(@NotNull PsiElement element, TextRange textRange) {
        super(element, textRange);
    }

    @Override
    public @Nullable PsiElement resolve() {
        String valueString = getRangeInElement().substring(getElement().getText());

        if (StringUtil.isNotEmpty(valueString)) {
            URL url = PoshiSeleniumReference.class.getResource(PoshiConstants.LIFERAY_SELENIUM_FILE_PATH);

            if (url != null) {
                VirtualFile liferaySeleniumFile = VfsUtil.findFileByURL(url);

                if (liferaySeleniumFile != null) {
                    if (liferaySeleniumFile.isValid()) {
                        PsiFile psiFile = PsiManager.getInstance(getElement().getProject()).findFile(liferaySeleniumFile);

                        if (psiFile instanceof PsiJavaFile) {
                            PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;

                            PsiClass[] classes = psiJavaFile.getClasses();

                            if (classes.length > 0) {
                                PsiClass liferaySeleniumClass = classes[0];

                                PsiMethod[] methods = liferaySeleniumClass.getMethods();

                                for (PsiMethod method : methods) {
                                    if (valueString.equals(method.getName())) {
                                        return method;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    @Override
    public Object @NotNull [] getVariants() {
        List<Object> result = new ArrayList<>();

        URL url = PoshiSeleniumReference.class.getResource(PoshiConstants.LIFERAY_SELENIUM_FILE_PATH);

        if (url != null) {
            VirtualFile liferaySeleniumFile = VfsUtil.findFileByURL(url);

            if (liferaySeleniumFile != null) {
                if (liferaySeleniumFile.isValid()) {
                    PsiFile psiFile = PsiManager.getInstance(getElement().getProject()).findFile(liferaySeleniumFile);

                    if (psiFile instanceof PsiJavaFile) {
                        PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;

                        PsiClass[] classes = psiJavaFile.getClasses();

                        if (classes.length > 0) {
                            PsiClass liferaySeleniumClass = classes[0];

                            PsiMethod[] methods = liferaySeleniumClass.getMethods();

                            for (PsiMethod method : methods) {
                                result.add(LookupElementBuilder.create(method.getName()).withPsiElement(method).withIcon(Icons.LIFERAY_ICON));
                            }
                        }
                    }
                }
            }
        }

        return result.toArray(new Object[0]);
    }
}
