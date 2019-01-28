package de.dm.intellij.liferay.index;

import com.intellij.ide.highlighter.JavaClassFileType;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.project.IndexNotReadyException;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiClass;
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
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * File Based Index for finding the portlet name for JSPs
 */
public class PortletJspIndex extends FileBasedIndexExtension<JspKey, Void> implements PsiDependentIndex {

    @NonNls
    public static final ID<JspKey, Void> NAME = ID.create("PortletJspIndex");

    private static final String PORTLET_NAME_PROPERTY = "javax.portlet.name";

    private static final String[] PORTLET_JSP_INIT_PARAMS = new String[] {
        "javax.portlet.init-param.about-template",
        "javax.portlet.init-param.config-template",
        "javax.portlet.init-param.edit-template",
        "javax.portlet.init-param.edit-defaults-template",
        "javax.portlet.init-param.edit-guest-template",
        "javax.portlet.init-param.help-template",
        "javax.portlet.init-param.preview-template",
        "javax.portlet.init-param.print-template",
        "javax.portlet.init-param.view-template",
    };

    private final PortletJspIndexer portletJspIndexer = new PortletJspIndexer();

    @NotNull
    @Override
    public ID<JspKey, Void> getName() {
        return NAME;
    }

    @NotNull
    @Override
    public DataIndexer<JspKey, Void, FileContent> getIndexer() {
        return portletJspIndexer;
    }

    @NotNull
    @Override
    public KeyDescriptor<JspKey> getKeyDescriptor() {
        return new KeyDescriptor<JspKey>() {
            @Override
            public int getHashCode(JspKey value) {
                return value.hashCode();
            }

            @Override
            public boolean isEqual(JspKey val1, JspKey val2) {
                return val1.equals(val2);
            }

            @Override
            public void save(@NotNull DataOutput out, JspKey value) throws IOException {
                EnumeratorStringDescriptor.INSTANCE.save(out, value.getPortletName());
                EnumeratorStringDescriptor.INSTANCE.save(out, value.getJspPath());
            }

            @Override
            public JspKey read(@NotNull DataInput in) throws IOException {
                return new JspKey(EnumeratorStringDescriptor.INSTANCE.read(in), EnumeratorStringDescriptor.INSTANCE.read(in));
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

    public static List<String> getPortletNames(@NotNull String jspPath, GlobalSearchScope scope) {
        return ReadAction.compute(
            () -> {
                final List<String> result = new ArrayList<>();

                try {
                    FileBasedIndex.getInstance().processAllKeys(
                        NAME,
                        jspKey -> {
                            if (jspPath.equals(jspKey.getJspPath())) {
                                result.add(jspKey.getPortletName());
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


    private class PortletJspIndexer extends AbstractComponentPropertyIndexer<JspKey> {

        @NotNull
        @Override
        protected String getServiceClassName() {
            return "javax.portlet.Portlet";
        }

        @Override
        protected void processProperties(@NotNull Map<JspKey, Void> map, @NotNull Map<String, Collection<String>> properties, @NotNull PsiClass psiClass) {
            Collection<String> portletNames = properties.get(PORTLET_NAME_PROPERTY);

            if (portletNames == null) {
                portletNames = Collections.singletonList(psiClass.getQualifiedName());
            }

            for (String jspInitParam : PORTLET_JSP_INIT_PARAMS) {
                Collection<String> initParamValues = properties.get(jspInitParam);
                if (initParamValues != null) {
                    for (String initParamValue : initParamValues) {
                        for (String portletName : portletNames) {
                            //from PortletTracker.addingService()
                            String portletId = StringUtil.replace(portletName, Arrays.asList(".", "$"), Arrays.asList("_", "_"));
                            portletId = LiferayFileUtil.getJSSafeName(portletId);

                            map.put(new JspKey(portletId, initParamValue), null);
                        }
                    }
                }
            }
        }

    }
}
