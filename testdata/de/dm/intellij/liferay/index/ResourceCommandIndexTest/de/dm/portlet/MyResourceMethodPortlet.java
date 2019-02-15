package de.dm.portlet;

import org.osgi.service.component.annotations.Component;
import javax.portlet.Portlet;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

@Component(
    property = {
        "javax.portlet.display-name=My Portlet",
        "javax.portlet.name=de_dm_portlet_MyResourceMethodPortletName"
    },
    service = Portlet.class
)
public class MyResourceMethodPortlet implements Portlet {

    public void resourceMethod(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

    }

}