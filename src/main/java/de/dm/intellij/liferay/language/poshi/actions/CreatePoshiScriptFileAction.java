package de.dm.intellij.liferay.language.poshi.actions;

import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class CreatePoshiScriptFileAction extends CreateFileFromTemplateAction implements DumbAware {

    public CreatePoshiScriptFileAction() {
        super("Poshi Script File", "Creates a new poshi script file", Icons.LIFERAY_ICON);
    }

    @Override
    protected void buildDialog(@NotNull Project project, @NotNull PsiDirectory directory, CreateFileFromTemplateDialog.@NotNull Builder builder) {
        builder.setTitle("New Poshi Script File")
                .addKind("Path", Icons.LIFERAY_ICON, "Poshi Path File")
                .addKind("Function", Icons.LIFERAY_ICON, "Poshi Function")
                .addKind("Macro", Icons.LIFERAY_ICON, "Poshi Macro")
                .addKind("Testcase", Icons.LIFERAY_ICON, "Poshi Testcase");

    }

    @Override
    protected String getActionName(PsiDirectory directory, @NonNls @NotNull String newName, @NonNls String templateName) {
        return "Create Poshi Path File " + newName;
    }
}
