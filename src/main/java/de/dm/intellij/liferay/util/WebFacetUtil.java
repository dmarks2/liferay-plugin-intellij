package de.dm.intellij.liferay.util;

import com.intellij.facet.impl.FacetUtil;
import com.intellij.javaee.web.WebRoot;
import com.intellij.javaee.web.facet.WebFacet;
import com.intellij.javaee.web.facet.WebFacetType;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import de.dm.intellij.liferay.language.freemarker.runner.FreemarkerAttachBreakpointHandler;

import java.util.Collection;
import java.util.List;

public class WebFacetUtil {

    private final static Logger log = Logger.getInstance(WebFacetUtil.class);

    private static final String LIFERAY_RESOURCES_WEB_FACET = "LiferayResourcesWeb";

    public static void addWebFacet(VirtualFile resources, VirtualFile parent, Module module) {
        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);

        //Add web facet only if at <source-root>/<dir>
        for (VirtualFile sourceRoot : moduleRootManager.getSourceRoots()) {
            if (sourceRoot.equals(parent)) {
                boolean facetPresent = false;
                Collection<WebFacet> webFacets = WebFacet.getInstances(module);

                outer:
                for (WebFacet webFacet : webFacets) {
                    List<WebRoot> webRoots = webFacet.getWebRoots();
                    for (WebRoot webRoot : webRoots) {
                        if ( (webRoot.getFile() != null) && (webRoot.getFile().equals(resources)) ) {
                            facetPresent = true;
                            break outer;
                        }
                    }
                }

                if (!facetPresent) {
                    if (log.isDebugEnabled()) {
                        log.debug("Adding " + resources.getPath() + " as " + LIFERAY_RESOURCES_WEB_FACET + " facet");
                    }

                    final WebFacet webFacet = FacetUtil.addFacet(module, WebFacetType.getInstance(), LIFERAY_RESOURCES_WEB_FACET);
                    webFacet.addWebRoot(resources, "/");
                }

                break;
            }
        }
    }
}
