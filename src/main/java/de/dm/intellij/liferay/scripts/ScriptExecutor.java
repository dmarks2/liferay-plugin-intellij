package de.dm.intellij.liferay.scripts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ScriptExecutor {

    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.err.println("Invalid syntax");

            System.exit(1);
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String scriptName = args[2];

        Path scriptFile = Path.of(scriptName);

        if (! scriptFile.toFile().exists()) {
            System.err.println("Script file " + scriptName + " does not exist!");

            System.exit(1);
        }

        String content = Files.readString(scriptFile);

        content = content.replace("\\\\", "\\\\\\\\");
        content = content.replace("\\", "\\\\");
        content = content.replace("\r", "\\r");
        content = content.replace("\n", "\\n");

        String gogoScript = "$groovyExecutor eval null $inputObjects null $'" + content + "';";

        try (GogoShellClient client = new GogoShellClient(host, port)) {
            client.send("service = { $.context service ([($.context servicereferences $1 $2)] get 0) };");

            client.send("editServerCommand = service \"com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand\" \"(mvc.command.name=/server_admin/edit_server)\"");
            client.send("editServerCommandClass = $editServerCommand class");

            client.send("currentThread = (($editServerCommandClass forname \"java.lang.Thread\") method \"currentThread\") invoke null;");
            client.send("currentThreadClassLoader = $currentThread contextclassloader;");

            client.send("$currentThread setcontextclassloader ($editServerCommandClass classloader);");

            client.send("portal = service \"com.liferay.portal.kernel.util.Portal\"");
            client.send("companyId = $portal defaultCompanyId");

            client.send("roleLocalService = service \"com.liferay.portal.kernel.service.RoleLocalService\"");
            client.send("adminRole = $roleLocalService getRole $companyId \"Administrator\"");

            client.send("userLocalService = service \"com.liferay.portal.kernel.service.UserLocalService\"");
            client.send("adminUser = ($userLocalService getRoleUsers ($adminRole roleId)) get 0");

            client.send("PrincipalThreadLocal = ((bundle 0) loadclass com.liferay.portal.kernel.security.auth.PrincipalThreadLocal)");
            client.send("currentUser = $PrincipalThreadLocal name");
            client.send("$PrincipalThreadLocal setName ($adminUser userId)");

            client.send("groovyExecutor = service \"com.liferay.portal.kernel.scripting.ScriptingExecutor\" \"(scripting.language=groovy)\";");

            client.send("System = ((bundle 0) loadclass java.lang.System);");
            client.send("inputObjects = [out=System.out];");

            String result = client.send(gogoScript);

            client.send("$PrincipalThreadLocal setName $currentUser");
            client.send("$currentThread setcontextclassloader $currentThreadClassLoader;");

            System.out.println(result);
        }
    }
}
