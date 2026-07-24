import com.liferay.portal.kernel.theme.ThemeDisplay;

public class MyPortletInstanceConfiguration {
	public static void main(String[] args) {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		<warning descr="PortletDisplay.getPortletInstanceConfiguration() has been removed. Use ConfigurationProviderUtil.getPortletInstanceConfiguration(configClass, themeDisplay) instead. (see LPS-198379)">themeDisplay.getPortletDisplay().getPortletInstanceConfiguration(MyConfiguration.class)</warning>;
	}
}
