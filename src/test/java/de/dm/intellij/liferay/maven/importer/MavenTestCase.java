package de.dm.intellij.liferay.maven.importer;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.LanguageLevelProjectExtension;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.testFramework.EdtTestUtil;
import com.intellij.testFramework.RunAll;
import com.intellij.testFramework.UsefulTestCase;
import com.intellij.testFramework.builders.JavaModuleFixtureBuilder;
import com.intellij.testFramework.builders.ModuleFixtureBuilder;
import com.intellij.testFramework.fixtures.IdeaProjectTestFixture;
import com.intellij.testFramework.fixtures.IdeaTestFixtureFactory;
import com.intellij.testFramework.fixtures.JavaTestFixtureFactory;
import com.intellij.testFramework.fixtures.TestFixtureBuilder;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.idea.maven.project.MavenGeneralSettings;
import org.jetbrains.idea.maven.project.MavenHomeKt;
import org.jetbrains.idea.maven.project.MavenHomeType;
import org.jetbrains.idea.maven.project.MavenProjectsManager;
import org.jetbrains.idea.maven.project.MavenWorkspaceSettings;
import org.jetbrains.idea.maven.project.MavenWorkspaceSettingsComponent;
import org.jetbrains.idea.maven.project.StaticResolvedMavenHomeType;
import org.jetbrains.idea.maven.server.MavenServerManager;
import org.jetbrains.idea.maven.utils.MavenWslUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class MavenTestCase extends UsefulTestCase {
  private File ourTempDir;

  protected TestFixtureBuilder<IdeaProjectTestFixture> myProjectBuilder;
  protected IdeaProjectTestFixture myTestFixture;

  protected Project myProject;

  protected File myDir;
  protected VirtualFile myProjectRoot;

  protected VirtualFile myProjectPom;
  protected List<VirtualFile> myAllPoms = new ArrayList<>();

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    ensureTempDirCreated();

    myDir = new File(ourTempDir, getTestName(false));
    FileUtil.ensureExists(myDir);

    setUpFixtures();

    myProject = myTestFixture.getProject();

    MavenWorkspaceSettingsComponent.getInstance(myProject).loadState(new MavenWorkspaceSettings());

    String home = getTestMavenHome();
    if (home != null) {
      MavenHomeType mavenHomeType = MavenHomeKt.resolveMavenHomeType(home);

      getMavenGeneralSettings().setMavenHomeType(mavenHomeType);
    }

    EdtTestUtil.runInEdtAndWait(() -> {
      restoreSettingsFile();

      ApplicationManager.getApplication().runWriteAction(() -> {
        try {
          setUpInWriteAction();
        }
        catch (Throwable e) {
          try {
            tearDown();
          }
          catch (Exception e1) {
            e1.printStackTrace();
          }
          throw new RuntimeException(e);
        }
      });
    });
  }

  @Override
  protected void tearDown() throws Exception {
    new RunAll(
      () -> MavenServerManager.getInstance().shutdown(true),
      () -> EdtTestUtil.runInEdtAndWait(() -> tearDownFixtures()),
      () -> myProject = null,
      () -> super.tearDown(),
      () -> {
        if (myDir != null) {
          FileUtil.delete(myDir);
          // cannot use reliably the result of the com.intellij.openapi.util.io.FileUtil.delete() method
          // because com.intellij.openapi.util.io.FileUtilRt.deleteRecursivelyNIO() does not honor this contract
          if (myDir.exists()) {
            System.err.println("Cannot delete " + myDir);
            //printDirectoryContent(myDir);
            myDir.deleteOnExit();
          }
        }
      },
      () -> resetClassFields(getClass())
    ).run();
  }

  private void ensureTempDirCreated() throws IOException {
    if (ourTempDir != null) return;

    ourTempDir = new File(FileUtil.getTempDirectory(), "mavenTests");
    FileUtil.delete(ourTempDir);
    FileUtil.ensureExists(ourTempDir);
  }

  protected void setUpFixtures() throws Exception {
    myProjectBuilder = IdeaTestFixtureFactory.getFixtureFactory().createFixtureBuilder(getName());
    final JavaTestFixtureFactory factory = JavaTestFixtureFactory.getFixtureFactory();

    myProjectBuilder.addModule(JavaModuleFixtureBuilder.class);

    myTestFixture = factory.createCodeInsightFixture(myProjectBuilder.getFixture());
    myTestFixture.setUp();
    LanguageLevelProjectExtension.getInstance(myTestFixture.getProject()).setLanguageLevel(LanguageLevel.JDK_1_6);
  }

  protected void setUpInWriteAction() throws Exception {
    File projectDir = new File(myDir, "project");
    projectDir.mkdirs();
    myProjectRoot = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(projectDir);
  }

  protected void tearDownFixtures() throws Exception {
    try {
      myTestFixture.tearDown();
    }
    finally {
      myTestFixture = null;
    }
  }

  private void resetClassFields(final Class<?> aClass) {
    if (aClass == null) return;

    final Field[] fields = aClass.getDeclaredFields();
    for (Field field: fields) {
      final int modifiers = field.getModifiers();
      if ((modifiers & Modifier.FINAL) == 0
          && (modifiers & Modifier.STATIC) == 0
          && !field.getType().isPrimitive()) {
        field.setAccessible(true);
        try {
          field.set(this, null);
        }
        catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }

    if (aClass == MavenTestCase.class) return;
    resetClassFields(aClass.getSuperclass());
  }

  protected MavenGeneralSettings getMavenGeneralSettings() {
    return MavenProjectsManager.getInstance(myProject).getGeneralSettings();
  }

  protected String getRepositoryPath() {
    String path = getRepositoryFile().getPath();
    return FileUtil.toSystemIndependentName(path);
  }

  protected File getRepositoryFile() {
    MavenGeneralSettings mavenGeneralSettings = getMavenGeneralSettings();
    return MavenWslUtil.getLocalRepo(myProject, mavenGeneralSettings.getLocalRepository(), (StaticResolvedMavenHomeType) mavenGeneralSettings.getMavenHomeType(), mavenGeneralSettings.getUserSettingsFile(), mavenGeneralSettings.getMavenConfig());
  }
  protected String getProjectPath() {
    return myProjectRoot.getPath();
  }

  protected VirtualFile updateSettingsXml(String content) throws IOException {
    return updateSettingsXmlFully(createSettingsXmlContent(content));
  }

  protected VirtualFile updateSettingsXmlFully(@NonNls @Language("XML") String content) throws IOException {
    File ioFile = new File(myDir, "settings.xml");
    ioFile.createNewFile();
    VirtualFile f = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(ioFile);
    setFileContent(f, content, true);
    getMavenGeneralSettings().setUserSettingsFile(f.getPath());
    return f;
  }

  private static String createSettingsXmlContent(String content) {
    return "<settings>" +
           "</settings>";
  }

  protected void restoreSettingsFile() throws IOException {
    updateSettingsXml("");
  }

  protected VirtualFile createPomFile(final VirtualFile dir,
                                      @Language(value = "XML", prefix = "<project>", suffix = "</project>") String xml) {
    VirtualFile f = dir.findChild("pom.xml");
    if (f == null) {
      try {
        f = WriteAction.computeAndWait(() -> dir.createChildData(null, "pom.xml"));
      }
      catch (IOException e) {
        throw new RuntimeException(e);
      }
      myAllPoms.add(f);
    }
    setFileContent(f, createPomXml(xml), true);
    return f;
  }

  @NonNls
  @Language(value = "XML")
  public static String createPomXml(@NonNls @Language(value = "XML", prefix = "<project>", suffix = "</project>") String xml) {
    return xml;
  }

  private static void setFileContent(final VirtualFile file, final String content, final boolean advanceStamps) {
    try {
      WriteAction.runAndWait(() -> {
        if (advanceStamps) {
          file.setBinaryContent(content.getBytes(StandardCharsets.UTF_8), -1, file.getTimeStamp() + 4000);
        }
        else {
          file.setBinaryContent(content.getBytes(StandardCharsets.UTF_8), file.getModificationStamp(), file.getTimeStamp());
        }
      });
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static String getTestMavenHome() {
    return System.getProperty("idea.maven.test.home");
  }

}
