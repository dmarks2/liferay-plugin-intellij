package de.dm.portlet;

import org.osgi.service.component.annotations.Component;
import javax.portlet.Portlet;
import de.dm.portlet.PortletKeys;

@Component(
    property = {
        "javax.portlet.display-name=My Portlet",
        "javax.portlet.name=" + PortletKeys.MY_REFERENCE_PORTLET_KEY
    },
    service = Portlet.class
)
public class MyReferenceConstantPortlet implements Portlet {

}