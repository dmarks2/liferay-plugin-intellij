package de.dm.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import org.osgi.service.component.annotations.Component;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

@Component(
    immediate = true,
    property = {
        "javax.portlet.name=de_dm_portlet_MyRenderPortlet",
        "mvc.command.name=/my/render"
    },
    service = com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand.class
)
public class MyMVCRenderCommand implements MVCRenderCommand {

    public String render(javax.portlet.RenderRequest renderRequest, javax.portlet.RenderResponse renderResponse) {
        return "/html/render.jsp";
    }

}