package de.dm.intellij.liferay.language.freemarker;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.navigation.actions.GotoDeclarationAction;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayFreemarkerTaglibClassNameCompletionContributorTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/freemarker/LiferayFreemarkerTaglibClassNameCompletionContributorTest";

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return new LightProjectDescriptorBuilder()
                .rootAccess(
                        "/com/liferay/ftl",
                        "/com/liferay/tld"
                ).build();

    }

    public void testCompletion() {
        myFixture.configureByFiles("portal_normal.ftl", "liferay-aui.tld", "de/dm/MyObject.java");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("de.dm.MyObject"));
    }

    public void testCompletionSquareBrackets() {
        myFixture.configureByFiles("portal_normal_square_brackets.ftl", "liferay-aui.tld", "de/dm/MyObject.java");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
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
