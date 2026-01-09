package de.dm.intellij.liferay.language.html;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileVisitor;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.ProcessingContext;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;

public class LiferayWebContextPathCompletionContributor extends CompletionContributor {

	public LiferayWebContextPathCompletionContributor() {
		extend(CompletionType.BASIC,
				PlatformPatterns.psiElement(),
				new CompletionProvider<>() {
					@Override
					protected void addCompletions(
							@NotNull CompletionParameters parameters,
							@NotNull ProcessingContext context,
							@NotNull CompletionResultSet result) {

						PsiElement element = parameters.getPosition().getParent();

						String text = getStringValue(element);

						if (text == null) {
							return;
						}

						if (!text.startsWith("/o/")) {
							return;
						}

						Module module = ModuleUtilCore.findModuleForPsiElement(element);

						if (module == null) {
							return;
						}

						String contextPath = LiferayFileUtil.getWebContextPath(module, null);

						if (contextPath == null) {
							return;
						}

						String urlPrefix = "/o/" + contextPath + "/";

						VirtualFile resourcesRoot = LiferayFileUtil.getLiferayResourcesWebRoot(module);

						if (resourcesRoot == null) {
							return;
						}

						addCompletionsFromDirectory(
								resourcesRoot,
								urlPrefix,
								result
						);
					}

					private void addCompletionsFromDirectory(
							@NotNull VirtualFile resourcesRoot,
							@NotNull String urlPrefix,
							@NotNull CompletionResultSet result) {

						VfsUtilCore.visitChildrenRecursively(resourcesRoot, new VirtualFileVisitor<Void>() {

							@Override
							public boolean visitFile(@NotNull VirtualFile file) {
								if (!file.isDirectory()) {
									String relativePath = getRelativePath(resourcesRoot, file);

									String fullUrl = urlPrefix + relativePath;

									result.addElement(
											LookupElementBuilder.create(fullUrl)
													.withIcon(file.getFileType().getIcon())
													.withTypeText(file.getParent().getName())
									);
								}

								return true;
							}
						});
					}

					private String getRelativePath(VirtualFile root, VirtualFile file) {
						String path = file.getPath();
						String rootPath = root.getPath();

						if (path.startsWith(rootPath)) {
							return path.substring(rootPath.length() + 1);
						}

						return file.getName();
					}

					private String getStringValue(PsiElement element) {
						if (element instanceof XmlAttributeValue) {
							return ((XmlAttributeValue) element).getValue();
						}

						if (element instanceof PsiLiteralExpression) {
							Object value = ((PsiLiteralExpression) element).getValue();

							return value instanceof String ? (String) value : null;
						}

						return null;
					}
				}
		);
	}
}
