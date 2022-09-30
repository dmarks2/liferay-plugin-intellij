package de.dm.intellij.liferay.workflow;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlText;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LiferayWorkflowXmlTargetReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {

    private static final String[] TARGET_TAGS = new String[] {
            "task", "state", "fork", "join", "join-xor"
    };

    public LiferayWorkflowXmlTargetReference(@NotNull PsiElement psiElement) {
        super(psiElement);
    }

    @Override
    public @Nullable PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);

        if (resolveResults.length == 1) {
            return resolveResults[0].getElement();
        }

        return null;
    }

    @Override
    public Object @NotNull [] getVariants() {
        XmlText xmlText = getXmlText();

        List<Object> result = new ArrayList<Object>();

        PsiFile psiFile = xmlText.getContainingFile();

        psiFile = psiFile.getOriginalFile();

        if (psiFile instanceof XmlFile) {
            XmlFile xmlFile = (XmlFile) psiFile;

            XmlDocument xmlDocument = xmlFile.getDocument();

            XmlTag rootTag = xmlDocument.getRootTag();

            for (String targetTag : TARGET_TAGS) {
                XmlTag[] elements = rootTag.findSubTags(targetTag, xmlText.getParentTag().getNamespace());

                for (XmlTag element : elements) {
                    XmlTag name = element.findFirstSubTag("name");

                    if (name != null) {
                        XmlText text = PsiTreeUtil.getChildOfType(name, XmlText.class);

                        if (text != null) {
                            result.add(
                                    LookupElementBuilder.create(text.getText()).withPsiElement(text).withIcon(Icons.LIFERAY_ICON).withTypeText(element.getLocalName(), true)
                            );
                        }
                    }
                }
            }
        }

        return result.toArray(new Object[result.size()]);
    }

    @Override
    public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
        XmlText xmlText = getXmlText();

        PsiFile psiFile = xmlText.getContainingFile();

        psiFile = psiFile.getOriginalFile();

        Collection<PsiElement> results = new ArrayList<>();

        if (psiFile instanceof XmlFile) {
            XmlFile xmlFile = (XmlFile) psiFile;

            XmlDocument xmlDocument = xmlFile.getDocument();

            XmlTag rootTag = xmlDocument.getRootTag();

            for (String targetTag : TARGET_TAGS) {
                XmlTag[] elements = rootTag.findSubTags(targetTag, xmlText.getParentTag().getNamespace());

                for (XmlTag element : elements) {
                    XmlTag name = element.findFirstSubTag("name");

                    if (name != null) {
                        XmlText text = PsiTreeUtil.getChildOfType(name, XmlText.class);

                        if (text != null) {
                            if (getElement().getText().equals(text.getText())) {
                                results.add(text);
                            }
                        }
                    }
                }
            }
        }

        return PsiElementResolveResult.createResults(results);
    }

    private XmlText getXmlText() {
        return PsiTreeUtil.getParentOfType(getElement(), XmlText.class);
    }

}
