package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.XmlSuppressableInspectionTool;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.JspPsiUtil;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.XmlElementVisitor;
import com.intellij.psi.impl.source.jsp.jspXml.JspExpression;
import com.intellij.psi.jsp.JspFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlText;
import com.intellij.psi.xml.XmlToken;
import com.intellij.psi.xml.XmlTokenType;
import com.intellij.xml.XmlElementDescriptor;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class LiferayTaglibStringConcatInspection extends XmlSuppressableInspectionTool {

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "String concatenation inside taglib attributes";
    }

    @Override
    public String getStaticDescription() {
        return "Check for string concatenation together with jsp expessions.";
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
                LiferayInspectionsGroupNames.JSP_GROUP_NAME
        };
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new XmlElementVisitor() {
            @Override
            public void visitXmlAttribute(XmlAttribute attribute) {
                if (attribute.getValueElement() != null) {
                    XmlTag xmlTag = PsiTreeUtil.getParentOfType(attribute, XmlTag.class);
                    if (xmlTag != null) {
                        XmlElementDescriptor descriptor = xmlTag.getDescriptor();
                        if (descriptor != null) {
                            if (isRuntimeExpressionAttribute(descriptor, attribute.getName())) {

                                if (containsTextAndJspExpressions(attribute.getValueElement())) {
                                    holder.registerProblem(attribute.getValueElement(),
                                            "JSP expessions and string values cannot be concatenated inside the attribute",
                                            ProblemHighlightType.GENERIC_ERROR_OR_WARNING,
                                            new WrapInJSpExpression()
                                    );
                                }

                            }
                        }
                    }
                }
            }
        };
    }

    private static boolean containsTextAndJspExpressions(XmlAttributeValue valueElement) {
        boolean hasValueToken = false;

        XmlToken[] xmlTokens = PsiTreeUtil.getChildrenOfType(valueElement, XmlToken.class);
        if (xmlTokens != null) {
            hasValueToken = Arrays.stream(xmlTokens).anyMatch(
                xmlToken -> XmlTokenType.XML_ATTRIBUTE_VALUE_TOKEN.equals(xmlToken.getTokenType())
            );
        }
        JspExpression jspExpression = PsiTreeUtil.getChildOfType(valueElement, JspExpression.class);

        return (hasValueToken && jspExpression != null);
    }

    private static boolean isRuntimeExpressionAttribute(XmlElementDescriptor xmlElementDescriptor, String name) {
        XmlTag declaration = (XmlTag)xmlElementDescriptor.getDeclaration();

        if (declaration != null) {
            XmlTag[] attributes = declaration.findSubTags("attribute");

            for (XmlTag attribute : attributes) {
                String attributeName = attribute.getSubTagText("name");

                if (name.equals(attributeName)) {
                    String rtexprvalue = attribute.getSubTagText("rtexprvalue");

                    return "true".equals(rtexprvalue);
                }
            }
        }

        return false;
    }

    private static class WrapInJSpExpression implements LocalQuickFix {
        @Nls(capitalization = Nls.Capitalization.Sentence)
        @NotNull
        @Override
        public String getFamilyName() {
            return "Wrap in JSP expression";
        }

        @Override
        public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
            PsiElement element = descriptor.getPsiElement();

            JspFile jsp = JspPsiUtil.getJspFile(element);

            if (jsp != null) {
                XmlAttributeValue xmlAttributeValue = (XmlAttributeValue) element;

                TextRange range = element.getTextRange();
                PsiDocumentManager psiDocumentManager = PsiDocumentManager.getInstance(project);

                Document document = psiDocumentManager.getDocument(jsp);

                if (document != null) {
                    psiDocumentManager.doPostponedOperationsAndUnblockDocument(document);

                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("'<%=");

                    boolean firstChild = true;

                    for (PsiElement child : xmlAttributeValue.getChildren()) {
                        if (child instanceof XmlToken) {
                            XmlToken xmlToken = (XmlToken) child;
                            if (XmlTokenType.XML_ATTRIBUTE_VALUE_TOKEN.equals(xmlToken.getTokenType())) {
                                String text = xmlToken.getText();
                                if (!firstChild) {
                                    stringBuilder.append(" + ");
                                }
                                stringBuilder.append("\"").append(text).append("\"");

                                firstChild = false;
                            }
                        } else if (child instanceof JspExpression) {
                            JspExpression jspExpression = (JspExpression) child;

                            XmlText xmlText = PsiTreeUtil.getChildOfType(jspExpression, XmlText.class);

                            if (xmlText != null) {
                                if (!firstChild) {
                                    stringBuilder.append(" + ");
                                }

                                stringBuilder.append("(").append(xmlText.getText()).append(")");

                                firstChild = false;

                            }
                        }
                    }
                    stringBuilder.append("%>'");

                    document.replaceString(range.getStartOffset(), range.getEndOffset(), stringBuilder.toString());
                    PsiDocumentManager.getInstance(project).commitDocument(document);
                }
            }
        }
    }

}