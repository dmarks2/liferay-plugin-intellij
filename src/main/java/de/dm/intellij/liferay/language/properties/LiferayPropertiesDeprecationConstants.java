package de.dm.intellij.liferay.language.properties;

public class LiferayPropertiesDeprecationConstants {

	public static final String[] _OBSOLETE_SYSTEM_KEYS_7_0 = {
			"com.liferay.util.Http.proxy.host", "com.liferay.util.Http.proxy.port",
			"com.liferay.util.XSSUtil.regexp.pattern"
	};

	public static final String[] _OBSOLETE_SYSTEM_KEYS_7_1 = {
			"com.liferay.petra.memory.FinalizeManager.thread.enabled",
			"com.liferay.portal.kernel.memory.FinalizeManager.thread.enabled",
			"finalize.manager.thread.enabled"
	};

	public static final String[] _OBSOLETE_SYSTEM_KEYS_7_4 = {
			"com.liferay.util.axis.SimpleHTTPSender.regexp.pattern",
			"com.liferay.portal.kernel.util.ServiceProxyFactory.timeout" //https://liferay.atlassian.net/browse/LPS-204548
	};

	public static final String[][] _MIGRATED_SYSTEM_KEYS_7_0 = {
			{
					"com.liferay.filters.compression.CompressionFilter",
					"com.liferay.portal.servlet.filters.gzip.GZipFilter"
			},
			{
					"com.liferay.filters.strip.StripFilter",
					"com.liferay.portal.servlet.filters.strip.StripFilter"
			},
			{
					"com.liferay.util.Http.max.connections.per.host",
					"com.liferay.portal.util.HttpImpl.max.connections.per.host"
			},
			{
					"com.liferay.util.Http.max.total.connections",
					"com.liferay.portal.util.HttpImpl.max.total.connections"
			},
			{
					"com.liferay.util.Http.proxy.auth.type",
					"com.liferay.portal.util.HttpImpl.proxy.auth.type"
			},
			{
					"com.liferay.util.Http.proxy.ntlm.domain",
					"com.liferay.portal.util.HttpImpl.proxy.ntlm.domain"
			},
			{
					"com.liferay.util.Http.proxy.ntlm.host",
					"com.liferay.portal.util.HttpImpl.proxy.ntlm.host"
			},
			{
					"com.liferay.util.Http.proxy.password",
					"com.liferay.portal.util.HttpImpl.proxy.password"
			},
			{
					"com.liferay.util.Http.proxy.username",
					"com.liferay.portal.util.HttpImpl.proxy.username"
			},
			{
					"com.liferay.util.Http.timeout",
					"com.liferay.portal.util.HttpImpl.timeout"
			},
			{
					"com.liferay.util.format.PhoneNumberFormat",
					"phone.number.format.impl"
			},
			{
					"com.liferay.util.servlet.UploadServletRequest.max.size",
					"com.liferay.portal.upload.UploadServletRequestImpl.max.size"
			},
			{
					"com.liferay.util.servlet.UploadServletRequest.temp.dir",
					"com.liferay.portal.upload.UploadServletRequestImpl.temp.dir"
			},
			{
					"com.liferay.util.servlet.fileupload.LiferayFileItem.threshold.size",
					"com.liferay.portal.upload.LiferayFileItem.threshold.size"
			},
			{
					"com.liferay.util.servlet.fileupload.LiferayInputStream.threshold.size",
					"com.liferay.portal.upload.LiferayInputStream.threshold.size"
			}
	};

	public static final String[][] _RENAMED_SYSTEM_KEYS_7_0 = {
			{
					"com.liferay.portal.kernel.util.StringBundler.unsafe.create." +
							"threshold",
					"com.liferay.portal.kernel.util.StringBundler.threadlocal.buffer." +
							"limit"
			}
	};

	public static final String[][] _MODULARIZED_SYSTEM_KEYS_7_0 = {

			// Calendar

			{
					"ical4j.compatibility.outlook", "ical4j.compatibility.outlook",
					"com.liferay.calendar.service"
			},
			{
					"ical4j.parsing.relaxed", "ical4j.parsing.relaxed",
					"com.liferay.calendar.service"
			},
			{
					"ical4j.unfolding.relaxed", "ical4j.unfolding.relaxed",
					"com.liferay.calendar.service"
			},
			{
					"ical4j.validation.relaxed", "ical4j.validation.relaxed",
					"com.liferay.calendar.service"
			}
	};

	public static final String[][] _MODULARIZED_SYSTEM_KEYS_7_4 = {

			// Tika

			{
					"tika.config", "tika-config-xml", "com.liferay.portal.tika"
			}
	};

	public static final String[][] _MIGRATED_PORTAL_KEYS_7_0 = {
			{"cookie.http.only.names.excludes", "cookie.http.only.names.excludes"},
			{
					"finalize.manager.thread.enabled",
					"com.liferay.portal.kernel.memory.FinalizeManager.thread.enabled"
			},
			{
					"http.header.secure.x.content.type.options",
					"http.header.secure.x.content.type.options"
			},
			{
					"http.header.secure.x.content.type.options.urls.excludes",
					"http.header.secure.x.content.type.options.urls.excludes"
			},
			{
					"http.header.secure.x.frame.options",
					"http.header.secure.x.frame.options"
			},
			{
					"http.header.secure.x.frame.options.255",
					"http.header.secure.x.frame.options.255"
			},
			{
					"http.header.secure.x.xss.protection",
					"http.header.secure.x.xss.protection"
			}
	};
	public static final String[][] _MIGRATED_PORTAL_KEYS_7_4 = {
			{
					"module.framework.beginning.start.level",
					"module.framework.beginning.start.level"
			},
			{
					"module.framework.dynamic.install.start.level",
					"module.framework.dynamic.install.start.level"
			},
			{
					"module.framework.file.install.config.encoding",
					"module.framework.file.install.config.encoding"
			},
			{
					"module.framework.concurrent.startup.enabled",
					"module.framework.concurrent.startup.enabled"
			},
			{
					"module.framework.configuration.bundle.symbolic.names",
					"module.framework.configuration.bundle.symbolic.names"
			},
			{
					"module.framework.runtime.start.level",
					"module.framework.runtime.start.level"
			},
			{
					"module.framework.services.ignored.interfaces",
					"module.framework.services.ignored.interfaces"
			},
			{"module.framework.static.jars", "module.framework.static.jars"},
			{
					"module.framework.system.packages.extra",
					"module.framework.system.packages.extra"
			},
			{"module.framework.web.start.level", "module.framework.web.start.level"}
	};

