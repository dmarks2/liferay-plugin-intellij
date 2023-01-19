package de.dm.intellij.liferay.language.poshi.psi;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlTagValue;
import com.intellij.xml.util.XmlUtil;
import de.dm.intellij.liferay.language.poshi.constants.PoshiConstants;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PoshiPathLocatorReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {

    private final String pathName;
    private final String locatorName;

    public PoshiPathLocatorReference(@NotNull PsiElement element, String pathName, String locatorName, TextRange textRange) {
        super(element, textRange);

        this.pathName = pathName;
        this.locatorName = locatorName;
    }

    @Override
    public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
        Collection<PsiElement> results = new ArrayList<>();

        List<PsiFile> psiFiles = getPathFiles(getElement().getContainingFile().getOriginalFile());

        for (PsiFile psiFile : psiFiles) {
            if (FileUtil.getNameWithoutExtension(psiFile.getName()).equals(pathName)) {
                if (psiFile instanceof XmlFile) {
                    XmlFile xmlFile = (XmlFile) psiFile;

                    XmlTag rootTag = xmlFile.getRootTag();

                    if (rootTag != null) {
                        XmlUtil.processXmlElements(rootTag, element -> {
                            if (element instanceof XmlTag) {
                                XmlTag xmlTag = (XmlTag)element;

                                XmlTag parent = xmlTag.getParentTag();

                                if (parent != null) {
                                    XmlTag grandParent = parent.getParentTag();

                                    if (grandParent != null && "tbody".equals(grandParent.getName())) {
                                        XmlTag[] subTags = parent.getSubTags();

                                        List<XmlTag> tags = Arrays.stream(subTags).filter(tag -> "td".equals(tag.getName())).toList();

                                        //inject only in first <td>
                                        if (tags.indexOf(xmlTag) == 0) {
                                            XmlTagValue value = xmlTag.getValue();

                                            if (locatorName.equals(value.getText())) {
                                                results.add(xmlTag);
                                            }
                                        }
                                    }
                                }
                            }
                            return true;
                        }, true);
                    }
                }
            }
        }

        return PsiElementResolveResult.createResults(results);
    }

    @Override
    public @Nullable PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);

        if (resolveResults.length == 1) {
            return resolveResults[0].getElement();
        }

        return null;
    }

    @Override
    public Object @NotNull [] getVariants() {
        List<Object> result = new ArrayList<>();

        List<PsiFile> psiFiles = getPathFiles(getElement().getContainingFile().getOriginalFile());

        for (PsiFile psiFile : psiFiles) {
            if (FileUtil.getNameWithoutExtension(psiFile.getName()).equals(pathName)) {
                if (psiFile instanceof XmlFile) {
                    XmlFile xmlFile = (XmlFile) psiFile;

                    XmlTag rootTag = xmlFile.getRootTag();

                    if (rootTag != null) {
                        XmlUtil.processXmlElements(rootTag, element -> {
                            if (element instanceof XmlTag) {
                                XmlTag xmlTag = (XmlTag) element;

                                XmlTag parent = xmlTag.getParentTag();

                                if (parent != null) {
                                    XmlTag grandParent = parent.getParentTag();

                                    if (grandParent != null && "tbody".equals(grandParent.getName())) {
                                        XmlTag[] subTags = parent.getSubTags();

                                        List<XmlTag> tags = Arrays.stream(subTags).filter(tag -> "td".equals(tag.getName())).toList();

                                        //inject only in first <td>
                                        if (tags.indexOf(xmlTag) == 0) {
                                            XmlTagValue value = xmlTag.getValue();

                                            if (StringUtil.isNotEmpty(value.getText())) {
                                                result.add(LookupElementBuilder.create(value.getText()).withPsiElement(xmlTag).withIcon(Icons.LIFERAY_ICON));
                                            }
                                        }
                                    }
                                }
                            }
                            return true;
                        }, true);
                    }
                }
            }
        }

        return result.toArray(new Object[0]);
    }

    public static List<PsiFile> getPathFiles(@NotNull PsiFile testcaseFile) {
        List<PsiFile> result = new ArrayList<>();

        PsiDirectory parent = testcaseFile.getParent();

        if (parent != null) {
            parent = parent.getParent();

            if (parent != null) {
                PsiDirectory subdirectory = parent.findSubdirectory(PoshiConstants.PATHS_DIRECTORY);

                if (subdirectory != null) {
                    for (PsiFile psiFile : subdirectory.getFiles()) {
                        if (psiFile.getName().endsWith(PoshiConstants.PATH_EXTENSION)) {
                            result.add(psiFile);
                        }
                    }
                }
            }
        }

        return result;
    }


}