package de.dm.intellij.liferay.maven.importer;

import com.intellij.maven.testFramework.MavenImportingTestCase;
import com.intellij.openapi.module.Module;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import org.intellij.lang.annotations.Language;

public class LiferayVersionMavenImporterVersionPropertyTest extends MavenImportingTestCase {

    private Module module;

    @Language(value = "XML", prefix = "<project>", suffix = "</project>")
	private static final String XML = """
			<groupId>de.dm.liferay</groupId>
			<artifactId>version-property</artifactId>
			<version>1.0.0-SNAPSHOT</version>
			<packaging>pom</packaging>

			<properties>
				<liferay.version>7.3.0</liferay.version>
			</properties>
		""";

    @Override
    protected void setUp() throws Exception {
        super.setUp();

		createProjectPom(XML);

		importProject();

        module = myTestFixture.getModule();
    }



    public void testMavenImporterVersionProperty() {
        String liferayVersion = LiferayModuleComponent.getLiferayVersion(module);

        assertEquals("7.3.0", liferayVersion);
    }


}
