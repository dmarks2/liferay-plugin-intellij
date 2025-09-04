import org.osgi.service.component.annotations.Component;

@Component(
		property = {"osgi.command.scope=foo", "osgi.command.function=bar"},
		service = Object.class
)
public class MyRenameCommand {
	public void ba<caret>r()  {
	}

	public void upgrade() {
	}

	public void run() {

	}
}
