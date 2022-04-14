package de.dm.intellij.liferay.workflow;

import com.intellij.diagram.AbstractDiagramElementManager;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.Nullable;

public class LiferayWorkflowDiagramElementManager extends AbstractDiagramElementManager<XmlTag> {

    @Nullable
    @Override
    public XmlTag findInDataContext(DataContext dataContext) {
        PsiElement element = CommonDataKeys.PSI_ELEMENT.getData(dataContext);
        Project project = CommonDataKeys.PROJECT.getData(dataContext);

        PsiFile psiFile = CommonDataKeys.PSI_FILE.getData(dataContext);
        if (psiFile instanceof XmlFile) {
            XmlFile xmlFile = (XmlFile) psiFile;

            XmlTag rootTag = xmlFile.getRootTag();


            return rootTag;
        }

        return null;
    }

    @Override
    public boolean isAcceptableAsNode(Object o) {
        if (o instanceof XmlTag) {
            //TODO check filename or check namespace, check tags?

            return true;
        }

        return false;
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
