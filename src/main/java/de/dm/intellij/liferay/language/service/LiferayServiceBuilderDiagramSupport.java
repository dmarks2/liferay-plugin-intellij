package de.dm.intellij.liferay.language.service;

import com.intellij.javaee.model.xml.ejb.EjbJar;
import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.openapi.actionSystem.DataSink;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.TypeSafeDataProvider;
import com.intellij.openapi.graph.view.EditMode;
import com.intellij.openapi.graph.view.Graph2DView;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.ModificationTracker;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.persistence.diagram.PersistenceDiagram;
import com.intellij.persistence.diagram.PersistenceDiagramSupport;
import com.intellij.psi.CommonClassNames;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiType;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.PairProcessor;
import com.liferay.service.builder.Column;
import com.liferay.service.builder.Entity;
import com.liferay.service.builder.ServiceBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiferayServiceBuilderDiagramSupport implements PersistenceDiagramSupport<ServiceBuilder, Entity, Column> {

    private Map<String, PsiType> typeMappings;

    private ServiceBuilder serviceBuilder;

    private VirtualFile virtualFile;

    private Project project;

    public LiferayServiceBuilderDiagramSupport(ServiceBuilder serviceBuilder, Project project, VirtualFile virtualFile) throws IOException, JAXBException {
        this.serviceBuilder = serviceBuilder;
        this.virtualFile = virtualFile;
        this.project = project;

        initializeTypeMappings(project);
    }

    private void initializeTypeMappings(Project project) {
        typeMappings = new HashMap<>();

        GlobalSearchScope globalSearchScope = GlobalSearchScope.allScope(project);

        typeMappings.put("boolean", PsiType.BOOLEAN);
        typeMappings.put("Boolean", PsiType.getTypeByName(CommonClassNames.JAVA_LANG_BOOLEAN, project, globalSearchScope));
        typeMappings.put("double", PsiType.DOUBLE);
        typeMappings.put("Double", PsiType.getTypeByName(CommonClassNames.JAVA_LANG_DOUBLE, project, globalSearchScope));
        typeMappings.put("float", PsiType.FLOAT);
        typeMappings.put("Float", PsiType.getTypeByName(CommonClassNames.JAVA_LANG_FLOAT, project, globalSearchScope));
        typeMappings.put("int", PsiType.INT);
        typeMappings.put("Integer", PsiType.getTypeByName(CommonClassNames.JAVA_LANG_INTEGER, project, globalSearchScope));
        typeMappings.put("long", PsiType.LONG);
        typeMappings.put("Long", PsiType.getTypeByName(CommonClassNames.JAVA_LANG_LONG, project, globalSearchScope));
        typeMappings.put("short", PsiType.SHORT);
        typeMappings.put("Short", PsiType.getTypeByName(CommonClassNames.JAVA_LANG_SHORT, project, globalSearchScope));
        typeMappings.put("BigDecimal", PsiType.getTypeByName("java.math.BigDecimal", project, globalSearchScope));
        typeMappings.put("Date", PsiType.getTypeByName(CommonClassNames.JAVA_UTIL_DATE, project, globalSearchScope));
        typeMappings.put("String", PsiType.getTypeByName(CommonClassNames.JAVA_LANG_STRING, project, globalSearchScope));
    }

    @Override
    public ModificationTracker getModificationTracker(ServiceBuilder serviceBuilder) {
        return VirtualFileManager.getInstance(); //??
    }

    @Override
    public void startDataModelUpdate(ServiceBuilder serviceBuilder) {
        //TODO
    }

    @Override
    public void finishDataModelUpdate() {
        //TODO
    }

    @Override
    public String getUniqueId(Entity entity) {
        return entity.getName();
    }

    @Override
    public void processEntities(PairProcessor<? super Entity, String> pairProcessor, boolean superclasses, boolean embeddables) {
        List<Entity> entities = serviceBuilder.getEntity();

        for (Entity entity : entities) {
            boolean result = pairProcessor.process(entity, entity.getName());

            if (! result) {
                break;
            }
        }
    }

    @Override
    public void processSuper(Entity entity, PairProcessor<? super Entity, String> pairProcessor) {
        //TODO no superclasses?
    }

    @Override
    public void processRelated(Entity sourceEntity, PairProcessor<? super Column, String> pairProcessor) {
        for (Column column : sourceEntity.getColumn()) {
            String foreignEntityName = column.getEntity();

            if (StringUtil.isNotEmpty(foreignEntityName)) {
                boolean result = pairProcessor.process(column, foreignEntityName);

                if (!result) {
                    break;
                }
            }
        }
    }

    @Override
    public void processEmbedded(Entity entity, PairProcessor<? super Column, String> pairProcessor) {
        //TODO no embedded
    }

    @Override
    public void processAttributes(Entity entity, PairProcessor<? super Column, String> pairProcessor) {
        List<Column> columns = entity.getColumn();

        for (Column column : columns) {
            boolean result = pairProcessor.process(column, column.getName());

            if (! result) {
                break;
            }
        }
    }

    @Nullable
    @Override
    public Entity getAttributeTarget(Column column) {
        if (column.getEntity() != null) {
            String entityName = column.getEntity();

            Entity entity = getEntityByName(entityName);

            return entity;
        }
        return null;
    }

    @Nullable
    @Override
    public Column getInverseSideAttribute(Column column) {
        Entity target = getAttributeTarget(column);
        if (target != null) {
            Column primaryKeyColumn = getPrimaryKeyColumn(target);

            return primaryKeyColumn;
        }
        return null;
    }

    @Nullable
    @Override
    public String getAttributeName(Column column) {
        return column.getName();
    }

    @Nullable
    @Override
    public PsiType getAttributePsiType(Column column) {
        String type = column.getType();
        if (type != null) {
            return typeMappings.get(type);
        }

        return null;
    }

    @NotNull
    @Override
    public String getEntityTypeName(Entity entity) {
        return entity.getName();
    }

    @NotNull
    @Override
    public String getAttributeTypeName(Column column) {
        return column.getType();
    }

    @Override
    public boolean isIdAttribute(Column column) {
        if ("true".equals(column.getPrimary())) {
            return true;
        }

        return false;
    }

    @NotNull
    @Override
    public String getAttributeMultiplicityLabel(Column first, Column second, boolean isSource) {
        if (isSource) {
            if (first.getEntity() != null) {
                if ("Collection".equals(first.getType())) {
                    return "*";
                } else {
                    return "1";
                }
            }
        } else {
            Column inverseSideAttribute = getInverseSideAttribute(first);
            if (inverseSideAttribute != null) {
                if ("Collection".equals(inverseSideAttribute.getType())) {
                    return "*";
                } else {
                    return "1";
                }
            }
        }
        return "?";
    }

    @Nullable
    @Override
    public Icon getEntityIcon(Entity entity) {
        //TODO icons?

        return null;
    }

    @Nullable
    @Override
    public Icon getAttributeIcon(Column column, boolean forceId) {
        //TODO icons?

        return null;
    }

    @NotNull
    @Override
    public TypeSafeDataProvider createDataProvider(PersistenceDiagram<ServiceBuilder, Entity, Column> persistenceDiagram) {
        return new TypeSafeDataProvider() {
            @Override
            public void calcData(@NotNull DataKey key, @NotNull DataSink sink) {
                if (LangDataKeys.MODULE.equals(key)) {
                    Module module = ModuleUtil.findModuleForFile(virtualFile, project);
                    sink.put(LangDataKeys.MODULE, module);
                } else if (LangDataKeys.MODULE_CONTEXT.equals(key)) {
                    Module module = ModuleUtil.findModuleForFile(virtualFile, project);
                    sink.put(LangDataKeys.MODULE_CONTEXT, module);

                }

            }
        };
    }

    @Override
    public boolean processEditNode(PersistenceDiagram<ServiceBuilder, Entity, Column> persistenceDiagram, Entity entity) {
        //TODO nodes not editable
        return false;
    }

    @Override
    public boolean processEditEdge(PersistenceDiagram<ServiceBuilder, Entity, Column> persistenceDiagram) {
        //TODO nodes not editable

        return false;
    }

    @Override
    public void processCreateEdge(PersistenceDiagram<ServiceBuilder, Entity, Column> persistenceDiagram, Entity entity, Entity entity1) {
        //TODO nodes not editable
    }

    @Override
    public void customizeGraphView(Graph2DView graph2DView, EditMode editMode) {

    }

    public ServiceBuilder getServiceBuilder() {
        return serviceBuilder;
    }

    public VirtualFile getVirtualFile() {
        return virtualFile;
    }

    private Entity getEntityByName(String name) {
        return serviceBuilder.getEntity().stream().filter(
            e -> name.equals(e.getName())
        ).findFirst().orElse(null);
    }

    private Column getColumnByName(Entity entity, String name) {
        return entity.getColumn().stream().filter(
            e -> name.equals(e.getName())
        ).findFirst().orElse(null);
    }

    private Column getPrimaryKeyColumn(Entity entity) {
        return entity.getColumn().stream().filter(
            e -> "true".equals(e.getPrimary())
        ).findFirst().orElse(null);
    }
}
