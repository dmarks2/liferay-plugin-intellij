import com.liferay.portal.kernel.service.AddressLocalServiceUtil;

public class MyStaticMethodDeprecation {

	public void foo() {
		<warning descr="Adress column typeId has been renamed to listTypeId. (see LPS-162437)">AddressLocalServiceUtil.<error descr="Cannot resolve method 'getTypeAddresses' in 'AddressLocalServiceUtil'">getTypeAddresses</error>()</warning>;
	}

}