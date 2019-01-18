package de.dm.intellij.liferay.index;

import com.intellij.ide.highlighter.JavaClassFileType;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.project.IndexNotReadyException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.DataIndexer;
import com.intellij.util.indexing.DefaultFileTypeSpecificInputFilter;
import com.intellij.util.indexing.FileBasedIndex;
import com.intellij.util.indexing.FileBasedIndexExtension;
import com.intellij.util.indexing.FileContent;
import com.intellij.util.indexing.ID;
import com.intellij.util.indexing.PsiDependentIndex;
import com.intellij.util.io.DataExternalizer;
import com.intellij.util.io.EnumeratorStringDescriptor;
import com.intellij.util.io.KeyDescriptor;
import com.intellij.util.io.VoidDataExternalizer;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * FileBasedIndexer to quickly find all action commands names
 */
public class ActionCommandIndex extends FileBasedIndexExtension<CommandKey, Void> implements PsiDependentIndex {

    @NonNls
    public static final ID<CommandKey, Void> NAME = ID.create("ActionCommandIndex");

    private final ActionCommandIndexer actionCommandIndexer = new ActionCommandIndexer();

    @NotNull
    @Override
    public ID<CommandKey, Void> getName() {
        return NAME;
    }

    @NotNull
    @Override
    public DataIndexer<CommandKey, Void, FileContent> getIndexer() {
        return actionCommandIndexer;
    }

    @NotNull
    @Override
    public KeyDescriptor<CommandKey> getKeyDescriptor() {
        return new KeyDescriptor<CommandKey>() {
            @Override
            public int getHashCode(CommandKey value) {
                return value.hashCode();
            }

            @Override
            public boolean isEqual(CommandKey val1, CommandKey val2) {
                return val1.equals(val2);
            }

            @Override
            public void save(@NotNull DataOutput out, CommandKey value) throws IOException {
                EnumeratorStringDescriptor.INSTANCE.save(out, value.getPortletName());
                EnumeratorStringDescriptor.INSTANCE.save(out, value.getCommandName());
            }

            @Override
            public CommandKey read(@NotNull DataInput in) throws IOException {
                return new CommandKey(EnumeratorStringDescriptor.INSTANCE.read(in), EnumeratorStringDescriptor.INSTANCE.read(in));
            }
        };
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

    public static List<String> getActionCommands(@NotNull String portletName, GlobalSearchScope scope) {
        return ReadAction.compute(
                () -> {
                    final List<String> result = new ArrayList<>();

                    try {
                        FileBasedIndex.getInstance().processAllKeys(
                                NAME,
                                commandKey -> {
                                    if (portletName.equals(commandKey.getPortletName())) {
                                        result.add(commandKey.getCommandName());
                                    }
                                    return true;
                                },
                                scope,
                                null
                        );

                    } catch (IndexNotReadyException e) {
                        //ignore
                    }

                    return result;
                }
        );
    }

    private class ActionCommandIndexer extends AbstractComponentPropertyIndexer<CommandKey> {

        @NotNull
        @Override
        protected String getServiceClassName() {
            return "com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand";
        }

        @Override
        protected void processProperties(@NotNull Map<CommandKey, Void> map, @NotNull Map<String, String> properties, @NotNull PsiClass psiClass) {
            String mvcCommandName = properties.get("mvc.command.name");
            String portletName = properties.get("javax.portlet.name");

            if ( (mvcCommandName != null) && (portletName != null) ) {
                map.put(new CommandKey(portletName, mvcCommandName), null);
            }
        }
    }
}
