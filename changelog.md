Liferay Plugin for IntelliJ
===========================

All notable changes to this project will be documented in this file.

## 0.0.25
- Feature: code completion for the `unbind` attribute in a `@Reference` annotation

## 0.0.24
- Feature: add deprecation checks for Liferay 2024.Q4
- Feature: add support for IntelliJ IDEA 2025.1, dropped support for older versions
- Feature: add deprecation check for LPS-178619

## 0.0.23
- Feature: add deprecation check for LPS-188270
- Feature: add support for additional client extensions (Editor Config Contributor, FDS Filter, Instance Settings, Object Validation Rule, Site Initializer)
- Bugfix: improved startup time

## 0.0.22
- Feature: add deprecation check for LPS-104241, LPS-175951, LPS-182671
- Feature: deprecation checks for devDependencies in package.json
- Feature: add support for BundleSiteInitializer directory structures
- Feature: add documentation lookup and code completion for Liferay specific properties in `gradle.properties`
- Feature: add XPath expression support for Liferay specific helper classes like `SAXReaderUtil`
- Feature: add deprecation checks for Liferay 2024.Q3
- Feature: add support for custom taglib mappings for freemarker from third-party libraries
- Bugfix: Finally fixed gradle support for projects using Java 16 or below
- Bugfix: Exception occurred after deleting a fragment.json file
- Bugfix: Detecting servlet context imports in Freemarker files did not work for gradle projects (code completion, freemarker debugger)

## 0.0.21
- Feature: add deprecation checks for Liferay 2024.Q1 and Liferay 2024.Q2
- Feature: add quick fix for non-matching plugin or dependency versions in pom.xml (portal-web, service-builder, rest-builder)
- Feature: add support for ADTs for the Date Facet Portlet (available since Liferay 7.4.3.104)
- Feature: update maven checks for Liferay toolchain (including biz.aqute.bnd and com.liferay.ant.bnd)

## 0.0.20
- Change: Update support for IntelliJ 2024.1, dropped support for earlier versions
- Bugfix: Fixed ArrayIndexOutOfBoundsException when processing properties without a value

## 0.0.19
- Feature: add deprecation checks for Liferay 2023.Q4
- Feature: update webdriver version to support Chrome 114+ when running Poshi tests
- Bugfix: File references were not working in XML files for Liferay 7.4
- Bugfix: NullPointerException when trying to validate plugin versions in a pom.xml which did not contain a <plugins> section

## 0.0.18
- Feature: add freemarker taglib deprecation inspections
- Feature: add java, jsp, groovy code deprecation inspections
- Feature: add property file deprecation inspections (portal-ext.properties, system-ext.properties, liferay-plugin-package.properties)
- Feature: add changes from LPS-112464 (className changed to cssClass in Clay taglibs)
- Feature: add in-depth validations of Poshi Scripts (using external Poshi Validation)
- Feature: check if service-builder and rest-builder maven plugins match the used liferay version
- Feature: check if portal-web dependency matches the used liferay version
- Bugfix: fix encoding when executing Poshi Scripts (UTF-8 instead of system dependant encoding)
- Bugfix: Missing implicit variables workflowContext, roles and user in scripted-assignment tag for workflow definitions
- Change: Update support for IntelliJ 2023.3, dropped support for older versions

## 0.0.17
- Feature: Code completion features for client-extensions.yaml
- Feature: Code completion features for rest-config.yaml
- Feature: Code completion and references for JSP taglibs referring to javascript modules (e.g. react:component)
- Feature: Add support for date-time in data-lfr-editable-type (see LPS-154775)
- Feature: Add date_time field type for web content structures (see LPS-178624)
- Feature: show deprecation warnings for deprecated or removed taglibs or attributes in JSP files
- Bugfix: Fix "Module already disposed" when unloading a module
- Bugfix: fix support for IntelliJ 2023.2
- Change: Update support for IntelliJ 2023.1, dropped support for older versions
- Change: Update Poshi to 1.0.453
- Change: Remove unsupported languages in workflow scripts (see LPS-187594)

