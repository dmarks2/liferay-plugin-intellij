package de.dm.intellij.liferay.gradle;

import java.io.Serializable;

public interface LiferayThemeBuilderGradleTaskModel extends Serializable {

    /**
     * Returns the name of the parent theme (extracted from the gradle build file)
     * @return the name of the parent theme
     */
    String getParentName();

    /**
     * Returns if the theme builder plugin is present in the gradle build file
     *
     * @return true if theme builder plugin is present
     */
    boolean isEnabled();

}
