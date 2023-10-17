<%@ page import="com.liferay.portal.kernel.theme.ThemeDisplay" %>

<%
    ThemeDisplay themeDisplay = request.<error descr="Cannot resolve method 'getAttribute(String)'">getAttribute</error>("THEME_DISPLAY");
%>

<a href="<%=<warning descr="Flash support has been removed completely. (see LPS-121733)">themeDisplay.<error descr="Cannot resolve method 'getPathFlash' in 'ThemeDisplay'">getPathFlash</error>()</warning>%>">foo</a>