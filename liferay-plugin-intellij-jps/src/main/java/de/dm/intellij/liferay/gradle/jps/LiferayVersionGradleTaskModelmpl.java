package de.dm.intellij.liferay.gradle.jps;

public class LiferayVersionGradleTaskModelmpl implements LiferayVersionGradleTaskModel {

	private String liferayWorkspaceProduct;

	@Override
	public String getLiferayWorkspaceProduct() {
		return liferayWorkspaceProduct;
	}

	public void setLiferayWorkspaceProduct(String liferayWorkspaceProduct) {
		this.liferayWorkspaceProduct = liferayWorkspaceProduct;
	}
}
