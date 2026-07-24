import com.liferay.portal.kernel.theme.ThemeDisplay;

public class MyPortletInstanceConfiguration {
	public static void main(String[] args) {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil.getPortletInstanceConfiguration(MyConfiguration.class, themeDisplay.getPortletDisplay().getThemeDisplay());
	}
}
