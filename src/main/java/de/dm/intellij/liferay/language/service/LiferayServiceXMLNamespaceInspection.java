package de.dm.intellij.liferay.language.service;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.XmlSuppressableInspectionTool;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.XmlElementVisitor;
import com.intellij.psi.xml.XmlTagChild;
import com.intellij.psi.xml.XmlText;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class LiferayServiceXMLNamespaceInspection extends XmlSuppressableInspectionTool {

    private static final Pattern VALID_NAMESPACE_EXPRESSION = Pattern.compile("[A-Za-z_]{1}[A-Za-z0-9_]{0,62}");

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "check for valid namespace expression";
    }

    @Nls
    @NotNull
    @Override
    public String getGroupDisplayName() {
        return LiferayInspectionsGroupNames.LIFERAY_GROUP_NAME;
    }

    @NotNull
    @Override
    public String[] getGroupPath() {
        return new String[]{
                getGroupDisplayName(),
                LiferayInspectionsGroupNames.SERVICE_XML_GROUP_NAME
        };
    }

    @Nullable
    @Override
    public String getStaticDescription() {
        return "Namespace must start with a letter or underscore followed by letters, numbers or underscores and may not be longer that 63 chars.";
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new XmlElementVisitor() {
            @Override
            public void visitXmlText(XmlText xmlText) {
                boolean isNamespaceTag =
                        Stream.of(
                            xmlText
                        ).map(
                            XmlTagChild::getParentTag
                        ).filter(
                            Objects::nonNull
                        ).filter(
                            parentTag -> "namespace".equals(parentTag.getLocalName())
                        ).map(
                            XmlTagChild::getParentTag
                        ).filter(
                            Objects::nonNull
                        ).anyMatch(
                            grandParentTag -> "service-builder".equals(grandParentTag.getLocalName())
                        );

                if (isNamespaceTag) {
                    String text = xmlText.getText();
                    if (text != null) {
                        Matcher matcher = VALID_NAMESPACE_EXPRESSION.matcher(text);
                        if (!matcher.matches()) {
                            holder.registerProblem(xmlText,
                                "Namespace is not valid",
                                ProblemHighlightType.GENERIC_ERROR_OR_WARNING
                            );
                        }
                    }
                }
            }
        };
    }
}
