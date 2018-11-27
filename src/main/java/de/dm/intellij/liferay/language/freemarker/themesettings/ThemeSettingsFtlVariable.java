package de.dm.intellij.liferay.language.freemarker.themesettings;

import com.intellij.freemarker.psi.variables.FtlLightVariable;
import com.intellij.freemarker.psi.variables.FtlSpecialVariableType;
import com.intellij.freemarker.psi.variables.FtlVariable;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.xml.XmlFile;
import de.dm.intellij.liferay.language.freemarker.custom.CustomFtlVariable;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.theme.LiferayLookAndFeelXmlParser;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Collection;

public class ThemeSettingsFtlVariable extends FtlLightVariable {

    public static final String VARIABLE_NAME = "theme_settings";

    public ThemeSettingsFtlVariable(PsiElement parent) {
        super(VARIABLE_NAME, parent, getThemeSettingsVariableType(parent));
    }

    @Override
    public Icon getIcon(boolean open) {
        return Icons.LIFERAY_ICON;
    }

    private static FtlSpecialVariableType getThemeSettingsVariableType(final PsiElement parent) {
        return new FtlSpecialVariableType() {
            public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull PsiElement place, ResolveState state) {
                final Module module = ModuleUtil.findModuleForPsiElement(parent);
                String liferayLookAndFeelXml = LiferayModuleComponent.getLiferayLookAndFeelXml(module);
                if ((liferayLookAndFeelXml != null) && (liferayLookAndFeelXml.trim().length() > 0)) {
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

                                FtlVariable variable = new CustomFtlVariable(setting.key, place, type, setting.psiElement);
                                processor.execute(variable, state);
                            }
                        }
                        break;
                    }
                }

                return true;
            }
        };
    }

}
