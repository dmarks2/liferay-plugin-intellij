package de.dm.intellij.liferay.workflow;

import com.intellij.diagram.DiagramNodeBase;
import com.intellij.diagram.DiagramProvider;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class LiferayWorkflowStateNode extends DiagramNodeBase<XmlTag> {

    private final XmlTag xmlTag;

    public LiferayWorkflowStateNode(@NotNull DiagramProvider<XmlTag> provider, final XmlTag xmlTag) {
        super(provider);

        this.xmlTag = xmlTag;
    }

    @Nullable
    @Override
    public String getTooltip() {
        return xmlTag.getValue().getText();
    }

    @Override
    public Icon getIcon() {
        return null;
    }

    @NotNull
    @Override
    public XmlTag getIdentifyingElement() {
        return xmlTag;
    }

    public XmlTag getXmlTag() {
        return xmlTag;
    }
}
