package de.dm.intellij.liferay.language.sass;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInspection.util.InspectionMessage;
import com.intellij.json.psi.JsonFile;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonValue;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.css.CssBundle;
import com.intellij.psi.css.reference.CssReference;
import com.intellij.util.IncorrectOperationException;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
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
        Map<String, JsonProperty> cssVariableMap = getCssVariableMap();

        if (cssVariableMap != null) {
            String text = getElement().getText();

            if (text.length() > 2) {
                if (text.startsWith("--")) {
                    String variableName = text.substring(2);

                    JsonProperty jsonProperty = cssVariableMap.get(variableName);

                    if (jsonProperty != null) {
                        return jsonProperty.getValue();
                    }
                }
            }
        }

        return null;
    }

    @Override
    public Object @NotNull [] getVariants() {
        List<Object> result = new ArrayList<Object>();

        Map<String, JsonProperty> cssVariableMap = getCssVariableMap();

        if (cssVariableMap != null) {
            for (String cssVariable : cssVariableMap.keySet()) {
                JsonProperty jsonProperty = cssVariableMap.get(cssVariable);

                ItemPresentation presentation = jsonProperty.getPresentation();

                if (presentation != null && jsonProperty.getValue() != null) {
                    result.add(
                        LookupElementBuilder.createWithSmartPointer(
                                "--" + cssVariable,
                                jsonProperty.getValue()
                        )
                            .withIcon(presentation.getIcon(false))
                            .withTypeText(presentation.getLocationString(), true)
                    );
                }
            }
        }

        return result.toArray(new Object[0]);
    }

    public PsiElement handleElementRename(@NotNull String newElementName) throws IncorrectOperationException {
        return super.handleElementRename(newElementName.startsWith("--") ? newElementName : "--" + newElementName);
    }

    private Map<String, JsonProperty> getCssVariableMap() {
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

                        return liferayFrontendTokenDefinitionJsonElementVisitor.getCssVariableMap();
                    }
                }
            }
        }

        return null;
    }
}
