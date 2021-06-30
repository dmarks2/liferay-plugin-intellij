package de.dm.intellij.liferay.language.sass;

import com.intellij.codeInspection.util.InspectionMessage;
import com.intellij.json.psi.JsonFile;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonValue;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.css.CssBundle;
import com.intellij.psi.css.reference.CssReference;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class LiferayFrontendTokenDefinitionVariableReference extends PsiReferenceBase<PsiElement> implements CssReference {

    public LiferayFrontendTokenDefinitionVariableReference(@NotNull PsiElement element) {
       super(element);
    }

    @Override
    public @InspectionMessage @NotNull String getUnresolvedMessagePattern() {
        String result = CssBundle.message("message.pattern.value.0.not.found", this.getCanonicalText());

        return result;
    }

    @Override
    public @Nullable PsiElement resolve() {
        final Module module = ModuleUtil.findModuleForPsiElement(getElement());

        if (module != null) {
            VirtualFile frontendTokenDefinitionFile = LiferayFileUtil.getFileInSourceRoot(module, "WEB-INF/frontend-token-definition.json");

            if (frontendTokenDefinitionFile != null) {
                PsiManager psiManager = PsiManager.getInstance(getElement().getProject());

                PsiFile psiFile = psiManager.findFile(frontendTokenDefinitionFile);

                if (psiFile instanceof JsonFile) {
                    JsonFile jsonFile = (JsonFile) psiFile;

                    JsonValue topLevelValue = jsonFile.getTopLevelValue();

                    if (topLevelValue != null) {
                        LiferayFrontendTokenDefinitionJsonElementVisitor liferayFrontendTokenDefinitionJsonElementVisitor = new LiferayFrontendTokenDefinitionJsonElementVisitor();

                        topLevelValue.accept(liferayFrontendTokenDefinitionJsonElementVisitor);

                        Map<String, JsonProperty> cssVariableMap = liferayFrontendTokenDefinitionJsonElementVisitor.getCssVariableMap();

                        String text = getElement().getText();

                        if (text.length() > 2) {
                            if (text.startsWith("--")) {
                                String variableName = text.substring(2);

                                return cssVariableMap.get(variableName);
                            }
                        }
                    }
                }
            }
        }

        return null;
    }
}
