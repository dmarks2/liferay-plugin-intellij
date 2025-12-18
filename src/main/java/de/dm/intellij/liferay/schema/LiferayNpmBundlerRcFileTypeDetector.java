package de.dm.intellij.liferay.schema;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.util.io.ByteSequence;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import de.dm.intellij.liferay.language.osgi.config.LiferayConfigFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

public class LiferayNpmBundlerRcFileTypeDetector implements FileTypeRegistry.FileTypeDetector {

	@Override
	public @Nullable FileType detect(@NotNull VirtualFile virtualFile, @NotNull ByteSequence byteSequence, @Nullable CharSequence charSequence) {
		if (Objects.equals(".npmbundlerrc", virtualFile.getName())) {
			return LiferayNpmBundlerRcFileType.INSTANCE;
		}

		return null;
	}
}
