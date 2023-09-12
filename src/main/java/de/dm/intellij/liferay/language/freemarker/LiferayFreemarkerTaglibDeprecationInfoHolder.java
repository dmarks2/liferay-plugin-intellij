package de.dm.intellij.liferay.language.freemarker;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.freemarker.psi.FtlNameValuePair;
import com.intellij.freemarker.psi.directives.FtlMacro;
import com.intellij.psi.util.PsiTreeUtil;
import de.dm.intellij.liferay.language.jsp.AbstractLiferayInspectionInfoHolder;
import de.dm.intellij.liferay.language.jsp.LiferayJspTaglibDeprecationInfoHolder;
import de.dm.intellij.liferay.util.LiferayTaglibAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LiferayFreemarkerTaglibDeprecationInfoHolder extends AbstractLiferayInspectionInfoHolder<LiferayFreemarkerTaglibDeprecationInfoHolder> {

	private String myNamespace;
	private String myLocalName;
	private String myAttribute;

	private static LiferayFreemarkerTaglibDeprecationInfoHolder createTag(float majorLiferayVersion, String namespace, String message, String ticket, String localName) {
		return
				new LiferayFreemarkerTaglibDeprecationInfoHolder()
						.namespace(namespace)
						.localName(localName)
						.majorLiferayVersion(majorLiferayVersion)
						.message(message)
						.ticket(ticket);
	}

	public static ListWrapper<LiferayFreemarkerTaglibDeprecationInfoHolder> createTags(LiferayTaglibAttributes.TaglibDeprecationTags tags) {
		List<LiferayFreemarkerTaglibDeprecationInfoHolder> result = new ArrayList<>();

		for (String localName : tags.localNames()) {
			result.add(createTag(tags.majorLiferayVersion(), tags.namespace(), tags.message(), tags.ticket(), localName));
		}

		return new ListWrapper<>(result);
	}

	private static LiferayFreemarkerTaglibDeprecationInfoHolder createAttribute(float majorLiferayVersion, String namespace, String localName, String message, String ticket, String attribute) {
		return
				new LiferayFreemarkerTaglibDeprecationInfoHolder()
						.namespace(namespace)
						.localName(localName)
						.attribute(attribute)
						.majorLiferayVersion(majorLiferayVersion)
						.message(message)
						.ticket(ticket);
	}

	public static ListWrapper<LiferayFreemarkerTaglibDeprecationInfoHolder> createAttributes(LiferayTaglibAttributes.TaglibDeprecationAttributes attributes) {
		List<LiferayFreemarkerTaglibDeprecationInfoHolder> result = new ArrayList<>();

		for (String attribute : attributes.attributes()) {
			result.add(createAttribute(attributes.majorLiferayVersion(), attributes.namespace(), attributes.localName(), attributes.message(), attributes.ticket(), attribute));
		}

		return new ListWrapper<>(result);
	}

	public LiferayFreemarkerTaglibDeprecationInfoHolder namespace(String namespace) {
		this.myNamespace = namespace;

		return this;
	}

	public LiferayFreemarkerTaglibDeprecationInfoHolder localName(String localName) {
		this.myLocalName = localName;

		return this;
	}

	public LiferayFreemarkerTaglibDeprecationInfoHolder attribute(String attribute) {
		this.myAttribute = attribute;

		return this;
	}
	public void visitFtlNameValuePair(ProblemsHolder holder, FtlNameValuePair ftlNameValuePair) {
		FtlMacro ftlMacro = PsiTreeUtil.getParentOfType(ftlNameValuePair, FtlMacro.class);

		if (ftlMacro != null) {
			if (
					(isApplicableLiferayVersion(ftlNameValuePair)) &&
					(Objects.equals(LiferayFreemarkerTaglibs.getNamespace(ftlMacro), myNamespace)) &&
					(Objects.equals(LiferayFreemarkerTaglibs.getLocalName(ftlMacro), myLocalName)) &&
					(Objects.equals(ftlNameValuePair.getName(), myAttribute))
			) {
				holder.registerProblem(ftlNameValuePair, getDeprecationMessage(), quickFixes);
			}
		}
	}

}
