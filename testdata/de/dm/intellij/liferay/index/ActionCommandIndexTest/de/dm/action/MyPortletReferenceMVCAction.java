package de.dm.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import org.osgi.service.component.annotations.Component;
import de.dm.portlet.PortletKeys;

@Component(
    immediate = true,
    property = {
        "javax.portlet.name=" + PortletKeys.MY_PORTLET_KEY,
        "mvc.command.name=/my/portletref/action"
    },
    service = MVCActionCommand.class
)
public class MyPortletReferenceMVCAction implements MVCActionCommand {

}