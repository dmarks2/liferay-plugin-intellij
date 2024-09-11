import com.liferay.portal.kernel.xml.SAXReaderUtil

def docXml = SAXReaderUtil.read("<root/>")

def text = docXml.valueOf("t<caret>")
