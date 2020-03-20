import org.osgi.service.component.annotations.Component;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.search.IndexerPostProcessor;

@Component(
    service = {MVCActionCommand.class, IndexerPostProcessor.class}
)
public class <warning descr="Class MultiServiceComponent is not assignable to specified service com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand"><warning descr="Class MultiServiceComponent is not assignable to specified service com.liferay.portal.kernel.search.IndexerPostProcessor">MultiServiceComponent</warning></warning> {
}
