import com.liferay.portal.kernel.util.Http;
public class MyInstancePackageCall {

	public static void apply(Http http) {
		String foo = com.liferay.portal.kernel.util.HttpComponentsUtil.addParameter("http://localhost", "foo", "bar");
	}
}