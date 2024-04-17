package de.dm.intellij.liferay.maven.importer;

import com.intellij.maven.testFramework.MavenImportingTestCase;
import com.intellij.openapi.module.Module;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import org.intellij.lang.annotations.Language;

public class LiferayVersionMavenImporterWorkspaceBomTest extends MavenImportingTestCase {

    private Module module;

    @Language(value = "XML", prefix = "<project>", suffix = "</project>")
	private static final String XML = """
			    <groupId>de.dm.liferay</groupId>
			    <artifactId>workspace-bom</artifactId>
			    <version>1.0.0-SNAPSHOT</version>
			    <packaging>pom</packaging>

			    <dependencyManagement>
			        <dependencies>
			            <dependency>
			                <groupId>com.liferay.portal</groupId>
			                <artifactId>release.portal.bom</artifactId>
			                <version>7.3.0</version>
			                <type>pom</type>
			                <scope>import</scope>
			            </dependency>
					  </dependencies>
				 </dependencyManagement>
			""";

    @Override
    protected void setUp() throws Exception {
        super.setUp();

		createProjectPom(XML);

		importProject();

		module = getTestFixture().getModule();
    }

    public void testMavenImporterWorkspaceBom() {
        String liferayVersion = LiferayModuleComponent.getLiferayVersion(module);

        assertEquals("7.3.0", liferayVersion);
    }


}
