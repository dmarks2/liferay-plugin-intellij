package de.dm.portlet;

import org.osgi.service.component.annotations.Component;
import javax.portlet.Portlet;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

@Component(
    property = {
        "javax.portlet.display-name=My Portlet",
        "javax.portlet.name=de_dm_portlet_MyActionMethodPortletName"
    },
    service = Portlet.class
)
public class MyPortlet implements Portlet {

    public void actionMethod(ActionRequest actionRequest, ActionResponse actionResponse) {

    }

}