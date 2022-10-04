package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.xdebugger.XDebuggerUtil;

public class FreemarkerAttachBreakpointTypeTest extends BasePlatformTestCase {

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

    public void testCanPutBreakpointInLayoutTemplate() {
        myFixture.configureByFiles("layouttpl/custom/my_layout.ftl");

        VirtualFile virtualFile = myFixture.getFile().getVirtualFile();

        XDebuggerUtil xDebuggerUtil = XDebuggerUtil.getInstance();

        boolean canPutBreakpointAt = xDebuggerUtil.canPutBreakpointAt(myFixture.getProject(), virtualFile, 5);

        assertTrue("Should be able to set a breakpoint in my_layout.ftl", canPutBreakpointAt);
    }

    public void testCanPutBreakpointInWebContentTemplate() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/simple.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json");

        VirtualFile virtualFile = myFixture.getFile().getVirtualFile();

        XDebuggerUtil xDebuggerUtil = XDebuggerUtil.getInstance();

        boolean canPutBreakpointAt = xDebuggerUtil.canPutBreakpointAt(myFixture.getProject(), virtualFile, 5);

        assertTrue("Should be able to set a breakpoint in simple.ftl", canPutBreakpointAt);
    }

    public void testCanPutBreakpointInCommonMacros() {
        myFixture.configureByFiles("META-INF/resources/common_macros.ftl");

        VirtualFile virtualFile = myFixture.getFile().getVirtualFile();

        XDebuggerUtil xDebuggerUtil = XDebuggerUtil.getInstance();

        boolean canPutBreakpointAt = xDebuggerUtil.canPutBreakpointAt(myFixture.getProject(), virtualFile, 5);

        assertTrue("Should be able to set a breakpoint in common_macros.ftl", canPutBreakpointAt);
    }

}
