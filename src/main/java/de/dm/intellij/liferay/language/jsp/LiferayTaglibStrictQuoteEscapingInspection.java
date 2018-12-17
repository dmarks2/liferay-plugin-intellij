package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.XmlSuppressableInspectionTool;
import com.intellij.jsp.impl.CustomTagDescriptorBase;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.ElementManipulators;
import com.intellij.psi.JspPsiUtil;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.XmlElementVisitor;
import com.intellij.psi.impl.source.jsp.jspXml.JspExpression;
import com.intellij.psi.impl.source.jsp.jspXml.JspXmlText;
import com.intellij.psi.jsp.JspFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.XmlElementDescriptor;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class LiferayTaglibStrictQuoteEscapingInspection extends XmlSuppressableInspectionTool {

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "Strict quote escaping for taglib attributes";
    }

    @Override
    public String getStaticDescription() {
        return "Check for unescaped double quotes inside double quoted taglib attributes.";
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
                    if (isDoubleQuoted(attribute.getValueElement().getText())) {

                        XmlTag xmlTag = PsiTreeUtil.getParentOfType(attribute, XmlTag.class);
                        if (xmlTag != null) {
                            XmlElementDescriptor descriptor = xmlTag.getDescriptor();
                            if (descriptor instanceof CustomTagDescriptorBase) {
                                JspExpression[] jspExpressions = PsiTreeUtil.getChildrenOfType(attribute.getValueElement(), JspExpression.class);
                                if (jspExpressions != null) {
                                    for (JspExpression jspExpression : jspExpressions) {
                                        JspXmlText[] jspXmlTexts = PsiTreeUtil.getChildrenOfType(jspExpression, JspXmlText.class);
                                        if (jspXmlTexts != null) {
                                            for (JspXmlText jspXmlText : jspXmlTexts) {
                                                String text = jspXmlText.getText();
                                                if (containsUnescapedQuotes(text)) {
                                                    holder.registerProblem(attribute.getValueElement(),
                                                        "Attribute value is quoted with \" which must be escaped when used within the value",
                                                        ProblemHighlightType.WEAK_WARNING,
                                                        new UseSingleQuotesFix()
                                                    );
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
    }

    private static boolean containsUnescapedQuotes(String text) {
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\"') {
                if ( i == 0 || text.charAt(i-1) != '\\' ) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isDoubleQuoted(String s) {
        return s.length() > 1 && s.charAt(0) == '\"' && s.charAt(0) == s.charAt(s.length() - 1);
    }

    private static class UseSingleQuotesFix implements LocalQuickFix {

        @Nls(capitalization = Nls.Capitalization.Sentence)
        @NotNull
        @Override
        public String getFamilyName() {
            return "Use single quotes";
        }

        @Override
        public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
            PsiElement element = descriptor.getPsiElement();

            JspFile jsp = JspPsiUtil.getJspFile(element);

            XmlAttributeValue xmlAttributeValue = (XmlAttributeValue)element;

            TextRange range = element.getTextRange();
            Document document = PsiDocumentManager.getInstance(project).getDocument(jsp);
            PsiDocumentManager.getInstance(project).doPostponedOperationsAndUnblockDocument(document);

            String oldText = xmlAttributeValue.getText();
            String newText = "\'" + StringUtil.unquoteString(oldText, '\"') + "\'";

            document.replaceString(range.getStartOffset(), range.getEndOffset(), newText);
            PsiDocumentManager.getInstance(project).commitDocument(document);
        }
    }

}
