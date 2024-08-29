package de.dm.intellij.liferay.site.initializer;

import com.intellij.json.JsonLanguage;
import com.intellij.lang.injection.MultiHostInjector;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.ElementManipulators;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlText;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.intellij.plugins.intelliLang.inject.InjectedLanguage;
import org.intellij.plugins.intelliLang.inject.InjectorUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SiteInitializerDDMStructureJSONLanguageInjector implements MultiHostInjector {

	private static final String CDATA_START = "<![CDATA[";
	private static final String CDATA_END = "]]>";

	@Override
	public void getLanguagesToInject(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement context) {
		PsiFile psiFile = context.getContainingFile().getOriginalFile();

		VirtualFile virtualFile = psiFile.getVirtualFile();

		if (
				virtualFile != null &&
				(SiteInitializerUtil.isSiteInitializerFile(virtualFile)) &&
				(LiferayFileUtil.getParent(virtualFile, "ddm-structures") != null)
		) {
			if (context instanceof XmlTag xmlTag) {
				String tagName = xmlTag.getName();

				if ("definition".equals(tagName)) {
					InjectedLanguage jsonLanguage = InjectedLanguage.create(JsonLanguage.INSTANCE.getID(), "", "", true);

					List<InjectorUtils.InjectionInfo> list = new ArrayList<>();

					PsiElement[] myChildren = xmlTag.getChildren();

					for (PsiElement child : myChildren) {
						if (child instanceof XmlText) {
							if (((PsiLanguageInjectionHost) child).isValidHost()) {
								TextRange textRange = ElementManipulators.getManipulator(child).getRangeInElement(child);

								String text = child.getText();

								if (text != null) {
									int cdataStart = text.indexOf(CDATA_START);
									int cdataEnd = text.indexOf(CDATA_END);

									if (cdataStart > -1 && cdataEnd > -1 && cdataEnd > cdataStart) {
										textRange = new TextRange(cdataStart + CDATA_START.length(), cdataEnd);
									}

									list.add(
											new InjectorUtils.InjectionInfo(
													((PsiLanguageInjectionHost) child),
													jsonLanguage,
													textRange
											)
									);
								}
							}
						}
					}

					if (!list.isEmpty()) {
						InjectorUtils.registerInjection(
								JsonLanguage.INSTANCE,
								xmlTag.getContainingFile(),
								list,
								registrar
						);
					}
				}
			}
		}
	}

	@NotNull
	public List<? extends Class<? extends PsiElement>> elementsToInjectIn() {
		return List.of(XmlTag.class);
	}
}
