package de.dm.intellij.liferay.language.velocity;

import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.psi.PsiFile;
import com.intellij.velocity.VtlGlobalMacroProvider;
import com.intellij.velocity.psi.VtlMacro;
import com.intellij.velocity.psi.files.VtlFile;
import de.dm.intellij.liferay.language.TemplateMacroProcessor;
import de.dm.intellij.liferay.language.TemplateMacroProcessorUtil;
import de.dm.intellij.liferay.util.LiferayVersions;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class LiferayVtlMacroProvider extends VtlGlobalMacroProvider implements TemplateMacroProcessor<VtlFile, VtlMacro> {

    @NotNull
    public Collection<VtlMacro> getGlobalMacros(@NotNull VtlFile vtlFile) {
        try {

            return TemplateMacroProcessorUtil.getGlobalMacros(this, vtlFile);

        } catch (ProcessCanceledException e) {
            return Collections.emptySet();
        }
    }

    public Set<VtlMacro> getMacrosFromFile(PsiFile psiFile) {
        if (psiFile instanceof VtlFile) {
            return ((VtlFile)psiFile).getDefinedMacros();
        }
        return Collections.emptySet();
    }

    public String getMacroFileName(float liferayVersion) {
        if (liferayVersion == LiferayVersions.LIFERAY_VERSION_6_1) {
            return "/com/liferay/vtl/VM_liferay_61.vm";
        } else if (liferayVersion == LiferayVersions.LIFERAY_VERSION_6_2) {
            return "/com/liferay/vtl/VM_liferay_62.vm";
        } else if
                (
                    (liferayVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                    (liferayVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                ) {
            return "/com/liferay/vtl/VM_liferay_70.vm";
        }

        return null;
    }
}
