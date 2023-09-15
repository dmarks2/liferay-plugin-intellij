package de.dm.intellij.liferay.language.groovy;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
import de.dm.intellij.liferay.language.java.LiferayJavaDeprecations;
import de.dm.intellij.liferay.language.jsp.AbstractLiferayDeprecationInspection;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.lang.psi.GroovyElementVisitor;
import org.jetbrains.plugins.groovy.lang.psi.GroovyPsiElementVisitor;
import org.jetbrains.plugins.groovy.lang.psi.api.toplevel.imports.GrImportStatement;

import java.util.ArrayList;
import java.util.List;

import static de.dm.intellij.liferay.language.groovy.LiferayGroovyDeprecationInfoHolder.createImportStatements;


public class LiferayGroovyDeprecationInspection extends AbstractLiferayDeprecationInspection<LiferayGroovyDeprecationInfoHolder> {

	public static final List<LiferayGroovyDeprecationInfoHolder> GROOVY_DEPRECATIONS = new ArrayList<>();

	static {
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_50156_UTIL_BRIDGES));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_55364_CONTACT_NAME_EXCEPTION));
	}

	@Nls
	@NotNull
	@Override
	public String getDisplayName() {
		return "Liferay Groovy deprecations inspection";
	}

	@Override
	public String getStaticDescription() {
		return "Check for deprecated Liferay Groovy instructions.";
	}

	@Override
	public String @NotNull [] getGroupPath() {
		return new String[]{
				getGroupDisplayName(),
				LiferayInspectionsGroupNames.GROOVY_GROUP_NAME
		};
	}

	@Override
	protected List<LiferayGroovyDeprecationInfoHolder> getInspectionInfoHolders() {
		return GROOVY_DEPRECATIONS;
	}

	@Override
	protected PsiElementVisitor doBuildVisitor(ProblemsHolder holder, boolean isOnTheFly, List<LiferayGroovyDeprecationInfoHolder> inspectionInfoHolders) {
		return new GroovyPsiElementVisitor(new GroovyElementVisitor() {
			@Override
			public void visitImportStatement(@NotNull GrImportStatement importStatement) {
				for (LiferayGroovyDeprecationInfoHolder infoHolder : inspectionInfoHolders) {
					infoHolder.visitImportStatement(holder, importStatement);
				}
			}
		});
	}
}
