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

	public record JavaMethodCallDeprecation(float majorLiferayVersion, String message, String ticket, String[] methodSignatures, String[] newNames) {}

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
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_100144_ASSET_TAGS_SELECTOR_TAG = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.3f,
			"Removed java class AssetTagsSelectorTag.",
			"LPS-100144",
			new String[] {"com.liferay.asset.taglib.servlet.taglib.soy.AssetTagsSelectorTag"},
			new String[0] );

	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_106167_PORTAL_KERNEL = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.3f,
			"Deprecated portal-kernel classes have been moved to Petra libraries.",
			"LPS-106167",
			new String[] {"com.liferay.portal.kernel.util.StringPool", "com.liferay.portal.kernel.util.HashUtil", "com.liferay.portal.kernel.upload.UploadHandler", "com.liferay.portal.kernel.upload.BaseUploadHandler"},
			new String[] {"com.liferay.petra.string.StringPool", "com.liferay.petra.lang.HashUtil"});
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_89065_ASSET_CATEGORIES_TABLE = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.4f,
			"Removed the AssetEntries_AssetCategories Table and Corresponding Code.",
			"LPS-89065",
			new String[] {"com.liferay.asset.kernel.model.AssetEntries_AssetCategoriesTable"},
			new String[0] );
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_122955_SOY_PORTLET = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.4f,
			"The `SoyPortlet` class has been removed. It used to implement a portlet whose views were backed by Closure Templates (Soy).",
			"LPS-122955",
			new String[] {"com.liferay.flags.taglib.internal.frontend.js.loader.modules.extender.npm.NPMResolverProvider",
					"com.liferay.flags.taglib.servlet.taglib.soy.FlagsTag",
					"com.liferay.portal.portlet.bridge.soy.SoyPortletRegister",
					"com.liferay.portal.portlet.bridge.soy.internal.SoyPortlet",
					"com.liferay.portal.portlet.bridge.soy.internal.SoyPortletHelper",
					"com.liferay.portal.portlet.bridge.soy.internal.SoyPortletRegisterTracker",
					"com.liferay.portal.portlet.bridge.soy.internal.SoyPortletRequestFactory",
					"com.liferay.portal.portlet.bridge.soy.internal.util.SoyTemplateResourceFactoryUtil",
					"com.liferay.portal.portlet.bridge.soy.internal.util.SoyTemplateResourcesProviderUtil"},
			new String[0] );
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_124898_OPEN_ID_CONNECT_SERVICE_HANDLER = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.4f,
			"The OpenIdConnectServiceHandler interface has been removed and replaced by the OpenIdConnectAuthenticationHandler interface.",
			"LPS-124898",
			new String[] {"com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceHandler"},
			new String[] {"portal.security.sso.openid.connect.OpenIdConnectAuthenticationHandler"});
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_122956_SOY = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.4f,
			"Some modules and the classes they exported to allow Soy rendering server-side have been removed.",
			"LPS-122956",
			new String[] {"com.liferay.flags.taglib.servlet.taglib.soy.FlagsTag",
					"com.liferay.frontend.taglib.clay.servlet.taglib.soy.AlertTag",
					"com.liferay.frontend.taglib.clay.servlet.taglib.soy.BadgeTag",
					"com.liferay.frontend.taglib.clay.servlet.taglib.soy.ButtonTag",
					"com.liferay.frontend.taglib.clay.servlet.taglib.soy.CheckboxTag",
					"com.liferay.frontend.taglib.clay.servlet.taglib.soy.DropdownActionsTag",
					"com.liferay.frontend.taglib.clay.servlet.taglib.soy.DropdownMenuTag",
					"com.liferay.frontend.taglib.clay.servlet.taglib.soy.FileCardTag",
					"com.liferay.frontend.taglib.clay.servlet.taglib.soy.HorizontalCardTag",
					"com.liferay.frontend.taglib.clay.servlet.taglib.soy.IconTag",
					"com.liferay.frontend.taglib.clay.servlet.taglib.soy.ImageCardTag",
					"com.liferay.frontend.taglib.clay.servlet.taglib.soy.LabelTag",
					"com.liferay.frontend.taglib.clay.servlet.taglib.soy.LinkTag",
					"com.liferay.frontend.taglib.clay.servlet.taglib.soy.ManagementToolbarTag",
					"com.liferay.frontend.taglib.clay.servlet.taglib.soy.MultiSelectTag",
					"com.liferay.frontend.taglib.clay.servlet.taglib.soy.NavigationBarTag",
					"com.liferay.frontend.taglib.clay.servlet.taglib.soy.ProgressBarTag",
					"com.liferay.frontend.taglib.clay.servlet.taglib.soy.RadioTag",
					"com.liferay.frontend.taglib.clay.servlet.taglib.soy.SelectTag",
					"com.liferay.frontend.taglib.clay.servlet.taglib.soy.StickerTag",
					"com.liferay.frontend.taglib.clay.servlet.taglib.soy.StripeTag",
					"com.liferay.frontend.taglib.clay.servlet.taglib.soy.TableTag",
					"com.liferay.frontend.taglib.clay.servlet.taglib.soy.UserCardTag",
					"com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCardTag",
					"com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseClayTag",
					"com.liferay.frontend.taglib.soy.servlet.taglib.ComponentRendererTag",
					"com.liferay.frontend.taglib.soy.servlet.taglib.TemplateRendererTag",
					"com.liferay.portal.template.soy.TemplateResource",
					"com.liferay.portal.template.soy.SoyTemplateResourceFactory",
					"com.liferay.portal.template.soy.constants.SoyTemplateConstants",
					"com.liferay.portal.template.soy.data.SoyDataFactory",
					"com.liferay.portal.template.soy.data.SoyRawData",
					"com.liferay.portal.template.soy.util.SoyContext",
					"com.liferay.portal.template.soy.util.SoyContextFactory",
					"com.liferay.portal.template.soy.util.SoyContextFactoryUtil",
					"com.liferay.portal.template.soy.util.SoyRawData",
					"com.liferay.portal.template.soy.util.SoyTemplateResourcesProvider",
					"com.liferay.portal.template.soy.context.contributor.internal.template.ThemeDisplaySoyTemplateContextContributor",
					"com.liferay.portal.template.soy.internal.SoyContextImpl",
					"com.liferay.portal.template.soy.internal.BaseTemplateManager",
					"com.liferay.portal.template.soy.internal.SoyManagerCleaner",
					"com.liferay.portal.template.soy.internal.SoyMsgBundleBridge",
					"com.liferay.portal.template.soy.internal.SoyProviderCapabilityBundleRegister",
					"com.liferay.portal.template.soy.internal.SoyTemplate",
					"com.liferay.portal.template.soy.internal.SoyTemplateBundleResourceParser",
					"com.liferay.portal.template.soy.internal.SoyTemplateContextHelper",
					"com.liferay.portal.template.soy.internal.SoyTemplateRecord",
					"com.liferay.portal.template.soy.internal.SoyTemplateResourceBundleTrackerCustomizer",
					"com.liferay.portal.template.soy.internal.SoyTemplateResourceCache",
					"com.liferay.portal.template.soy.internal.SoyTemplateResourceFactoryImpl",
					"com.liferay.portal.template.soy.internal.SoyTemplateResourceLoader",
					"com.liferay.portal.template.soy.internal.SoyTofuCacheBag",
					"com.liferay.portal.template.soy.internal.SoyTofuCacheHandler",
					"com.liferay.portal.template.soy.internal.activator.PortalTemplateSoyImplBundleActivator",
					"com.liferay.portal.template.soy.internal.configuration.SoyTemplateEngineConfiguration",
					"com.liferay.portal.template.soy.internal.data.SoyDataFactoryImpl",
					"com.liferay.portal.template.soy.internal.data.SoyDataFactoryProvider",
					"com.liferay.portal.template.soy.internal.data.SoyHTMLDataImpl",
					"com.liferay.portal.template.soy.internal.data.SoyRawDataImpl",
					"com.liferay.portal.template.soy.internal.upgrade.PortalTemplateSoyImplUpgrade",
					"com.liferay.portal.template.soy.internal.util.SoyContextFactoryImpl",
					"com.liferay.portal.template.soy.internal.util.SoyTemplateResourcesCollectorUtil",
					"com.liferay.portal.template.soy.internal.util.SoyTemplateResourcesProviderImpl",
					"com.liferay.portal.template.soy.internal.util.SoyTemplateUtil",
					"com.liferay.frontend.taglib.clay.internal.SoyDataFactoryProvider"
			},
			new String[0] );
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_133200_OPEN_ID_CONNECT_SERVICE_HANDLER = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.4f,
			"The StringBundler class in package com.liferay.portal.kernel.util has been deprecated.",
			"LPS-133200",
			new String[] {"com.liferay.portal.kernel.util.StringBundler"},
			new String[] {"com.liferay.petra.string.StringBundler"} );
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_147929_PETRA_JSON_VALIDATOR = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.4f,
			"petra-json-validator has been moved to portal-json-validator.",
			"LPS-147929",
			new String[] {"com.liferay.petra.json.validator.JSONValidator", "com.liferay.petra.json.validator.JSONValidatorException" },
			new String[] {"com.liferay.portal.json.validator.JSONValidator", "com.liferay.portal.json.validator.JSONValidatorException" } );
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_148013_PETRA_JSON_WEB_SERVICE_CLIENT = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.4f,
			"petra-json-web-service-client has been moved to portal-json-web-service-client.",
			"LPS-148013",
			new String[] {
					"com.liferay.petra.json.web.service.client.BaseJSONWebServiceClientImpl",
					"com.liferay.petra.json.web.service.client.JSONWebServiceClient",
					"com.liferay.petra.json.web.service.client.JSONWebServiceClientFactory",
					"com.liferay.petra.json.web.service.client.JSONWebServiceException",
					"com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException",
					"com.liferay.petra.json.web.service.client.JSONWebServiceSerializeException",
					"com.liferay.petra.json.web.service.client.JSONWebServiceTransportException"
			},
			new String[] {
					"com.liferay.portal.json.web.service.client.BaseJSONWebServiceClientImpl",
					"com.liferay.portal.json.web.service.client.JSONWebServiceClient",
					"com.liferay.portal.json.web.service.client.JSONWebServiceClientFactory",
					"com.liferay.portal.json.web.service.client.JSONWebServiceException",
					"com.liferay.portal.json.web.service.client.JSONWebServiceInvocationException",
					"com.liferay.portal.json.web.service.client.JSONWebServiceSerializeException",
					"com.liferay.portal.json.web.service.client.JSONWebServiceTransportException"
			} );
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_148493_PETRA_LOG4J = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.4f,
			"petra-log4j has been moved to portal-log4j.",
			"LPS-148493",
			new String[] {"com.liferay.petra.log4j.Levels", "com.liferay.petra.log4j.Log4JUtil" },
			new String[] {"com.liferay.portal.log4j.Levels", "com.liferay.portal.log4j.Log4JUtil" } );
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_149147_PETRA_CONTENT = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.4f,
			"petra-content has been removed",
			"LPS-149147",
			new String[] {"com.liferay.petra.content.ContentUtil" },
			new String[0] );
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_149460_PETRA_MODEL_ADAPTER = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.4f,
			"petra-model-adapter has been moved to portlet-model-adapter",
			"LPS-149460",
			new String[] {"com.liferay.petra.model.adapter.util.ModelAdapterUtil" },
			new String[] {"com.liferay.portal.model.adapter.util.ModelAdapterUtil"});
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_150808_PETRA_HTTP_INVOKER = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.4f,
			"petra-http-invoker has been moved to portlet-http-invoker",
			"LPS-150808",
			new String[] {"com.liferay.petra.http.invoker.HttpInvoker" },
			new String[] {"com.liferay.portal.http.invoker.HttpInvoker"});
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_151723_PETRA_ENCRYPTOR = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.4f,
			"petra-encryptor has been moved to portlet-encryptor",
			"LPS-151723",
			new String[] {"com.liferay.petra.encryptor.Encryptor" },
			new String[] {"com.liferay.portal.encryptor.Encryptor"});
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_151632_PETRA_PORTLET_URL_BUILDER = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.4f,
			"petra-portlet-url-builder has been moved to portal-kernel.",
			"LPS-151632",
			new String[] {"com.liferay.petra.portlet.url.builder.ActionURLBuilder", "com.liferay.petra.portlet.url.builder.PortletURLBuilder", "com.liferay.petra.portlet.url.builder.RenderURLBuilder", "com.liferay.petra.portlet.url.builder.ResourceURLBuilder" },
			new String[] {"com.liferay.portal.kernel.portlet.url.builder.ActionURLBuilder", "com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder", "com.liferay.portal.kernel.portlet.url.builder.RenderURLBuilder", "com.liferay.portal.kernel.portlet.url.builder.ResourceURLBuilder" } );
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_152089_TRASH_UTIL = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.4f,
			"TrashUtil has been removed from portal-kernel. Use TrashHelper Components instead.",
			"LPS-152089",
			new String[] {
					"com.liferay.trash.kernel.model.TrashEntryConstants",
					"com.liferay.trash.kernel.util.comparator.EntryCreateDateComparator",
					"com.liferay.trash.kernel.util.comparator.EntryTypeComparator",
					"com.liferay.trash.kernel.util.comparator.EntryUserNameComparator",
					"com.liferay.trash.kernel.util.TrashUtil",
					"com.liferay.trash.kernel.util.Trash",
					"com.liferay.portlet.trash.util.TrashImpl"
			},
			new String[] {
					"com.liferay.trash.constants.TrashEntryConstants",
					"",
					"",
					"",
					"com.liferay.portal.kernel.trash.helper.TrashHelper",
					"",
					""
			});

	public static LiferayJavaDeprecations.JavaMethodCallDeprecation LPS_150185_HTTP_COMPONENTS_UTIL = new LiferayJavaDeprecations.JavaMethodCallDeprecation(
			7.4f,
			"Methods not depending on org.apache.http have been moved from HttpUtil to HttpComponentsUtil",
			"LPS-150185",
			new String[] {
					"com.liferay.portal.kernel.util.HttpUtil.addParameter()",
					"com.liferay.portal.kernel.util.HttpUtil.decodePath()",
					"com.liferay.portal.kernel.util.HttpUtil.decodeURL()",
					"com.liferay.portal.kernel.util.HttpUtil.encodeParameters()",
					"com.liferay.portal.kernel.util.HttpUtil.encodePath()",
					"com.liferay.portal.kernel.util.HttpUtil.fixPath()",
					"com.liferay.portal.kernel.util.HttpUtil.getCompleteURL()",
					"com.liferay.portal.kernel.util.HttpUtil.getDomain()",
					"com.liferay.portal.kernel.util.HttpUtil.getIpAddress()",
					"com.liferay.portal.kernel.util.HttpUtil.getParameter()",
					"com.liferay.portal.kernel.util.HttpUtil.getParameterMap()",
					"com.liferay.portal.kernel.util.HttpUtil.getPath()",
					"com.liferay.portal.kernel.util.HttpUtil.getProtocol()",
					"com.liferay.portal.kernel.util.HttpUtil.getQueryString()",
					"com.liferay.portal.kernel.util.HttpUtil.getRequestURL()",
					"com.liferay.portal.kernel.util.HttpUtil.getURI()",
					"com.liferay.portal.kernel.util.HttpUtil.hasDomain()",
					"com.liferay.portal.kernel.util.HttpUtil.hasProtocol()",
					"com.liferay.portal.kernel.util.HttpUtil.isForwarded()",
					"com.liferay.portal.kernel.util.HttpUtil.isSecure()",
					"com.liferay.portal.kernel.util.HttpUtil.normalizePath()",
					"com.liferay.portal.kernel.util.HttpUtil.parameterMapFromString()",
					"com.liferay.portal.kernel.util.HttpUtil.parameterMapToString()",
					"com.liferay.portal.kernel.util.HttpUtil.protocolize()",
					"com.liferay.portal.kernel.util.HttpUtil.removeDomain()",
					"com.liferay.portal.kernel.util.HttpUtil.removeParameter()",
					"com.liferay.portal.kernel.util.HttpUtil.removePathParameters()",
					"com.liferay.portal.kernel.util.HttpUtil.removeProtocol()",
					"com.liferay.portal.kernel.util.HttpUtil.sanitizeHeader()",
					"com.liferay.portal.kernel.util.HttpUtil.setParameter()",
					"com.liferay.portal.kernel.util.HttpUtil.shortenURL()",
					"com.liferay.portal.kernel.util.Http.addParameter()",
					"com.liferay.portal.kernel.util.Http.decodePath()",
					"com.liferay.portal.kernel.util.Http.decodeURL()",
					"com.liferay.portal.kernel.util.Http.encodeParameters()",
					"com.liferay.portal.kernel.util.Http.encodePath()",
					"com.liferay.portal.kernel.util.Http.fixPath()",
					"com.liferay.portal.kernel.util.Http.getCompleteURL()",
					"com.liferay.portal.kernel.util.Http.getDomain()",
					"com.liferay.portal.kernel.util.Http.getIpAddress()",
					"com.liferay.portal.kernel.util.Http.getParameter()",
					"com.liferay.portal.kernel.util.Http.getParameterMap()",
					"com.liferay.portal.kernel.util.Http.getPath()",
					"com.liferay.portal.kernel.util.Http.getProtocol()",
					"com.liferay.portal.kernel.util.Http.getQueryString()",
					"com.liferay.portal.kernel.util.Http.getRequestURL()",
					"com.liferay.portal.kernel.util.Http.getURI()",
					"com.liferay.portal.kernel.util.Http.hasDomain()",
					"com.liferay.portal.kernel.util.Http.hasProtocol()",
					"com.liferay.portal.kernel.util.Http.isForwarded()",
					"com.liferay.portal.kernel.util.Http.isSecure()",
					"com.liferay.portal.kernel.util.Http.normalizePath()",
					"com.liferay.portal.kernel.util.Http.parameterMapFromString()",
					"com.liferay.portal.kernel.util.Http.parameterMapToString()",
					"com.liferay.portal.kernel.util.Http.protocolize()",
					"com.liferay.portal.kernel.util.Http.removeDomain()",
					"com.liferay.portal.kernel.util.Http.removeParameter()",
					"com.liferay.portal.kernel.util.Http.removePathParameters()",
					"com.liferay.portal.kernel.util.Http.removeProtocol()",
					"com.liferay.portal.kernel.util.Http.sanitizeHeader()",
					"com.liferay.portal.kernel.util.Http.setParameter()",
					"com.liferay.portal.kernel.util.Http.shortenURL()",
			},
			new String[] {
					"com.liferay.portal.kernel.util.HttpComponentsUtil.addParameter",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.decodePath",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.decodeURL",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.encodeParameters",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.encodePath",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.fixPath",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.getCompleteURL",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.getDomain",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.getIpAddress",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.getParameter",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.getParameterMap",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.getPath",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.getProtocol",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.getQueryString",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.getRequestURL",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.getURI",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.hasDomain",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.hasProtocol",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.isForwarded",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.isSecure",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.normalizePath",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.parameterMapFromString",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.parameterMapToString",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.protocolize",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.removeDomain",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.removeParameter",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.removePathParameters",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.removeProtocol",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.sanitizeHeader",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.setParameter",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.shortenURL",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.addParameter",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.decodePath",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.decodeURL",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.encodeParameters",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.encodePath",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.fixPath",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.getCompleteURL",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.getDomain",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.getIpAddress",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.getParameter",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.getParameterMap",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.getPath",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.getProtocol",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.getQueryString",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.getRequestURL",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.getURI",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.hasDomain",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.hasProtocol",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.isForwarded",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.isSecure",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.normalizePath",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.parameterMapFromString",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.parameterMapToString",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.protocolize",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.removeDomain",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.removeParameter",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.removePathParameters",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.removeProtocol",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.sanitizeHeader",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.setParameter",
					"com.liferay.portal.kernel.util.HttpComponentsUtil.shortenURL"
			} );

	public static LiferayJavaDeprecations.JavaMethodCallDeprecation LPS_162437_ADDRESS = new LiferayJavaDeprecations.JavaMethodCallDeprecation(
			7.4f,
			"Adress column typeId has been renamed to listTypeId.",
			"LPS-162437",
			new String[] {
					"com.liferay.portal.kernel.model.Address.getTypeId()", "com.liferay.portal.kernel.model.Address.getType()", "com.liferay.portal.kernel.model.Address.setTypeId()",
					"com.liferay.portal.kernel.service.AddressLocalService.getTypeAddresses()", "com.liferay.portal.kernel.service.AddressLocalServiceUtil.getTypeAddresses()",
			},
			new String[] {
					"getListTypeId", "getListType", "setListTypeId",
					"getListTypeAddresses", "getListTypeAddresses"
			} );

	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_162830_CONFIGURATION_BEAN_DECLARATION = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.4f,
			"A ConfigurationBeanDeclaration is no longer neccessary and the class can be removed.",
			"LPS-162830",
			new String[] {"com.liferay.portal.kernel.settings.definition.ConfigurationBeanDeclaration" },
			new String[0] );

	public static LiferayJavaDeprecations.JavaMethodCallDeprecation LPS_163821_EMAIL_ADDRESS = new LiferayJavaDeprecations.JavaMethodCallDeprecation(
			7.4f,
			"EmailAddress column typeId has been renamed to listTypeId.",
			"LPS-163821",
			new String[] {"com.liferay.portal.kernel.model.EmailAddress.getTypeId()", "com.liferay.portal.kernel.model.EmailAddress.getType()", "com.liferay.portal.kernel.model.EmailAddress.setTypeId()"},
			new String[] {"getListTypeId", "getListType", "setListTypeId"} );
	public static LiferayJavaDeprecations.JavaMethodCallDeprecation LPS_162450_PHONE = new LiferayJavaDeprecations.JavaMethodCallDeprecation(
			7.4f,
			"Phone column typeId has been renamed to listTypeId.",
			"LPS-162450",
			new String[] {"com.liferay.portal.kernel.model.Phone.getTypeId()", "com.liferay.portal.kernel.model.Phone.getType()", "com.liferay.portal.kernel.model.Phone.setTypeId()"},
			new String[] {"getListTypeId", "getListType", "setListTypeId"} );
	public static LiferayJavaDeprecations.JavaMethodCallDeprecation LPS_164415_WEBSITE = new LiferayJavaDeprecations.JavaMethodCallDeprecation(
			7.4f,
			"Website column typeId has been renamed to listTypeId.",
			"LPS-164415",
			new String[] {"com.liferay.portal.kernel.model.Website.getTypeId()", "com.liferay.portal.kernel.model.Website.getType()", "com.liferay.portal.kernel.model.Website.setTypeId()"},
			new String[] {"getListTypeId", "getListType", "setListTypeId"} );
	public static LiferayJavaDeprecations.JavaMethodCallDeprecation LPS_164522_CONTACT = new LiferayJavaDeprecations.JavaMethodCallDeprecation(
			7.4f,
			"Contact columns prefixId and suffixId have been renamed to prefixListTypeId and suffixListTypeId.",
			"LPS-164522",
			new String[] {"com.liferay.portal.kernel.model.Contact.getPrefixId()", "com.liferay.portal.kernel.model.Contact.setPrefixId()", "com.liferay.portal.kernel.model.Contact.getSuffixId()", "com.liferay.portal.kernel.model.Contact.setSuffixId()"},
			new String[] {"getPrefixListTypeId", "setPrefixListTypeId", "getSuffixListTypeId", "setSuffixListTypeId"} );
	public static LiferayJavaDeprecations.JavaMethodCallDeprecation LPS_165244_ORGANIZATION = new LiferayJavaDeprecations.JavaMethodCallDeprecation(
			7.4f,
			"Organization column statusId has been renamed to statusListTypeId.",
			"LPS-165244",
			new String[] {"com.liferay.portal.kernel.model.Organization.getStatusId()", "com.liferay.portal.kernel.model.Contact.setStatusId()"},
			new String[] {"getStatusListTypeId", "setStatusListTypeId"} );
	public static LiferayJavaDeprecations.JavaMethodCallDeprecation LPS_165685_ORG_LABOR = new LiferayJavaDeprecations.JavaMethodCallDeprecation(
			7.4f,
			"OrgLabor column typeId has been renamed to listTypeId.",
			"LPS-165685",
			new String[] {"com.liferay.portal.kernel.model.OrgLabor.getTypeId()", "com.liferay.portal.kernel.model.OrgLabor.setTypeId()", "com.liferay.portal.kernel.model.OrgLabor.getType()"},
			new String[] {"getListTypeId", "setListTypeId", "getListType"} );
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_181233_CTSQL_MODE_THREAD_LOCAL = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.4f,
			"Moved CTSQLModeThreadLocal to portal-kernel and Changed Package.",
			"LPS-181233",
			new String[] {"com.liferay.portal.change.tracking.sql.CTSQLModeThreadLocal"},
			new String[] {"com.liferay.portal.kernel.change.tracking.sql.CTSQLModeThreadLocal"} );
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_169777_SCRIPTING_EXECUTOR_EXTENDER = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.4f,
			"ScriptingExecutorExtender and ScriptBundleProvider have been removed due to security vulnerabilities.",
			"LPS-169777",
			new String[] {"com.liferay.portal.scripting.ScriptBundleProvider", "com.liferay.portal.scripting.executor.internal.constants.ScriptingExecutorConstants", "com.liferay.portal.scripting.executor.internal.extender.ScriptingExecutorExtender"},
			new String[0] );
	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_176640_S3_FILE_CACHE = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.4f,
			"S3FileCache was removed due to design flaws.",
			"LPS-176640",
			new String[] {"com.liferay.portal.store.s3.S3FileCache"},
			new String[0] );
	public static LiferayJavaDeprecations.JavaMethodCallDeprecation LPS_194314_SCHEDULER_ENGINE_UNSCHEDULE = new LiferayJavaDeprecations.JavaMethodCallDeprecation(
			7.4f,
			"The method unschedule is removed from SchedulerEngine, use delete instead.",
			"LPS-194314",
			new String[] {"com.liferay.portal.kernel.scheduler.SchedulerEngine.unschedule()", "com.liferay.portal.kernel.scheduler.SchedulerEngineHelper.unschedule()", "com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil.unschedule()", },
			new String[] {"delete", "delete", "delete"} );



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
