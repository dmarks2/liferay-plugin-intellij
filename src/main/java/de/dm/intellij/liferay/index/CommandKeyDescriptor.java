package de.dm.intellij.liferay.index;

import com.intellij.util.io.EnumeratorStringDescriptor;
import com.intellij.util.io.KeyDescriptor;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CommandKeyDescriptor implements KeyDescriptor<CommandKey> {

    @Override
    public int getHashCode(CommandKey value) {
        return value.hashCode();
    }

    @Override
    public boolean isEqual(CommandKey val1, CommandKey val2) {
        return val1.equals(val2);
    }

    @Override
    public void save(@NotNull DataOutput out, CommandKey value) throws IOException {
        EnumeratorStringDescriptor.INSTANCE.save(out, value.getPortletName());
        EnumeratorStringDescriptor.INSTANCE.save(out, value.getCommandName());
    }

    @Override
    public CommandKey read(@NotNull DataInput in) throws IOException {
        return new CommandKey(EnumeratorStringDescriptor.INSTANCE.read(in), EnumeratorStringDescriptor.INSTANCE.read(in));
    }

}
