package de.dm.intellij.bndtools.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import de.dm.intellij.bndtools.BndLanguage;
import de.dm.intellij.bndtools.parser.BndHeaderParsers;
import de.dm.intellij.bndtools.parser.OsgiConstants;
import de.dm.intellij.bndtools.psi.BndHeader;
import de.dm.intellij.bndtools.psi.BndTokenType;
import de.dm.intellij.bndtools.psi.Directive;
import org.jetbrains.annotations.NotNull;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class BndCompletionContributor extends CompletionContributor {

    // List of known Liferay analyzers:
    // * https://github.com/liferay/liferay-portal/blob/master/modules/sdk/ant-bnd/src/main/java/com/liferay/ant/bnd/enterprise/EnterpriseAnalyzerPlugin.java
    // * https://github.com/liferay/liferay-portal/blob/master/modules/sdk/ant-bnd/src/main/java/com/liferay/ant/bnd/jsp/JspAnalyzerPlugin.java
    // * https://github.com/liferay/liferay-portal/blob/master/modules/sdk/ant-bnd/src/main/java/com/liferay/ant/bnd/metatype/MetatypePlugin.java
    // * https://github.com/liferay/liferay-portal/blob/master/modules/sdk/ant-bnd/src/main/java/com/liferay/ant/bnd/npm/NpmAnalyzerPlugin.java
    // * https://github.com/liferay/liferay-portal/blob/master/modules/sdk/ant-bnd/src/main/java/com/liferay/ant/bnd/resource/AddResourceVerifierPlugin.java
    // * https://github.com/liferay/liferay-portal/blob/master/modules/sdk/ant-bnd/src/main/java/com/liferay/ant/bnd/resource/bundle/AggregateResourceBundleLoaderAnalyzerPlugin.java
    // * https://github.com/liferay/liferay-portal/blob/master/modules/sdk/ant-bnd/src/main/java/com/liferay/ant/bnd/resource/bundle/ProvidesResourceBundleLoaderAnalyzerPlugin.java
    // * https://github.com/liferay/liferay-portal/blob/master/modules/sdk/ant-bnd/src/main/java/com/liferay/ant/bnd/resource/bundle/ResourceBundleLoaderAnalyzerPlugin.java
    // * https://github.com/liferay/liferay-portal/blob/master/modules/sdk/ant-bnd/src/main/java/com/liferay/ant/bnd/sass/SassAnalyzerPlugin.java
    // * https://github.com/liferay/liferay-portal/blob/master/modules/sdk/ant-bnd/src/main/java/com/liferay/ant/bnd/service/ServiceAnalyzerPlugin.java
    // * https://github.com/liferay/liferay-portal/blob/master/modules/sdk/ant-bnd/src/main/java/com/liferay/ant/bnd/social/SocialAnalyzerPlugin.java
    // * https://github.com/liferay/liferay-portal/blob/master/modules/sdk/ant-bnd/src/main/java/com/liferay/ant/bnd/spring/SpringDependencyAnalyzerPlugin.java

    public BndCompletionContributor() {
        extend(
            CompletionType.BASIC, _header(OsgiConstants.EXPORT_PACKAGE),
            new HeaderParametersProvider(OsgiConstants.VERSION_ATTRIBUTE, OsgiConstants.USES_DIRECTIVE + ':'));

        extend(
            CompletionType.BASIC, _header(OsgiConstants.IMPORT_PACKAGE),
            new HeaderParametersProvider(OsgiConstants.VERSION_ATTRIBUTE, OsgiConstants.RESOLUTION_DIRECTIVE + ':'));

        extend(
            CompletionType.BASIC, _directive(OsgiConstants.RESOLUTION_DIRECTIVE),
            new SimpleProvider(OsgiConstants.RESOLUTION_MANDATORY, OsgiConstants.RESOLUTION_OPTIONAL));

        extend(CompletionType.BASIC,
            PlatformPatterns.psiElement(BndTokenType.HEADER_NAME).withLanguage(BndLanguage.getInstance()),
                new CompletionProvider<>() {
                    @Override
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               @NotNull ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        for (String header : BndHeaderParsers.PARSERS_MAP.keySet()) {
                            LookupElementBuilder lookupElementBuilder = LookupElementBuilder.create(header).withInsertHandler(HEADER_INSERT_HANDLER);

                            if (BndTypeConstants.BND_TYPES.containsKey(header)) {
                                lookupElementBuilder = lookupElementBuilder.withTypeText(BndTypeConstants.BND_TYPES.get(header));
                            }

                            resultSet.addElement(lookupElementBuilder);
                        }
                    }
                }
        );

    }

    private static ElementPattern<PsiElement> _directive(String name) {
        PsiElementPattern.Capture<PsiElement> element = psiElement(BndTokenType.HEADER_VALUE_PART);

        PsiElementPattern.Capture<Directive> directiveElement = psiElement(Directive.class);

        return element.withSuperParent(2, directiveElement.withName(name));
    }

    private static ElementPattern<PsiElement> _header(String name) {
        PsiElementPattern.Capture<PsiElement> psiElementCapture = psiElement(BndTokenType.HEADER_VALUE_PART);

        psiElementCapture.afterLeaf(";");

        PsiElementPattern.Capture<BndHeader> headerElement = psiElement(BndHeader.class);

        return psiElementCapture.withSuperParent(3, headerElement.withName(name));
    }

    private static final InsertHandler<LookupElement> HEADER_INSERT_HANDLER = (context, item) -> {
        context.setAddCompletionChar(false);
        EditorModificationUtil.insertStringAtCaret(context.getEditor(), ": ");
        context.commitDocument();
    };


}
