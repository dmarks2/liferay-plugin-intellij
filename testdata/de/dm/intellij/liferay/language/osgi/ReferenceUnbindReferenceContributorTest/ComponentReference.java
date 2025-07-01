import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class ComponentReference {

	@Reference(
		unbind = "<caret>"
	)
	private void setObject(Object object) {
		_object = object;
	}

	private void unsetObject(Object object) {
		_object = null;
	}

	private Object _object;

}
