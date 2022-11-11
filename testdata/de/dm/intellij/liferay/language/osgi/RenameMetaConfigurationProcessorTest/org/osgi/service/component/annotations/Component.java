package org.osgi.service.component.annotations;

public @interface Component {
    String	NAME	= "$";

    String[] configurationPid() default NAME;
}