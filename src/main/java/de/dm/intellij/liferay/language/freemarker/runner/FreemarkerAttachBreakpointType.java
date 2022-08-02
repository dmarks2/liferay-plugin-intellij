package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.freemarker.psi.FtlLanguage;
import com.intellij.freemarker.psi.files.FtlFileType;
import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.lang.Language;
import com.intellij.lang.injection.InjectedLanguageManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.source.tree.injected.InjectedLanguageUtil;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlText;
import com.intellij.xdebugger.breakpoints.XLineBreakpointType;
import com.intellij.xml.util.XmlUtil;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import de.dm.intellij.liferay.util.LiferayXMLUtil;
import de.dm.intellij.liferay.workflow.LiferayWorkflowContextVariablesUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FreemarkerAttachBreakpointType extends XLineBreakpointType<FreemarkerAttachBreakpointProperties> {

    public FreemarkerAttachBreakpointType() {
        super("ftl", "Freemarker Breakpoints");
    }

    @Nullable
    @Override
    public FreemarkerAttachBreakpointProperties createBreakpointProperties(@NotNull VirtualFile file, int line) {
        return null;
    }

    @Override
    public boolean canPutAt(@NotNull VirtualFile file, int line, @NotNull Project project) {
        boolean isFtl = FtlFileType.INSTANCE.equals(file.getFileType());

        if (isFtl) {
            PsiFile psiFile = PsiManager.getInstance(project).findFile(file);

            if (psiFile != null) {
                return (LiferayFileUtil.isThemeTemplateFile(psiFile)) ||
                    (LiferayFileUtil.isLayoutTemplateFile(psiFile)) ||
                    (LiferayFileUtil.isJournalTemplateFile(psiFile)) ||
                    (LiferayFileUtil.isApplicationDisplayTemplateFile(psiFile));
            }
        } else if (XmlFileType.INSTANCE.equals(file.getFileType())) {
            XmlTag xmlTag = LiferayXMLUtil.getXmlTagAt(project, file, line);

            if (xmlTag != null) {
                boolean isWorkflowTemplateTag = LiferayWorkflowContextVariablesUtil.isWorkflowTemplateTag(xmlTag);

                if (isWorkflowTemplateTag) {
                    XmlText xmlText = PsiTreeUtil.getChildOfType(xmlTag, XmlText.class);

                    if (xmlText != null) {
                        PsiElement element = InjectedLanguageUtil.findElementInInjected((PsiLanguageInjectionHost) xmlText, 0);

                        if (element != null) {
                            Language language = element.getLanguage();

                            return FtlLanguage.INSTANCE.equals(language);
                        }
                    }
                }
            }
        }

        return false;
    }
}
