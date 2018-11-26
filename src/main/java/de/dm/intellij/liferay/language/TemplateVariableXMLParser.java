package de.dm.intellij.liferay.language;

import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TemplateVariableXMLParser implements TemplateVariableParser<XmlFile> {

    @Override
    public List<TemplateVariable> getTemplateVariables(XmlFile xmlFile, PsiFile templateFile) {
        List<TemplateVariable> result = new ArrayList<>();

        XmlDocument xmlDocument = xmlFile.getDocument();

        XmlTag rootTag = xmlDocument.getRootTag();

        XmlTag[] subTags = rootTag.findSubTags("dynamic-element");

        for (XmlTag tag : subTags) {
            TemplateVariable templateVariable = getTemplateVariable(templateFile, tag);
            if (templateVariable != null) {
                result.add(templateVariable);
            }
        }

        return result;
    }

    @Nullable
    private TemplateVariable getTemplateVariable(PsiFile templateFile, XmlTag xmlTag) {
        XmlAttribute xmlAttribute = xmlTag.getAttribute("name");
        if (xmlAttribute != null) {
            TemplateVariable templateVariable = new TemplateVariable();

            String name = xmlAttribute.getValue();

            templateVariable.setName(name);

            boolean repeatable = false;
            if ( (xmlTag.getAttribute("repeatable") != null) && "true".equalsIgnoreCase(xmlTag.getAttributeValue("repeatable")) ) {
                repeatable = true;
            }

            templateVariable.setRepeatable(repeatable);
            templateVariable.setNavigationalElement(xmlTag.getNavigationElement());
            templateVariable.setParentFile(templateFile);

            XmlTag[] subTags = xmlTag.findSubTags("dynamic-element");
            for (XmlTag subTag : subTags) {
                TemplateVariable nestedVariable = getTemplateVariable(templateFile, subTag);
                if (nestedVariable != null) {
                    templateVariable.addNestedVariable(nestedVariable);
                }
            }

            return templateVariable;
        }

        return null;
    }
}
