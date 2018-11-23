package de.dm.intellij.liferay.language.velocity;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiType;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.xml.XmlFile;
import com.intellij.velocity.VtlGlobalVariableProvider;
import com.intellij.velocity.psi.VtlVariable;
import com.intellij.velocity.psi.files.VtlFile;
import de.dm.intellij.liferay.language.TemplateVariableProcessor;
import de.dm.intellij.liferay.language.TemplateVariableProcessorUtil;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.theme.LiferayLookAndFeelXmlParser;
import de.dm.intellij.liferay.util.LiferayVersions;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;

public class LiferayVtlVariableProvider extends VtlGlobalVariableProvider implements TemplateVariableProcessor<VtlFile, VtlVariable> {

    @NotNull
    public Collection<VtlVariable> getGlobalVariables(@NotNull VtlFile vtlFile) {
        try {

            return TemplateVariableProcessorUtil.getGlobalVariables(this, vtlFile);

        } catch (ProcessCanceledException e) {
            return Collections.emptySet();
        }
    }

    public VtlVariable createVariable(final String name, final VtlFile parent, String typeText, PsiElement navigationalElement, final Collection<VtlVariable> nestedVariables, boolean repeatable) {
        if ("theme_settings".equals(name)) {
            return new CustomVtlVariable(name, parent, typeText, navigationalElement) {
                @Override
                public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {
                    final Module module = ModuleUtil.findModuleForPsiElement(parent);
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

                                    VtlVariable variable = new CustomVtlVariable(setting.key, parent, type, setting.psiElement);
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
            };
        }
        return new CustomVtlVariable(name, parent, typeText, navigationalElement, nestedVariables, false);
    }

    @Override
    public String[] getAdditionalLanguageSpecificResources(float liferayVersion) {
        if (
                (liferayVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                (liferayVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                ) { //Liferay 7.0
            return new String[] {
                    "/com/liferay/vtl/context_additional_velocity_70.vm"
            };
        }

        return new String[0];
    }

}
