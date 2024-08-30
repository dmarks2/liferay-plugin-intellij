package de.dm.intellij.liferay.site.initializer;

import com.intellij.json.psi.JsonFile;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonValue;
import com.intellij.lang.injection.InjectedLanguageManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlTagValue;
import com.intellij.psi.xml.XmlText;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SiteInitializerUtil {

	private static final String SITE_INITIALIZER = "site-initializer";

	public static boolean isSiteInitializerFile(@NotNull VirtualFile virtualFile) {
		return LiferayFileUtil.getParent(virtualFile, SITE_INITIALIZER) != null;
	}

	public static boolean isSiteInitializerFile(@NotNull VirtualFile virtualFile, @NotNull String name) {
		return virtualFile.isValid() && SiteInitializerUtil.isSiteInitializerFile(virtualFile) && (name + ".json").equals(virtualFile.getName());
	}

	public static String getDDMTemplateStructureKey(Project project, VirtualFile ddmTemplateJSONVirtualFile) {
		if (ddmTemplateJSONVirtualFile != null) {
			PsiFile psiFile = PsiManager.getInstance(project).findFile(ddmTemplateJSONVirtualFile);

			if (psiFile instanceof JsonFile jsonFile) {
				JsonValue topLevelValue = jsonFile.getTopLevelValue();

				if (topLevelValue != null) {
					PsiElement[] children = topLevelValue.getChildren();

					for (PsiElement psiElement : children) {
						if (psiElement instanceof JsonProperty jsonProperty) {
							if ("ddmStructureKey".equals(jsonProperty.getName())) {
								JsonValue jsonValue = jsonProperty.getValue();

								if (jsonValue != null) {
									String ddmStructureKey = jsonValue.getText();

									ddmStructureKey = StringUtil.unquoteString(ddmStructureKey);

									return ddmStructureKey;
								}
							}
						}
					}
				}
			}
		}

		return null;
	}

	public static VirtualFile getDDMStructureFile(Project project, String ddmStructureKey, VirtualFile siteInitializerDirectory) {
		String structureKey = getKey(ddmStructureKey);

		VirtualFile ddmStructuresDirectory = LiferayFileUtil.getChild(siteInitializerDirectory, "ddm-structures");

		PsiManager psiManager = PsiManager.getInstance(project);

		if (ddmStructuresDirectory != null) {
			VirtualFile[] children = ddmStructuresDirectory.getChildren();

			for (VirtualFile child : children) {
				if (StringUtil.equals(child.getExtension(), "xml")) {
					PsiFile psiFile = psiManager.findFile(child);

					if (psiFile instanceof XmlFile xmlFile) {
						String structureName = getDDMStructureName(xmlFile);

						if (structureName != null) {
							if (StringUtil.equals(structureKey, getKey(structureName))) {
								return getDDMStructureDefinitionJSONFile(xmlFile);
							}
						}
					}
				}
			}
		}

		return null;
	}

	private static String getDDMStructureName(XmlFile xmlFile) {
		if (xmlFile != null) {
			XmlDocument xmlDocument = xmlFile.getDocument();

			if (xmlDocument != null) {
				XmlTag rootTag = xmlDocument.getRootTag();

				if (rootTag != null) {
					XmlTag structureTag = rootTag.findFirstSubTag("structure");

					if (structureTag != null) {
						XmlTag nameTag = structureTag.findFirstSubTag("name");

						if (nameTag != null) {
							return nameTag.getValue().getTrimmedText();
						}
					}
				}
			}
		}

		return null;
	}

	private static VirtualFile getDDMStructureDefinitionJSONFile(XmlFile xmlFile) {
		if (xmlFile != null) {
			XmlDocument xmlDocument = xmlFile.getDocument();

			if (xmlDocument != null) {
				XmlTag rootTag = xmlDocument.getRootTag();

				if (rootTag != null) {
					XmlTag structureTag = rootTag.findFirstSubTag("structure");

					if (structureTag != null) {
						XmlTag definitionTag = structureTag.findFirstSubTag("definition");

						if (definitionTag != null) {
							return getDefinitionVirtualFileWindow(definitionTag);
						}
					}
				}
			}
		}

		return null;
	}

	private static VirtualFile getDefinitionVirtualFileWindow(XmlTag definitionTag) {
		InjectedLanguageManager injectedLanguageManager = InjectedLanguageManager.getInstance(definitionTag.getProject());

		XmlTagValue tagValue = definitionTag.getValue();

		XmlText[] textElements = tagValue.getTextElements();

		if (textElements.length > 0) {
			XmlText xmlText = textElements[0];

			List<Pair<PsiElement, TextRange>> injectedPsiFiles = injectedLanguageManager.getInjectedPsiFiles(xmlText);

			if (
					(injectedPsiFiles != null) &&
							(!injectedPsiFiles.isEmpty())
			) {
				Pair<PsiElement, TextRange> psiElementTextRangePair = injectedPsiFiles.get(0);

				PsiElement psiElement = psiElementTextRangePair.getFirst();

				if (psiElement instanceof PsiFile psiFile) {
					return psiFile.getVirtualFile();
				}
			}
		}

		return null;
	}

	private static String getKey(String name) {
		name = StringUtil.replace(name, " ", "-");

		name = StringUtil.toUpperCase(name);

		return name;
	}
}
