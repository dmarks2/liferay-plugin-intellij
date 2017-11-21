package de.dm.intellij.liferay.language.tpl;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import com.intellij.velocity.psi.files.VtlFileType;
import org.jetbrains.annotations.NotNull;

/**
 * Declare that .tpl files should be handled as Velocity files
 */
public class TplFileTypeFactory extends FileTypeFactory {

    @Override
    public void createFileTypes(@NotNull FileTypeConsumer consumer) {
        consumer.consume(VtlFileType.INSTANCE, "tpl");
    }
}
