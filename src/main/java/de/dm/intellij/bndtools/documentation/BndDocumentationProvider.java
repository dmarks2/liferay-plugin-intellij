package de.dm.intellij.bndtools.documentation;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import de.dm.intellij.bndtools.psi.BndHeader;
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

            String info = "<b>" + name + "</b><br/>\n";

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

                info += description;
            }

            return info;
        }

        return null;
    }

}
