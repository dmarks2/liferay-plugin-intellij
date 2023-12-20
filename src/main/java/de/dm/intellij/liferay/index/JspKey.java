package de.dm.intellij.liferay.index;

public record JspKey(String portletName, String jspPath) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JspKey that = (JspKey) o;
        return portletName.equals(that.portletName) &&
                jspPath.equals(that.jspPath);
    }

}
