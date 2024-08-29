package de.dm.intellij.liferay.site.initializer;

import com.intellij.openapi.vfs.VirtualFile;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;

public class SiteInitializerUtil {

	private static final String SITE_INITIALIZER = "site-initializer";

	public static boolean isSiteInitializerFile(@NotNull VirtualFile virtualFile) {
		return LiferayFileUtil.getParent(virtualFile, SITE_INITIALIZER) != null;
	}

	public static boolean isSiteInitializerFile(@NotNull VirtualFile virtualFile, @NotNull String name) {
		return virtualFile.isValid() && SiteInitializerUtil.isSiteInitializerFile(virtualFile) && (name + ".json").equals(virtualFile.getName());
	}
}
