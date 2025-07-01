package org.osgi.service.component.annotations;

public @interface Reference {

	String unbind() default "";

}
