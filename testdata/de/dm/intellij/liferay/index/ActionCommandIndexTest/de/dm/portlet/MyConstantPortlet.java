package de.dm.portlet;

import org.osgi.service.component.annotations.Component;
import javax.portlet.Portlet;
import javax.portlet.ProcessAction;

@Component(
    property = {
        "javax.portlet.display-name=My Portlet",
        "javax.portlet.name=de_dm_portlet_MyPortletName"
    },
    service = Portlet.class
)
public class MyConstantPortlet implements Portlet {

    public static final String MY_ACTION_NAME = "/my/action";

    @ProcessAction(name=MyConstantPortlet.MY_ACTION_NAME)
    public void handleActionCommand() {

    }

}