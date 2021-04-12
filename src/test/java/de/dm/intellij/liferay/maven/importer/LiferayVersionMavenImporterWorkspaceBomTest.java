package de.dm.intellij.liferay.maven.importer;

import com.intellij.openapi.module.Module;
import de.dm.intellij.liferay.module.LiferayModuleComponent;

public class LiferayVersionMavenImporterWorkspaceBomTest extends MavenImportingTestCase {

    private Module module;

    private static final String XML = "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
            "    <modelVersion>4.0.0</modelVersion>\n" +
            "\n" +
            "    <groupId>de.dm.liferay</groupId>\n" +
            "    <artifactId>workspace-bom</artifactId>\n" +
            "    <version>1.0.0-SNAPSHOT</version>\n" +
            "    <packaging>pom</packaging>\n" +
            "\n" +
            "    <dependencyManagement>\n" +
            "        <dependencies>\n" +
            "            <dependency>\n" +
            "                <groupId>com.liferay.portal</groupId>\n" +
            "                <artifactId>release.portal.bom</artifactId>\n" +
            "                <version>7.3.0</version>\n" +
            "                <type>pom</type>\n" +
            "                <scope>import</scope>\n" +
            "            </dependency>" +
            "       </dependencies>" +
            "   </dependencyManagement>" +
            "</project>\n";

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        module = super.createMavenModule("workspace-bom", XML);
    }

    public void testMavenImporterWorkspaceBom() {
        String liferayVersion = LiferayModuleComponent.getLiferayVersion(module);

        assertEquals("7.3.0", liferayVersion);
    }


}