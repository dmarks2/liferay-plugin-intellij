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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class AbstractCommandKeyIndexer extends AbstractComponentPropertyIndexer<CommandKey> {

    public static List<String> getCommands(@NotNull ID<CommandKey, Void> name, @NotNull String portletName, GlobalSearchScope scope) {
        return ReadAction.compute(
            () -> {
                final List<String> result = new ArrayList<>();

                try {
                    FileBasedIndex.getInstance().processAllKeys(
                        name,
                        commandKey -> {
                            if (portletName.equals(commandKey.getPortletName())) {
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

                return result;
            }
        );
    }

    public static List<PsiFile> getPortletClasses(@NotNull ID<CommandKey, Void> name, Project project, String portletName, String commandName, GlobalSearchScope scope) {
        return ReadAction.compute(
            () -> {
                List<PsiFile> result = new ArrayList<>();

                try {
                    Collection<VirtualFile> containingFiles = FileBasedIndex.getInstance().getContainingFiles(name, new CommandKey(portletName, commandName), scope);

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
