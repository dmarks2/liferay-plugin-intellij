package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LiferayTaglibDeprecationInfoHolder {

	private String myNamespace;
	private String myLocalName;
	private String myAttribute;

	private float myMajorLiferayVersion;

	private String myMessage;

	private String myTicket;

	private LocalQuickFix[] quickFixes;

	public static LiferayTaglibDeprecationInfoHolder createTag(float majorLiferayVersion, String namespace, String message, String ticket, String localName) {
		return
				new LiferayTaglibDeprecationInfoHolder()
						.majorLiferayVersion(majorLiferayVersion)
						.namespace(namespace)
						.localName(localName)
						.message(message)
						.ticket(ticket);
	}

	public static ListWrapper createTags(float majorLiferayVersion, String namespace, String message, String ticket, String... localNames) {
		List<LiferayTaglibDeprecationInfoHolder> result = new ArrayList<>();

		for (String localName : localNames) {
			result.add(createTag(majorLiferayVersion, namespace, message, ticket, localName));
		}

		return new ListWrapper(result);
	}

	public static LiferayTaglibDeprecationInfoHolder createAttribute(float majorLiferayVersion, String namespace, String localName, String message, String ticket, String attribute) {
		return
				new LiferayTaglibDeprecationInfoHolder()
						.majorLiferayVersion(majorLiferayVersion)
						.namespace(namespace)
						.localName(localName)
						.attribute(attribute)
						.message(message)
						.ticket(ticket);
	}

	public static ListWrapper createAttributes(float majorLiferayVersion, String namespace, String localName, String message, String ticket, String... attributes) {
		List<LiferayTaglibDeprecationInfoHolder> result = new ArrayList<>();

		for (String attribute : attributes) {
			result.add(createAttribute(majorLiferayVersion, namespace, localName, message, ticket, attribute));
		}

		return new ListWrapper(result);
	}

	public LiferayTaglibDeprecationInfoHolder namespace(String namespace) {
		this.myNamespace = namespace;

		return this;
	}

	public LiferayTaglibDeprecationInfoHolder localName(String localName) {
		this.myLocalName = localName;

		return this;
	}

	public LiferayTaglibDeprecationInfoHolder attribute(String attribute) {
		this.myAttribute = attribute;

		return this;
	}

	public LiferayTaglibDeprecationInfoHolder majorLiferayVersion(float majorLiferayVersion) {
		this.myMajorLiferayVersion = majorLiferayVersion;

		return this;
	}

	public LiferayTaglibDeprecationInfoHolder message(String message) {
		this.myMessage = message;

		return this;
	}

	public LiferayTaglibDeprecationInfoHolder ticket(String ticket) {
		this.myTicket = ticket;

		return this;
	}

	public LiferayTaglibDeprecationInfoHolder quickfix(LocalQuickFix... quickFixes) {
		this.quickFixes = quickFixes;

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

	protected String getDeprecationMessage() {
		return "<html><body>" + myMessage + " (see <a href=\"https://liferay.atlassian.net/browse/" + myTicket + "\">" + myTicket + "</a>)</body></html>";
	}

	protected boolean isApplicableLiferayVersion(PsiElement psiElement) {
		Module module = ModuleUtilCore.findModuleForPsiElement(psiElement);

		if ((module != null) && (!module.isDisposed())) {
			float version = LiferayModuleComponent.getPortalMajorVersion(module);

			return version >= myMajorLiferayVersion;
		}

		return false;
	}

	public static class ListWrapper extends ArrayList<LiferayTaglibDeprecationInfoHolder> {
		public ListWrapper(List<LiferayTaglibDeprecationInfoHolder> list) {
			super(list);
		}

		public ListWrapper quickfix(LocalQuickFix... quickFixes) {
			this.replaceAll(liferayTaglibDeprecationInfoHolder -> liferayTaglibDeprecationInfoHolder.quickfix(quickFixes));

			return this;
		}
	}
}
