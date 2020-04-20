package de.dm.intellij.bndtools.parser;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class OsgiVersion {

    private final int			major;
    private final int			minor;
    private final int			micro;
    private final String		qualifier;
    private static final String	SEPARATOR		= ".";
    private transient String	versionString /* default to null */;

    public OsgiVersion(String version) {
        int maj = 0;
        int min = 0;
        int mic = 0;
        String qual = "";

        try {
            StringTokenizer st = new StringTokenizer(version, SEPARATOR, true);
            maj = parseInt(st.nextToken(), version);

            if (st.hasMoreTokens()) { // minor
                st.nextToken(); // consume delimiter
                min = parseInt(st.nextToken(), version);

                if (st.hasMoreTokens()) { // micro
                    st.nextToken(); // consume delimiter
                    mic = parseInt(st.nextToken(), version);

                    if (st.hasMoreTokens()) { // qualifier separator
                        st.nextToken(); // consume delimiter
                        qual = st.nextToken(""); // remaining string

                        if (st.hasMoreTokens()) { // fail safe
                            throw new IllegalArgumentException("invalid version \"" + version + "\": invalid format");
                        }
                    }
                }
            }
        } catch (NoSuchElementException e) {
            IllegalArgumentException iae = new IllegalArgumentException("invalid version \"" + version + "\": invalid format");
            iae.initCause(e);
            throw iae;
        }
        major = maj;
        minor = min;
        micro = mic;
        qualifier = qual;

        validate();
    }

    /**
     * Parse numeric component into an int.
     *
     * @param value Numeric component
     * @param version Complete version string for exception message, if any
     * @return int value of numeric component
     */
    private static int parseInt(String value, String version) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            IllegalArgumentException iae = new IllegalArgumentException("invalid version \"" + version + "\": non-numeric \"" + value + "\"");
            iae.initCause(e);
            throw iae;
        }
    }

    /**
     * Called by the Version constructors to validate the version components.
     *
     * @throws IllegalArgumentException If the numerical components are negative
     *         or the qualifier string is invalid.
     */
    private void validate() {
        if (major < 0) {
            throw new IllegalArgumentException("invalid version \"" + toString0() + "\": negative number \"" + major + "\"");
        }
        if (minor < 0) {
            throw new IllegalArgumentException("invalid version \"" + toString0() + "\": negative number \"" + minor + "\"");
        }
        if (micro < 0) {
            throw new IllegalArgumentException("invalid version \"" + toString0() + "\": negative number \"" + micro + "\"");
        }
        for (char ch : qualifier.toCharArray()) {
            if (('A' <= ch) && (ch <= 'Z')) {
                continue;
            }
            if (('a' <= ch) && (ch <= 'z')) {
                continue;
            }
            if (('0' <= ch) && (ch <= '9')) {
                continue;
            }
            if ((ch == '_') || (ch == '-')) {
                continue;
            }
            throw new IllegalArgumentException("invalid version \"" + toString0() + "\": invalid qualifier \"" + qualifier + "\"");
        }
    }

    String toString0() {
        String s = versionString;
        if (s != null) {
            return s;
        }
        int q = qualifier.length();
        StringBuffer result = new StringBuffer(20 + q);
        result.append(major);
        result.append(SEPARATOR);
        result.append(minor);
        result.append(SEPARATOR);
        result.append(micro);
        if (q > 0) {
            result.append(SEPARATOR);
            result.append(qualifier);
        }
        return versionString = result.toString();
    }

}
