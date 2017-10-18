//### This file created by BYACC 1.8(/Java extension  1.13)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//###           14 Sep 06  -- Keltin Leung-- ReduceListener support, eliminate underflow report in error recovery
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 11 "Parser.y"
package decaf.frontend;

import decaf.tree.Tree;
import decaf.tree.Tree.*;
import decaf.error.*;
import java.util.*;
//#line 25 "Parser.java"
interface ReduceListener {
  public boolean onReduce(String rule);
}




public class Parser
             extends BaseParser
             implements ReduceListener
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

ReduceListener reduceListener = null;
void yyclearin ()       {yychar = (-1);}
void yyerrok ()         {yyerrflag=0;}
void addReduceListener(ReduceListener l) {
  reduceListener = l;}


//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:SemValue
String   yytext;//user variable to return contextual strings
SemValue yyval; //used to return semantic vals from action routines
SemValue yylval;//the 'lval' (result) I got from yylex()
SemValue valstk[] = new SemValue[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new SemValue();
  yylval=new SemValue();
  valptr=-1;
}
final void val_push(SemValue val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    SemValue[] newstack = new SemValue[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final SemValue val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final SemValue val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short VOID=257;
public final static short BOOL=258;
public final static short INT=259;
public final static short STRING=260;
public final static short CLASS=261;
public final static short COMPLEX=262;
public final static short NULL=263;
public final static short EXTENDS=264;
public final static short THIS=265;
public final static short WHILE=266;
public final static short FOR=267;
public final static short IF=268;
public final static short ELSE=269;
public final static short RETURN=270;
public final static short BREAK=271;
public final static short NEW=272;
public final static short PRINT=273;
public final static short READ_INTEGER=274;
public final static short READ_LINE=275;
public final static short LITERAL=276;
public final static short IDENTIFIER=277;
public final static short AND=278;
public final static short OR=279;
public final static short STATIC=280;
public final static short INSTANCEOF=281;
public final static short LESS_EQUAL=282;
public final static short GREATER_EQUAL=283;
public final static short EQUAL=284;
public final static short NOT_EQUAL=285;
public final static short CASE=286;
public final static short SWITCH=287;
public final static short DEFAULT=288;
public final static short DOUBLELEFT=289;
public final static short REPEAT=290;
public final static short UNTIL=291;
public final static short CONTINUE=292;
public final static short SUPER=293;
public final static short DCOPY=294;
public final static short SCOPY=295;
public final static short RE=296;
public final static short IM=297;
public final static short DO=298;
public final static short OD=299;
public final static short UMINUS=300;
public final static short EMPTY=301;
public final static short SwitchStmt=302;
public final static short COMPCAST=303;
public final static short DOBLOCK=304;
public final static short PRINTCOMP=305;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    5,    2,    6,    6,    7,    7,    7,    9,    9,
   10,   10,    8,    8,   11,   12,   12,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   14,   14,   14,   28,   28,   25,   25,   27,   26,
   26,   26,   26,   26,   26,   26,   26,   26,   26,   26,
   26,   26,   26,   26,   26,   26,   26,   26,   26,   26,
   26,   26,   26,   26,   26,   26,   26,   26,   26,   26,
   26,   26,   26,   26,   30,   30,   31,   29,   29,   33,
   33,   16,   17,   20,   23,   21,   32,   34,   34,   36,
   35,   24,   37,   37,   38,   15,   39,   39,   18,   18,
   19,   22,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    1,
    2,    3,    6,    2,    0,    2,    2,    0,    1,    0,
    3,    1,    7,    6,    3,    2,    0,    1,    2,    1,
    1,    1,    2,    2,    2,    2,    2,    1,    1,    1,
    2,    3,    1,    0,    2,    0,    2,    4,    5,    1,
    1,    1,    1,    1,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    2,    2,
    3,    3,    1,    4,    5,    6,    5,    3,    5,    4,
    4,    2,    2,    2,    1,    1,    1,    1,    0,    3,
    1,    5,    9,    1,    6,    1,    8,    2,    0,    4,
    4,    3,    3,    1,    3,    6,    2,    0,    2,    1,
    4,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   14,   18,
    0,    7,    8,    6,    9,    0,   10,    0,   13,   16,
    0,    0,   17,   11,    0,    4,    0,    0,    0,    0,
   12,    0,   22,    0,    0,    0,    0,    5,    0,    0,
    0,   27,   24,   21,   23,    0,   86,   73,    0,    0,
    0,    0,   94,    0,    0,    0,    0,   85,    0,    0,
    0,    0,   25,    0,    0,   96,   87,    0,    0,    0,
    0,    0,   39,    0,    0,   28,   40,   26,    0,   30,
   31,   32,    0,    0,    0,    0,    0,   38,    0,    0,
    0,    0,    0,   52,   53,   54,    0,    0,    0,   50,
    0,   51,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  104,    0,    0,   29,   33,   34,   35,   36,   37,   41,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   45,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   71,   72,    0,    0,
   68,    0,    0,    0,    0,    0,  102,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   74,    0,    0,  111,    0,    0,    0,    0,   80,   81,
  105,  103,  112,   48,    0,    0,    0,   92,    0,    0,
   75,    0,    0,   77,   99,    0,    0,   49,    0,    0,
  106,   76,    0,   95,    0,  107,    0,    0,    0,   98,
    0,    0,    0,   97,   93,    0,    0,  101,  100,
};
final static short yydgoto[] = {                          2,
    3,    4,   76,   21,   34,    8,   11,   23,   35,   36,
   77,   46,   78,   79,   80,   81,   82,   83,   84,   85,
   86,   87,   88,   89,  100,   91,  102,   93,  206,   94,
   95,   96,  156,  223,  229,  230,  120,  121,  221,
};
final static short yysindex[] = {                      -234,
 -239,    0, -234,    0, -227,    0, -219,  -55,    0,    0,
  368,    0,    0,    0,    0, -205,    0, -136,    0,    0,
   23,  -88,    0,    0,  -87,    0,   43,   -6,   55, -136,
    0, -136,    0,  -83,   60,   52,   66,    0,  -15, -136,
  -15,    0,    0,    0,    0,   16,    0,    0,   69,   71,
   73,  178,    0, -124,   76,   77,   78,    0,   90,  178,
  178,  110,    0,   92,   65,    0,    0,  100,  102,  178,
  178,  178,    0,  178,  107,    0,    0,    0,   56,    0,
    0,    0,   89,   95,   97,  104,  106,    0,  111,   45,
 1193,    0, -128,    0,    0,    0,  178,  178,  178,    0,
 1193,    0,  119,   80,  178,  128,  131,  178,  -27,  -27,
 -126,  576,  178, -118,  178,  178, 1193, 1193,  600, -247,
    0, 1193,  178,    0,    0,    0,    0,    0,    0,    0,
  178,  178,  178,  178,  178,  178,  178,  178,  178,  178,
  178,  178,  178,  178,    0,  178,  178,  178,  134,  627,
  117,  651,  138,  152, 1193,  -23,    0,    0,  804,  142,
    0,  855,  151,  902,  914,   65,    0,  178,   47, 1193,
 1065, 1262,    8,    8,  -32,  -32,   29,   29,  -27,  -27,
  -27,    8,    8,  926,  938,  115,  178,   65,  178,   65,
    0,  965,  178,    0,  -84,  178,   72,  178,    0,    0,
    0,    0,    0,    0,  178,  155,  156,    0,  989,  -70,
    0, 1193,  162,    0,    0, 1032,  115,    0,  178,   65,
    0,    0, -240,    0,  163,    0,  147,  149,   83,    0,
   65,  178,  178,    0,    0, 1054, 1104,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  210,    0,   91,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  153,    0,    0,  172,
    0,  172,    0,    0,    0,  174,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -58,    0,    0,    0,    0,
    0,  -57,    0,    0,    0,    0,    0,    0,    0,  -60,
  -60,  -60,    0,    0,  -58,    0,    0,    0,    0,  -60,
  -60,  -60,    0,  -60,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, 1240,
    0,  549,    0,    0,    0,    0,  -60,  -58,  -60,    0,
  187,    0,    0,    0,  -60,    0,    0,  -60,  468,  477,
    0,    0,  -60,    0,  -60,  -60,  -19,   19,    0,    0,
    0,   21,  -60,    0,    0,    0,    0,    0,    0,    0,
  -60,  -60,  -60,  -60,  -60,  -60,  -60,  -60,  -60,  -60,
  -60,  -60,  -60,  -60,    0,  -60,  -60,  -60,  441,    0,
    0,    0,    0,  -60,   53,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -58,    0,  -60,    0,  -17,
  -24,  -12,  591,  642,   26,  123, 1301, 1354,  504,  513,
  540, 1201, 1314,    0,    0,   87,  -25,  -58,  -60,  -58,
    0,    0,  -60,    0,    0,  -60,    0,  -60,    0,    0,
    0,    0,    0,    0,  -60,    0,  180,    0,    0,  -33,
    0,   59,    0,    0,    0,    0,  143,    0,    3,  -58,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -58,  -60,  -60,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  219,  220,    1,   75,    0,    0,    0,  215,    0,
   63,    0,  -22,  -92,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  826, 1545, 1215,    0,    0,   33,
    0,    0, -114,    0,    0,    0,    0,   81,    0,
};
final static int YYTABLESIZE=1778;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        108,
   44,  110,   28,   28,  142,  151,  108,   28,  169,  140,
  138,  108,  139,  145,  141,   89,   66,  194,  145,   66,
  193,   82,   47,   42,   82,  108,    1,  144,   67,  143,
   33,   67,   33,   66,   66,   58,    7,    5,   82,   82,
   44,   42,  114,   44,  142,   67,   67,  227,   61,  140,
  138,  167,  139,  145,  141,   62,  168,    9,  146,   83,
   60,   84,   83,  146,   84,  142,   60,   10,   66,   60,
  140,   24,  207,   82,  145,  141,   83,   83,   84,   84,
   67,   26,   30,   60,   60,   22,   31,  203,   60,  108,
  193,  108,   25,   91,   32,   40,   91,   61,  146,   90,
   39,   43,   90,   45,   62,  131,   41,   42,   97,   60,
   98,   83,   99,   84,  124,  105,  106,  107,   60,  146,
   12,   13,   14,   15,   16,   17,  225,   78,  104,  108,
   78,  113,   12,   13,   14,   15,   16,   17,   42,  115,
   63,  116,   61,  201,   78,   78,  123,  125,  149,   62,
  160,  142,  103,  126,   60,  127,  140,  138,  153,  139,
  145,  141,  128,   61,  129,  208,   61,  210,  157,  130,
  154,  158,  163,  187,  144,  189,  143,  147,  191,   78,
   61,   61,  196,   79,   61,   61,   79,   42,   27,   29,
  198,   62,  213,   38,  215,  218,   60,  226,  220,  193,
   79,   79,  222,  231,  232,  146,  233,  234,  235,    1,
   61,    5,   20,   15,   19,   61,   46,   62,   46,   46,
   88,    6,   60,  108,  108,  108,  108,  108,  108,  108,
   20,  108,  108,  108,  108,   79,  108,  108,  108,  108,
  108,  108,  108,  108,   31,  109,   37,  108,  202,  134,
  135,   46,  108,   66,   66,  228,  108,  108,  108,  108,
  108,  108,  108,  108,  108,  108,   67,    0,  108,  108,
  108,  108,   12,   13,   14,   15,   16,   17,   47,   46,
   48,   49,   50,   51,    0,   52,   53,   54,   55,   56,
   57,   58,    0,    0,    0,    0,   59,    0,    0,    0,
    0,   64,    0,   60,   60,   65,    0,   66,   67,   68,
   69,   70,   71,   72,   60,    0,    0,   73,   74,    0,
   75,   12,   13,   14,   15,   16,   17,   47,    0,   48,
   49,   50,   51,    0,   52,   53,   54,   55,   56,   57,
   58,    0,    0,    0,    0,   59,    0,    0,    0,    0,
   64,    0,    0,    0,   65,    0,   66,   67,   68,   69,
   70,   71,   72,    0,   78,   78,   73,   74,    0,   75,
  111,    0,   47,    0,   48,   78,    0,    0,    0,    0,
    0,   54,    0,   56,   57,   58,    0,    0,    0,    0,
   59,    0,    0,    0,    0,   64,  134,  135,  136,  137,
   61,   61,   67,   68,   69,   70,   71,    0,    0,    0,
    0,   61,   74,    0,   47,    0,   48,    0,    0,    0,
   79,   79,    0,   54,    0,   56,   57,   58,    0,    0,
    0,   79,   59,    0,    0,    0,    0,   64,    0,    0,
   47,    0,   48,    0,   67,   68,   69,   70,   71,   54,
    0,   56,   57,   58,   74,    0,    0,    0,   59,    0,
    0,    0,    0,   64,    0,    0,    0,    0,    0,    0,
   67,   68,   69,   70,   71,    0,    0,   47,    0,    0,
   74,   47,   47,   47,   47,   47,   47,   47,    0,    0,
    0,    0,   19,    0,    0,    0,    0,    0,   47,   47,
   47,   47,   47,   47,   69,    0,    0,    0,   69,   69,
   69,   69,   69,   70,   69,    0,    0,   70,   70,   70,
   70,   70,    0,   70,    0,   69,   69,   69,    0,   69,
   69,   47,    0,   47,   70,   70,   70,    0,   70,   70,
   57,    0,    0,    0,   57,   57,   57,   57,   57,   58,
   57,    0,    0,   58,   58,   58,   58,   58,    0,   58,
   69,   57,   57,   57,    0,   57,   57,    0,    0,   70,
   58,   58,   58,    0,   58,   58,   59,    0,    0,    0,
   59,   59,   59,   59,   59,   51,   59,    0,    0,   43,
   51,   51,    0,   51,   51,   51,   57,   59,   59,   59,
    0,   59,   59,    0,    0,   58,    0,   43,   51,    0,
   51,   51,  142,    0,    0,    0,  161,  140,  138,    0,
  139,  145,  141,    0,   12,   13,   14,   15,   16,   17,
    0,   64,   59,    0,   64,  144,  142,  143,  147,   51,
    0,  140,  138,    0,  139,  145,  141,   18,   64,   64,
    0,    0,    0,   64,    0,    0,    0,  166,    0,  144,
    0,  143,  147,  142,    0,    0,  146,  188,  140,  138,
    0,  139,  145,  141,    0,    0,    0,    0,    0,    0,
    0,    0,   65,   64,    0,   65,  144,  142,  143,  147,
  146,  190,  140,  138,    0,  139,  145,  141,    0,   65,
   65,    0,    0,    0,   65,    0,    0,    0,    0,    0,
  144,    0,  143,  147,    0,    0,    0,  146,   47,   47,
    0,    0,   47,   47,   47,   47,    0,    0,    0,   47,
    0,    0,    0,    0,   65,    0,    0,    0,    0,    0,
    0,  146,    0,    0,    0,   69,   69,    0,    0,   69,
   69,   69,   69,    0,   70,   70,   69,    0,   70,   70,
   70,   70,    0,    0,    0,   70,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   57,   57,    0,    0,   57,   57,   57,   57,    0,
   58,   58,   57,    0,   58,   58,   58,   58,    0,    0,
    0,   58,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   59,   59,    0,
    0,   59,   59,   59,   59,    0,   51,   51,   59,    0,
   51,   51,   51,   51,    0,    0,    0,   51,    0,    0,
  142,    0,    0,    0,    0,  140,  138,  195,  139,  145,
  141,    0,    0,  132,  133,    0,    0,  134,  135,  136,
  137,    0,    0,  144,  148,  143,  147,    0,   64,   64,
    0,   90,    0,    0,   64,   64,    0,  132,  133,   64,
    0,  134,  135,  136,  137,    0,    0,    0,  148,    0,
   90,  142,    0,    0,  146,  197,  140,  138,    0,  139,
  145,  141,    0,    0,  132,  133,    0,    0,  134,  135,
  136,  137,    0,    0,  144,  148,  143,  147,    0,   65,
   65,    0,    0,   90,    0,   65,   65,    0,  132,  133,
   65,    0,  134,  135,  136,  137,    0,    0,  142,  148,
    0,    0,  199,  140,  138,  146,  139,  145,  141,    0,
  142,    0,    0,    0,  200,  140,  138,    0,  139,  145,
  141,  144,  142,  143,  147,    0,    0,  140,  138,    0,
  139,  145,  141,  144,  142,  143,  147,    0,    0,  140,
  138,    0,  139,  145,  141,  144,    0,  143,  147,    0,
    0,   90,  146,    0,    0,  205,    0,  144,    0,  143,
  147,  142,    0,    0,  146,    0,  140,  138,    0,  139,
  145,  141,    0,   90,    0,   90,  146,    0,  204,    0,
    0,    0,    0,    0,  144,  142,  143,  147,  146,    0,
  140,  138,    0,  139,  145,  141,    0,    0,    0,    0,
    0,    0,    0,    0,   90,   90,    0,  219,  144,    0,
  143,  147,    0,    0,    0,  146,   90,  211,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  142,    0,
    0,    0,  224,  140,  138,    0,  139,  145,  141,  146,
    0,  132,  133,    0,    0,  134,  135,  136,  137,    0,
  142,  144,  148,  143,  147,  140,  138,    0,  139,  145,
  141,  142,    0,    0,    0,    0,  140,  138,    0,  139,
  145,  141,  238,  144,    0,  143,  147,    0,    0,    0,
    0,    0,  146,    0,  144,    0,  143,  147,    0,    0,
    0,    0,  132,  133,    0,    0,  134,  135,  136,  137,
  142,    0,    0,  148,  146,  140,  138,    0,  139,  145,
  141,    0,    0,    0,    0,  146,    0,    0,    0,    0,
    0,    0,  239,  144,    0,  143,  147,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  132,
  133,    0,    0,  134,  135,  136,  137,    0,    0,    0,
  148,  132,  133,    0,  146,  134,  135,  136,  137,    0,
    0,    0,  148,  132,  133,    0,    0,  134,  135,  136,
  137,    0,    0,    0,  148,  132,  133,    0,    0,  134,
  135,  136,  137,    0,    0,    0,  148,    0,    0,  142,
    0,    0,    0,    0,  140,  138,    0,  139,  145,  141,
    0,   63,  132,  133,   63,    0,  134,  135,  136,  137,
    0,    0,  144,  148,  143,  147,    0,    0,   63,   63,
   92,    0,    0,   63,    0,    0,  132,  133,    0,    0,
  134,  135,  136,  137,    0,    0,   50,  148,    0,   92,
    0,   50,   50,  146,   50,   50,   50,    0,    0,    0,
    0,    0,    0,   63,    0,    0,    0,    0,  142,   50,
    0,   50,   50,  140,  138,    0,  139,  145,  141,  132,
  133,    0,   92,  134,  135,  136,  137,    0,    0,    0,
  148,  144,    0,  143,  147,    0,    0,    0,    0,    0,
   50,  132,  133,    0,    0,  134,  135,  136,  137,    0,
    0,   55,  148,   55,   55,   55,  134,  135,  136,  137,
    0,    0,  146,  148,   62,    0,    0,   62,   55,   55,
   55,    0,   55,   55,    0,    0,    0,    0,    0,    0,
    0,   62,   62,    0,    0,    0,   62,    0,    0,    0,
   92,  132,  133,    0,    0,  134,  135,  136,  137,    0,
    0,    0,  148,   55,   56,    0,   56,   56,   56,    0,
    0,    0,   92,    0,   92,    0,   62,    0,    0,    0,
    0,   56,   56,   56,    0,   56,   56,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   92,   92,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   92,   56,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  132,  133,    0,    0,  134,  135,  136,  137,   63,   63,
    0,  148,    0,    0,   63,   63,    0,    0,    0,   63,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   50,   50,    0,
    0,   50,   50,   50,   50,    0,    0,    0,   50,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  132,
    0,    0,    0,  134,  135,  136,  137,    0,    0,    0,
  148,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   55,   55,
    0,    0,   55,   55,   55,   55,    0,    0,    0,   55,
    0,   62,   62,    0,    0,    0,  101,   62,   62,    0,
    0,    0,   62,    0,  109,  110,  112,    0,    0,    0,
    0,    0,    0,    0,  117,  118,  119,    0,  122,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   56,   56,    0,    0,   56,   56,   56,   56,    0,
    0,  150,   56,  152,    0,    0,    0,    0,    0,  155,
    0,    0,  159,    0,    0,    0,    0,  162,    0,  164,
  165,    0,    0,    0,    0,    0,    0,  155,    0,    0,
    0,    0,    0,    0,    0,  170,  171,  172,  173,  174,
  175,  176,  177,  178,  179,  180,  181,  182,  183,    0,
  184,  185,  186,    0,    0,    0,    0,    0,  192,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  119,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  155,    0,  209,    0,    0,    0,  212,    0,    0,
  214,    0,  216,    0,    0,    0,    0,    0,    0,  217,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  236,  237,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   59,   91,   91,   37,   98,   40,   91,  123,   42,
   43,   45,   45,   46,   47,   41,   41,   41,   46,   44,
   44,   41,  263,   41,   44,   59,  261,   60,   41,   62,
   30,   44,   32,   58,   59,  276,  264,  277,   58,   59,
   40,   59,   65,   41,   37,   58,   59,  288,   33,   42,
   43,  299,   45,   46,   47,   40,  304,  277,   91,   41,
   45,   41,   44,   91,   44,   37,   41,  123,   93,   44,
   42,  277,  187,   93,   46,   47,   58,   59,   58,   59,
   93,   59,   40,   58,   59,   11,   93,   41,   63,  123,
   44,  125,   18,   41,   40,   44,   44,   33,   91,   41,
   41,   39,   44,   41,   40,   61,   41,  123,   40,   45,
   40,   93,   40,   93,   59,   40,   40,   40,   93,   91,
  257,  258,  259,  260,  261,  262,  219,   41,   54,   40,
   44,   40,  257,  258,  259,  260,  261,  262,  123,   40,
  125,   40,   33,  166,   58,   59,   40,   59,  277,   40,
  277,   37,  277,   59,   45,   59,   42,   43,   40,   45,
   46,   47,   59,   41,   59,  188,   44,  190,   41,   59,
   91,   41,  291,   40,   60,   59,   62,   63,   41,   93,
   58,   59,   41,   41,   33,   63,   44,  123,  277,  277,
   40,   40,  277,  277,  123,   41,   45,  220,  269,   44,
   58,   59,   41,   41,   58,   91,   58,  125,  231,    0,
   33,   59,   41,  123,   41,   93,  277,   40,  277,  277,
   41,    3,   45,  257,  258,  259,  260,  261,  262,  263,
   11,  265,  266,  267,  268,   93,  270,  271,  272,  273,
  274,  275,  276,  277,   93,   59,   32,  281,  168,  282,
  283,  277,  286,  278,  279,  223,  290,  291,  292,  293,
  294,  295,  296,  297,  298,  299,  279,   -1,  302,  303,
  304,  305,  257,  258,  259,  260,  261,  262,  263,  277,
  265,  266,  267,  268,   -1,  270,  271,  272,  273,  274,
  275,  276,   -1,   -1,   -1,   -1,  281,   -1,   -1,   -1,
   -1,  286,   -1,  278,  279,  290,   -1,  292,  293,  294,
  295,  296,  297,  298,  289,   -1,   -1,  302,  303,   -1,
  305,  257,  258,  259,  260,  261,  262,  263,   -1,  265,
  266,  267,  268,   -1,  270,  271,  272,  273,  274,  275,
  276,   -1,   -1,   -1,   -1,  281,   -1,   -1,   -1,   -1,
  286,   -1,   -1,   -1,  290,   -1,  292,  293,  294,  295,
  296,  297,  298,   -1,  278,  279,  302,  303,   -1,  305,
  261,   -1,  263,   -1,  265,  289,   -1,   -1,   -1,   -1,
   -1,  272,   -1,  274,  275,  276,   -1,   -1,   -1,   -1,
  281,   -1,   -1,   -1,   -1,  286,  282,  283,  284,  285,
  278,  279,  293,  294,  295,  296,  297,   -1,   -1,   -1,
   -1,  289,  303,   -1,  263,   -1,  265,   -1,   -1,   -1,
  278,  279,   -1,  272,   -1,  274,  275,  276,   -1,   -1,
   -1,  289,  281,   -1,   -1,   -1,   -1,  286,   -1,   -1,
  263,   -1,  265,   -1,  293,  294,  295,  296,  297,  272,
   -1,  274,  275,  276,  303,   -1,   -1,   -1,  281,   -1,
   -1,   -1,   -1,  286,   -1,   -1,   -1,   -1,   -1,   -1,
  293,  294,  295,  296,  297,   -1,   -1,   37,   -1,   -1,
  303,   41,   42,   43,   44,   45,   46,   47,   -1,   -1,
   -1,   -1,  125,   -1,   -1,   -1,   -1,   -1,   58,   59,
   60,   61,   62,   63,   37,   -1,   -1,   -1,   41,   42,
   43,   44,   45,   37,   47,   -1,   -1,   41,   42,   43,
   44,   45,   -1,   47,   -1,   58,   59,   60,   -1,   62,
   63,   91,   -1,   93,   58,   59,   60,   -1,   62,   63,
   37,   -1,   -1,   -1,   41,   42,   43,   44,   45,   37,
   47,   -1,   -1,   41,   42,   43,   44,   45,   -1,   47,
   93,   58,   59,   60,   -1,   62,   63,   -1,   -1,   93,
   58,   59,   60,   -1,   62,   63,   37,   -1,   -1,   -1,
   41,   42,   43,   44,   45,   37,   47,   -1,   -1,   41,
   42,   43,   -1,   45,   46,   47,   93,   58,   59,   60,
   -1,   62,   63,   -1,   -1,   93,   -1,   59,   60,   -1,
   62,   63,   37,   -1,   -1,   -1,   41,   42,   43,   -1,
   45,   46,   47,   -1,  257,  258,  259,  260,  261,  262,
   -1,   41,   93,   -1,   44,   60,   37,   62,   63,   91,
   -1,   42,   43,   -1,   45,   46,   47,  280,   58,   59,
   -1,   -1,   -1,   63,   -1,   -1,   -1,   58,   -1,   60,
   -1,   62,   63,   37,   -1,   -1,   91,   41,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   41,   93,   -1,   44,   60,   37,   62,   63,
   91,   41,   42,   43,   -1,   45,   46,   47,   -1,   58,
   59,   -1,   -1,   -1,   63,   -1,   -1,   -1,   -1,   -1,
   60,   -1,   62,   63,   -1,   -1,   -1,   91,  278,  279,
   -1,   -1,  282,  283,  284,  285,   -1,   -1,   -1,  289,
   -1,   -1,   -1,   -1,   93,   -1,   -1,   -1,   -1,   -1,
   -1,   91,   -1,   -1,   -1,  278,  279,   -1,   -1,  282,
  283,  284,  285,   -1,  278,  279,  289,   -1,  282,  283,
  284,  285,   -1,   -1,   -1,  289,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,
  278,  279,  289,   -1,  282,  283,  284,  285,   -1,   -1,
   -1,  289,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  278,  279,   -1,
   -1,  282,  283,  284,  285,   -1,  278,  279,  289,   -1,
  282,  283,  284,  285,   -1,   -1,   -1,  289,   -1,   -1,
   37,   -1,   -1,   -1,   -1,   42,   43,   44,   45,   46,
   47,   -1,   -1,  278,  279,   -1,   -1,  282,  283,  284,
  285,   -1,   -1,   60,  289,   62,   63,   -1,  278,  279,
   -1,   46,   -1,   -1,  284,  285,   -1,  278,  279,  289,
   -1,  282,  283,  284,  285,   -1,   -1,   -1,  289,   -1,
   65,   37,   -1,   -1,   91,   41,   42,   43,   -1,   45,
   46,   47,   -1,   -1,  278,  279,   -1,   -1,  282,  283,
  284,  285,   -1,   -1,   60,  289,   62,   63,   -1,  278,
  279,   -1,   -1,   98,   -1,  284,  285,   -1,  278,  279,
  289,   -1,  282,  283,  284,  285,   -1,   -1,   37,  289,
   -1,   -1,   41,   42,   43,   91,   45,   46,   47,   -1,
   37,   -1,   -1,   -1,   41,   42,   43,   -1,   45,   46,
   47,   60,   37,   62,   63,   -1,   -1,   42,   43,   -1,
   45,   46,   47,   60,   37,   62,   63,   -1,   -1,   42,
   43,   -1,   45,   46,   47,   60,   -1,   62,   63,   -1,
   -1,  166,   91,   -1,   -1,   58,   -1,   60,   -1,   62,
   63,   37,   -1,   -1,   91,   -1,   42,   43,   -1,   45,
   46,   47,   -1,  188,   -1,  190,   91,   -1,   93,   -1,
   -1,   -1,   -1,   -1,   60,   37,   62,   63,   91,   -1,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  219,  220,   -1,   59,   60,   -1,
   62,   63,   -1,   -1,   -1,   91,  231,   93,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   37,   -1,
   -1,   -1,   41,   42,   43,   -1,   45,   46,   47,   91,
   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,
   37,   60,  289,   62,   63,   42,   43,   -1,   45,   46,
   47,   37,   -1,   -1,   -1,   -1,   42,   43,   -1,   45,
   46,   47,   59,   60,   -1,   62,   63,   -1,   -1,   -1,
   -1,   -1,   91,   -1,   60,   -1,   62,   63,   -1,   -1,
   -1,   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,
   37,   -1,   -1,  289,   91,   42,   43,   -1,   45,   46,
   47,   -1,   -1,   -1,   -1,   91,   -1,   -1,   -1,   -1,
   -1,   -1,   59,   60,   -1,   62,   63,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  278,
  279,   -1,   -1,  282,  283,  284,  285,   -1,   -1,   -1,
  289,  278,  279,   -1,   91,  282,  283,  284,  285,   -1,
   -1,   -1,  289,  278,  279,   -1,   -1,  282,  283,  284,
  285,   -1,   -1,   -1,  289,  278,  279,   -1,   -1,  282,
  283,  284,  285,   -1,   -1,   -1,  289,   -1,   -1,   37,
   -1,   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,
   -1,   41,  278,  279,   44,   -1,  282,  283,  284,  285,
   -1,   -1,   60,  289,   62,   63,   -1,   -1,   58,   59,
   46,   -1,   -1,   63,   -1,   -1,  278,  279,   -1,   -1,
  282,  283,  284,  285,   -1,   -1,   37,  289,   -1,   65,
   -1,   42,   43,   91,   45,   46,   47,   -1,   -1,   -1,
   -1,   -1,   -1,   93,   -1,   -1,   -1,   -1,   37,   60,
   -1,   62,   63,   42,   43,   -1,   45,   46,   47,  278,
  279,   -1,   98,  282,  283,  284,  285,   -1,   -1,   -1,
  289,   60,   -1,   62,   63,   -1,   -1,   -1,   -1,   -1,
   91,  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,
   -1,   41,  289,   43,   44,   45,  282,  283,  284,  285,
   -1,   -1,   91,  289,   41,   -1,   -1,   44,   58,   59,
   60,   -1,   62,   63,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   58,   59,   -1,   -1,   -1,   63,   -1,   -1,   -1,
  166,  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,
   -1,   -1,  289,   93,   41,   -1,   43,   44,   45,   -1,
   -1,   -1,  188,   -1,  190,   -1,   93,   -1,   -1,   -1,
   -1,   58,   59,   60,   -1,   62,   63,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  219,  220,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  231,   93,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  278,  279,   -1,   -1,  282,  283,  284,  285,  278,  279,
   -1,  289,   -1,   -1,  284,  285,   -1,   -1,   -1,  289,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  278,  279,   -1,
   -1,  282,  283,  284,  285,   -1,   -1,   -1,  289,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  278,
   -1,   -1,   -1,  282,  283,  284,  285,   -1,   -1,   -1,
  289,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  278,  279,
   -1,   -1,  282,  283,  284,  285,   -1,   -1,   -1,  289,
   -1,  278,  279,   -1,   -1,   -1,   52,  284,  285,   -1,
   -1,   -1,  289,   -1,   60,   61,   62,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   70,   71,   72,   -1,   74,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,
   -1,   97,  289,   99,   -1,   -1,   -1,   -1,   -1,  105,
   -1,   -1,  108,   -1,   -1,   -1,   -1,  113,   -1,  115,
  116,   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  131,  132,  133,  134,  135,
  136,  137,  138,  139,  140,  141,  142,  143,  144,   -1,
  146,  147,  148,   -1,   -1,   -1,   -1,   -1,  154,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  168,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  187,   -1,  189,   -1,   -1,   -1,  193,   -1,   -1,
  196,   -1,  198,   -1,   -1,   -1,   -1,   -1,   -1,  205,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  232,  233,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=305;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'","'?'","'@'",null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"VOID","BOOL","INT","STRING",
"CLASS","COMPLEX","NULL","EXTENDS","THIS","WHILE","FOR","IF","ELSE","RETURN",
"BREAK","NEW","PRINT","READ_INTEGER","READ_LINE","LITERAL","IDENTIFIER","AND",
"OR","STATIC","INSTANCEOF","LESS_EQUAL","GREATER_EQUAL","EQUAL","NOT_EQUAL",
"CASE","SWITCH","DEFAULT","DOUBLELEFT","REPEAT","UNTIL","CONTINUE","SUPER",
"DCOPY","SCOPY","RE","IM","DO","OD","UMINUS","EMPTY","SwitchStmt","COMPCAST",
"DOBLOCK","PRINTCOMP",
};
final static String yyrule[] = {
"$accept : Program",
"Program : ClassList",
"ClassList : ClassList ClassDef",
"ClassList : ClassDef",
"VariableDef : Variable ';'",
"Variable : Type IDENTIFIER",
"Type : INT",
"Type : VOID",
"Type : BOOL",
"Type : STRING",
"Type : COMPLEX",
"Type : CLASS IDENTIFIER",
"Type : Type '[' ']'",
"ClassDef : CLASS IDENTIFIER ExtendsClause '{' FieldList '}'",
"ExtendsClause : EXTENDS IDENTIFIER",
"ExtendsClause :",
"FieldList : FieldList VariableDef",
"FieldList : FieldList FunctionDef",
"FieldList :",
"Formals : VariableList",
"Formals :",
"VariableList : VariableList ',' Variable",
"VariableList : Variable",
"FunctionDef : STATIC Type IDENTIFIER '(' Formals ')' StmtBlock",
"FunctionDef : Type IDENTIFIER '(' Formals ')' StmtBlock",
"StmtBlock : '{' StmtList '}'",
"StmtList : StmtList Stmt",
"StmtList :",
"Stmt : VariableDef",
"Stmt : SimpleStmt ';'",
"Stmt : IfStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"Stmt : ReturnStmt ';'",
"Stmt : PrintStmt ';'",
"Stmt : BreakStmt ';'",
"Stmt : ContinueStmt ';'",
"Stmt : PrintCompStmt ';'",
"Stmt : RepeatStmt",
"Stmt : SwitchStmt",
"Stmt : StmtBlock",
"Stmt : Do ';'",
"SimpleStmt : LValue '=' Expr",
"SimpleStmt : Call",
"SimpleStmt :",
"Receiver : Expr '.'",
"Receiver :",
"LValue : Receiver IDENTIFIER",
"LValue : Expr '[' Expr ']'",
"Call : Receiver IDENTIFIER '(' Actuals ')'",
"Expr : LValue",
"Expr : Call",
"Expr : Constant",
"Expr : Super",
"Expr : Cases",
"Expr : Expr '+' Expr",
"Expr : Expr '-' Expr",
"Expr : Expr '*' Expr",
"Expr : Expr '/' Expr",
"Expr : Expr '%' Expr",
"Expr : Expr EQUAL Expr",
"Expr : Expr NOT_EQUAL Expr",
"Expr : Expr '<' Expr",
"Expr : Expr '>' Expr",
"Expr : Expr LESS_EQUAL Expr",
"Expr : Expr GREATER_EQUAL Expr",
"Expr : Expr AND Expr",
"Expr : Expr OR Expr",
"Expr : '(' Expr ')'",
"Expr : '-' Expr",
"Expr : '!' Expr",
"Expr : READ_INTEGER '(' ')'",
"Expr : READ_LINE '(' ')'",
"Expr : THIS",
"Expr : NEW IDENTIFIER '(' ')'",
"Expr : NEW Type '[' Expr ']'",
"Expr : INSTANCEOF '(' Expr ',' IDENTIFIER ')'",
"Expr : '(' CLASS IDENTIFIER ')' Expr",
"Expr : Expr DOUBLELEFT Expr",
"Expr : Expr '?' Expr ':' Expr",
"Expr : DCOPY '(' Expr ')'",
"Expr : SCOPY '(' Expr ')'",
"Expr : RE Expr",
"Expr : IM Expr",
"Expr : COMPCAST Expr",
"Constant : LITERAL",
"Constant : NULL",
"Super : SUPER",
"Actuals : ExprList",
"Actuals :",
"ExprList : ExprList ',' Expr",
"ExprList : Expr",
"WhileStmt : WHILE '(' Expr ')' Stmt",
"ForStmt : FOR '(' SimpleStmt ';' Expr ';' SimpleStmt ')' Stmt",
"BreakStmt : BREAK",
"RepeatStmt : REPEAT Stmt UNTIL '(' Expr ')'",
"ContinueStmt : CONTINUE",
"Cases : CASE '(' Expr ')' '{' CaseStmtList DefaultStmt '}'",
"CaseStmtList : CaseStmtList CaseStmt",
"CaseStmtList :",
"CaseStmt : Constant ':' Expr ';'",
"DefaultStmt : DEFAULT ':' Expr ';'",
"Do : DO DoStmtList OD",
"DoStmtList : DoStmtList DOBLOCK DoStmt",
"DoStmtList : DoStmt",
"DoStmt : Expr ':' Stmt",
"IfStmt : IF '(' Expr ')' Stmt ElseClause",
"ElseClause : ELSE Stmt",
"ElseClause :",
"ReturnStmt : RETURN Expr",
"ReturnStmt : RETURN",
"PrintStmt : PRINT '(' ExprList ')'",
"PrintCompStmt : PRINTCOMP '(' ExprList ')'",
};

