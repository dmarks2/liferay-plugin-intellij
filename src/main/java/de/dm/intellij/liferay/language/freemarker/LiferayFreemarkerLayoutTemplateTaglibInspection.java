package de.dm.intellij.liferay.language.freemarker;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.freemarker.psi.FtlExpression;
import com.intellij.freemarker.psi.directives.FtlDirective;
import com.intellij.freemarker.psi.directives.FtlIfDirective;
import com.intellij.freemarker.psi.directives.FtlMacro;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Checks that Liferay taglibs used in Freemarker Layout Templates are guarded by an existence check like
 * <code>&lt;#if liferay_portlet??&gt;</code>.
 * <p>
 * Liferay evaluates layout templates in advance (without a request / taglib support) to determine the columns
 * and the portlets contained in the layout (e.g. to collect the header CSS files of those portlets). In this phase
 * the taglibs are not available, so the template processing fails if a taglib is used unguarded.
 */
public class LiferayFreemarkerLayoutTemplateTaglibInspection extends LocalInspectionTool {

	private static final String LIFERAY_MACRO_LIBRARY_PREFIX = "liferay";

	/**
	 * Macros of the auto-imported Liferay macro library (FTL_liferay.ftl) which use taglibs internally,
	 * mapped to the taglib they need.
	 */
	private static final Map<String, String> LIFERAY_MACRO_LIBRARY_TAGLIBS = Map.of(
			"breadcrumbs", "liferay_portlet",
			"control_menu", "liferay_product_navigation",
			"languages", "liferay_portlet",
			"navigation_menu", "liferay_portlet",
			"search", "liferay_portlet",
			"search_bar", "liferay_portlet",
			"user_personal_bar", "liferay_portlet"
	);

	@Override
	public boolean isEnabledByDefault() {
		return true;
	}

	@Nls
	@NotNull
	@Override
	public String getGroupDisplayName() {
		return LiferayInspectionsGroupNames.LIFERAY_GROUP_NAME;
	}

	@Override
	public String @NotNull [] getGroupPath() {
		return new String[]{
				getGroupDisplayName(),
				LiferayInspectionsGroupNames.FREEMARKER_GROUP_NAME
		};
	}

	@Nls
	@NotNull
	@Override
	public String getDisplayName() {
		return "Unguarded taglib usage in Freemarker layout template";
	}

	@Override
	public String getStaticDescription() {
		return "Checks that Liferay taglibs used in Freemarker layout templates are surrounded by an existence check like <code>&lt;#if liferay_portlet??&gt;</code>. " +
				"Liferay evaluates layout templates in advance to determine the columns and contained portlets (e.g. to collect the header CSS files). " +
				"Taglibs are not available in that phase, so an unguarded taglib usage breaks the template processing. " +
				"This also applies to macros of the Liferay macro library which use taglibs internally (e.g. <code>&lt;@liferay.breadcrumbs /&gt;</code>).";
	}

	@NotNull
	@Override
	public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
		PsiFile psiFile = holder.getFile();

		if (!LiferayFileUtil.isLayoutTemplateFile(psiFile)) {
			return PsiElementVisitor.EMPTY_VISITOR;
		}

