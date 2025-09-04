import org.osgi.service.component.annotations.Component;

@Component(
		property = {"osgi.command.scope=foo", "osgi.command.function=ba<caret>r"},
		service = Object.class
)
public class MyValidCommand {
	public void bar()  {
	}

	public void upgrade() {
	}

	public void run() {

	}
}
