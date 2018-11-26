package de.dm.intellij.liferay.language.velocity.themesettings;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiType;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.xml.XmlFile;
import com.intellij.velocity.psi.VtlLightVariable;
import com.intellij.velocity.psi.VtlVariable;
import com.intellij.velocity.psi.files.VtlFile;
import de.dm.intellij.liferay.language.velocity.CustomVtlVariable;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.theme.LiferayLookAndFeelXmlParser;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class ThemeSettingsVtlVariable extends VtlLightVariable {

    public static final String VARIABLE_NAME = "theme_settings";

    public ThemeSettingsVtlVariable(@NotNull VtlFile parent) {
        super(VARIABLE_NAME, parent, "java.util.Properties");
    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {
        final Module module = ModuleUtil.findModuleForPsiElement(getParent());
        String liferayLookAndFeelXml = LiferayModuleComponent.getLiferayLookAndFeelXml(module);
        if ( (liferayLookAndFeelXml != null) && (liferayLookAndFeelXml.trim().length() > 0) ) {
            ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
            VirtualFile[] contentRoots = moduleRootManager.getContentRoots();
            for (VirtualFile contentRoot : contentRoots) {
                String relativeFileUrl = liferayLookAndFeelXml;

                String contentRootUrl = contentRoot.getUrl();

                if (relativeFileUrl.startsWith(contentRootUrl)) {
                    relativeFileUrl = relativeFileUrl.substring(contentRootUrl.length());
                }

                VirtualFile virtualFile = VfsUtilCore.findRelativeFile(relativeFileUrl, contentRoot);
                if (virtualFile != null) {
                    XmlFile xmlFile = (XmlFile) PsiManager.getInstance(module.getProject()).findFile(virtualFile);
                    Collection<LiferayLookAndFeelXmlParser.Setting> settings = LiferayLookAndFeelXmlParser.parseSettings(xmlFile);
                    for (LiferayLookAndFeelXmlParser.Setting setting : settings) {
                        String type = ("checkbox".equals(setting.type) ? "java.lang.Boolean" : "java.lang.String");

                        VtlVariable variable = new CustomVtlVariable(setting.key, (VtlFile) getParent(), type, setting.psiElement);
                        processor.execute(variable, state);
                    }

                    break;
                }
            }
        }

        return true;
    }

    @Override
    public PsiType getPsiType() {
        return null;
    }




}
