import org.osgi.service.component.annotations.Component;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;

@Component(
    service = MVCActionCommand.class
)
public class MyComponent implements MVCActionCommand {
}