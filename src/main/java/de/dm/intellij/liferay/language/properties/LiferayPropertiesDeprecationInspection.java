package de.dm.intellij.liferay.language.properties;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.lang.properties.psi.impl.PropertyImpl;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import de.dm.intellij.liferay.language.jsp.AbstractLiferayDeprecationInspection;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static de.dm.intellij.liferay.language.properties.LiferayPropertiesDeprecationInfoHolder.createProperties;

public class LiferayPropertiesDeprecationInspection extends AbstractLiferayDeprecationInspection<LiferayPropertiesDeprecationInfoHolder> {
	public static final List<LiferayPropertiesDeprecationInfoHolder> PROPERTY_DEPRECATIONS = new ArrayList<>();

	static {
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.0f, "Removed Portal Properties Used to Display Sections in Form Navigators", "LPS-54903",
				"company.settings.form.configuration",
				"company.settings.form.identification",
				"company.settings.form.miscellaneous",
				"company.settings.form.social",
				"layout.form.add",
				"layout.form.update",
				"layout.set.form.update",
				"organizations.form.add.identification",
				"organizations.form.add.main",
				"organizations.form.add.miscellaneous",
				"organizations.form.update.identification",
				"organizations.form.update.main",
				"organizations.form.update.miscellaneous",
				"sites.form.add.advanced",
				"sites.form.add.main",
				"sites.form.add.miscellaneous",
				"sites.form.add.seo",
				"sites.form.update.advanced",
				"sites.form.update.main",
				"sites.form.update.miscellaneous",
				"sites.form.update.seo",
				"users.form.add.identification",
				"users.form.add.main",
				"users.form.add.miscellaneous",
				"users.form.my.account.identification",
				"users.form.my.account.main",
				"users.form.my.account.miscellaneous",
				"users.form.update.identification",
				"users.form.update.main",
				"users.form.update.miscellaneous").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.0f, "Removed the asset.entry.validator Property", "LPS-64370",
				"asset.entry.validator").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.0f, "Removed the asset.publisher.asset.entry.query.processors Property", "LPS-52966",
				"asset.publisher.asset.entry.query.processors").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.0f, "Removed Liferay Frontend Editor BBCode Web, Previously Known as Liferay BBCode Editor", "LPS-48334",
				"editor.wysiwyg.portal-web.docroot.html.portlet.message_boards.edit_message.bb_code.jsp").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.0f, "Replaced the Breadcrumb Portlet's Display Styles with ADTs", "LPS-53577",
				"breadcrumb.display.style.default", "breadcrumb.display.style.options").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.0f, "The convert.processes key was removed from portal.properties.", "LPS-50604",
				"convert.processes").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.0f, "Deprecated the social.activity.sets.enabled Property.", "LPS-63635",
				"social.activity.sets.enabled").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.0f, "Merged Configured Email Signature Field into the Body of Email Messages from Message Boards and Wiki", "LPS-44599",
				"message.boards.email.message.added.signature", "message.boards.email.message.updated.signature", "wiki.email.page.added.signature", "wiki.email.page.updated.signature").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.0f, "Moved Journal File Uploads Portlet Properties to OSGi Configuration", "LPS-69209",
				"journal.image.extensions", "journal.image.small.max.size").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.0f, "Moved Journal Portlet Properties to OSGi Configuration", "LPS-58672",
				"journal.display.views",
				"journal.default.display.view",
				"journal.article.expire.all.versions",
				"journal.article.form.add",
				"journal.article.form.default.values",
				"journal.article.form.translate",
				"journal.article.form.update",
				"journal.article.force.autogenerate.id",
				"journal.article.types",
				"journal.article.token.page.break",
				"journal.article.check.interval",
				"journal.article.storage.type",
				"journal.article.view.permission.check.enabled",
				"journal.article.comments.enabled",
				"journal.article.database.keyword.search.content",
				"journal.feed.force.autogenerate.id",
				"journal.transformer.listener",
				"journal.publish.to.live.by.default",
				"journal.publish.version.history.by.default",
				"journal.sync.content.search.on.startup",
				"journal.email.from.name",
				"journal.email.from.address",
				"journal.email.article.added.enabled",
				"journal.email.article.added.subject",
				"journal.email.article.added.body",
				"journal.email.article.approval.denied.enabled",
				"journal.email.article.approval.denied.subject",
				"journal.email.article.approval.denied.body",
				"journal.email.article.approval.granted.enabled",
				"journal.email.article.approval.granted.subject",
				"journal.email.article.approval.granted.body",
				"journal.email.article.approval.requested.enabled",
				"journal.email.article.approval.requested.subject",
				"journal.email.article.approval.requested.body",
				"journal.email.article.review.enabled",
				"journal.email.article.review.subject",
				"journal.email.article.review.body",
				"journal.email.article.updated.enabled",
				"journal.email.article.updated.subject",
				"journal.email.article.updated.body",
				"journal.lar.creation.strategy",
				"journal.error.template[ftl]",
				"journal.error.template[vm]",
				"journal.error.template[xsl]",
				"journal.articles.page.delta.values",
				"journal.articles.search.with.index",
				"journal.articles.index.all.versions",
				"journal.content.publish.to.live.by.default",
				"journal.content.search.show.listed").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.0f, "Replaced the Language Portlet's Display Styles with ADTs", "LPS-54419",
				"language.display.style.default", "language.display.style.options").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.0f, "Moved Shopping File Uploads Portlet Properties to OSGi Configuration", "LPS-69210",
				"shopping.cart.min.qty.multiple",
				"shopping.category.forward.to.car",
				"shopping.category.show.special.items",
				"shopping.item.show.availabilit",
				"shopping.image.small.max.size",
				"shopping.image.medium.max.size",
				"shopping.image.large.max.size",
				"shopping.image.extensions",
				"shopping.email.from.name",
				"shopping.email.from.address",
				"shopping.email.order.confirmation.enabled",
				"shopping.email.order.confirmation.subject",
				"shopping.email.order.confirmation.body",
				"shopping.email.order.shipping.enabled",
				"shopping.email.order.shipping.subject",
				"shopping.email.order.shipping.body",
				"shopping.order.comments.enabled").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.0f, "The user.last.name.required property has been removed from portal.properties and the corresponding UI.", "LPS-54956",
				"users.last.name.required").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.1f, "Moved CAPTCHA Portal Properties to OSGi Configuration.", "LPS-67830",
				"captcha.max.challenges",
				"captcha.check.portal.create_account",
				"captcha.check.portal.send_password",
				"captcha.check.portlet.message_boards.edit_category",
				"captcha.check.portlet.message_boards.edit_message",
				"captcha.engine.impl",
				"captcha.engine.recaptcha.key.private",
				"captcha.engine.recaptcha.key.public",
				"captcha.engine.recaptcha.url.script",
				"captcha.engine.recaptcha.url.noscript",
				"captcha.engine.recaptcha.url.verify",
				"captcha.engine.simplecaptcha.height",
				"captcha.engine.simplecaptcha.width",
				"captcha.engine.simplecaptcha.background.producers",
				"captcha.engine.simplecaptcha.gimpy.renderers",
				"captcha.engine.simplecaptcha.noise.producers",
				"captcha.engine.simplecaptcha.text.producers",
				"captcha.engine.simplecaptcha.word.renderers"
				).quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.1f, "Moved OpenOffice Properties to OSGi Configuration.", "LPS-71382",
				"openoffice.cache.enabled", "openoffice.server.enabled", "openoffice.server.host", "openoffice.server.port").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.1f, "Moved Organization Type Properties to OSGi Configuration.", "LPS-77183",
				"organizations.types",
				"organizations.rootable",
				"organizations.children.types",
				"organizations.country.enabled",
				"organizations.country.required").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.1f, "Moved Three DL File Properties to OSGi Configuration.", "LPS-69208",
				"dl.file.entry.previewable.processor.max.size", "dl.file.extensions", "dl.file.max.size").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.1f, "Moved Upload Servlet Request Portal Properties to OSGi Configuration.", "LPS-69102",
				"com.liferay.portal.upload.UploadServletRequestImpl.max.size", "com.liferay.portal.upload.UploadServletRequestImpl.temp.dir").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.1f, "Moved Users File Uploads Portlet Properties to OSGi Configuration.", "LPS-69211",
				"users.image.check.token", "users.image.max.size", "users.image.max.height", "users.image.max.width").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.1f, "Removed JavaScript Minification Properties From Portal Properties.", "LPS-74375",
				"minifier.javascript.impl",
				"yui.compressor.css.line.break",
				"yui.compressor.js.disable.optimizations",
				"yui.compressor.js.line.break",
				"yui.compressor.js.munge",
				"yui.compressor.js.preserve.all.semicolons",
				"yui.compressor.js.verbose").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.2f, "Moved Two Staging Properties to OSGi Configuration.", "LPS-88018",
				"staging.delete.temp.lar.on.failure", "staging.delete.temp.lar.on.success").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.3f, "Three bootstrap cache properties have been moved from portal.properties to an OSGi configuration.", "LPS-96563",
				"ehcache.bootstrap.cache.loader.enabled", "ehcache.bootstrap.cache.loader.properties", "ehcache.bootstrap.cache.loader.properties.default").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.3f, "Blocking cache support was removed.", "LPS-115687",
				"ehcache.blocking.cache.allowed", "permissions.object.blocking.cache", "value.object.entity.blocking.cache").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.3f, "The portal property user.groups.copy.layouts.to.user.personal.site and the behavior associated with it were removed.", "LPS-106339",
				"user.groups.copy.layouts.to.user.personal.site").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.3f, "Server-side Parallel Rendering Is No Longer Supported.", "LPS-110359",
				"layout.parallel.render.*").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.3f, "Remove Support for Setting Cache Properties for Each Entity Model.", "LPS-116049",
				"value.object.column.bitmask.enabled.*", "value.object.entity.cache.enabled.*", "value.object.finder.cache.enabled.*").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.4f, "The portal's Clamd integration implementation has been replaced by an OSGi service that uses a Clamd remote service.", "LPS-122280",
				"dl.store.antivirus.impl").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.4f, "The X-Xss-Protection header is not supported by modern browsers.", "LPS-134188",
				"http.header.secure.x.xss.protection").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.4f, "The buffered.increment.enabled portal property has been removed and the view.count.enabled moved to OSGi configurations.", "LPS-120626",
				"buffered.increment.enabled", "view.count.enabled").quickfix(removeProperty()).version("7.3.6"));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.4f, "Portal property module.framework.properties.file.install.optionalImportRefreshScope has been removed.", "LPS-122008",
				"module.framework.properties.file.install.optionalImportRefreshScope").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.4f, "Redirect URL configuration is no longer configurable via portal properties and have been moved to OSGi configurations.", "LPS-128837",
				"redirect.url.domains.allowed", "redirect.url.ips.allowed", "redirect.url.security.mode").quickfix(removeProperty()));
		PROPERTY_DEPRECATIONS.addAll(createProperties(7.4f, "The properties tika.config, text.extraction.fork.process.enabled and text.extraction.fork.process.mime.types have been moved to OSGi configurations.", "LPS-147938",
				"tika.config", "text.extraction.fork.process.enabled", "text.extraction.fork.process.mime.types").quickfix(removeProperty()));


	}
	@Nls
	@NotNull
	@Override
	public String getDisplayName() {
		return "Liferay Properties deprecations inspection";
	}

	@Override
	public String getStaticDescription() {
		return "Check for deprecated Liferay Properties.";
	}

	@Override
	public String @NotNull [] getGroupPath() {
		return new String[]{
				getGroupDisplayName(),
				LiferayInspectionsGroupNames.PROPERTIES_GROUP_NAME
		};
	}
	@Override
	protected List<LiferayPropertiesDeprecationInfoHolder> getInspectionInfoHolders() {
		return PROPERTY_DEPRECATIONS;
	}

	@Override
	protected PsiElementVisitor doBuildVisitor(ProblemsHolder holder, boolean isOnTheFly, List<LiferayPropertiesDeprecationInfoHolder> inspectionInfoHolders) {
		return new PsiElementVisitor() {
			@Override
			public void visitElement(@NotNull PsiElement element) {
				if (element instanceof PropertyImpl property) {
					for (LiferayPropertiesDeprecationInfoHolder infoHolder : inspectionInfoHolders) {
						infoHolder.visitProperty(holder, property);
					}
				}
			}
		};
	}

	private static LocalQuickFix removeProperty() {
		return new RemovePropertyQuickFix();
	}

	private static class RemovePropertyQuickFix implements LocalQuickFix {

		@Nls
		@NotNull
		@Override
		public String getFamilyName() {
			return "Remove Property";
		}

		@Nls
		@NotNull
		@Override
		public String getName() {
			return "Remove Property";
		}

		@Override
		public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
			PsiElement psiElement = descriptor.getPsiElement();

			PropertyImpl property;

			if (psiElement instanceof PropertyImpl) {
				property = (PropertyImpl) psiElement;
			} else {
				property = PsiTreeUtil.getParentOfType(psiElement, PropertyImpl.class);
			}

			if (property == null) {
				return;
			}

			property.delete();
		}
	}

	private static LocalQuickFix renameProperty(String newName) {
		return new RenamePropertyQuickFix(newName);
	}

	private static class RenamePropertyQuickFix implements LocalQuickFix {

		private String newName;

		public RenamePropertyQuickFix(String newName) {
			this.newName = newName;
		}

		@Nls
		@NotNull
		@Override
		public String getFamilyName() {
			return "Rename Property";
		}

		@Nls
		@NotNull
		@Override
		public String getName() {
			return "Rename Property to " + newName;
		}

		@Override
		public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
			PsiElement psiElement = descriptor.getPsiElement();

			PropertyImpl property;

			if (psiElement instanceof PropertyImpl) {
				property = (PropertyImpl) psiElement;
			} else {
				property = PsiTreeUtil.getParentOfType(psiElement, PropertyImpl.class);
			}

			if (property == null) {
				return;
			}

			property.setName(newName);
		}
	}

}


