package de.dm.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import org.osgi.service.component.annotations.Component;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import de.dm.portlet.PortletKeys;

@Component(
    immediate = true,
    property = {
        "javax.portlet.name=" + PortletKeys.MY_PORTLET_NAME,
        "mvc.command.name=/my/render"
    },
    service = com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand.class
)
public class MyMVCRenderCommand implements MVCRenderCommand {

    public String render(RenderRequest renderRequest, RenderResponse renderResponse) {
        return "/html/render.jsp";
    }

}