import org.osgi.service.component.annotations.Reference;

public class ClassWithoutComponent {

    @Reference
    private MyObject <error descr="Reference annotation on a field where the class ClassWithoutComponent is not annotated with @Component does not work.">object</error>;

}