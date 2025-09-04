import org.osgi.service.component.annotations.Component;

@Component(
		property = {"osgi.command.scope=foo", "osgi.command.function=<caret>"},
		service = Object.class
)
public class MyOsgiCommand {
	public void bar()  {
	}

	public void upgrade() {
	}

	public void upgrade(String param) {

	}

	public void run() {

	}
}
