package de.dm.intellij.liferay.language.jsp;

import com.intellij.lang.ecmascript6.resolve.JSFileReferencesUtil;
import com.intellij.lang.ecmascript6.resolve.JSProcessedPath;
import com.intellij.lang.javascript.frameworks.modules.JSModuleFileReferenceSet;
import com.intellij.lang.javascript.frameworks.modules.resolver.JSDefaultFileReferenceContext;
import com.intellij.lang.typescript.tsconfig.TypeScriptConfigUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.util.ProcessingContext;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import de.dm.intellij.liferay.util.LiferayTaglibAttributes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;

public class LiferayTaglibJavascriptFileReferenceContributor extends AbstractLiferayTaglibReferenceContributor{

	@Override
	protected PsiReferenceProvider getReferenceProvider() {
		return new PsiReferenceProvider() {

			@Override
			public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
				String text = StringUtil.unquoteString(element.getText());

				JSProcessedPath actualPath = JSProcessedPath.getActualPath(text, 0);

				String modulePath = actualPath.fixedPath;

				String scheme = JSFileReferencesUtil.findFileUrlPrefix(modulePath);
				if (scheme != null) {
					modulePath = modulePath.substring(scheme.length());
				}

				JSDefaultFileReferenceContext jsDefaultFileReferenceContext = new JSDefaultFileReferenceContext(modulePath, element, TypeScriptConfigUtil.getConfigForPsiFile(element.getContainingFile(), true)) {
					public boolean isUrlEncoded() {
						return scheme != null;
					}

					@Override
					public @Nullable VirtualFile baseUrl() {
						return LiferayFileUtil.getWebContextForFile(element.getContainingFile().getOriginalFile());
					}
				};

				JSModuleFileReferenceSet jsModuleFileReferenceSet = new JSModuleFileReferenceSet(text, jsDefaultFileReferenceContext, element, 0);

				return jsModuleFileReferenceSet.getAllReferences();
			}
		};
	}

	@Override
	protected String[] getAttributeNames() {
		return new String[] {
				"buttonPropsTransformer",
				"defaultEventHandler",
				"module",
				"propsTransformer"
		};
	}

	@Override
	protected Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> getTaglibMap() {
		return LiferayTaglibAttributes.TAGLIB_JAVASCRIPT_FILES;
	}
}
