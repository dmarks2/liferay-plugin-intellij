import org.osgi.service.component.annotations.Component;

@Component(
		property = {"osgi.command.scope=foo", "osgi.command.function=bar"},
		service = Object.class
)
public class MyOsgiCommand {
	public void bar()  {
	}
}
