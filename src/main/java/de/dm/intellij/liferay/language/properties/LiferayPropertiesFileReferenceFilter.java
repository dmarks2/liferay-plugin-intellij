package de.dm.intellij.liferay.language.properties;

import com.intellij.lang.properties.psi.PropertiesFile;
import com.intellij.lang.properties.psi.impl.PropertyImpl;
import com.intellij.lang.properties.psi.impl.PropertyValueImpl;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.filters.position.FilterPattern;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;

public class LiferayPropertiesFileReferenceFilter extends FilterPattern {

    private static final Collection<String> FILE_REFERENCE_PROPERTIES = Arrays.asList(
        "calendar.rss.template",
        "custom.sql.configs",
        "include-and-override",
        "resource.actions.configs"
    );

    public static boolean isPortletPropertiesFile(PsiElement psiElement) {
        PsiFile file = psiElement.getContainingFile();
        return file instanceof PropertiesFile && (
            Comparing.equal(file.getName(), "portlet.properties", SystemInfo.isFileSystemCaseSensitive) ||
                Comparing.equal(file.getName(), "portlet-ext.properties", SystemInfo.isFileSystemCaseSensitive)
        );
    }

    public LiferayPropertiesFileReferenceFilter() {
        super(new ElementFilter() {
                  @Override
                  public boolean isAcceptable(Object element, @Nullable PsiElement context) {
                      if (element instanceof PsiElement psiElement) {

						  if (isPortletPropertiesFile(psiElement)) {
                              if (psiElement instanceof PropertyValueImpl propertyValue) {
								  PropertyImpl property = (PropertyImpl) propertyValue.getParent();

                                  if (property != null) {
                                      String key = property.getKey();

                                      if (key != null) {
										  return FILE_REFERENCE_PROPERTIES.contains(key);
                                      }
                                  }
                              }
                          }
                      }

                      return false;
                  }

                  @Override
                  public boolean isClassAcceptable(Class hintClass) {
                      return true;
                  }
              }
        );
    }
}
