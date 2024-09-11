package de.dm.intellij.liferay.workspace;

import junit.framework.TestCase;

public class LiferayWorkspaceUtilTest extends TestCase {

	public void testGetLiferayVersion() {
		assertEquals("7.4.3.120", LiferayWorkspaceUtil.getLiferayVersion("portal-7.4-ga120"));
		assertEquals("7.4.3.55", LiferayWorkspaceUtil.getLiferayVersion("portal-7.4-ga55"));
		assertEquals("7.3.7", LiferayWorkspaceUtil.getLiferayVersion("portal-7.3-ga8"));
		assertEquals("7.3.10.3", LiferayWorkspaceUtil.getLiferayVersion("dxp-7.3.3"));
		assertEquals("7.4.13.u1", LiferayWorkspaceUtil.getLiferayVersion("dxp-7.4-u1"));
		assertEquals("2024.q2.12", LiferayWorkspaceUtil.getLiferayVersion("dxp-2024.q2.12"));
	}
}
