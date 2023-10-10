package de.dm.intellij.liferay.language.properties;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.lang.properties.psi.impl.PropertyImpl;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import de.dm.intellij.liferay.language.jsp.AbstractLiferayInspectionInfoHolder;
import de.dm.intellij.liferay.module.LiferayModuleComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LiferayPropertiesDeprecationInfoHolder extends AbstractLiferayInspectionInfoHolder<LiferayPropertiesDeprecationInfoHolder> {

	private String myPropertyName;
	private String myPropertyValue;
	private boolean myMatchVersion;

	private static LiferayPropertiesDeprecationInfoHolder createProperty(float majorLiferayVersion, String message, String ticket, String propertyName) {
		return
				new LiferayPropertiesDeprecationInfoHolder()
						.property(propertyName)
						.majorLiferayVersion(majorLiferayVersion)
						.message(message)
						.ticket(ticket);
	}

	private static LiferayPropertiesDeprecationInfoHolder createPropertyValue(float majorLiferayVersion, String message, String ticket, boolean matchVersion, String propertyName, String propertyValue) {
		return
				new LiferayPropertiesDeprecationInfoHolder()
						.property(propertyName)
						.value(propertyValue)
						.matchVersion(matchVersion)
						.majorLiferayVersion(majorLiferayVersion)
						.message(message)
						.ticket(ticket);
	}

	public static ListWrapper<LiferayPropertiesDeprecationInfoHolder> createProperties(float majorLiferayVersion, String message, String ticket, String... properties) {
		List<LiferayPropertiesDeprecationInfoHolder> result = new ArrayList<>();

		for (String property : properties) {
			result.add(createProperty(majorLiferayVersion, message, ticket, property));
		}

		return new ListWrapper<>(result);
	}
	public static ListWrapper<LiferayPropertiesDeprecationInfoHolder> createPropertyValues(float majorLiferayVersion, String message, String ticket, boolean matchVersion, String property, String... values) {
		List<LiferayPropertiesDeprecationInfoHolder> result = new ArrayList<>();

		for (String value : values) {
			result.add(createPropertyValue(majorLiferayVersion, message, ticket, matchVersion, property, value));
		}

		return new ListWrapper<>(result);
	}

	public LiferayPropertiesDeprecationInfoHolder property(String propertyName) {
		this.myPropertyName = propertyName;

		return this;
	}

	public LiferayPropertiesDeprecationInfoHolder value(String propertyValue) {
		this.myPropertyValue = propertyValue;

		return this;
	}
	public LiferayPropertiesDeprecationInfoHolder matchVersion(boolean matchVersion) {
		this.myMatchVersion = myMatchVersion;

		return this;
	}

	public void visitProperty(ProblemsHolder holder, PropertyImpl property) {
		if (
				(isApplicableLiferayVersion(property)) &&
				(StringUtil.isNotEmpty(myPropertyName)) &&
				(
					(Objects.equals(myPropertyName, property.getName())) ||
					(StringUtil.endsWith(myPropertyName, ".*") && Objects.requireNonNull(property.getName()).startsWith(Objects.requireNonNull(StringUtil.substringBefore(myPropertyName, ".*"))))
				) &&
				(
						(myPropertyValue == null) ||
						(Objects.equals(myPropertyValue, property.getValue()))
				)
		) {
			holder.registerProblem(property, getDeprecationMessage(), quickFixes);
		}
	}

	@Override
	protected boolean isApplicableLiferayVersion(PsiElement psiElement) {
		if (!myMatchVersion) {
			return super.isApplicableLiferayVersion(psiElement);
		}

		Module module = ModuleUtilCore.findModuleForPsiElement(psiElement);

		if ((module != null) && (!module.isDisposed())) {
			float version = LiferayModuleComponent.getPortalMajorVersion(module);

			return (version == myMajorLiferayVersion);
		}

		return false;
	}
}
