package de.dm.intellij.liferay.schema;

import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlFile;
import com.intellij.xml.XmlSchemaProvider;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import de.dm.intellij.liferay.util.LiferayVersions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;

/**
 * Provides XML Schema files for portlet-model-hints.xml and custom-sql/default.xml
 */
public class LiferayXmlSchemaProvider extends XmlSchemaProvider {

    @Nullable
    @Override
    public XmlFile getSchema(@NotNull String url, @Nullable Module module, @NotNull PsiFile baseFile) {
        URL targetFileUrl = null;

        PsiFile psiFile = baseFile;
        if (baseFile.getOriginalFile() != null) {
            psiFile = baseFile.getOriginalFile();
        }

        if (
                (psiFile.getName().equals("portal-model-hints.xml")) ||
                (psiFile.getName().equals("ext-model-hints.xml")) ||
                (psiFile.getName().equals("portlet-model-hints.xml")) ||
                (psiFile.getName().equals("portlet-model-hints-ext.xml"))
        ) {
            targetFileUrl = LiferayXmlSchemaProvider.class.getResource("/com/liferay/xsd/liferay-portlet-model-hints_7_0_0.xsd");
        } else if (
                (psiFile.getName().equals("default.xml")) &&
                        (psiFile.getParent() != null) &&
                        (psiFile.getParent().getName().equals("custom-sql"))
                ) {
            targetFileUrl = LiferayXmlSchemaProvider.class.getResource("/com/liferay/xsd/liferay-custom-sql_7_0_0.xsd");
        } else if (LiferayFileUtil.isJournalStructureFile(psiFile)) {
            float liferayVersion = LiferayModuleComponent.getPortalMajorVersion(module);
            if (LiferayVersions.LIFERAY_VERSION_6_1 == liferayVersion) {
                targetFileUrl = LiferayXmlSchemaProvider.class.getResource("/com/liferay/xsd/liferay-structures_6_1_0.xsd");
            } else if (LiferayVersions.LIFERAY_VERSION_6_2 == liferayVersion) {
                targetFileUrl = LiferayXmlSchemaProvider.class.getResource("/com/liferay/definitions/liferay-ddm-structure_6_2_0.xsd");
            }
        }

        if (targetFileUrl != null) {
            VirtualFile virtualFile = VfsUtil.findFileByURL(targetFileUrl);
            if (virtualFile != null) {
                PsiFile targetFile = PsiManager.getInstance(baseFile.getProject()).findFile(virtualFile);
                if (targetFile instanceof XmlFile) {
                    return (XmlFile) targetFile;
                }
            }
        }
        return null;
    }

    @Override
    public boolean isAvailable(@NotNull XmlFile file) {
        PsiFile psiFile = file;
        if (file.getOriginalFile() != null) {
            psiFile = file.getOriginalFile();
        }

        if (psiFile.getFileType() != XmlFileType.INSTANCE) {
            return false;
        }
        if (
                (psiFile.getName().equals("portal-model-hints.xml")) ||
                (psiFile.getName().equals("ext-model-hints.xml")) ||
                (psiFile.getName().equals("portlet-model-hints.xml")) ||
                (psiFile.getName().equals("portlet-model-hints-ext.xml"))
        ){
            return true;
        }
        if (psiFile.getName().equals("default.xml")) {
            if ( (psiFile.getParent() != null) && (psiFile.getParent().getName().equals("custom-sql")) ) {
                return true;
            }
        }
        if (LiferayFileUtil.isJournalStructureFile(file)) {
            return true;
        }
        return false;
    }
}
