package de.dm.intellij.liferay.language.service;

import com.intellij.codeInsight.daemon.GutterMark;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.navigation.GotoRelatedItem;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class LiferayServiceXMLLineMarkerProviderTest extends LightJavaCodeInsightFixtureTestCase {

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return LightProjectDescriptorBuilder.DEFAULT_PROJECT_DESCRIPTOR;
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/service/LiferayServiceXMLLineMarkerProviderTest";
    }

    @SuppressWarnings("unchecked")
    public void testExceptionNameInspection() {
        myFixture.configureByFiles(
            "service.xml",
            "de/dm/liferay/model/impl/MyModelImpl.java"
        );

        boolean lineMarkerFound = false;
        List<GutterMark> allMarkers = myFixture.findAllGutters();
        for (GutterMark gutterMark : allMarkers) {
            if (gutterMark instanceof LineMarkerInfo.LineMarkerGutterIconRenderer lineMarkerGutterIconRenderer) {

				LineMarkerInfo lineMarkerInfo = lineMarkerGutterIconRenderer.getLineMarkerInfo();

                if (lineMarkerInfo instanceof RelatedItemLineMarkerInfo relatedItemLineMarkerInfo) {

					Collection<GotoRelatedItem> gotoRelatedItems = relatedItemLineMarkerInfo.createGotoRelatedItems();

                    if (!gotoRelatedItems.isEmpty()) {
                        GotoRelatedItem gotoRelatedItem = gotoRelatedItems.iterator().next();

                        PsiElement element = gotoRelatedItem.getElement();

                        if (element != null) {
                            lineMarkerFound = true;
                        }
                    }
                }
            }
        }


        assertTrue("service.xml line marker not found", lineMarkerFound);
    }

}
