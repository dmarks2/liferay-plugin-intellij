package de.dm.intellij.liferay.gradle;

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
