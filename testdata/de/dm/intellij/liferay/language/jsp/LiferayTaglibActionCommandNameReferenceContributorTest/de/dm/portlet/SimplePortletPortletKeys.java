package de.dm.portlet;

import org.osgi.service.component.annotations.Component;
import javax.portlet.Portlet;
import javax.portlet.ProcessAction;

import de.dm.portlet.PortletKeys;

@Component(
        property = {
                "javax.portlet.display-name=My Portlet",
                "javax.portlet.name=" + PortletKeys.MY_PORTLET_NAME
        },
        service = Portlet.class
)
public class SimplePortletPortletKeys implements Portlet {

}