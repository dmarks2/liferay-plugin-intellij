package de.dm.intellij.bndtools.parser;

import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

/**
 * @author Dominik Marks
 */
public class BundleVersionParserTest extends BasePlatformTestCase {

	public void testInvalidBundleVersionHighlighting() {
		myFixture.configureByText("bnd.bnd", "Bundle-Version: foo.bar\n");

		List<HighlightInfo> highlightInfos = myFixture.doHighlighting();

		assertFalse(highlightInfos.isEmpty());

		HighlightInfo highlightInfo = highlightInfos.get(0);

		assertEquals(highlightInfo.getDescription(), "invalid version \"foo.bar\": non-numeric \"foo\"");
	}

	public void testValidBundleVersionHighlighting() {
		myFixture.configureByText("bnd.bnd", "Bundle-Version: 1.0.0\n");

		List<HighlightInfo> highlightInfos = myFixture.doHighlighting();

		assertTrue(highlightInfos.isEmpty());
	}

}
