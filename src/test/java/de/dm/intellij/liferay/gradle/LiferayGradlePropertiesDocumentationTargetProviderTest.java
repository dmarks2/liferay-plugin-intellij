package de.dm.intellij.liferay.gradle;

import com.intellij.lang.documentation.ide.IdeDocumentationTargetProvider;
import com.intellij.platform.backend.documentation.DocumentationTarget;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayGradlePropertiesDocumentationTargetProviderTest extends BasePlatformTestCase {

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/gradle/LiferayGradlePropertiesDocumentationTargetProviderTest";
	}

	public void testPortalPropertiesDocumentationProvider() {
		myFixture.configureByFiles("gradle.properties");

		IdeDocumentationTargetProvider documentationTargetProvider = IdeDocumentationTargetProvider.getInstance(getProject());

		List<? extends @NotNull DocumentationTarget> documentationTargets = documentationTargetProvider.documentationTargets(myFixture.getEditor(), myFixture.getFile(), myFixture.getEditor().getCaretModel().getOffset());

		assertTrue(documentationTargets.size() > 0);

		DocumentationTarget target = documentationTargets.get(0);

		assertEquals("Should provide proper documentation for liferay.workspace.default.repository.enabled property", "<div class='definition'><pre>liferay.workspace.default.repository.enabled</pre></div><div class='content'>Set this to true to configure Liferay CDN as the default repository in the root project. The default value is <code>true</code>.</div>", target.computeDocumentationHint());
	}

}
