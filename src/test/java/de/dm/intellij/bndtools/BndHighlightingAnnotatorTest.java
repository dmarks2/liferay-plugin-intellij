package de.dm.intellij.bndtools;

import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class BndHighlightingAnnotatorTest extends BasePlatformTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/bndtools/BndHighlightingAnnotatorTest";

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    public void testLineCommentHighlighting() {
        myFixture.configureByFiles("bnd.bnd");

        EditorColorsManager editorColorsManager = EditorColorsManager.getInstance();

        EditorColorsScheme globalScheme = editorColorsManager.getGlobalScheme();

        TextAttributes lineCommentTextAttributes = globalScheme.getAttributes(OsgiManifestColorsAndFonts.LINE_COMMENT_KEY);

        List<HighlightInfo> highlightInfos = myFixture.doHighlighting();

        HighlightInfo highlightInfo = highlightInfos.get(0);

        assertEquals(lineCommentTextAttributes, highlightInfo.getTextAttributes(null, globalScheme));
    }
}
