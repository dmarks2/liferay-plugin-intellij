package de.dm.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;

import org.osgi.service.component.annotations.Component;

import de.dm.action.ActionKeys;
import de.dm.portlet.PortletKeys;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PortletKeys.MY_PORTLET_NAME,
                "mvc.command.name=/my/action"
        },
        service = MVCActionCommand.class
)
public class MyActionKeyMVCActionCommand implements MVCActionCommand {

}