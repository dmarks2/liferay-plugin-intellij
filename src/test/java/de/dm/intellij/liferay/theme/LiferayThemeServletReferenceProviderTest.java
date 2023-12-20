package de.dm.intellij.liferay.theme;

import com.intellij.facet.impl.FacetUtil;
import com.intellij.javaee.web.facet.WebFacet;
import com.intellij.javaee.web.facet.WebFacetType;
import com.intellij.openapi.module.Module;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class LiferayThemeServletReferenceProviderTest extends BasePlatformTestCase {
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		Module module = myFixture.getModule();

		WebFacet webFacet = FacetUtil.addFacet(module, WebFacetType.getInstance(), "Web");

		webFacet.addWebRoot(myFixture.getTempDirPath() + "/src", "/");
	}

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/theme/LiferayThemeServletReferenceProviderTest";
	}

	public void testResolveThemeServletPath() {
		myFixture.configureByFiles("src/test.html", "gulpfile.js");
	}
}
