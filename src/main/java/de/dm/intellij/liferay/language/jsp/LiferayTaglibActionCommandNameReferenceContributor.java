package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
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
import de.dm.intellij.liferay.index.ActionCommandIndex;
import de.dm.intellij.liferay.index.PortletJspIndex;
import de.dm.intellij.liferay.util.Icons;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import de.dm.intellij.liferay.util.LiferayTaglibs;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
 * Reference completion for action command names
 */
public class LiferayTaglibActionCommandNameReferenceContributor extends AbstractLiferayTaglibReferenceContributor {

    private static final Map<String, Collection<Pair<String, String>>> TAGLIB_ATTRIBUTES = new HashMap<String, Collection<Pair<String, String>>>();

    private static final String ACTION_NAME = "javax.portlet.action";

    static {
        TAGLIB_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_JAVAX_PORTLET, Arrays.asList(
            new Pair<String, String>("actionURL", "name"),
            new Pair<String, String>("param", "value")
        ));

        TAGLIB_ATTRIBUTES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_PORTLET, Arrays.asList(
            new Pair<String, String>("actionURL", "name"),
            new Pair<String, String>("param", "value")
        ));
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

                                        String actionName = null;
                                        Collection<String> portletNames = Collections.emptyList();

                                        if ("param".equals(localName)) {
                                            String paramName = parentTag.getAttributeValue("name");
                                            if (ACTION_NAME.equals(paramName)) {
                                                actionName = getElement().getValue();
                                                if (actionName != null) {
                                                    XmlTag actionURLTag = getActionURLTagFromParamTag(parentTag);
                                                    portletNames = getPortletNameFromActionURLTag(actionURLTag);

                                                    if (portletNames.isEmpty()) {
                                                        portletNames = getPortletNameFromJspPath(parentTag);
                                                    }
                                                }
                                            }
                                        } else if ("actionURL".equals(localName)) {
                                            actionName = getElement().getValue();
                                            portletNames = getPortletNameFromActionURLTag(parentTag);

                                            if (portletNames.isEmpty()) {
                                                portletNames = getPortletNameFromJspPath(parentTag);
                                            }
                                        }

                                        if (actionName != null && (! (portletNames.isEmpty()) ) ) {
                                            List<PsiFile> portletClasses = new ArrayList<>();

                                            for (String portletName : portletNames) {
                                                portletClasses.addAll(ActionCommandIndex.getPortletClasses(project, portletName, actionName, GlobalSearchScope.allScope(project)));
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

                                        if ("param".equals(localName)) {
                                            XmlTag actionURLTag = getActionURLTagFromParamTag(parentTag);
                                            portletNames = getPortletNameFromActionURLTag(actionURLTag);

                                            if (portletNames.isEmpty()) {
                                                portletNames = getPortletNameFromJspPath(parentTag);
                                            }
                                        } else if ("actionURL".equals(localName)) {
                                            portletNames = getPortletNameFromActionURLTag(parentTag);

                                            if (portletNames.isEmpty()) {
                                                portletNames = getPortletNameFromJspPath(parentTag);
                                            }
                                        }

                                        if (! (portletNames.isEmpty()) ) {
                                            for (String portletName : portletNames) {
                                                List<String> actionCommands = ActionCommandIndex.getActionCommands(portletName, GlobalSearchScope.allScope(project));
                                                Set<String> distinctActionCommands = new TreeSet<>(actionCommands);

                                                for (String actionCommand : distinctActionCommands) {
                                                    List<PsiFile> portletClasses = ActionCommandIndex.getPortletClasses(project, portletName, actionCommand, GlobalSearchScope.allScope(project));
                                                    if (portletClasses.size() > 0) {
                                                        result.add(
                                                            LookupElementBuilder.create(actionCommand).
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
            "name", "value"
        };
    }

    @Override
    protected Map<String, Collection<Pair<String, String>>> getTaglibMap() {
        return TAGLIB_ATTRIBUTES;
    }

    @Nullable
    private static XmlTag getActionURLTagFromParamTag(XmlTag paramTag) {
        XmlTag xmlTag = PsiTreeUtil.getParentOfType(paramTag, XmlTag.class);
        if (xmlTag != null) {
            if ("actionURL".equals(xmlTag.getLocalName())) {
                return xmlTag;
            }
        }

        return null;
    }

    private static Collection<String> getPortletNameFromActionURLTag(XmlTag xmlTag) {
        if (xmlTag != null) {
            if ("actionURL".equals(xmlTag.getLocalName())) {
                String namePortletNameValue = xmlTag.getAttributeValue("portletName");

                if (namePortletNameValue != null) {
                    return Collections.singletonList(namePortletNameValue);
                }
            }
        }

        return Collections.emptyList();
    }

    private static Collection<String> getPortletNameFromJspPath(XmlTag xmlTag) {
        Collection<String> result = new ArrayList<>();

        if (xmlTag != null) {
            PsiFile psiFile = xmlTag.getContainingFile();
            if (psiFile != null) {
                psiFile = psiFile.getOriginalFile();

                VirtualFile virtualFile = psiFile.getVirtualFile();
                if (virtualFile != null) {
                    Module module = ModuleUtil.findModuleForFile(virtualFile, xmlTag.getProject());

                    if (module != null) {
                        Collection<String> relativePaths = LiferayFileUtil.getWebRootsRelativePaths(module, virtualFile);
                        for (String relativePath : relativePaths) {
                            if (! (relativePath.startsWith("/")) ) {
                                relativePath = "/" + relativePath;
                            }

                            List<String> portletNames = PortletJspIndex.getPortletNames(relativePath, GlobalSearchScope.moduleScope(module));

                            result.addAll(portletNames);
                        }
                    }
                }
            }
        }

        return result;
    }

}
