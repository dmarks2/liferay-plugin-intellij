package de.dm.intellij.liferay.workflow;

import com.intellij.diagram.DiagramVfsResolver;
import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class LiferayWorkflowDiagramVfsResolver implements DiagramVfsResolver<XmlTag> {

    @Override
    public String getQualifiedName(XmlTag xmlTag) {
        return xmlTag.getDescriptor().getQualifiedName(); //??
    }

    @Nullable
    @Override
    public XmlTag resolveElementByFQN(@NotNull String path, @NotNull Project project) {
        VirtualFile file = LocalFileSystem.getInstance().findFileByIoFile(new File(path));

        if (file != null) {
            if (XmlFileType.INSTANCE.equals(file.getFileType())) {
                XmlFile xmlFile = (XmlFile) PsiManager.getInstance(project).findFile(file);

                if (xmlFile != null) {
                    return xmlFile.getRootTag(); //??
                }
            }
        }

        return null;
    }
}
