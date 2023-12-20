package de.dm.intellij.liferay.gradle;

import java.io.Serializable;

public interface LiferayBundleSupportGradleTaskModel extends Serializable {

    /**
     * Returns the liferayHome directory (extracted from the bundle support plugin)
     *
     * @return The liferay home
     */
    String getLiferayHome();

}
