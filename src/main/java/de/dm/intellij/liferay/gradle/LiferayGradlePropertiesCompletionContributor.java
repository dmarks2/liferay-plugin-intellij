package de.dm.intellij.liferay.gradle;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.properties.psi.impl.PropertyKeyImpl;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiFile;
import com.intellij.util.ProcessingContext;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LiferayGradlePropertiesCompletionContributor extends CompletionContributor {

	public LiferayGradlePropertiesCompletionContributor() {
		extend(
				CompletionType.BASIC,
				PlatformPatterns.
						psiElement(PropertyKeyImpl.class).
						inVirtualFile(
								PlatformPatterns.
										virtualFile().
										withName(
												StandardPatterns.
														string().
														equalTo("gradle.properties")
										)
						),
				new CompletionProvider<CompletionParameters>() {
					@Override
					protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
						List<LookupElementBuilder> lookups = new ArrayList<>();

						PsiFile containingFile = parameters.getOriginalFile();

						final Module module = ModuleUtil.findModuleForPsiElement(containingFile);

						if (module == null) {
							return;
						}

						for (String key : LiferayGradlePropertiesDocumentationBundle.keys()) {
							lookups.add(LookupElementBuilder.create(key).withIcon(Icons.LIFERAY_ICON));
						}

						result.addAllElements(lookups);
						result.stopHere();
					}
				}
		);
	}

}
