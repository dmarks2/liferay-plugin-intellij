package de.dm.intellij.liferay.gradle;

import com.intellij.compiler.CompilerTestUtil;
import com.intellij.gradle.toolingExtension.util.GradleVersionUtil;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.externalSystem.model.ProjectSystemId;
import com.intellij.openapi.externalSystem.model.settings.ExternalSystemExecutionSettings;
import com.intellij.openapi.externalSystem.settings.ExternalProjectSettings;
import com.intellij.openapi.externalSystem.settings.ExternalSystemSettingsListener;
import com.intellij.openapi.externalSystem.util.ExternalSystemApiUtil;
import com.intellij.openapi.projectRoots.JavaSdk;
import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkType;
import com.intellij.openapi.projectRoots.SimpleJavaSdkType;
import com.intellij.openapi.projectRoots.impl.SdkConfigurationUtil;
import com.intellij.openapi.ui.TestDialog;
import com.intellij.openapi.ui.TestDialogManager;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.platform.externalSystem.testFramework.ExternalSystemImportingTestCase;
import com.intellij.testFramework.IdeaTestUtil;
import com.intellij.testFramework.RunAll;
import com.intellij.util.SmartList;
import org.gradle.StartParameter;
import org.gradle.util.GradleVersion;
import org.gradle.wrapper.PathAssembler;
import org.gradle.wrapper.WrapperConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.gradle.settings.DistributionType;
import org.jetbrains.plugins.gradle.settings.GradleProjectSettings;
import org.jetbrains.plugins.gradle.settings.GradleSystemSettings;
import org.jetbrains.plugins.gradle.util.GradleConstants;
import org.jetbrains.plugins.gradle.util.GradleUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public abstract class AbstractGradleImportingTestCase extends ExternalSystemImportingTestCase {

	public static final String GRADLE_JDK_NAME = "Gradle JDK";
	private static final int GRADLE_DAEMON_TTL_MS = 10000;

	private final List<Sdk> removedSdks = new SmartList<>();
	private PathAssembler.LocalDistribution myDistribution;

	private GradleProjectSettings myProjectSettings;
	private String myJdkHome;

	private @Nullable Disposable myTestDisposable = null;

	protected abstract GradleVersion getCurrentGradleVersion();

	@Override
	protected void setUp() throws Exception {
		myProjectSettings = new GradleProjectSettings().withQualifiedModuleNames();

		super.setUp();

		WriteAction.runAndWait(this::configureJdkTable);

		System.setProperty(ExternalSystemExecutionSettings.REMOTE_PROCESS_IDLE_TTL_IN_MS_KEY, String.valueOf(GRADLE_DAEMON_TTL_MS));

		installGradleJvmConfigurator();
	}

	@Override
	public void tearDown() throws Exception {
		if (myJdkHome == null) {
			//super.setUpInWriteAction() wasn't called

			RunAll.runAll(
					() -> Disposer.dispose(getTestDisposable()),
					super::tearDown
			);

			return;
		}

		RunAll.runAll(
				() -> {
					WriteAction.runAndWait(() -> {
						Arrays.stream(ProjectJdkTable.getInstance().getAllJdks()).forEach(ProjectJdkTable.getInstance()::removeJdk);
						for (Sdk sdk : removedSdks) {
							SdkConfigurationUtil.addSdk(sdk);
						}
						removedSdks.clear();
					});
				},
				() -> {
					TestDialogManager.setTestDialog(TestDialog.DEFAULT);
					CompilerTestUtil.deleteBuildSystemDirectory(myProject);
				},
				() -> {
					if (isGradleAtLeast("7.0")) {
						GradleSystemSettings.getInstance().setGradleVmOptions("");
					}
				},
				() -> Disposer.dispose(getTestDisposable()),
				super::tearDown
		);
	}

	protected boolean isGradleAtLeast(@NotNull String ver) {
		return GradleVersionUtil.isGradleAtLeast(getCurrentGradleVersion(), ver);
	}

	protected void installGradleJvmConfigurator() {
		ExternalSystemApiUtil.subscribe(myProject, GradleConstants.SYSTEM_ID, new ExternalSystemSettingsListener<GradleProjectSettings>() {
			@Override
			public void onProjectsLinked(@NotNull Collection<GradleProjectSettings> settings) {
				for (var projectSettings : settings) {
					projectSettings.setGradleJvm(GRADLE_JDK_NAME);
				}
			}
		}, getTestDisposable());
	}

	@Override
	protected ExternalProjectSettings getCurrentExternalProjectSettings() {
		return myProjectSettings;
	}

	@Override
	protected ProjectSystemId getExternalSystemId() {
		return GradleConstants.SYSTEM_ID;
	}

	@Override
	protected String getTestsTempDir() {
		return "tmp";
	}

	@Override
	protected String getExternalSystemConfigFileName() {
		return "build.gradle";
	}

	protected void setUpInWriteAction() throws Exception {
		super.setUpInWriteAction();

		myJdkHome = requireRealJdkHome();

		myDistribution = configureWrapper();
	}

	protected void configureJdkTable() {
		cleanJdkTable();

		ArrayList<Sdk> jdks = new ArrayList<>(Arrays.asList(createJdkFromJavaHome()));

		populateJdkTable(jdks);
	}

	protected void cleanJdkTable() {
		removedSdks.clear();

		for (Sdk sdk : ProjectJdkTable.getInstance().getAllJdks()) {
			ProjectJdkTable.getInstance().removeJdk(sdk);

			if (GRADLE_JDK_NAME.equals(sdk.getName())) {
				continue;
			}

			removedSdks.add(sdk);
		}
	}

	protected void populateJdkTable(@NotNull List<Sdk> jdks) {
		for (Sdk jdk : jdks) {
			ProjectJdkTable.getInstance().addJdk(jdk);
		}
	}

	private Sdk createJdkFromJavaHome() {
		VirtualFile jdkHomeDir = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(new File(myJdkHome));

		JavaSdk javaSdk = JavaSdk.getInstance();

		SdkType javaSdkType = javaSdk == null ? SimpleJavaSdkType.getInstance() : javaSdk;

		Sdk jdk = SdkConfigurationUtil.setupSdk(new Sdk[0], jdkHomeDir, javaSdkType, true, null, GRADLE_JDK_NAME);

		assertNotNull("Cannot create JDK for " + myJdkHome, jdk);

		return jdk;
	}

	private String requireRealJdkHome() {
		return IdeaTestUtil.requireRealJdkHome();
	}

	private PathAssembler.LocalDistribution configureWrapper() {

		myProjectSettings.setDistributionType(DistributionType.DEFAULT_WRAPPED);

		WriteAction.runAndWait(() -> GradleWrapperUtil.generateGradleWrapper(myProjectRoot.toNioPath(), getCurrentGradleVersion()));

		String projectPath = getProjectPath();
		WrapperConfiguration wrapperConfiguration = GradleUtil.getWrapperConfiguration(projectPath);
		PathAssembler pathAssembler = new PathAssembler(StartParameter.DEFAULT_GRADLE_USER_HOME, new File(projectPath));
		PathAssembler.LocalDistribution localDistribution = pathAssembler.getDistribution(wrapperConfiguration);

		File zip = localDistribution.getZipFile();
		try {
			if (zip.exists()) {
				try {
					new ZipFile(zip).close();
				}
				catch (ZipException e) {
					e.printStackTrace();
					System.out.println("Corrupted file will be removed: " + zip);
					Files.delete(zip.toPath());
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return localDistribution;
	}

	private @NotNull Disposable getTestDisposable() {
		if (myTestDisposable == null) {
			myTestDisposable = Disposer.newDisposable();
		}
		return myTestDisposable;
	}

}
