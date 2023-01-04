package de.dm.intellij.liferay.language.poshi.lexer;

import com.intellij.lexer.FlexAdapter;
import de.dm.intellij.liferay.language.poshi._PoshiLexer;

public class PoshiLexerAdapter extends FlexAdapter {
    public PoshiLexerAdapter() {
        super(new _PoshiLexer(null));
    }
}
