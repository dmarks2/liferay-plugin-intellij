import com.liferay.portal.kernel.util.<error descr="Cannot resolve symbol 'HttpUtil'">HttpUtil</error>

def foo = <warning descr="Methods not depending on org.apache.http have been moved from HttpUtil to HttpComponentsUtil (see LPS-150185)">HttpUtil.addParameter("http://localhost", "foo", "bar")</warning>
