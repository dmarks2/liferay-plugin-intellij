JavaScript
==========

1. [Predefined JavaScript Libraries](#predefined-javascript-libraries)
2. [JavaScript injection for taglibs](#javascript-injection-for-taglibs)

Predefined JavaScript Libraries
-------------------------------

The plugin provides predefined JavaScript Libraries for AlloyUI and Liferay Barebone files. By this, code completion works
for JavaScript variables like ```AUI()``` or ```Liferay```.

![Liferay JavaScript](liferay_js.png "Liferay Javascript")

*This feature works in IntelliJ Ultimate Edition only.*

*To lookup AlloyUI or Liferay Javascript files you need to add a dependency to ```portal-web``` (Liferay 6.x) or to 
```com.liferay.frontend.js.web``` and ```com.liferay.frontend.js.aui.web``` (Liferay 7.x) somewhere in your project.*

JavaScript injection for taglibs
--------------------------------

When using the ``<aui:script>``  or ``<aui:validator>`` tag in JSPs, the content is detected as JavaScript, so you can use all JavaScript features
like highlighting, syntax checking and code completions. This works for several attributes of AUI or Liferay tags, too, e.g. for ``<aui:a onClick="javascript">``.

The same applies to Freemarker templates. 

![AUI Script Tag](aui_script.png "AUI Script Tag")

The following tag libraries are supported:

    <aui:>
    <liferay-frontend:>
    <liferay-ui:>
    

*This feature works in IntelliJ Ultimate Edition only.*

*To detect the tags, the namespaces must be declared properly, e.g.*

``` html
    <%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
```


