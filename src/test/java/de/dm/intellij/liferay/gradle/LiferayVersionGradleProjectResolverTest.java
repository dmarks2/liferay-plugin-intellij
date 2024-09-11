package de.dm.intellij.liferay.gradle;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import org.gradle.util.GradleVersion;

import java.io.IOException;

public class LiferayVersionGradleProjectResolverTest extends AbstractGradleImportingTestCase {
	@Override
	protected GradleVersion getCurrentGradleVersion() {
		return GradleVersion.version("8.5");
	}

	public void testGradleVersionFromLiferayWorkspaceProduct() throws IOException {
		createProjectSubFile("build.gradle", "apply plugin: 'java'");

		createProjectSubFile("gradle.properties", "liferay.workspace.product=portal-7.4-ga120\n");

		createProjectSubDir("src/main/java");

		createProjectSubFile("src/main/java/Sample.java", "public class Sample {}");

		importProject();

		Module module = ModuleManager.getInstance(myProject).getModules()[0];

		String liferayVersion = LiferayModuleComponent.getLiferayVersion(module);

		//TODO how to test? module not created in test...

		//assertEquals("7.4.3.120", liferayVersion);
	}
}
