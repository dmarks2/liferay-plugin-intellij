package de.dm.portlet;

import org.osgi.service.component.annotations.Component;
import javax.portlet.Portlet;

@Component(
    property = {
        "javax.portlet.display-name=My Portlet",
        "javax.portlet.name=" + MyConstantPortlet.MY_PORTLET_NAME
    },
    service = Portlet.class
)
public class MyConstantPortlet implements Portlet {

    public static final String MY_PORTLET_NAME = "de_dm_portlet_MyConstantPortlet";

}