package de.dm.intellij.liferay.client;

import com.intellij.util.xml.NanoXmlUtil;

import java.io.Reader;
import java.io.StringReader;
import java.util.concurrent.atomic.AtomicReference;

public class LiferayXMLUtil {

    public static String getName(String nameXML) {
        AtomicReference<String> result = new AtomicReference<>();

        result.set(nameXML);

        NanoXmlUtil.parse(new StringReader(nameXML), new NanoXmlUtil.BaseXmlBuilder() {
            @Override
            public void addPCData(Reader reader, String systemID, int lineNr) throws Exception {
                result.set(readText(reader).trim());
            }
        });

        return result.get();
    }
}
