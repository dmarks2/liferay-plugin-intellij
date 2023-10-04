package de.dm.intellij.liferay.language.poshi.annotation;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.JavaAwareProjectJdkTableImpl;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.testFramework.IdeaTestUtil;
import com.intellij.testFramework.JavaProjectTestCase;
import com.intellij.testFramework.PsiTestUtil;
import com.intellij.testFramework.fixtures.IdeaProjectTestFixture;
import com.intellij.testFramework.fixtures.IdeaTestFixtureFactory;
import com.intellij.testFramework.fixtures.JavaCodeInsightTestFixture;
import com.intellij.testFramework.fixtures.JavaTestFixtureFactory;
import com.intellij.testFramework.fixtures.TestFixtureBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class PoshiExternalAnnotatorTest extends JavaProjectTestCase {

	@NotNull
	protected LanguageLevel getProjectLanguageLevel() {
		return LanguageLevel.JDK_11;
	}

	@Override
	protected @Nullable Sdk getTestProjectJdk() {
		return JavaAwareProjectJdkTableImpl.getInstanceEx().getInternalJdk();
	}


	protected JavaCodeInsightTestFixture myFixture;

	private File sourceRootFile;
	private PoshiExternalAnnotator poshiExternalAnnotator = new PoshiExternalAnnotator();

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		TestFixtureBuilder<IdeaProjectTestFixture> projectBuilder = IdeaTestFixtureFactory.getFixtureFactory().createFixtureBuilder(getName());

		myFixture = JavaTestFixtureFactory.getFixtureFactory().createCodeInsightFixture(projectBuilder.getFixture());

		myFixture.setTestDataPath(getTestDataPath());

		myFixture.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		try {
			myFixture.tearDown();
		}
		catch (Throwable e) {
			addSuppressedException(e);
		}
		finally {
			myFixture = null;
			sourceRootFile = null;
			poshiExternalAnnotator = null;
		}

		super.tearDown();
	}

	@Override
	protected void setUpProject() throws Exception {
		super.setUpProject();

		ApplicationManager.getApplication().runWriteAction(() -> {
			ProjectRootManager projectRootManager = ProjectRootManager.getInstance(myProject);

			projectRootManager.setProjectSdk(getTestProjectJdk());
		});
	}



	@Override
	protected void setUpModule() {
		super.setUpModule();

		ApplicationManager.getApplication().runWriteAction(() -> {
			String tempDirPath = FileUtil.getTempDirectory();

			String contentRoot = tempDirPath + "/" + "mymodule";
			String sourceRoot = contentRoot + "/src";

			File contentRootFile = new File(contentRoot);
			sourceRootFile = new File(sourceRoot);

			sourceRootFile.mkdirs();

			VirtualFile contentDir = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(contentRootFile);
			VirtualFile srcDir = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(sourceRootFile);

			final ModuleRootManager rootManager = ModuleRootManager.getInstance(myModule);
			PsiTestUtil.removeAllRoots(myModule, rootManager.getSdk());

			PsiTestUtil.addContentRoot(myModule, contentDir);
			PsiTestUtil.addSourceRoot(myModule, srcDir);

			IdeaTestUtil.setModuleLanguageLevel(myModule, LanguageLevel.JDK_11);
		});
	}


	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/language/poshi/annotation/PoshiExternalAnnotatorTest";
	}


	public void testIncomplete() {
		ApplicationManager.getApplication().runWriteAction(() -> {
			ProjectRootManager projectRootManager = ProjectRootManager.getInstance(myFixture.getProject());

			projectRootManager.setProjectSdk(getTestProjectJdk());
		});

		myFixture.copyFileToProject("testcases/Liferay.testcase", sourceRootFile.getPath() + "/testcases/Liferay.testcase");

		PoshiValidatorRunnerParams params = new PoshiValidatorRunnerParams();

		params.project = myFixture.getProject();
		params.workingDirectory = sourceRootFile;

		PoshiExternalValidationHost validationHost = poshiExternalAnnotator.doAnnotate(params);

		assertNotNull(validationHost);

		assertEmpty(validationHost.errors);
	}

	public void testInvalid() {
		ApplicationManager.getApplication().runWriteAction(() -> {
			ProjectRootManager projectRootManager = ProjectRootManager.getInstance(myFixture.getProject());

			projectRootManager.setProjectSdk(getTestProjectJdk());
		});

		myFixture.copyFileToProject("testcases/Invalid.testcase", sourceRootFile.getPath() + "/testcases/Invalid.testcase");

		PoshiValidatorRunnerParams params = new PoshiValidatorRunnerParams();

		params.project = myFixture.getProject();
		params.workingDirectory = sourceRootFile;

		PoshiExternalValidationHost validationHost = poshiExternalAnnotator.doAnnotate(params);

		assertNotNull(validationHost);

		assertNotEmpty(validationHost.errors);
	}
	/*

	public void testValidFile() {
		myFixture.configureByFiles("testcases/Liferay.testcase");

		myFixture.checkHighlighting();
	}

	public void testInvalidFile() {
		myFixture.copyFileToProject("testcases/Invalid.testcase", "testcases/Invalid.testcase");

		myFixture.configureByFiles("testcases/Invalid.testcase");

		myFixture.checkHighlighting();
	}

	 */
}
