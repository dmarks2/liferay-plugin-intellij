package de.dm.intellij.liferay.language.jsp;

import com.intellij.javaee.web.WebRoot;
import com.intellij.javaee.web.facet.WebFacet;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.Collection;

public class LiferayJspWebContentRootListenerTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/jsp/LiferayJspWebContentRootListenerTest";
    }

    public void testWebContentRootListener() {
        myFixture.configureByFiles("META-INF/resources/init.jsp");

        Collection<WebFacet> webFacets = WebFacet.getInstances(myFixture.getModule());

        assertTrue(webFacets.size() > 0);

        WebFacet webFacet = webFacets.iterator().next();

        assertTrue(webFacet.getWebRoots().size() > 0);

        WebRoot webRoot = webFacet.getWebRoots().iterator().next();

        assertEquals("/", webRoot.getRelativePath());
        assertEquals(myFixture.getFile().getParent().getVirtualFile(), webRoot.getFile());
    }

}
