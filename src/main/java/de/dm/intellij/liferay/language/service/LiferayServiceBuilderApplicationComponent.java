package de.dm.intellij.liferay.language.service;

import com.intellij.util.xml.ElementPresentationManager;
import com.liferay.service.builder.Column;
import com.liferay.service.builder.Entity;

public class LiferayServiceBuilderApplicationComponent {

    public LiferayServiceBuilderApplicationComponent() {
        ElementPresentationManager.registerNameProvider((o) -> {
            if (o instanceof Entity) {
                return ((Entity) o).getName();
            } else if (o instanceof Column) {
                return ((Column) o).getName();
            }

            return null;
        });
    }

}
