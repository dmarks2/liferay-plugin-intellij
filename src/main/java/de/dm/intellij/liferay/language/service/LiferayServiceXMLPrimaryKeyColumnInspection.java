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

public class LiferayServiceXMLPrimaryKeyColumnInspection extends XmlSuppressableInspectionTool {

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "check for valid type for a primary key column";
    }

    @Nls
    @NotNull
    @Override
    public String getGroupDisplayName() {
        return LiferayInspectionsGroupNames.LIFERAY_GROUP_NAME;
    }

    @NotNull
    @Override
    public String[] getGroupPath() {
        return new String[]{
                getGroupDisplayName(),
                LiferayInspectionsGroupNames.SERVICE_XML_GROUP_NAME
        };
    }

    @Nullable
    @Override
    public String getStaticDescription() {
        return "Primary key of entity must be an int, long, or String";
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new XmlElementVisitor() {
            @Override
            public void visitXmlAttributeValue(XmlAttributeValue value) {
                if (LiferayServiceXMLUtil.isColumnPrimaryAttribute(value)) {
                    String text = value.getValue();

                    if ("true".equals(text)) {
                        XmlTag xmlTag = PsiTreeUtil.getParentOfType(value, XmlTag.class);
                        if (xmlTag != null) {
                            String type = xmlTag.getAttributeValue("type");
                            if (type != null) {
                                if (!(
                                    Arrays.asList("int", "long", "String").contains(type)
                                )
                                ) {
                                    String columnName = xmlTag.getAttributeValue("name");
                                    String entityName = null;

                                    XmlTag entityTag = PsiTreeUtil.getParentOfType(xmlTag, XmlTag.class);
                                    if (entityTag != null) {
                                        entityName = entityTag.getAttributeValue("name");
                                    }

                                    holder.registerProblem(value,
                                        "Primary Key " + columnName + (entityName != null ? " of entity " + entityName : "") + " must be an int, long or String",
                                        ProblemHighlightType.GENERIC_ERROR_OR_WARNING
                                    );
                                }
                            }
                        }
                    }
                }
            }
        };
    }
}
