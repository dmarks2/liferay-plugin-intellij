package de.dm.intellij.liferay.language;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiNamedElement;
import de.dm.intellij.liferay.module.LiferayModuleComponent;

import java.net.URL;
import java.util.Collection;
import java.util.Collections;

public class TemplateMacroProcessorUtil {

    public static <F extends PsiFile, T extends PsiNamedElement> Collection<T> getGlobalMacros(TemplateMacroProcessor<F, T> templateMacroProcessor, F file) {
        F templateFile = (F)file.getOriginalFile();

        final Module module = ModuleUtil.findModuleForPsiElement(templateFile);
        if (module == null) {
            return Collections.emptyList();
        }
        LiferayModuleComponent component = module.getService(LiferayModuleComponent.class);
        if (component != null) {
            float portalMajorVersion = component.getPortalMajorVersion();

            String macroFileName = templateMacroProcessor.getMacroFileName(portalMajorVersion);
            if (macroFileName != null) {
                URL url = TemplateMacroProcessorUtil.class.getResource(macroFileName);
                if (url != null) {
                    VirtualFile macroFile = VfsUtil.findFileByURL(url);

                    if (macroFile != null) {
                        if (macroFile.isValid()) {
                            PsiFile macroPsiFile = PsiManager.getInstance(module.getProject()).findFile(macroFile);

                            return templateMacroProcessor.getMacrosFromFile(macroPsiFile);
                        }
                    }
                }
            }

        }

        return Collections.emptyList();
    }

}

