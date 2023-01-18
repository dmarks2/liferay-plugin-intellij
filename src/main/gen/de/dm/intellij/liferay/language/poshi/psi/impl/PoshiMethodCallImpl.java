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
import com.intellij.psi.PsiReference;

public class PoshiMethodCallImpl extends ASTWrapperPsiElement implements PoshiMethodCall {

  public PoshiMethodCallImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PoshiVisitor visitor) {
    visitor.visitMethodCall(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PoshiVisitor) accept((PoshiVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  public PsiReference @NotNull [] getReferences() {
    return PoshiPsiImplUtil.getReferences(this);
  }

}
