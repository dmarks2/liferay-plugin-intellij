package de.dm.intellij.liferay.index;

import java.util.Objects;

public class CommandKey {

    private final String portletName;

    private final String commandName;

    public CommandKey(String portletName, String commandName) {
        this.portletName = portletName;
        this.commandName = commandName;
    }

    public String getPortletName() {
        return portletName;
    }

    public String getCommandName() {
        return commandName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandKey that = (CommandKey) o;
        return portletName.equals(that.portletName) &&
                commandName.equals(that.commandName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(portletName, commandName);
    }
}
