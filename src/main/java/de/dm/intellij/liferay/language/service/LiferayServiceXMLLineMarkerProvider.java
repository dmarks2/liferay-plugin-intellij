package de.dm.intellij.liferay.language.service;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;

/**
 * Provides a Line Marker for an Entity defined in service.xml. Using the Line Marker you can jump to the Model implementation
 */
public class LiferayServiceXMLLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, Collection<? super RelatedItemLineMarkerInfo> result) {
        if (element instanceof XmlTag) {
            XmlTag xmlTag = (XmlTag) element;
            String name = xmlTag.getLocalName();
            if ("entity".equals(name)) {
                if (xmlTag.getParent() instanceof XmlTag) {
                    XmlTag parent = (XmlTag) xmlTag.getParent();
                    if ("service-builder".equals(parent.getName())) {
                        String packagePath = parent.getAttributeValue("package-path");
                        if (packagePath != null) {
                            String entityName = xmlTag.getAttributeValue("name");
                            if (entityName != null) {
                                Project project = element.getProject();

                                String targetClassName = packagePath + ".model.impl." + entityName + "Impl";

                                PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(targetClassName, GlobalSearchScope.allScope(project));

                                if (psiClass != null) {
                                    NavigationGutterIconBuilder<PsiElement> builder =
                                            NavigationGutterIconBuilder.create(AllIcons.Gutter.ImplementedMethod).
                                                    setTargets(Arrays.asList(psiClass)).
                                                    setTooltipText("Navigate to Implementation");

                                    result.add(builder.createLineMarkerInfo(element));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
