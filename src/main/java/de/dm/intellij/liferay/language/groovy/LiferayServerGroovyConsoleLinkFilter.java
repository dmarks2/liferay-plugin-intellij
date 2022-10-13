package de.dm.intellij.liferay.language.groovy;

import com.intellij.execution.filters.Filter;
import com.intellij.execution.filters.HyperlinkInfo;
import com.intellij.execution.filters.OpenFileHyperlinkInfo;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LiferayServerGroovyConsoleLinkFilter implements Filter {

    //sample message:
    //  at Script1.run(Script1.groovy:28)

    private static final Pattern PATTERN = Pattern.compile("at Script1.run\\((Script1.groovy:(\\d{1,5}))\\)");

    private final Project project;
    private final String fileName;

    public LiferayServerGroovyConsoleLinkFilter(Project project, String fileName) {
        this.project = project;
        this.fileName = fileName;
    }

    @Override
    public @Nullable Result applyFilter(@NotNull String line, int entireLength) {
        List<ResultItem> results = new ArrayList<>();

        Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            int lineNumber = Integer.parseInt(matcher.group(2));

            File file = new File(fileName);

            VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);

            if (virtualFile != null) {
                HyperlinkInfo hyperlinkInfo = new OpenFileHyperlinkInfo(project, virtualFile, (lineNumber - 1));

                int startPoint = entireLength - line.length();

                results.add(new ResultItem(
                        startPoint + matcher.start(1),
                        startPoint + matcher.end(1),
                        hyperlinkInfo
                ));
            }
        }

        return new Result(results);
    }
}
