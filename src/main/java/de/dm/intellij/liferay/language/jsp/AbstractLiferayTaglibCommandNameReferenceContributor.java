package de.dm.intellij.liferay.language.jsp;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlTag;
import de.dm.intellij.liferay.index.PortletJspIndex;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class AbstractLiferayTaglibCommandNameReferenceContributor extends AbstractLiferayTaglibReferenceContributor {

    @NotNull
    protected abstract String getURLTagLocalName();

    @Nullable
    protected XmlTag getURLTagFromParamTag(XmlTag paramTag) {
        XmlTag xmlTag = PsiTreeUtil.getParentOfType(paramTag, XmlTag.class);
        if (xmlTag != null) {
            if (getURLTagLocalName().equals(xmlTag.getLocalName())) {
                return xmlTag;
            }
        }

        return null;
    }

    @NotNull
    protected Collection<String> getPortletNameFromURLTag(XmlTag xmlTag) {
        if (xmlTag != null) {
            if (getURLTagLocalName().equals(xmlTag.getLocalName())) {
                String namePortletNameValue = xmlTag.getAttributeValue("portletName");

                if (namePortletNameValue != null) {
                    return Collections.singletonList(namePortletNameValue);
                }
            }
        }

        return Collections.emptyList();
    }

    @NotNull
    protected Collection<String> getPortletNamesFromParentTag(@NotNull XmlTag parentTag) {
        Collection<String> portletNames = Collections.emptyList();

        XmlTag actionURLTag = getURLTagFromParamTag(parentTag);
        portletNames = getPortletNameFromURLTag(actionURLTag);

        if (portletNames.isEmpty()) {
            portletNames = getPortletNameFromJspPath(parentTag);
        }

        return portletNames;
    }

    protected Collection<String> getPortletNameFromJspPath(XmlTag xmlTag) {
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

                            List<String> portletNames = PortletJspIndex.getPortletNames(relativePath, xmlTag.getProject(), GlobalSearchScope.moduleScope(module));

                            result.addAll(portletNames);
                        }
                    }
                }
            }
        }

        return result;
    }


}
