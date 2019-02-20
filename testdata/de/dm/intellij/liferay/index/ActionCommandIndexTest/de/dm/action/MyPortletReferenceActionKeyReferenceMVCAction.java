package de.dm.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import org.osgi.service.component.annotations.Component;
import de.dm.action.ActionKeys;
import de.dm.portlet.PortletKeys;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PortletKeys.MY_SECOND_PORTLET_KEY,
                "mvc.command.name=" + ActionKeys.MY_SECOND_ACTION_KEY
        },
        service = MVCActionCommand.class
)
public class MyReferenceMVCAction implements MVCActionCommand {

}