package de.dm.intellij.liferay.language.osgi.run;

import com.intellij.codeInsight.daemon.GutterMark;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GogoShellLineMarkerProviderTest extends LightJavaCodeInsightFixtureTestCase {

	private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/osgi/run/GogoShellLineMarkerProviderTest";

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@NotNull
	@Override
	protected LightProjectDescriptor getProjectDescriptor() {
		return new LightProjectDescriptorBuilder()
				.library("OSGi", TEST_DATA_PATH, "osgi.jar")
				.build();
	}
	@Override
	protected String getTestDataPath() {
		return TEST_DATA_PATH;
	}


	public void testLineMarkerForValidGogoCommand() {
		myFixture.configureByFile("MyOsgiCommand.java");

		boolean markerFound = false;

		List<GutterMark> allMarkers = myFixture.findAllGutters();
		for (GutterMark gutterMark : allMarkers) {
			if (gutterMark instanceof LineMarkerInfo.LineMarkerGutterIconRenderer<?> lineMarkerGutterIconRenderer) {

				LineMarkerInfo<?> lineMarkerInfo = lineMarkerGutterIconRenderer.getLineMarkerInfo();

				if (StringUtil.contains(lineMarkerInfo.getLineMarkerTooltip(), "foo:bar")) {
					markerFound = true;

					break;
				}
			}
		}

		assertTrue("Line marker should be created for valid gogo command", markerFound);
	}
}
