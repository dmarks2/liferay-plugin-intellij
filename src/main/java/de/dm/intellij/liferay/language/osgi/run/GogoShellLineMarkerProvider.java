package de.dm.intellij.liferay.language.osgi.run;

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProvider;
import com.intellij.execution.RunManager;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.runners.ExecutionUtil;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;

public class GogoShellLineMarkerProvider implements LineMarkerProvider {

    @Nullable
    @Override
    public LineMarkerInfo<?> getLineMarkerInfo(@NotNull PsiElement element) {
        if (!(element instanceof PsiMethod method)) {
            return null;
        }

		PsiClass containingClass = method.getContainingClass();
        
        if (containingClass == null) {
            return null;
        }

		if (method.getNameIdentifier() == null) {
			return null;
		}

        String scope = getOsgiCommandScope(containingClass);
        String function = getOsgiCommandFunction(method);
        
        if (scope == null || function == null) {
            return null;
        }

        String gogoCommand = scope + ":" + function;
        
        return new LineMarkerInfo<>(
            method.getNameIdentifier(),
            method.getNameIdentifier().getTextRange(),
            AllIcons.Actions.Execute,
            psiElement -> "Run Gogo Shell Command: " + gogoCommand,
            (e, elt) -> executeGogoCommand(method.getProject(), gogoCommand),
            GutterIconRenderer.Alignment.LEFT,
            () -> "Run Gogo Shell Command"
        );
    }

    @Override
    public void collectSlowLineMarkers(@NotNull java.util.List<? extends PsiElement> elements, 
                                     @NotNull Collection<? super LineMarkerInfo<?>> result) {
    }

    private String getOsgiCommandScope(PsiClass psiClass) {
        PsiAnnotation componentAnnotation = psiClass.getAnnotation("org.osgi.service.component.annotations.Component");

		if (componentAnnotation == null) {
            return null;
        }

        PsiAnnotationMemberValue propertyValue = componentAnnotation.findAttributeValue("property");

		if (propertyValue instanceof PsiArrayInitializerMemberValue arrayValue) {
			for (PsiAnnotationMemberValue memberValue : arrayValue.getInitializers()) {
                String text = memberValue.getText();

                if (text.contains("osgi.command.scope=")) {
                    return extractPropertyValue(text, "osgi.command.scope");
                }
            }
        } else if (propertyValue != null) {
            String text = propertyValue.getText();

			if (text.contains("osgi.command.scope=")) {
                return extractPropertyValue(text, "osgi.command.scope");
            }
        }
        
        return null;
    }

    private String getOsgiCommandFunction(PsiMethod method) {
        PsiClass containingClass = method.getContainingClass();
        if (containingClass == null) {
			return null;
		}
        
        PsiAnnotation componentAnnotation = containingClass.getAnnotation("org.osgi.service.component.annotations.Component");
        if (componentAnnotation == null) {
			return null;
		}

        PsiAnnotationMemberValue propertyValue = componentAnnotation.findAttributeValue("property");

		if (propertyValue instanceof PsiArrayInitializerMemberValue arrayValue) {
			for (PsiAnnotationMemberValue memberValue : arrayValue.getInitializers()) {
                String text = memberValue.getText();
                if (text.contains("osgi.command.function=")) {
                    String functions = extractPropertyValue(text, "osgi.command.function");
                    if (functions != null && functions.contains(method.getName())) {
                        return method.getName();
                    }
                }
            }
        } else if (propertyValue != null) {
            String text = propertyValue.getText();
            if (text.contains("osgi.command.function=")) {
                String functions = extractPropertyValue(text, "osgi.command.function");
                if (functions != null && functions.contains(method.getName())) {
                    return method.getName();
                }
            }
        }
        
        return null;
    }

    private String extractPropertyValue(String propertyText, String propertyName) {
        int startIndex = propertyText.indexOf(propertyName + "=");

        if (startIndex == -1) {
			return null;
		}
        
        startIndex += propertyName.length() + 1;

		int endIndex = propertyText.indexOf("\"", startIndex);

		if (endIndex == -1) {
            endIndex = propertyText.length();
        }
        
        return propertyText.substring(startIndex, endIndex).replace("\"", "");
    }

    private void executeGogoCommand(Project project, String gogoCommand) {
        RunManager runManager = RunManager.getInstance(project);

		GogoShellConfigurationType configurationType = GogoShellConfigurationType.getInstance();
        
        RunnerAndConfigurationSettings settings = runManager.createConfiguration(
            gogoCommand,
            configurationType.getConfigurationFactories()[0]
        );
        
        GogoShellRunConfiguration configuration = (GogoShellRunConfiguration) settings.getConfiguration();

		configuration.setGogoCommand(gogoCommand);
        
        runManager.addConfiguration(settings);
        runManager.setSelectedConfiguration(settings);
        
        ExecutionUtil.runConfiguration(settings, DefaultRunExecutor.getRunExecutorInstance());
    }
}
