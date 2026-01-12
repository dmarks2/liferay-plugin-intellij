package de.dm.intellij.liferay.theme;

import com.google.gson.stream.JsonToken;
import com.intellij.openapi.externalSystem.service.project.autoimport.FileChangeListenerBase;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.roots.ContentEntry;
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

/**
 * Extracts parent Theme name from package.json.
 * Extracts build and dist directories from package.json and add them as excluded directories for the IntelliJ Module.
 */
public class LiferayPackageJSONParser extends FileChangeListenerBase {

    public static class PackageJSONInfo {
        public String baseTheme;
        public String pathBuild = "build";
        public String pathDist = "dist";
    }

    public static void handleChange(Project project, VirtualFile virtualFile) {
        final Module module = ModuleUtil.findModuleForFile(virtualFile, project);
        if (module != null) {
            LiferayModuleComponent component = module.getService(LiferayModuleComponent.class);

            if (component != null) {
                try {
                    PackageJSONInfo packageJSONInfo = getPackageJSONInfo(virtualFile);

                    if (packageJSONInfo != null) {
                        if (StringUtil.isNotEmpty(packageJSONInfo.baseTheme)) {
                            component.setParentTheme(packageJSONInfo.baseTheme);
                        }

                        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);

                        ProjectUtils.runDumbAwareLater(project, () -> {
                            if (!module.isDisposed()) {
                                ModuleRootModificationUtil.updateModel(module, model -> {
                                    for (VirtualFile sourceRoot : moduleRootManager.getContentRoots()) {
                                        VirtualFile pathBuildFile = sourceRoot.findFileByRelativePath(packageJSONInfo.pathBuild);
                                        VirtualFile pathDistFile = sourceRoot.findFileByRelativePath(packageJSONInfo.pathDist);

                                        for (ContentEntry contentEntry : model.getContentEntries()) {
                                            if (sourceRoot.equals(contentEntry.getFile())) {
                                                if (pathBuildFile != null) {
                                                    if (!isExcluded(contentEntry, pathBuildFile)) {
                                                        contentEntry.addExcludeFolder(pathBuildFile);
                                                    }
                                                }
                                                if (pathDistFile != null) {
                                                    if (!isExcluded(contentEntry, pathDistFile)) {
                                                        contentEntry.addExcludeFolder(pathDistFile);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                } catch (Exception e) {
                    //unparseable json?
                }
            }
        }
    }

    private static boolean isExcluded(ContentEntry contentEntry, VirtualFile file) {
        for (String url : contentEntry.getExcludeFolderUrls()) {
            if (url.equals(file.getUrl())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRelevantFile(String path) {
        return path.endsWith("package.json");
    }

    public static PackageJSONInfo getPackageJSONInfo(VirtualFile packageJsonFile) throws IOException {
        String fileText = FileUtil.loadTextAndClose(packageJsonFile.getInputStream());

        if (StringUtil.isNotEmpty(fileText)) {
            PackageJSONInfo packageJSONInfo = new PackageJSONInfo();

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
                                            packageJSONInfo.baseTheme = jsonReaderEx.nextString();
                                        } else {
                                            jsonReaderEx.skipValue();
                                        }
                                    }
                                    jsonReaderEx.endObject();
                                } else {
                                    packageJSONInfo.baseTheme = jsonReaderEx.nextString();
                                }
                            } else if ("pathBuild".equals(subname)) {
                                packageJSONInfo.pathBuild = jsonReaderEx.nextString();
                            } else if ("pathDist".equals(subname)) {
                                packageJSONInfo.pathDist = jsonReaderEx.nextString();
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

            if (StringUtil.isNotEmpty(packageJSONInfo.baseTheme)) {
                packageJSONInfo.baseTheme = StringUtil.unquoteString(packageJSONInfo.baseTheme);
            }

            if (packageJSONInfo.pathBuild.startsWith("./")) {
                packageJSONInfo.pathBuild = packageJSONInfo.pathBuild.substring(3);
            }

            if (packageJSONInfo.pathDist.startsWith("./")) {
                packageJSONInfo.pathDist = packageJSONInfo.pathDist.substring(3);
            }

            return packageJSONInfo;
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
