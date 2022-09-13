import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

public abstract class AbstractClassWithoutComponent {

    @Reference
    private MyObject object;

}