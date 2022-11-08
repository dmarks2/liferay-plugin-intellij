package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInsight.TargetElementUtil;
import com.intellij.find.findUsages.PsiElement2UsageTargetAdapter;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNameValuePair;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.usages.UsageTarget;
import com.intellij.usages.UsageTargetProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MetaConfigurationUsageTargetProvider implements UsageTargetProvider {
    @Override
    public UsageTarget @Nullable [] getTargets(@NotNull Editor editor, @NotNull PsiFile file) {
        PsiElement element = file.findElementAt(TargetElementUtil.adjustOffset(file, editor.getDocument(), editor.getCaretModel().getOffset()));

        if (element == null) return null;

        PsiNameValuePair nameValuePair = PsiTreeUtil.getParentOfType(element, PsiNameValuePair.class);

        if (nameValuePair != null && "id".equals(nameValuePair.getName())) {
            PsiAnnotation annotation = PsiTreeUtil.getParentOfType(nameValuePair, PsiAnnotation.class);

            if (annotation != null && "aQute.bnd.annotation.metatype.Meta.OCD".equals(annotation.getQualifiedName())) {
                return new UsageTarget[] {new PsiElement2UsageTargetAdapter(element, true)};
            }
        }

        return null;
    }
}
