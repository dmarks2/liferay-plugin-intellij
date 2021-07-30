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

import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.LanguageLevelModuleExtension;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.pom.java.LanguageLevel;
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
public class BundleActivatorParserTest extends LightJavaCodeInsightFixtureTestCase {

	public void testInvalidBundleActivatorHighlighting() {
		myFixture.configureByFiles(
			"invalid/bnd.bnd", "com/liferay/test/Foo.java", "org/osgi/framework/BundleActivator.java");

		List<HighlightInfo> highlightInfos = myFixture.doHighlighting();

		assertFalse(highlightInfos.isEmpty());

		HighlightInfo highlightInfo = highlightInfos.get(0);

		assertEquals(highlightInfo.getDescription(), "Activator class does not inherit from BundleActivator");
	}

	public void testValidBundleActivatorHighlighting() {
		myFixture.configureByFiles(
			"valid/bnd.bnd", "com/liferay/test/MyBundleActivator.java", "org/osgi/framework/BundleActivator.java");

		List<HighlightInfo> highlightInfos = myFixture.doHighlighting();

		assertTrue(highlightInfos.isEmpty());
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

	private static final String _TEST_DATA_PATH = "testdata/de/dm/intellij/bndtools/parser/BundleActivatorParserTest";

}