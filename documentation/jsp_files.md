Editing JSP files
=================

## Description

Existing JSPs can be overridden by a *JSP Hook* (Liferay 6.x) or an *OSGi fragment* (Liferay 7 / DXP).

The plugin searches the *portal-web* (Liferay 6.x) or the *fragment-host* (Liferay 7 / DXP), so that ```<%@ include file=... %>``` can be resolved properly.

By this, references from the *original* JSPs can be resolved, code completion and syntax checking is possible.

![Reference to login-web](login_web.png "Reference to login-web")

In Liferay 7 / DXP the JSPs for your portlet are placed at ```META-INF/resources```. This plugin defines that folder as Web Root, so
absolute paths (e.g. for includes) are resolved properly. 

Several Liferay or AUI tags provide attributes like ```cssClass``` or ```iconCssClass```. This plugins provides code completion features for this attribute, so that
(S)CSS classes are resolved properly.

![cssClass attribute](jsp_css_class.png "cssClass attribute")

Many attributes for Liferay or AUI tags refer to keys inside language resource bundles. This plugin adds code completion features for those
attributes.

![language keys](jsp_language_keys.png "language keys")

Additionally you can search for classnames and java bean properties in specific tags like `<aui:model-context>` or `<aui:input>`.

![classnames](jsp_classname.gif "classnames")
 
You can compare your JSP with the original Liferay file to see what has been changed. This works for JSP Hooks (Liferay 6.x) and OSGi fragments (Liferay 7 / DXP).

![JSP compare](jsp_compare.png "JSP compare")

Using double quotes inside taglib attributes are not permitted. A configurable inspection is provided
which checks for this case and offers a quick fix to use single quotes instead.

![JSP inspections](jsp_inspections.gif "JSP inspection")

 
## Requirements

This feature works in IntelliJ Ultimate Edition only.

To use references to a *fragment-host* (Liferay 7 / DXP), the corresponding library must be present in your module (e.g. dependency declaration in Ivy, Maven or Gradle).

To resolve references for *JSP Hooks* (Liferay 6.x), the library ```portal-web``` must be present in your module (e.g. dependency declaration in Ivy, Maven or Gradle).

