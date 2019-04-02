package de.dm.intellij.liferay.language.service;

import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.persistence.diagram.PersistenceDiagramProvider;
import com.intellij.psi.ElementDescriptionLocation;
import com.intellij.psi.PsiFile;
import com.liferay.service.builder.Column;
import com.liferay.service.builder.Entity;
import com.liferay.service.builder.ServiceBuilder;
import org.intellij.lang.annotations.Pattern;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

public class LiferayServiceBuilderDiagramProvider extends PersistenceDiagramProvider<ServiceBuilder, Entity, Column> {

    @Override
    protected Object findInDataContext(DataContext dataContext) {
        PsiFile psiFile = CommonDataKeys.PSI_FILE.getData(dataContext);
        Project project = CommonDataKeys.PROJECT.getData(dataContext);

        if (project != null && psiFile != null) {
            VirtualFile virtualFile = psiFile.getVirtualFile();

            try {
                ServiceBuilder serviceBuilder = LiferayServiceBuilderFactoryUtil.readServiceXml(virtualFile.getInputStream());

                LiferayServiceBuilderDiagramSupport diagramSupport = new LiferayServiceBuilderDiagramSupport(serviceBuilder, project, virtualFile);

                return new UnitInfo<>(diagramSupport, serviceBuilder);
            } catch (IOException | JAXBException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected UnitInfo<ServiceBuilder, Entity, Column> resolveUnitInfoByFQN(String s, Project project) {
        VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(new File(s));

        if (virtualFile != null) {
            try {
                ServiceBuilder serviceBuilder = LiferayServiceBuilderFactoryUtil.readServiceXml(virtualFile.getInputStream());

                LiferayServiceBuilderDiagramSupport diagramSupport = new LiferayServiceBuilderDiagramSupport(serviceBuilder, project, virtualFile);

                return new UnitInfo<>(diagramSupport, serviceBuilder);
            } catch (IOException | JAXBException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected String getUnitQualifiedName(UnitInfo<ServiceBuilder, Entity, Column> unitInfo) {
        LiferayServiceBuilderDiagramSupport diagramSupport = (LiferayServiceBuilderDiagramSupport)unitInfo.mySupport;

        VirtualFile virtualFile = diagramSupport.getVirtualFile();

        return virtualFile.getPath();
    }

    @Override
    protected String getUnitDisplayName(UnitInfo<ServiceBuilder, Entity, Column> unitInfo, ElementDescriptionLocation elementDescriptionLocation) {
        return "Unit Info Display Name";
    }

    @Override
    @Pattern("[a-zA-Z0-9_-]*")
    public String getID() {
        return "ServiceBuilder";
    }

    @Override
    public String getPresentableName() {
        return "Liferay Service Builder Diagram";
    }

}
