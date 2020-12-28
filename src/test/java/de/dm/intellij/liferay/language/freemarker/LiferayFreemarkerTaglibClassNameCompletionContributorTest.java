package de.dm.intellij.liferay.language.freemarker;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.navigation.actions.GotoDeclarationAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.JavaAwareProjectJdkTableImpl;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.LanguageLevelModuleExtension;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URL;
import java.util.List;

public class LiferayFreemarkerTaglibClassNameCompletionContributorTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/freemarker/LiferayFreemarkerTaglibClassNameCompletionContributorTest";

    private static final LightProjectDescriptor JAVA_DESCRIPTOR = new DefaultLightProjectDescriptor() {

        @Override
        public void configureModule(@NotNull Module module, @NotNull ModifiableRootModel model, @NotNull ContentEntry contentEntry) {
            LanguageLevelModuleExtension extension = model.getModuleExtension(LanguageLevelModuleExtension.class);
            if (extension != null) {
                extension.setLanguageLevel(LanguageLevel.JDK_1_8);
            }
            Sdk jdk = JavaAwareProjectJdkTableImpl.getInstanceEx().getInternalJdk();
            model.setSdk(jdk);

            URL resource = LiferayFtlVariableProviderTest.class.getResource("/com/liferay/ftl");
            String resourcePath = PathUtil.toSystemIndependentName(new File(resource.getFile()).getAbsolutePath());
            VfsRootAccess.allowRootAccess( Disposer.newDisposable(), resourcePath );

            resource = LiferayFtlVariableProviderTest.class.getResource("/com/liferay/tld");
            resourcePath = PathUtil.toSystemIndependentName(new File(resource.getFile()).getAbsolutePath());
            VfsRootAccess.allowRootAccess( Disposer.newDisposable(), resourcePath );
        }
    };

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return JAVA_DESCRIPTOR;
    }

    public void testCompletion() {
        myFixture.configureByFiles("portal_normal.ftl", "liferay-aui.tld", "de/dm/MyObject.java");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("de.dm.MyObject"));
    }

    public void testCompletionSquareBrackets() {
        myFixture.configureByFiles("portal_normal_square_brackets.ftl", "liferay-aui.tld", "de/dm/MyObject.java");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("de.dm.MyObject"));
    }

    public void testGotoDeclaration() {
        myFixture.configureByFiles("goto_declaration.ftl", "liferay-aui.tld", "de/dm/MyObject.java");

        int caretOffset = myFixture.getCaretOffset();
        PsiElement targetElement = GotoDeclarationAction.findTargetElement(getProject(), myFixture.getEditor(), caretOffset);

        assertNotNull(targetElement);

        assertTrue(targetElement instanceof PsiClass);

        assertEquals("de.dm.MyObject", ((PsiClass) targetElement).getQualifiedName());
    }
}
