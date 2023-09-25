package de.dm.intellij.liferay.language.groovy;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
import de.dm.intellij.liferay.language.java.LiferayJavaDeprecationInfoHolder;
import de.dm.intellij.liferay.language.java.LiferayJavaDeprecations;
import de.dm.intellij.liferay.language.jsp.AbstractLiferayDeprecationInspection;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.lang.psi.GroovyElementVisitor;
import org.jetbrains.plugins.groovy.lang.psi.GroovyPsiElementVisitor;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.path.GrMethodCallExpression;
import org.jetbrains.plugins.groovy.lang.psi.api.toplevel.imports.GrImportStatement;

import java.util.ArrayList;
import java.util.List;

import static de.dm.intellij.liferay.language.groovy.LiferayGroovyDeprecationInfoHolder.createImportStatements;
import static de.dm.intellij.liferay.language.groovy.LiferayGroovyDeprecationInfoHolder.createMethodCalls;


public class LiferayGroovyDeprecationInspection extends AbstractLiferayDeprecationInspection<LiferayGroovyDeprecationInfoHolder> {

	public static final List<LiferayGroovyDeprecationInfoHolder> GROOVY_DEPRECATIONS = new ArrayList<>();

	static {
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_50156_UTIL_BRIDGES));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_53113_USER_SCREEN_NAME_EXCEPTION));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_53279_USER_EMAIL_ADDRESS_EXCEPTION));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_53487_USER_ID_EXCEPTION));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_55364_CONTACT_NAME_EXCEPTION));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_61952_PORTAL_KERNEL));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_63205_USER_EXPORTER));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_NONE_MODULARIZATION_70));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_NONE_MODULARIZATION_71));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_88912_INVOKABLE_SERVICE));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_88913_SERVICE_LOADER_CONDITION));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_88869_TERMS_OF_USE));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_88870_CONVERTER));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_89223_OSGI_SERVICE_UTILS));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_89139_PREDICATE_FILTER));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_88911_FUNCTION_SUPPLIER));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_NONE_MODULARIZATION_72));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_100144_ASSET_TAGS_SELECTOR_TAG));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_106167_PORTAL_KERNEL));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_89065_ASSET_CATEGORIES_TABLE));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_122955_SOY_PORTLET));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_124898_OPEN_ID_CONNECT_SERVICE_HANDLER));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_122956_SOY));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_133200_OPEN_ID_CONNECT_SERVICE_HANDLER));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_147929_PETRA_JSON_VALIDATOR).version("7.4.3.23"));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_149147_PETRA_CONTENT).version("7.4.3.29"));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_149460_PETRA_MODEL_ADAPTER).version("7.4.3.22"));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_150808_PETRA_HTTP_INVOKER).version("7.4.3.24"));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_151723_PETRA_ENCRYPTOR).version("7.4.3.24"));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_148013_PETRA_JSON_WEB_SERVICE_CLIENT).version("7.4.3.31"));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_148493_PETRA_LOG4J).version("7.4.3.42"));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_151632_PETRA_PORTLET_URL_BUILDER).version("7.4.3.45"));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_181233_CTSQL_MODE_THREAD_LOCAL).version("7.4.3.74"));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_176640_S3_FILE_CACHE).version("7.4.3.80"));
		GROOVY_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_169777_SCRIPTING_EXECUTOR_EXTENDER).version("7.4.3.84"));
		GROOVY_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_162450_PHONE).version("7.4.3.45"));
		GROOVY_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_162437_ADDRESS).version("7.4.3.45"));
		GROOVY_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_163821_EMAIL_ADDRESS).version("7.4.3.48"));
		GROOVY_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_164415_WEBSITE).version("7.4.3.48"));
		GROOVY_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_164522_CONTACT).version("7.4.3.50"));
		GROOVY_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_165244_ORGANIZATION).version("7.4.3.50"));
		GROOVY_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_165685_ORG_LABOR).version("7.4.3.52"));
		GROOVY_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_194314_SCHEDULER_ENGINE_UNSCHEDULE).version("7.4.3.95"));
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

			@Override
			public void visitMethodCallExpression(@NotNull GrMethodCallExpression methodCallExpression) {
				for (LiferayGroovyDeprecationInfoHolder infoHolder : inspectionInfoHolders) {
					infoHolder.visitMethodCallExpression(holder, methodCallExpression);
				}
			}
		});
	}

}
