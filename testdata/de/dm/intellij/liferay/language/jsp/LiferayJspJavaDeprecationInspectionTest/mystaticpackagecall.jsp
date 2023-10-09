<%@ page import="com.liferay.portal.kernel.util.<error descr="Cannot resolve class 'HttpUtil'">HttpUtil</error>" %>

<%
    String foo = <warning descr="Methods not depending on org.apache.http have been moved from HttpUtil to HttpComponentsUtil (see LPS-150185)"><error descr="Cannot resolve symbol 'HttpUtil'">HttpUtil</error>.addParameter("http://localhost", "foo", "bar")</warning>;
%>