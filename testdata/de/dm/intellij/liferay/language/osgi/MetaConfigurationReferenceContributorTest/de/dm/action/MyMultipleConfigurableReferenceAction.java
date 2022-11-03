package de.dm.action;

import org.osgi.service.component.annotations.Component;

@Component(
        configurationPid = {
                "foo",
                "de.dm.configuration.MyC<caret>onfiguration"
        }
)
public class MyMultipleConfigurableReferenceAction {

}