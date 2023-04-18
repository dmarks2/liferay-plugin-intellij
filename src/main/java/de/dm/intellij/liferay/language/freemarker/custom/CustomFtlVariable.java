package de.dm.intellij.liferay.language.freemarker.custom;

import com.intellij.freemarker.psi.FtlType;
import com.intellij.freemarker.psi.FtlVariantsProcessor;
import com.intellij.freemarker.psi.variables.FtlLightVariable;
import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiSubstitutor;
import com.intellij.psi.PsiTypes;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.NameHint;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PropertyUtil;
import de.dm.intellij.liferay.language.freemarker.LiferayFreemarkerUtil;
import de.dm.intellij.liferay.util.Icons;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CustomFtlVariable extends FtlLightVariable {

    private PsiElement navigationElement;
    private String typeText;

    public CustomFtlVariable(@NotNull String name, @NotNull PsiElement parent, @NotNull String typeText) {
        super(name, parent, typeText);

        this.typeText = typeText;

        findNavigationalElement();
    }

    public CustomFtlVariable(@NotNull String name, @NotNull PsiElement parent, @Nullable FtlType type) {
        super(name, parent, type);
    }

    public CustomFtlVariable(@NotNull String name, @NotNull PsiElement parent, @NotNull String typeText, PsiElement navigationElement) {
        super(name, parent, typeText);
        this.navigationElement = navigationElement;
    }

    @NotNull
    @Override
    public PsiElement getNavigationElement() {
        if (navigationElement != null) {
            return navigationElement;
        }
        return super.getNavigationElement();
    }

    @Override
    public Icon getIcon(boolean open) {
        return Icons.LIFERAY_ICON;
    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {
        FtlType ftlType = getType();

        PsiClassType classType = LiferayFreemarkerUtil.getClassType(ftlType);
        if (classType != null) {
            String className = classType.getCanonicalText();

            if ("com.liferay.portal.kernel.templateparser.TemplateNode".equals(className)) {
                return processTemplateNode(processor, state, place, classType);
            }
        }

        return super.processDeclarations(processor, state, lastParent, place);
    }

    private boolean processTemplateNode(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, @NotNull PsiElement place, PsiClassType classType) {
        //default implementation of FtlPsiType does not resolve members for classes which inherit from java.util.Map (TemplateNode inherits from Map)
        //so resolve members of com.liferay.portal.kernel.templateparser.TemplateNode manually

        PsiClassType.ClassResolveResult resolveResult = classType.resolveGenerics();
        PsiClass psiClass = resolveResult.getElement();
        if (psiClass == null) {
            return true;
        }

        ResolveState newState = state.put(PsiSubstitutor.KEY, resolveResult.getSubstitutor());
        String hint = processor instanceof FtlVariantsProcessor ? ((FtlVariantsProcessor) processor).getReferenceName() : null;
        if (!psiClass.processDeclarations(new PsiMemberProcessor(processor, hint), newState, (PsiElement) null, place)) {
            return false;
        }

        if (hint != null && !((FtlVariantsProcessor) processor).isMethodCall()) {
            String isAccessor = PropertyUtil.suggestGetterName(hint, PsiTypes.booleanType());
            if (!psiClass.processDeclarations(new PsiMemberProcessor(processor, isAccessor), newState, (PsiElement) null, place)) {
                return false;
            }

            String getAccessor = PropertyUtil.suggestGetterName(hint, PsiTypes.intType());
            if (!psiClass.processDeclarations(new PsiMemberProcessor(processor, getAccessor), newState, (PsiElement) null, place)) {
                return false;
            }
        }

        return false;
    }

    private void findNavigationalElement() {
        if (typeText != null) {
            if (! (typeText.startsWith("java"))) {
                this.navigationElement = ProjectUtils.getClassWithoutResolve(
                        typeText,
                        getProject(),
                        GlobalSearchScope.allScope(getProject())
                );
            }
        }
    }


    private class PsiMemberProcessor implements NameHint, PsiScopeProcessor {
        private final PsiScopeProcessor myDelegate;
        private final String myNameHint;

        public PsiMemberProcessor(PsiScopeProcessor delegate, String hint) {
            this.myDelegate = delegate;
            this.myNameHint = hint;
        }

        public boolean execute(@NotNull PsiElement element, @NotNull ResolveState state) {
            return this.myDelegate.execute(element, state);
        }

        @SuppressWarnings("unchecked")
        public <T> T getHint(@NotNull Key<T> hintKey) {
            return hintKey == NameHint.KEY && this.myNameHint != null ? (T) this : null;
        }

        @Nullable
        public String getName(@NotNull ResolveState state) {
            return this.myNameHint;
        }

    }
}