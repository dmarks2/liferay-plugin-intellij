package de.dm.intellij.liferay.workflow;

import com.intellij.diagram.DiagramDataModel;
import com.intellij.diagram.DiagramEdge;
import com.intellij.diagram.DiagramNode;
import com.intellij.diagram.DiagramProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.ModificationTracker;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LiferayWorkflowDiagramModel extends DiagramDataModel<XmlTag> {

    private Map<String, LiferayWorkflowStateNode> states;

    public LiferayWorkflowDiagramModel(Project project, DiagramProvider<XmlTag> provider, XmlDocument xmlDocument) {
        super(project, provider);

        states = getStates(xmlDocument);
    }

    @NotNull
    @Override
    public Collection<? extends DiagramNode<XmlTag>> getNodes() {
        return states.values();
    }

    @NotNull
    @Override
    public Collection<? extends DiagramEdge<XmlTag>> getEdges() {
        return Collections.emptyList();
    }

    @NotNull
    @Override
    public String getNodeName(DiagramNode<XmlTag> diagramNode) {
        if (diagramNode instanceof LiferayWorkflowStateNode) {
            return ((LiferayWorkflowStateNode)diagramNode).getXmlTag().getValue().getText();
        }
        return "";
    }

    @Nullable
    @Override
    public DiagramNode<XmlTag> addElement(XmlTag xmlDocument) {
        return null;
    }

    @Override
    public void refreshDataModel() {

    }

    @NotNull
    @Override
    public ModificationTracker getModificationTracker() {
        return VirtualFileManager.getInstance();
    }

    @Override
    public void dispose() {

    }

    private Map<String, LiferayWorkflowStateNode> getStates(XmlDocument xmlDocument) {
        Map<String, LiferayWorkflowStateNode> result = new HashMap<>();

        XmlTag rootTag = xmlDocument.getRootTag();
        if (rootTag != null) {
            XmlTag[] stateTags = rootTag.findSubTags("state");
            for (XmlTag stateTag : stateTags) {
                String name = stateTag.getSubTagText("name");
                if (name != null) {
                    result.put(name, new LiferayWorkflowStateNode(getProvider(), stateTag));
                }
            }
        }

        return result;
    }
}
