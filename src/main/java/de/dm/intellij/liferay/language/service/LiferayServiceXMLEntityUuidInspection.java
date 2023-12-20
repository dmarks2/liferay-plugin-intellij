package de.dm.intellij.liferay.language.service;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.XmlSuppressableInspectionTool;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.XmlElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTag;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class LiferayServiceXMLEntityUuidInspection extends XmlSuppressableInspectionTool {

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "check that entities with a uuid have a primary key column";
    }

    @Nls
    @NotNull
    @Override
    public String getGroupDisplayName() {
        return LiferayInspectionsGroupNames.LIFERAY_GROUP_NAME;
    }


    @Override
    public String @NotNull [] getGroupPath() {
        return new String[]{
            getGroupDisplayName(),
            LiferayInspectionsGroupNames.SERVICE_XML_GROUP_NAME
        };
    }

    @Nullable
    @Override
    public String getStaticDescription() {
        return "Unable to create entity with a UUID without a primary key";
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new XmlElementVisitor() {
            @Override
            public void visitXmlAttributeValue(@NotNull XmlAttributeValue value) {
                if (LiferayServiceXMLUtil.isEntityUuidAttribute(value)) {
                    String text = value.getValue();
                    if ("true".equals(text)) {
                        XmlTag xmlTag = PsiTreeUtil.getParentOfType(value, XmlTag.class);
                        if (xmlTag != null) {
                            XmlTag[] childrenOfType = PsiTreeUtil.getChildrenOfType(xmlTag, XmlTag.class);

                            boolean hasPrimaryColumn = false;

                            if (childrenOfType != null) {

                                hasPrimaryColumn = Arrays.stream(
                                    childrenOfType
                                ).filter(
                                    child -> "column".equals(child.getName())
                                ).map(
                                    primary -> primary.getAttributeValue("primary")
                                ).anyMatch(
                                    "true"::equals
                                );

                            }

                            if (! hasPrimaryColumn) {
                                String entityName = xmlTag.getAttributeValue("name");

                                holder.registerProblem(value,
                                    "Unable to create entity " + entityName + " with a UUID without a primary key",
                                    ProblemHighlightType.GENERIC_ERROR_OR_WARNING
                                );
                            }
                        }
                    }
                }
            }
        };
    }
}
