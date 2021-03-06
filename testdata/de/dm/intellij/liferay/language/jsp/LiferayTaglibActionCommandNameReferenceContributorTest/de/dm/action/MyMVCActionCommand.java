package de.dm.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import org.osgi.service.component.annotations.Component;

@Component(
    immediate = true,
    property = {
        "javax.portlet.name=de_dm_portlet_MyPortletName",
        "mvc.command.name=/my/action"
    },
    service = MVCActionCommand.class
)
public class MyMVCActionCommand implements MVCActionCommand {

}