import org.osgi.service.component.annotations.Component;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;

@Component(
    service = MVCActionCommand.class
)
public class ValidInheritedComponent extends BaseMVCActionCommand {
}