//#line 550 "Parser.y"
    
	/**
	 * 打印当前归约所用的语法规则<br>
	 * 请勿修改。
	 */
    public boolean onReduce(String rule) {
		if (rule.startsWith("$$"))
			return false;
		else
			rule = rule.replaceAll(" \\$\\$\\d+", "");

   	    if (rule.endsWith(":"))
    	    System.out.println(rule + " <empty>");
   	    else
			System.out.println(rule);
		return false;
    }
    
    public void diagnose() {
		addReduceListener(this);
		yyparse();
	}
//#line 807 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    //if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      //if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        //if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        //if (yychar < 0)    //it it didn't work/error
        //  {
        //  yychar = 0;      //change it to default string (no -1!)
          //if (yydebug)
          //  yylexdebug(yystate,yychar);
        //  }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        //if (yydebug)
          //debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      //if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0 || valptr<0)   //check for under & overflow here
            {
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            //if (yydebug)
              //debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            //if (yydebug)
              //debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0 || valptr<0)   //check for under & overflow here
              {
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        //if (yydebug)
          //{
          //yys = null;
          //if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          //if (yys == null) yys = "illegal-symbol";
          //debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          //}
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    //if (yydebug)
      //debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    if (reduceListener == null || reduceListener.onReduce(yyrule[yyn])) // if intercepted!
      switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 61 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 67 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 71 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 81 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 87 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 91 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 95 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 99 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 103 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.COMPLEX, val_peek(0).loc);
                	}
