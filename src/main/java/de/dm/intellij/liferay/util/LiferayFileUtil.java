package de.dm.intellij.liferay.util;

import com.intellij.injected.editor.VirtualFileWindow;
import com.intellij.javaee.web.WebRoot;
import com.intellij.javaee.web.facet.WebFacet;
import com.intellij.json.psi.JsonFile;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonValue;
import com.intellij.openapi.fileEditor.impl.LoadTextUtil;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.JarFileSystem;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileSystem;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceHelper;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.site.initializer.SiteInitializerUtil;
import de.dm.intellij.liferay.theme.LiferayLookAndFeelXmlParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LiferayFileUtil {

    private static final String TEMPLATES = "templates";
    private static final String JOURNAL = "journal";
    private static final String STRUCTURES = "structures";
    private static final String APPLICATION_DISPLAY = "application_display";
    private static final String LAYOUTTPL = "layouttpl";

    public static boolean isJournalTemplateFile(PsiFile psiFile) {
        //Journal Template files in a resource importer directory structure is detected in a path like "/journal/templates/StructureName/TemplateName" or "/journal/templates/TemplateName"
        if (psiFile != null) {
            if (psiFile.getParent() != null) {
                VirtualFile parent = psiFile.getVirtualFile().getParent();
                //Template with a parent folder for the Structure
                if (! (TEMPLATES.equals(parent.getName())) ) {
                    parent = parent.getParent();
                }
                if (parent != null) {
                    String templatesFolderName = parent.getName();
                    if (TEMPLATES.equals(templatesFolderName)) {
                        VirtualFile grandGrandParent = parent.getParent();
                        if (grandGrandParent != null) {
                            String journalFolderName = grandGrandParent.getName();
							return JOURNAL.equals(journalFolderName);
                        }
                    }
                }
            }

            if (psiFile.getVirtualFile() != null) {
                VirtualFile virtualFile = psiFile.getVirtualFile();

                return SiteInitializerUtil.isSiteInitializerFile(virtualFile) && (LiferayFileUtil.getParent(virtualFile, "ddm-templates") != null) && (StringUtil.equals(virtualFile.getName(), "ddm-template.ftl"));
            }
        }
        return false;
    }

    public static boolean isJournalStructureFile(PsiFile psiFile) {
        //Journal Structure files in a resource importer directory structure is detected in a path like "/journal/structures/Structure.xml" or "/journal/structures/Structure.json"
        if ( (psiFile instanceof XmlFile) || (psiFile instanceof JsonFile) ) {
            if (psiFile.getParent() != null) {
                VirtualFile parent = psiFile.getVirtualFile().getParent();
                if (parent != null) {
                    String structureFolderName = parent.getName();
                    if (STRUCTURES.equals(structureFolderName)) {
                        VirtualFile grandGrandParent = parent.getParent();
                        if (grandGrandParent != null) {
                            String journalFolderName = grandGrandParent.getName();
							return JOURNAL.equals(journalFolderName);
                        }
                    }
                }
            } else if (psiFile.getVirtualFile() instanceof VirtualFileWindow virtualFileWindow) {
                VirtualFile delegate = virtualFileWindow.getDelegate();

				return (SiteInitializerUtil.isSiteInitializerFile(delegate)) &&
					   (LiferayFileUtil.getParent(delegate, "ddm-structures") != null);
            }
        }

        return false;
    }

    public static boolean isFragmentFile(PsiFile psiFile) {
        if (psiFile.getParent() != null) {
            return isFragmentFile(psiFile.getVirtualFile());
        }

        return false;
    }

    public static boolean isFragmentFile(VirtualFile virtualFile) {
        VirtualFile parent = virtualFile.getParent();
        if ( (parent != null) && (parent.isValid()) ) {
            return getChild(parent, "fragment.json") != null;
        }

        return false;
    }

    public static boolean isFragmentHtmlFile(VirtualFile virtualFile) {
        return
                (virtualFile != null) &&
                (virtualFile.getExtension() != null) &&
                ("html".equalsIgnoreCase(virtualFile.getExtension()) || "htm".equalsIgnoreCase(virtualFile.getExtension())) &&
                isFragmentFile(virtualFile);
    }

    @Nullable
    public static String getJournalStructureJsonFileDefinitionSchemaVersion(PsiFile psiFile) {
        if (psiFile instanceof JsonFile jsonFile) {
			JsonValue topLevelValue = jsonFile.getTopLevelValue();

            if (topLevelValue != null) {
                PsiElement[] children = topLevelValue.getChildren();

                for (PsiElement psiElement : children) {
                    if (psiElement instanceof JsonProperty jsonProperty) {
						if ("definitionSchemaVersion".equals(jsonProperty.getName())) {
                            JsonValue jsonValue = jsonProperty.getValue();

                            if (jsonValue != null) {
                                String definitionSchemaVersion = jsonValue.getText();

                                definitionSchemaVersion = StringUtil.unquoteString(definitionSchemaVersion);

                                return definitionSchemaVersion;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    public static boolean isJournalStructureDataDefinitionSchema(PsiFile psiFile) {
        if (psiFile instanceof JsonFile jsonFile) {
			JsonValue topLevelValue = jsonFile.getTopLevelValue();

            if (topLevelValue != null) {
                PsiElement[] children = topLevelValue.getChildren();

                for (PsiElement psiElement : children) {
                    if (psiElement instanceof JsonProperty jsonProperty) {
						if ("contentType".equals(jsonProperty.getName())) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public static boolean isApplicationDisplayTemplateFile(PsiFile psiFile) {
        //Application Display Template files in a resource importer directory structure is detected in a path like "/templates/application_display/type/TemplateName"
        if (psiFile != null) {
            if (psiFile.getParent() != null) {
                VirtualFile parent = psiFile.getVirtualFile().getParent();
                if (parent.getParent() != null) {
                    VirtualFile grandParent = parent.getParent();
                    String grandParentName = grandParent.getName();
                    if (APPLICATION_DISPLAY.equals(grandParentName)) {
                        VirtualFile grandGrandParent = grandParent.getParent();
                        if (grandGrandParent != null) {
                            String grandGrandParentName = grandGrandParent.getName();
							return TEMPLATES.equals(grandGrandParentName);
                        }
                    }

                }
            }
        }
        return false;
    }

    public static String getApplicationDisplayTemplateType(PsiFile psiFile) {
        if (psiFile != null) {
            if (psiFile.getParent() != null) {
                VirtualFile parent = psiFile.getVirtualFile().getParent();
                return parent.getName();
            }
        }
        return null;
    }


    /**
     * Check if the given file is a theme template file.
     * The file is a theme template file if it is in the directory hierarchy of the template-path folder which has been defined in liferay-look-and-feel.xml
     *
     * @param psiFile The file to be checked
     * @return true if the given file is a template file
     */
    public static boolean isThemeTemplateFile(PsiFile psiFile) {
        final Module module = ModuleUtil.findModuleForPsiElement(psiFile);
        if ( (module != null) && (psiFile.getVirtualFile() != null) ) {
            LiferayModuleComponent component = module.getService(LiferayModuleComponent.class);
            if (component != null) {
                String templatesPath = component.getThemeSettings().get(LiferayLookAndFeelXmlParser.TEMPLATES_PATH);
                if ((templatesPath != null) && (!templatesPath.trim().isEmpty())) {
                    VirtualFile webRootFile = getFileInWebRoot(module, templatesPath);
                    VirtualFile sourceRootFile = getFileInSourceRoot(module, templatesPath);
                    return
                            (
                                    ((webRootFile != null) && (isParent(psiFile.getVirtualFile(), webRootFile))) ||
                                            ((sourceRootFile != null) && (isParent(psiFile.getVirtualFile(), sourceRootFile)))
                            );
                }
            }
        }

        return false;
    }

    /**
     * Check if given file is a layout template file.
     * A layout template file is detected by the extension ".tpl" or if the file is present in a subdirectory of "layouttpl"
     *
     * @param psiFile The file to be checked
     * @return true if the given file is a layout template file
     */
    public static boolean isLayoutTemplateFile(PsiFile psiFile) {
        VirtualFile virtualFile = psiFile.getVirtualFile();

        if (virtualFile != null) {
            if ("tpl".equals(virtualFile.getExtension())) {
                return true;
            }

            VirtualFile layouttplVirtualFile = getParent(virtualFile, LAYOUTTPL);
			return layouttplVirtualFile != null;
        }

        return false;
    }

    public static boolean isParent(@NotNull VirtualFile child, @NotNull VirtualFile parent) {
        VirtualFile ancestor = child;
        while ( ( ancestor = ancestor.getParent()) != null) {
            if (ancestor.equals(parent)) {
                return true;
            }
        }

        return false;
    }

    public static VirtualFile getJournalStructureFile(PsiFile journalTemplateFile) {
        //TODO: add site initializer folder structure
        if (isJournalTemplateFile(journalTemplateFile)) {
            VirtualFile virtualFile = journalTemplateFile.getVirtualFile();

            if (SiteInitializerUtil.isSiteInitializerFile(virtualFile)) {
                VirtualFile templateJSONFile = LiferayFileUtil.getChild(virtualFile.getParent(), "ddm-template.json");

                Project project = journalTemplateFile.getProject();

                String ddmStructureKey = SiteInitializerUtil.getDDMTemplateStructureKey(project, templateJSONFile);

                VirtualFile siteInitializerDirectory = LiferayFileUtil.getParent(virtualFile, "site-initializer");

                if (siteInitializerDirectory != null) {
                    return SiteInitializerUtil.getDDMStructureFile(project, ddmStructureKey, siteInitializerDirectory);
                }
            } else {
                VirtualFile parent = virtualFile.getParent();

                String templateName = virtualFile.getNameWithoutExtension();

                //Structure file in same directory
                VirtualFile structureFile = getChild(parent, templateName + ".xml");
                if (structureFile != null) {
                    return structureFile;
                }
                structureFile = getChild(parent, templateName + ".json");
                if (structureFile != null) {
                    return structureFile;
                }

                //Template with a parent folder for the Structure
                if (!(TEMPLATES.equals(parent.getName()))) {
                    templateName = parent.getName();

                    parent = parent.getParent();
                }
                VirtualFile grandParent = parent.getParent();
                VirtualFile structures = getChild(grandParent, STRUCTURES);
                if (structures != null) {
                    VirtualFile result = getChild(structures, templateName + ".xml");
                    if (result == null) {
                        result = getChild(structures, templateName + ".json"); //JSON-Format for Liferay 7
                    }
                    return result;
                }
            }
        }
        return null;
    }

    public static VirtualFile getFileInSourceRoot(Module module, String path) {
        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        for (VirtualFile sourceRoot : moduleRootManager.getSourceRoots()) {
            VirtualFile pathFile = sourceRoot.findFileByRelativePath(path);
            if (pathFile != null) {
                return pathFile;
            }
        }

        return null;
    }

    public static VirtualFile getFileInContentRoot(Module module, String path) {
        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);

        for (VirtualFile sourceRoot : moduleRootManager.getContentRoots()) {
            VirtualFile pathFile = sourceRoot.findFileByRelativePath(path);
            if (pathFile != null) {
                return pathFile;
            }

            if (StringUtil.equals(sourceRoot.getName(), "main")) {
                VirtualFile parent = sourceRoot.getParent();

                if (parent != null && StringUtil.equals(parent.getName(), "src")) {
                    VirtualFile grandParent = parent.getParent();

                    if (grandParent != null) {
                        pathFile = grandParent.findFileByRelativePath(path);

                        if (pathFile != null) {
                            return pathFile;
                        }
                    }
                }
            }
        }

        return null;
    }

    public static VirtualFile getFileInWebRoot(Module module, String path) {
        Collection<WebFacet> webFacets = WebFacet.getInstances(module);
        for (WebFacet webFacet : webFacets) {
            //Iterate all WebFacets and examine their Web Root directories
            List<WebRoot> webRoots = webFacet.getWebRoots();
            for (WebRoot webRoot : webRoots) {
                if ("/".equals(webRoot.getRelativePath())) {
                    if (webRoot.getFile() != null) {
                        VirtualFile pathFile = webRoot.getFile().findFileByRelativePath(path);
                        if (pathFile != null) {
                            return pathFile;
                        }
                    }
                }
            }
        }

        return null;
    }

    public static Collection<String> getWebRootsRelativePaths(Module module, VirtualFile virtualFile) {
        Collection<String> result = new ArrayList<>();

        Collection<WebFacet> webFacets = WebFacet.getInstances(module);
        for (WebFacet webFacet : webFacets) {
            List<WebRoot> webRoots = webFacet.getWebRoots();
            for (WebRoot webRoot : webRoots) {
                VirtualFile webRootFile = webRoot.getFile();
                if ( (webRootFile != null) && (webRootFile.isValid()) ) {
                    String relativePath = VfsUtilCore.getRelativePath(virtualFile, webRootFile);
                    if (relativePath != null) {
                        result.add(relativePath);
                    }
                }
            }
        }

        return result;
    }

    public static Collection<String> getContentRootRelativePaths(Module module, VirtualFile virtualFile) {
        Collection<String> result = new ArrayList<>();

        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        for (VirtualFile sourceRoot : moduleRootManager.getContentRoots()) {
            String relativePath = VfsUtilCore.getRelativePath(virtualFile, sourceRoot);

            if (relativePath != null) {
                result.add(relativePath);
            }
        }

        return result;
    }

    public static Collection<String> getSourceRootRelativePaths(Module module, VirtualFile virtualFile) {
        Collection<String> result = new ArrayList<>();

        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        for (VirtualFile sourceRoot : moduleRootManager.getSourceRoots()) {
            String relativePath = VfsUtilCore.getRelativePath(virtualFile, sourceRoot);

            if (relativePath != null) {
                result.add(relativePath);
            }
        }

        return result;
    }

    public static VirtualFile getChild(VirtualFile parent, String name) {
        int index = name.indexOf('/');

        String pathElement = name;

        if (index > -1) {
            pathElement = name.substring(0, index);
        }

        if (parent != null) {
            for (VirtualFile virtualFile : parent.getChildren()) {
                if (pathElement.equals(virtualFile.getName())) {
                    if (index == -1) {
                        return virtualFile;
                    } else {
                        return getChild(virtualFile, name.substring(index+1));
                    }
                }
            }
        }

        return null;
    }

    public static VirtualFile getParent(VirtualFile file, String name) {
        if (file != null) {
            VirtualFile parent = file.getParent();
            if (parent != null) {
                if (name.equals(parent.getName())) {
                    return parent;
                }
            }
            return getParent(parent, name);
        }

        return null;
    }

    public static PsiFileSystemItem getParent(PsiFileSystemItem file, String name) {
        if (file != null) {
            PsiFileSystemItem parent = file.getParent();
            if (parent != null) {
                if (name.equals(parent.getName())) {
                    return parent;
                }
            }
            return getParent(parent, name);
        }

        return null;
    }

    public static PsiFileSystemItem getChild(PsiFileSystemItem parent, String name) {
        int index = name.indexOf('/');

        String pathElement = name;

        if (index > -1) {
            pathElement = name.substring(0, index);
        }

        if (parent != null) {
            for (PsiElement psiElement : parent.getChildren()) {
                if (psiElement instanceof PsiFileSystemItem psiFileSystemItem) {
					if (pathElement.equals(psiFileSystemItem.getName())) {
                        if (index == -1) {
                            return psiFileSystemItem;
                        } else {
                            return getChild(psiFileSystemItem, name.substring(index + 1));
                        }
                    }
                }
            }
        }

        return null;
    }

    public static VirtualFile getThemeSettingsDirectory(Module module, String themeSetting) {
        String themeSettingValue = LiferayModuleComponent.getThemeSetting(module, themeSetting);
        if ((themeSettingValue != null) && (!themeSettingValue.trim().isEmpty())) {
            VirtualFile themeSettingsFile = LiferayFileUtil.getFileInSourceRoot(module, themeSettingValue);
            if (themeSettingsFile == null) {
                themeSettingsFile = LiferayFileUtil.getFileInWebRoot(module, themeSettingValue);
            }

            return themeSettingsFile;
        }
        return null;
    }

    public static void addLibraryRoot(final Collection<PsiFileSystemItem> result, final FileReferenceHelper fileReferenceHelper, final Module module, final String libraryName, final String path) {

        ModuleRootManager.getInstance(module).orderEntries().forEachLibrary(
            library -> {

                if (library.getName() != null && library.getName().contains(libraryName)) {
                    VirtualFile[] files = library.getFiles(OrderRootType.CLASSES);
                    for (VirtualFile file : files) {

                        VirtualFile root = getJarRoot(file);

                        if (root != null) {
                            if ((path == null) || (path.trim().isEmpty())) {
                                result.add(fileReferenceHelper.getPsiFileSystemItem(module.getProject(), root));
                            } else {
                                VirtualFile child = getChild(root, path);
                                if (child != null) {
                                    result.add(fileReferenceHelper.getPsiFileSystemItem(module.getProject(), child));
                                }
                            }
                        }
                    }
                }

                return true;
            }
        );

    }

    public static VirtualFile getJarRoot(VirtualFile file) {
        VirtualFile root;

        VirtualFileSystem virtualFileSystem = file.getFileSystem();

        if (virtualFileSystem instanceof JarFileSystem jarFileSystem) {
			root = jarFileSystem.getRootByEntry(file);
        } else {
            root = JarFileSystem.getInstance().getJarRootForLocalFile(file);
        }

        return root;
    }

    public static String getCustomJspDir(Module module) {
        String liferayHookXml = LiferayModuleComponent.getLiferayHookXml(module);
        if ( (liferayHookXml != null) && (!liferayHookXml.trim().isEmpty()) ) {
            VirtualFile virtualFile = VfsUtilCore.findRelativeFile(liferayHookXml, null);

            if (virtualFile != null) {
                XmlFile xmlFile = (XmlFile) PsiManager.getInstance(module.getProject()).findFile(virtualFile);
                if ((xmlFile != null) && (xmlFile.isValid())) {
                    XmlTag rootTag = xmlFile.getRootTag();

                    if (rootTag != null) {
                        if ("hook".equals(rootTag.getLocalName())) {
                            XmlTag customJspDirTag = rootTag.findFirstSubTag("custom-jsp-dir");
                            if (customJspDirTag != null) {
                                return customJspDirTag.getValue().getTrimmedText();
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    public static String getJSSafeName(String name) {
        if (name == null) {
            return null;
        }

        StringBuilder sb = null;

        int index = 0;

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);

            if (
                    (c == '-') ||
                    (c == '.') ||
                    (c == ' ')
            ) {
                if (sb == null) {
                    sb = new StringBuilder(name.length() - 1);

                    sb.append(name, index, i);
                }
            } else if (sb != null) {
                sb.append(c);
            }
        }

        if (sb == null) {
            return name;
        } else {
            return sb.toString();
        }
    }

    public static String getPortletId(String portletName) {
        //from PortletTracker.addingService()
        String portletId = StringUtil.replace(portletName, Arrays.asList(".", "$"), Arrays.asList("_", "_"));

        portletId = getJSSafeName(portletId);

        return portletId;
    }

    public static String getWebContextPath(Module module, String defaultValue) {
        VirtualFile bndVirtualFile = getFileInContentRoot(module, "bnd.bnd");

        if (bndVirtualFile != null) {
            CharSequence text = LoadTextUtil.loadText(bndVirtualFile);

            Pattern webContextPathPattern = Pattern.compile("Web-ContextPath:( *)\\/([\\w.-]+)");
            Matcher webContextPathMatcher = webContextPathPattern.matcher(text);

            if (webContextPathMatcher.find()) {
                String webContextPath = webContextPathMatcher.group(2);

                if (webContextPath.startsWith("/")) {
                    webContextPath = webContextPath.substring(1);
                }

                return webContextPath;
            }
        }

        return defaultValue;
    }

    public static boolean isWebResourcesFile(PsiFile psiFile) {
        final Module module = ModuleUtil.findModuleForPsiElement(psiFile);

        if ( (module != null) && (psiFile.getVirtualFile() != null) ) {
            VirtualFile virtualFile = psiFile.getVirtualFile();

            Collection<WebFacet> webFacets = WebFacet.getInstances(module);

            if (! webFacets.isEmpty()) {
                for (WebFacet webFacet : webFacets) {
                    List<WebRoot> webRoots = webFacet.getWebRoots();

                    for (WebRoot webRoot : webRoots) {
                        if ("/".equals(webRoot.getRelativePath())) {
                            VirtualFile webRootFile = webRoot.getFile();

                            if ( (webRootFile != null) && (webRootFile.isValid()) ) {
                                String relativePath = VfsUtilCore.getRelativePath(virtualFile, webRootFile);

                                return (relativePath != null);
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public static VirtualFile getWebContextForFile(PsiFile psiFile) {
        final Module module = ModuleUtil.findModuleForPsiElement(psiFile);

        if ( (module != null) && (psiFile.getVirtualFile() != null) ) {
            VirtualFile virtualFile = psiFile.getVirtualFile();

            Collection<WebFacet> webFacets = WebFacet.getInstances(module);

            if (! webFacets.isEmpty()) {
                for (WebFacet webFacet : webFacets) {
                    List<WebRoot> webRoots = webFacet.getWebRoots();

                    for (WebRoot webRoot : webRoots) {
                        if ("/".equals(webRoot.getRelativePath())) {
                            VirtualFile webRootFile = webRoot.getFile();

                            if ( (webRootFile != null) && (webRootFile.isValid()) ) {
                                String relativePath = VfsUtilCore.getRelativePath(virtualFile, webRootFile);

                                if (relativePath != null) {
                                    return webRootFile;
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
