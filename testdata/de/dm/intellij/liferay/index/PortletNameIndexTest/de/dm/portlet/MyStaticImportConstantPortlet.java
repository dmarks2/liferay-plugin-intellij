package de.dm.portlet;

import org.osgi.service.component.annotations.Component;
import javax.portlet.Portlet;

import static de.dm.portlet.PortletKeys.MY_PORTLET_KEY;

@Component(
    property = {
        "javax.portlet.display-name=My Portlet",
        "javax.portlet.name=" + MY_PORTLET_KEY
    },
    service = Portlet.class
)
public class MyStaticImportConstantPortlet implements Portlet {

}