	public static final String[][] _RENAMED_PORTAL_KEYS_7_0 = {
			{"amazon.license.0", "amazon.access.key.id"},
			{"amazon.license.1", "amazon.access.key.id"},
			{"amazon.license.2", "amazon.access.key.id"},
			{"amazon.license.3", "amazon.access.key.id"},
			{"cdn.host", "cdn.host.http"},
			{"cluster.executor.debug.enabled", "cluster.link.debug.enabled"},
			{
					"com.liferay.portal.servlet.filters.compression.CompressionFilter",
					"com.liferay.portal.servlet.filters.gzip.GZipFilter"
			},
			{
					"default.guest.friendly.url",
					"default.guest.public.layout.friendly.url"
			},
			{"default.guest.layout.column", "default.guest.public.layout.column"},
			{"default.guest.layout.name", "default.guest.public.layout.name"},
			{
					"default.guest.layout.template.id",
					"default.guest.public.layout.template.id"
			},
			{"default.user.layout.column", "default.user.public.layout.column"},
			{"default.user.layout.name", "default.user.public.layout.name"},
			{
					"default.user.layout.template.id",
					"default.user.public.layout.template.id"
			},
			{"default.user.private.layout.lar", "default.user.private.layouts.lar"},
			{"default.user.public.layout.lar", "default.user.public.layouts.lar"},
			{
					"dl.hook.cmis.credentials.password",
					"dl.store.cmis.credentials.password"
			},
			{
					"dl.hook.cmis.credentials.username",
					"dl.store.cmis.credentials.username"
			},
			{"dl.hook.cmis.repository.url", "dl.store.cmis.repository.url"},
			{"dl.hook.cmis.system.root.dir", "dl.store.cmis.system.root.dir"},
			{"dl.hook.file.system.root.dir", "dl.store.file.system.root.dir"},
			{"dl.hook.impl", "dl.store.impl"},
			{"dl.hook.jcr.fetch.delay", "dl.store.jcr.fetch.delay"},
			{"dl.hook.jcr.fetch.max.failures", "dl.store.jcr.fetch.max.failures"},
			{"dl.hook.jcr.move.version.labels", "dl.store.jcr.move.version.labels"},
			{"dl.hook.s3.access.key", "dl.store.s3.access.key"},
			{"dl.hook.s3.bucket.name", "dl.store.s3.bucket.name"},
			{"dl.hook.s3.secret.key", "dl.store.s3.secret.key"},
			{
					"editor.wysiwyg.portal-web.docroot.html.portlet.calendar." +
							"edit_configuration.jsp",
					"editor.wysiwyg.portal-web.docroot.html.portlet.calendar." +
							"configuration.jsp"
			},
			{
					"editor.wysiwyg.portal-web.docroot.html.portlet.invitation." +
							"edit_configuration.jsp",
					"editor.wysiwyg.portal-web.docroot.html.portlet.invitation." +
							"configuration.jsp"
			},
			{
					"editor.wysiwyg.portal-web.docroot.html.portlet.journal." +
							"edit_configuration.jsp",
					"editor.wysiwyg.portal-web.docroot.html.portlet.journal." +
							"configuration.jsp"
			},
			{
					"editor.wysiwyg.portal-web.docroot.html.portlet.message_boards." +
							"edit_configuration.jsp",
					"editor.wysiwyg.portal-web.docroot.html.portlet.message_boards." +
							"configuration.jsp"
			},
			{
					"editor.wysiwyg.portal-web.docroot.html.portlet.shopping." +
							"edit_configuration.jsp",
					"editor.wysiwyg.portal-web.docroot.html.portlet.shopping." +
							"configuration.jsp"
			},
			{
					"ehcache.cluster.link.replicator.properties",
					"ehcache.replicator.properties"
			},
			{
					"ehcache.cluster.link.replicator.properties.default",
					"ehcache.replicator.properties.default"
			},
			{
					"field.editable.com.liferay.portal.kernel.model.User.emailAddress",
					"field.editable.user.types"
			},
			{
					"field.editable.com.liferay.portal.kernel.model.User.screenName",
					"field.editable.user.types"
			},
			{"icon.menu.max.display.items", "menu.max.display.items"},
			{"journal.error.template.freemarker", "journal.error.template[ftl]"},
			{"journal.error.template.velocity", "journal.error.template[vm]"},
			{"journal.error.template.xsl", "journal.error.template[xsl]"},
			{
					"journal.template.velocity.restricted.variables",
					"velocity.engine.restricted.variables"
			},
			{
					"passwords.passwordpolicytoolkit.charset.lowercase",
					"passwords.passwordpolicytoolkit.validator.charset.lowercase"
			},
			{
					"passwords.passwordpolicytoolkit.charset.numbers",
					"passwords.passwordpolicytoolkit.validator.charset.numbers"
			},
			{
					"passwords.passwordpolicytoolkit.charset.symbols",
					"passwords.passwordpolicytoolkit.validator.charset.symbols"
			},
			{
					"passwords.passwordpolicytoolkit.charset.uppercase",
					"passwords.passwordpolicytoolkit.validator.charset.uppercase"
			},
			{
					"permissions.inline.sql.resource.block.query.threshhold",
					"permissions.inline.sql.resource.block.query.threshold"
			},
			{"portal.instance.http.port", "portal.instance.http.socket.address"},
			{"portal.instance.https.port", "portal.instance.http.socket.address"},
			{"referer.url.domains.allowed", "redirect.url.domains.allowed"},
			{"referer.url.ips.allowed", "redirect.url.ips.allowed"},
			{"referer.url.security.mode", "redirect.url.security.mode"},
			{
					"tags.asset.increment.view.counter.enabled",
					"asset.entry.increment.view.counter.enabled"
			}
		};

	public static final String[][] _RENAMED_PORTAL_KEYS_7_3 = {
			{"buffered.increment.enabled", "view.count.enabled"},
			{
					"module.framework.properties.felix.fileinstall.bundles.new.start",
					"module.framework.properties.file.install.bundles.new.start"
			},
			{
					"module.framework.properties.felix.fileinstall.bundles." +
							"startActivationPolicy",
					"module.framework.properties.file.install.bundles." +
							"startActivationPolicy"
			},
			{
					"module.framework.properties.felix.fileinstall.bundles." +
							"startTransient",
					"module.framework.properties.file.install.bundles.startTransient"
			},
			{
					"module.framework.properties.felix.fileinstall.disableNio2",
					"module.framework.properties.file.install.disableNio2"
			},
			{
					"module.framework.properties.felix.fileinstall.log.level",
					"module.framework.properties.file.install.log.level"
			},
			{
					"module.framework.properties.felix.fileinstall.noInitialDelay",
					"module.framework.properties.file.install.noInitialDelay"
			},
			{
					"module.framework.properties.felix.fileinstall.subdir.mode",
					"module.framework.properties.file.install.subdir.mode"
			}
	};

	public static final String[][] _RENAMED_PORTAL_KEYS_7_4 = {
			{
					"com.liferay.portal.servlet.filters.urlrewrite.UrlRewriteFilter",
					"com.liferay.portal.url.rewrite.filter.internal.URLRewriteFilter"
			},
			{
					"com.liferay.portal.upload.LiferayFileItem.threshold.size",
					"com.liferay.portal.kernel.upload.FileItem.threshold.size"
			},
			{
					"module.framework.properties.dependency.manager.sync.timeout",
					"dependency.manager.sync.timeout"
			},
			{
					"module.framework.properties.dependency.manager.thread.pool." +
							"enabled",
					"dependency.manager.thread.pool.enabled"
			},
			{
					"module.framework.properties.felix.fileinstall.bundles.new.start",
					"module.framework.file.install.bundles.start.new"
			},
			{
					"module.framework.properties.file.install.bundles.new.start",
					"module.framework.file.install.bundles.start.new"
			},
			{
					"module.framework.properties.felix.fileinstall.bundles." +
							"startActivationPolicy",
					"module.framework.file.install.bundles.use.start.activation.policy"
			},
			{
					"module.framework.properties.file.install.bundles." +
							"startActivationPolicy",
					"module.framework.file.install.bundles.use.start.activation.policy"
			},
			{
					"module.framework.properties.felix.fileinstall.bundles." +
							"startTransient",
					"module.framework.file.install.bundles.start.transient"
			},
			{
					"module.framework.properties.file.install.bundles.startTransient",
					"module.framework.file.install.bundles.start.transient"
			},
			{
					"module.framework.properties.felix.fileinstall.noInitialDelay",
					"module.framework.file.install.no.initial.delay"
			},
			{
					"module.framework.properties.file.install.noInitialDelay",
					"module.framework.file.install.no.initial.delay"
			},
			{
					"module.framework.properties.felix.fileinstall.subdir.mode",
					"module.framework.file.install.subdir.mode"
			},
			{
					"module.framework.properties.file.install.subdir.mode",
					"module.framework.file.install.subdir.mode"
			},
			{
					"module.framework.properties.initial.system.check.enabled",
					"initial.system.check.enabled"
			},
			{"sql.data.max.parameters", "database.max.parameters"}
	};

