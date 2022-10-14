import org.osgi.service.component.annotations.Component;
import javax.portlet.Portlet;

@Component(
        property = {
                "javax.portlet.<caret>"
        },
        service = Portlet.class
)
public class MyComponentLookup {
}