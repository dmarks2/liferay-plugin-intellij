import com.liferay.portal.kernel.model.Phone;
public class MyPhoneUtil {

	public void foo(Phone phone) {
		System.out.println(<warning descr="Phone column typeId has been renamed to listTypeId. (see LPS-162450)">phone.<error descr="Cannot resolve method 'setTypeId' in 'Phone'">setTypeId</error>(4711)</warning>);
	}
}