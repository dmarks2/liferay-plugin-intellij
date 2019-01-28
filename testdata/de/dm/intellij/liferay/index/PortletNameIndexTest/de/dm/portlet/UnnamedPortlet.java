package de.dm.portlet;

import org.osgi.service.component.annotations.Component;
import javax.portlet.Portlet;

@Component(
    service = Portlet.class
)
public class UnnamedPortlet implements Portlet {
}