break;
case 11:
//#line 107 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 12:
//#line 111 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 13:
//#line 117 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 14:
//#line 123 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 15:
//#line 127 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 16:
//#line 133 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 17:
//#line 137 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 18:
//#line 141 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 20:
//#line 149 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 21:
//#line 156 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 22:
//#line 160 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 23:
//#line 167 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 171 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 25:
//#line 177 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 26:
//#line 183 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 27:
//#line 187 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 28:
//#line 194 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 29:
//#line 199 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 42:
//#line 219 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 43:
//#line 223 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 44:
//#line 227 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 46:
//#line 234 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 47:
//#line 240 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 48:
//#line 247 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 49:
//#line 253 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 50:
//#line 262 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 54:
//#line 269 "Parser.y"
{
                		
                }
break;
case 55:
//#line 273 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 56:
//#line 277 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 57:
//#line 281 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 285 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 289 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 293 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 297 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 301 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 305 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 309 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 313 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 317 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 67:
//#line 321 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 68:
//#line 325 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 69:
//#line 329 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 70:
//#line 333 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 71:
//#line 337 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 72:
//#line 341 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 73:
//#line 345 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 74:
//#line 349 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 75:
//#line 353 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 76:
//#line 357 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 77:
//#line 361 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 78:
//#line 365 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 79:
//#line 369 "Parser.y"
{
                		yyval.expr = new Tree.Triple(Tree.COND, val_peek(4).expr, val_peek(2).expr, val_peek(0).expr, val_peek(3).loc);
                	}
