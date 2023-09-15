package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.jsp.psi.BaseJspUtil;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.XmlElementVisitor;
import com.intellij.psi.jsp.JspDirectiveKind;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import de.dm.intellij.liferay.language.java.LiferayJavaDeprecations;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static de.dm.intellij.liferay.language.jsp.LiferayJspJavaDeprecationInfoHolder.createImportStatements;

public class LiferayJspJavaDeprecationInspection extends AbstractLiferayDeprecationInspection<LiferayJspJavaDeprecationInfoHolder> {

	public static final List<LiferayJspJavaDeprecationInfoHolder> JAVA_DEPRECATIONS = new ArrayList<>();

	static {
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_50156_UTIL_BRIDGES));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_53113_USER_SCREEN_NAME_EXCEPTION));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_53279_USER_EMAIL_ADDRESS_EXCEPTION));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_53487_USER_ID_EXCEPTION));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_55364_CONTACT_NAME_EXCEPTION));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_61952_PORTAL_KERNEL));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_63205_USER_EXPORTER));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_NONE_MODULARIZATION_70));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_NONE_MODULARIZATION_71));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_88912_INVOKABLE_SERVICE));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_88913_SERVICE_LOADER_CONDITION));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_88869_TERMS_OF_USE));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_88870_CONVERTER));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_89223_OSGI_SERVICE_UTILS));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_89139_PREDICATE_FILTER));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_88911_FUNCTION_SUPPLIER));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_NONE_MODULARIZATION_72));
	}
	@Nls
	@NotNull
	@Override
	public String getDisplayName() {
		return "Liferay Java deprecations inspection";
	}

	@Override
	public String getStaticDescription() {
		return "Check for deprecated Liferay Java instructions inside JSP files.";
	}

	@Override
	public String @NotNull [] getGroupPath() {
		return new String[]{
				getGroupDisplayName(),
				LiferayInspectionsGroupNames.JSP_GROUP_NAME
		};
	}
	@Override
	protected List<LiferayJspJavaDeprecationInfoHolder> getInspectionInfoHolders() {
		return JAVA_DEPRECATIONS;
	}

	@Override
	protected PsiElementVisitor doBuildVisitor(ProblemsHolder holder, boolean isOnTheFly, List<LiferayJspJavaDeprecationInfoHolder> inspectionInfoHolders) {
		return new XmlElementVisitor() {

			@Override
			public void visitXmlTag(@NotNull XmlTag tag) {
				XmlAttribute importDirective = getImportDirective(tag);

				if (importDirective != null) {
					for (LiferayJspJavaDeprecationInfoHolder infoHolder : inspectionInfoHolders) {
						infoHolder.visitImportDirective(holder, importDirective);
					}
				}
				super.visitXmlTag(tag);
			}
		};
	}

	private static XmlAttribute getImportDirective(XmlTag xmlTag) {
		JspDirectiveKind directiveKindByTag = BaseJspUtil.getDirectiveKindByTag(xmlTag);

		if (directiveKindByTag == JspDirectiveKind.PAGE) {
			for (XmlAttribute xmlAttribute : xmlTag.getAttributes()) {
				if (Objects.equals("import", xmlAttribute.getName())) {
					String namespace = xmlAttribute.getNamespace();
					if (namespace.isEmpty() || "http://java.sun.com/JSP/Page".equals(namespace)) {
						return xmlAttribute;
					}
				}
			}
		}

		return null;
	}

}
