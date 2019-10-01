Liferay Plugin for IntelliJ
===========================

All notable changes to this project will be documented in this file.

## 0.0.7
- Feature: Updated taglib support for Liferay 7.1 and 7.2
- Feature: Inspections for wrongly nested Liferay tags inside JSPs

## 0.0.6
- Feature: File References for portlet.properties files
- Feature: Improved bnd.bnd editor
- Feature: Improved code completions to support Portlet 3.0 Specification
- Feature: Added support for Liferay 7.2
- Bugfix: Fixed parsing of invalid pom.xml
- Bugfix: Fixed non-idempotent computation error message
- Bugfix: Detecting the bundles directory for gradle projects
- Change: Updated compatibility to IntelliJ 2019.2 

## 0.0.5
- Feature: Experimental Freemarker debugger
- Feature: Additional service.xml inspections (primary key, uuid)
- Feature: resolve \_SERVLET_CONTEXT\_ based imports in Freemarker templates
- Feature: added preliminary support for Liferay 7.1
- Bugfix: JavaScript Support for aui:validator of type "required"
- Bugfix: Resolve Portlet Names from constant in a class from the same package as the portlet class 
- Bugfix: Fixed compatibility with IntelliJ 2019.1.x
- Bugfix: Fixed code completion for implicit Groovy variables
- Bugfix: TemplateNode properties (e.g. .getData()) were not offered for nested structure variables in Freemarker templates

## 0.0.4
- Bugfix: Fixed parsing liferay-look-and-feel.xml when creating that file for the first time
- Bugfix: Correctly detect locale strings in JSON schema for journal structure files
- Bugfix: Fixed compatibility with IntelliJ 2018.3 when injecting javascript into freemarker taglibs
- Bugfix: ServiceLocator lookup in Application Display Templates was not working properly
- Feature: Code completion for portlet names in JSP taglibs
- Feature: Code completion for action/render/resource commands in JSP taglibs
- Feature: Support for multiple OSGi service definitions in a @Component annotation
- Feature: Inspections for service.xml
- Feature: Class Name completion for objectUtil in Freemarker templates
- Feature: Quick Documentation lookup for Journal Structure variables in Freemarker templates
- Feature: Experimental support to debug JSPs (in own modules and in dependencies)

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
- Feature: Added taglib code completion for class names
- Feature: Class Name completion for serviceLocator.findService(), enumUtil, staticUtil and staticFieldGetter in Freemarker templates
- Feature: Implicit resolve of serviceLocator, enumUtil and staticUtil instructions in Freemarker templates
- Feature: Inspections for JSP files 
- Change: do not include AlloyUI and Liferay Javascript in the plugin, use project dependencies instead
- Change: dropped support for IntelliJ 2016 and 2017, now requires 2018.1 and above

## 0.0.2
- Feature: Added Compass Support for Liferay 6.1 and 6.2
- Feature: Added Liferay specific contexts for Live Templates 
- Feature: Compare JSPs with original Liferay file (JSP hooks or web fragments)

## 0.0.1
- Initial Version