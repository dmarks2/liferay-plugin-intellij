package de.dm.intellij.bndtools.completion;

import java.util.HashMap;
import java.util.Map;

public class BndTypeConstants {

    public static final Map<String, String> BND_TYPES = new HashMap<>();

    static {
        BND_TYPES.put("Bnd-LastModified", "LONG");
        BND_TYPES.put("Bundle-Activator", "CLASS");
        BND_TYPES.put("Bundle-Blueprint", "RESOURCE");
        BND_TYPES.put("Bundle-Category", "STRING");
        BND_TYPES.put("Bundle-Copyright", "STRING");
        BND_TYPES.put("Bundle-Description", "STRING");
        BND_TYPES.put("Bundle-DocURL", "STRING");
        BND_TYPES.put("Bundle-Name", "STRING");
        BND_TYPES.put("Created-By", "STRING");
        BND_TYPES.put("Liferay-Icons-Pack-Name", "STRING");
        BND_TYPES.put("Liferay-Icons-Path", "RESOURCE");
        BND_TYPES.put("Liferay-Releng-App-Description", "STRING");
        BND_TYPES.put("Liferay-Releng-App-Title", "STRING");
        BND_TYPES.put("Meta-Persistence", "RESOURCE");
        BND_TYPES.put("Service-Component", "RESOURCE");
        BND_TYPES.put("Test-Cases", "CLASS");
        BND_TYPES.put("Tool", "STRING");
        BND_TYPES.put("-add-resource", "RESOURCE");
        BND_TYPES.put("-failok", "BOOLEAN");
        BND_TYPES.put("-javaagent", "BOOLEAN");
        BND_TYPES.put("-liferay-aggregate-resource-bundles", "STRING");
        BND_TYPES.put("-manifest-name", "RESOURCE");
        BND_TYPES.put("-nobuildincache", "BOOLEAN");
        BND_TYPES.put("-nobundles", "BOOLEAN");
        BND_TYPES.put("-noclassforname", "BOOLEAN");
        BND_TYPES.put("-nodefaultversion", "BOOLEAN");
        BND_TYPES.put("-noee", "BOOLEAN");
        BND_TYPES.put("-noextraheaders", "BOOLEAN");
        BND_TYPES.put("-noimportjava", "BOOLEAN");
        BND_TYPES.put("-nojunit", "BOOLEAN");
        BND_TYPES.put("-nojunitosgi", "BOOLEAN");
        BND_TYPES.put("-nomanifest", "BOOLEAN");
        BND_TYPES.put("-nouses", "BOOLEAN");
        BND_TYPES.put("-pedantic", "BOOLEAN");
        BND_TYPES.put("-pom", "BOOLEAN");
        BND_TYPES.put("-remoteworkspace", "BOOLEAN");
        BND_TYPES.put("-reportnewer", "BOOLEAN");
        BND_TYPES.put("-resolve.excludesystem", "BOOLEAN");
        BND_TYPES.put("-resolvedebug", "INTEGER");
        BND_TYPES.put("-resourceonly", "BOOLEAN");
        BND_TYPES.put("-runbuilds", "BOOLEAN");
        BND_TYPES.put("-runframeworkrestart", "BOOLEAN");
        BND_TYPES.put("-runkeep", "BOOLEAN");
        BND_TYPES.put("-runnoreferences", "BOOLEAN");
        BND_TYPES.put("-runtrace", "BOOLEAN");
        BND_TYPES.put("-snapshot", "STRING");
        BND_TYPES.put("-sources", "BOOLEAN");
        BND_TYPES.put("-strict", "BOOLEAN");
        BND_TYPES.put("-testcontinuous", "BOOLEAN");
        BND_TYPES.put("-testunresolved", "BOOLEAN");
    }
}
