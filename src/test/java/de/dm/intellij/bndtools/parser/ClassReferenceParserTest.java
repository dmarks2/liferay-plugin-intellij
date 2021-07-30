/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package de.dm.intellij.bndtools.parser;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.LanguageLevelModuleExtension;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

/**
 * @author Dominik Marks
 */
public class ClassReferenceParserTest extends LightJavaCodeInsightFixtureTestCase {

	public void testInvalidClassReferenceHighlighting() {
		myFixture.configureByFiles("invalidClassReference/bnd.bnd", "com/liferay/test/Foo.java");

		List<HighlightInfo> highlightInfos = myFixture.doHighlighting();

		assertFalse(highlightInfos.isEmpty());

		HighlightInfo highlightInfo = highlightInfos.get(0);

		assertEquals(highlightInfo.getDescription(), "Cannot resolve class 'com.liferay.test.NonExisting'");
	}

	public void testResolveClass() {
		myFixture.configureByFiles("resolveClass/bnd.bnd", "com/liferay/test/Foo.java");

		PsiFile psiFile = myFixture.getFile();

		PsiElement element = psiFile.findElementAt(myFixture.getCaretOffset());

		PsiElement parentElement = element.getParent();

		PsiReference[] references = parentElement.getReferences();

		assertNotNull(references);
		assertEquals(references.length, 4);

		PsiElement resolve = references[3].resolve();

		assertNotNull(resolve);

		assertInstanceOf(resolve, PsiClass.class);

		PsiClass psiClass = (PsiClass)resolve;

		assertEquals("Foo", psiClass.getName());
	}

	public void testValidClassReferenceHighlighting() {
		myFixture.configureByFiles("validClassReference/bnd.bnd", "com/liferay/test/Foo.java");

		List<HighlightInfo> highlightInfos = myFixture.doHighlighting(HighlightSeverity.WARNING);

		assertTrue(highlightInfos.isEmpty());
	}

	public void testClassReferenceContributor() {
		myFixture.configureByFiles("classReferenceContributor/bnd.bnd", "com/liferay/test/Foo.java");

		myFixture.complete(CompletionType.BASIC, 1);
		List<String> strings = myFixture.getLookupElementStrings();
		assertTrue(strings.contains("Foo"));
	}

	@NotNull
	@Override
	protected LightProjectDescriptor getProjectDescriptor() {
		return _JAVA_DESCRIPTOR;
	}

	@Override
	protected String getTestDataPath() {
		return _TEST_DATA_PATH;
	}

	private static final LightProjectDescriptor _JAVA_DESCRIPTOR = new DefaultLightProjectDescriptor() {

		@Override
		public void configureModule(
                @NotNull Module module, @NotNull ModifiableRootModel modifiableRootModel,
                @NotNull ContentEntry contentEntry) {

			LanguageLevelModuleExtension extension = modifiableRootModel.getModuleExtension(
				LanguageLevelModuleExtension.class);

			if (extension != null) {
				extension.setLanguageLevel(LanguageLevel.JDK_1_8);
			}

			File testDataDir = new File(_TEST_DATA_PATH);

			final String testDataPath = PathUtil.toSystemIndependentName(testDataDir.getAbsolutePath());

			Disposable disposable = Disposer.newDisposable();
			try {
				VfsRootAccess.allowRootAccess(disposable, testDataPath);
			} finally {
				Disposer.dispose(disposable);
			}
		}

	};

	private static final String _TEST_DATA_PATH = "testdata/de/dm/intellij/bndtools/parser/ClassReferenceParserTest";

}