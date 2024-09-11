package de.dm.intellij.liferay.gradle.jps;

public class LiferayBundleSupportGradleTaskModelImpl implements LiferayBundleSupportGradleTaskModel {

    private String liferayHome;

    @Override
    public String getLiferayHome() {
        return liferayHome;
    }

    public void setLiferayHome(String liferayHome) {
        this.liferayHome = liferayHome;
    }
}
