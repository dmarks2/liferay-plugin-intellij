Velocity and Freemarker
=======================

1. [Liferay Layout Templates](#liferay-layout-templates)
2. [Implicit variables for Liferay Themes](#implicit-variables-for-liferay-themes)
3. [Special helper variables for Freemarker](#special-helper-variables-for-freemarker)
4. [Freemarker taglibs](#freemarker-taglibs)
5. [Implicit Theme Settings variables](#implicit-theme-settings-variables)
6. [Implicit Theme Path variables](#implicit-theme-path-variables)

Liferay Layout Templates
------------------------

Liferay Layout template are defined in ``.tpl`` files. This plugin defines that those files should be handled as Velocity files, so
all Velocity features like highlighting, syntax checking and code completions are available.  

![Liferay Layout Templates](layout_tpl.png "Liferay Layout Templates")

*This feature works in IntelliJ Ultimate Edition only.*

Implicit variables for Liferay Themes
-------------------------------------

Liferay Themes can be created with Freemarker or Velocity. This plugin defines implicit variables which are usable in those files.

![Freemarker Variables](freemarker_variables.png "Freemarker Variables")

*This feature works in IntelliJ Ultimate Edition only.*

Special helper variables for Freemarker
---------------------------------------

Additional support is available for special helper variables in Freemarker templates like ```serviceLocator```. 
You can look up appropriate class names and the returned object is detected as the correct class type.

The following variables are supported:

    enumUtil
    serviceLocator
    staticFieldGetter
    staticUtil

*This feature works in IntelliJ Ultimate Edition only.*

Freemarker taglibs
------------------

Additionally basic Velocity and Freemarker macros are available. For Freemarker, known Liferay Taglibs are available as additional namespaces. 

If you are using ```<@aui:script>``` tags etc. the content is detected as Javascript, too.

![Freemarker Taglibs](freemarker_taglibs.png "Freemarker Taglibs")

The following taglibs are available:

    <@liferay_aui />
    <@liferay_portlet />
    <@portlet />
    <@liferay_security />
    <@liferay_theme />
    <@liferay_ui />
    <@liferay_util />
    <@liferay_product_navigation />
    <@liferay_journal />
    <@liferay_flags />
    <@liferay_layout />
    <@liferay_site_navigation />
    <@liferay_asset />
    <@liferay_map />
    <@liferay_item_selector />
    <@liferay_expando />
    <@liferay_frontend />
    <@liferay_trash />

*This feature works in IntelliJ Ultimate Edition only.*

Implicit Theme Settings variables
---------------------------------

If you have defined custom theme settings in your ```liferay-look-and-feel.xml``` those are are provided for code completion, too. You can jump
to the declaration in the ```liferay-look-and-feel.xml```.

![Freemarker Theme Settings](freemarker_theme_settings.png "Freemarker Theme Settings")

*This feature works in IntelliJ Ultimate Edition only.*

Implicit Theme Path variables
-----------------------------

Known path variables like ```${images_folder}``` are resolved, so code completion e.g. for images in your theme works.

![Freemarker Path Variables](freemarker_path_variables.png "Freemarker Path Variables")

The following theme path variables are available:

    images_folder
    css_folder
    full_css_path
    javascript_folder 
    templates_folder
    full_templates_path

*This feature works in IntelliJ Ultimate Edition only.*