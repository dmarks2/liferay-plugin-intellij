import com.liferay.portal.kernel.service.AddressLocalServiceUtil;

public class MyStaticMethodDeprecation {

	public void foo() {
		AddressLocalServiceUtil.getListTypeAddresses();
	}

}