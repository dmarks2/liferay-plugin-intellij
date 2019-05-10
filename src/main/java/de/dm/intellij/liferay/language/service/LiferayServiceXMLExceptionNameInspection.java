package de.dm.intellij.liferay.language.service;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.XmlSuppressableInspectionTool;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.XmlElementVisitor;
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
            public void visitXmlText(XmlText xmlText) {
                if (LiferayServiceXMLUtil.isExceptionTag(xmlText)) {
                    String text = xmlText.getText();

                    if ( (text != null) && (text.endsWith("Exception")) ) {
                        holder.registerProblem(xmlText,
                            "Do not add Exception at the end of the name",
                            ProblemHighlightType.GENERIC_ERROR_OR_WARNING,
                            new RemoveExceptionSuffixFix()
                        );
                    }
                }

            }
        };
    }

    private static class RemoveExceptionSuffixFix implements LocalQuickFix {

        @Nls(capitalization = Nls.Capitalization.Sentence)
        @NotNull
        @Override
        public String getFamilyName() {
            return "Remove Exception suffix";
        }

        @Override
        public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
            PsiElement element = descriptor.getPsiElement();

            PsiFile containingFile = element.getContainingFile();

            XmlText xmlText = (XmlText)element;

            TextRange range = element.getTextRange();
            PsiDocumentManager psiDocumentManager = PsiDocumentManager.getInstance(project);

            Document document = psiDocumentManager.getDocument(containingFile);
            psiDocumentManager.doPostponedOperationsAndUnblockDocument(document);

            String oldText = xmlText.getText();
            String newText = oldText.substring(0, oldText.length() - "Exception".length());

            document.replaceString(range.getStartOffset(), range.getEndOffset(), newText);
            psiDocumentManager.commitDocument(document);
        }
    }

}
