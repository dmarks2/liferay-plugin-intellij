import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class ClassWithComponent {

    @Reference
    private MyObject object;

}