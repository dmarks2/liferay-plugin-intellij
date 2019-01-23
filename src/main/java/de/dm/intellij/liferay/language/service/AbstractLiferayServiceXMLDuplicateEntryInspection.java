package de.dm.intellij.liferay.language.service;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.XmlSuppressableInspectionTool;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.XmlElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlTagValue;
import com.intellij.psi.xml.XmlText;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLiferayServiceXMLDuplicateEntryInspection extends XmlSuppressableInspectionTool {

    @Override
    public boolean isEnabledByDefault() {
        return true;
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

    protected abstract boolean isSuitableXmlText(XmlText xmlText);

    protected abstract boolean isSuitableXmlAttributeValue(XmlAttributeValue xmlAttributeValue);

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new XmlElementVisitor() {
            @Override
            public void visitXmlText(XmlText xmlText) {
                if (isSuitableXmlText(xmlText)) {
                    String text = xmlText.getText();

                    XmlTag xmlTag = PsiTreeUtil.getParentOfType(xmlText, XmlTag.class);
                    if (xmlTag != null) {
                        XmlTag parentTag = PsiTreeUtil.getParentOfType(xmlTag, XmlTag.class);
                        if (parentTag != null) {
                            List<XmlTag> subTags = findSubTagsWithText(parentTag, xmlTag.getLocalName(), text);
                            if (subTags.size() > 1) {
                                holder.registerProblem(xmlText,
                                    "Duplicate entry",
                                    ProblemHighlightType.GENERIC_ERROR_OR_WARNING,
                                    new RemoveXmlTagFix()
                                );
                            }
                        }
                    }
                }
            }

            @Override
            public void visitXmlAttributeValue(XmlAttributeValue xmlAttributeValue) {
                if (isSuitableXmlAttributeValue(xmlAttributeValue)) {
                    String text = xmlAttributeValue.getValue();

                    XmlAttribute xmlAttribute = PsiTreeUtil.getParentOfType(xmlAttributeValue, XmlAttribute.class);

                    if (xmlAttribute != null) {
                        XmlTag xmlTag = PsiTreeUtil.getParentOfType(xmlAttribute, XmlTag.class);

                        if (xmlTag != null) {
                            XmlTag parentTag = PsiTreeUtil.getParentOfType(xmlTag, XmlTag.class);

                            if (parentTag != null) {
                                List<XmlTag> subTags = findSubTagsWithAttributeValue(parentTag, xmlTag.getLocalName(), xmlAttribute.getName(), text);
                                if (subTags.size() > 1) {
                                    holder.registerProblem(xmlAttributeValue,
                                        "Duplicate entry",
                                        ProblemHighlightType.GENERIC_ERROR_OR_WARNING,
                                        new RemoveXmlTagFix()
                                    );
                                }
                            }
                        }
                    }
                }
            }
        };
    }

    private List<XmlTag> findSubTagsWithText(XmlTag parentTag, String localName, String text) {
        List<XmlTag> result = new ArrayList<>();

        for (XmlTag xmlTag : parentTag.getSubTags()) {
            if (localName.equals(xmlTag.getLocalName())) {
                XmlTagValue xmlTagValue = xmlTag.getValue();

                if (text.equals(xmlTagValue.getText())) {
                    result.add(xmlTag);
                }
            }
        }

        return result;
    }

    private List<XmlTag> findSubTagsWithAttributeValue(XmlTag parentTag, String localName, String attributeName, String attributeValue) {
        List<XmlTag> result = new ArrayList<>();

        for (XmlTag xmlTag : parentTag.getSubTags()) {
            if (localName.equals(xmlTag.getLocalName())) {
                XmlAttribute attribute = xmlTag.getAttribute(attributeName);

                if (attribute != null) {
                    if (attributeValue.equals(attribute.getValue())) {
                        result.add(xmlTag);
                    }
                }
            }
        }

        return result;
    }

    private static class RemoveXmlTagFix implements LocalQuickFix {

        @Nls(capitalization = Nls.Capitalization.Sentence)
        @NotNull
        @Override
        public String getFamilyName() {
            return "Remove entry";
        }

        @Override
        public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
            PsiElement element = descriptor.getPsiElement();

            PsiFile containingFile = element.getContainingFile();

            XmlTag xmlTag = PsiTreeUtil.getParentOfType(element, XmlTag.class);

            if (xmlTag != null) {
                XmlTag parentTag = PsiTreeUtil.getParentOfType(xmlTag, XmlTag.class);
                if (parentTag != null) {

                    new WriteCommandAction(project, containingFile) {
                        @Override
                        protected void run(@NotNull Result result) {
                            parentTag.getNode().removeChild(xmlTag.getNode());
                        }
                    }.execute();
                }
            }
        }
    }


}
