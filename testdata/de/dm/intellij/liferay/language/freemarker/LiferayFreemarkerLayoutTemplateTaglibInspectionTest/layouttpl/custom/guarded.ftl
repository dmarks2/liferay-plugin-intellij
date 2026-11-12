<div class="my-layout" id="main-content" role="main">
	<#if liferay_portlet??>
		<@liferay_portlet["runtime"]
			portletName="com_liferay_site_navigation_breadcrumb_web_portlet_SiteNavigationBreadcrumbPortlet"
		/>
	</#if>
	<#if (liferay_ui)?has_content && themeDisplay??>
		<div>
			<@liferay_ui["message"] key="hello" />
		</div>
	</#if>
	<#if foo??>
		<#if liferay_util??>
			<@liferay_util["include"] page="/foo.jsp" />
		</#if>
	</#if>
	<#if someCondition>
		foo
	<#elseif liferay_portlet??>
		<@liferay_portlet.runtime portletName="foo" />
	</#if>
	<@myMacro />
	<div class="portlet-layout row">
		<div class="col-md-12 portlet-column portlet-column-only" id="column-1">
			${processor.processColumn("column-1", "portlet-column-content portlet-column-content-only")}
		</div>
	</div>
</div>
