package de.dm.intellij.liferay.maven.catalogs;

import de.dm.intellij.maven.archetypes.plugin.ArchetypeCatalogDefinition;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LiferayMavenArchetypesCatalogDefinition extends ArchetypeCatalogDefinition {

    public Set<String> getArchetypeCatalogURLs() {
        //adds the Maven Archetype Catalog for Liferay Maven Archetypes
        return new HashSet<String>(Arrays.asList("https://repository.liferay.com/nexus/content/repositories/liferay-public-releases/archetype-catalog.xml"));
    }

}
