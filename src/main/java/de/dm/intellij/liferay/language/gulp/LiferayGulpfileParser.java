package de.dm.intellij.liferay.language.gulp;

import com.google.gson.JsonParseException;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.externalSystem.service.project.autoimport.FileChangeListenerBase;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModifiableModelsProvider;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.SourceFolder;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.io.JsonReaderEx;

import java.io.IOException;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LiferayGulpfileParser extends FileChangeListenerBase {

    private static final Pattern DECLARE_LIFERAY_THEME_TASKS = Pattern.compile("(\\w+)[ ]*=[ ]*require\\(['\"]liferay-theme-tasks['\"]\\)");

    public static void handleChange(Project project, VirtualFile virtualFile) {
        final Module module = ModuleUtil.findModuleForFile(virtualFile, project);
        if (module != null) {

            if (virtualFile.getLength() > 0) {
                try {
                    String fileText = FileUtil.loadTextAndClose(virtualFile.getInputStream());

                    Matcher declareLiferayThemeTaskMatcher = DECLARE_LIFERAY_THEME_TASKS.matcher(fileText);
                    if (declareLiferayThemeTaskMatcher.find()) {
                        String liferayThemeTaskVariableName = declareLiferayThemeTaskMatcher.group(1);

                        int index = fileText.indexOf(liferayThemeTaskVariableName + ".registerTasks(");

                        if (index > -1) {
                            int openBracketIndex = index + (liferayThemeTaskVariableName + ".registerTasks(").length() - 1;

                            int closingBracketIndex = getMatchingBracket(fileText, openBracketIndex);

                            if (closingBracketIndex > -1) {
                                String jsonExpression = fileText.substring(openBracketIndex + 1, closingBracketIndex);

                                String pathSrc = "src";

                                try (JsonReaderEx jsonReaderEx = new JsonReaderEx(jsonExpression)) {
                                    jsonReaderEx.setLenient(true);

                                    jsonReaderEx.beginObject();

                                    while (jsonReaderEx.hasNext()) {
                                        String name = jsonReaderEx.nextName();
                                        if ("pathSrc".equals(name)) {
                                            pathSrc = jsonReaderEx.nextString();
                                        } else {
                                            jsonReaderEx.skipValue();
                                        }
                                    }

                                    jsonReaderEx.endObject();
                                } catch (JsonParseException e) {
                                    //content is probably not valid JSON, ignore for now.
                                }

                                if (pathSrc.length() > 0) {
                                    if (pathSrc.startsWith("./")) {
                                        pathSrc = pathSrc.substring(2);
                                    }

                                    VirtualFile sourceRootVirtualFile = LiferayFileUtil.getFileInContentRoot(module, pathSrc);

                                    if (sourceRootVirtualFile != null) {

                                        final ModifiableModelsProvider modelsProvider = ModifiableModelsProvider.getInstance();
                                        final ModifiableRootModel model = modelsProvider.getModuleModifiableModel(module);

                                        ApplicationManager.getApplication().runWriteAction(
                                                () -> {
                                                    ContentEntry[] contentEntries = model.getContentEntries();
                                                    if (contentEntries.length > 0) {
                                                        ContentEntry contentEntry = contentEntries[0];

                                                        if (! ApplicationManager.getApplication().isUnitTestMode()) {
                                                            SourceFolder[] sourceFolders = contentEntry.getSourceFolders();

                                                            for (SourceFolder sourceFolder : sourceFolders) {
                                                                if (!sourceFolder.isTestSource()) {
                                                                    contentEntry.removeSourceFolder(sourceFolder);
                                                                }
                                                            }
                                                        }

                                                        contentEntry.addSourceFolder(sourceRootVirtualFile, false);
                                                    }
                                                    modelsProvider.commitModuleModifiableModel(model);
                                                }
                                        );
                                    }
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    //unable to read the file, ignore?
                }
            }
        }
    }

    public static boolean isRelevantFile(String path) {
        return path.endsWith("gulpfile.js");
    }

    @Override
    protected boolean isRelevant(String path) {
        return isRelevantFile(path);
    }

    @Override
    protected void updateFile(VirtualFile virtualFile, VFileEvent event) {
        Project project = ProjectUtil.guessProjectForFile(virtualFile);
        if (project != null) {
            handleChange(project, virtualFile);
        }
    }

    @Override
    protected void deleteFile(VirtualFile file, VFileEvent event) {

    }

    @Override
    protected void apply() {

    }

    private static int getMatchingBracket(String expression, int index) {
        int i;

        if (expression.charAt(index) != '(') {
            return -1;
        }

        Stack<Integer> st = new Stack<>();

        for (i = index; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') {
                st.push((int) expression.charAt(i));
            } else if (expression.charAt(i) == ')') {
                st.pop();

                if (st.empty()) {
                    return i;
                }
            }
        }

        return -1;
    }
}
