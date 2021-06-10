import org.osgi.service.component.annotations.Reference;

public class ClassWithoutComponentMethod {

    @Reference
    public void <error descr="Reference annotation on a method where the class ClassWithoutComponentMethod is not annotated with @Component does not work.">setObject</error>(MyObject object) {

    }

}