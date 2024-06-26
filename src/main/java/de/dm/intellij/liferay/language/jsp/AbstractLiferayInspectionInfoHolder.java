package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.util.Version;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLiferayInspectionInfoHolder<T> {

	protected float myMajorLiferayVersion;

	protected String myLiferayVersion;

	protected String myMessage;

	protected String myTicket;

	protected LocalQuickFix[] quickFixes;

	@SuppressWarnings("unchecked")
	public T majorLiferayVersion(float majorLiferayVersion) {
		this.myMajorLiferayVersion = majorLiferayVersion;

		return (T)this;
	}

	@SuppressWarnings("unchecked")
	public T message(String message) {
		this.myMessage = message;

		return (T)this;
	}

	@SuppressWarnings("unchecked")
	public T ticket(String ticket) {
		this.myTicket = ticket;

		return (T)this;
	}

	@SuppressWarnings("unchecked")
	public T quickfix(LocalQuickFix... quickFixes) {
		this.quickFixes = quickFixes;

		return (T)this;
	}

	@SuppressWarnings("unchecked")
	public T version(String version) {
		this.myLiferayVersion = version;

		return (T)this;
	}

	protected String getMessage() {
		return myMessage;
	}

	protected String getDeprecationMessage() {
		if (StringUtil.isNotEmpty(myTicket)) {
			return "<html><body>" + getMessage() + " (see <a href=\"https://liferay.atlassian.net/browse/" + myTicket + "\">" + myTicket + "</a>)</body></html>";
		} else {
			return getMessage();
		}
	}

	protected boolean isApplicableLiferayVersion(PsiElement psiElement) {
		Module module = ModuleUtilCore.findModuleForPsiElement(psiElement);

		if ((module != null) && (!module.isDisposed())) {
			float version = LiferayModuleComponent.getPortalMajorVersion(module);

			if (version > myMajorLiferayVersion) {
				return true;
			} else if (version == myMajorLiferayVersion) {
				if (StringUtil.isEmpty(myLiferayVersion)) {
					return true;
				}

				return Version.compare(LiferayModuleComponent.getLiferayVersion(module), myLiferayVersion) >= 0;
			}
		}

		return false;
	}

	public static class ListWrapper <E> extends ArrayList<E> {
		public ListWrapper(List<E> list) {
			super(list);
		}

		@SuppressWarnings("unchecked")
		public AbstractLiferayInspectionInfoHolder.ListWrapper<E> quickfix(LocalQuickFix... quickFixes) {
			this.replaceAll(liferayTaglibDeprecationInfoHolder -> ((AbstractLiferayInspectionInfoHolder<E>)liferayTaglibDeprecationInfoHolder).quickfix(quickFixes));

			return this;
		}

		@SuppressWarnings("unchecked")
		public AbstractLiferayInspectionInfoHolder.ListWrapper<E> version(String version) {
			this.replaceAll(liferayTaglibDeprecationInfoHolder -> ((AbstractLiferayInspectionInfoHolder<E>)liferayTaglibDeprecationInfoHolder).version(version));

			return this;
		}
	}
}
