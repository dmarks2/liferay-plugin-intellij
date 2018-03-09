package de.dm.intellij.liferay.language.freemarker;

import com.intellij.freemarker.psi.FtlType;
import com.intellij.freemarker.psi.FtlVariantsProcessor;
import com.intellij.freemarker.psi.variables.FtlLightVariable;
import com.intellij.freemarker.psi.variables.FtlPsiType;
import com.intellij.freemarker.psi.variables.FtlVariable;
import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiSubstitutor;
import com.intellij.psi.PsiType;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.BaseScopeProcessor;
import com.intellij.psi.scope.NameHint;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.util.PropertyUtil;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;

public class CustomFtlVariable extends FtlLightVariable {

    private PsiElement navigationElement;
    private Collection<FtlVariable> nestedVariables;

    public CustomFtlVariable(@NotNull String name, @NotNull PsiElement parent, @Nullable FtlType type) {
        super(name, parent, type);
    }

    public CustomFtlVariable(@NotNull String name, @NotNull PsiElement parent, @Nullable FtlType type, PsiElement navigationElement) {
        super(name, parent, type);
        this.navigationElement = navigationElement;
    }

    public CustomFtlVariable(@NotNull String name, @NotNull PsiElement parent, @NotNull String typeText, PsiElement navigationElement) {
        this(name, parent, typeText, navigationElement, null);
    }

    public CustomFtlVariable(@NotNull String name, @NotNull PsiElement parent, @NotNull String typeText, PsiElement navigationElement, Collection<FtlVariable> nestedVariables) {
        super(name, parent, typeText);
        this.navigationElement = navigationElement;
        this.nestedVariables = nestedVariables;
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
        FtlPsiType ftlPsiType = (ftlType instanceof FtlPsiType ? (FtlPsiType)ftlType : null);
        PsiClassType classType = null;
        String className = null;
        if ( (ftlPsiType != null) && (ftlPsiType.getPsiType() instanceof PsiClassType) ) {
            classType = (PsiClassType)ftlPsiType.getPsiType();
            className = classType.getCanonicalText();
        }

        if (nestedVariables != null) {
            for (FtlVariable variable : nestedVariables) {
                processor.execute(variable, state);
            }
            return false;
        } else if ("com.liferay.portal.kernel.templateparser.TemplateNode".equals(className)) {
            //default implementation of FtlPsiType does not resolve members for classes which inherit from java.util.Map (TemplateNode inherits from Map)
            //so resolve members of com.liferay.portal.kernel.templateparser.TemplateNode manually

            if (classType != null) {
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
                    String isAccessor = PropertyUtil.suggestGetterName(hint, PsiType.BOOLEAN);
                    if (!psiClass.processDeclarations(new PsiMemberProcessor(processor, isAccessor), newState, (PsiElement) null, place)) {
                        return false;
                    }

                    String getAccessor = PropertyUtil.suggestGetterName(hint, PsiType.INT);
                    if (!psiClass.processDeclarations(new PsiMemberProcessor(processor, getAccessor), newState, (PsiElement) null, place)) {
                        return false;
                    }
                }
            }
        }

        return super.processDeclarations(processor, state, lastParent, place);
    }


    private class PsiMemberProcessor extends BaseScopeProcessor implements NameHint {
        private final PsiScopeProcessor myDelegate;
        private final String myNameHint;

        public PsiMemberProcessor(PsiScopeProcessor delegate, String hint) {
            this.myDelegate = delegate;
            this.myNameHint = hint;
        }

        public boolean execute(@NotNull PsiElement element, @NotNull ResolveState state) {
            return this.myDelegate.execute(element, state);
        }

        public <T> T getHint(@NotNull Key<T> hintKey) {
            return hintKey == NameHint.KEY && this.myNameHint != null ? (T) this : null;
        }

        @Nullable
        public String getName(@NotNull ResolveState state) {
            return this.myNameHint;
        }

    }
}