break;
case 80:
//#line 373 "Parser.y"
{
                		yyval.expr = new Tree.Dcopy(val_peek(1).expr, val_peek(1).loc);
                	}
break;
case 81:
//#line 377 "Parser.y"
{
                		yyval.expr = new Tree.Scopy(val_peek(1).expr, val_peek(1).loc);
                	}
break;
case 82:
//#line 381 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.RE, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 83:
//#line 385 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.IM, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 84:
//#line 389 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.COMPCAST, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 85:
//#line 395 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 86:
//#line 399 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 87:
//#line 405 "Parser.y"
{
						yyval.expr = new Tree.Super(val_peek(0).loc);
					}
break;
case 89:
//#line 412 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 90:
//#line 419 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 91:
//#line 423 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 92:
//#line 430 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 93:
//#line 436 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 94:
//#line 442 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 95:
//#line 448 "Parser.y"
{
						yyval.stmt = new Tree.RepeatLoop(val_peek(4).stmt, val_peek(1).expr, val_peek(5).loc);
					}
break;
case 96:
//#line 454 "Parser.y"
{
						yyval.stmt = new Tree.Continue(val_peek(0).loc);
					}
break;
case 97:
//#line 460 "Parser.y"
{
						yyval.expr = new Tree.Switch(val_peek(5).expr, val_peek(2).caselist, val_peek(1).defa, val_peek(7).loc);
					}
