Editing Velocity and Freemarker files in Layout Templates and Themes
====================================================================

Velocity and Freemarker files are being used at different places.

## Description

**Liferay Layout template** are using ``.tpl`` files. This plugin defines that those files should be handled as Velocity files, so
all Velocity features like highlighting, syntax checking and code completions are available.  

![Liferay Layout Templates](layout_tpl.png "Liferay Layout Templates")

**Liferay Themes** can be created with Freemarker or Velocity. This plugin defines implicit variables which are usable in those files.
Code Completion works.

![Freemarker Variables](freemarker_variables.png "Freemarker Variables")

Additionally basic Velocity and Freemarker macros are available. For Freemarker, known Liferay Taglibs are available as additional namespaces (Liferay 7 / DXP only).

![Freemarker Taglibs](freemarker_taglibs.png "Freemarker Taglibs")

If you have defined custom theme settings in your ```liferay-look-and-feel.xml``` those are are provided for code completion, too. You can jump
to the declaration in the ```liferay-look-and-feel.xml```.

![Freemarker Theme Settings](freemarker_theme_settings.png "Freemarker Theme Settings")

Known path variables like ```${images_folder}``` are resolved, so code completion e.g. for images in your theme works.

![Freemarker Path Variables](freemarker_path_variables.png "Freemarker Path Variables")

## Requirements

This feature works in IntelliJ Ultimate Edition only.