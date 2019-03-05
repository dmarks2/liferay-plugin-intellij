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

        String defaultLanguageId = rootTag.getAttributeValue("default-locale");

        XmlTag[] subTags = rootTag.findSubTags("dynamic-element");

        for (XmlTag tag : subTags) {
            TemplateVariable templateVariable = getTemplateVariable(templateFile, xmlFile, tag, defaultLanguageId);
            if (templateVariable != null) {
                result.add(templateVariable);
            }
        }

        return result;
    }

    @Nullable
    private TemplateVariable getTemplateVariable(PsiFile templateFile, PsiFile originalFile, XmlTag xmlTag, String defaultLanguageId) {
        XmlAttribute xmlAttribute = xmlTag.getAttribute("name");
        if (xmlAttribute != null) {
            TemplateVariable templateVariable = new TemplateVariable();

            String name = xmlAttribute.getValue();

            templateVariable.setName(name);

            boolean repeatable = false;
            if ( (xmlTag.getAttribute("repeatable") != null) && "true".equalsIgnoreCase(xmlTag.getAttributeValue("repeatable")) ) {
                repeatable = true;
            }

            String type = xmlTag.getAttributeValue("type");

            XmlTag[] metaDataTags = xmlTag.findSubTags("meta-data");
            for (XmlTag metaDataTag : metaDataTags) {
                String locale = metaDataTag.getAttributeValue("locale");
                String label = metaDataTag.getSubTagText("label");
                String tip = metaDataTag.getSubTagText("tip");
                if (label != null) {
                    templateVariable.getLabels().put(locale, label);
                }
                if (tip != null) {
                    templateVariable.getTips().put(locale, tip);
                }
            }

            templateVariable.setRepeatable(repeatable);
            templateVariable.setNavigationalElement(xmlTag.getNavigationElement());
            templateVariable.setParentFile(templateFile);
            templateVariable.setOriginalFile(originalFile);
            templateVariable.setDefaultLanguageId(defaultLanguageId);
            templateVariable.setType(type);

            XmlTag[] subTags = xmlTag.findSubTags("dynamic-element");
            for (XmlTag subTag : subTags) {
                TemplateVariable nestedVariable = getTemplateVariable(templateFile, originalFile, subTag, defaultLanguageId);
                if (nestedVariable != null) {
                    templateVariable.addNestedVariable(nestedVariable);
                }
            }

            return templateVariable;
        }

        return null;
    }
}
