package de.dm.intellij.liferay.maven.importer;

import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.projectRoots.impl.JavaAwareProjectJdkTableImpl;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.testFramework.RunAll;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.idea.maven.model.MavenExplicitProfiles;
import org.jetbrains.idea.maven.project.MavenGeneralSettings;
import org.jetbrains.idea.maven.project.MavenProject;
import org.jetbrains.idea.maven.project.MavenProjectsManager;
import org.jetbrains.idea.maven.project.MavenProjectsTree;
import org.jetbrains.idea.maven.project.StaticResolvedMavenHomeType;
import org.jetbrains.idea.maven.server.MavenServerManager;
import org.jetbrains.idea.maven.utils.MavenWslUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public abstract class MavenImportingTestCase extends MavenTestCase {
  protected MavenProjectsTree myProjectsTree;
  protected MavenProjectsManager myProjectsManager;

  @Override
  protected void setUp() throws Exception {
    VfsRootAccess.allowRootAccess(getTestRootDisposable(), PathManager.getConfigPath());

    super.setUp();

    MavenGeneralSettings mavenGeneralSettings = getMavenGeneralSettings();
    File settingsFile =  MavenWslUtil.getGlobalSettings(myProject, (StaticResolvedMavenHomeType) mavenGeneralSettings.getMavenHomeType(), mavenGeneralSettings.getMavenConfig());

    if (settingsFile != null) {
      VfsRootAccess.allowRootAccess(getTestRootDisposable(), settingsFile.getAbsolutePath());
    }
  }

  @Override
  protected void tearDown() {
    new RunAll(
            () -> WriteAction.runAndWait(JavaAwareProjectJdkTableImpl::removeInternalJdkInTests),
            /*      () -> Messages.setTestDialog(TestDialog.DEFAULT), */
            () -> removeFromLocalRepository("test"),
            () -> myProjectsManager = null,
            () -> myProjectsTree = null,
            super::tearDown
    ).run();
  }

  @Override
  protected void setUpInWriteAction() throws Exception {
    super.setUpInWriteAction();
    myProjectsManager = MavenProjectsManager.getInstance(myProject);
    removeFromLocalRepository("test");
  }

  protected Module getModule(final String name) {
    Module m = ReadAction.compute(() -> ModuleManager.getInstance(myProject).findModuleByName(name));
    assertNotNull("Module " + name + " not found", m);
    return m;
  }
  protected void importProject(VirtualFile file) {
    importProjects(file);
  }

  protected void importProjects(VirtualFile... files) {
    doImportProjects(Arrays.asList(files), true);
  }

  private void doImportProjects(final List<VirtualFile> files, boolean failOnReadingError, String... profiles) {
    initProjectsManager(false);

    readProjects(files, profiles);

    UIUtil.invokeAndWaitIfNeeded(() -> myProjectsManager.importProjects());

    if (failOnReadingError) {
      for (MavenProject each : myProjectsTree.getProjects()) {
        assertFalse("Failed to import Maven project: " + each.getProblems(), each.hasReadingProblems());
      }
    }
  }

  protected void readProjects(List<VirtualFile> files, String... profiles) {
    myProjectsManager.resetManagedFilesAndProfilesInTests(files, new MavenExplicitProfiles(Arrays.asList(profiles)));
    waitForReadingCompletion();
  }


  protected void initProjectsManager(boolean enableEventHandling) {
    myProjectsManager.initForTests();
    myProjectsTree = myProjectsManager.getProjectsTreeForTests();
    if (enableEventHandling) myProjectsManager.listenForExternalChanges();
  }


  protected void waitForReadingCompletion() {
    UIUtil.invokeAndWaitIfNeeded(() -> {
      try {
        myProjectsManager.waitForReadingCompletion();
      }
      catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
  }

  protected void removeFromLocalRepository(String relativePath) {
    if (SystemInfo.isWindows) {
      MavenServerManager.getInstance().shutdown(true);
    }
    FileUtil.delete(new File(getRepositoryPath(), relativePath));
  }

  /**
   * Create a new module into the test project from existing in memory POM.
   *
   * @param name the new module name
   * @param xml the project POM
   * @return the created module
   */
  protected Module createMavenModule(String name, String xml) {
    Module module = myTestFixture.getModule();
    File moduleDir = new File(module.getModuleFilePath()).getParentFile();
    VirtualFile pomFile = createPomFile(LocalFileSystem.getInstance().findFileByIoFile(moduleDir), xml);
    importProject(pomFile);
    Module[] modules = ModuleManager.getInstance(myTestFixture.getProject()).getModules();
    if (modules.length > 0) {
      module = modules[modules.length - 1];
      //setupJdkForModule(module.getName());
    }
    return module;
  }
}
