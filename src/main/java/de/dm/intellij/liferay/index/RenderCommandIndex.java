package de.dm.intellij.liferay.index;

import com.intellij.ide.highlighter.JavaClassFileType;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.DataIndexer;
import com.intellij.util.indexing.DefaultFileTypeSpecificInputFilter;
import com.intellij.util.indexing.FileBasedIndex;
import com.intellij.util.indexing.FileBasedIndexExtension;
import com.intellij.util.indexing.FileContent;
import com.intellij.util.indexing.ID;
import com.intellij.util.io.DataExternalizer;
import com.intellij.util.io.KeyDescriptor;
import com.intellij.util.io.VoidDataExternalizer;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * FileBasedIndexer to quickly find all render commands names
 */
public class RenderCommandIndex extends FileBasedIndexExtension<CommandKey, Void> {

    @NonNls
    public static final ID<CommandKey, Void> NAME = ID.create("RenderCommandIndex");

    private final RenderCommandIndexer renderCommandIndexer = new RenderCommandIndexer();

    @NotNull
    @Override
    public ID<CommandKey, Void> getName() {
        return NAME;
    }

    @NotNull
    @Override
    public DataIndexer<CommandKey, Void, FileContent> getIndexer() {
        return renderCommandIndexer;
    }

    @NotNull
    @Override
    public KeyDescriptor<CommandKey> getKeyDescriptor() {
        return new CommandKeyDescriptor();
    }

    @NotNull
    @Override
    public DataExternalizer<Void> getValueExternalizer() {
        return VoidDataExternalizer.INSTANCE;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @NotNull
    @Override
    public FileBasedIndex.InputFilter getInputFilter() {
        return new DefaultFileTypeSpecificInputFilter(JavaFileType.INSTANCE, JavaClassFileType.INSTANCE);
    }

    @Override
    public boolean dependsOnFileContent() {
        return true;
    }

    public static List<String> getRenderCommands(@NotNull String portletName, Project project, GlobalSearchScope scope) {
        return AbstractCommandKeyIndexer.getCommands(NAME, portletName, project, scope);
    }

    public static List<PsiFile> getPortletClasses(Project project, String portletName, String commandName, GlobalSearchScope scope) {
        return AbstractCommandKeyIndexer.getPortletClasses(NAME, project, portletName, commandName, scope);
    }

    private class RenderCommandIndexer extends AbstractCommandKeyIndexer {

        @NotNull
        @Override
        protected String[] getServiceClassNames() {
            return new String[]{"com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand"};
        }

    }

}
