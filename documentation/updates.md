Liferay Updates
===============

1. [Deprecations](#deprecations)

Deprecations
------------

Java code, taglibs or properties may be been deprecated or removed in newer Liferay versions. This plugin offers several inspections to
check for deprecations or removals. If possible, a quickfix is offered to fix the problems.

The following areas are checked

* Java code
  * Check for moved or removed classes in import statements
  * Check for moved or removed method calls in Liferay APIs
  * Check for methods moved to other classes
* JSP files
  * Check for removed or changed Taglibs
  * Check for moved or removed classes in `<@page import>` statements
  * Check for moved or removed method calls in Liferay APIs
  * Check for methods moved to other classes
* Groovy scripts
  * Check for moved or removed classes in import statements
  * Check for moved or removed method calls in Liferay APIs
  * Check for methods moved to other classes
* Freemarker templates
  * Check for removed or changed Taglibs
* Property files
  * Check for removed or changed properties inside `portal-xxx.properties`
  * Check for matching liferay versions key inside `liferay-plugin-package.properties`
* XML files
  * Checks for matching DTD or Schema versions based on the used liferay version
* Maven Dependencies
  * Checks if `service-builder` and `rest-builder` plugins match the used liferay version
  * Checks if `portal-web` dependency matches the used liferay version

*Checking for removals or deprecations works for Liferay 7.0 to Liferay 7.4*
