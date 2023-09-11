package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LiferayJspTaglibDeprecationInfoHolder extends AbstractLiferayInspectionInfoHolder<LiferayJspTaglibDeprecationInfoHolder> {

	private String myNamespace;
	private String myLocalName;
	private String myAttribute;

	public static LiferayJspTaglibDeprecationInfoHolder createTag(float majorLiferayVersion, String namespace, String message, String ticket, String localName) {
		return
				new LiferayJspTaglibDeprecationInfoHolder()
						.namespace(namespace)
						.localName(localName)
						.majorLiferayVersion(majorLiferayVersion)
						.message(message)
						.ticket(ticket);
	}

	public static ListWrapper<LiferayJspTaglibDeprecationInfoHolder> createTags(float majorLiferayVersion, String namespace, String message, String ticket, String... localNames) {
		List<LiferayJspTaglibDeprecationInfoHolder> result = new ArrayList<>();

		for (String localName : localNames) {
			result.add(createTag(majorLiferayVersion, namespace, message, ticket, localName));
		}

		return new ListWrapper<>(result);
	}

	public static LiferayJspTaglibDeprecationInfoHolder createAttribute(float majorLiferayVersion, String namespace, String localName, String message, String ticket, String attribute) {
		return
				new LiferayJspTaglibDeprecationInfoHolder()
						.namespace(namespace)
						.localName(localName)
						.attribute(attribute)
						.majorLiferayVersion(majorLiferayVersion)
						.message(message)
						.ticket(ticket);
	}

	public static ListWrapper<LiferayJspTaglibDeprecationInfoHolder> createAttributes(float majorLiferayVersion, String namespace, String localName, String message, String ticket, String... attributes) {
		List<LiferayJspTaglibDeprecationInfoHolder> result = new ArrayList<>();

		for (String attribute : attributes) {
			result.add(createAttribute(majorLiferayVersion, namespace, localName, message, ticket, attribute));
		}

		return new ListWrapper<>(result);
	}

	public LiferayJspTaglibDeprecationInfoHolder namespace(String namespace) {
		this.myNamespace = namespace;

		return this;
	}

	public LiferayJspTaglibDeprecationInfoHolder localName(String localName) {
		this.myLocalName = localName;

		return this;
	}

	public LiferayJspTaglibDeprecationInfoHolder attribute(String attribute) {
		this.myAttribute = attribute;

		return this;
	}


	public void visitXmlTag(@NotNull ProblemsHolder holder, @NotNull XmlTag xmlTag) {
		if (
				(isApplicableLiferayVersion(xmlTag)) &&
				(Objects.equals(xmlTag.getNamespace(), myNamespace)) &&
				(Objects.equals(xmlTag.getLocalName(), myLocalName)) &&
				(StringUtil.isEmpty(myAttribute))
		) {
			holder.registerProblem(xmlTag, getDeprecationMessage(), quickFixes);
		}
	}

	public void visitXmlAttribute(@NotNull ProblemsHolder holder, @NotNull XmlAttribute attribute) {
		XmlTag xmlTag = PsiTreeUtil.getParentOfType(attribute, XmlTag.class);

		if (
				(xmlTag != null) &&
				(isApplicableLiferayVersion(xmlTag)) &&
				(Objects.equals(xmlTag.getNamespace(), myNamespace)) &&
				(Objects.equals(xmlTag.getLocalName(), myLocalName)) &&
				(Objects.equals(attribute.getLocalName(), myAttribute))
		) {
			holder.registerProblem(attribute, getDeprecationMessage(), quickFixes);
		}
	}

}
