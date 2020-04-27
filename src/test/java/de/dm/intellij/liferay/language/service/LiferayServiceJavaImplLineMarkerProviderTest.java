package de.dm.intellij.liferay.language.service;

import com.intellij.codeInsight.daemon.GutterMark;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.navigation.GotoRelatedItem;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.JavaAwareProjectJdkTableImpl;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.LanguageLevelModuleExtension;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.IdeaTestUtil;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class LiferayServiceJavaImplLineMarkerProviderTest extends LightJavaCodeInsightFixtureTestCase {

    private static final LightProjectDescriptor MY_PROJECT_DESCRIPTOR = new DefaultLightProjectDescriptor() {

        @Override
        public void configureModule(@NotNull Module module, @NotNull ModifiableRootModel model, @NotNull ContentEntry contentEntry) {
            LanguageLevelModuleExtension extension = model.getModuleExtension(LanguageLevelModuleExtension.class);
            if (extension != null) {
                extension.setLanguageLevel(LanguageLevel.JDK_1_8);
            }

            Sdk jdk = JavaAwareProjectJdkTableImpl.getInstanceEx().getInternalJdk();
            model.setSdk(jdk);
        }

        @Override
        public Sdk getSdk() {
            return IdeaTestUtil.getMockJdk18();
        }
    };

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return MY_PROJECT_DESCRIPTOR;
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/service/LiferayServiceJavaImplLineMarkerProviderTest";
    }

    @SuppressWarnings("unchecked")
    public void testExceptionNameInspection() {
        myFixture.configureByFiles(
            "de/dm/liferay/model/impl/MyModelImpl.java",
            "service.xml"
        );

        boolean lineMarkerFound = false;
        List<GutterMark> allMarkers = myFixture.findAllGutters();
        for (GutterMark gutterMark : allMarkers) {
            if (gutterMark instanceof LineMarkerInfo.LineMarkerGutterIconRenderer) {
                LineMarkerInfo.LineMarkerGutterIconRenderer lineMarkerGutterIconRenderer = (LineMarkerInfo.LineMarkerGutterIconRenderer)gutterMark;

                LineMarkerInfo lineMarkerInfo = lineMarkerGutterIconRenderer.getLineMarkerInfo();

                if (lineMarkerInfo instanceof RelatedItemLineMarkerInfo) {
                    RelatedItemLineMarkerInfo relatedItemLineMarkerInfo = (RelatedItemLineMarkerInfo)lineMarkerInfo;

                    Collection<GotoRelatedItem> gotoRelatedItems = relatedItemLineMarkerInfo.createGotoRelatedItems();

                    if (gotoRelatedItems.size() > 0) {
                        GotoRelatedItem gotoRelatedItem = gotoRelatedItems.iterator().next();

                        PsiElement element = gotoRelatedItem.getElement();

                        if (element != null) {
                            lineMarkerFound = true;
                        }
                    }
                }
            }
        }


        assertTrue("Java Implementation line marker not found", lineMarkerFound);
    }
}
