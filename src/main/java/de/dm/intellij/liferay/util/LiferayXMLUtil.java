package de.dm.intellij.liferay.util;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.Nullable;

public class LiferayXMLUtil {

    public static int getStartLineNumber(VirtualFile file, XmlTag tag) {
        int textOffset = tag.getTextOffset();

        Document document = FileDocumentManager.getInstance().getDocument(file);

        if (document != null && textOffset < document.getTextLength()) {
            return document.getLineNumber(textOffset);
        }

        return -1;
    }

    public static int getEndLineNumber(VirtualFile file, XmlTag tag) {
        int textOffset = tag.getTextOffset() + tag.getTextLength();

        Document document = FileDocumentManager.getInstance().getDocument(file);

        if (document != null && textOffset < document.getTextLength()) {
            return document.getLineNumber(textOffset);
        }

        return -1;
    }

    @Nullable
    public static XmlTag getXmlTagAt(Project project, VirtualFile virtualFile, int lineNumber) {
        XmlFile xmlFile = (XmlFile) PsiManager.getInstance(project).findFile(virtualFile);

        if (xmlFile != null) {
            XmlTag rootTag = xmlFile.getRootTag();

            if (rootTag != null) {
                return findXmlTag(virtualFile, lineNumber, rootTag);
            }
        }

        return null;
    }

    private static XmlTag findXmlTag(VirtualFile virtualFile, int lineNumber, XmlTag rootTag) {
        final XmlTag[] subTags = rootTag.getSubTags();

        for (int i = 0; i < subTags.length; i++) {
            XmlTag subTag = subTags[i];

            int subTagStartLineNumber = getStartLineNumber(virtualFile, subTag);
            int subTagEndLineNumber = getEndLineNumber(virtualFile, subTag);

            if (subTagStartLineNumber == lineNumber) {
                return subTag;
            } else if (subTagStartLineNumber > lineNumber && i > 0 && subTags[i - 1].getSubTags().length > 0) {
                return findXmlTag(virtualFile, lineNumber, subTags[i - 1]);
            } else if (subTagStartLineNumber <= lineNumber && subTagEndLineNumber >= lineNumber && subTag.getSubTags().length == 0) {
                return subTag;
            }
        }

        if (subTags.length > 0) {
            final XmlTag lastElement = subTags[subTags.length - 1];

            return findXmlTag(virtualFile, lineNumber, lastElement);
        } else {
            return null;
        }
    }

}
