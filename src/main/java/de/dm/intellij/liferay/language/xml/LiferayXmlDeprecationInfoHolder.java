package de.dm.intellij.liferay.language.xml;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlDoctype;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlProlog;
import com.intellij.psi.xml.XmlTag;
import de.dm.intellij.liferay.language.jsp.AbstractLiferayInspectionInfoHolder;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LiferayXmlDeprecationInfoHolder extends AbstractLiferayInspectionInfoHolder<LiferayXmlDeprecationInfoHolder> {

	private String myPublicId;
	private String myDtdUri;

	private String myUrn;

	private String mySchemaLocation;

	private static LiferayXmlDeprecationInfoHolder createDtd(float majorLiferayVersion, String message, String publicId, String dtdUri) {
		return
				new LiferayXmlDeprecationInfoHolder()
						.dtd(publicId, dtdUri)
						.majorLiferayVersion(majorLiferayVersion)
						.message(message);
	}

	private static LiferayXmlDeprecationInfoHolder createNamespace(float majorLiferayVersion, String message, String urn, String schemaLocation) {
		return
				new LiferayXmlDeprecationInfoHolder()
						.namespace(urn, schemaLocation)
						.majorLiferayVersion(majorLiferayVersion)
						.message(message);
	}

	public static ListWrapper<LiferayXmlDeprecationInfoHolder> createDtds(LiferayXmlDeprecations.DtdDeprecation dtdDeprecation) {
		List<LiferayXmlDeprecationInfoHolder> result = new ArrayList<>();

		for (LiferayXmlDeprecations.PublicIdDtdUri publicIdDtdUri : dtdDeprecation.dtds()) {
			LiferayXmlDeprecationInfoHolder deprecationInfoHolder = createDtd(dtdDeprecation.majorLiferayVersion(), "The descriptor XML DTD versions should be matched with the Liferay version",  publicIdDtdUri.publicId(), publicIdDtdUri.dtdUri());

			deprecationInfoHolder = deprecationInfoHolder.quickfix(updateDtd(dtdDeprecation.newDtd()));

			result.add(deprecationInfoHolder);
		}

		return new ListWrapper<>(result);
	}

	public static ListWrapper<LiferayXmlDeprecationInfoHolder> createNamespaces(LiferayXmlDeprecations.NamespaceDeprecation namespaceDeprecation) {
		List<LiferayXmlDeprecationInfoHolder> result = new ArrayList<>();

		for (LiferayXmlDeprecations.UrnSchemaLocation urnSchemaLocation : namespaceDeprecation.namespaces()) {
			LiferayXmlDeprecationInfoHolder deprecationInfoHolder = createNamespace(namespaceDeprecation.majorLiferayVersion(), "The XML Schema Location should be matched with the Liferay version",  urnSchemaLocation.urn(), urnSchemaLocation.schemaLocation());

			deprecationInfoHolder = deprecationInfoHolder.quickfix(updateNamespace(namespaceDeprecation.newNamespace()));

			result.add(deprecationInfoHolder);
		}

		return new ListWrapper<>(result);
	}

	public LiferayXmlDeprecationInfoHolder dtd(String publicId, String dtdUri) {
		this.myPublicId = publicId;
		this.myDtdUri = dtdUri;

		return this;
	}

	public LiferayXmlDeprecationInfoHolder namespace(String urn, String schemaLocation) {
		this.myUrn = urn;
		this.mySchemaLocation = schemaLocation;

		return this;
	}

	public void visitDoctype(ProblemsHolder holder, XmlDoctype xmlDoctype) {
		if (
				(isApplicableLiferayVersion(xmlDoctype)) &&
				(StringUtil.isNotEmpty(myPublicId)) &&
				(StringUtil.isNotEmpty(myDtdUri)) &&
				(Objects.equals(myPublicId, xmlDoctype.getPublicId())) &&
				(Objects.equals(myDtdUri, xmlDoctype.getDtdUri()))
		) {
			holder.registerProblem(xmlDoctype, getDeprecationMessage(), quickFixes);
		}
	}

	public void visitNamespaceDeclaration(ProblemsHolder holder, XmlTag rootTag) {
		if (
				(rootTag.hasNamespaceDeclarations()) &&
				(isApplicableLiferayVersion(rootTag)) &&
				(StringUtil.isNotEmpty(myUrn)) &&
				(StringUtil.isNotEmpty(mySchemaLocation)) &&
				(StringUtil.equals(getSchemaLocationString(rootTag), myUrn + " " + mySchemaLocation))
		) {
			holder.registerProblem(rootTag, getDeprecationMessage(), quickFixes);
		}
	}

	private static LocalQuickFix updateDtd(LiferayXmlDeprecations.PublicIdDtdUri newDtd) {
		return new UpdateDtdStatementQuickFix(newDtd);
	}

	private static class UpdateDtdStatementQuickFix implements LocalQuickFix {
		private final LiferayXmlDeprecations.PublicIdDtdUri newDtd;

		public UpdateDtdStatementQuickFix(LiferayXmlDeprecations.PublicIdDtdUri newDtd) {
			this.newDtd = newDtd;
		}

		@Nls
		@NotNull
		@Override
		public String getFamilyName() {
			return "Update DTD Definition";
		}

		@Nls
		@NotNull
		@Override
		public String getName() {
			return "Update DTD Definition to " + newDtd.publicId();
		}

		@Override
		public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
			PsiElement psiElement = descriptor.getPsiElement();

			XmlDoctype doctype;

			if (psiElement instanceof XmlDoctype) {
				doctype = (XmlDoctype) psiElement;
			} else {
				doctype = PsiTreeUtil.getParentOfType(psiElement, XmlDoctype.class);
			}

			if (doctype == null) {
				return;
			}

			XmlFile xmlFile = createDummyXmlFile(project, "<!DOCTYPE var PUBLIC \"" + newDtd.publicId() + "\" \"" + newDtd.dtdUri() + "\"><root />");

			XmlDocument document = xmlFile.getDocument();

			if (document != null) {
				XmlProlog prolog = document.getProlog();

				if (prolog != null) {
					XmlDoctype newDoctype = prolog.getDoctype();

					if (newDoctype != null) {
						newDoctype.getNameElement().replace(doctype.getNameElement());

						doctype.replace(newDoctype);
					}
				}
			}
		}
	}

	private static LocalQuickFix updateNamespace(LiferayXmlDeprecations.UrnSchemaLocation newNamespace) {
		return new UpdateNamespaceQuickFix(newNamespace);
	}

	private static class UpdateNamespaceQuickFix implements LocalQuickFix {
		private final LiferayXmlDeprecations.UrnSchemaLocation newNamespace;

		public UpdateNamespaceQuickFix(LiferayXmlDeprecations.UrnSchemaLocation newNamespace) {
			this.newNamespace = newNamespace;
		}

		@Nls
		@NotNull
		@Override
		public String getFamilyName() {
			return "Update Namespace";
		}

		@Nls
		@NotNull
		@Override
		public String getName() {
			return "Update Namespace to " + newNamespace.urn();
		}

		@Override
		public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
			PsiElement psiElement = descriptor.getPsiElement();

			XmlTag xmlTag;

			if (psiElement instanceof XmlTag) {
				xmlTag = (XmlTag) psiElement;
			} else {
				xmlTag = PsiTreeUtil.getParentOfType(psiElement, XmlTag.class);
			}

			if (xmlTag == null) {
				return;
			}

			XmlFile xmlFile = createDummyXmlFile(project, "<root />");

			XmlDocument document = xmlFile.getDocument();

			if (document != null) {
				XmlTag rootTag = document.getRootTag();

				if (rootTag != null) {
					rootTag.setName(xmlTag.getName());

					for (XmlAttribute attribute : xmlTag.getAttributes()) {
						if 	(
								(Objects.equals("http://www.w3.org/2001/XMLSchema-instance", attribute.getNamespace())) &&
								(Objects.equals("schemaLocation", attribute.getLocalName()))
						) {
							rootTag.setAttribute(attribute.getLocalName(), attribute.getNamespace(), newNamespace.urn() + " " + newNamespace.schemaLocation());
						} else if (
								(Objects.equals("xmlns", attribute.getLocalName())) &&
								(StringUtil.isEmpty(attribute.getNamespace()))
						) {
							rootTag.setAttribute(attribute.getLocalName(), "", newNamespace.urn());
						} else {
							rootTag.setAttribute(attribute.getName(), attribute.getNamespace(), attribute.getValue());
						}
					}

					for (XmlTag child : xmlTag.getSubTags()) {
						rootTag.add(child);
					}

					xmlTag.replace(rootTag);
				}
			}
		}
	}

	private static XmlFile createDummyXmlFile(Project project, String text) {
		return (XmlFile) PsiFileFactory.getInstance(project).createFileFromText("_Dummy_." + XmlFileType.INSTANCE.getDefaultExtension(), XmlFileType.INSTANCE, text);
	}

	protected boolean isApplicableLiferayVersion(PsiElement psiElement) {
		Module module = ModuleUtilCore.findModuleForPsiElement(psiElement);

		if ((module != null) && (!module.isDisposed())) {
			float version = LiferayModuleComponent.getPortalMajorVersion(module);

			return (version == myMajorLiferayVersion);
		}

		return false;
	}

	private String getSchemaLocationString(XmlTag rootTag) {
		for (XmlAttribute attribute : rootTag.getAttributes()) {
			if (
					(Objects.equals("http://www.w3.org/2001/XMLSchema-instance", attribute.getNamespace())) &&
					(Objects.equals("schemaLocation", attribute.getLocalName()))
			) {
				return attribute.getValue();
			}
		}

		return null;
	}
}
