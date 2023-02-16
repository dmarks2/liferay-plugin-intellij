// This is a generated file. Not intended for manual editing.
package de.dm.intellij.liferay.language.poshi.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static de.dm.intellij.liferay.language.poshi.psi.PoshiTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import de.dm.intellij.liferay.language.poshi.psi.*;

public class PoshiAnnotationImpl extends ASTWrapperPsiElement implements PoshiAnnotation {

  public PoshiAnnotationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PoshiVisitor visitor) {
    visitor.visitAnnotation(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PoshiVisitor) accept((PoshiVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PoshiStringQuotedDouble getStringQuotedDouble() {
    return findChildByClass(PoshiStringQuotedDouble.class);
  }

  @Override
  @NotNull
  public PsiElement getAnnotationName() {
    return findNotNullChildByType(ANNOTATION_NAME);
  }

  @Override
  @Nullable
  public PsiElement getNumericConstant() {
    return findChildByType(NUMERIC_CONSTANT);
  }

  @Override
  public String getName() {
    return PoshiPsiImplUtil.getName(this);
  }

  @Override
  public String getValue() {
    return PoshiPsiImplUtil.getValue(this);
  }

}
