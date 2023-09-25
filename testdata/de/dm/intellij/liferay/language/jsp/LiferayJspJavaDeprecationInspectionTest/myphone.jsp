<%@ page import="com.liferay.portal.kernel.model.Phone" %>

<%
    Phone phone = new Phone();
%>

<div>
    <%=<warning descr="Phone column typeId has been renamed to listTypeId. (see LPS-162450)">phone.<error descr="Cannot resolve method 'getTypeId' in 'Phone'">getTypeId</error>()</warning>%>
</div>