import com.liferay.portal.kernel.model.Phone;
public class MyPhoneUtil {

	public void foo(Phone phone) {
		System.out.println(phone.setListTypeId(4711));
	}
}