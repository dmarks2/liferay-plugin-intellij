package de.dm.intellij.liferay.language.freemarker;

import com.intellij.freemarker.psi.FtlArgumentList;
import com.intellij.freemarker.psi.FtlElementTypes;
import com.intellij.freemarker.psi.FtlNameValuePair;
import com.intellij.freemarker.psi.FtlStringLiteral;
import com.intellij.freemarker.psi.directives.FtlMacro;
import com.intellij.lang.ASTNode;
import com.intellij.lang.injection.MultiHostInjector;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.lang.javascript.JSTargetedInjector;
import com.intellij.lang.javascript.JavascriptLanguage;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.SmartList;
import de.dm.intellij.liferay.language.AlloyUIScriptLanguageInjector;
import de.dm.intellij.liferay.util.LiferayTaglibs;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiferayFreemarkerJavascriptLanguageInjector implements MultiHostInjector, JSTargetedInjector {

    private static final Map<String, String> FTL_MACRO_PREFIXES = new HashMap<String, String>();

    static {
        FTL_MACRO_PREFIXES.put("liferay_aui", LiferayTaglibs.TAGLIB_URI_AUI);
        FTL_MACRO_PREFIXES.put("liferay_ui", LiferayTaglibs.TAGLIB_URI_LIFERAY_UI);
        FTL_MACRO_PREFIXES.put("liferay_frontend", LiferayTaglibs.TAGLIB_URI_LIFERAY_FRONTEND);
    }

    public void getLanguagesToInject(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement context) {
        if (! (context.isValid())) {
            return;
        }
        if (!(context instanceof FtlMacro)) {
            return;
        }

        FtlMacro ftlMacro = (FtlMacro) context;
        String directiveName = ftlMacro.getDirectiveName();
        if (directiveName.contains(".")) {
            String[] split = directiveName.split("\\.");
            if (split.length == 2) {
                String prefix = split[0];
                String name = split[1];

                if (FTL_MACRO_PREFIXES.containsKey(prefix)) {
                    String namespace = FTL_MACRO_PREFIXES.get(prefix);
                    if (AlloyUIScriptLanguageInjector.TAGLIB_ATTTRIBUTES.containsKey(namespace)) {
                        for (Pair<String, String> pair : AlloyUIScriptLanguageInjector.TAGLIB_ATTTRIBUTES.get(namespace)) {
                            if (pair.getKey().equals(name)) {
                                String attributeName = pair.getValue();
                                if ("".equals(attributeName)) {
                                    injectIntoBody(registrar, ftlMacro);
                                } else {
                                    FtlArgumentList argumentList = ftlMacro.getArgumentList();
                                    FtlNameValuePair[] namedArguments = argumentList.getNamedArguments();
                                    for (FtlNameValuePair ftlNameValuePair : namedArguments) {
                                        if (attributeName.equals(ftlNameValuePair.getName())) {
                                            PsiElement valueElement = ftlNameValuePair.getValueElement();
                                            injectIntoAttribute(registrar, valueElement);
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void injectIntoAttribute(@NotNull MultiHostRegistrar registrar, PsiElement valueElement) {
        if (valueElement instanceof FtlStringLiteral) {
            FtlStringLiteral ftlStringLiteral = (FtlStringLiteral)valueElement;
            List<TextRange> ranges = new SmartList<TextRange>();
            PsiElement[] children = ftlStringLiteral.getChildren();
            ranges.add(ftlStringLiteral.getValueRange());

            int startOffset = ftlStringLiteral.getTextRange().getStartOffset();

            for (PsiElement child : children) {
                TextRange textRange = child.getTextRange();
                TextRange rangeInElement = textRange.shiftLeft(startOffset);

                TextRange lastRange = ranges.remove(ranges.size() - 1);
                TextRange leftRange = new TextRange(lastRange.getStartOffset(), rangeInElement.getStartOffset());
                if (leftRange.getLength() > 0) {
                    ranges.add(leftRange);
                }

                TextRange rightRange = new TextRange(rangeInElement.getEndOffset(), lastRange.getEndOffset());
                if (rightRange.getLength() > 0) {
                    ranges.add(rightRange);
                }
            }

            if (! (ranges.isEmpty()) ) {
                registrar.startInjecting(JavascriptLanguage.INSTANCE);
                for (TextRange textRange : ranges) {
                    registrar.addPlace(null, null, ftlStringLiteral, textRange);
                }
                registrar.doneInjecting();
            }
        }
    }

    private void injectIntoBody(@NotNull MultiHostRegistrar registrar, FtlMacro ftlMacro) {
        TextRange injectionRange = ftlMacro.getInjectionRange();
        if (injectionRange != null) {
            if (! (injectionRange.isEmpty())) {
                registrar.startInjecting(JavascriptLanguage.INSTANCE);
                registrar.addPlace(null, null, (PsiLanguageInjectionHost) ftlMacro, injectionRange);
                registrar.doneInjecting();
            }
        } else {
            int startOffset = ftlMacro.getTextRange().getStartOffset();
            List<TextRange> ranges = new SmartList<TextRange>();

            ASTNode node = ftlMacro.getNode();
            ASTNode[] children = node.getChildren(TokenSet.create(FtlElementTypes.TEMPLATE_TEXT));
            for (ASTNode child : children) {
                TextRange textRange = child.getTextRange();
                TextRange rangeInElement = textRange.shiftLeft(startOffset);
                ranges.add(rangeInElement);
            }

            if (! (ranges.isEmpty()) ) {
                registrar.startInjecting(JavascriptLanguage.INSTANCE);
                for (TextRange textRange : ranges) {
                    registrar.addPlace(null, null, ftlMacro, textRange);
                }
                registrar.doneInjecting();
            }
        }
    }

    @NotNull
    public List<? extends Class<? extends PsiElement>> elementsToInjectIn() {
        return Arrays.asList(FtlMacro.class);
    }
}
