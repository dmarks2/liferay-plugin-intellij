<%@ page import="com.liferay.portal.kernel.util.HttpUtil" %>

<%
    String foo = com.liferay.portal.kernel.util.HttpComponentsUtil.addParameter("http://localhost", "foo", "bar");
%>