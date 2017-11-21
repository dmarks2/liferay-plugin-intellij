package de.dm.intellij.bndtools;

import com.intellij.ide.highlighter.custom.SyntaxTable;
import com.intellij.openapi.fileTypes.impl.AbstractFileType;
import com.intellij.openapi.fileTypes.impl.CustomSyntaxTableFileType;
import com.intellij.openapi.vfs.VirtualFile;
import de.dm.intellij.liferay.util.Icons;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;

import static com.intellij.openapi.fileTypes.impl.AbstractFileType.ELEMENT_HIGHLIGHTING;

public class BndFileType implements CustomSyntaxTableFileType {

    public static final BndFileType INSTANCE = new BndFileType();

    private SyntaxTable syntaxTable = null;

    public BndFileType() {
        InputStream inputStream = null;

        try{
            inputStream = BndFileType.class.getResourceAsStream("/org/bndtools/bnd/bnd.xml");
            if (inputStream != null) {
                SAXBuilder saxBuilder = new SAXBuilder();
                Document document = saxBuilder.build(inputStream);
                Element root = document.getRootElement();

                Element highlighting = root.getChild(ELEMENT_HIGHLIGHTING);
                if (highlighting != null) {
                    syntaxTable = AbstractFileType.readSyntaxTable(highlighting);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @NotNull
    public String getName() {
        return "BND file";
    }

    @NotNull
    public String getDescription() {
        return "BND file";
    }

    @NotNull
    public String getDefaultExtension() {
        return "bnd";
    }

    @Nullable
    public Icon getIcon() {
        return Icons.BND_ICON;
    }

    public boolean isBinary() {
        return false;
    }

    public boolean isReadOnly() {
        return false;
    }

    @Nullable
    public String getCharset(@NotNull VirtualFile file, @NotNull byte[] content) {
        return null;
    }

    public SyntaxTable getSyntaxTable() {
        return syntaxTable;
    }
}
