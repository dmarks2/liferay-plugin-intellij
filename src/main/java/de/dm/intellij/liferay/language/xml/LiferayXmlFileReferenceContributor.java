package de.dm.intellij.liferay.language.xml;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.SoftFileReferenceSet;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.java.JavaResourceRootType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class LiferayXmlFileReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(
                PlatformPatterns.psiElement(PsiElement.class).and(new LiferayXmlFileReferenceFilterPattern()),
                new PsiReferenceProvider() {

                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                        TextRange originalRange = element.getTextRange();
                        String valueString;
                        int startInElement;
                        if (element instanceof XmlAttributeValue) {
                            TextRange valueRange = TextRange.create(1, originalRange.getLength() - 1);
                            valueString = valueRange.substring(element.getText());
                            startInElement = 1;
                        } else {
                            TextRange valueRange = TextRange.create(0, originalRange.getLength());
                            valueString = valueRange.substring(element.getText());
                            startInElement = 0;
                        }

                        FileReferenceSet fileReferenceSet = new SoftFileReferenceSet(valueString, element, startInElement, null, SystemInfo.isFileSystemCaseSensitive, false);

                        fileReferenceSet.addCustomization(
                                FileReferenceSet.DEFAULT_PATH_EVALUATOR_OPTION,
                                LiferayXmlFileReferenceContributor::getModuleResourceDirectories
                        );

                        return fileReferenceSet.getAllReferences();
                    }
                }
        );
    }

    private static Collection<PsiFileSystemItem> getModuleResourceDirectories(@NotNull final PsiFile file) {
        final VirtualFile virtualFile = file.getVirtualFile();
        if (virtualFile == null) return Collections.emptyList();

        final PsiDirectory parent = file.getParent();
        final Module module = ModuleUtilCore.findModuleForPsiElement(parent == null ? file : parent);
        if (module == null) return Collections.emptyList();

        PsiManager psiManager = PsiManager.getInstance(module.getProject());

        Collection<PsiFileSystemItem> result = new ArrayList<>();

        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        for (VirtualFile sourceRoot : moduleRootManager.getSourceRoots()) {
            PsiDirectory psiDirectory = psiManager.findDirectory(sourceRoot);
            if (psiDirectory != null) {
                result.add(psiDirectory);
            }
        }

        return result;
    }
}
