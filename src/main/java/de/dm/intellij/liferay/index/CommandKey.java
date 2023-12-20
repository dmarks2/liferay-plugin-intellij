package de.dm.intellij.liferay.index;

public record CommandKey(String portletName, String commandName) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandKey that = (CommandKey) o;
        return portletName.equals(that.portletName) &&
                commandName.equals(that.commandName);
    }

}
