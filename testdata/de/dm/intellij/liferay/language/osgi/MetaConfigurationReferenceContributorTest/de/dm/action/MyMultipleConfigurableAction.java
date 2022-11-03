package de.dm.action;

import org.osgi.service.component.annotations.Component;

@Component(
        configurationPid = {
                "foo",
                "<caret>"
        }
)
public class MyMultipleConfigurableAction {

}