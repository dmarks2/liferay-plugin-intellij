import org.osgi.service.component.annotations.Component;
import javax.portlet.Portlet;

@Component(
        property = {
                "javax.portlet.info.title=Foo"
        },
        service = Portlet.class
)
public class MyComponent {
}