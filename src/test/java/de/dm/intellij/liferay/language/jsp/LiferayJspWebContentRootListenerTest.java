package de.dm.intellij.liferay.language.jsp;

import com.intellij.javaee.web.WebRoot;
import com.intellij.javaee.web.facet.WebFacet;
import com.intellij.psi.PsiDirectory;
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

		assertFalse(webFacets.isEmpty());

        WebFacet webFacet = webFacets.iterator().next();

		assertFalse(webFacet.getWebRoots().isEmpty());

        WebRoot webRoot = webFacet.getWebRoots().iterator().next();

        assertEquals("/", webRoot.getRelativePath());
        PsiDirectory parent = myFixture.getFile().getParent();

        assertNotNull(parent);

        assertEquals(parent.getVirtualFile(), webRoot.getFile());
    }

}
