package de.dm.intellij.liferay.language.groovy;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.lang.psi.api.toplevel.imports.GrImportStatement;

public class GroovyUtil {

	@NotNull
	public static String getMatchFromImports(@NotNull PsiFile psiFile, @NotNull String className) {
		GrImportStatement[] importStatements = PsiTreeUtil.getChildrenOfType(psiFile, GrImportStatement.class);

		if (importStatements != null) {
			//search for import statements
			for (GrImportStatement importStatement : importStatements) {
				String qualifiedName = importStatement.getImportFqn();

				if (qualifiedName != null) {
					if (
							(className.equals(qualifiedName)) ||
							(className.equals(StringUtil.getShortName(qualifiedName)))
					) {
						return qualifiedName;
					}
				}
			}
		}

		return className;
	}

}
