Groovy Scripts
==============

1. [Implicit Variables](#implicit-variables)
2. [Running Groovy scripts on Liferay Server](#running-groovy-scripts-on-liferay-server)

Implicit Variables
------------------

In Liferay you can use groovy script in the admin console. Some variables are implicitly available
in those scripts. This plugin offers those implicit variables, so that code completion etc. works.

The following implicit variables are provided for groovy files:

    out
    actionRequest
    actionResponse
    portletConfig
    portletContext
    preferences
    userInfo

Running Groovy scripts on Liferay Server
----------------------------------------

It is possible to run groovy scripts from IntelliJ directly on a running Liferay Server.

To be able to run scripts the Gogo Shell must be available via telnet. For Liferay 7.1 and above
the developer must be enabled to expose the Gogo Shell on port 11311.

In IntelliJ just open the groovy file and click on the run marker at the top of the file.
Additional settings can be made by creating a Run Configuration of type **Liferay Remote Groovy**.

The results of the script executions are shown in the console.