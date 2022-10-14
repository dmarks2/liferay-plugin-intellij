package de.dm.intellij.liferay.language.properties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.AbstractMap;
import java.util.Map;
import java.util.TreeMap;

public class LiferayPortalPropertiesParser {

    public static Map<String, AbstractMap.SimpleImmutableEntry<String, String>> getPortalProperties(InputStream inputStream) throws IOException {
        Map<String, AbstractMap.SimpleImmutableEntry<String, String>> result = new TreeMap<>();

        StringBuilder currentComment = new StringBuilder();

        StringBuilder joinedLines = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            for(String line; (line = reader.readLine()) != null; ) {
                if (line.startsWith("##")) {
                    joinedLines = new StringBuilder();

                    //Block comment, continue
                    continue;
                }

                if (line.startsWith("    # ")) {
                    currentComment.append(line);
                    currentComment.append("\n");

                    joinedLines = new StringBuilder();

                    continue;
                }

                if (line.equals("    #")) {
                    currentComment.append("\n");

                    joinedLines = new StringBuilder();

                    continue;
                }

                if (line.length() == 0) {
                    currentComment.setLength(0);

                    joinedLines = new StringBuilder();

                    continue;
                }

                String property = "";

                if (line.endsWith("\\")) {
                    joinedLines.append(line).append("\n");
                } else {
                    String currentLine = line;
                    if (joinedLines.length() > 0) {
                        currentLine = joinedLines.toString();
                    }

                    if (currentLine.startsWith("    #")) {
                        property = currentLine.substring(5);
                    } else {
                        property = currentLine.substring(4);
                    }

                    if (property.contains("=")) {
                        String[] split = property.split("=");

                        String propertyKey = split[0];

                        String type = String.class.getSimpleName();

                        String value = "";

                        if (split.length > 1) {
                            value = split[1];

                            if ("true".equalsIgnoreCase(value.trim()) || "false".equalsIgnoreCase(value.trim())) {
                                type = Boolean.class.getSimpleName();
                            } else if (isNumericString(value.trim())) {
                                type = Integer.class.getSimpleName();
                            }
                        }

                        AbstractMap.SimpleImmutableEntry<String, String> entry = new AbstractMap.SimpleImmutableEntry<>(type + "|" + value, currentComment.toString());

                        result.put(propertyKey, entry);
                    }

                    joinedLines = new StringBuilder();
                }
            }
        }

        return result;
    }

    private static boolean isNumeric(char c) {
        return (c >= '0' && c <= '9');
    }

    private static boolean isNumericString(String s) {
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (!isNumeric(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
