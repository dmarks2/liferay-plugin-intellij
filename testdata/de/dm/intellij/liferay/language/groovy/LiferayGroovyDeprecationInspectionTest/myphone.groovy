import com.liferay.portal.kernel.model.Phone

phone = new Phone()

long id = <warning descr="Phone column typeId has been renamed to listTypeId. (see LPS-162450)">phone.getTypeId()</warning>