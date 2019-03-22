package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import com.intellij.xdebugger.XDebuggerUtil;
import de.dm.intellij.liferay.client.Constants;
import de.dm.intellij.liferay.client.LiferayServicesUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class FreemarkerAttachBreakpointTypeTest extends LightCodeInsightFixtureTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/freemarker/runner/FreemarkerAttachBreakpointTypeTest";
    }

    public void testCanPutBreakpointInTheme() {
        myFixture.configureByFiles("templates/portal_normal.ftl", "WEB-INF/liferay-look-and-feel.xml");

        VirtualFile virtualFile = myFixture.getFile().getVirtualFile();

        XDebuggerUtil xDebuggerUtil = XDebuggerUtil.getInstance();

        boolean canPutBreakpointAt = xDebuggerUtil.canPutBreakpointAt(myFixture.getProject(), virtualFile, 5);

        assertTrue("Should be able to set a breakpoint in portal_normal.ftl", canPutBreakpointAt);
    }

}
