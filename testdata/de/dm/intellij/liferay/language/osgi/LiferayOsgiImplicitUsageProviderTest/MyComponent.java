import org.osgi.service.component.annotations.Reference;

public class MyComponent {

    @Reference
    private org.osgi.service.component.annotations.ConfigurationPolicy <warning descr="Private field 'foo' is assigned but never accessed">foo</warning>;

}