package de.dm.intellij.liferay.language.service;

import com.liferay.service.builder.ObjectFactory;
import com.liferay.service.builder.ServiceBuilder;
import com.sun.xml.bind.v2.ContextFactory;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

public class LiferayServiceBuilderFactoryUtil {

    public static ServiceBuilder readServiceXml(InputStream inputStream) throws JAXBException {
        JAXBContext jaxbContext = ContextFactory.createContext(new Class[]{ObjectFactory.class}, null);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        InputSource inputSource = new InputSource(inputStream);

        ServiceBuilder result = (ServiceBuilder) unmarshaller.unmarshal(inputSource);

        return result;
    }
}
