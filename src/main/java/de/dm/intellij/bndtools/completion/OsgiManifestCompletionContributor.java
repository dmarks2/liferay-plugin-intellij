package de.dm.intellij.bndtools.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import de.dm.intellij.bndtools.psi.Directive;
import org.jetbrains.lang.manifest.psi.Header;
import org.jetbrains.lang.manifest.psi.ManifestTokenType;
import org.osgi.framework.Constants;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class OsgiManifestCompletionContributor extends CompletionContributor {

    public OsgiManifestCompletionContributor() {
        extend(
            CompletionType.BASIC, _header(Constants.EXPORT_PACKAGE),
            new HeaderParametersProvider(Constants.VERSION_ATTRIBUTE, Constants.USES_DIRECTIVE + ':'));

        extend(
            CompletionType.BASIC, _header(Constants.IMPORT_PACKAGE),
            new HeaderParametersProvider(Constants.VERSION_ATTRIBUTE, Constants.RESOLUTION_DIRECTIVE + ':'));

        extend(
            CompletionType.BASIC, _directive(Constants.RESOLUTION_DIRECTIVE),
            new SimpleProvider(Constants.RESOLUTION_MANDATORY, Constants.RESOLUTION_OPTIONAL));
    }

    private static ElementPattern<PsiElement> _directive(String name) {
        PsiElementPattern.Capture<PsiElement> element = psiElement(ManifestTokenType.HEADER_VALUE_PART);

        PsiElementPattern.Capture<Directive> directiveElement = psiElement(Directive.class);

        return element.withSuperParent(2, directiveElement.withName(name));
    }

    private static ElementPattern<PsiElement> _header(String name) {
        PsiElementPattern.Capture<PsiElement> psiElementCapture = psiElement(ManifestTokenType.HEADER_VALUE_PART);

        psiElementCapture.afterLeaf(";");

        PsiElementPattern.Capture<Header> headerElement = psiElement(Header.class);

        return psiElementCapture.withSuperParent(3, headerElement.withName(name));
    }

}