		return new PsiElementVisitor() {
			@Override
			public void visitElement(@NotNull PsiElement element) {
				if (element instanceof FtlMacro ftlMacro) {
					String prefix = LiferayFreemarkerTaglibs.getPrefix(ftlMacro);

					if (prefix == null) {
						return;
					}

					String requiredTaglib;
					String message;

					if (LiferayFreemarkerTaglibs.FTL_MACRO_PREFIXES.containsKey(prefix)) {
						requiredTaglib = prefix;
						message = "Taglib " + prefix + " is not available while Liferay determines the columns of a layout template, surround it with <#if " + prefix + "??>";
					} else if (LIFERAY_MACRO_LIBRARY_PREFIX.equals(prefix)) {
						String localName = LiferayFreemarkerTaglibs.getLocalName(ftlMacro);

						requiredTaglib = LIFERAY_MACRO_LIBRARY_TAGLIBS.get(localName);

						if (requiredTaglib == null) {
							return;
						}

						message = "Macro " + prefix + "." + localName + " uses taglib " + requiredTaglib + ", which is not available while Liferay determines the columns of a layout template, surround it with <#if " + requiredTaglib + "??>";
					} else {
						return;
					}

					if (isGuarded(ftlMacro, requiredTaglib)) {
						return;
					}

					PsiElement problemElement = ftlMacro.getStartTagNameElement();

					if (problemElement == null) {
						problemElement = ftlMacro;
					}

					holder.registerProblem(
							problemElement,
							message,
							new SurroundWithIfExistsQuickFix(requiredTaglib)
					);
				}
			}
		};
	}

	private static boolean isGuarded(@NotNull FtlMacro ftlMacro, @NotNull String prefix) {
		Pattern guardPattern = createGuardPattern(prefix);

		int macroOffset = ftlMacro.getTextRange().getStartOffset();

		FtlIfDirective ifDirective = PsiTreeUtil.getParentOfType(ftlMacro, FtlIfDirective.class);

		while (ifDirective != null) {
			String conditionText = getGoverningConditionText(ifDirective, macroOffset);

			if (conditionText != null && guardPattern.matcher(conditionText).find()) {
				return true;
			}

			ifDirective = PsiTreeUtil.getParentOfType(ifDirective, FtlIfDirective.class);
		}

		return false;
	}

	/**
	 * Returns the condition text of the branch (if / elseif) of the given if directive the given offset belongs to.
	 * Returns null if the offset belongs to an else branch.
	 */
	@Nullable
	private static String getGoverningConditionText(@NotNull FtlIfDirective ifDirective, int offset) {
		FtlDirective governingBranch = null;

		for (FtlDirective branch : ifDirective.getBranches()) {
			if (branch == ifDirective) {
				continue;
			}

			String branchName = getBranchName(branch);

			if (("elseif".equals(branchName) || "else".equals(branchName)) && branch.getTextRange().getStartOffset() <= offset) {
				if (governingBranch == null || governingBranch.getTextRange().getStartOffset() < branch.getTextRange().getStartOffset()) {
					governingBranch = branch;
				}
			}
		}

		if (governingBranch == null) {
			FtlExpression condition = ifDirective.getCondition();

			return condition != null ? condition.getText() : null;
		}

		if ("elseif".equals(getBranchName(governingBranch))) {
			String text = governingBranch.getText();

			int endOfStartTag = indexOfStartTagEnd(text);

			return endOfStartTag > 0 ? text.substring(0, endOfStartTag) : text;
		}

		return null;
	}

	@Nullable
	private static String getBranchName(@NotNull FtlDirective branch) {
		String directiveName = branch.getDirectiveName();

		if (directiveName == null) {
			return null;
		}

		directiveName = directiveName.trim();

		if (directiveName.startsWith("#")) {
			directiveName = directiveName.substring(1);
		}

		if ("elseIf".equals(directiveName)) {
			directiveName = "elseif";
		}

		return directiveName;
	}

	private static int indexOfStartTagEnd(String text) {
		int angle = text.indexOf('>');
		int square = text.indexOf(']');

		if (angle < 0) {
			return square;
		}
		if (square < 0) {
			return angle;
		}

		return Math.min(angle, square);
	}

	private static Pattern createGuardPattern(@NotNull String prefix) {
		// matches "prefix??", "(prefix)??", "prefix?has_content", "(prefix)?has_content"
		return Pattern.compile("(?<![\\w.])\\(?\\s*" + Pattern.quote(prefix) + "\\s*\\)?\\s*(\\?\\?|\\?has_content)");
	}

	private static class SurroundWithIfExistsQuickFix implements LocalQuickFix {

		private final String prefix;

		public SurroundWithIfExistsQuickFix(String prefix) {
			this.prefix = prefix;
		}

		@Nls
		@NotNull
		@Override
		public String getFamilyName() {
			return "Surround with existence check";
		}

		@Nls
		@NotNull
		@Override
		public String getName() {
			return "Surround with <#if " + prefix + "??>";
		}

		@Override
		public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
			PsiElement psiElement = descriptor.getPsiElement();

			FtlMacro ftlMacro;

			if (psiElement instanceof FtlMacro) {
				ftlMacro = (FtlMacro) psiElement;
			} else {
				ftlMacro = PsiTreeUtil.getParentOfType(psiElement, FtlMacro.class);
			}

			if (ftlMacro == null) {
				return;
			}

			PsiFile psiFile = ftlMacro.getContainingFile();

			Document document = PsiDocumentManager.getInstance(project).getDocument(psiFile);

			if (document == null) {
				return;
			}

			TextRange range = ftlMacro.getTextRange();

			String macroText = document.getText(range);

			boolean squareBrackets = macroText.startsWith("[");

			String startTag = squareBrackets ? "[#if " + prefix + "??]" : "<#if " + prefix + "??>";
			String endTag = squareBrackets ? "[/#if]" : "</#if>";

			PsiDocumentManager.getInstance(project).doPostponedOperationsAndUnblockDocument(document);

			document.replaceString(range.getStartOffset(), range.getEndOffset(), startTag + macroText + endTag);

			PsiDocumentManager.getInstance(project).commitDocument(document);
		}
	}
}
