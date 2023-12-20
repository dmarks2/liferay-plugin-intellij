package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.ElementManipulators;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.ProcessingContext;
import de.dm.intellij.liferay.index.PortletNameIndex;
import de.dm.intellij.liferay.util.Icons;
import de.dm.intellij.liferay.util.LiferayTaglibs;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Reference completion for portletName taglib attributes
 */
public class LiferayTaglibPortletNameReferenceContributor extends AbstractLiferayTaglibReferenceContributor {

    private static final Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_ATTRIBUTES = new HashMap<>();

    static {
        TAGLIB_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_PORTLET, Arrays.asList(
				new AbstractMap.SimpleEntry<>("actionURL", "portletName"),
				new AbstractMap.SimpleEntry<>("preview", "portletName"),
				new AbstractMap.SimpleEntry<>("renderURL", "portletName"),
				new AbstractMap.SimpleEntry<>("resourceURL", "portletName"),
				new AbstractMap.SimpleEntry<>("runtime", "portletName")
        ));

    }


    @Override
    protected PsiReferenceProvider getReferenceProvider() {
        return new PsiReferenceProvider() {
            @Override
            public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                if (element instanceof XmlAttributeValue) {
					return new PsiReference[]{
							new PsiReferenceBase.Poly<>((XmlAttributeValue) element, ElementManipulators.getValueTextRange(element), true) {
								@Override
								public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
									Project project = getElement().getProject();

									String value = getElement().getValue();

									List<PsiFile> portletClasses = PortletNameIndex.getPortletClasses(project, value, GlobalSearchScope.allScope(project));

									return PsiElementResolveResult.createResults(portletClasses);
								}

								@Override
								public Object @NotNull [] getVariants() {
									List<Object> result = new ArrayList<>();

									Project project = getElement().getProject();

									List<String> portletNames = PortletNameIndex.getPortletNames(project, GlobalSearchScope.allScope(project));

									Set<String> distinctPortletNames = new TreeSet<>(portletNames);

									for (String portletName : distinctPortletNames) {
										List<PsiFile> portletClasses = PortletNameIndex.getPortletClasses(project, portletName, GlobalSearchScope.allScope(project));
										if (!portletClasses.isEmpty()) {
											result.add(
													LookupElementBuilder.create(portletName).
															withIcon(Icons.LIFERAY_ICON)
											);
										}
									}

									return result.toArray(new Object[0]);
								}
							}
					};
				}
                return new PsiReference[0];
            }
        };
    }

    @Override
    protected String[] getAttributeNames() {
        return new String[] {
                "portletName"
        };
    }

    @Override
    protected Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> getTaglibMap() {
        return TAGLIB_ATTRIBUTES;
    }
}
