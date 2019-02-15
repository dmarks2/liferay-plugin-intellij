<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>

<liferay-portlet:renderURL var="foo" portletName="de_dm_portlet_MyPortletName">
    <liferay-portlet:param name="mvcRenderCommandName" value="<caret>" />
</liferay-portlet:renderURL>