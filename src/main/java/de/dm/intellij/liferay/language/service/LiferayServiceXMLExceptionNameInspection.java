package de.dm.intellij.liferay.language.service;

import com.intellij.codeInsight.daemon.GroupNames;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.XmlSuppressableInspectionTool;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.XmlElementVisitor;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlText;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LiferayServiceXMLExceptionNameInspection extends XmlSuppressableInspectionTool {

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "unneccessary service.xml exception suffix";
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
        return new String[] {
            getGroupDisplayName(),
            LiferayInspectionsGroupNames.SERVICE_XML_GROUP_NAME
        };
    }

    @Nullable
    @Override
    public String getStaticDescription() {
        return "Check for unneccessary Exception suffix at service.xml exception entries.";
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new XmlElementVisitor() {
            @Override
            public void visitXmlText(XmlText text) {
                XmlTag xmlTag = text.getParentTag();
                if ( (xmlTag != null) && ("exception".equals(xmlTag.getLocalName())) ) {
                    xmlTag = xmlTag.getParentTag();
                    if ( (xmlTag != null) && ("exceptions".equals(xmlTag.getLocalName())) ) {
                        xmlTag = xmlTag.getParentTag();
                        if ( (xmlTag != null) && ("service-builder".equals(xmlTag.getLocalName())) ) {
                            if ( (text.getText() != null) && (text.getText().endsWith("Exception")) ) {
                                holder.registerProblem(text,
                                        "Do not add Exception at the end of the name",
                                        ProblemHighlightType.WEAK_WARNING
                                );
                                //TODO offer a QuickFix
                            }
                        }
                    }
                }

            }
        };
    }
}
