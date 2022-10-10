package de.dm.intellij.bndtools.documentation;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.lang.documentation.DocumentationMarkup;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import de.dm.intellij.bndtools.BndElementFactory;
import de.dm.intellij.bndtools.psi.BndHeader;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

public class BndDocumentationProvider extends AbstractDocumentationProvider {

    //Documentations from
    // * https://bnd.bndtools.org/chapters/800-headers.html
    // * https://bnd.bndtools.org/chapters/825-instructions-ref.html

    @Nullable
    @Override
    public String generateDoc(PsiElement element, @Nullable PsiElement originalElement) {
        if (element instanceof BndHeader) {
            BndHeader bndHeader = (BndHeader) element;

            String name = bndHeader.getName();

            String description = BndDocumentationBundle.message(name);

            if (description == null) {
                int lastIndexOf = name.lastIndexOf('.');

                if (lastIndexOf > -1) {
                    String parentName = name.substring(0, lastIndexOf);

                    description = BndDocumentationBundle.message(parentName + ".*");
                }
            }

            if (description != null) {
                description = StringUtil.join(StringUtil.split(description, "\n"), "<br>");
            }

            if (description != null) {
                return DocumentationMarkup.DEFINITION_START +
                        name +
                        DocumentationMarkup.DEFINITION_END +
                        DocumentationMarkup.CONTENT_START +
                        description +
                        DocumentationMarkup.CONTENT_END;
            }
        }

        return null;
    }

    @Override
    public @Nullable @Nls String getQuickNavigateInfo(PsiElement element, PsiElement originalElement) {
        return generateDoc(element, originalElement);
    }

    @Override
    public @Nullable PsiElement getDocumentationElementForLookupItem(PsiManager psiManager, Object object, PsiElement element) {
        if (object instanceof String) {
            String lookupString = (String) object;

            return BndElementFactory.getInstance(psiManager.getProject()).createHeader(lookupString);
        }

        return super.getDocumentationElementForLookupItem(psiManager, object, element);
    }
}
