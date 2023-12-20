package de.dm.intellij.liferay.workflow;

import com.intellij.diagram.AbstractDiagramElementManager;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LiferayWorkflowDiagramElementManager extends AbstractDiagramElementManager<XmlTag> {

    @Nullable
    @Override
    public XmlTag findInDataContext(@NotNull DataContext dataContext) {
        PsiFile psiFile = CommonDataKeys.PSI_FILE.getData(dataContext);
        if (psiFile instanceof XmlFile xmlFile) {
			return xmlFile.getRootTag();
        }

        return null;
    }

    @Override
    public boolean isAcceptableAsNode(Object o) {
		//TODO check filename or check namespace, check tags?
		return o instanceof XmlTag;
	}

    @Nullable
    @Override
    public String getElementTitle(XmlTag xmlTag) {
        return xmlTag.getName();
    }

    @Override
    public String getNodeTooltip(XmlTag xmlTag) {
        return null;
    }
}
