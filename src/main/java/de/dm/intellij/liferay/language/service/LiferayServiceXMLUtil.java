package de.dm.intellij.liferay.language.service;

import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTagChild;
import com.intellij.psi.xml.XmlText;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

public class LiferayServiceXMLUtil {

    public static boolean isColumnNameAttribute(@NotNull XmlAttributeValue xmlAttributeValue) {
        return Stream.of(
            xmlAttributeValue
        ).map(
            XmlAttributeValue::getParent
        ).filter(
            parent -> parent instanceof XmlAttribute
        ).map(
            xmlAttribute -> (XmlAttribute)xmlAttribute
        ).filter(
            xmlAttribute -> "name".equals(xmlAttribute.getLocalName())
        ).map(
            XmlAttribute::getParent
        ).filter(
            Objects::nonNull
        ).filter(
            parentTag -> "column".equals(parentTag.getLocalName())
        ).map(
            XmlTagChild::getParentTag
        ).filter(
            Objects::nonNull
        ).filter(
            grandParentTag -> "entity".equals(grandParentTag.getLocalName())
        ).map(
            XmlTagChild::getParentTag
        ).filter(
            Objects::nonNull
        ).anyMatch(
            grandParentTag -> "service-builder".equals(grandParentTag.getLocalName())
        );
    }

    public static boolean isFinderNameAttribute(@NotNull XmlAttributeValue xmlAttributeValue) {
        return Stream.of(
            xmlAttributeValue
        ).map(
            XmlAttributeValue::getParent
        ).filter(
            parent -> parent instanceof XmlAttribute
        ).map(
            xmlAttribute -> (XmlAttribute)xmlAttribute
        ).filter(
            xmlAttribute -> "name".equals(xmlAttribute.getLocalName())
        ).map(
            XmlAttribute::getParent
        ).filter(
            Objects::nonNull
        ).filter(
            parentTag -> "finder".equals(parentTag.getLocalName())
        ).map(
            XmlTagChild::getParentTag
        ).filter(
            Objects::nonNull
        ).filter(
            grandParentTag -> "entity".equals(grandParentTag.getLocalName())
        ).map(
            XmlTagChild::getParentTag
        ).filter(
            Objects::nonNull
        ).anyMatch(
            grandParentTag -> "service-builder".equals(grandParentTag.getLocalName())
        );
    }

    public static boolean isEntityNameAttribute(@NotNull XmlAttributeValue xmlAttributeValue) {
        return Stream.of(
            xmlAttributeValue
        ).map(
            XmlAttributeValue::getParent
        ).filter(
            parent -> parent instanceof XmlAttribute
        ).map(
            xmlAttribute -> (XmlAttribute)xmlAttribute
        ).filter(
            xmlAttribute -> "name".equals(xmlAttribute.getLocalName())
        ).map(
            XmlAttribute::getParent
        ).filter(
            Objects::nonNull
        ).filter(
            parentTag -> "entity".equals(parentTag.getLocalName())
        ).map(
            XmlTagChild::getParentTag
        ).filter(
            Objects::nonNull
        ).anyMatch(
            grandParentTag -> "service-builder".equals(grandParentTag.getLocalName())
        );
    }

    public static boolean isExceptionTag(@NotNull XmlText xmlText) {
        return Stream.of(
            xmlText
        ).map(
            XmlTagChild::getParentTag
        ).filter(
            Objects::nonNull
        ).filter(
            parentTag -> "exception".equals(parentTag.getLocalName())
        ).map(
            XmlTagChild::getParentTag
        ).filter(
            Objects::nonNull
        ).filter(
            grandParentTag -> "exceptions".equals(grandParentTag.getLocalName())
        ).map(
            XmlTagChild::getParentTag
        ).filter(
            Objects::nonNull
        ).anyMatch(
            grandParentTag -> "service-builder".equals(grandParentTag.getLocalName())
        );
    }

    public static boolean isNamespaceTag(@NotNull XmlText xmlText) {
        return Stream.of(
            xmlText
        ).map(
            XmlTagChild::getParentTag
        ).filter(
            Objects::nonNull
        ).filter(
            parentTag -> "namespace".equals(parentTag.getLocalName())
        ).map(
            XmlTagChild::getParentTag
        ).filter(
            Objects::nonNull
        ).anyMatch(
            grandParentTag -> "service-builder".equals(grandParentTag.getLocalName())
        );
    }
}
