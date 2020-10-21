package de.dm.intellij.liferay.language.service;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Provides a Line Marker for a Service Model Implemenation class. Using the Line Marker you can jump to the declaration in service.xml.
 */

public class LiferayServiceJavaImplLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, Collection<? super RelatedItemLineMarkerInfo<?>> result) {
        if (element instanceof PsiIdentifier) {
            if (element.getParent() instanceof PsiClass) {
                PsiClass psiClass = (PsiClass) element.getParent();
                String name = psiClass.getName();
                PsiJavaFile psiJavaFile = (PsiJavaFile) psiClass.getContainingFile();
                if (psiJavaFile != null) {
                    String packageName = psiJavaFile.getPackageName();
                    if ((name.endsWith("Impl")) && (packageName.endsWith(".model.impl"))) {
                        String targetName = name.substring(0, name.length() - 4);
                        String targetPackage = packageName.substring(0, packageName.length() - 11);

                        Collection<PsiElement> targets = new ArrayList<PsiElement>();

                        Project project = element.getProject();

                        PsiFile[] files = FilenameIndex.getFilesByName(project, "service.xml", GlobalSearchScope.allScope(project));
                        for (PsiFile psiFile : files) {
                            if (psiFile instanceof XmlFile) {
                                XmlFile xmlFile = (XmlFile) psiFile;
                                if (xmlFile.isValid()) {
                                    XmlTag rootTag = xmlFile.getRootTag();
                                    if ("service-builder".equals(rootTag.getLocalName())) {
                                        String packagePath = rootTag.getAttributeValue("package-path");
                                        if (packagePath != null) {
                                            if (targetPackage.equals(packagePath)) {
                                                XmlTag[] subTags = rootTag.findSubTags("entity");
                                                for (XmlTag xmlTag : subTags) {
                                                    String entityName = xmlTag.getAttributeValue("name");
                                                    if (entityName != null) {
                                                        if (targetName.equals(entityName)) {
                                                            targets.add(xmlTag);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }

                        if (!(targets.isEmpty())) {
                            NavigationGutterIconBuilder<PsiElement> builder =
                                    NavigationGutterIconBuilder.create(AllIcons.Gutter.ImplementingMethod).
                                            setTargets(targets).
                                            setTooltipText("Navigate to Declaration");

                            result.add(builder.createLineMarkerInfo(element));

                        }

                    }
                }
            }
        }
    }
}
