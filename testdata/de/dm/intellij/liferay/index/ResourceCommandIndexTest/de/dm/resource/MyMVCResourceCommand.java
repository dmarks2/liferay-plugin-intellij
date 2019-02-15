package de.dm.resource;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import org.osgi.service.component.annotations.Component;

@Component(
    immediate = true,
    property = {
        "javax.portlet.name=de_dm_portlet_MyPortletName",
        "mvc.command.name=/my/resource"
    },
    service = MVCResourceCommand.class
)
public class MyMVCResourceCommand implements MVCResourceCommand {

}