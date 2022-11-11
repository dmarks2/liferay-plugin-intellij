package aQute.bnd.annotation.metatype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface Meta {
    String NULL = "§NULL§";

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface OCD {
        String id() default "§NULL§";
    }

}