break;
case 98:
//#line 466 "Parser.y"
{
                        yyval.caselist.add(val_peek(0).cas);
                    }
break;
case 99:
//#line 470 "Parser.y"
{
                        yyval = new SemValue();
                        yyval.caselist = new ArrayList<Tree.Case>();
                    }
break;
case 100:
//#line 477 "Parser.y"
{
 						yyval.cas = new Tree.Case(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
 					}
break;
case 101:
//#line 483 "Parser.y"
{
                        yyval.defa = new Tree.Default(val_peek(1).expr, val_peek(3).loc);
                    }
break;
case 102:
//#line 488 "Parser.y"
{
						yyval.stmt = new Tree.Doing(val_peek(1).doeslist, val_peek(2).loc);
					}
break;
case 103:
//#line 494 "Parser.y"
{
						yyval.doeslist.add(val_peek(0).does);
					}
break;
case 104:
//#line 498 "Parser.y"
{
                        yyval = new SemValue();
                        yyval.doeslist = new ArrayList<Tree.Do>();
                        yyval.doeslist.add(val_peek(0).does);
                    }
break;
case 105:
//#line 506 "Parser.y"
{
						yyval.does = new Tree.Do(val_peek(2).expr, val_peek(0).stmt, val_peek(2).loc);
					}
break;
case 106:
//#line 512 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 107:
//#line 518 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 108:
//#line 522 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 109:
//#line 528 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 110:
//#line 532 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 111:
//#line 538 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
case 112:
//#line 544 "Parser.y"
{
						yyval.stmt = new Tree.Printcomp(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1529 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    //if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      //if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        //if (yychar<0) yychar=0;  //clean, if necessary
        //if (yydebug)
          //yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      //if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
