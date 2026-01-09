package de.dm.intellij.liferay.language.html;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;

public class LiferayWebContextFileReferenceSet extends FileReferenceSet {

	private final VirtualFile resourceRoot;

	public LiferayWebContextFileReferenceSet(
			@NotNull String path,
			@NotNull PsiElement element,
			int startOffset,
			@NotNull VirtualFile resourceRoot) {
		super(path, element, startOffset, null, true, true);

		this.resourceRoot = resourceRoot;
	}

	@Override
	public @NotNull Collection<PsiFileSystemItem> computeDefaultContexts() {
		PsiManager psiManager = PsiManager.getInstance(getElement().getProject());

		PsiFileSystemItem directory = psiManager.findDirectory(resourceRoot);

		if (directory != null) {
			return Collections.singleton(directory);
		}

		return Collections.emptyList();
	}

	@Override
	public boolean isSoft() {
		return false;
	}
}
