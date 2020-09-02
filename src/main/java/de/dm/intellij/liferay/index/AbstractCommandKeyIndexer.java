package de.dm.intellij.liferay.index;

import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.project.IndexNotReadyException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import com.intellij.util.indexing.ID;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class AbstractCommandKeyIndexer extends AbstractComponentPropertyIndexer<CommandKey> {

    public static List<String> getCommands(@NotNull ID<CommandKey, Void> name, @NotNull String portletName, Project project, GlobalSearchScope scope) {
        return ReadAction.compute(
            () -> {
                final List<CommandKey> result = new ArrayList<>();
                final List<String> commands = new ArrayList<>();

                try {
                    FileBasedIndex.getInstance().processAllKeys(
                        name,
                        commandKey -> {
                            result.add(commandKey);
                            return true;
                        },
                        scope,
                        null
                    );

                } catch (IndexNotReadyException e) {
                    //ignore
                }

                for (CommandKey commandKey : result) {
                    String commandKeyPortletName = commandKey.getPortletName();

                    commandKeyPortletName = ProjectUtils.resolveReferencePlaceholder(commandKeyPortletName, project, scope);

                    if (portletName.equals(commandKeyPortletName)) {
                        String commandName = commandKey.getCommandName();
                        commandName = ProjectUtils.resolveReferencePlaceholder(commandName, project, scope);

                        commands.add(commandName);
                    }
                }

                return commands;
            }
        );
    }

    @NotNull
    public static List<String> getCommandReferencePlaceholders(@NotNull ID<CommandKey, Void> name, String commandName, Project project, GlobalSearchScope scope) {
        return ReadAction.compute(
                () -> {
                    final List<String> result = new ArrayList<>();
                    final List<String> commands = new ArrayList<>();

                    try {
                        FileBasedIndex.getInstance().processAllKeys(
                                name,
                                commandKey -> {
                                    if (commandKey.getCommandName().startsWith(ProjectUtils.REFERENCE_PLACEHOLDER)) {
                                        result.add(commandKey.getCommandName());
                                    }
                                    return true;
                                },
                                scope,
                                null
                        );

                    } catch (IndexNotReadyException e) {
                        //ignore
                    }

                    for (String value : result) {
                        String resolvedCommandName = ProjectUtils.resolveReferencePlaceholder(value, project, scope);
                        if (commandName.equals(resolvedCommandName)) {
                            commands.add(value);
                        }
                    }

                    return commands;
                }
        );
    }


    public static List<PsiFile> getPortletClasses(@NotNull ID<CommandKey, Void> name, Project project, String portletName, String commandName, GlobalSearchScope scope) {
        List<String> portletReferencePlaceholders = PortletNameIndex.getPortletReferencePlaceholders(portletName, project, scope);
        List<String> commandKeyPlaceholders = getCommandReferencePlaceholders(name, commandName, project, scope);

        return ReadAction.compute(
            () -> {
                List<PsiFile> result = new ArrayList<>();

                try {
                    Collection<VirtualFile> containingFiles = FileBasedIndex.getInstance().getContainingFiles(name, new CommandKey(portletName, commandName), scope);
                    for (String portletReferencePlaceholder : portletReferencePlaceholders) {
                        containingFiles.addAll(FileBasedIndex.getInstance().getContainingFiles(name, new CommandKey(portletReferencePlaceholder, commandName), scope));
                    }
                    for (String commandKeyPlaceholder : commandKeyPlaceholders) {
                        containingFiles.addAll(FileBasedIndex.getInstance().getContainingFiles(name, new CommandKey(portletName, commandKeyPlaceholder), scope));
                    }

                    for (String portletReferencePlaceholder : portletReferencePlaceholders) {
                        for (String commandKeyPlaceholder : commandKeyPlaceholders) {
                            containingFiles.addAll(FileBasedIndex.getInstance().getContainingFiles(name, new CommandKey(portletReferencePlaceholder, commandKeyPlaceholder), scope));
                        }
                    }

                    PsiManager psiManager = PsiManager.getInstance(project);

                    for (VirtualFile virtualFile : containingFiles) {
                        if (! virtualFile.isValid()) {
                            continue;
                        }

                        PsiFile psiFile = psiManager.findFile(virtualFile);

                        if (psiFile != null) {
                            result.add(psiFile);
                        }

                    }
                } catch (IndexNotReadyException e) {
                    //ignore
                }

                return result;
            }
        );
    }


    @Override
    protected void processProperties(@NotNull Map<CommandKey, Void> map, @NotNull Map<String, Collection<String>> properties, @NotNull PsiClass psiClass, String serviceClassName) {
        if (! properties.isEmpty()) {
            Collection<String> mvcCommandNames = properties.get("mvc.command.name");
            Collection<String> portletNames = properties.get("javax.portlet.name");

            if ((mvcCommandNames != null) && (portletNames != null)) {
                for (String mvcCommandName : mvcCommandNames) {
                    for (String portletName : portletNames) {
                        map.put(new CommandKey(portletName, mvcCommandName), null);
                    }
                }
            }
        }
    }

}
