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
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.ProcessingContext;
import de.dm.intellij.liferay.index.ResourceCommandIndex;
import de.dm.intellij.liferay.util.Icons;
import de.dm.intellij.liferay.util.LiferayTaglibs;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Reference completion for resource command names
 */
public class LiferayTaglibResourceCommandNameReferenceContributor extends AbstractLiferayTaglibCommandNameReferenceContributor {

    private static final Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_ATTRIBUTES = new HashMap<>();

    static {
        TAGLIB_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_JAVAX_PORTLET, Arrays.asList(
            new AbstractMap.SimpleEntry<>("resourceURL", "id")
        ));

        TAGLIB_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_PORTLET, Arrays.asList(
            new AbstractMap.SimpleEntry<>("resourceURL", "id")
        ));
    }

    @NotNull
    @Override
    protected String getURLTagLocalName() {
        return "resourceURL";
    }

    @Override
    protected PsiReferenceProvider getReferenceProvider() {
        return new PsiReferenceProvider() {
            @NotNull
            @Override
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                if (element instanceof XmlAttributeValue) {
                    XmlAttributeValue xmlAttributeValue = (XmlAttributeValue)element;

                    String value = xmlAttributeValue.getValue();
                    if (value != null) {
                        return new PsiReference[]{
                            new PsiReferenceBase.Poly<XmlAttributeValue>((XmlAttributeValue) element, ElementManipulators.getValueTextRange(element), true) {
                                @NotNull
                                @Override
                                public ResolveResult[] multiResolve(boolean incompleteCode) {
                                    Project project = getElement().getProject();

                                    XmlTag parentTag = PsiTreeUtil.getParentOfType(getElement(), XmlTag.class);
                                    if (parentTag != null) {
                                        String localName = parentTag.getLocalName();

                                        String resourceCommandName = null;
                                        Collection<String> portletNames = Collections.emptyList();

                                        if (getURLTagLocalName().equals(localName)) {
                                            resourceCommandName = getElement().getValue();
                                            portletNames = getPortletNameFromURLTag(parentTag);

                                            if (portletNames.isEmpty()) {
                                                portletNames = getPortletNameFromJspPath(parentTag);
                                            }
                                        }

                                        if (resourceCommandName != null && (! (portletNames.isEmpty()) ) ) {
                                            List<PsiFile> portletClasses = new ArrayList<>();

                                            for (String portletName : portletNames) {
                                                portletClasses.addAll(ResourceCommandIndex.getPortletClasses(project, portletName, resourceCommandName, GlobalSearchScope.allScope(project)));
                                            }

                                            return PsiElementResolveResult.createResults(portletClasses);
                                        }
                                    }

                                    return new ResolveResult[0];
                                }

                                @NotNull
                                @Override
                                public Object[] getVariants() {
                                    List<Object> result = new ArrayList<Object>();

                                    Project project = getElement().getProject();

                                    XmlTag parentTag = PsiTreeUtil.getParentOfType(getElement(), XmlTag.class);
                                    if (parentTag != null) {
                                        String localName = parentTag.getLocalName();

                                        Collection<String> portletNames = Collections.emptyList();

                                        if (getURLTagLocalName().equals(localName)) {
                                            portletNames = getPortletNameFromURLTag(parentTag);

                                            if (portletNames.isEmpty()) {
                                            portletNames = getPortletNameFromJspPath(parentTag);
                                            }
                                        }

                                        if (! (portletNames.isEmpty()) ) {
                                            for (String portletName : portletNames) {
                                                List<String> resourceCommands = ResourceCommandIndex.getResourceCommands(portletName, project, GlobalSearchScope.allScope(project));
                                                Set<String> distinctResourceCommands = new TreeSet<>(resourceCommands);

                                                for (String resourceCommand : distinctResourceCommands) {
                                                    List<PsiFile> portletClasses = ResourceCommandIndex.getPortletClasses(project, portletName, resourceCommand, GlobalSearchScope.allScope(project));
                                                    if (portletClasses.size() > 0) {
                                                        result.add(
                                                            LookupElementBuilder.create(resourceCommand).
                                                                withIcon(Icons.LIFERAY_ICON)
                                                        );

                                                    }
                                                }
                                            }
                                        }
                                    }

                                    return result.toArray(new Object[result.size()]);
                                }
                            }
                        };
                    }
                }
                return new PsiReference[0];
            }
        };
    }

    @Override
    protected String[] getAttributeNames() {
        return new String[] {
            "id"
        };
    }

    @Override
    protected Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> getTaglibMap() {
        return TAGLIB_ATTRIBUTES;
    }
}
