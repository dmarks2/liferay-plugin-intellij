package de.dm.intellij.liferay.language.osgi.config;

import com.intellij.psi.tree.IElementType;

public interface LiferayConfigElementTypes {
    IElementType FILE = new LiferayConfigElementType("FILE");
    IElementType PROPERTY = new LiferayConfigElementType("PROPERTY");
    IElementType PROPERTY_VALUE = new LiferayConfigElementType("PROPERTY_VALUE");
    IElementType ARRAY_VALUE = new LiferayConfigElementType("ARRAY_VALUE");
    IElementType ARRAY_ELEMENT = new LiferayConfigElementType("ARRAY_ELEMENT");
}
