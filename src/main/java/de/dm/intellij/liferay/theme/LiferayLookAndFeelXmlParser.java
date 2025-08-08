package de.dm.intellij.liferay.theme;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.externalSystem.service.project.autoimport.FileChangeListenerBase;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.NanoXmlUtil;
import de.dm.intellij.liferay.module.LiferayModuleComponent;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Class to parse liferay-look-and-feel.xml (if present) and to save information into LiferayModuleComponent
 */
public class LiferayLookAndFeelXmlParser extends FileChangeListenerBase  {

    private final static Logger log = Logger.getInstance(LiferayLookAndFeelXmlParser.class);

    public static final String TEMPLATE_EXTENSION = "template-extension";
    public static final String ROOT_PATH = "root-path";
    public static final String CSS_PATH = "css-path";
    public static final String IMAGES_PATH = "images-path";
    public static final String JAVASCRIPT_PATH = "javascript-path";
    public static final String TEMPLATES_PATH = "templates-path";

    public static void handleChange(Project project, VirtualFile virtualFile) {
        final Module module = ModuleUtil.findModuleForFile(virtualFile, project);
        if (module != null) {
            LiferayModuleComponent component = module.getService(LiferayModuleComponent.class);
            if (component != null) {
                component.setLiferayLookAndFeelXml(virtualFile.getUrl());

                if (virtualFile.exists() && virtualFile.getLength() > 0) {
                    try {
                        //set defaults
                        component.getThemeSettings().put(TEMPLATE_EXTENSION, "ftl");
                        component.getThemeSettings().put(ROOT_PATH, "/");
                        component.getThemeSettings().put(CSS_PATH, "/css");
                        component.getThemeSettings().put(IMAGES_PATH, "/images");
                        component.getThemeSettings().put(JAVASCRIPT_PATH, "/js");
                        component.getThemeSettings().put(TEMPLATES_PATH, "/templates");

                        NanoXmlUtil.parse(virtualFile.getInputStream(), new NanoXmlUtil.BaseXmlBuilder() {
                            @Override
                            public void addPCData(Reader reader, String systemID, int lineNr) throws Exception {
                                String location = getLocation();

                                switch (location) {
                                    case ".look-and-feel.theme." + ROOT_PATH:
                                        component.getThemeSettings().put(ROOT_PATH, readText(reader).trim());
                                        break;
                                    case ".look-and-feel.theme." + TEMPLATE_EXTENSION:
                                        component.getThemeSettings().put(TEMPLATE_EXTENSION, readText(reader).trim());
                                        break;
                                    case ".look-and-feel.theme." + CSS_PATH:
                                        component.getThemeSettings().put(CSS_PATH, readText(reader).trim());
                                        break;
                                    case ".look-and-feel.theme." + IMAGES_PATH:
                                        component.getThemeSettings().put(IMAGES_PATH, readText(reader).trim());
                                        break;
                                    case ".look-and-feel.theme." + JAVASCRIPT_PATH:
                                        component.getThemeSettings().put(JAVASCRIPT_PATH, readText(reader).trim());
                                        break;
                                    case ".look-and-feel.theme." + TEMPLATES_PATH:
                                        component.getThemeSettings().put(TEMPLATES_PATH, readText(reader).trim());
                                        break;
                                }
                            }
                        });
                    } catch (IOException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }
    }

    public static Collection<Setting> parseSettings(XmlFile xmlFile) {
        Collection<Setting> result = new ArrayList<>();

        if (xmlFile != null) {
            if (xmlFile.isValid()) {
                XmlTag rootTag = xmlFile.getRootTag();

                if (rootTag != null) {
                    if ("look-and-feel".equals(rootTag.getLocalName())) {
                        XmlTag themeTag = rootTag.findFirstSubTag("theme");
                        if (themeTag != null) {
                            XmlTag settingsTag = themeTag.findFirstSubTag("settings");
                            if (settingsTag != null) {
                                for (XmlTag xmlTag : settingsTag.findSubTags("setting")) {
                                    String key = xmlTag.getAttributeValue("key");
                                    String type = xmlTag.getAttributeValue("type");

                                    result.add(new Setting(xmlTag, key, type));
                                    //checkbox=boolean, other=string
                                }
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    public static boolean isRelevantFile(String path) {
        return path.endsWith("liferay-look-and-feel.xml");
    }

    @Override
    protected boolean isRelevant(String path) {
        return isRelevantFile(path);
    }

    @Override
    protected void updateFile(VirtualFile virtualFile, VFileEvent vFileEvent) {
        Project project = ProjectUtil.guessProjectForFile(virtualFile);
        if (project != null) {
            handleChange(project, virtualFile);
        }
    }

    @Override
    protected void deleteFile(VirtualFile virtualFile, VFileEvent vFileEvent) {
    }

    @Override
    protected void apply() {
    }

    public static class Setting {
        public PsiElement psiElement;
        public String key;
        public String type;

        public Setting(PsiElement psiElement, String key, String type) {
            this.psiElement = psiElement;
            this.key = key;
            this.type = type;
        }
    }
}
