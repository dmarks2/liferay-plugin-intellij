package de.dm.intellij.liferay.language.osgi.config;

import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class LiferayConfigParserTest extends BasePlatformTestCase {

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/language/osgi/config/LiferayConfigParserTest";
	}

	public void testValidOsgiLiferayConfigFile() {
		myFixture.configureByFiles("osgi/configs/com.liferay.example.MyOsgiConfig.config");

		FileType fileType = myFixture.getFile().getFileType();

		assertTrue("osgi/configs/com.liferay.example.MyOsgiConfig.config should be detected as Liferay Config File, but is " + fileType, fileType instanceof LiferayConfigFileType);

		List<HighlightInfo> highlightInfos = myFixture.doHighlighting();

		assertTrue("osgi/configs/com.liferay.example.MyOsgiConfig.config is a valid file, so highlighting should not give any errors", highlightInfos.isEmpty());
	}

	public void testInvalidOsgiLiferayConfigFile() {
		myFixture.configureByFiles("osgi/configs/com.liferay.example.Invalid.config");

		FileType fileType = myFixture.getFile().getFileType();

		assertTrue("osgi/configs/com.liferay.example.Invalid.config should be detected as Liferay Config File, but is " + fileType, fileType instanceof LiferayConfigFileType);

		List<HighlightInfo> highlightInfos = myFixture.doHighlighting();

		assertFalse("osgi/configs/com.liferay.example.Invalid.config is an invalid file", highlightInfos.isEmpty());
	}

	public void testInvalidMissingBracketLiferayConfigFile() {
		myFixture.configureByFiles("osgi/configs/com.liferay.example.MissingBracket.config");

		List<HighlightInfo> highlightInfos = myFixture.doHighlighting();

		assertFalse("osgi/configs/com.liferay.example.MissingBracket.config is invalid, because of a missing closing bracket", highlightInfos.isEmpty());
	}


	public void testValidBndLiferayConfigFile() {
		myFixture.configureByFiles("custom/com.liferay.example.MyCustomConfig.config", "bnd.bnd");

		FileType fileType = myFixture.getFile().getFileType();

		assertTrue("custom/com.liferay.example.MyCustomConfig.config should be detected as Liferay Config File, but is " + fileType, fileType instanceof LiferayConfigFileType);
	}

	public void testOtherConfigFile() {
		myFixture.configureByFiles("other.config");

		FileType fileType = myFixture.getFile().getFileType();

		assertFalse("other.config should NOT be detected as Liferay Config File", fileType instanceof LiferayConfigFileType);
	}


}
