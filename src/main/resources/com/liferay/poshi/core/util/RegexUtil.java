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

package com.liferay.poshi.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class RegexUtil {

	public static String getGroup(String content, String regex, int group) {
		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);

		Matcher matcher = pattern.matcher(content);

		if (matcher.find()) {
			return matcher.group(group);
		}

		return "";
	}

	public static String replace(String content, String regex, String group) {
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(content);

		if (matcher.find()) {
			return matcher.group(GetterUtil.getInteger(group));
		}

		return null;
	}

}