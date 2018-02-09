package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.patterns.PatternCondition;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ComponentPropertiesCompletionContributor extends CompletionContributor {

    //see https://dev.liferay.com/develop/reference/-/knowledge_base/7-0/portlet-descriptor-to-osgi-service-property-map
    private static final String[][] KEYWORDS = {
            {"javax.portlet.description", "String", ""},
            {"javax.portlet.name", "String", ""},
            {"javax.portlet.display-name", "String", ""},
            {"javax.portlet.init-param", "", ""},
            {"javax.portlet.expiration-cache", "int", ""},
            {"javax.portlet.mime-type", "String", ""},
            {"javax.portlet.portlet-mode", "String", ""},
            {"javax.portlet.window-state", "String", ""},
            {"javax.portlet.resource-bundle", "String", ""},
            {"javax.portlet.info.title", "String", ""},
            {"javax.portlet.info.short-title", "String", ""},
            {"javax.portlet.info.keywords", "String", ""},
            {"javax.portlet.preferences", "String", ""},
            {"javax.portlet.security-role-ref", "String", ""},
            {"javax.portlet.supported-processing-event", "String", ""},
            {"javax.portlet.supported-publishing-event", "String", ""},
            {"javax.portlet.supported-public-render-parameter", "String", ""},
            {"javax.portlet.supported-locale", "String", ""},
            {"com.liferay.portlet.icon", "String", ""},
            {"com.liferay.portlet.virtual-path", "String", ""},
            {"com.liferay.portlet.struts-path", "String", ""},
            {"com.liferay.portlet.parent-struts-path", "String", ""},
            {"com.liferay.portlet.configuration-path", "String", ""},
            {"com.liferay.portlet.friendly-url-mapping", "String", ""},
            {"com.liferay.portlet.friendly-url-routes", "String", ""},
            {"com.liferay.portlet.control-panel-entry-category", "String", ""},
            {"com.liferay.portlet.control-panel-entry-weight", "double", ""},
            {"com.liferay.portlet.preferences-company-wide", "boolean", ""},
            {"com.liferay.portlet.preferences-unique-per-layout", "boolean", ""},
            {"com.liferay.portlet.preferences-owned-by-group", "boolean", ""},
            {"com.liferay.portlet.use-default-template", "boolean", ""},
            {"com.liferay.portlet.show-portlet-access-denied", "boolean", ""},
            {"com.liferay.portlet.show-portlet-inactive", "boolean", ""},
            {"com.liferay.portlet.action-url-redirect", "boolean", ""},
            {"com.liferay.portlet.restore-current-view", "boolean", ""},
            {"com.liferay.portlet.maximize-edit", "boolean", ""},
            {"com.liferay.portlet.maximize-help", "boolean", ""},
            {"com.liferay.portlet.pop-up-print", "boolean", ""},
            {"com.liferay.portlet.layout-cacheable", "boolean", ""},
            {"com.liferay.portlet.instanceable", "boolean", ""},
            {"com.liferay.portlet.remoteable", "boolean", ""},
            {"com.liferay.portlet.scopeable", "boolean", ""},
            {"com.liferay.portlet.single-page-application", "boolean", ""},
            {"com.liferay.portlet.user-principal-strategy", "String", ""},
            {"com.liferay.portlet.private-request-attributes", "boolean", ""},
            {"com.liferay.portlet.private-session-attributes", "boolean", ""},
            {"com.liferay.portlet.autopropagated-parameters", "String", ""},
            {"com.liferay.portlet.requires-namespaced-parameters", "boolean", ""},
            {"com.liferay.portlet.action-timeout", "int", ""},
            {"com.liferay.portlet.render-timeout", "int", ""},
            {"com.liferay.portlet.render-weight", "int", ""},
            {"com.liferay.portlet.ajaxable", "boolean", ""},
            {"com.liferay.portlet.header-portal-css", "String", ""},
            {"com.liferay.portlet.header-portlet-css", "String", ""},
            {"com.liferay.portlet.header-portal-javascript", "String", ""},
            {"com.liferay.portlet.header-portlet-javascript", "String", ""},
            {"com.liferay.portlet.footer-portal-css", "String", ""},
            {"com.liferay.portlet.footer-portlet-css", "String", ""},
            {"com.liferay.portlet.footer-portal-javascript", "String", ""},
            {"com.liferay.portlet.footer-portlet-javascript", "String", ""},
            {"com.liferay.portlet.css-class-wrapper", "String", ""},
            {"com.liferay.portlet.facebook-integration", "String", ""},
            {"com.liferay.portlet.add-default-resource", "boolean", ""},
            {"com.liferay.portlet.system", "boolean", ""},
            {"com.liferay.portlet.active", "boolean", ""},
            {"com.liferay.portlet.display-category", "String", ""}
    };

    private List<LookupElementBuilder> KEYWORD_LOOKUPS = new ArrayList<LookupElementBuilder>();


    public ComponentPropertiesCompletionContributor() {
        for (String[] keyword : KEYWORDS) {
            KEYWORD_LOOKUPS.add(LookupElementBuilder.create(keyword[0]).withTypeText(keyword[1]).withTailText(keyword[2], true));
        };

        extend(
                CompletionType.BASIC,
                PlatformPatterns
                        .psiElement(PsiJavaToken.class)
                        .withLanguage(JavaLanguage.INSTANCE)
                        .withElementType(JavaTokenType.STRING_LITERAL)
                        .with(new PatternCondition<PsiJavaToken>("pattern") {
                            @Override
                            public boolean accepts(@NotNull PsiJavaToken psiJavaToken, ProcessingContext context) {
                                if (psiJavaToken.getParent() == null) {
                                    return false;
                                }
                                if (psiJavaToken.getParent() instanceof PsiLiteralExpression) {
                                    if (psiJavaToken.getParent().getParent() instanceof PsiArrayInitializerMemberValue) {
                                        if (psiJavaToken.getParent().getParent().getParent() instanceof PsiNameValuePair) {
                                            PsiNameValuePair nameValuePair = (PsiNameValuePair)psiJavaToken.getParent().getParent().getParent();
                                            if (nameValuePair.getNameIdentifier() != null) {
                                                if ("property".equals(nameValuePair.getNameIdentifier().getText())) {
                                                    if (psiJavaToken.getParent().getParent().getParent().getParent() instanceof PsiAnnotationParameterList) {
                                                        if (psiJavaToken.getParent().getParent().getParent().getParent().getParent() instanceof PsiAnnotation) {
                                                            PsiAnnotation annotation = (PsiAnnotation)psiJavaToken.getParent().getParent().getParent().getParent().getParent();
                                                            if (annotation.getNameReferenceElement() != null) {
                                                                if ("org.osgi.service.component.annotations.Component".equals(annotation.getNameReferenceElement().getQualifiedName())) {
                                                                    return true;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                return false;
                            }
                        })
                ,
                new CompletionProvider<CompletionParameters>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
                        result.addAllElements(KEYWORD_LOOKUPS);
                    }
                }

        );
    }
}
