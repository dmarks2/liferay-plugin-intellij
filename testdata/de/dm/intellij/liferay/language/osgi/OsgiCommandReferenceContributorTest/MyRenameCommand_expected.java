import org.osgi.service.component.annotations.Component;

@Component(
		property = {"osgi.command.scope=foo", "osgi.command.function=renamed"},
		service = Object.class
)
public class MyRenameCommand {
	public void renamed()  {
	}

	public void upgrade() {
	}

	public void run() {

	}
}
