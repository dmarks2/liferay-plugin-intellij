<#-- @ftlvariable name="saxReaderUtil" type="com.liferay.portal.kernel.xml.SAXReaderUtil" -->
<#-- @ftlvariable name="docXml" type="com.liferay.portal.kernel.xml.Document" -->

<#assign
    docXml = saxReaderUtil.read("<root/>")
>

<#assign
    text = docXml.valueOf("<caret>")
/>
