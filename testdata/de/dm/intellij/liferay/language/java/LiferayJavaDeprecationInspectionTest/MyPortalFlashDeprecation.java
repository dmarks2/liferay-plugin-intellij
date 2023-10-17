import com.liferay.portal.kernel.theme.ThemeDisplay;

public class MyPortalFlashDeprecation {

	public static void main(String[] args) {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		String flashPath = <warning descr="Flash support has been removed completely. (see LPS-121733)">themeDisplay.<error descr="Cannot resolve method 'getPathFlash' in 'ThemeDisplay'">getPathFlash</error>()</warning>;
	}
}