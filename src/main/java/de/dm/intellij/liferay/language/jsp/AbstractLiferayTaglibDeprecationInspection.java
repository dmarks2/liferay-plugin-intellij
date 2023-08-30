package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.XmlSuppressableInspectionTool;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.XmlElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class AbstractLiferayTaglibDeprecationInspection extends XmlSuppressableInspectionTool {

	@Override
	public boolean isEnabledByDefault() {
		return true;
	}

	@Nls
	@NotNull
	@Override
	public String getDisplayName() {
		return "Liferay taglib deprecations inspection";
	}

	@Override
	public String getStaticDescription() {
		return "Check for deprecated Liferay taglibs.";
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
				LiferayInspectionsGroupNames.JSP_GROUP_NAME
		};
	}

	protected abstract Map<String, Collection<AbstractMap.SimpleImmutableEntry<String, String>>> getTaglibAttributes();

	protected abstract float[] getApplicableLiferayVersions();

	protected String getDeprecationMessage(@NotNull XmlAttribute attribute) {
		return "Attribute '" + attribute.getLocalName() + "' has been deprecated.";
	}
	protected String getDeprecationMessage(@NotNull XmlTag tag) {
		return "Tag '" + tag.getNamespacePrefix() + ":" + tag.getName() + "' has been deprecated.";
	}

	protected boolean isApplicableLiferayVersion(PsiElement psiElement) {
		Module module = ModuleUtilCore.findModuleForPsiElement(psiElement);

		if ((module != null) && (!module.isDisposed())) {
			float version = LiferayModuleComponent.getPortalMajorVersion(module);

			for (float applicableLiferayVersion : getApplicableLiferayVersions()) {
				if (applicableLiferayVersion == version) {
					return true;
				}
			}
		}

		return false;
	}

	@NotNull
	@Override
	public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
		return new XmlElementVisitor() {

			@Override
			public void visitXmlTag(@NotNull XmlTag xmlTag) {
				if (isApplicableLiferayVersion(xmlTag)) {

					String namespace = xmlTag.getNamespace();
					String localName = xmlTag.getLocalName();

					if (getTaglibAttributes().containsKey(namespace)) {
						Collection<AbstractMap.SimpleImmutableEntry<String, String>> entries = getTaglibAttributes().get(namespace);

						List<AbstractMap.SimpleImmutableEntry<String, String>> applicableEntries = entries.stream().filter(e -> e.getKey().equals(localName)).toList();

						for (AbstractMap.SimpleImmutableEntry<String, String> applicableEntry : applicableEntries) {
							if (StringUtil.isEmpty(applicableEntry.getValue())) {
								holder.registerProblem(xmlTag, getDeprecationMessage(xmlTag));
							}
						}
					}
				}
			}

			public void visitXmlAttribute(@NotNull XmlAttribute attribute) {
				if (isApplicableLiferayVersion(attribute)) {
					XmlTag xmlTag = PsiTreeUtil.getParentOfType(attribute, XmlTag.class);

					if (xmlTag != null) {
						String namespace = xmlTag.getNamespace();
						String localName = xmlTag.getLocalName();

						if (getTaglibAttributes().containsKey(namespace)) {
							Collection<AbstractMap.SimpleImmutableEntry<String, String>> entries = getTaglibAttributes().get(namespace);

							List<AbstractMap.SimpleImmutableEntry<String, String>> applicableEntries = entries.stream().filter(e -> e.getKey().equals(localName)).toList();

							for (AbstractMap.SimpleImmutableEntry<String, String> applicableEntry : applicableEntries) {
								if (applicableEntry.getValue().equals(attribute.getLocalName())) {
									holder.registerProblem(attribute, getDeprecationMessage(attribute));
								}
							}
						}
					}
				}
			}
		};
	}
}