	public static final String[] _OBSOLETE_PORTAL_KEYS_7_0 = {
			"aim.login", "amazon.access.key.id",
			"amazon.associate.tag", "amazon.secret.access.key",
			"asset.entry.increment.view.counter.enabled", "asset.entry.validator",
			"asset.publisher.asset.entry.query.processors",
			"asset.publisher.filter.unlistable.entries",
			"asset.publisher.query.form.configuration",
			"asset.tag.permissions.enabled", "asset.tag.properties.default",
			"asset.tag.properties.enabled", "asset.tag.suggestions.enabled",
			"auth.max.failures.limit", "auto.deploy.blacklist.threshold",
			"blogs.image.small.max.size", "breadcrumb.display.style.options",
			"buffered.increment.parallel.queue.size",
			"buffered.increment.serial.queue.size", "cas.validate.url",
			"cluster.executor.heartbeat.interval",
			"com.liferay.filters.doubleclick.DoubleClickFilter",
			"com.liferay.portal.servlet.filters.audit.AuditFilter",
			"com.liferay.portal.servlet.filters.doubleclick.DoubleClickFilter",
			"com.liferay.portal.servlet.filters.charbufferpool." +
					"CharBufferPoolFilter",
			"com.liferay.portal.servlet.filters.monitoring.MonitoringFilter",
			"com.liferay.portal.servlet.filters.secure.SecureFilter",
			"com.liferay.portal.servlet.filters.validhtml.ValidHtmlFilter",
			"commons.pool.enabled", "company.settings.form.configuration",
			"company.settings.form.identification",
			"company.settings.form.miscellaneous", "company.settings.form.social",
			"control.panel.home.portlet.id", "convert.processes",
			"default.guest.public.layout.wap.color.scheme.id",
			"default.guest.public.layout.wap.theme.id",
			"default.user.private.layout.wap.color.scheme.id",
			"default.user.private.layout.wap.theme.id",
			"default.user.public.layout.wap.color.scheme.id",
			"default.user.public.layout.wap.theme.id",
			"default.wap.color.scheme.id", "default.wap.theme.id",
			"discussion.thread.view", "dl.file.entry.read.count.enabled",
			"dl.folder.menu.visible", "dockbar.add.portlets",
			"dockbar.administrative.links.show.in.pop.up",
			"dynamic.data.lists.record.set.force.autogenerate.key",
			"dynamic.data.lists.template.language.parser[ftl]",
			"dynamic.data.lists.template.language.parser[vm]",
			"dynamic.data.lists.template.language.parser[xsl]",
			"dynamic.data.mapping.structure.index.with.thread",
			"dynamic.data.mapping.structure.private.field.names",
			"dynamic.data.mapping.structure.private.field.datatype[_fieldsDisplay]",
			"dynamic.data.mapping.structure.private.field.repeatable[" +
					"_fieldsDisplay]",
			"dynamic.data.mapping.template.language.types",
			"editor.ckeditor.version", "editor.inline.editing.enabled",
			"editor.wysiwyg.portal-web.docroot.html.portlet.asset_publisher." +
					"configuration.jsp",
			"editor.wysiwyg.portal-web.docroot.html.portlet.blogs.configuration." +
					"jsp",
			"editor.wysiwyg.portal-web.docroot.html.portlet.bookmarks." +
					"configuration.jsp",
			"editor.wysiwyg.portal-web.docroot.html.portlet.document_library." +
					"editor.wysiwyg.portal-web.docroot.html.portlet.invitation." +
					"configuration.jsp",
			"editor.wysiwyg.portal-web.docroot.html.portlet.journal." +
					"configuration.jsp",
			"editor.wysiwyg.portal-web.docroot.html.portlet.login.configuration." +
					"jsp",
			"editor.wysiwyg.portal-web.docroot.html.portlet.message_boards." +
					"configuration.jsp",
			"editor.wysiwyg.portal-web.docroot.html.portlet.portal_settings." +
					"email_notifications.jsp",
			"ehcache.bootstrap.cache.loader.factory",
			"ehcache.cache.event.listener.factory",
			"ehcache.cache.manager.peer.listener.factory",
			"ehcache.cache.manager.peer.provider.factory",
			"ehcache.cache.manager.statistics.thread.pool.size",
			"ehcache.multi.vm.config.location.peerProviderProperties",
			"ehcache.statistics.enabled",
			"hot.deploy.hook.custom.jsp.verification.enabled",
			"hibernate.cache.region.factory_class",
			"hibernate.cache.use_minimal_puts", "hibernate.cache.use_query_cache",
			"hibernate.cache.use_second_level_cache",
			"hibernate.cache.use_structured_entries", "icq.jar", "icq.login",
			"icq.password", "index.filter.search.limit",
			"index.portal.field.analyzer.enabled", "index.search.highlight.enabled",
			"index.read.only", "invitation.email.max.recipients",
			"invitation.email.message.body", "invitation.email.message.subject",
			"invoker.filter.chain.cache.size", "javax.persistence.validation.mode",
			"jbi.workflow.url", "json.deserializer.strict.mode",
			"journal.article.form.add", "journal.article.form.default.values",
			"journal.article.form.update", "journal.article.form.translate",
			"journal.article.types", "journal.articles.page.delta.values",
			"journal.browse.by.structures.sorted.by.name",
			"journal.template.language.parser[css]",
			"journal.template.language.parser[ftl]",
			"journal.template.language.parser[vm]",
			"journal.template.language.parser[xsl]",
			"journal.template.language.types", "jpa.configs",
			"jpa.database.platform", "jpa.database.type", "jpa.load.time.weaver",
			"jpa.provider", "jpa.provider.property.eclipselink.allow-zero-id",
			"jpa.provider.property.eclipselink.logging.level",
			"jpa.provider.property.eclipselink.logging.timestamp",
			"language.display.style.options", "layout.edit.page[control_panel]",
			"layout.first.pageable[control_panel]", "layout.form.add",
			"layout.form.update",
			"layout.parallel.render.thread.pool.allow.core.thread.timeout",
			"layout.parentable[control_panel]", "layout.reset.portlet.ids",
			"layout.set.form.update", "layout.types", "layout.url[control_panel]",
			"layout.url.friendliable[control_panel]",
			"layout.view.page[control_panel]", "library.download.url.resin.jar",
			"library.download.url.script-10.jar", "lucene.analyzer",
			"lucene.cluster.index.loading.sync.timeout", "lucene.file.extractor",
			"lucene.file.extractor.regexp.strip", "lucene.replicate.write",
			"lucene.store.jdbc.auto.clean.up",
			"lucene.store.jdbc.auto.clean.up.enabled",
			"lucene.store.jdbc.auto.clean.up.interval",
			"lucene.store.jdbc.dialect.db2", "lucene.store.jdbc.dialect.derby",
			"lucene.store.jdbc.dialect.hsqldb", "lucene.store.jdbc.dialect.jtds",
			"lucene.store.jdbc.dialect.microsoft",
			"lucene.store.jdbc.dialect.mysql", "lucene.store.jdbc.dialect.oracle",
			"lucene.store.jdbc.dialect.postgresql", "mail.hook.cyrus.add.user",
			"mail.hook.cyrus.delete.user", "mail.hook.cyrus.home",
			"mail.hook.fusemail.account.type", "mail.hook.fusemail.group.parent",
			"mail.hook.fusemail.password", "mail.hook.fusemail.url",
			"mail.hook.fusemail.username",
			"memory.cluster.scheduler.lock.cache.enabled",
			"message.boards.email.message.added.signature",
			"message.boards.email.message.updated.signature",
			"message.boards.thread.locking.enabled",
			"message.boards.thread.previous.and.next.navigation.enabled",
			"message.boards.thread.views", "message.boards.thread.views.default",
			"microsoft.translator.client.id", "microsoft.translator.client.secret",
			"minifier.inline.content.cache.size",
			"mobile.device.styling.wap.enabled", "module.framework.initial.bundles",
			"msn.login", "msn.password", "multicast.group.address[\"hibernate\"]",
			"multicast.group.port[\"hibernate\"]",
			"net.sf.ehcache.configurationResourceName",
			"net.sf.ehcache.configurationResourceName.peerProviderProperties",
			"organizations.form.add.identification", "organizations.form.add.main",
			"organizations.form.add.miscellaneous",
			"organizations.form.update.identification",
			"organizations.form.update.main",
			"organizations.form.update.miscellaneous",
			"organizations.indexer.enabled", "portal.cache.manager.type.multi.vm",
			"portal.cache.manager.type.single.vm", "portal.ctx",
			"portal.security.manager.enable", "permissions.list.filter",
			"permissions.thread.local.cache.max.size",
			"permissions.user.check.algorithm", "persistence.provider",
			"ratings.max.score", "ratings.min.score", "sandbox.deploy.dir",
			"sandbox.deploy.enabled", "sandbox.deploy.interval",
			"sandbox.deploy.listeners", "sc.image.max.size",
			"sc.image.thumbnail.max.height", "sc.image.thumbnail.max.width",
			"sc.product.comments.enabled", "scheduler.classes",
			"schema.run.minimal", "search.container.page.iterator.page.values",
			"service.builder.service.read.only.prefixes", "setup.database.types",
			"shard.available.names", "shard.default.name", "shard.selector",
			"siteminder.auth.enabled", "siteminder.import.from.ldap",
			"siteminder.user.header", "sites.form.add.advanced",
			"sites.form.add.main", "sites.form.add.miscellaneous",
			"sites.form.add.seo", "sites.form.update.advanced",
			"sites.form.update.main", "sites.form.update.miscellaneous",
			"sites.form.update.seo", "staging.lock.enabled",
			"social.activity.sets.bundling.enabled",
			"table.mapper.cache.mapping.table.names", "tck.url",
			"user.groups.indexer.enabled", "users.form.add.identification",
			"users.indexer.enabled", "users.form.add.main",
			"users.form.add.miscellaneous", "users.form.my.account.identification",
			"users.form.my.account.main", "users.form.my.account.miscellaneous",
			"users.form.update.identification", "users.form.update.main",
			"users.form.update.miscellaneous", "vaadin.resources.path",
			"vaadin.theme", "vaadin.widgetset",
			"value.object.finder.blocking.cache", "webdav.storage.class",
			"webdav.storage.show.edit.url", "webdav.storage.show.view.url",
			"webdav.storage.tokens", "wiki.email.page.added.signature",
			"wiki.email.page.updated.signature", "xss.allow", "ym.login",
			"ym.password"
	};

