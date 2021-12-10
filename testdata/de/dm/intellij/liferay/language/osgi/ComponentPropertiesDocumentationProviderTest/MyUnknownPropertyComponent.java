import org.osgi.service.component.annotations.Component;
import javax.portlet.Portlet;

@Component(
        property = {
                "unknown.property=Foo"
        },
        service = Portlet.class
)
public class MyUnknownPropertyComponent {
}