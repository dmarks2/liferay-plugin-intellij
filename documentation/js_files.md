Editing JavaScript files
========================

## Description

The plugin provides predefined JavaScript Libraries for AlloyUI and Liferay Barebone files. By this, code completion works
for JavaScript variables like ```AUI()``` or ```Liferay```.

![Liferay JavaScript](liferay_js.png "Liferay Javascript")

Additionally when using the ``<aui:script>`` Tag in JSPs, the content is detected as JavaScript, so you can use all JavaScript features
like highlighting, syntax checking and code completions.

![AUI Script Tag](aui_script.png "AUI Script Tag")

## Requirements

To detect the ``<aui:script>`` Tag, the namespace ``aui`` must be declared properly, e.g.

``` html
    <%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
```

This feature works in IntelliJ Ultimate Edition only.
