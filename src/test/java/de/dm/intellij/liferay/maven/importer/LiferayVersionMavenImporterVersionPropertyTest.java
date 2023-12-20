package de.dm.intellij.liferay.maven.importer;

import com.intellij.openapi.module.Module;
import de.dm.intellij.liferay.module.LiferayModuleComponent;

public class LiferayVersionMavenImporterVersionPropertyTest extends MavenImportingTestCase {

    private Module module;

    private static final String XML = """
			<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
			    <modelVersion>4.0.0</modelVersion>

			    <groupId>de.dm.liferay</groupId>
			    <artifactId>version-property</artifactId>
			    <version>1.0.0-SNAPSHOT</version>
			    <packaging>pom</packaging>

			    <properties>
			        <liferay.version>7.3.0</liferay.version>
			    </properties>
			</project>
			""";

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        module = super.createMavenModule("version-property", XML);
    }

    public void testMavenImporterVersionProperty() {
        String liferayVersion = LiferayModuleComponent.getLiferayVersion(module);

        assertEquals("7.3.0", liferayVersion);
    }


}
