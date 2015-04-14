package com.sendtend.utils.CalculatorHelper;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CalculatorLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
	public static final int PLUS=1, MINUS=2, MULT=3, DIV=4, RPAREN=5, LPAREN=6, INT=7, NEWLINE=8, WS=9;
	public static String[] modeNames = {"DEFAULT_MODE"};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", "'\\u0006'", "'\\u0007'", "'\b'", "'\t'"
	};
	
	public static final String[] ruleNames = {
		"PLUS", "MINUS", "MULT", "DIV", "RPAREN", "LPAREN", "INT", "NEWLINE", "WS"
	};

	public CalculatorLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Calculator.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 8: 
			WS_action((RuleContext)_localctx, actionIndex); 
			break;
		}
	}
	
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: 
			skip(); 
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\13\62\b\1\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3"+
		"\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\6\b#\n\b\r\b\16\b$\3\t"+
		"\5\t(\n\t\3\t\3\t\3\n\6\n-\n\n\r\n\16\n.\3\n\3\n\2\2\13\3\3\5\4\7\5\t"+
		"\6\13\7\r\b\17\t\21\n\23\13\3\2\3\5\2\13\f\17\17\"\"\64\2\3\3\2\2\2\2"+
		"\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2"+
		"\2\2\21\3\2\2\2\2\23\3\2\2\2\3\25\3\2\2\2\5\27\3\2\2\2\7\31\3\2\2\2\t"+
		"\33\3\2\2\2\13\35\3\2\2\2\r\37\3\2\2\2\17\"\3\2\2\2\21\'\3\2\2\2\23,\3"+
		"\2\2\2\25\26\7-\2\2\26\4\3\2\2\2\27\30\7/\2\2\30\6\3\2\2\2\31\32\7,\2"+
		"\2\32\b\3\2\2\2\33\34\7\61\2\2\34\n\3\2\2\2\35\36\7+\2\2\36\f\3\2\2\2"+
		"\37 \7*\2\2 \16\3\2\2\2!#\4\62;\2\"!\3\2\2\2#$\3\2\2\2$\"\3\2\2\2$%\3"+
		"\2\2\2%\20\3\2\2\2&(\7\17\2\2\'&\3\2\2\2\'(\3\2\2\2()\3\2\2\2)*\7\f\2"+
		"\2*\22\3\2\2\2+-\t\2\2\2,+\3\2\2\2-.\3\2\2\2.,\3\2\2\2./\3\2\2\2/\60\3"+
		"\2\2\2\60\61\b\n\2\2\61\24\3\2\2\2\6\2$\'.\3\3\n\2";
	
	public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
