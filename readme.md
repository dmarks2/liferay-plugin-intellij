<!--
  Title: Liferay Plugin for IntelliJ
  Description: A plugin for Jetbrains IntelliJ IDEA to support developing Liferay components.
  Author: dmarks2
  -->
  
Liferay Plugin for IntelliJ
===========================

[![Build Status](https://travis-ci.org/dmarks2/liferay-plugin-intellij.svg?branch=master)](https://travis-ci.org/dmarks2/liferay-plugin-intellij)

Description
-----------
A plugin for Jetbrains [IntelliJ IDEA](https://www.jetbrains.com/idea/) to support developing [Liferay](http://www.liferay.com/) components. 

Note that there is an [official plugin for IntelliJ from Liferay](https://plugins.jetbrains.com/plugin/10739-liferay) (which I contribute to regularly). This plugin here should be 
considered experimental. Both plugins cannot be installed at the same time.

This plugin mainly focuses on code completion features. It gives IntelliJ IDEA more information about typical Liferay files and project structures. 
By this the existing code completion features of IntelliJ can be used for Liferay specific files, too. There is no user interface
for the plugin. Just have a look at the following topics to see which areas have been improved:

* [Creating new Modules](documentation/new_modules.md) becomes easier, because this plugin provides you with the official Liferay Maven archetypes. 
* [Editing Liferay XML files](documentation/xml_files.md) is improved, e.g. by providing the XML Schema files for mostly all Liferay specific XML files. 
* For [JavaScript files](documentation/js_files.md) the plugin provides you with libraries for the Liferay barebone Javascript files and AlloyUI. 
* For [Velocity and Freemarker files in Layout Templates and Themes](documentation/vtl_ftl_files.md) the plugin provides code completion for known variables and theme settings.
* Also for [Journal Templates and Application Display Templates](documentation/structures_templates_adt.md) known variables are provided, including variables from Journal structures.
* For [CSS / SCSS files](documentation/scss_files.md) references to the parent theme are resolved and specific placeholders like @theme_image_path@ are detected. 
* When [editing JSP files](documentation/jsp_files.md) you have access to the original JSPs (in JSP hooks or web fragments). Additionally code completion is possible for Liferay or AUI taglibs including cssClasses and language keys.
* Known properties for [OSGi components](documentation/osgi_components.md) are provided (e.g. Portlet properties).
* You can jump forward and backward between the [service.xml](documentation/service_xml.md) and the implementation class.
* Support for [groovy scripts](documentation/groovy.md) (Liferay admin console).
* Code completion and documentation are available for [portal.properties](documentation/portalprops.md)

Supported IDEs
--------------
* IntelliJ 2021.2 and above (Community)
* IntelliJ 2021.2 and above (Ultimate)

Supported Liferay Versions
--------------------------
* Liferay 6.1 CE / EE
* Liferay 6.2 CE / EE
* Liferay 7.0, 7.1, 7.2, 7.3, 7.4 / DXP

Supported Build Tools
---------------------
* Maven
* Gradle (Liferay 7.0, 7.1, 7.2, 7.3, 7.4 / DXP only)
* Npm 

Releases
--------

See [Releases](https://github.com/dmarks2/liferay-plugin-intellij/releases) to download binary release versions.

For automatic updates configure the following URL as custom repository in IntelliJ IDEA:

    https://raw.githubusercontent.com/dmarks2/liferay-plugin-intellij/master/updatePlugins.xml

Developers
----------
This plugin uses a [Gradle based IntelliJ Plugin development](http://www.jetbrains.org/intellij/sdk/docs/tutorials/build_system/prerequisites.html).

To build the plugin, just run ```gradle build```. The build script downloads required files automatically.

Contributions are welcome.

Known Issues
------------
* Indent does not work properly inside ``<aui:script>``-Tags containing other tags like ``<portlet:namespace/>``.
* ``@theme_image_path@`` reference in CSS files work, but still shown in red (SCSS works properly)
* This plugin cannot be installed together with the official plugin from Liferay.

Licence
-------

See [LICENSE.txt](LICENSE.txt).

For third-party licenses see [LICENSE-3RD-PARTY.txt](LICENSE-3RD-PARTY.txt).
