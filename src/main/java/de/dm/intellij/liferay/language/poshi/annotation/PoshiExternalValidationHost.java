package de.dm.intellij.liferay.language.poshi.annotation;

import com.intellij.lang.annotation.AnnotationBuilder;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PoshiExternalValidationHost {

	public record PoshiError(String message, String filePath, int lineNumber) {}

	List<PoshiError> errors = new ArrayList<>();

	public void addError(String message, String filePath, int lineNumber) {
		errors.add(new PoshiError(message, filePath, lineNumber));
	}

	public void apply(@NotNull PsiFile file, @NotNull AnnotationHolder holder) {
		if (errors != null) {
			for (PoshiError error : errors) {
				String filePath = error.filePath();

				VirtualFile virtualFile = file.getVirtualFile();

				if (PoshiExternalAnnotator.log.isDebugEnabled()) {
					PoshiExternalAnnotator.log.debug("Examining error " + error.message() + " in " + error.filePath() + ":" + error.lineNumber());

					PoshiExternalAnnotator.log.debug("Compare " + FileUtil.toSystemDependentName(filePath) + " with " + FileUtil.toSystemDependentName(virtualFile.getPath()) + " = " + Objects.equals(FileUtil.toSystemDependentName(filePath), FileUtil.toSystemDependentName(virtualFile.getPath())));
				}

				if (Objects.equals(FileUtil.toSystemDependentName(filePath), FileUtil.toSystemDependentName(virtualFile.getPath()))) {
					Document document = PsiDocumentManager.getInstance(file.getProject()).getDocument(file);

					if (PoshiExternalAnnotator.log.isDebugEnabled()) {
						PoshiExternalAnnotator.log.debug("Getting document for " + file + " = " + document);
					}

					if (document != null) {
						int documentLineNumber = error.lineNumber() - 1;

						if (PoshiExternalAnnotator.log.isDebugEnabled()) {
							PoshiExternalAnnotator.log.debug("Found error at line " + documentLineNumber + "/" + document.getLineCount());
						}

						if (documentLineNumber <= document.getLineCount()) {
							TextRange textRange = new TextRange(document.getLineStartOffset(documentLineNumber), document.getLineEndOffset(documentLineNumber));

							AnnotationBuilder builder = holder.newAnnotation(HighlightSeverity.ERROR, error.message()).range(textRange);

							if (PoshiExternalAnnotator.log.isDebugEnabled()) {
								PoshiExternalAnnotator.log.debug("Adding ERROR annotation to " + textRange);
							}

							builder.create();
						}
					}
				}
			}
		}
	}
}
