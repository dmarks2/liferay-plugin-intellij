<div class="my-layout" id="main-content" role="main">
	<@<warning descr="Macro liferay.breadcrumbs uses taglib liferay_portlet, which is not available while Liferay determines the columns of a layout template, surround it with <#if liferay_portlet??>">liferay.breadcrumbs</warning> />
	<@<warning descr="Macro liferay.control_menu uses taglib liferay_product_navigation, which is not available while Liferay determines the columns of a layout template, surround it with <#if liferay_product_navigation??>">liferay.control_menu</warning> />
	<@<warning descr="Macro liferay.languages uses taglib liferay_portlet, which is not available while Liferay determines the columns of a layout template, surround it with <#if liferay_portlet??>">liferay.languages</warning> />
	<@<warning descr="Macro liferay.navigation_menu uses taglib liferay_portlet, which is not available while Liferay determines the columns of a layout template, surround it with <#if liferay_portlet??>">liferay.navigation_menu</warning> />
	<@<warning descr="Macro liferay.search uses taglib liferay_portlet, which is not available while Liferay determines the columns of a layout template, surround it with <#if liferay_portlet??>">liferay.search</warning> />
	<@<warning descr="Macro liferay.search_bar uses taglib liferay_portlet, which is not available while Liferay determines the columns of a layout template, surround it with <#if liferay_portlet??>">liferay.search_bar</warning> />
	<@<warning descr="Macro liferay.user_personal_bar uses taglib liferay_portlet, which is not available while Liferay determines the columns of a layout template, surround it with <#if liferay_portlet??>">liferay.user_personal_bar</warning> />
	<@liferay.css file_name="main.css" />
	<@liferay.language key="hello" />
	<#if liferay_portlet??>
		<@liferay.breadcrumbs />
	</#if>
	<#if liferay_portlet??>
		<@<warning descr="Macro liferay.control_menu uses taglib liferay_product_navigation, which is not available while Liferay determines the columns of a layout template, surround it with <#if liferay_product_navigation??>">liferay.control_menu</warning> />
	</#if>
	<div class="portlet-layout row">
		<div class="col-md-12 portlet-column portlet-column-only" id="column-1">
			${processor.processColumn("column-1", "portlet-column-content portlet-column-content-only")}
		</div>
	</div>
</div>
