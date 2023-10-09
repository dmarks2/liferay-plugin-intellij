import com.liferay.portal.kernel.util.<error descr="Cannot resolve symbol 'Http'">Http</error>;
public class MyInstancePackageCall {

	public static void apply(<error descr="Cannot resolve symbol 'Http'">Http</error> http) {
		String foo = <warning descr="Methods not depending on org.apache.http have been moved from HttpUtil to HttpComponentsUtil (see LPS-150185)">http.<error descr="Cannot resolve method 'addParameter(String, String, String)'">addParameter</error>("http://localhost", "foo", "bar")</warning>;
	}
}