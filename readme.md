Liferay Plugin for IntelliJ
===========================

[![Build Status](https://travis-ci.org/dmarks2/liferay-plugin-intellij.svg?branch=master)](https://travis-ci.org/dmarks2/liferay-plugin-intellij)

Description
-----------
A plugin to help working with [Liferay](http://www.liferay.com/) in IntelliJ IDEA

Supported IDEs
--------------
* IntelliJ 2016.3+ (Community)
* IntelliJ 2016.3+ (Ultimate)

Supported Liferay Versions
--------------------------
* Liferay 6.1 CE / EE
* Liferay 6.2 CE / EE
* Liferay 7.0 / DXP

Supported Build Tools
---------------------
* Maven
* Gradle (Liferay 7.0 / DXP only)
* Npm 

Features
--------

This plugin adds some features for developing Liferay modules. The following topics have been
improved so far:

* [Creating new Modules](documentation/new_modules.md)  
* [Editing Liferay XML files](documentation/xml_files.md)
* [Editing JavaScript files](documentation/js_files.md)
* [Editing Velocity and Freemarker files in Layout Templates and Themes](documentation/vtl_ftl_files.md)
* [Editing Journal Templates and Application Display Templates](documentation/structures_templates_adt.md)
* [Editing CSS / SCSS files](documentation/scss_files.md)
* [Editing JSP files](documentation/jsp_files.md)
* [Editing OSGi components](documentation/osgi_components.md)
* [Editing service.xml](documentation/service_xml.md)


Developers
----------
This plugin uses a [Gradle based IntelliJ Plugin development](http://www.jetbrains.org/intellij/sdk/docs/tutorials/build_system/prerequisites.html).

To build the plugin, just run ```gradle build```. The build script downloads required files automatically.

Contributions are welcome.

Known Issues
------------
* Indent does not work properly in ``<aui:script>``-Tags
* ``@theme_image_path@`` reference in CSS files work, but still shown in red (SCSS works properly)

Ideas
-----
* Predefinded Velocity / Freemarker variables in Theme files (like ``$init`` or ``$full_templates_path``)

Licence
-------

See [LICENSE.txt](LICENSE.txt).

For third-party licenses see [LICENSE-3RD-PARTY.txt](LICENSE-3RD-PARTY.txt).
