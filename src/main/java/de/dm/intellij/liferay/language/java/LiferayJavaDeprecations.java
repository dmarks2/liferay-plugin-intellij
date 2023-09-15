package de.dm.intellij.liferay.language.java;

import com.intellij.openapi.util.io.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;

public class LiferayJavaDeprecations {

	public record JavaImportDeprecation(float majorLiferayVersion, String message, String ticket, String[] importStatements, String[] newImportStatements) {
		public JavaImportDeprecation(float majorLiferayVersion, String message, String ticket, String filename) {
			this(majorLiferayVersion, message, ticket, getImportStatements(filename).getKey(), getImportStatements(filename).getValue());
		}
	}

	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_50156_UTIL_BRIDGES = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.0f,
			"The classes from package com.liferay.util.bridges.mvc in util-bridges.jar were moved to a new package com.liferay.portal.kernel.portlet.bridges.mvc in portal-service.jar.",
			"LPS-50156",
			new String[] {"com.liferay.util.bridges.mvc.ActionCommand", "com.liferay.util.bridges.mvc.BaseActionCommand", "com.liferay.util.bridges.mvc.MVCPortlet"},
			new String[] {"com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand", "com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand", "com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet"});
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_53113_USER_SCREEN_NAME_EXCEPTION = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.0f,
			"Replaced the ReservedUserScreenNameException with UserScreenNameException.MustNotBeReserved in UserLocalService.",
			"LPS-53113",
			new String[] {"com.liferay.portal.ReservedUserScreenNameException"},
			new String[] {"com.liferay.portal.kernel.exception.UserScreenNameException"});
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_53279_USER_EMAIL_ADDRESS_EXCEPTION = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.0f,
			"Replaced the ReservedUserEmailAddressException with UserEmailAddressException Inner Classes in User Services.",
			"LPS-53279",
			new String[] {"com.liferay.portal.ReservedUserEmailAddressException"},
			new String[] {"com.liferay.portal.kernel.exception.UserEmailAddressException"});
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_53487_USER_ID_EXCEPTION = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.0f,
			"The ReservedUserIdException has been deprecated and replaced with UserIdException.MustNotBeReserved.",
			"LPS-53487",
			new String[] {"com.liferay.portal.ReservedUserIdException"},
			new String[] {"com.liferay.portal.kernel.exception.UserIdException"});
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_55364_CONTACT_NAME_EXCEPTION = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.0f,
			"The use of classes ContactFirstNameException, ContactFullNameException, and ContactLastNameException has been moved to inner classes in a new class called ContactNameException.",
			"LPS-55364",
			new String[] {"com.liferay.portal.ContactFirstNameException", "com.liferay.portal.ContactFullNameException", "com.liferay.portal.ContactLastNameException"},
			new String[] {"com.liferay.portal.kernel.exception.ContactNameException", "com.liferay.portal.kernel.exception.ContactNameException", "com.liferay.portal.kernel.exception.ContactNameException"});
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_61952_PORTAL_KERNEL = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.0f,
			"The portal-kernel and portal-impl folders have many packages with the same name. Therefore, all of these packages are affected by the split package problem.",
			"LPS-61952",
			"/com/liferay/java/LPS_61952_Imports.csv");
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_63205_USER_EXPORTER = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.0f,
			"User Operation and Importer/Exporter Classes and Utilities Moved or Removed.",
			"LPS-63205",
			new String[] {"com.liferay.portal.kernel.security.exportimport.UserImporter", "com.liferay.portal.kernel.security.exportimport.UserExporter", "com.liferay.portal.kernel.security.exportimport.UserOperation", "com.liferay.portal.kernel.security.exportimport.UserImporterUtil", "com.liferay.portal.kernel.security.exportimport.UserExporterUtil"},
			new String[] {"com.liferay.portal.security.exportimport.UserImporter", "com.liferay.portal.security.exportimport.UserExporter", "com.liferay.portal.security.exportimport.UserOperation"});

	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_NONE_MODULARIZATION_70 = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.0f,
			"Many classes from former portal-service.jar from Liferay Portal 6.x have been moved into application and framework API modules.",
			"",
			"/com/liferay/java/Modularization_Imports_70.csv");

	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_NONE_MODULARIZATION_71 = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.1f,
			"Many classes from former portal-service.jar from Liferay Portal 6.x have been moved into application and framework API modules.",
			"",
			"/com/liferay/java/Modularization_Imports_71.csv");

	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_88912_INVOKABLE_SERVICE = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.2f,
			"The InvokableService and InvokableLocalService interfaces in package com.liferay.portal.kernel.service were removed.",
			"LPS-88912",
			new String[] {"com.liferay.portal.kernel.service.InvokableService", "com.liferay.portal.kernel.service.InvokableLocalService"},
			new String[0] );
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_88913_SERVICE_LOADER_CONDITION = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.2f,
			"The interface ServiceLoaderCondition and its implementation DefaultServiceLoaderCondition in package com.liferay.portal.kernel.util were removed.",
			"LPS-88913",
			new String[] {"com.liferay.portal.kernel.util.DefaultServiceLoaderCondition", "com.liferay.portal.kernel.util.ServiceLoaderCondition"},
			new String[0] );
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_88869_TERMS_OF_USE = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.2f,
			"The TermsOfUseContentProvider interface's package changed.",
			"LPS-88869",
			new String[] {"com.liferay.portal.kernel.util.TermsOfUseContentProvider", "com.liferay.portal.kernel.util.TermsOfUseContentProviderRegistryUtil"},
			new String[] {"com.liferay.portal.kernel.terms.of.use.TermsOfUseContentProvider"});

	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_88870_CONVERTER = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.2f,
			"The interface com.liferay.portal.kernel.util.Converter and its implementation com.liferay.portal.spring.hibernate.HibernateConfigurationConverter were removed.",
			"LPS-88870",
			new String[] {"com.liferay.portal.spring.hibernate.HibernateConfigurationConverter", "com.liferay.portal.kernel.util.Converter"},
			new String[0] );
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_89223_OSGI_SERVICE_UTILS = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.2f,
			"The com.liferay.portal.osgi.util.test.OSGiServiceUtil class was removed. Also, the following interfaces were removed from the com.liferay.portal.kernel.util package: UnsafeConsumer, UnsafeFunction, UnsafeRunnable.",
			"LPS-89223",
			new String[] {"com.liferay.portal.osgi.util.test.OSGiServiceUtil", "com.liferay.portal.kernel.util.UnsafeConsumer", "com.liferay.portal.kernel.util.UnsafeFunction", "com.liferay.portal.kernel.util.UnsafeRunnable"},
			new String[0] );
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_89139_PREDICATE_FILTER = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.2f,
			"The interface com.liferay.portal.kernel.util.PredicateFilter was removed and replaced with java.util.function.Predicate.",
			"LPS-89139",
			new String[] {"com.liferay.portal.kernel.util.PredicateFilter", "com.liferay.portal.kernel.util.AggregatePredicateFilter",
					"com.liferay.portal.kernel.util.PrefixPredicateFilter",
					"com.liferay.portal.kernel.portlet.JavaScriptPortletResourcePredicateFilter",
					"com.liferay.dynamic.data.mapping.form.values.query.internal.model.DDMFormFieldValuePredicateFilter"},
			new String[] {"java.util.function.Predicate"} );
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_88911_FUNCTION_SUPPLIER = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.2f,
			"The Function and Supplier interfaces in package com.liferay.portal.kernel.util were removed. Their usages were replaced with java.util.function.Function and java.util.function.Supplier.",
			"LPS-88911",
			new String[] {"com.liferay.portal.kernel.util.Function", "com.liferay.portal.kernel.util.Supplier"},
			new String[] {"java.util.function.Function", "java.util.function.Supplier"} );

	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_NONE_MODULARIZATION_72 = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.2f,
			"Many classes from former portal-service.jar from Liferay Portal 6.x have been moved into application and framework API modules.",
			"",
			"/com/liferay/java/Modularization_Imports_72.csv");



	private static AbstractMap.SimpleImmutableEntry<String[], String[]> getImportStatements(String filename) {
		String[] importStatements = new String[0];
		String[] newImportStatements = new String[0];

		try (InputStream inputStream = JavaImportDeprecation.class.getResourceAsStream(filename)) {
			if (inputStream != null) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				FileUtil.copy(inputStream, baos);

				String text = baos.toString(StandardCharsets.UTF_8);

				String[] lines = text.split("\n");

				importStatements = new String[lines.length];
				newImportStatements = new String[lines.length];

				for (int i = 0; i < lines.length; i++) {
					if (lines[i].contains(",")) {
						String[] values = lines[i].split(",");

						importStatements[i] = values[0];
						newImportStatements[i] = values[1];
					} else if (lines[i].contains("=")) {
						String[] values = lines[i].split("=");

						importStatements[i] = values[0];
						newImportStatements[i] = values[1];
					}
				}
			}
		} catch (Exception exception) {
			//nothing
		}

		return new AbstractMap.SimpleImmutableEntry<>(importStatements, newImportStatements);
	}

}
