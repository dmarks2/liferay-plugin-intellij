package de.dm.intellij.liferay.util;

import org.jetbrains.annotations.NotNull;

public class Version implements Comparable<Version> {

	public final int[] numbers;

	public Version(@NotNull String version) {
		String[] split = version.split("-")[0].split("\\.");

		numbers = new int[split.length];

		for (int i = 0; i < split.length; i++) {
			numbers[i] = Integer.parseInt(split[i]);
		}
	}

	@Override
	public int compareTo(@NotNull Version another) {
		final int maxLength = Math.max(numbers.length, another.numbers.length);

		for (int i = 0; i < maxLength; i++) {
			final int left = i < numbers.length ? numbers[i] : 0;
			final int right = i < another.numbers.length ? another.numbers[i] : 0;

			if (left != right) {
				return left < right ? -1 : 1;
			}
		}
		return 0;
	}

	public static int compare(String version1, String version2) {
		return new Version(version1).compareTo(new Version(version2));
	}
}
