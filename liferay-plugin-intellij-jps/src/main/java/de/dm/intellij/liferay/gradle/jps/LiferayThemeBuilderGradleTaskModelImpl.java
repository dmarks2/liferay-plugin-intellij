package de.dm.intellij.liferay.gradle.jps;

public class LiferayThemeBuilderGradleTaskModelImpl implements LiferayThemeBuilderGradleTaskModel {

    private boolean enabled;
    private String parentName;

    public LiferayThemeBuilderGradleTaskModelImpl() {
    }

    @Override
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
