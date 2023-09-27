package de.dm.intellij.liferay.language.xml;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.XmlElementVisitor;
import com.intellij.psi.xml.XmlDoctype;
import de.dm.intellij.liferay.language.jsp.AbstractLiferayDeprecationInspection;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static de.dm.intellij.liferay.language.xml.LiferayXmlDeprecationInfoHolder.createDtds;

public class LiferayXmlDeprecationInspection extends AbstractLiferayDeprecationInspection<LiferayXmlDeprecationInfoHolder> {

	public static final List<LiferayXmlDeprecationInfoHolder> XML_DEPRECATIONS = new ArrayList<>();

	static {
		for (LiferayXmlDeprecations.DtdDeprecation dtdDeprecation : LiferayXmlDeprecations.XML_DTD_DEPRECATIONS) {
			XML_DEPRECATIONS.addAll(createDtds(dtdDeprecation));
		}
	}

	@Nls
	@NotNull
	@Override
	public String getDisplayName() {
		return "Liferay XML deprecations inspection";
	}

	@Override
	public String getStaticDescription() {
		return "Check for deprecated Liferay XML instructions.";
	}

	@Override
	public String @NotNull [] getGroupPath() {
		return new String[]{
				getGroupDisplayName(),
				LiferayInspectionsGroupNames.XML_GROUP_NAME
		};
	}

	@Override
	protected List<LiferayXmlDeprecationInfoHolder> getInspectionInfoHolders() {
		return XML_DEPRECATIONS;
	}

	@Override
	protected PsiElementVisitor doBuildVisitor(ProblemsHolder holder, boolean isOnTheFly, List<LiferayXmlDeprecationInfoHolder> inspectionInfoHolders) {
		return new XmlElementVisitor() {

			@Override
			public void visitXmlDoctype(@NotNull XmlDoctype xmlDoctype) {
				for (LiferayXmlDeprecationInfoHolder infoHolder : inspectionInfoHolders) {
					infoHolder.visitDoctype(holder, xmlDoctype);
				}
			}
		};
	}
}
