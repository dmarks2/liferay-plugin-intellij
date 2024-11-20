package de.dm.intellij.liferay.language.freemarker;

import com.intellij.freemarker.psi.files.FtlXmlNamespaceType;
import com.intellij.freemarker.psi.variables.FtlVariable;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.LibraryOrderEntry;
import com.intellij.openapi.roots.ModuleOrderEntry;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ModuleSourceOrderEntry;
import com.intellij.openapi.roots.OrderEnumerator;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.xml.XmlNSDescriptor;
import de.dm.intellij.liferay.language.freemarker.custom.CustomFtlVariable;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;

public class LiferayFreemarkerTaglibsUtil {

	private final static Logger log = Logger.getInstance(LiferayFreemarkerTaglibsUtil.class);

	public static void getCustomTaglibMappings(@NotNull Module module, Map<String, List<? extends FtlVariable>> customTaglibMappings) {
		Project project = module.getProject();

		ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);

		OrderEnumerator orderEntries = moduleRootManager.orderEntries();

		orderEntries.forEach(orderEntry -> {
			if (
					(orderEntry instanceof ModuleSourceOrderEntry) ||
					(orderEntry instanceof ModuleOrderEntry)
			) {
				Module orderEntryModule;

				if (orderEntry instanceof ModuleSourceOrderEntry moduleSourceOrderEntry) {
					orderEntryModule = moduleSourceOrderEntry.getOwnerModule();
				} else {
					ModuleOrderEntry moduleOrderEntry = (ModuleOrderEntry) orderEntry;

					orderEntryModule = moduleOrderEntry.getModule();
				}

				if (orderEntryModule != null) {
					handleModule(orderEntryModule, customTaglibMappings);
				}
			} else if (orderEntry instanceof LibraryOrderEntry libraryOrderEntry) {
				Library library = libraryOrderEntry.getLibrary();

				if (library != null) {
					handleLibrary(project, library, customTaglibMappings);
				}
			}

			return true;
		});
	}

	private static void handleModule(@NotNull Module module, @NotNull Map<String, List<? extends FtlVariable>> customTaglibMappings) {
		ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);

		VirtualFile[] sourceRoots = moduleRootManager.getSourceRoots();

		for (VirtualFile contentRoot : sourceRoots) {
			VirtualFile taglibMappingsProperties = LiferayFileUtil.getChild(contentRoot, "META-INF/taglib-mappings.properties");

			if (taglibMappingsProperties != null) {
				try {
					Project project = module.getProject();

					handleTaglibMappingsProperties(project, customTaglibMappings, taglibMappingsProperties, contentRoot);
				} catch (IOException e) {
					log.warn("Unable to add " + taglibMappingsProperties.getPath() + " as taglib: " + e.getMessage(), e);
				}
			}
		}
	}

	private static void handleLibrary(@NotNull Project project, @NotNull Library library, @NotNull Map<String, List<? extends FtlVariable>> customTaglibMappings) {
		VirtualFile[] files = library.getFiles(OrderRootType.CLASSES);

		for (VirtualFile file : files) {
			VirtualFile root = LiferayFileUtil.getJarRoot(file);

			if (root != null) {
				VirtualFile taglibMappingsProperties = LiferayFileUtil.getChild(root, "META-INF/taglib-mappings.properties");

				if (taglibMappingsProperties != null) {
					try {
						handleTaglibMappingsProperties(project, customTaglibMappings, taglibMappingsProperties, root);
					} catch (IOException e) {
						log.warn("Unable to add " + taglibMappingsProperties.getPath() + " as taglib: " + e.getMessage(), e);
					}
				}
			}
		}
	}

	private static void handleTaglibMappingsProperties(@NotNull Project project, @NotNull Map<String, List<? extends FtlVariable>> customTaglibMappings, @NotNull VirtualFile taglibMappingsProperties, VirtualFile root) throws IOException {
		PropertyResourceBundle resourceBundle = new PropertyResourceBundle(taglibMappingsProperties.getInputStream());

		for (Enumeration<String> enumeration = resourceBundle.getKeys(); enumeration.hasMoreElements(); ) {
			String key = enumeration.nextElement();

			String value = resourceBundle.getString(key);

			if (StringUtil.isNotEmpty(value)) {
				value = StringUtil.substringAfter(value, "/");

				VirtualFile tldFile = LiferayFileUtil.getChild(root, value);

				if (tldFile != null) {
					if (! customTaglibMappings.containsKey(key)) {
						if (log.isDebugEnabled()) {
							log.debug("Adding " + tldFile.getPath() + " with taglib prefix " + key);
						}

						customTaglibMappings.put(key, getTaglibSupportVariables(project, key, tldFile));
					}
				}
			}
		}
	}

	private static List<? extends FtlVariable> getTaglibSupportVariables(Project project, @NonNls final String taglibPrefix, VirtualFile tldFile) {
		XmlFile xmlFile = (XmlFile)PsiManager.getInstance(project).findFile(tldFile);

		if (xmlFile == null) {
			return Collections.emptyList();
		}

		final XmlDocument document = xmlFile.getDocument();
		if (document == null) {
			return Collections.emptyList();
		}

		final XmlNSDescriptor descriptor = (XmlNSDescriptor) document.getMetaData();
		if (descriptor == null) {
			return Collections.emptyList();
		}

		PsiElement declaration = descriptor.getDeclaration();
		if (declaration == null) {
			declaration = xmlFile;
		}

		return List.of(new CustomFtlVariable(taglibPrefix, declaration, new FtlXmlNamespaceType(descriptor)));
	}


}
