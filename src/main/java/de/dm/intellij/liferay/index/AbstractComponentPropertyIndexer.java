package de.dm.intellij.liferay.index;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiAnnotationParameterList;
import com.intellij.psi.PsiArrayInitializerMemberValue;
import com.intellij.psi.PsiBinaryExpression;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaCodeReferenceElement;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiNameValuePair;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.indexing.DataIndexer;
import com.intellij.util.indexing.FileContent;
import de.dm.intellij.liferay.language.osgi.ComponentPropertiesCompletionContributor;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractComponentPropertyIndexer<Key> implements DataIndexer<Key, Void, FileContent> {

	@NotNull
	@Override
	public Map<Key, Void> map(@NotNull FileContent fileContent) {
		FileType fileType = fileContent.getFileType();

		if (fileType.isBinary()) {
			return Collections.emptyMap();
		}

		CharSequence text = fileContent.getContentAsText();

		if (! _isCandidateForIndexing(text)) {
			return Collections.emptyMap();
		}

		Map<Key, Void> map = new HashMap<>();

		PsiJavaFile psiJavaFile = getPsiJavaFileForPsiDependentIndex(fileContent);

		if (psiJavaFile == null) {
			return map;
		}

		PsiClass[] psiClasses = psiJavaFile.getClasses();

		String[] serviceClassNames = getServiceClassNames();

		for (PsiClass psiClass : psiClasses) {
			for (String serviceClassName : serviceClassNames) {
				Map<String, Collection<String>> componentProperties = getComponentProperties(psiClass, serviceClassName);

				if (componentProperties != null) {
					processProperties(map, componentProperties, psiClass, serviceClassName);
				}
			}
		}

		return map;
	}

	@Nullable
	protected PsiJavaFile getPsiJavaFileForPsiDependentIndex(@NotNull FileContent fileContent) {
		VirtualFile virtualFile = fileContent.getFile();

		if (!virtualFile.isValid()) {
			return null;
		}

		if (!JavaFileType.INSTANCE.equals(virtualFile.getFileType())) {
			return null;
		}

		PsiFile psiFile = fileContent.getPsiFile();

		if (!(psiFile instanceof PsiJavaFile)) {
			return null;
		}

		return (PsiJavaFile) psiFile;
	}

	@NotNull
	protected abstract String[] getServiceClassNames();

	protected abstract void processProperties(@NotNull Map<Key, Void> map, @NotNull Map<String, Collection<String>> properties, @NotNull PsiClass psiClass, String serviceClassName);

	@Nullable
	protected Map<String, Collection<String>> getComponentProperties(PsiClass psiClass, String requiredServiceClassName) {
		for (PsiAnnotation psiAnnotation : psiClass.getAnnotations()) {
			PsiJavaCodeReferenceElement nameReferenceElement = psiAnnotation.getNameReferenceElement();

			if (nameReferenceElement != null) {
				String qualifiedName = ProjectUtils.getQualifiedNameWithoutResolve(nameReferenceElement, false);

				if ("org.osgi.service.component.annotations.Component".equals(qualifiedName)) {
					PsiAnnotationParameterList psiAnnotationParameterList = psiAnnotation.getParameterList();

					List<String> serviceClassNames = ComponentPropertiesCompletionContributor.getServiceClassNames(psiAnnotationParameterList);
					if (!(serviceClassNames.isEmpty())) {
						for (String serviceClassName : serviceClassNames) {
							if (requiredServiceClassName.equals(serviceClassName)) {
								Map<String, Collection<String>> properties = new HashMap<>();

								for (PsiNameValuePair psiNameValuePair : psiAnnotationParameterList.getAttributes()) {
									if ("property".equals(psiNameValuePair.getName())) {
										PsiAnnotationMemberValue psiNameValuePairValue = psiNameValuePair.getValue();

										if (psiNameValuePairValue instanceof PsiArrayInitializerMemberValue psiArrayInitializerMemberValue) {
											PsiAnnotationMemberValue[] initializers = psiArrayInitializerMemberValue.getInitializers();

											for (PsiAnnotationMemberValue initializer : initializers) {
												if (initializer instanceof PsiLiteralExpression) {
													AbstractMap.SimpleImmutableEntry<String, String> property = getProperty((PsiLiteralExpression) initializer);
													if (property != null) {
														Collection<String> values = properties.computeIfAbsent(property.getKey(), k -> new ArrayList<>());
														values.add(property.getValue());
													}
												}
												else if (initializer instanceof PsiBinaryExpression) {
													AbstractMap.SimpleImmutableEntry<String, String> property = getProperty((PsiBinaryExpression) initializer);
													if (property != null) {
														Collection<String> values = properties.computeIfAbsent(property.getKey(), k -> new ArrayList<>());
														values.add(property.getValue());
													}
												}
											}
										}
									}
								}

								return properties;
							}
						}
					}
				}
			}
		}

		return null;
	}

	@Nullable
	protected AbstractMap.SimpleImmutableEntry<String, String> getProperty(@NotNull PsiLiteralExpression psiLiteralExpression) {
		String text = psiLiteralExpression.getText();
		text = StringUtil.unquoteString(text);

		int eqIndex = text.indexOf('=');

		if (eqIndex > 0 && eqIndex < text.length() - 1) {
			return new AbstractMap.SimpleImmutableEntry<>(text.substring(0, eqIndex), text.substring(eqIndex + 1));
		}

		return null;
	}

	@Nullable
	protected AbstractMap.SimpleImmutableEntry<String, String> getProperty(@NotNull PsiBinaryExpression psiBinaryExpression) {
		PsiLiteralExpression psiLiteralExpression = PsiTreeUtil.getChildOfType(psiBinaryExpression, PsiLiteralExpression.class);
		if (psiLiteralExpression != null) {
			String text = psiLiteralExpression.getText();
			text = StringUtil.unquoteString(text);

			int eqIndex = text.indexOf('=');

			if (eqIndex > 0) {
				String firstPart = text.substring(0, eqIndex);

				PsiReferenceExpression psiReferenceExpression = PsiTreeUtil.getChildOfType(psiBinaryExpression, PsiReferenceExpression.class);

				if (psiReferenceExpression != null) {
					String qualifiedName = ProjectUtils.getQualifiedNameWithoutResolve(psiReferenceExpression, true);

					return new AbstractMap.SimpleImmutableEntry<>(firstPart, ProjectUtils.REFERENCE_PLACEHOLDER + qualifiedName);
				}
			}
		}

		return null;
	}

	private boolean _isCandidateForIndexing(CharSequence text) {
		return StringUtil.indexOf(text, "@Component") >= 0;
	}

}
