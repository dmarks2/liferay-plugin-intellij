package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.XmlSuppressableInspectionTool;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.XmlElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlTag;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import de.dm.intellij.liferay.util.LiferayTaglibAttributes;
import de.dm.intellij.liferay.util.LiferayTaglibs;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class LiferayTaglibNestingInspection extends XmlSuppressableInspectionTool {

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "Liferay taglib nesting inspection";
    }

    @Override
    public String getStaticDescription() {
        return "Check for valid nesting of Liferay taglibs.";
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
            LiferayInspectionsGroupNames.JSP_GROUP_NAME
        };
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new XmlElementVisitor() {
            @Override
            public void visitXmlTag(XmlTag tag) {
                String namespace = tag.getNamespace();
                String localName = tag.getLocalName();

                if (LiferayTaglibAttributes.TAGLIB_SUGGESTED_PARENTS.containsKey(namespace)) {
                    Collection<AbstractMap.SimpleImmutableEntry<String, String>> entries = LiferayTaglibAttributes.TAGLIB_SUGGESTED_PARENTS.get(namespace);

                    List<AbstractMap.SimpleImmutableEntry<String, String>> suggestedEntries = entries.stream().filter(e -> e.getKey().equals(localName)).collect(Collectors.toList());

                    if (! suggestedEntries.isEmpty()) {
                        Set<String> suggestedNames = new TreeSet<>();

                        boolean nestingValid = false;

                        if (LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI.equals(namespace)) {
                            if ("input".equals(localName)) {
                                if (tag.getAttributeValue("formName") != null) {
                                    nestingValid = true;
                                }
                            }
                        } else if (LiferayTaglibs.TAGLIB_URI_LIFERAY_UI.equals(namespace)) {
                            if ("panel".equals(localName)) {
                                if (tag.getAttributeValue("parentId") != null) {
                                    nestingValid = true;
                                }
                            }
                        }

                        if (!nestingValid) {

                            PsiElement suggestedParentElement = null;

                            for (AbstractMap.SimpleImmutableEntry<String, String> entry : suggestedEntries) {
                                String suggestedParentNamespace = namespace;
                                String suggestedParentLocalName = entry.getValue();

                                if (suggestedParentLocalName.contains("|")) {
                                    List<String> values = StringUtil.split(suggestedParentLocalName, "|");

                                    suggestedParentNamespace = values.get(0);
                                    suggestedParentLocalName = values.get(1);
                                }

                                suggestedNames.add(suggestedParentLocalName);

                                String finalSuggestedParentNamespace = suggestedParentNamespace;
                                String finalSuggestedParentLocalName = suggestedParentLocalName;

                                suggestedParentElement = PsiTreeUtil.findFirstParent(tag, true, t -> {
                                    if (t instanceof XmlTag) {
                                        XmlTag parentTag = (XmlTag) t;

                                        String parentTagNamespace = parentTag.getNamespace();
                                        String parentTagLocalName = parentTag.getLocalName();

                                        if (parentTagNamespace.equals(finalSuggestedParentNamespace)) {
                                            if (parentTagLocalName.equals(finalSuggestedParentLocalName)) {
                                                return true;
                                            }
                                        }
                                    }

                                    return false;
                                });

                                if (suggestedParentElement != null) {
                                    break;
                                }
                            }

                            if (suggestedParentElement != null) {
                                nestingValid = true;
                            }
                        }

                        if (!nestingValid) {
                            StringJoiner stringJoiner = new StringJoiner(" or ", "", "");

                            for (String suggestedName : suggestedNames) {
                                stringJoiner.add(suggestedName);
                            }

                            holder.registerProblem(tag,
                                tag.getName() + " should be inside a " + stringJoiner.toString() + " element",
                                ProblemHighlightType.GENERIC_ERROR_OR_WARNING
                                /*,
                                new WrapInTagFix(
                                    new SafeTagInfo(namespace, suggestedParentLocalName, null, true)
                                )*/
                            );
                        }
                    }

                }

            }

        };
    }

}
