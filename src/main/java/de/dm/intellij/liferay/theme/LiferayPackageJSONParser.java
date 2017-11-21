package de.dm.intellij.liferay.theme;

import com.intellij.json.psi.JsonFile;
import com.intellij.json.psi.JsonObject;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonValue;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ModuleRootModificationUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileEvent;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import de.dm.intellij.liferay.module.LiferayModuleComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Extracts parent Theme name from package.json.
 *
 * Extracts build and dist directories from package.json and add them as excluded directories for the IntelliJ Module.
 */
public class LiferayPackageJSONParser {

    public static void handleChange(Project project, VirtualFileEvent event) {
        if ("package.json".equals(event.getFileName())) {
            VirtualFile virtualFile = event.getFile();
            final Module module = ModuleUtil.findModuleForFile(virtualFile, project);
            if (module != null) {
                LiferayModuleComponent component = module.getComponent(LiferayModuleComponent.class);
                if (component != null) {
                    JsonFile jsonFile = (JsonFile) PsiManager.getInstance(module.getProject()).findFile(virtualFile);
                    if (jsonFile != null) {
                        JsonValue root = jsonFile.getTopLevelValue();
                        if (root != null) {
                            for (PsiElement value : root.getChildren()) {
                                if (value instanceof JsonProperty) {
                                    JsonProperty property = (JsonProperty) value;
                                    if ("liferayTheme".equals(property.getName())) {
                                        JsonValue jsonValue = property.getValue();
                                        if (jsonValue instanceof JsonObject) {
                                            JsonObject jsonObject = (JsonObject) jsonValue;

                                            JsonProperty baseThemeProperty = jsonObject.findProperty("baseTheme");
                                            if (baseThemeProperty != null) {
                                                String baseTheme = baseThemeProperty.getValue().getText();
                                                if ((baseTheme != null) && (baseTheme.trim().length() > 0)) {
                                                    baseTheme = StringUtil.unquoteString(baseTheme);

                                                    component.setParentTheme(baseTheme);
                                                }
                                            }

                                            /*
                                                Excluding build and dist directories
                                             */

                                            //Build paths, see https://github.com/liferay/liferay-theme-tasks
                                            JsonProperty pathBuildProperty = jsonObject.findProperty("pathBuild");
                                            JsonProperty pathDistProperty = jsonObject.findProperty("pathDist");

                                            String pathBuild = "build";
                                            if (pathBuildProperty != null) {
                                                pathBuild = pathBuildProperty.getValue().getText();
                                                if (pathBuild.startsWith("./")) {
                                                    pathBuild = pathBuild.substring(3);
                                                }
                                            }
                                            String pathDist = "dist";
                                            if (pathDistProperty != null) {
                                                pathDist = pathDistProperty.getValue().getText();
                                                if (pathDist.startsWith("./")) {
                                                    pathDist = pathDist.substring(3);
                                                }
                                            }
                                            ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);

                                            for (VirtualFile sourceRoot : moduleRootManager.getContentRoots()) {
                                                Collection<String> excludeFolders = new ArrayList<String>();

                                                VirtualFile pathBuildFile = sourceRoot.findFileByRelativePath(pathBuild);
                                                VirtualFile pathDistFile = sourceRoot.findFileByRelativePath(pathDist);
                                                if (pathBuildFile != null) {
                                                    excludeFolders.add(pathBuildFile.getUrl());
                                                }
                                                if (pathDistFile != null) {
                                                    excludeFolders.add(pathDistFile.getUrl());
                                                }

                                                ModuleRootModificationUtil.updateExcludedFolders(
                                                        module,
                                                        sourceRoot,
                                                        Collections.<String>emptyList(),
                                                        excludeFolders
                                                        );

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
