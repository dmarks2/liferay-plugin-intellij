import com.liferay.portal.kernel.util.HttpUtil;
public class MyStaticPackageCall {

	public static void main(String[] args) {
		String foo = com.liferay.portal.kernel.util.HttpComponentsUtil.addParameter("http://localhost", "foo", "bar");
	}
}