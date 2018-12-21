package de.dm.intellij.liferay.language.freemarker.staticutil;

import com.intellij.freemarker.psi.FtlIndexExpression;
import com.intellij.freemarker.psi.variables.FtlLightVariable;
import com.intellij.freemarker.psi.variables.FtlPsiType;
import com.intellij.freemarker.psi.variables.FtlSpecialVariableType;
import com.intellij.freemarker.psi.variables.FtlVariable;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiSubstitutor;
import com.intellij.psi.PsiType;
import com.intellij.psi.ResolveState;
import com.intellij.psi.impl.source.PsiImmediateClassType;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.util.IncorrectOperationException;
import de.dm.intellij.liferay.language.freemarker.LiferayFreemarkerUtil;
import de.dm.intellij.liferay.language.freemarker.custom.CustomFtlVariable;
import org.jetbrains.annotations.NotNull;

public class StaticUtilFtlVariable extends FtlLightVariable {

    public static final String VARIABLE_NAME = "staticUtil";

    public StaticUtilFtlVariable(@NotNull PsiElement parent) {
        super(VARIABLE_NAME, parent, getVariableType(parent));
    }

    private static FtlSpecialVariableType getVariableType(final PsiElement parent) {
        return new FtlSpecialVariableType() {
            public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull PsiElement place, ResolveState state) {
                if (place instanceof FtlIndexExpression) {
                    FtlIndexExpression ftlIndexExpression = (FtlIndexExpression)place;

                    String referenceName = LiferayFreemarkerUtil.getIndexExpressionQualifiedReferenceName(ftlIndexExpression);
                    if (referenceName != null) {
                        try {
                            final PsiType targetType = JavaPsiFacade.getInstance(parent.getProject()).getElementFactory().createTypeFromText(referenceName, parent);
                            if (targetType instanceof PsiClassType) {
                                PsiClassType psiClassType = (PsiClassType)targetType;
                                PsiClass psiClass = psiClassType.resolve();
                                if (psiClass != null) {
                                    PsiType immediateClassType = new PsiImmediateClassType(psiClass, PsiSubstitutor.EMPTY);
                                    FtlPsiType ftlPsiType = new FtlPsiType(immediateClassType, true);
                                    FtlVariable variable = new CustomFtlVariable(referenceName, place, ftlPsiType);

                                    processor.execute(variable, state);
                                }
                            }
                        } catch (IncorrectOperationException e) {
                            //unable to create type from text
                        }

                    }
                }

                return true;
            }
        };
    }
}
