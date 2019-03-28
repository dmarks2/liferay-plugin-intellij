package de.dm.portlet;

import org.osgi.service.component.annotations.Component;
import javax.portlet.Portlet;
import de.dm.util.MyPortletKeys;

@Component(
    property = {
        "javax.portlet.display-name=My Portlet",
        "javax.portlet.name=" + MyPortletKeys.MY_PORTLET_KEY
    },
    service = Portlet.class
)
public class MyReferenceConstantImportPortlet implements Portlet {

}