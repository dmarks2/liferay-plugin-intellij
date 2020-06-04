package de.dm.intellij.liferay.util;

import com.intellij.facet.impl.FacetUtil;
import com.intellij.javaee.web.WebRoot;
import com.intellij.javaee.web.facet.WebFacet;
import com.intellij.javaee.web.facet.WebFacetType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;

import java.util.Collection;
import java.util.List;

public class WebFacetUtil {

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
                    final WebFacet webFacet = FacetUtil.addFacet(module, WebFacetType.getInstance());
                    webFacet.addWebRoot(resources, "/");
                }

                break;
            }
        }
    }
}