## 0.0.16
- Feature: Experimental Poshi support
- Feature: Add Freemarker debugging support in Application Display Templates for the Similar Results portlet
- Bugfix: Execution of Liferay groovy scripts required Java 17 (now Java 11 is enough)
- Bugfix: Freemarker debugging did not work ins some Application Display Templates due to API changes in newer Liferay versions
- Bugfix: Implicit variables for Blog Application Display Templates in newer Liferay versions

## 0.0.15
- Feature: Ability to compare theme files with the original files from the parent theme
- Feature: Resolve configurationPid OSGi properties to configuration interface classes
- Feature: Add inspection to check if a Meta Configuration ID matches the full qualified classname of the interface
- Change: Remove inspection about aui:fieldset must be inside an aui:fieldset-group (tag has been removed in Liferay lately)
- Change: Update support for IntelliJ 2022.3, dropped support for older versions
- Change: Disabled gradle importers for bundle-support and theme plugins (no longer working with Java 17)
- Bugfix: Setting Freemarker Breakpoint in Application Display Templates with filenames containing spaces did not work

## 0.0.14
- Feature: Resolve Resource Bundles
- Feature: Support for Journal Structures based on Data Engine Definition schema (Liferay 7.4 only)
- Feature: Implicit usage of constructors in OSGi components (e.g. ServiceWrappers)
- Feature: Resolve known Freemarker context variables in Fragment HTML files
- Feature: Add support for Freemarker debugger in Liferay 7.4
- Feature: Ability to use Freemarker debugger for Modules based on direct service calls to Advanced Resources Importer
- Feature: JavaScript support for liferay-look-and-feel.xml
- Feature: Add javax.portlet.version to properties of Portlet components
- Feature: Run groovy scripts directly on a running Liferay server
- Feature: Improve documentation provided for implicit freemarker variables
- Feature: Add references for target names in workflow definition files
- Feature: Add support to debug Freemarker templates imported from web context
- Feature: Added some documentations for bnd.bnd instructions
- Change: Update support for IntelliJ 2022.1
- Bugfix: ClassCastException during code completions in an empty frontend-token-definition.json file
- Bugfix: description tag inside notification of a workflow definition supports Freemarker, too
- Bugfix: unable to attach Freemarker breakpoint if journal structure or template has been renamed 
- Bugfix: try to circumvent IntelliJ hang when adding a Freemarker breakpoint
- Bugfix: Do not add inspection of @Reference usage in abstract classes or interfaces

## 0.0.13
- Feature: Language injections for script and template tags in Liferay workflow definition files
- Feature: Provide workflow context variables for script and template tags (groovy and freemarker)
- Feature: Resolve Files and Layout entries in Resources Importer Template files
- Feature: Inject SQL language into custom-sql definition files
- Feature: OSGi component properties for Fragment components
- Feature: Add support for custom fragment html tags and attributes
- Feature: Add JSON schemas for several fragment related files
- Feature: Add freemarker support for fragment html files
- Feature: File references in fragment.json file
- Feature: Add support for comments in bnd.bnd files
- Bugfix: Exception when trying to inject Javascript inside aui:script-tags containing additional attributes (like "use" or "require")
- Bugfix: Avoid NullPointerException when trying to lookup documentation for an unknown OSGi property
- Bugfix: Inner classes where not properly resolved inside className attributes in JSP files
- Change: Fixed support for IntelliJ 2021.3

## 0.0.12
- Feature: Added support for Liferay 7.4
- Feature: Code Completion and documentation for portal.properties files
- Feature: JSON Schema for frontend-token-definition.json
- Feature: Language.properties references for label attributes in frontend-token-definition.json  
- Feature: Clay SCSS Variables documentation
- Feature: Added support for portlet-display-templates.xml (XML Schema and file references)
- Feature: Added support for new journal structure json schema (for Liferay 7.4)
- Bugfix: Fixed detecting the bundles directory from the bundle-support maven plugin
- Change: Fixed support for IntelliJ 2021.2, dropped support for IntelliJ 2021.1 and lower
  
## 0.0.11
- Feature: Implicit usage of some property files and keys ("javax.portlet.title." or "liferay.workspace."), so that they are no longer shown as "unused property"
- Feature: Inspection of @Reference usage outside a @Component class  
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