	public static final String[] _OBSOLETE_PORTAL_KEYS_7_4 = {
			"dl.file.entry.processors"
	};


	public static final String[][] _MODULARIZED_PORTAL_KEYS_7_0 = {

			// Asset

			{
					"asset.browser.search.with.database", "search.with.database",
					"com.liferay.asset.browser.web"
			},
			{
					"asset.categories.navigation.display.templates.config",
					"display.templates.config",
					"com.liferay.asset.categories.navigation.web"
			},
			{
					"asset.publisher.check.interval", "check.interval",
					"com.liferay.asset.publisher.web"
			},
			{
					"asset.publisher.email.from.address", "email.from.address",
					"com.liferay.asset.publisher.web"
			},
			{
					"asset.publisher.email.from.name", "email.from.name",
					"com.liferay.asset.publisher.web"
			},
			{
					"asset.publisher.email.asset.entry.added.enabled",
					"email.asset.entry.added.enabled", "com.liferay.asset.publisher.web"
			},
			{
					"asset.publisher.email.asset.entry.added.subject",
					"email.asset.entry.added.subject", "com.liferay.asset.publisher.web"
			},
			{
					"asset.publisher.email.asset.entry.added.body",
					"email.asset.entry.added.body", "com.liferay.asset.publisher.web"
			},
			{
					"asset.publisher.display.style.default", "display.style.default",
					"com.liferay.asset.publisher.web"
			},
			{
					"asset.publisher.display.styles", "display.styles",
					"com.liferay.asset.publisher.web"
			},
			{
					"asset.publisher.display.templates.config",
					"display.templates.config", "com.liferay.asset.publisher.web"
			},
			{
					"asset.publisher.dynamic.subscription.limit",
					"dynamic.subscription.limit", "com.liferay.asset.publisher.web"
			},
			{
					"asset.publisher.permission.checking.configurable",
					"permission.checking.configurable",
					"com.liferay.asset.publisher.web"
			},
			{
					"asset.publisher.search.with.index", "search.with.index",
					"com.liferay.asset.publisher.web"
			},
			{
					"asset.tags.navigation.display.templates.config",
					"display.templates.config", "com.liferay.asset.tags.navigation.web"
			},

			// Authentication Verifier

			{
					"auth.verifier.BasicAuthHeaderAutoLogin.basic_auth",
					"auth.verifier.BasicAuthHeaderAuthVerifier.basic_auth",
					"com.liferay.portal.security.auth.verifier"
			},
			{
					"auth.verifier.BasicAuthHeaderAutoLogin.hosts.allowed",
					"auth.verifier.BasicAuthHeaderAuthVerifier.hosts.allowed",
					"com.liferay.portal.security.auth.verifier"
			},
			{
					"auth.verifier.BasicAuthHeaderAutoLogin.urls.excludes",
					"auth.verifier.BasicAuthHeaderAuthVerifier.urls.excludes",
					"com.liferay.portal.security.auth.verifier"
			},
			{
					"auth.verifier.BasicAuthHeaderAutoLogin.urls.includes",
					"auth.verifier.BasicAuthHeaderAuthVerifier.urls.includes",
					"com.liferay.portal.security.auth.verifier"
			},

			{
					"auth.verifier.DigestAuthenticationAuthVerifier.digest_auth",
					"auth.verifier.DigestAuthenticationAuthVerifier.digest_auth",
					"com.liferay.portal.security.auth.verifier"
			},
			{
					"auth.verifier.DigestAuthenticationAuthVerifier.hosts.allowed",
					"auth.verifier.DigestAuthenticationAuthVerifier.hosts.allowed",
					"com.liferay.portal.security.auth.verifier"
			},
			{
					"auth.verifier.DigestAuthenticationAuthVerifier.urls.excludes",
					"auth.verifier.DigestAuthenticationAuthVerifier.urls.excludes",
					"com.liferay.portal.security.auth.verifier"
			},
			{
					"auth.verifier.DigestAuthenticationAuthVerifier.urls.includes",
					"auth.verifier.DigestAuthenticationAuthVerifier.urls.includes",
					"com.liferay.portal.security.auth.verifier"
			},

			{
					"auth.verifier.ParameterAutoLogin.hosts.allowed",
					"auth.verifier.RequestParameterAuthVerifier.hosts.allowed",
					"com.liferay.portal.security.auth.verifier"
			},
			{
					"auth.verifier.ParameterAutoLogin.urls.excludes",
					"auth.verifier.RequestParameterAuthVerifier.urls.excludes",
					"com.liferay.portal.security.auth.verifier"
			},
			{
					"auth.verifier.ParameterAutoLogin.urls.includes",
					"auth.verifier.RequestParameterAuthVerifier.urls.includes",
					"com.liferay.portal.security.auth.verifier"
			},

			{
					"auth.verifier.PortalSessionAuthVerifier.hosts.allowed",
					"auth.verifier.PortalSessionAuthVerifier.hosts.allowed",
					"com.liferay.portal.security.auth.verifier"
			},
			{
					"auth.verifier.PortalSessionAuthVerifier.urls.excludes",
					"auth.verifier.PortalSessionAuthVerifier.urls.excludes",
					"com.liferay.portal.security.auth.verifier"
			},
			{
					"auth.verifier.PortalSessionAuthVerifier.urls.includes",
					"auth.verifier.PortalSessionAuthVerifier.urls.includes",
					"com.liferay.portal.security.auth.verifier"
			},

			{
					"auth.verifier.TunnelingServletAuthVerifier.hosts.allowed",
					"auth.verifier.TunnelAuthVerifier.hosts.allowed",
					"com.liferay.portal.security.auth.verifier"
			},
			{
					"auth.verifier.TunnelingServletAuthVerifier.urls.excludes",
					"auth.verifier.TunnelAuthVerifier.urls.excludes",
					"com.liferay.portal.security.auth.verifier"
			},
			{
					"auth.verifier.TunnelingServletAuthVerifier.urls.includes",
					"auth.verifier.TunnelAuthVerifier.urls.includes",
					"com.liferay.portal.security.auth.verifier"
			},

			// Blogs

			{
					"blogs.display.templates.config", "display.templates.config",
					"com.liferay.blogs.web"
			},

			{
					"blogs.entry.check.interval", "entry.check.interval",
					"com.liferay.blogs.web"
			},

			{
					"blogs.linkback.job.interval", "linkback.job.interval",
					"com.liferay.blogs.web"
			},

			// Bookmarks

			{
					"bookmarks.email.entry.added.body", "email.entry.added.body",
					"com.liferay.bookmarks.service"
			},
			{
					"bookmarks.email.entry.added.enabled", "email.entry.added.enabled",
					"com.liferay.bookmarks.service"
			},
			{
					"bookmarks.email.entry.added.subject", "email.entry.added.subject",
					"com.liferay.bookmarks.service"
			},
			{
					"bookmarks.email.entry.updated.body", "email.entry.updated.body",
					"com.liferay.bookmarks.service"
			},
			{
					"bookmarks.email.entry.updated.enabled",
					"email.entry.updated.enabled", "com.liferay.bookmarks.service"
			},
			{
					"bookmarks.email.entry.updated.subject",
					"email.entry.updated.subject", "com.liferay.bookmarks.service"
			},
			{
					"bookmarks.email.from.address", "email.from.address",
					"com.liferay.bookmarks.service"
			},
			{
					"bookmarks.email.from.name", "email.from.name",
					"com.liferay.bookmarks.service"
			},
			{
					"bookmarks.entry.columns", "entry.columns",
					"com.liferay.bookmarks.service"
			},
			{
					"bookmarks.folder.columns", "folder.columns",
					"com.liferay.bookmarks.service"
			},
			{
					"bookmarks.folders.search.visible", "folders.search.visible",
					"com.liferay.bookmarks.service"
			},
			{
					"bookmarks.related.assets.enabled", "related.assets.enabled",
					"com.liferay.bookmarks.service"
			},
			{
					"bookmarks.subfolders.visible", "subfolders.visible",
					"com.liferay.bookmarks.service"
			},

			// Breadcrumb

			{
					"breadcrumb.display.style.default", "ddm.template.key.default",
					"com.liferay.site.navigation.breadcrumb.web"
			},
			{
					"breadcrumb.display.templates.config", "display.templates.config",
					"com.liferay.site.navigation.breadcrumb.web"
			},
			{
					"breadcrumb.show.guest.group", "show.guest.group",
					"com.liferay.site.navigation.breadcrumb.web"
			},
			{
					"breadcrumb.show.parent.groups", "show.parent.groups",
					"com.liferay.site.navigation.breadcrumb.web"
			},

			// CAS

			{"cas.auth.enabled", "enabled", "com.liferay.portal.security.sso.cas"},
			{
					"cas.import.from.ldap", "import.from.ldap",
					"com.liferay.portal.security.sso.cas"
			},
			{"cas.login.url", "login.url", "com.liferay.portal.security.sso.cas"},
			{
					"cas.logout.on.session.expiration", "logout.on.session.expiration",
					"com.liferay.portal.security.sso.cas"
			},
			{
					"cas.logout.url", "logout.url",
					"com.liferay.portal.security.sso.cas"
			},
			{
					"cas.no.such.user.redirect.url", "no.such.user.redirect.url",
					"com.liferay.portal.security.sso.cas"
			},
			{
					"cas.server.name", "server.name",
					"com.liferay.portal.security.sso.cas"
			},
			{
					"cas.server.url", "server.url",
					"com.liferay.portal.security.sso.cas"
			},
			{
					"cas.service.url", "service.url",
					"com.liferay.portal.security.sso.cas"
			},

			// Cluster Link

			{
					"cluster.link.debug.enabled", "cluster.link.debug.enabled",
					"com.liferay.portal.cluster"
			},

			// Currency Converter

			{
					"currency.converter.symbols", "symbols",
					"com.liferay.currency.converter.web"
			},

			// Document Library

			{
					"dl.display.templates.config", "display.templates.config",
					"com.liferay.document.library.web"
			},
			{
					"dl.repository.cmis.delete.depth", "delete.depth",
					"com.liferay.document.library.repository.cmis"
			},
			{
					"dl.store.advanced.file.system.root.dir", "root.dir",
					"com.liferay.portal.store.filesystem"
			},
			{
					"dl.store.cmis.credentials.username", "credentials.username",
					"com.liferay.portal.store.cmis"
			},
			{
					"dl.store.cmis.credentials.password", "credentials.password",
					"com.liferay.portal.store.cmis"
			},
			{
					"dl.store.cmis.repository.url", "repository.url",
					"com.liferay.portal.store.cmis"
			},
			{
					"dl.store.cmis.system.root.dir", "system.root.dir",
					"com.liferay.portal.store.cmis"
			},
			{
					"dl.store.file.system.root.dir", "root.dir",
					"com.liferay.portal.store.filesystem"
			},
			{
					"dl.store.jcr.fetch.delay", "fetch.delay",
					"com.liferay.portal.store.jcr"
			},
			{
					"dl.store.jcr.fetch.max.failures", "fetch.max.failures",
					"com.liferay.portal.store.jcr"
			},
			{
					"dl.store.jcr.move.version.labels", "move.version.labels",
					"com.liferay.portal.store.jcr"
			},
			{
					"dl.store.s3.access.key", "access.key",
					"com.liferay.portal.store.s3"
			},
			{
					"dl.store.s3.bucket.name", "bucket.name",
					"com.liferay.portal.store.s3"
			},
			{
					"dl.store.s3.jets3t[httpclient.max-connections]",
					"http.client.max.connections", "com.liferay.portal.store.s3"
			},
			{
					"dl.store.s3.jets3t[s3service.default-bucket-location]",
					"s3service.default.bucket.location", "com.liferay.portal.store.s3"
			},
			{
					"dl.store.s3.jets3t[s3service.default-storage-class]",
					"s3service.default.storage.class", "com.liferay.portal.store.s3"
			},
			{
					"dl.store.s3.jets3t[s3service.s3-endpoint]",
					"s3service.s3.endpoint", "com.liferay.portal.store.s3"
			},
			{
					"dl.store.s3.secret.key", "secret.key",
					"com.liferay.portal.store.s3"
			},
			{
					"dl.store.s3.temp.dir.clean.up.expunge",
					"temp.dir.clean.up.expunge", "com.liferay.portal.store.s3"
			},
			{
					"dl.store.s3.temp.dir.clean.up.frequency",
					"temp.dir.clean.up.frequency", "com.liferay.portal.store.s3"
			},
			{
					"dl.temporary.file.entries.check.interval",
					"temporary.file.entries.check.interval",
					"com.liferay.document.library.web"
			},

			// Dynamic Data Lists

			{
					"dynamic.data.lists.error.template",
					"dynamic.data.lists.error.template",
					"com.liferay.dynamic.data.lists.web"
			},
			{
					"dynamic.data.lists.storage.type",
					"dynamic.data.lists.storage.type",
					"com.liferay.dynamic.data.lists.web"
			},

			// Dynamic Data Mapping

			{
					"dynamic.data.mapping.image.extensions",
					"dynamic.data.mapping.image.extensions",
					"com.liferay.dynamic.data.mapping.service"
			},
			{
					"dynamic.data.mapping.image.small.max.size",
					"dynamic.data.mapping.image.small.max.size",
					"com.liferay.dynamic.data.mapping.service"
			},
			{
					"dynamic.data.mapping.structure.force.autogenerate.key",
					"dynamic.data.mapping.structure.force.autogenerate.key",
					"com.liferay.dynamic.data.mapping.web"
			},
			{
					"dynamic.data.mapping.template.force.autogenerate.key",
					"dynamic.data.mapping.template.force.autogenerate.key",
					"com.liferay.dynamic.data.mapping.web"
			},
			{
					"dynamic.data.mapping.template.language.default",
					"dynamic.data.mapping.template.language.default",
					"com.liferay.dynamic.data.mapping.web"
			},
			{
					"dynamic.data.mapping.template.language.content",
					"dynamic.data.mapping.template.language.content",
					"com.liferay.dynamic.data.mapping.web"
			},

			// Facebook Connect

			{
					"facebook.connect.auth.enabled", "enabled",
					"com.liferay.portal.security.sso.facebook.connect"
			},
			{
					"facebook.connect.app.id", "app.id",
					"com.liferay.portal.security.sso.facebook.connect"
			},
			{
					"facebook.connect.app.secret", "app.secret",
					"com.liferay.portal.security.sso.facebook.connect"
			},
			{
					"facebook.connect.graph.url", "graph.url",
					"com.liferay.portal.security.sso.facebook.connect"
			},
			{
					"facebook.connect.oauth.auth.url", "oauth.auth.url",
					"com.liferay.portal.security.sso.facebook.connect"
			},
			{
					"facebook.connect.oauth.redirect.url", "oauth.redirect.url",
					"com.liferay.portal.security.sso.facebook.connect"
			},
			{
					"facebook.connect.oauth.token.url", "oauth.token.url",
					"com.liferay.portal.security.sso.facebook.connect"
			},
			{
					"facebook.connect.verified.account.required",
					"verified.account.required",
					"com.liferay.portal.security.sso.facebook.connect"
			},

			// Flags

			{"flags.email.body", "email.body", "com.liferay.flags"},
			{
					"flags.email.from.address", "email.from.address",
					"com.liferay.flags"
			},
			{"flags.email.from.name", "email.from.name", "com.liferay.flags"},
			{"flags.email.subject", "email.subject", "com.liferay.flags"},
			{
					"flags.guest.users.enabled", "guest.users.enabled",
					"com.liferay.flags"
			},
			{"flags.reasons", "reasons", "com.liferay.flags"},

			// FreeMarker Engine

			{
					"freemarker.engine.localized.lookup", "localized.lookup",
					"com.liferay.portal.template.freemarker"
			},
			{
					"freemarker.engine.macro.library", "macro.library",
					"com.liferay.portal.template.freemarker"
			},
			{
					"freemarker.engine.resource.modification.check.interval",
					"resource.modification.check",
					"com.liferay.portal.template.freemarker"
			},
			{
					"freemarker.engine.restricted.classes", "restricted.classes",
					"com.liferay.portal.template.freemarker"
			},
			{
					"freemarker.engine.restricted.packages", "restricted.packages",
					"com.liferay.portal.template.freemarker"
			},
			{
					"freemarker.engine.template.exception.handler",
					"template.exception.handler",
					"com.liferay.portal.template.freemarker"
			},
			{
					"freemarker.engine.template.parsers", "template.parsers",
					"com.liferay.portal.template.freemarker"
			},
			{
					"journal.template.freemarker.restricted.variables",
					"restricted.variables", "com.liferay.portal.template.freemarker"
			},

			// IFrame

			{"iframe.auth", "auth", "com.liferay.iframe.web"},
			{"iframe.auth-type", "auth.type", "com.liferay.iframe.web"},
			{"iframe.form-method", "form.method", "com.liferay.iframe.web"},
			{
					"iframe.hidden-variables", "hidden.variables",
					"com.liferay.iframe.web"
			},

			// JCR

			{
					"jcr.initialize.on.startup", "initialize.on.startup",
					"com.liferay.portal.store.jcr"
			},
			{
					"jcr.jackrabbit.config.file.path", "jackrabbit.config.file.path",
					"com.liferay.portal.store.jcr"
			},
			{
					"jcr.jackrabbit.credentials.password",
					"jackrabbit.credentials.password", "com.liferay.portal.store.jcr"
			},
			{
					"jcr.jackrabbit.credentials.username",
					"jackrabbit.credentials.username", "com.liferay.portal.store.jcr"
			},
			{
					"jcr.jackrabbit.repository.home", "repository.home",
					"com.liferay.portal.store.jcr"
			},
			{
					"jcr.jackrabbit.repository.root", "repository.root",
					"com.liferay.portal.store.jcr"
			},
			{
					"jcr.node.documentlibrary", "node.documentlibrary",
					"com.liferay.portal.store.jcr"
			},
			{
					"jcr.workspace.name", "workspace.name",
					"com.liferay.portal.store.jcr"
			},
			{"jcr.wrap.session", "wrap.session", "com.liferay.portal.store.jcr"},

			// Journal

			{
					"journal.article.check.interval", "check.interval",
					"com.liferay.journal.web"
			},
			{
					"journal.article.comments.enabled",
					"journal.article.comments.enabled", "com.liferay.journal.service"
			},
			{
					"journal.article.custom.tokens", "journal.article.custom.tokens",
					"com.liferay.journal.service"
			},
			{
					"journal.article.database.keyword.search.content",
					"journal.article.database.keyword.search.content",
					"com.liferay.journal.service"
			},
			{
					"journal.article.expire.all.versions",
					"journal.article.expire.all.versions", "com.liferay.journal.service"
			},
			{
					"journal.article.force.autogenerate.id",
					"journal.article.force.autogenerate.id", "com.liferay.journal.web"
			},
			{
					"journal.articles.search.with.index",
					"journal.articles.search.with.index", "com.liferay.journal.web"
			},
			{
					"journal.article.storage.type", "journal.article.storage.type",
					"com.liferay.journal.service"
			},
			{
					"journal.article.token.page.break",
					"journal.article.token.page.break", "com.liferay.journal.service"
			},
			{
					"journal.article.view.permission.check.enabled",
					"journal.article.view.permission.check.enabled",
					"com.liferay.journal.service"
			},
			{
					"journal.articles.index.all.versions",
					"journal.articles.index.all.versions", "com.liferay.journal.service"
			},
			{
					"journal.char.blacklist", "char.blacklist",
					"com.liferay.journal.service"
			},
			{
					"journal.content.publish.to.live.by.default",
					"publish.to.live.by.default", "com.liferay.journal.content.web"
			},
			{
					"journal.content.search.show.listed", "show.listed",
					"com.liferay.journal.content.search.web"
			},
			{
					"journal.default.display.view", "default.display.view",
					"com.liferay.journal.web"
			},
			{"journal.display.views", "display.views", "com.liferay.journal.web"},
			{
					"journal.email.from.address", "email.from.address",
					"com.liferay.journal.service"
			},
			{
					"journal.email.from.name", "email.from.name",
					"com.liferay.journal.service"
			},
			{
					"journal.email.article.added.enabled",
					"email.article.added.enabled", "com.liferay.journal.service"
			},
			{
					"journal.email.article.added.subject",
					"email.article.added.subject", "com.liferay.journal.service"
			},
			{
					"journal.email.article.added.body", "email.article.added.body",
					"com.liferay.journal.service"
			},
			{
					"journal.email.article.approval.denied.enabled",
					"email.article.approval.denied.enabled",
					"com.liferay.journal.service"
			},
			{
					"journal.email.article.approval.denied.subject",
					"email.article.approval.denied.subject",
					"com.liferay.journal.service"
			},
			{
					"journal.email.article.approval.denied.body",
					"email.article.approval.denied.body", "com.liferay.journal.service"
			},
			{
					"journal.email.article.approval.granted.enabled",
					"email.article.approval.granted.enabled",
					"com.liferay.journal.service"
			},
			{
					"journal.email.article.approval.granted.subject",
					"email.article.approval.granted.subject",
					"com.liferay.journal.service"
			},
			{
					"journal.email.article.approval.granted.body",
					"email.article.approval.granted.body", "com.liferay.journal.service"
			},
			{
					"journal.email.article.approval.requested.enabled",
					"email.article.approval.requested.enabled",
					"com.liferay.journal.service"
			},
			{
					"journal.email.article.approval.requested.subject",
					"email.article.approval.requested.subject",
					"com.liferay.journal.service"
			},
			{
					"journal.email.article.approval.requested.body",
					"email.article.approval.requested.body",
					"com.liferay.journal.service"
			},
			{
					"journal.email.article.moved.to.folder.enabled",
					"email.article.moved.to.folder.enabled",
					"com.liferay.journal.service"
			},
			{
					"journal.email.article.moved.to.folder.subject",
					"email.article.moved.to.folder.subject",
					"com.liferay.journal.service"
			},
			{
					"journal.email.article.moved.from.folder.body",
					"email.article.moved.from.folder.body",
					"com.liferay.journal.service"
			},
			{
					"journal.email.article.moved.from.folder.enabled",
					"email.article.moved.from.folder.enabled",
					"com.liferay.journal.service"
			},
			{
					"journal.email.article.moved.from.folder.subject",
					"email.article.moved.from.folder.subject",
					"com.liferay.journal.service"
			},
			{
					"journal.email.article.moved.from.folder.body",
					"email.article.moved.from.folder.body",
					"com.liferay.journal.service"
			},
			{
					"journal.email.article.review.enabled",
					"email.article.review.enabled", "com.liferay.journal.service"
			},
			{
					"journal.email.article.review.subject",
					"email.article.review.subject", "com.liferay.journal.service"
			},
			{
					"journal.email.article.review.body", "email.article.review.body",
					"com.liferay.journal.service"
			},
			{
					"journal.email.article.updated.enabled",
					"email.article.updated.enabled", "com.liferay.journal.service"
			},
			{
					"journal.email.article.updated.subject",
					"email.article.updated.subject", "com.liferay.journal.service"
			},
			{
					"journal.email.article.updated.body", "email.article.updated.body",
					"com.liferay.journal.service"
			},
			{
					"journal.error.template[ftl]", "error.template[ftl]",
					"com.liferay.journal.service"
			},
			{
					"journal.error.template[vm]", "error.template[vm]",
					"com.liferay.journal.service"
			},
			{
					"journal.error.template[xsl]", "error.template[xsl]",
					"com.liferay.journal.service"
			},
			{
					"journal.feed.force.autogenerate.id",
					"journal.feed.force.autogenerate.id", "com.liferay.journal.web"
			},
			{
					"journal.folder.icon.check.count",
					"journal.folder.icon.check.count", "com.liferay.journal.service"
			},
			{
					"journal.lar.creation.strategy", "lar.creation.strategy",
					"com.liferay.journal.service"
			},
			{
					"journal.publish.to.live.by.default", "publish.to.live.by.defaul",
					"com.liferay.journal.web"
			},
			{
					"journal.publish.version.history.by.default",
					"publish.version.history.by.default", "com.liferay.journal.web"
			},
			{
					"journal.sync.content.search.on.startup",
					"sync.content.search.on.startup", "com.liferay.journal.service"
			},
			{
					"journal.template.language.content[css]",
					"journal.article.template.language.content[css]",
					"com.liferay.journal.web"
			},
			{
					"journal.template.language.content[ftl]",
					"journal.article.template.language.content[ftl]",
					"com.liferay.journal.web"
			},
			{
					"journal.template.language.content[vm]",
					"journal.article.template.language.content[vm]",
					"com.liferay.journal.web"
			},
			{
					"journal.template.language.content[xsl]",
					"journal.article.template.language.content[xsl]",
					"com.liferay.journal.web"
			},
			{
					"journal.transformer.listener", "transformer.listener",
					"com.liferay.journal.service"
			},
			{
					"journal.transformer.regex.pattern", "transformer.regex.pattern",
					"com.liferay.journal.service"
			},
			{
					"journal.transformer.regex.replacement",
					"transformer.regex.replacement", "com.liferay.journal.service"
			},
			{
					"terms.of.use.journal.article.group.id",
					"terms.of.use.journal.article.group.id",
					"com.liferay.journal.service"
			},
			{
					"terms.of.use.journal.article.id",
					"terms.of.use.journal.article.id", "com.liferay.journal.service"
			},

			// Language

			{
					"language.display.style.default", "ddm.template.key.default",
					"com.liferay.site.navigation.language.web"
			},
			{
					"language.display.templates.config", "display.templates.config",
					"com.liferay.site.navigation.language.web"
			},

			// Lucene

			{
					"lucene.analyzer.max.tokens", "analyzer.max.tokens",
					"com.liferay.portal.search.lucene"
			},
			{
					"lucene.buffer.size", "buffer.size",
					"com.liferay.portal.search.lucene"
			},
			{
					"lucene.commit.batch.size", "commit.batch.size",
					"com.liferay.portal.search.lucene"
			},
			{
					"lucene.commit.time.interval", "commit.time.interval",
					"com.liferay.portal.search.lucene"
			},
			{"lucene.dir", "dir", "com.liferay.portal.search.lucene"},
			{
					"lucene.merge.factor", "merge.factor",
					"com.liferay.portal.search.lucene"
			},
			{
					"lucene.merge.policy", "merge.policy",
					"com.liferay.portal.search.lucene"
			},
			{
					"lucene.merge.scheduler", "merge.scheduler",
					"com.liferay.portal.search.lucene"
			},
			{
					"lucene.store.type", "store.type",
					"com.liferay.portal.search.lucene"
			},
			{
					"lucene.store.type.file.force.mmap", "store.type.file.force.mmp",
					"com.liferay.portal.search.lucene"
			},

			// Message Boards

			{
					"message.boards.expire.ban.job.interval", "expire.ban.job.interval",
					"com.liferay.message.boards.web"
			},

			// Monitoring

			{
					"monitoring.portal.request", "monitor.portal.request",
					"com.liferay.portal.monitoring"
			},
			{
					"monitoring.portlet.action.request",
					"monitor.portlet.action.request", "com.liferay.portal.monitoring"
			},
			{
					"monitoring.portlet.event.request", "monitor.portlet.event.request",
					"com.liferay.portal.monitoring"
			},
			{
					"monitoring.portlet.render.request",
					"monitor.portlet.render.request", "com.liferay.portal.monitoring"
			},
			{
					"monitoring.portlet.resource.request",
					"monitor.portlet.resource.request", "com.liferay.portal.monitoring"
			},
			{
					"monitoring.show.per.request.data.sample",
					"show.per.request.data.sample", "com.liferay.portal.monitoring"
			},

			// Navigation

			{
					"navigation.display.style.default", "ddm.template.key.default",
					"com.liferay.site.navigation.menu.web"
			},
			{
					"navigation.display.style.options", "display.style.options",
					"com.liferay.site.navigation.menu.web"
			},

			// Nested Portlets

			{
					"nested.portlets.layout.template.default",
					"layout.template.default", "com.liferay.nested.portlets.web"
			},
			{
					"nested.portlets.layout.template.unsupported",
					"layout.template.unsupported", "com.liferay.nested.portlets.web"
			},

			// NTLM

			{
					"ntlm.auth.enabled", "enabled",
					"com.liferay.portal.security.sso.ntlm"
			},
			{"ntlm.auth.domain", "domain", "com.liferay.portal.security.sso.ntlm"},
			{
					"ntlm.auth.domain.controller", "domain.controller",
					"com.liferay.portal.security.sso.ntlm"
			},
			{
					"ntlm.auth.domain.controller.name", "domain.controller.name",
					"com.liferay.portal.security.sso.ntlm"
			},
			{
					"ntlm.auth.negotiate.flags", "negotiate.flags",
					"com.liferay.portal.security.sso.ntlm"
			},
			{
					"ntlm.auth.service.account", "service.account",
					"com.liferay.portal.security.sso.ntlm"
			},
			{
					"ntlm.auth.service.password", "service.password",
					"com.liferay.portal.security.sso.ntlm"
			},

			// OpenID

			{
					"open.id.auth.enabled", "enabled",
					"com.liferay.portal.security.sso.openid"
			},
			{
					"open.id.providers", "providers",
					"com.liferay.portal.security.sso.openid"
			},
			{
					"open.id.ax.schema[default]", "ax.schema",
					"com.liferay.portal.security.sso.openid"
			},
			{
					"open.id.ax.type.email[default]", "ax.type.email",
					"com.liferay.portal.security.sso.openid"
			},
			{
					"open.id.ax.type.firstname[default]", "ax.type.firstname",
					"com.liferay.portal.security.sso.openid"
			},
			{
					"open.id.ax.type.lastname[default]", "ax.type.lastname",
					"com.liferay.portal.security.sso.openid"
			},
			{
					"open.id.ax.schema[yahoo]", "ax.schema",
					"com.liferay.portal.security.sso.openid"
			},
			{
					"open.id.ax.type.email[yahoo]", "ax.type.email",
					"com.liferay.portal.security.sso.openid"
			},
			{
					"open.id.ax.type.fullname[yahoo]", "ax.type.fullname",
					"com.liferay.portal.security.sso.openid"
			},
			{
					"open.id.url[yahoo]", "url",
					"com.liferay.portal.security.sso.openid"
			},

			// OpenSSO

			{
					"open.sso.auth.enabled", "enabled",
					"com.liferay.portal.security.sso.opensso"
			},
			{
					"open.sso.email.address.attr", "email.address.attr",
					"com.liferay.portal.security.sso.opensso"
			},
			{
					"open.sso.first.name.attr", "first.name.attr",
					"com.liferay.portal.security.sso.opensso"
			},
			{
					"open.sso.last.name.attr", "last.name.attr",
					"com.liferay.portal.security.sso.opensso"
			},
			{
					"open.sso.import.from.ldap", "import.from.ldap",
					"com.liferay.portal.security.sso.opensso"
			},
			{
					"open.sso.login.url", "login.url",
					"com.liferay.portal.security.sso.opensso"
			},
			{
					"open.sso.logout.on.session.expiration",
					"logout.on.session.expiration",
					"com.liferay.portal.security.sso.opensso"
			},
			{
					"open.sso.logout.url", "logout.url",
					"com.liferay.portal.security.sso.opensso"
			},
			{
					"open.sso.screen.name.attr", "screen.name.attr",
					"com.liferay.portal.security.sso.opensso"
			},
			{
					"open.sso.service.url", "service.url",
					"com.liferay.portal.security.sso.opensso"
			},

			// Polls

			{
					"polls.publish.to.live.by.default", "publish.to.live.by.default",
					"com.liferay.polls.service"
			},

			// Request Header

			{
					"request.header.auth.hosts.allowed", "authHostsAllowed",
					"com.liferay.portal.security.auto.login"
			},

			{
					"request.header.auth.import.from.ldap", "importFromLDAP",
					"com.liferay.portal.security.auto.login.request.header"
			},

			// RSS

			{
					"rss.display.templates.config", "display.templates.config",
					"com.liferay.rss.web"
			},

			// Shopping

			{
					"shopping.cart.min.qty.multiple", "cart.min.qty.multiple",
					"com.liferay.shopping.service"
			},
			{
					"shopping.category.forward.to.cart", "category.forward.to.cart",
					"com.liferay.shopping.service"
			},
			{
					"shopping.category.show.special.items",
					"category.show.special.items", "com.liferay.shopping.service"
			},
			{
					"shopping.credit.card.types", "credit.card.types",
					"com.liferay.shopping.service"
			},
			{
					"shopping.currency.id", "currency.id",
					"com.liferay.shopping.service"
			},
			{
					"shopping.email.from.address", "email.from.address",
					"com.liferay.shopping.service"
			},
			{
					"shopping.email.from.name", "email.from.name",
					"com.liferay.shopping.service"
			},
			{
					"shopping.email.order.confirmation.enabled",
					"email.order.confirmation.enabled", "com.liferay.shopping.service"
			},
			{
					"shopping.email.order.confirmation.subject",
					"email.order.confirmation.subject", "com.liferay.shopping.service"
			},
			{
					"shopping.email.order.confirmation.body",
					"email.order.confirmation.body", "com.liferay.shopping.service"
			},
			{
					"shopping.email.order.shipping.enabled",
					"email.order.shipping.enabled", "com.liferay.shopping.service"
			},
			{
					"shopping.email.order.shipping.subject",
					"email.order.shipping.subject", "com.liferay.shopping.service"
			},
			{
					"shopping.email.order.shipping.body", "email.order.shipping.body",
					"com.liferay.shopping.service"
			},
			{
					"shopping.image.extensions", "image.extensions",
					"com.liferay.shopping.service"
			},
			{
					"shopping.image.large.max.size", "image.large.max.size",
					"com.liferay.shopping.service"
			},
			{
					"shopping.image.medium.max.size", "image.medium.max.size",
					"com.liferay.shopping.service"
			},
			{
					"shopping.image.small.max.size", "image.small.max.size",
					"com.liferay.shopping.service"
			},
			{"shopping.insurance", "insurance", "com.liferay.shopping.service"},
			{
					"shopping.insurance.formula", "insurance.formula",
					"com.liferay.shopping.service"
			},
			{
					"shopping.item.show.availability", "item.show.availability",
					"com.liferay.shopping.service"
			},
			{"shopping.min.order", "min.order", "com.liferay.shopping.service"},
			{
					"shopping.order.comments.enabled", "order.comments.enabled",
					"com.liferay.shopping.service"
			},
			{
					"shopping.paypal.email.address", "paypal.email.address",
					"com.liferay.shopping.service"
			},
			{"shopping.shipping", "shipping", "com.liferay.shopping.service"},
			{
					"shopping.shipping.formula", "shipping.formula",
					"com.liferay.shopping.service"
			},
			{"shopping.tax.rate", "tax.rate", "com.liferay.shopping.service"},

			// Scripting

			{
					"scripting.forbidden.classes", "forbidden.classes",
					"com.liferay.portal.scripting.javascript"
			},
			{
					"scripting.jruby.load.paths", "load.paths",
					"com.liferay.portal.scripting.ruby"
			},

			// Search

			{
					"search.facet.configuration", "facet.configuration",
					"com.liferay.search.web"
			},

			// Site Map

			{
					"sitemap.display.templates.config", "display.templates.config",
					"com.liferay.site.navigation.site.map.web"
			},

			// Staging

			{
					"staging.draft.export.import.configuration.check.interval",
					"draft.export.import.configuration.check.interval",
					"com.liferay.exportimport.web"
			},
			{
					"staging.draft.export.import.configuration.clean.up.count",
					"draft.export.import.configuration.clean.up.count",
					"com.liferay.exportimport.web"
			},

			// Social Activity

			{
					"social.activity.contribution.increments",
					"contribution.increments", "com.liferay.social.activity"
			},
			{
					"social.activity.contribution.limit.values",
					"contribution.limit.values", "com.liferay.social.activity"
			},
			{
					"social.activity.participation.increments",
					"participation.increments", "com.liferay.social.activity"
			},
			{
					"social.activity.participation.limit.values",
					"participation.limit.values", "com.liferay.social.activity"
			},

			// Tags Compiler

			{
					"tags.compiler.enabled", "enabled",
					"com.liferay.asset.tags.compiler.web"
			},

			// Translator

			{
					"translator.default.languages", "translation.id",
					"com.liferay.translator.web"
			},
			{"translator.languages", "language.ids", "com.liferay.translator.web"},

			// Velocity Engine

			{
					"velocity.engine.directive.if.to.string.null.check",
					"directive.if.to.string.null.check",
					"com.liferay.portal.template.velocity"
			},
			{
					"velocity.engine.resource.parsers", "resource.parsers",
					"com.liferay.portal.template.velocity"
			},
			{
					"velocity.engine.resource.modification.check.interval",
					"resource.modification.check.interval",
					"com.liferay.portal.template.velocity"
			},
			{
					"velocity.engine.restricted.classes", "restricted.classes",
					"com.liferay.portal.template.velocity"
			},
			{
					"velocity.engine.restricted.packages", "restricted.packages",
					"com.liferay.portal.template.velocity"
			},
			{
					"velocity.engine.restricted.variables", "restricted.variables",
					"com.liferay.portal.template.velocity"
			},
			{
					"velocity.engine.velocimacro.library", "macro.library",
					"com.liferay.portal.template.velocity"
			},
			{
					"velocity.engine.logger", "logger",
					"com.liferay.portal.template.velocity"
			},
			{
					"velocity.engine.logger.category", "logger.category",
					"com.liferay.portal.template.velocity"
			},

			// XSL Content

			{
					"xsl.content.valid.url.prefixes", "valid.url.prefixes",
					"com.liferay.xsl.content.web"
			},
			{
					"xsl.content.xml.doctype.declaration.allowed",
					"xml.doctype.declaration.allowed", "com.liferay.xsl.content.web"
			},
			{
					"xsl.content.xml.external.general.entities.allowed",
					"xml.external.general.entities.allowed",
					"com.liferay.xsl.content.web"
			},
			{
					"xsl.content.xml.external.parameter.entities.allowed",
					"xml.external.parameter.entities.allowed",
					"com.liferay.xsl.content.web"
			},
			{
					"xsl.content.xsl.secure.processing.enabled",
					"xsl.secure.processing.enabled", "com.liferay.xsl.content.web"
			},

			// XSL Engine

			{
					"xsl.template.secure.processing.enabled",
					"secure.processing.enabled", "com.liferay.portal.template.xsl"
			}
	};

