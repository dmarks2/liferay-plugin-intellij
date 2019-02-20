package de.dm.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import org.osgi.service.component.annotations.Component;
import de.dm.action.ActionKeys;

@Component(
    immediate = true,
    property = {
        "javax.portlet.name=de_dm_portlet_MyPortletName",
        "mvc.command.name=" + ActionKeys.MY_ACTION_KEY
    },
    service = MVCActionCommand.class
)
public class MyActionKeyMVCActionCommand implements MVCActionCommand {

}