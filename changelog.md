Liferay Plugin for IntelliJ
===========================

All notable changes to this project will be documented in this file.

## 0.0.4
- Bugfix: Fixed parsing liferay-look-and-feel.xml when creating that file for the first time
- Bugfix: Correctly detect locale strings in JSON schema for journal structure files
- Bugfix: Fixed compatibility with IntelliJ 2018.3 when injecting javascript into freemarker taglibs
- Feature: Code completion for portlet names in JSP taglibs

## 0.0.3
- Feature: Javascript injection for aui:validator and many onXXX attributes
- Feature: File References in several Liferay XML files
- Bugfix: Code completion for OSGi properties did not work when using Java Constants
- Feature: Quick documentation for several OSGi properties
- Feature: Support for core JSP hooks
- Feature: Detect implicit usage for OSGi annotations like (at)Reference etc.
- Feature: Javascript injection into Freemarker templates
- Feature: JavaBean property references in JSP Taglibs
- Feature: JSON Schema for Journal Structures and other resources importer files
- Feature: Support for OSGi fragments for non-Liferay modules
- Feature: Implicit groovy variables
- Feature: Automatically exclude the "bundles" directory
- Feature: Liferay target version support
- Feature: Improved support for getSiblings() in Freemarker templates
- Feature: Inspections for service.xml
- Feature: Added taglib code completion for class names
- Feature: Class Name completion for serviceLocator.findService(), enumUtil, staticUtil and staticFieldGetter in Freemarker templates
- Feature: Implicit resolve of serviceLocator, enumUtil and staticUtil instructions in Freemarker templates
- Feature: Inpsections for JSP files 
- Change: do not include AlloyUI and Liferay Javascript in the plugin, use project dependencies instead
- Change: dropped support for IntelliJ 2016 and 2017, now requires 2018.1 and above

## 0.0.2
- Feature: Added Compass Support for Liferay 6.1 and 6.2
- Feature: Added Liferay specific contexts for Live Templates 
- Feature: Compare JSPs with original Liferay file (JSP hooks or web fragments)

## 0.0.1
- Initial Version