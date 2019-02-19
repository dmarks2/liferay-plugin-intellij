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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LiferayWorkflowDiagramProvider extends BaseDiagramProvider<XmlTag> {

    public static final String ID = "LiferayWorkflowDiagramProvider";

    private final DiagramVfsResolver<XmlTag> diagramVfsResolver = new LiferayWorkflowDiagramVfsResolver();
    private DiagramElementManager<XmlTag> diagramElementManager = new LiferayWorkflowDiagramElementManager();

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getPresentableName() {
        return "Liferay Workflow";
    }

    @Override
    public DiagramElementManager<XmlTag> getElementManager() {
        return diagramElementManager;
    }

    @Override
    public DiagramVfsResolver<XmlTag> getVfsResolver() {
        return diagramVfsResolver;
    }

    @Override
    public DiagramDataModel<XmlTag> createDataModel(@NotNull Project project, @Nullable XmlTag xmlTag, @Nullable VirtualFile virtualFile, DiagramPresentationModel diagramPresentationModel) {
        PsiFile containingFile = xmlTag.getContainingFile();
        if (containingFile instanceof XmlFile) {
            XmlFile xmlFile = (XmlFile)containingFile;

            return new LiferayWorkflowDiagramModel(project, this, xmlFile.getDocument());
        }

        return null;
    }

}
