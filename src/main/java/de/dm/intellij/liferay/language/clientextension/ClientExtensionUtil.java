package de.dm.intellij.liferay.language.clientextension;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLFile;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLMapping;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class ClientExtensionUtil {

	public static boolean isClientExtensionFile(VirtualFile virtualFile) {
		return
				virtualFile.isValid() &&
						virtualFile.getNameWithoutExtension().startsWith("client-extension") &&
						(Objects.equals(virtualFile.getExtension(), "yaml") || Objects.equals(virtualFile.getExtension(), "yml"));
	}

	@Nullable
	public static String getClientExtensionType(PsiFile psiFile) {
		if (psiFile instanceof YAMLFile yamlFile) {
			List<YAMLDocument> documents = yamlFile.getDocuments();

			for (YAMLDocument document : documents) {
				PsiElement[] children = document.getChildren();

				for (PsiElement psiElement : children) {
					if (psiElement instanceof YAMLMapping yamlMapping) {
						Collection<YAMLKeyValue> keyValues = yamlMapping.getKeyValues();

						for (YAMLKeyValue keyValue : keyValues) {
							String key = keyValue.getKeyText();

							if (!"assemble".equals(keyValue.getKeyText())) {
								if (keyValue.getValue() instanceof YAMLMapping childMapping) {
									YAMLKeyValue type = childMapping.getKeyValueByKey("type");

									if (type != null) {
										return type.getValueText();
									}
								}
							}
						}
					}
				}
			}
		}

		return null;
	}

}
