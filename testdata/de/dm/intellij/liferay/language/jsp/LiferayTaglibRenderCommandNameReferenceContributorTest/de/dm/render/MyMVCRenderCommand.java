package de.dm.render;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import org.osgi.service.component.annotations.Component;

@Component(
    immediate = true,
    property = {
        "javax.portlet.name=de_dm_portlet_MyPortletName",
        "mvc.command.name=/my/render"
    },
    service = MVCRenderCommand.class
)
public class MyMVCAction implements MVCRenderCommand {

}