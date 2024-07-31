package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.jsp.psi.BaseJspUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.jsp.JspDirectiveKind;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import de.dm.intellij.liferay.language.java.LiferayJavaDeprecations;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import de.dm.intellij.liferay.util.LiferayVersions;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static de.dm.intellij.liferay.language.jsp.LiferayJspJavaDeprecationInfoHolder.createImportStatements;
import static de.dm.intellij.liferay.language.jsp.LiferayJspJavaDeprecationInfoHolder.createMethodCalls;

public class LiferayJspJavaDeprecationInspection extends AbstractLiferayDeprecationInspection<LiferayJspJavaDeprecationInfoHolder> {

	public static final List<LiferayJspJavaDeprecationInfoHolder> JAVA_DEPRECATIONS = new ArrayList<>();

	static {
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_50156_UTIL_BRIDGES));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_53113_USER_SCREEN_NAME_EXCEPTION));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_53279_USER_EMAIL_ADDRESS_EXCEPTION));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_53487_USER_ID_EXCEPTION));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_55364_CONTACT_NAME_EXCEPTION));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_61952_PORTAL_KERNEL));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_63205_USER_EXPORTER));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_NONE_MODULARIZATION_70));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_NONE_MODULARIZATION_71));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_88912_INVOKABLE_SERVICE));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_88913_SERVICE_LOADER_CONDITION));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_88869_TERMS_OF_USE));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_88870_CONVERTER));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_89223_OSGI_SERVICE_UTILS));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_89139_PREDICATE_FILTER));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_88911_FUNCTION_SUPPLIER));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_NONE_MODULARIZATION_72));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_95909_ELASTICSEARCH_7).version("7.3.3"));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_100144_ASSET_TAGS_SELECTOR_TAG));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_106167_PORTAL_KERNEL));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_104241_REMOVED_DEPRECATIONS));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_89065_ASSET_CATEGORIES_TABLE));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_182671_BASE_MODEL_PERMISSION_CHECKER).version("7.4.3.92"));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_122955_SOY_PORTLET));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_124898_OPEN_ID_CONNECT_SERVICE_HANDLER));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_122956_SOY));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_133200_OPEN_ID_CONNECT_SERVICE_HANDLER));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_147929_PETRA_JSON_VALIDATOR).version("7.4.3.23"));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_149147_PETRA_CONTENT).version("7.4.3.29"));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_149460_PETRA_MODEL_ADAPTER).version("7.4.3.22"));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_150808_PETRA_HTTP_INVOKER).version("7.4.3.24"));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_151723_PETRA_ENCRYPTOR).version("7.4.3.24"));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_148013_PETRA_JSON_WEB_SERVICE_CLIENT).version("7.4.3.31"));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_148493_PETRA_LOG4J).version("7.4.3.42"));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_151632_PETRA_PORTLET_URL_BUILDER).version("7.4.3.45"));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_152089_TRASH_UTIL).version("7.4.3.23"));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_162830_CONFIGURATION_BEAN_DECLARATION).version("7.4.3.51"));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_181233_CTSQL_MODE_THREAD_LOCAL).version("7.4.3.74"));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_176640_S3_FILE_CACHE).version("7.4.3.80"));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_195116_DESTINATION_MESSAGE_BUS_EVENT_LISTENER).version("7.4.3.94"));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_198809_MODEL_SEARCH_REGISTRAR_HELPER).version("7.4.3.100"));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_198877_OPEN_ID_UTIL).version("7.4.3.109"));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_198653_FORM_NAVIGATOR_ENTRY_UTIL).version("7.4.3.100"));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_198859_THREAD_LOCAL_DISTRIBUTOR).version("7.4.3.102"));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_169777_SCRIPTING_EXECUTOR_EXTENDER).version("7.4.3.84"));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_201086_AUDIT_MESSAGE_FACTORY).version(LiferayVersions.LIFERAY_2024_Q1_CE));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_199532_JS_AWARE_PORTAL_WEB_RESOURCE).version(LiferayVersions.LIFERAY_2024_Q1_CE));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_203260_SITEMAP).version(LiferayVersions.LIFERAY_2024_Q1_CE));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_203720_CATEGORY_FACET).version(LiferayVersions.LIFERAY_2024_Q1_CE));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPD_15179_OBJECT_GRAPH_UTIL).version(LiferayVersions.LIFERAY_2024_Q1_CE));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_199470_SITE_MEMBERSHIP).version(LiferayVersions.LIFERAY_2024_Q1_CE));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_201156_LAYOUT_PROTOTYPE).version(LiferayVersions.LIFERAY_2024_Q1_CE));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_203854_JSON_WEB_SERVICE).version(LiferayVersions.LIFERAY_2024_Q1_CE));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_200088_PORTLET_CONTEXT_FACTORY).version(LiferayVersions.LIFERAY_2024_Q1_CE));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_200073_ASSET_ENTRIES_FACET).version(LiferayVersions.LIFERAY_2024_Q1_CE));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_199470_SITE_MEMBERSHIP_POLICY).version(LiferayVersions.LIFERAY_2024_Q1_CE));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_200563_SITE_MEMBERSHIP_POLICY).version(LiferayVersions.LIFERAY_2024_Q1_CE));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_188559_PROCESSOR).version(LiferayVersions.LIFERAY_2024_Q1_CE));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_200537_DEFAULT_CONTROL_PANEL_ENTRY).version(LiferayVersions.LIFERAY_2024_Q1_CE));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPD_20659_REMOTE_PREFERENCE).version(LiferayVersions.LIFERAY_2024_Q2_CE));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPS_196226_REGISTRY_CLASSES).version(LiferayVersions.LIFERAY_2024_Q2_CE));
		JAVA_DEPRECATIONS.addAll(createImportStatements(LiferayJavaDeprecations.LPD_24699_PORTAL_LIFECYCLE).version(LiferayVersions.LIFERAY_2024_Q2_CE));
		JAVA_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_150185_HTTP_COMPONENTS_UTIL).version("7.4.3.22"));
		JAVA_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_162450_PHONE).version("7.4.3.45"));
		JAVA_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_162437_ADDRESS).version("7.4.3.45"));
		JAVA_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_163821_EMAIL_ADDRESS).version("7.4.3.48"));
		JAVA_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_164415_WEBSITE).version("7.4.3.48"));
		JAVA_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_164522_CONTACT).version("7.4.3.50"));
		JAVA_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_165244_ORGANIZATION).version("7.4.3.50"));
		JAVA_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_165685_ORG_LABOR).version("7.4.3.52"));
		JAVA_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_194314_SCHEDULER_ENGINE_UNSCHEDULE).version("7.4.3.95"));
		JAVA_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_194337_DESTINATION).version("7.4.3.94"));
		JAVA_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_173628_CHECK_COMPANY_ACCOUNT_ROLES).version("7.4.3.100"));
		JAVA_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_197840_SCOPE_GROUP_TYPE).version("7.4.3.102"));
		JAVA_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_200501_STARTUP_FINISHED).version("7.4.3.109"));
		JAVA_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_200072_TEMPLATE_MANAGER).version("7.4.3.102"));
		JAVA_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_121733_THEME_DISPLAY_FLASH));
		JAVA_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_134188_SANITIZED_SERVLET_RESPONSE));
		JAVA_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_203214_SITEMAP_CONFIGURATION_MANAGER).version(LiferayVersions.LIFERAY_2024_Q1_CE));
		JAVA_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPD_15179_REFLECTION_UTIL_ARRAY_CLONE).version(LiferayVersions.LIFERAY_2024_Q1_CE));
		JAVA_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_199958_INDEX_WRITER_HELPER).version(LiferayVersions.LIFERAY_2024_Q1_CE));
		JAVA_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPS_188565_FRIENDLY_URL_NORMALIZER).version(LiferayVersions.LIFERAY_2024_Q1_CE));
		JAVA_DEPRECATIONS.addAll(createMethodCalls(LiferayJavaDeprecations.LPD_2110_PORTAL_UTIL_LOCALIZED_FRIENDLY_URL).version(LiferayVersions.LIFERAY_2024_Q2_CE));
	}
	@Nls
	@NotNull
	@Override
	public String getDisplayName() {
		return "Liferay Java deprecations inspection";
	}

	@Override
	public String getStaticDescription() {
		return "Check for deprecated Liferay Java instructions inside JSP files.";
	}

	@Override
	public String @NotNull [] getGroupPath() {
		return new String[]{
				getGroupDisplayName(),
				LiferayInspectionsGroupNames.JSP_GROUP_NAME
		};
	}
	@Override
	protected List<LiferayJspJavaDeprecationInfoHolder> getInspectionInfoHolders() {
		return JAVA_DEPRECATIONS;
	}

	@Override
	protected PsiElementVisitor doBuildVisitor(ProblemsHolder holder, boolean isOnTheFly, List<LiferayJspJavaDeprecationInfoHolder> inspectionInfoHolders) {
		return new PsiElementVisitor() {

			@Override
			public void visitElement(@NotNull PsiElement element) {
				if (element instanceof XmlTag xmlTag) {
					visitXmlTag(xmlTag);
				} else if (element instanceof PsiMethodCallExpression methodCallExpression) {
					visitMethodCallExpression(methodCallExpression);
				}

				super.visitElement(element);
			}

			public void visitMethodCallExpression(@NotNull PsiMethodCallExpression methodCallExpression) {
				for (LiferayJspJavaDeprecationInfoHolder infoHolder : inspectionInfoHolders) {
					infoHolder.visitMethodCallExpression(holder, methodCallExpression);
				}
			}

			public void visitXmlTag(@NotNull XmlTag tag) {
				XmlAttribute importDirective = getImportDirective(tag);

				if (importDirective != null) {
					for (LiferayJspJavaDeprecationInfoHolder infoHolder : inspectionInfoHolders) {
						infoHolder.visitImportDirective(holder, importDirective);
					}
				}
			}
		};
	}

	private static XmlAttribute getImportDirective(XmlTag xmlTag) {
		JspDirectiveKind directiveKindByTag = BaseJspUtil.getDirectiveKindByTag(xmlTag);

		if (directiveKindByTag == JspDirectiveKind.PAGE) {
			for (XmlAttribute xmlAttribute : xmlTag.getAttributes()) {
				if (Objects.equals("import", xmlAttribute.getName())) {
					String namespace = xmlAttribute.getNamespace();
					if (namespace.isEmpty() || "http://java.sun.com/JSP/Page".equals(namespace)) {
						return xmlAttribute;
					}
				}
			}
		}

		return null;
	}

}