	public static final String[][] _MODULARIZED_PORTAL_KEYS_7_1 = {
			// Permissions

			{
					"permissions.inline.sql.check.enabled", "sqlCheckEnabled",
					"com.liferay.portal.security.permission.impl"
			}
	};

	public static final String[][] _MODULARIZED_PORTAL_KEYS_7_2 = {
			{
					"auth.verifier.ImageRequestAuthVerifier.hosts.allowed",
					"auth.verifier.ImageRequestAuthVerifier.hosts.allowed",
					"com.liferay.document.library.document.conversion"
			},
			{
					"auth.verifier.ImageRequestAuthVerifier.urls.excludes",
					"auth.verifier.ImageRequestAuthVerifier.urls.excludes",
					"com.liferay.document.library.document.conversion"
			},
			{
					"auth.verifier.ImageRequestAuthVerifier.urls.includes",
					"auth.verifier.ImageRequestAuthVerifier.urls.includes",
					"com.liferay.document.library.document.conversion"
			},
			{
					"blogs.image.max.size", "blogs.image.max.size",
					"com.liferay.blogs.api"
			},

			{
					"blogs.image.extensions", "blogs.image.extensions",
					"com.liferay.blogs.api"
			},
			// Discussion

			{
					"discussion.email.body", "discussion.email.body",
					"com.liferay.comment.api"
			},

			{
					"discussion.email.comments.added.enabled",
					"discussion.email.comments.added.enabled", "com.liferay.comment.api"
			},

			{
					"discussion.email.subject", "discussion.email.subject",
					"com.liferay.comment.api"
			},
			// Document Library

			{
					"dl.file.rank.check.interval", "check.file.ranks.interval",
					"com.liferay.recent.documents.web"
			},
			{
					"dl.file.rank.max.size", "max.size",
					"com.liferay.document.library.file.rank.service"
			}
	};

	public static final String[][] _MODULARIZED_PORTAL_KEYS_7_3 = {
			// View Count

			{
					"view.count.enabled", "enabled", "com.liferay.view.count.service"
			},
	};

	public static final String[][] _MODULARIZED_PORTAL_KEYS_7_4 = {
			// Session --> https://liferay.atlassian.net/browse/LPD-14031

			{
					"session.timeout.auto.extend", "auto-extend",
					"com.liferay.frontend.js.web"
			},
			{
					"session.timeout.auto.extend.offset", "auto-extend-offset",
					"com.liferay.frontend.js.web"
			},
			// Text Extraction

			{
					"text.extraction.fork.process.enabled",
					"text-extraction-fork-process-enabled",
					"com.liferay.portal.tika"
			},
			{
					"text.extraction.fork.process.mime.types",
					"text-extraction-fork-process-mime-types",
					"com.liferay.portal.tika"
			},
	};
}
