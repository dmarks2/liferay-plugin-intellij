/* The following code was generated by JFlex 1.7.0 tweaked for IntelliJ platform */

package de.dm.intellij.liferay.language.poshi;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static de.dm.intellij.liferay.language.poshi.psi.PoshiTypes.*;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.7.0
 * from the specification file <tt>_PoshiLexer.flex</tt>
 */
public class _PoshiLexer implements FlexLexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 0
  };

  /** 
   * Translates characters to character classes
   * Chosen bits are [11, 6, 4]
   * Total runtime size is 15392 bytes
   */
  public static int ZZ_CMAP(int ch) {
    return ZZ_CMAP_A[(ZZ_CMAP_Y[(ZZ_CMAP_Z[ch>>10]<<6)|((ch>>4)&0x3f)]<<4)|(ch&0xf)];
  }

  /* The ZZ_CMAP_Z table has 1088 entries */
  static final char ZZ_CMAP_Z[] = zzUnpackCMap(
    "\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\6\15\1\16\23\15"+
    "\1\17\1\15\1\20\1\21\12\15\1\22\10\12\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1"+
    "\32\1\33\1\34\1\35\1\36\2\12\1\15\1\37\3\12\1\40\10\12\1\41\1\42\5\15\1\43"+
    "\1\44\11\12\1\45\2\12\1\46\4\12\1\47\1\50\1\51\1\12\1\52\1\12\1\53\1\54\1"+
    "\55\3\12\51\15\1\56\3\15\1\57\1\60\4\15\1\61\12\12\1\62\u02c1\12\1\63\277"+
    "\12");

  /* The ZZ_CMAP_Y table has 3328 entries */
  static final char ZZ_CMAP_Y[] = zzUnpackCMap(
    "\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\1\1\11\1\12\1\13\1\14\1\13\1\14\34"+
    "\13\1\15\1\16\1\17\1\1\7\13\1\20\1\21\1\13\1\22\4\13\1\23\10\13\1\22\12\13"+
    "\1\24\1\13\1\25\1\24\1\13\1\26\1\24\1\13\1\27\1\30\1\13\1\31\1\32\1\1\1\31"+
    "\4\13\1\33\6\13\1\34\1\35\1\36\1\1\3\13\1\37\6\13\1\16\3\13\1\40\2\13\1\41"+
    "\1\1\1\13\1\42\4\1\1\13\1\43\1\1\1\44\1\22\7\13\1\45\1\24\1\34\1\46\1\35\1"+
    "\47\1\50\1\51\1\45\1\16\1\52\1\46\1\35\1\53\1\54\1\55\1\56\1\57\1\60\1\22"+
    "\1\35\1\61\1\62\1\63\1\45\1\64\1\65\1\46\1\35\1\61\1\66\1\67\1\45\1\70\1\71"+
    "\1\72\1\73\1\33\1\74\1\75\1\56\1\1\1\76\1\77\1\35\1\100\1\101\1\102\1\45\1"+
    "\1\1\76\1\77\1\35\1\103\1\101\1\104\1\45\1\105\1\106\1\77\1\13\1\37\1\107"+
    "\1\110\1\45\1\111\1\112\1\113\1\13\1\114\1\115\1\116\1\56\1\117\1\24\2\13"+
    "\1\31\1\120\1\121\2\1\1\122\1\123\1\124\1\125\1\126\1\127\2\1\1\63\1\130\1"+
    "\121\1\131\1\132\1\13\1\133\1\24\1\134\1\132\1\13\1\133\1\135\3\1\4\13\1\121"+
    "\4\13\1\41\2\13\1\136\2\13\1\137\24\13\1\140\1\141\2\13\1\140\2\13\1\142\1"+
    "\143\1\14\3\13\1\143\3\13\1\37\2\1\1\13\1\1\5\13\1\144\1\24\45\13\1\36\1\13"+
    "\1\145\1\31\4\13\1\146\1\147\1\150\1\151\1\13\1\151\1\13\1\152\1\150\1\153"+
    "\5\13\1\154\1\121\1\1\1\155\1\121\5\13\1\26\2\13\1\31\4\13\1\57\1\13\1\120"+
    "\2\42\1\56\1\13\1\41\1\151\2\13\1\42\1\13\2\121\2\1\1\13\1\42\3\13\1\120\1"+
    "\13\1\36\2\121\1\156\1\120\4\1\4\13\1\42\1\121\1\157\1\152\7\13\1\152\3\13"+
    "\1\26\1\100\2\13\1\41\1\147\4\1\1\160\1\13\1\161\17\13\1\162\21\13\1\144\2"+
    "\13\1\144\1\163\1\13\1\41\3\13\1\164\1\165\1\166\1\133\1\165\1\167\1\1\1\170"+
    "\1\171\1\63\1\172\1\1\1\173\1\1\1\133\3\1\2\13\1\63\1\174\1\175\1\176\1\127"+
    "\1\177\1\1\2\13\1\147\62\1\1\56\2\13\1\121\161\1\2\13\1\120\2\13\1\120\10"+
    "\13\1\200\1\152\2\13\1\136\3\13\1\201\1\171\1\13\1\202\4\203\2\13\2\1\1\171"+
    "\35\1\1\204\1\1\1\24\1\205\1\24\4\13\1\206\1\24\4\13\1\137\1\207\1\13\1\41"+
    "\1\24\4\13\1\120\1\1\1\13\1\31\3\1\1\13\40\1\133\13\1\57\4\1\135\13\1\57\2"+
    "\1\10\13\1\133\4\1\2\13\1\41\20\13\1\133\1\13\1\42\1\1\3\13\1\210\7\13\1\16"+
    "\1\1\1\211\1\212\5\13\1\213\1\13\1\120\1\26\3\1\1\211\2\13\1\26\1\1\3\13\1"+
    "\152\4\13\1\57\1\121\1\13\1\214\2\13\1\41\2\13\1\152\1\13\1\133\4\13\1\215"+
    "\1\121\1\13\1\120\3\13\1\202\1\41\1\121\1\13\1\113\4\13\1\32\1\155\1\13\1"+
    "\216\1\217\1\220\1\203\2\13\1\137\1\57\7\13\1\221\1\121\72\13\1\152\1\13\1"+
    "\222\2\13\1\42\20\1\26\13\1\41\6\13\1\121\2\1\1\202\1\223\1\35\1\224\1\225"+
    "\6\13\1\16\1\1\1\226\25\13\1\41\1\1\4\13\1\212\2\13\1\26\2\1\1\42\1\13\1\1"+
    "\1\13\1\227\1\230\2\1\1\134\7\13\1\133\1\1\1\121\1\24\1\231\1\24\1\31\1\56"+
    "\4\13\1\120\1\232\1\233\2\1\1\234\1\13\1\14\1\235\2\41\2\1\7\13\1\31\4\1\3"+
    "\13\1\151\7\1\1\236\10\1\1\13\1\133\3\13\2\63\1\1\2\13\1\1\1\13\1\31\2\13"+
    "\1\31\1\13\1\41\2\13\1\237\1\240\2\1\11\13\1\41\1\121\2\13\1\237\1\13\1\42"+
    "\2\13\1\26\3\13\1\152\11\1\23\13\1\202\1\13\1\57\1\26\11\1\1\241\2\13\1\242"+
    "\1\13\1\57\1\13\1\202\1\13\1\120\4\1\1\13\1\243\1\13\1\57\1\13\1\121\4\1\3"+
    "\13\1\244\4\1\1\245\1\246\1\13\1\247\2\1\1\13\1\133\1\13\1\133\2\1\1\132\1"+
    "\13\1\202\1\1\3\13\1\57\1\13\1\57\1\13\1\32\1\13\1\16\6\1\4\13\1\147\3\1\3"+
    "\13\1\32\3\13\1\32\60\1\4\13\1\202\1\1\1\56\1\171\3\13\1\31\1\1\1\13\1\147"+
    "\1\121\3\13\1\134\1\1\2\13\1\250\4\13\1\251\1\252\2\1\1\13\1\22\1\13\1\253"+
    "\4\1\1\254\1\27\1\147\3\13\1\31\1\121\1\34\1\46\1\35\1\61\1\66\1\255\1\256"+
    "\1\151\10\1\4\13\1\31\1\121\2\1\4\13\1\257\1\121\12\1\3\13\1\260\1\63\1\261"+
    "\2\1\4\13\1\262\1\121\2\1\3\13\1\26\1\121\3\1\1\13\1\100\1\42\1\121\26\1\4"+
    "\13\1\121\1\171\34\1\3\13\1\147\20\1\1\35\2\13\1\14\1\63\1\121\1\1\1\212\1"+
    "\13\1\212\1\132\1\202\64\1\71\13\1\121\6\1\6\13\1\120\1\1\14\13\1\152\53\1"+
    "\2\13\1\120\75\1\44\13\1\202\33\1\43\13\1\147\1\13\1\120\1\121\6\1\1\13\1"+
    "\41\1\151\3\13\1\202\1\152\1\121\1\226\1\263\1\13\67\1\4\13\1\151\2\13\1\120"+
    "\1\171\1\13\4\1\1\63\1\1\76\13\1\133\1\1\57\13\1\32\20\1\1\16\77\1\6\13\1"+
    "\31\1\133\1\147\1\264\114\1\1\265\1\266\1\267\1\1\1\270\11\1\1\271\33\1\5"+
    "\13\1\134\3\13\1\150\1\272\1\273\1\274\3\13\1\275\1\276\1\13\1\277\1\300\1"+
    "\77\24\13\1\260\1\13\1\77\1\137\1\13\1\137\1\13\1\134\1\13\1\134\1\120\1\13"+
    "\1\120\1\13\1\35\1\13\1\35\1\13\1\301\3\13\40\1\3\13\1\222\2\13\1\133\1\302"+
    "\1\303\1\157\1\24\25\1\1\14\1\213\1\304\75\1\14\13\1\151\1\202\2\1\4\13\1"+
    "\31\1\121\112\1\1\274\1\13\1\305\1\306\1\307\1\310\1\311\1\312\1\313\1\42"+
    "\1\314\1\42\47\1\1\13\1\121\1\13\1\121\1\13\1\121\47\1\55\13\1\202\2\1\103"+
    "\13\1\151\15\13\1\41\150\13\1\16\25\1\41\13\1\41\56\1\17\13\41\1");

  /* The ZZ_CMAP_A table has 3280 entries */
  static final char ZZ_CMAP_A[] = zzUnpackCMap(
    "\11\0\1\4\1\2\2\1\1\3\22\0\1\47\1\21\1\7\4\0\1\11\1\54\1\55\1\6\1\20\1\61"+
    "\1\17\1\60\1\5\12\13\1\62\1\63\1\0\1\22\2\0\1\12\3\14\1\37\20\14\1\33\5\14"+
    "\1\56\1\10\1\57\3\14\1\35\1\15\1\42\1\23\1\24\1\25\1\15\1\51\1\26\1\15\1\50"+
    "\1\46\1\43\1\27\1\31\1\34\1\15\1\36\1\32\1\30\1\41\1\45\1\40\1\15\1\44\1\15"+
    "\1\52\1\0\1\53\7\0\1\1\12\0\1\4\11\0\1\16\12\0\1\16\4\0\1\16\5\0\27\16\1\0"+
    "\12\16\4\0\14\16\16\0\5\16\7\0\1\16\1\0\1\16\1\0\5\16\1\0\2\16\2\0\4\16\1"+
    "\0\1\16\6\0\1\16\1\0\3\16\1\0\1\16\1\0\4\16\1\0\23\16\1\0\11\16\1\0\26\16"+
    "\2\0\1\16\6\0\10\16\10\0\16\16\1\0\1\16\1\0\2\16\1\0\2\16\1\0\1\16\10\0\13"+
    "\16\5\0\3\16\15\0\12\16\4\0\6\16\1\0\10\16\2\0\12\16\1\0\23\16\2\0\14\16\2"+
    "\0\11\16\4\0\1\16\5\0\16\16\2\0\14\16\4\0\5\16\1\0\10\16\6\0\20\16\2\0\13"+
    "\16\2\0\16\16\1\0\1\16\3\0\4\16\2\0\11\16\2\0\2\16\2\0\4\16\10\0\1\16\4\0"+
    "\2\16\1\0\1\16\1\0\3\16\1\0\6\16\4\0\2\16\1\0\2\16\1\0\2\16\1\0\2\16\2\0\1"+
    "\16\1\0\5\16\4\0\2\16\2\0\3\16\3\0\1\16\7\0\4\16\1\0\1\16\7\0\20\16\13\0\3"+
    "\16\1\0\11\16\1\0\2\16\1\0\2\16\1\0\5\16\2\0\12\16\1\0\3\16\1\0\3\16\2\0\1"+
    "\16\30\0\1\16\7\0\3\16\1\0\10\16\2\0\6\16\2\0\2\16\2\0\3\16\10\0\2\16\4\0"+
    "\2\16\1\0\1\16\1\0\1\16\20\0\2\16\1\0\6\16\3\0\3\16\1\0\4\16\3\0\2\16\1\0"+
    "\1\16\1\0\2\16\3\0\2\16\3\0\3\16\3\0\5\16\3\0\3\16\1\0\4\16\2\0\1\16\6\0\1"+
    "\16\10\0\4\16\1\0\10\16\1\0\3\16\1\0\30\16\3\0\10\16\1\0\3\16\1\0\4\16\7\0"+
    "\2\16\1\0\3\16\5\0\4\16\1\0\5\16\2\0\4\16\5\0\2\16\7\0\1\16\2\0\2\16\16\0"+
    "\3\16\1\0\10\16\1\0\7\16\1\0\3\16\1\0\5\16\5\0\4\16\7\0\1\16\12\0\6\16\2\0"+
    "\2\16\1\0\22\16\3\0\10\16\1\0\11\16\1\0\1\16\2\0\7\16\3\0\1\16\4\0\6\16\1"+
    "\0\1\16\1\0\10\16\2\0\2\16\14\0\17\16\1\0\12\16\7\0\2\16\1\0\1\16\2\0\2\16"+
    "\1\0\1\16\2\0\1\16\6\0\4\16\1\0\7\16\1\0\3\16\1\0\1\16\1\0\1\16\2\0\2\16\1"+
    "\0\15\16\1\0\3\16\2\0\5\16\1\0\1\16\1\0\6\16\2\0\12\16\2\0\4\16\10\0\2\16"+
    "\13\0\1\16\1\0\1\16\1\0\1\16\4\0\12\16\1\0\24\16\3\0\5\16\1\0\12\16\6\0\1"+
    "\16\11\0\6\16\1\0\1\16\5\0\1\16\2\0\13\16\1\0\15\16\1\0\4\16\2\0\7\16\1\0"+
    "\1\16\1\0\4\16\2\0\1\16\1\0\4\16\2\0\7\16\1\0\1\16\1\0\4\16\2\0\16\16\2\0"+
    "\6\16\2\0\1\4\32\16\3\0\13\16\7\0\15\16\1\0\7\16\13\0\4\16\14\0\1\16\1\0\2"+
    "\16\14\0\4\16\3\0\1\16\4\0\2\16\15\0\3\16\11\0\1\16\23\0\10\16\1\0\23\16\1"+
    "\0\2\16\6\0\6\16\5\0\15\16\1\0\1\16\1\0\1\16\1\0\1\16\1\0\6\16\1\0\7\16\1"+
    "\0\1\16\3\0\3\16\1\0\7\16\3\0\4\16\2\0\6\16\4\0\13\4\15\0\2\1\5\0\1\4\17\0"+
    "\1\16\4\0\1\16\12\0\1\4\1\0\1\16\15\0\1\16\2\0\1\16\4\0\1\16\2\0\12\16\1\0"+
    "\1\16\3\0\5\16\6\0\1\16\1\0\1\16\1\0\1\16\1\0\4\16\1\0\1\16\5\0\5\16\4\0\1"+
    "\16\1\0\5\16\6\0\15\16\7\0\10\16\11\0\7\16\1\0\7\16\1\0\1\4\4\0\3\16\11\0"+
    "\5\16\2\0\5\16\3\0\7\16\2\0\2\16\2\0\3\16\5\0\16\16\1\0\12\16\1\0\1\16\7\0"+
    "\11\16\2\0\27\16\2\0\15\16\3\0\1\16\1\0\1\16\2\0\1\16\16\0\1\16\2\0\5\16\12"+
    "\0\6\16\2\0\6\16\2\0\6\16\11\0\13\16\1\0\2\16\2\0\7\16\4\0\5\16\3\0\5\16\5"+
    "\0\12\16\1\0\5\16\1\0\1\16\1\0\2\16\1\0\2\16\1\0\12\16\3\0\15\16\3\0\2\16"+
    "\30\0\16\16\4\0\1\16\2\0\6\16\2\0\6\16\2\0\6\16\2\0\3\16\3\0\14\16\1\0\16"+
    "\16\1\0\2\16\1\0\1\16\15\0\1\16\2\0\4\16\4\0\10\16\1\0\5\16\12\0\6\16\2\0"+
    "\1\16\1\0\14\16\1\0\2\16\3\0\1\16\2\0\4\16\1\0\2\16\12\0\10\16\6\0\6\16\1"+
    "\0\2\16\5\0\10\16\1\0\3\16\1\0\13\16\4\0\3\16\4\0\5\16\2\0\1\16\11\0\5\16"+
    "\5\0\3\16\3\0\13\16\1\0\1\16\3\0\10\16\6\0\1\16\1\0\7\16\1\0\1\16\1\0\4\16"+
    "\1\0\2\16\6\0\1\16\5\0\7\16\2\0\7\16\3\0\6\16\1\0\1\16\10\0\6\16\2\0\10\16"+
    "\10\0\6\16\2\0\1\16\3\0\1\16\13\0\10\16\5\0\15\16\3\0\2\16\6\0\5\16\3\0\6"+
    "\16\10\0\10\16\2\0\7\16\16\0\4\16\4\0\3\16\15\0\1\16\2\0\2\16\2\0\4\16\1\0"+
    "\14\16\1\0\1\16\1\0\7\16\1\0\21\16\1\0\4\16\2\0\10\16\1\0\7\16\1\0\14\16\1"+
    "\0\4\16\1\0\5\16\1\0\1\16\3\0\11\16\1\0\10\16\2\0\2\16\5\0\1\16\16\0\1\16"+
    "\13\0\2\16\1\0\2\16\1\0\5\16\6\0\2\16\1\0\1\16\2\0\1\16\1\0\12\16\1\0\4\16"+
    "\1\0\1\16\1\0\1\16\6\0\1\16\4\0\1\16\1\0\1\16\1\0\1\16\1\0\3\16\1\0\2\16\1"+
    "\0\1\16\2\0\1\16\1\0\1\16\1\0\1\16\1\0\1\16\1\0\1\16\1\0\2\16\1\0\1\16\2\0"+
    "\4\16\1\0\7\16\1\0\4\16\1\0\4\16\1\0\1\16\1\0\12\16\1\0\5\16\1\0\3\16\1\0"+
    "\5\16\1\0\5\16");

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\2\2\1\1\3\1\4\2\1\1\5"+
    "\1\6\1\7\1\10\14\4\1\11\1\12\1\13\1\14"+
    "\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24"+
    "\1\3\2\0\1\25\1\0\1\26\1\7\4\4\1\27"+
    "\12\4\1\0\2\25\2\4\1\30\12\4\1\31\1\4"+
    "\1\24\2\0\1\25\1\4\1\32\1\4\1\33\1\4"+
    "\1\34\6\4\1\35\1\0\1\25\1\4\1\0\2\4"+
    "\1\36\3\4\1\37\1\40\1\0\1\41\1\4\1\0"+
    "\2\4\1\42\1\4\1\43\1\41\1\4\1\44\4\4"+
    "\1\45\1\46\1\47\1\4\1\50";

  private static int [] zzUnpackAction() {
    int [] result = new int[127];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\64\0\150\0\234\0\320\0\u0104\0\u0138\0\u016c"+
    "\0\u01a0\0\u01d4\0\64\0\u0208\0\u0208\0\u023c\0\u0270\0\u02a4"+
    "\0\u02d8\0\u030c\0\u0340\0\u0374\0\u03a8\0\u03dc\0\u0410\0\u0444"+
    "\0\u0478\0\64\0\64\0\64\0\64\0\u0138\0\u0138\0\64"+
    "\0\64\0\64\0\64\0\u04ac\0\u04e0\0\64\0\u0514\0\u0548"+
    "\0\u057c\0\u01a0\0\u05b0\0\64\0\u05e4\0\u0618\0\u064c\0\u0680"+
    "\0\u0138\0\u06b4\0\u06e8\0\u071c\0\u0750\0\u0784\0\u07b8\0\u07ec"+
    "\0\u0820\0\u0854\0\u0888\0\u08bc\0\u0548\0\u08f0\0\u0924\0\u0958"+
    "\0\u0138\0\u098c\0\u09c0\0\u09f4\0\u0a28\0\u0a5c\0\u0a90\0\u0ac4"+
    "\0\u0af8\0\u0b2c\0\u0b60\0\u0138\0\u0b94\0\64\0\u08f0\0\u0bc8"+
    "\0\u0bfc\0\u0c30\0\u0c64\0\u0c98\0\u0138\0\u0ccc\0\u0138\0\u0d00"+
    "\0\u0d34\0\u0d68\0\u0d9c\0\u0dd0\0\u0e04\0\u0138\0\u0e38\0\u0e6c"+
    "\0\u0ea0\0\u0ed4\0\u0f08\0\u0f3c\0\u0138\0\u0f70\0\u0fa4\0\u0fd8"+
    "\0\u0138\0\u0138\0\u100c\0\u0e6c\0\u1040\0\u1074\0\u10a8\0\u10dc"+
    "\0\u0138\0\u1110\0\u0138\0\u100c\0\u1144\0\64\0\u1178\0\u11ac"+
    "\0\u11e0\0\u1214\0\u0138\0\u0138\0\u0138\0\u1248\0\u0138";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[127];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\4\3\1\4\1\5\1\6\1\7\1\10\1\11"+
    "\1\12\2\7\1\2\2\13\1\14\1\15\1\16\1\17"+
    "\1\20\1\21\1\7\1\22\1\7\1\23\1\7\1\24"+
    "\1\7\1\25\1\7\1\26\2\7\1\27\1\7\1\30"+
    "\1\31\1\3\2\7\1\32\1\33\1\34\1\35\1\36"+
    "\1\37\1\40\1\41\1\42\1\43\65\0\4\3\42\0"+
    "\1\3\21\0\1\44\1\45\62\0\1\13\56\0\2\6"+
    "\2\0\3\6\1\46\1\47\53\6\10\0\1\7\2\0"+
    "\5\7\3\0\24\7\1\0\2\7\4\0\2\7\4\0"+
    "\1\50\3\0\5\50\1\51\52\50\1\0\4\52\6\0"+
    "\1\53\1\0\1\53\1\0\1\53\3\0\10\53\1\0"+
    "\3\53\1\0\7\53\1\52\2\53\25\0\1\12\72\0"+
    "\1\54\51\0\1\7\2\0\5\7\3\0\1\7\1\55"+
    "\22\7\1\0\2\7\4\0\2\7\14\0\1\7\2\0"+
    "\5\7\3\0\23\7\1\56\1\0\2\7\4\0\2\7"+
    "\14\0\1\7\2\0\5\7\3\0\6\7\1\57\7\7"+
    "\1\60\5\7\1\0\2\7\4\0\2\7\14\0\1\7"+
    "\2\0\5\7\3\0\2\7\1\61\21\7\1\0\2\7"+
    "\4\0\2\7\14\0\1\7\2\0\5\7\3\0\1\7"+
    "\1\62\10\7\1\63\11\7\1\0\2\7\4\0\2\7"+
    "\14\0\1\7\2\0\5\7\3\0\1\7\1\64\3\7"+
    "\1\65\16\7\1\0\2\7\4\0\2\7\14\0\1\7"+
    "\2\0\5\7\3\0\13\7\1\66\10\7\1\0\2\7"+
    "\4\0\2\7\14\0\1\7\2\0\5\7\3\0\1\7"+
    "\1\67\22\7\1\0\2\7\4\0\2\7\14\0\1\7"+
    "\2\0\5\7\3\0\24\7\1\0\1\7\1\70\4\0"+
    "\2\7\14\0\1\7\2\0\5\7\3\0\12\7\1\71"+
    "\11\7\1\0\2\7\4\0\2\7\14\0\1\7\2\0"+
    "\5\7\3\0\12\7\1\72\11\7\1\0\2\7\4\0"+
    "\2\7\14\0\1\7\2\0\5\7\3\0\3\7\1\73"+
    "\20\7\1\0\2\7\4\0\2\7\4\0\1\44\3\0"+
    "\60\44\6\45\1\74\55\45\2\6\2\0\60\6\1\50"+
    "\3\0\5\50\1\75\53\50\3\0\5\50\1\76\52\50"+
    "\13\0\1\53\1\0\1\53\1\0\1\53\3\0\10\53"+
    "\1\0\3\53\1\0\7\53\1\0\2\53\22\0\1\7"+
    "\2\0\5\7\3\0\2\7\1\77\21\7\1\0\2\7"+
    "\4\0\2\7\14\0\1\7\2\0\5\7\3\0\7\7"+
    "\1\100\14\7\1\0\2\7\4\0\2\7\14\0\1\7"+
    "\2\0\5\7\3\0\13\7\1\101\10\7\1\0\2\7"+
    "\4\0\2\7\14\0\1\7\2\0\5\7\3\0\4\7"+
    "\1\102\17\7\1\0\2\7\4\0\2\7\14\0\1\7"+
    "\2\0\5\7\3\0\7\7\1\103\2\7\1\104\11\7"+
    "\1\0\2\7\4\0\2\7\14\0\1\7\2\0\5\7"+
    "\3\0\7\7\1\105\14\7\1\0\2\7\4\0\2\7"+
    "\14\0\1\7\2\0\5\7\3\0\5\7\1\106\16\7"+
    "\1\0\2\7\4\0\2\7\14\0\1\7\2\0\5\7"+
    "\3\0\12\7\1\107\11\7\1\0\2\7\4\0\2\7"+
    "\14\0\1\7\2\0\5\7\3\0\6\7\1\110\15\7"+
    "\1\0\2\7\4\0\2\7\14\0\1\7\2\0\5\7"+
    "\3\0\5\7\1\111\16\7\1\0\2\7\4\0\2\7"+
    "\14\0\1\7\2\0\5\7\3\0\3\7\1\112\20\7"+
    "\1\0\2\7\4\0\2\7\14\0\1\7\2\0\5\7"+
    "\3\0\17\7\1\113\4\7\1\0\2\7\4\0\2\7"+
    "\14\0\1\7\2\0\5\7\3\0\13\7\1\114\10\7"+
    "\1\0\2\7\4\0\2\7\14\0\1\7\2\0\5\7"+
    "\3\0\7\7\1\115\14\7\1\0\2\7\4\0\2\7"+
    "\4\0\5\45\1\116\1\74\55\45\1\117\1\0\1\120"+
    "\1\0\5\117\1\121\52\117\10\0\1\7\2\0\5\7"+
    "\3\0\3\7\1\122\20\7\1\0\2\7\4\0\2\7"+
    "\14\0\1\7\2\0\5\7\3\0\1\7\1\123\22\7"+
    "\1\0\2\7\4\0\2\7\14\0\1\7\2\0\5\7"+
    "\3\0\17\7\1\124\4\7\1\0\2\7\4\0\2\7"+
    "\14\0\1\7\2\0\5\7\3\0\5\7\1\125\16\7"+
    "\1\0\2\7\4\0\2\7\14\0\1\7\2\0\5\7"+
    "\3\0\13\7\1\126\10\7\1\0\2\7\4\0\2\7"+
    "\14\0\1\7\2\0\5\7\3\0\24\7\1\0\1\127"+
    "\1\7\4\0\2\7\14\0\1\7\2\0\5\7\3\0"+
    "\10\7\1\130\13\7\1\0\2\7\4\0\2\7\14\0"+
    "\1\7\2\0\5\7\3\0\5\7\1\131\16\7\1\0"+
    "\2\7\4\0\2\7\14\0\1\7\2\0\5\7\3\0"+
    "\11\7\1\132\12\7\1\0\2\7\4\0\2\7\14\0"+
    "\1\7\2\0\5\7\3\0\16\7\1\133\5\7\1\0"+
    "\2\7\4\0\2\7\14\0\1\7\2\0\5\7\3\0"+
    "\23\7\1\134\1\0\2\7\4\0\2\7\14\0\1\7"+
    "\2\0\5\7\3\0\13\7\1\135\10\7\1\0\2\7"+
    "\4\0\2\7\14\0\1\7\2\0\5\7\3\0\5\7"+
    "\1\136\16\7\1\0\2\7\4\0\2\7\4\0\1\120"+
    "\1\0\1\120\1\0\5\120\1\137\52\120\1\117\1\0"+
    "\1\120\1\0\5\117\1\140\52\117\10\0\1\7\2\0"+
    "\5\7\3\0\4\7\1\141\17\7\1\0\2\7\4\0"+
    "\2\7\14\0\1\7\2\0\5\7\3\0\24\7\1\142"+
    "\2\7\4\0\2\7\14\0\1\7\2\0\5\7\3\0"+
    "\5\7\1\143\16\7\1\0\2\7\4\0\2\7\14\0"+
    "\1\7\2\0\5\7\3\0\14\7\1\144\7\7\1\0"+
    "\2\7\4\0\2\7\14\0\1\7\2\0\5\7\3\0"+
    "\11\7\1\145\12\7\1\0\2\7\4\0\2\7\14\0"+
    "\1\7\2\0\5\7\3\0\3\7\1\146\20\7\1\0"+
    "\2\7\4\0\2\7\14\0\1\7\2\0\5\7\3\0"+
    "\1\7\1\147\22\7\1\0\2\7\4\0\2\7\14\0"+
    "\1\7\2\0\5\7\3\0\13\7\1\150\10\7\1\0"+
    "\2\7\4\0\2\7\14\0\1\7\2\0\5\7\3\0"+
    "\1\7\1\151\22\7\1\0\2\7\4\0\2\7\14\0"+
    "\1\7\2\0\5\7\3\0\6\7\1\152\15\7\1\0"+
    "\2\7\4\0\2\7\4\0\1\120\1\0\1\120\1\0"+
    "\5\120\1\153\52\120\1\117\1\0\1\120\1\0\5\117"+
    "\1\154\52\117\10\0\1\7\2\0\5\7\3\0\3\7"+
    "\1\155\20\7\1\0\2\7\4\0\2\7\32\0\1\156"+
    "\45\0\1\7\2\0\5\7\3\0\3\7\1\157\20\7"+
    "\1\0\2\7\4\0\2\7\14\0\1\7\2\0\5\7"+
    "\3\0\6\7\1\160\15\7\1\0\2\7\4\0\2\7"+
    "\14\0\1\7\2\0\5\7\3\0\17\7\1\161\4\7"+
    "\1\0\2\7\4\0\2\7\14\0\1\7\2\0\5\7"+
    "\3\0\13\7\1\162\10\7\1\0\2\7\4\0\2\7"+
    "\14\0\1\7\2\0\5\7\3\0\4\7\1\163\17\7"+
    "\1\0\2\7\4\0\2\7\4\0\1\120\1\0\1\120"+
    "\1\0\5\120\1\164\52\120\10\0\1\7\2\0\5\7"+
    "\3\0\5\7\1\165\16\7\1\0\2\7\4\0\2\7"+
    "\31\0\1\166\46\0\1\7\2\0\5\7\3\0\6\7"+
    "\1\167\15\7\1\0\2\7\4\0\2\7\14\0\1\7"+
    "\2\0\5\7\3\0\15\7\1\170\6\7\1\0\2\7"+
    "\4\0\2\7\14\0\1\7\2\0\5\7\3\0\5\7"+
    "\1\171\16\7\1\0\2\7\4\0\2\7\14\0\1\7"+
    "\2\0\5\7\3\0\3\7\1\172\20\7\1\0\2\7"+
    "\4\0\2\7\14\0\1\7\2\0\5\7\3\0\4\7"+
    "\1\173\17\7\1\0\2\7\4\0\2\7\14\0\1\7"+
    "\2\0\5\7\3\0\4\7\1\174\17\7\1\0\2\7"+
    "\4\0\2\7\14\0\1\7\2\0\5\7\3\0\21\7"+
    "\1\175\2\7\1\0\2\7\4\0\2\7\14\0\1\7"+
    "\2\0\5\7\3\0\6\7\1\176\15\7\1\0\2\7"+
    "\4\0\2\7\14\0\1\7\2\0\5\7\3\0\4\7"+
    "\1\177\17\7\1\0\2\7\4\0\2\7\4\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[4732];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String[] ZZ_ERROR_MSG = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\11\10\1\1\11\16\1\4\11\2\1\4\11"+
    "\2\1\1\11\2\0\1\1\1\0\1\1\1\11\17\1"+
    "\1\0\21\1\1\11\2\0\16\1\1\0\2\1\1\0"+
    "\10\1\1\0\2\1\1\0\7\1\1\11\11\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[127];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private CharSequence zzBuffer = "";

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /**
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
  public _PoshiLexer() {
    this((java.io.Reader)null);
  }


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public _PoshiLexer(java.io.Reader in) {
    this.zzReader = in;
  }


  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    int size = 0;
    for (int i = 0, length = packed.length(); i < length; i += 2) {
      size += packed.charAt(i);
    }
    char[] map = new char[size];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < packed.length()) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }

  public final int getTokenStart() {
    return zzStartRead;
  }

  public final int getTokenEnd() {
    return getTokenStart() + yylength();
  }

  public void reset(CharSequence buffer, int start, int end, int initialState) {
    zzBuffer = buffer;
    zzCurrentPos = zzMarkedPos = zzStartRead = start;
    zzAtEOF  = false;
    zzAtBOL = true;
    zzEndRead = end;
    yybegin(initialState);
  }

  /**
   * Refills the input buffer.
   *
   * @return      {@code false}, iff there was new input.
   *
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {
    return true;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final CharSequence yytext() {
    return zzBuffer.subSequence(zzStartRead, zzMarkedPos);
  }


  /**
   * Returns the character at position {@code pos} from the
   * matched text.
   *
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch.
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer.charAt(zzStartRead+pos);
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occurred while scanning.
   *
   * In a wellformed scanner (no or only correct usage of
   * yypushback(int) and a match-all fallback rule) this method
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public IElementType advance() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    CharSequence zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL/*, zzEndReadL*/);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL/*, zzEndReadL*/);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + ZZ_CMAP(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
        return null;
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1: 
            { return BAD_CHARACTER;
            } 
            // fall through
          case 41: break;
          case 2: 
            { return WHITE_SPACE;
            } 
            // fall through
          case 42: break;
          case 3: 
            { return DOUBLE_QUOTED_STRING;
            } 
            // fall through
          case 43: break;
          case 4: 
            { return IDENTIFIER;
            } 
            // fall through
          case 44: break;
          case 5: 
            { return NUMERIC_CONSTANT;
            } 
            // fall through
          case 45: break;
          case 6: 
            { return ARITHMETIC_OPERATOR;
            } 
            // fall through
          case 46: break;
          case 7: 
            { return COMPARISION_OPERATOR;
            } 
            // fall through
          case 47: break;
          case 8: 
            { return EQUALS;
            } 
            // fall through
          case 48: break;
          case 9: 
            { return CURLY_LBRACE;
            } 
            // fall through
          case 49: break;
          case 10: 
            { return CURLY_RBRACE;
            } 
            // fall through
          case 50: break;
          case 11: 
            { return ROUND_LBRACE;
            } 
            // fall through
          case 51: break;
          case 12: 
            { return ROUND_RBRACE;
            } 
            // fall through
          case 52: break;
          case 13: 
            { return SQUARE_LBRACE;
            } 
            // fall through
          case 53: break;
          case 14: 
            { return SQUARE_RBRACE;
            } 
            // fall through
          case 54: break;
          case 15: 
            { return PERIOD;
            } 
            // fall through
          case 55: break;
          case 16: 
            { return COMMA;
            } 
            // fall through
          case 56: break;
          case 17: 
            { return COLON;
            } 
            // fall through
          case 57: break;
          case 18: 
            { return SEMICOLON;
            } 
            // fall through
          case 58: break;
          case 19: 
            { return LINE_COMMENT;
            } 
            // fall through
          case 59: break;
          case 20: 
            { return BLOCK_COMMENT;
            } 
            // fall through
          case 60: break;
          case 21: 
            { return SINGLE_QUOTED_STRING;
            } 
            // fall through
          case 61: break;
          case 22: 
            { return ANNOTATION_NAME;
            } 
            // fall through
          case 62: break;
          case 23: 
            { return IF;
            } 
            // fall through
          case 63: break;
          case 24: 
            { return FOR;
            } 
            // fall through
          case 64: break;
          case 25: 
            { return VAR;
            } 
            // fall through
          case 65: break;
          case 26: 
            { return ELSE;
            } 
            // fall through
          case 66: break;
          case 27: 
            { return TEST;
            } 
            // fall through
          case 67: break;
          case 28: 
            { return TASK;
            } 
            // fall through
          case 68: break;
          case 29: 
            { return LIST;
            } 
            // fall through
          case 69: break;
          case 30: 
            { return SET_UP;
            } 
            // fall through
          case 70: break;
          case 31: 
            { return WHILE;
            } 
            // fall through
          case 71: break;
          case 32: 
            { return MACRO;
            } 
            // fall through
          case 72: break;
          case 33: 
            { return SINGLE_QUOTED_MULTILINE;
            } 
            // fall through
          case 73: break;
          case 34: 
            { return STATIC;
            } 
            // fall through
          case 74: break;
          case 35: 
            { return RETURN;
            } 
            // fall through
          case 75: break;
          case 36: 
            { return ELSE_IF;
            } 
            // fall through
          case 76: break;
          case 37: 
            { return FUNCTION;
            } 
            // fall through
          case 77: break;
          case 38: 
            { return TEAR_DOWN;
            } 
            // fall through
          case 78: break;
          case 39: 
            { return PROPERTY;
            } 
            // fall through
          case 79: break;
          case 40: 
            { return DEFINITION;
            } 
            // fall through
          case 80: break;
          default:
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
