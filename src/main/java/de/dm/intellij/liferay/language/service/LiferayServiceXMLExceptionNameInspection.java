package de.dm.intellij.liferay.language.service;

import com.intellij.codeInsight.daemon.GroupNames;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.XmlSuppressableInspectionTool;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.XmlElementVisitor;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlText;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class LiferayServiceXMLExceptionNameInspection extends XmlSuppressableInspectionTool {

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "Check for unneccessary Exception suffix at service.xml exception entries.";
    }

    @Nls
    @NotNull
    @Override
    public String getGroupDisplayName() {
        return GroupNames.NAMING_CONVENTIONS_GROUP_NAME
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
