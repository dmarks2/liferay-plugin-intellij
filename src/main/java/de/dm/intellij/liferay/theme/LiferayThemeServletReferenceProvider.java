package de.dm.intellij.liferay.theme;

import com.intellij.javaee.web.CustomServletReferenceAdapter;
import com.intellij.javaee.web.ServletMappingInfo;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.paths.PathReference;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.SoftFileReferenceSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LiferayThemeServletReferenceProvider extends CustomServletReferenceAdapter {

	private static final Pattern THEME_SERVLET_PATH = Pattern.compile("^/o/([a-zA-Z0-9_.-]*)/(.*)");

	@Override
	protected PsiReference[] createReferences(@NotNull PsiElement psiElement, int offset, String text, @Nullable ServletMappingInfo servletMappingInfo, boolean soft) {
		Matcher matcher = THEME_SERVLET_PATH.matcher(text);

		if (matcher.find()) {
			String themeName = matcher.group(1);

			PsiFile psiFile = psiElement.getContainingFile();

			psiFile = psiFile.getOriginalFile();

			Module module = ModuleUtil.findModuleForPsiElement(psiFile);

			if ( (module != null) && (module.getName().equals(themeName)) ) { //TODO Web-ContextPath?
				String fileName = matcher.group(2);

				int startInElement = matcher.start(2);

				FileReferenceSet fileReferenceSet = new SoftFileReferenceSet(fileName, psiElement, startInElement, null, SystemInfo.isFileSystemCaseSensitive, false);

				fileReferenceSet.addCustomization(
						FileReferenceSet.DEFAULT_PATH_EVALUATOR_OPTION,
						file -> {
							Collection<PsiFileSystemItem> result = new ArrayList<>();

							ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);

							PsiManager psiManager = PsiManager.getInstance(module.getProject());

							for (VirtualFile sourceRoot : moduleRootManager.getSourceRoots()) {
								PsiDirectory psiDirectory = psiManager.findDirectory(sourceRoot);
								if (psiDirectory != null) {
									result.add(psiDirectory);
								}
							}

							return result;
						}
				);

				return fileReferenceSet.getAllReferences();
			}
		}

		return new PsiReference[0];
	}

	@Override
	public @Nullable PathReference createWebPath(String path, @NotNull PsiElement psiElement, ServletMappingInfo servletMappingInfo) {
		return null;
	}
}
