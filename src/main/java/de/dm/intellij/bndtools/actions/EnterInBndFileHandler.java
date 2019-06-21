package de.dm.intellij.bndtools.actions;

import com.intellij.codeInsight.editorActions.enter.EnterHandlerDelegateAdapter;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import de.dm.intellij.bndtools.psi.BndFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.lang.manifest.psi.ManifestTokenType;

public class EnterInBndFileHandler extends EnterHandlerDelegateAdapter {

    @Override
    public Result preprocessEnter(@NotNull PsiFile file, @NotNull Editor editor, @NotNull Ref<Integer> caretOffsetRef, @NotNull Ref<Integer> caretAdvance, @NotNull DataContext dataContext, EditorActionHandler originalHandler) {
        if (file instanceof BndFile) {
            int caretOffset = caretOffsetRef.get();

            Document document = editor.getDocument();

            Project project = file.getProject();

            PsiDocumentManager psiDocumentManager = PsiDocumentManager.getInstance(project);

            psiDocumentManager.commitDocument(document);

            PsiElement psiAtOffset = file.findElementAt(caretOffset);

            handleEnterInBndFile(editor, document, psiAtOffset, caretOffset);

            return Result.Stop;
        }

        return Result.Continue;
    }

    private void handleEnterInBndFile(Editor editor, Document document, PsiElement psiAtOffset, int caretOffset) {
        String toInsert;

        IElementType elementType = psiAtOffset == null ? null : psiAtOffset.getNode().getElementType();

        if (elementType == ManifestTokenType.HEADER_VALUE_PART) {
            toInsert = "\\\n    ";
        } else {
            toInsert = "\n";
        }

        document.insertString(caretOffset, toInsert);

        caretOffset += toInsert.length();

        editor.getCaretModel().moveToOffset(caretOffset);
        editor.getScrollingModel().scrollToCaret(ScrollType.RELATIVE);
        editor.getSelectionModel().removeSelection();
    }
}
