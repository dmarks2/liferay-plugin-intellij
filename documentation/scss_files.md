CSS / SCSS files
================

1. [Theme Image Path reference](#theme-image-path-reference)
2. [Bourbon Library](#bourbon-library)
3. [Theme Inheritance](#theme-inheritance)
4. [Compass Support](#compass-support)

Theme Image Path reference
--------------------------

References to images can use the placeholder ```@theme_image_path@```. This plugin tells IntelliJ where
this folder is present in your Theme, so that you can use code completion and jump to the images. The plugin
will check the ```images-path``` property in your ```liferay-look-and-feel.xml``` to find the correct folder.

![Theme Image Path Variable](theme_image_path.png "Theme Image Path Variable")

Bourbon library
---------------

For Liferay 7 / DXP the *css-frontend-common* library is available in Theme SCSS files. This plugin makes it available
in IntelliJ, so that you can use the provided Mixins or Bourbon functions.

![Bourbon Functions](bourbon.png "Bourbon Functions")

*This feature works in IntelliJ Ultimate Edition only.*

*To use references to css-frontend-common, this library must be present in your module (e.g. dependency declaration in Ivy, Maven or Gradle).*

Theme Inheritance
-----------------

If you inherit from the parent theme *_styled* or *_unstyled*, this plugin provides these to IntelliJ. So you
can use references and code completion features.

![Unstyled Variables](unstyled_variables.png "Unstyled Variables")

*This feature works in IntelliJ Ultimate Edition only.*

*To use references to the parent theme in Liferay 7 / DXP, those libraries must be present in your module (e.g. dependency declaration in Ivy, Maven or Gradle).*

*To use references to the parent theme in Liferay 6.x, a reference to ```portal-web``` must be present in your module (e.g. dependency declaration in Ivy, Maven or Gradle).*

Compass Support
---------------

Liferay 6.1 and 6.2 use the Compass library. This plugin creates a Compass library, so that functions and mixins can be resolved.

![Compass Support](compass.png "Compass Support")

*This feature works in IntelliJ Ultimate Edition only.*





