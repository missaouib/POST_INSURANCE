package com.sendtend.utils.CalculatorHelper;

import java.util.List;

import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.TerminalNode;

import System.Data.DataRow;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CalculatorParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =	new PredictionContextCache();
	public static final int	PLUS=1, MINUS=2, MULT=3, DIV=4, RPAREN=5, LPAREN=6, INT=7, NEWLINE=8, WS=9;
	public static final String[] tokenNames = {"<INVALID>", "'+'", "'-'", "'*'", "'/'", "')'", "'('", "INT", "NEWLINE", "WS"};
	public static final int	RULE_prog = 0, RULE_stat = 1, RULE_expr = 2, RULE_multExpr = 3, RULE_atom = 4;
	public static final String[] ruleNames = {"prog", "stat", "expr", "multExpr", "atom"};

	@Override
	public String getGrammarFileName() { return "Calculator.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	private DataRow dataRow = null;
	public void setDataRow(DataRow dataRow) {
		this.dataRow = dataRow;
	}

	private double dResult = 0;
	public double getResult() {
		return(dResult);
	}
	
	private boolean bHasError = false;
	public boolean isHasError() {
		return(bHasError);
	}

	public CalculatorParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	
	public static class ProgContext extends ParserRuleContext {
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		@Override public int getRuleIndex() { return RULE_prog; }
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(11); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(10); stat();
				}
				}
				setState(13); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << INT) | (1L << NEWLINE))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatContext extends ParserRuleContext {
		public ExprContext expr;
		public TerminalNode NEWLINE() { return getToken(CalculatorParser.NEWLINE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		
		public StatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		@Override public int getRuleIndex() { return RULE_stat; }
	}

	public final StatContext stat() throws RecognitionException {
		StatContext _localctx = new StatContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_stat);
		try {
			setState(20);
			switch (_input.LA(1)) {
			case LPAREN:
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(15); ((StatContext)_localctx).expr = expr();
				setState(16); match(NEWLINE);
				dResult=((StatContext)_localctx).expr.value;
				}
				break;
			case NEWLINE:
				enterOuterAlt(_localctx, 2);
				{
				setState(19); match(NEWLINE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public double value;
		public MultExprContext a;
		public MultExprContext b;
		public TerminalNode MINUS(int i) {
			return getToken(CalculatorParser.MINUS, i);
		}
		
		public MultExprContext multExpr(int i) {
			return getRuleContext(MultExprContext.class,i);
		}
		
		public List<TerminalNode> MINUS() { return getTokens(CalculatorParser.MINUS); }
		
		public List<MultExprContext> multExpr() {
			return getRuleContexts(MultExprContext.class);
		}
		
		public List<TerminalNode> PLUS() { return getTokens(CalculatorParser.PLUS); }
		
		public TerminalNode PLUS(int i) {
			return getToken(CalculatorParser.PLUS, i);
		}
		
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		@Override public int getRuleIndex() { return RULE_expr; }
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(22); ((ExprContext)_localctx).a = multExpr();
			((ExprContext)_localctx).value =  ((ExprContext)_localctx).a.value;
			setState(34);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PLUS || _la==MINUS) {
				{
				setState(32);
				switch (_input.LA(1)) {
				case PLUS:
					{
					setState(24); match(PLUS);
					setState(25); ((ExprContext)_localctx).b = multExpr();
					_localctx.value+=((ExprContext)_localctx).b.value;
					}
					break;
				case MINUS:
					{
					setState(28); match(MINUS);
					setState(29); ((ExprContext)_localctx).b = multExpr();
					_localctx.value-=((ExprContext)_localctx).b.value;
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(36);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultExprContext extends ParserRuleContext {
		public double value;
		public AtomContext a;
		public AtomContext b;
		public List<TerminalNode> DIV() { return getTokens(CalculatorParser.DIV); }
		
		public AtomContext atom(int i) {
			return getRuleContext(AtomContext.class,i);
		}
		
		public List<TerminalNode> MULT() { return getTokens(CalculatorParser.MULT); }
		
		public TerminalNode DIV(int i) {
			return getToken(CalculatorParser.DIV, i);
		}
		
		public TerminalNode MULT(int i) {
			return getToken(CalculatorParser.MULT, i);
		}
		
		public List<AtomContext> atom() {
			return getRuleContexts(AtomContext.class);
		}
		
		public MultExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		@Override public int getRuleIndex() { return RULE_multExpr; }
	}

	public final MultExprContext multExpr() throws RecognitionException {
		MultExprContext _localctx = new MultExprContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_multExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37); ((MultExprContext)_localctx).a = atom();
			((MultExprContext)_localctx).value =  ((MultExprContext)_localctx).a.value;
			setState(49);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MULT || _la==DIV) {
				{
				setState(47);
				switch (_input.LA(1)) {
				case MULT:
					{
					setState(39); match(MULT);
					setState(40); ((MultExprContext)_localctx).b = atom();
					_localctx.value*=((MultExprContext)_localctx).b.value;
					}
					break;
				case DIV:
					{
					setState(43); match(DIV);
					setState(44); ((MultExprContext)_localctx).b = atom();
					_localctx.value/=((MultExprContext)_localctx).b.value;
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(51);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomContext extends ParserRuleContext {
		public double value;
		public Token INT;
		public ExprContext expr;
		public TerminalNode INT() { return getToken(CalculatorParser.INT, 0); }
		
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		
		public TerminalNode RPAREN() { return getToken(CalculatorParser.RPAREN, 0); }
		
		public TerminalNode LPAREN() { return getToken(CalculatorParser.LPAREN, 0); }
		
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		@Override public int getRuleIndex() { return RULE_atom; }
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_atom);
		try {
			setState(59);
			switch (_input.LA(1)) {
			case INT:
				enterOuterAlt(_localctx, 1);
				{
					setState(52); ((AtomContext)_localctx).INT = match(INT);
	
					try {
						int columnIndex = Integer.parseInt((((AtomContext)_localctx).INT!=null?((AtomContext)_localctx).INT.getText():null)) - 1;
						// 需要处理数值列的值，如“123,345.00”中的逗号分隔符
						String cellValue = "0";
						if(dataRow.getValue(columnIndex) != null && !dataRow.getValue(columnIndex).equals("")) {
							cellValue = dataRow.getValue(columnIndex).toString();
						}
						
						((AtomContext)_localctx).value = Double.parseDouble(cellValue.replace(",", ""));
					} catch(Exception err) {
						((AtomContext)_localctx).value = 0;
						bHasError = true;
					}
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 2);
				{
				setState(54); match(LPAREN);
				setState(55); ((AtomContext)_localctx).expr = expr();
				setState(56); match(RPAREN);
				((AtomContext)_localctx).value =  ((AtomContext)_localctx).expr.value;
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\13@\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\6\2\16\n\2\r\2\16\2\17\3\3\3\3\3\3\3\3"+
		"\3\3\5\3\27\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\7\4#\n\4\f\4\16"+
		"\4&\13\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5\62\n\5\f\5\16\5\65"+
		"\13\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6>\n\6\3\6\2\2\7\2\4\6\b\n\2\2A\2"+
		"\r\3\2\2\2\4\26\3\2\2\2\6\30\3\2\2\2\b\'\3\2\2\2\n=\3\2\2\2\f\16\5\4\3"+
		"\2\r\f\3\2\2\2\16\17\3\2\2\2\17\r\3\2\2\2\17\20\3\2\2\2\20\3\3\2\2\2\21"+
		"\22\5\6\4\2\22\23\7\n\2\2\23\24\b\3\1\2\24\27\3\2\2\2\25\27\7\n\2\2\26"+
		"\21\3\2\2\2\26\25\3\2\2\2\27\5\3\2\2\2\30\31\5\b\5\2\31$\b\4\1\2\32\33"+
		"\7\3\2\2\33\34\5\b\5\2\34\35\b\4\1\2\35#\3\2\2\2\36\37\7\4\2\2\37 \5\b"+
		"\5\2 !\b\4\1\2!#\3\2\2\2\"\32\3\2\2\2\"\36\3\2\2\2#&\3\2\2\2$\"\3\2\2"+
		"\2$%\3\2\2\2%\7\3\2\2\2&$\3\2\2\2\'(\5\n\6\2(\63\b\5\1\2)*\7\5\2\2*+\5"+
		"\n\6\2+,\b\5\1\2,\62\3\2\2\2-.\7\6\2\2./\5\n\6\2/\60\b\5\1\2\60\62\3\2"+
		"\2\2\61)\3\2\2\2\61-\3\2\2\2\62\65\3\2\2\2\63\61\3\2\2\2\63\64\3\2\2\2"+
		"\64\t\3\2\2\2\65\63\3\2\2\2\66\67\7\t\2\2\67>\b\6\1\289\7\b\2\29:\5\6"+
		"\4\2:;\7\7\2\2;<\b\6\1\2<>\3\2\2\2=\66\3\2\2\2=8\3\2\2\2>\13\3\2\2\2\t"+
		"\17\26\"$\61\63=";
	
	public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
