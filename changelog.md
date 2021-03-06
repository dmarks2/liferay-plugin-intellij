Liferay Plugin for IntelliJ
===========================

All notable changes to this project will be documented in this file.

## 0.0.11
- Feature: Implicit usage of some property files and keys ("javax.portlet.title." or "liferay.workspace."), so that they are no longer shown as "unused property"
- Feature: Inspection of @Reference usage outside of a @Component class  
- Bugfix: OSGi property code completion for single properties, e.g. without brackets around
- Bugfix: Improved compatibility with IntelliJ 2021.1 
- Bugfix: fixed implicit freemarker variables for modified facet portlet
- Change: Dropped support for IntelliJ IDEA 2020.3 or earlier

## 0.0.10
- Feature: Additional taglibs available in Freemarker templates for Liferay 7.1, 7.2 and 7.3
- Feature: Updated taglib support (CSS, Language Bundle) for Liferay 7.3
- Feature: Added some basic code completions for some liferay taglib attributes in JSPs and Freemarker
- Feature: Code completion for Java Classes in Freemarker taglibs
- Feature: Support for debugging Resource Importer files imported to specific sites  
- Bugfix: Parsing package.json may lead to an exception in the log
- Bugfix: Fixed compatibility with IntelliJ Community Edition
- Bugfix: Fixed injection of Javascript inside Freemarker taglibs using the square bracket notation
- Bugfix: Improved compatibility with IntelliJ 2020.2 and IntelliJ 2020.3
- Bugfix: Fixed errors in IntelliJ log (recursive commit, index deadlock)

## 0.0.9
- Feature: Added Liferay taglib support for Liferay 7.1 and 7.2 in Freemarker templates
- Feature: Added additional component properties for Liferay 7.1. and 7.2
- Feature: Live Templates for Application Display Templates and Journal Templates
- Feature: Added generic support for Liferay 7.3
- Feature: Inspection to check inheritance of OSGi component service classes
- Feature: Experimental support for new ADTs for the search components in Liferay 7.3
- Feature: Detect source folder from gulpfile.js to improve code completions in Liferay Theme modules
- Bugfix: Bugfix renaming packages inside bnd.bnd files
- Bugfix: Detecting the Liferay version not working correctly in submodules
- Bugfix: Resolving files relative to the content root directories in XML files using file references
- Bugfix: Fixed Velocity support for Liferay 7.1, 7.2 and 7.3
- Bugfix: Fixed detecting proper Liferay version when .iml file is saved inside .idea folder
- Bugfix: Fixed Freemarker Debugger for Web Content Templates in Liferay 7.3
- Change: Dropped support for IntelliJ IDEA 2019.2 or earlier
- Change: Try to avoid conflicts with the official IntelliJ plugin from Liferay

## 0.0.8
- Bugfix: Bugfix detecting a wrong version number in the Bundle-Version instruction inside the bnd.bnd file.

## 0.0.7
- Feature: Updated taglib support for Liferay 7.1 and 7.2
- Feature: Inspections for wrongly nested Liferay tags inside JSPs
- Feature: resolve \_SERVLET_CONTEXT\_ based imports in Freemarker templates from external dependencies, too.
- Feature OSGi Websocket Whiteboard properties
- Bugfix: Performance issues resolving JSPs from Custom JSP Bags
- Bugfix: fixed JSON schema for sitemap.json 

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