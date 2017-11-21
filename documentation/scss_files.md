Editing CSS / SCSS files
========================

CSS and SCSS are used to create stylings for your portlets and develop new Themes for Liferay.

## Description

*References to images* can use the placeholder ```@theme_image_path@```. This plugin tells IntelliJ where
this folder is present in your Theme, so that you can use code completion and jump to the images. The plugin
will check the ```images-path``` property in your ```liferay-look-and-feel.xml``` to find the correct folder.

![Theme Image Path Variable](theme_image_path.png "Theme Image Path Variable")

For Liferay 7 / DXP the *css-frontend-common* library is available in Theme SCSS files. This plugin makes it available
in IntelliJ, so that you can use the provided Mixins or Bourbon functions.

![Bourbon Functions](bourbon.png "Bourbon Functions")

If you inherit from the parent theme *_styled* or *_unstyled*, this plugin provides these to IntelliJ. So you
can use references or code completion features.

![Unstyled Variables](unstyled_variables.png "Unstyled Variables")

## Requirements

This feature works in IntelliJ Ultimate Edition only.

To use references to *css-frontend-common*, this library must be present in your module (e.g. dependency declaration in Ivy, Maven or Gradle).

To use references to the parent theme in Liferay 7 / DXP, those libraries must be present in your module (e.g. dependency declaration in Ivy, Maven or Gradle).

To use references to the parent theme in Liferay 6.x, a reference to ```portal-web``` must be present in your module (e.g. dependency declaration in Ivy, Maven or Gradle).
