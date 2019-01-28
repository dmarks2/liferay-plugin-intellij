package de.dm.intellij.liferay.index;

import java.util.Objects;

public class JspKey {

    private final String portletName;

    private final String jspPath;

    public JspKey(String portletName, String jspPath) {
        this.portletName = portletName;
        this.jspPath = jspPath;
    }

    public String getPortletName() {
        return portletName;
    }

    public String getJspPath() {
        return jspPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JspKey that = (JspKey) o;
        return portletName.equals(that.portletName) &&
            jspPath.equals(that.jspPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(portletName, jspPath);
    }

}
