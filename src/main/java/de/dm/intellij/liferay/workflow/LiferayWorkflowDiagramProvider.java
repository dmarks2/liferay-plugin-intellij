package de.dm.intellij.liferay.workflow;

import com.intellij.diagram.BaseDiagramProvider;
import com.intellij.diagram.DiagramDataModel;
import com.intellij.diagram.DiagramElementManager;
import com.intellij.diagram.DiagramPresentationModel;
import com.intellij.diagram.DiagramVfsResolver;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LiferayWorkflowDiagramProvider extends BaseDiagramProvider<XmlTag> {

    public static final String ID = "LiferayWorkflowDiagramProvider";

    private final DiagramVfsResolver<XmlTag> diagramVfsResolver = new LiferayWorkflowDiagramVfsResolver();
    private final DiagramElementManager<XmlTag> diagramElementManager = new LiferayWorkflowDiagramElementManager();

    @Pattern("[a-zA-Z0-9_-]*")
    @Override
    public @NotNull String getID() {
        return ID;
    }

    @Override
    public @NotNull String getPresentableName() {
        return "Liferay workflow";
    }

    @Override
    public @NotNull DiagramElementManager<XmlTag> getElementManager() {
        return diagramElementManager;
    }

    @Override
    public @NotNull DiagramVfsResolver<XmlTag> getVfsResolver() {
        return diagramVfsResolver;
    }

    @Override
    public @NotNull DiagramDataModel<XmlTag> createDataModel(@NotNull Project project, @Nullable XmlTag xmlTag, @Nullable VirtualFile virtualFile, @NotNull DiagramPresentationModel diagramPresentationModel) {
        if (xmlTag != null) {
            PsiFile containingFile = xmlTag.getContainingFile();
            if (containingFile instanceof XmlFile xmlFile) {
				return new LiferayWorkflowDiagramModel(project, this, xmlFile.getDocument());
            }
        }

        return null;
    }

}
