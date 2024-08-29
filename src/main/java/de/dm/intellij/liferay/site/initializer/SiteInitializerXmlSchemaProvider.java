package de.dm.intellij.liferay.site.initializer;

import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlFile;
import com.intellij.xml.XmlSchemaProvider;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;

public class SiteInitializerXmlSchemaProvider extends XmlSchemaProvider {

	@Override
	public @Nullable XmlFile getSchema(@NotNull @NonNls String url, @Nullable Module module, @NotNull PsiFile baseFile) {
		PsiFile psiFile = baseFile.getOriginalFile();

		VirtualFile virtualFile = psiFile.getVirtualFile();

		if (
				virtualFile != null &&
				(SiteInitializerUtil.isSiteInitializerFile(virtualFile)) &&
				(LiferayFileUtil.getParent(virtualFile, "ddm-structures") != null)
		) {
			URL resource = SiteInitializerXmlSchemaProvider.class.getResource("/com/liferay/schema/site/initializer/ddm-structure.xsd");

			if (resource != null) {
				VirtualFile targetVirtualFile = VfsUtil.findFileByURL(resource);

				if (targetVirtualFile != null) {
					PsiFile targetFile = PsiManager.getInstance(baseFile.getProject()).findFile(targetVirtualFile);
					if (targetFile instanceof XmlFile) {
						return (XmlFile) targetFile;
					}
				}
			}
		}

		return null;
	}

	@Override
	public boolean isAvailable(@NotNull XmlFile file) {
		PsiFile psiFile = file.getOriginalFile();

		if (psiFile.getFileType() != XmlFileType.INSTANCE) {
			return false;
		}

		VirtualFile virtualFile = psiFile.getVirtualFile();

		if (virtualFile != null) {
			return (SiteInitializerUtil.isSiteInitializerFile(virtualFile)) &&
					(LiferayFileUtil.getParent(virtualFile, "ddm-structures") != null);
		}

		return false;
	}

}
