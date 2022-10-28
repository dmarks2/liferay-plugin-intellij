package de.dm.intellij.liferay.theme;

import com.google.gson.stream.JsonToken;
import com.intellij.openapi.externalSystem.service.project.autoimport.FileChangeListenerBase;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ModuleRootModificationUtil;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.io.JsonReaderEx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Extracts parent Theme name from package.json.
 *
 * Extracts build and dist directories from package.json and add them as excluded directories for the IntelliJ Module.
 */
public class LiferayPackageJSONParser extends FileChangeListenerBase {

    public static void handleChange(Project project, VirtualFile virtualFile) {
        final Module module = ModuleUtil.findModuleForFile(virtualFile, project);
        if (module != null) {
            LiferayModuleComponent component = module.getService(LiferayModuleComponent.class);
            if (component != null) {

                try {
                    String fileText = FileUtil.loadTextAndClose(virtualFile.getInputStream());

                    if (StringUtil.isNotEmpty(fileText)) {

                        String baseTheme = null;
                        String pathBuild = "build";
                        String pathDist = "dist";

                        JsonReaderEx jsonReaderEx = new JsonReaderEx(fileText);
                        jsonReaderEx.beginObject();
                        while (jsonReaderEx.hasNext()) {
                            String name = jsonReaderEx.nextName();
                            if ("liferayTheme".equals(name)) {
                                jsonReaderEx.beginObject();
                                while (jsonReaderEx.hasNext()) {
                                    String subname = jsonReaderEx.nextName();
                                    if ("baseTheme".equals(subname)) {
                                        JsonToken jsonToken = jsonReaderEx.peek();

                                        if (JsonToken.BEGIN_OBJECT.equals(jsonToken)) {
                                            jsonReaderEx.beginObject();
                                            while (jsonReaderEx.hasNext()) {
                                                String subsubname = jsonReaderEx.nextName();

                                                if ("name".equals(subsubname)) {
                                                    baseTheme = jsonReaderEx.nextString();
                                                } else {
                                                    jsonReaderEx.skipValue();
                                                }
                                            }
                                            jsonReaderEx.endObject();
                                        } else {
                                            baseTheme = jsonReaderEx.nextString();
                                        }
                                    } else if ("pathBuild".equals(subname)) {
                                        pathBuild = jsonReaderEx.nextString();
                                    } else if ("pathDist".equals(subname)) {
                                        pathDist = jsonReaderEx.nextString();
                                    } else {
                                        jsonReaderEx.skipValue();
                                    }
                                }
                                jsonReaderEx.endObject();
                            } else {
                                jsonReaderEx.skipValue();
                            }
                        }
                        jsonReaderEx.endObject();

                        if (StringUtil.isNotEmpty(baseTheme)) {
                            baseTheme = StringUtil.unquoteString(baseTheme);

                            component.setParentTheme(baseTheme);
                        }

                        if (pathBuild.startsWith("./")) {
                            pathBuild = pathBuild.substring(3);
                        }

                        if (pathDist.startsWith("./")) {
                            pathDist = pathDist.substring(3);
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

                            ProjectUtils.runDumbAwareLater(project, () -> ModuleRootModificationUtil.updateExcludedFolders(
                                    module,
                                    sourceRoot,
                                    Collections.<String>emptyList(),
                                    excludeFolders
                            ));
                        }
                    }

                } catch (Exception e) {
                    //unparseable json?
                }
            }
        }
    }

    public static boolean isRelevantFile(String path) {
        return path.endsWith("package.json");
    }

    public static String getParentTheme(VirtualFile packageJsonFile) throws IOException {
        String fileText = FileUtil.loadTextAndClose(packageJsonFile.getInputStream());

        if (StringUtil.isNotEmpty(fileText)) {
            String baseTheme = null;

            try (JsonReaderEx jsonReaderEx = new JsonReaderEx(fileText)) {
                jsonReaderEx.beginObject();
                while (jsonReaderEx.hasNext()) {
                    String name = jsonReaderEx.nextName();
                    if ("liferayTheme".equals(name)) {
                        jsonReaderEx.beginObject();
                        while (jsonReaderEx.hasNext()) {
                            String subname = jsonReaderEx.nextName();
                            if ("baseTheme".equals(subname)) {
                                JsonToken jsonToken = jsonReaderEx.peek();

                                if (JsonToken.BEGIN_OBJECT.equals(jsonToken)) {
                                    jsonReaderEx.beginObject();
                                    while (jsonReaderEx.hasNext()) {
                                        String subsubname = jsonReaderEx.nextName();

                                        if ("name".equals(subsubname)) {
                                            baseTheme = jsonReaderEx.nextString();
                                        } else {
                                            jsonReaderEx.skipValue();
                                        }
                                    }
                                    jsonReaderEx.endObject();
                                } else {
                                    baseTheme = jsonReaderEx.nextString();
                                }
                            } else {
                                jsonReaderEx.skipValue();
                            }
                        }
                        jsonReaderEx.endObject();
                    } else {
                        jsonReaderEx.skipValue();
                    }
                }
                jsonReaderEx.endObject();
            }

            if (StringUtil.isNotEmpty(baseTheme)) {
                baseTheme = StringUtil.unquoteString(baseTheme);
            }

            return baseTheme;
        }

        return null;
    }

    @Override
    protected boolean isRelevant(String path) {
        return isRelevantFile(path);
    }

    @Override
    protected void updateFile(VirtualFile virtualFile, VFileEvent vFileEvent) {
        Project project = ProjectUtil.guessProjectForFile(virtualFile);
        if (project != null) {
            handleChange(project, virtualFile);
        }
    }

    @Override
    protected void deleteFile(VirtualFile virtualFile, VFileEvent vFileEvent) {
    }

    @Override
    protected void apply() {
    }
}
