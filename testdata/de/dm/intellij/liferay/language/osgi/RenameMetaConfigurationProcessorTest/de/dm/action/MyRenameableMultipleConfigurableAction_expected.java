package de.dm.action;

import org.osgi.service.component.annotations.Component;

@Component(
        configurationPid = {
                "de.dm.configuration.UpdatedConfiguration",
                "de.dm.configuration.AnotherConfiguration"
        }
)
public class MyRenameableMultipleConfigurableAction {

}