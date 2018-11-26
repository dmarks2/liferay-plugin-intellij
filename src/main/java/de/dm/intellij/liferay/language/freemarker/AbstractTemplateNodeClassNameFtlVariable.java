package de.dm.intellij.liferay.language.freemarker;

import com.intellij.freemarker.psi.FtlIndexExpression;
import com.intellij.freemarker.psi.FtlQualifiedReference;
import com.intellij.freemarker.psi.files.FtlFile;
import com.intellij.freemarker.psi.variables.FtlLightVariable;
import com.intellij.freemarker.psi.variables.FtlPsiType;
import com.intellij.freemarker.psi.variables.FtlSpecialVariableType;
import com.intellij.freemarker.psi.variables.FtlVariable;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiType;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractTemplateNodeClassNameFtlVariable extends FtlLightVariable {

    public AbstractTemplateNodeClassNameFtlVariable(@NotNull String name, @NotNull PsiElement parent) {
        super(name, parent, getVariableType(parent));
    }

    private static FtlSpecialVariableType getVariableType(final PsiElement parent) {
        return new FtlSpecialVariableType() {
            public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull PsiElement place, ResolveState state) {
                if (place instanceof FtlIndexExpression) {
                    FtlIndexExpression ftlIndexExpression = (FtlIndexExpression)place;

                    FtlQualifiedReference qualifiedReference = ftlIndexExpression.getQualifiedReference();

                    if (qualifiedReference != null) {
                        String referenceName = qualifiedReference.getReferenceName();
                        if (referenceName != null) {
                            try {
                                final PsiType targetType = JavaPsiFacade.getInstance(parent.getProject()).getElementFactory().createTypeFromText(referenceName, parent);

                                FtlVariable variable = new CustomFtlVariable(referenceName, place, FtlPsiType.wrap(targetType));
                                processor.execute(variable, state);
                            } catch (IncorrectOperationException e) {
                                //unable to create type from text
                            }

                        }
                    }
                }

                return true;
            }
        };
    }


}
