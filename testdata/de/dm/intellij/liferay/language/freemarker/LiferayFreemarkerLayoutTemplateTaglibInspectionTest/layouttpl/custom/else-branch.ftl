<#if liferay_portlet??>
	<@liferay_portlet["runtime"] portletName="foo" />
<#else>
	<@<warning descr="Taglib liferay_portlet is not available while Liferay determines the columns of a layout template, surround it with <#if liferay_portlet??>">liferay_portlet["runtime"]</warning> portletName="bar" />
</#if>
<#if liferay_ui??>
	<@<warning descr="Taglib liferay_portlet is not available while Liferay determines the columns of a layout template, surround it with <#if liferay_portlet??>">liferay_portlet["runtime"]</warning> portletName="baz" />
</#if>
