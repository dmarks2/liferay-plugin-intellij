package de.dm.intellij.liferay.language.osgi.config;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.util.io.ByteSequence;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

public class LiferayConfigFileTypeDetector implements FileTypeRegistry.FileTypeDetector {

	@Override
	public @Nullable FileType detect(@NotNull VirtualFile virtualFile, @NotNull ByteSequence byteSequence, @Nullable CharSequence charSequence) {
		if (!Objects.equals("config", virtualFile.getExtension())) {
			return null;
		}

		if (isOsgiConfigsDirectory(virtualFile)) {
			return LiferayConfigFileType.INSTANCE;
		}

		if (isInLiferayConfigurationPath(virtualFile)) {
			return LiferayConfigFileType.INSTANCE;
		}

		return null;
	}

	private boolean isOsgiConfigsDirectory(@NotNull VirtualFile virtualFile) {
		VirtualFile parent = virtualFile.getParent();

		if (parent != null && Objects.equals("configs", parent.getName())) {
			VirtualFile grandParent = parent.getParent();

			return grandParent != null && Objects.equals("osgi", grandParent.getName());
		}

		return false;
	}

	private boolean isInLiferayConfigurationPath(@NotNull VirtualFile file) {
		VirtualFile current = file.getParent();
		VirtualFile bndFile = null;

		while (current != null) {
			VirtualFile potential = current.findChild("bnd.bnd");

			if (potential != null) {
				bndFile = potential;
				break;
			}

			current = current.getParent();
		}

		if (bndFile == null) {
			return false;
		}

		try {
			String content = new String(bndFile.contentsToByteArray(), StandardCharsets.UTF_8);

			Properties props = new Properties();

			props.load(new StringReader(content));

			String configPath = props.getProperty("Liferay-Configuration-Path");

			if (configPath == null) {
				return false;
			}

			if (StringUtil.startsWith(configPath, "/")) {
				configPath = configPath.substring(1);
			}

			Path bndParent = Paths.get(bndFile.getParent().getPath());

			Path configDir = bndParent.resolve(configPath);

			Path resourcesConfigDir = bndParent.resolve(Paths.get("src", "main", "resources")).resolve(configPath);

			Path filePath = Paths.get(file.getParent().getPath());

			return filePath.startsWith(configDir) || filePath.startsWith(resourcesConfigDir);
		} catch (IOException ioException) {
			return false;
		}
	}
}
