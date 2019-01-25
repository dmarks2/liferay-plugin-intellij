<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>

<liferay-portlet:actionURL var="foo" portletName="de_dm_portlet_MyPortletName">
    <liferay-portlet:param name="javax.portlet.action" value="<caret>" />
</liferay-portlet:actionURL>