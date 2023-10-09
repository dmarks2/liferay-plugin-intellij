package de.dm.intellij.liferay.language.poshi.annotation;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.process.ProcessOutput;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import de.dm.intellij.liferay.language.poshi.PoshiFileType;
import de.dm.intellij.liferay.language.poshi.PoshiPathFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PoshiExternalAnnotator extends ExternalAnnotator<PoshiValidatorRunnerParams, PoshiExternalValidationHost> implements DumbAware {

	protected final static Logger log = Logger.getInstance(PoshiExternalAnnotator.class);

	@Override
	public @Nullable PoshiValidatorRunnerParams collectInformation(@NotNull PsiFile file) {
		if (ApplicationManager.getApplication().isUnitTestMode()) {
			return null;
		}

		if (
				(file.getFileType().equals(PoshiFileType.INSTANCE)) ||
				(StringUtil.endsWith(file.getName(), (".path")))
		) {
			PoshiValidatorRunner.prepare();

			PoshiValidatorRunnerParams params = new PoshiValidatorRunnerParams();

			params.project = file.getProject();

			VirtualFile virtualFile = file.getVirtualFile();
			VirtualFile parent = virtualFile.getParent();
			VirtualFile grandParent = parent.getParent();

			params.workingDirectory = VfsUtilCore.virtualToIoFile(grandParent);

			if (log.isDebugEnabled()) {
				log.debug("Working Directory = " + params.workingDirectory);
			}

			return params;
		}

		return null;
	}

	@Override
	public @Nullable PoshiExternalValidationHost doAnnotate(PoshiValidatorRunnerParams params) {
		try {
			ProcessOutput output = PoshiValidatorRunner.runPoshiValidator(params.project, params.workingDirectory);

			PoshiExternalValidationHost poshiExternalValidationHost = new PoshiExternalValidationHost();

			PoshiValidatorRunnerParser.parseOutput(output, poshiExternalValidationHost);

			return poshiExternalValidationHost;
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void apply(@NotNull PsiFile file, PoshiExternalValidationHost annotationResult, @NotNull AnnotationHolder holder) {
		annotationResult.apply(file, holder);
	}
}
