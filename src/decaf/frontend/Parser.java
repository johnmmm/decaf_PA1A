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
public final static short UMINUS=298;
public final static short EMPTY=299;
public final static short SwitchStmt=300;
public final static short COMPCAST=301;
public final static short PRINTCOMP=302;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    5,    2,    6,    6,    7,    7,    7,    9,    9,
   10,   10,    8,    8,   11,   12,   12,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   13,   13,
   14,   14,   14,   27,   27,   24,   24,   26,   25,   25,
   25,   25,   25,   25,   25,   25,   25,   25,   25,   25,
   25,   25,   25,   25,   25,   25,   25,   25,   25,   25,
   25,   25,   25,   25,   25,   25,   25,   25,   25,   25,
   25,   25,   25,   29,   29,   30,   28,   28,   32,   32,
   16,   17,   20,   23,   21,   31,   33,   33,   35,   34,
   15,   36,   36,   18,   18,   19,   22,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    1,
    2,    3,    6,    2,    0,    2,    2,    0,    1,    0,
    3,    1,    7,    6,    3,    2,    0,    1,    2,    1,
    1,    1,    2,    2,    2,    2,    2,    1,    1,    1,
    3,    1,    0,    2,    0,    2,    4,    5,    1,    1,
    1,    1,    1,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    2,    2,    3,
    3,    1,    4,    5,    6,    5,    3,    5,    4,    4,
    2,    2,    2,    1,    1,    1,    1,    0,    3,    1,
    5,    9,    1,    6,    1,    8,    2,    0,    4,    4,
    6,    2,    0,    2,    1,    4,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   14,   18,
    0,    7,    8,    6,    9,    0,   10,    0,   13,   16,
    0,    0,   17,   11,    0,    4,    0,    0,    0,    0,
   12,    0,   22,    0,    0,    0,    0,    5,    0,    0,
    0,   27,   24,   21,   23,    0,   85,   72,    0,    0,
    0,    0,   93,    0,    0,    0,    0,   84,    0,    0,
    0,    0,   25,    0,    0,   95,   86,    0,    0,    0,
    0,   39,    0,    0,   28,   40,   26,    0,   30,   31,
   32,    0,    0,    0,    0,    0,   38,    0,    0,    0,
    0,   51,   52,   53,    0,    0,    0,   49,    0,   50,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   29,   33,
   34,   35,   36,   37,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   44,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   70,   71,    0,    0,   67,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   73,    0,    0,  106,    0,    0,    0,    0,   79,
   80,  107,   47,    0,    0,    0,   91,    0,    0,   74,
    0,    0,   76,   98,    0,    0,   48,    0,    0,  101,
   75,    0,   94,    0,  102,    0,    0,    0,   97,    0,
    0,    0,   96,   92,    0,    0,  100,   99,
};
final static short yydgoto[] = {                          2,
    3,    4,   75,   21,   34,    8,   11,   23,   35,   36,
   76,   46,   77,   78,   79,   80,   81,   82,   83,   84,
   85,   86,   87,   98,   89,  100,   91,  195,   92,   93,
   94,  150,  212,  218,  219,  210,
};
final static short yysindex[] = {                      -239,
 -254,    0, -239,    0, -219,    0, -227,  -68,    0,    0,
  -49,    0,    0,    0,    0, -221,    0,  132,    0,    0,
   12,  -83,    0,    0,  -82,    0,   27,  -16,   42,  132,
    0,  132,    0,  -77,   46,   39,   48,    0,  -29,  132,
  -29,    0,    0,    0,    0,   13,    0,    0,   55,   57,
   58,  257,    0,  240,   59,   61,   63,    0,   64,  257,
  257,  102,    0,   66,   60,    0,    0,   74,   76,  257,
  257,    0,  257,   77,    0,    0,    0,   62,    0,    0,
    0,   67,   71,   75,   78,   80,    0,   79, 1153,    0,
 -159,    0,    0,    0,  257,  257,  257,    0, 1153,    0,
   84,   28,  257,  104,  110,  257,  -43,  -43, -134,  590,
  257, -139,  257,  257, 1153, 1153, 1153,  257,    0,    0,
    0,    0,    0,    0,  257,  257,  257,  257,  257,  257,
  257,  257,  257,  257,  257,  257,  257,  257,    0,  257,
  257,  257,  113,  617,   95,  677,  114,  152, 1153,  -23,
    0,    0,  699,  116,    0,  729,  120,  818,  845,    8,
 1153, 1187,   86,  -18,  -18,   65,   65,   -5,   -5,  -43,
  -43,  -43,  -18,  -18,  877,  957,  159,  257,   60,  257,
   60,    0,  979,  257,    0, -115,  257,   44,  257,    0,
    0,    0,    0,  257,  124,  131,    0,  991,  -98,    0,
 1153,  135,    0,    0, 1015,  159,    0,  257,   60,    0,
    0, -204,    0,  137,    0,  121,  126,   73,    0,   60,
  257,  257,    0,    0, 1073, 1126,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  182,    0,   68,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  129,    0,    0,  158,
    0,  158,    0,    0,    0,  162,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -57,    0,    0,    0,    0,
    0,  -54,    0,    0,    0,    0,    0,    0,    0,  -70,
  -70,  -70,    0,    0,  -57,    0,    0,    0,    0,  -70,
  -70,    0,  -70,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, 1165,    0,  127,
    0,    0,    0,    0,  -70,  -57,  -70,    0,  155,    0,
    0,    0,  -70,    0,    0,  -70,  449,  518,    0,    0,
  -70,    0,  -70,  -70,    3,   16,  100,  -70,    0,    0,
    0,    0,    0,    0,  -70,  -70,  -70,  -70,  -70,  -70,
  -70,  -70,  -70,  -70,  -70,  -70,  -70,  -70,    0,  -70,
  -70,  -70,  413,    0,    0,    0,    0,  -70,   10,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -26,  122,  -28, 1032, 1268,  -24,   22,  421, 1243,  527,
  554,  563, 1281, 1301,    0,    0,  905,  -31,  -57,  -70,
  -57,    0,    0,  -70,    0,    0,  -70,    0,  -70,    0,
    0,    0,    0,  -70,    0,  175,    0,    0,  -33,    0,
   47,    0,    0,    0,    0,  988,    0,  -30,  -57,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -57,
  -70,  -70,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  214,  225,   38,   25,    0,    0,    0,  217,    0,
   -1,    0,  -59,  -95,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  573, 1478,  605,    0,    0,   40,    0,
    0, -114,    0,    0,    0,    0,
};
final static int YYTABLESIZE=1700;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        103,
  145,   43,  139,  160,  105,  112,  103,   28,   28,   88,
   43,  103,   66,   28,   41,   66,   59,  185,  136,   59,
  184,    1,    5,  134,  132,  103,  133,  139,  135,   66,
   66,  136,   41,   59,   59,   22,  134,   43,   59,   45,
  139,  135,   25,   81,    7,   61,   81,  140,  192,    9,
   90,  184,   62,   90,   10,   24,   82,   60,   47,   82,
   81,   81,   60,  196,   66,   60,   30,   33,   59,   33,
   26,   58,  140,   82,   82,   19,   31,   44,  102,   60,
   60,   32,   40,  216,   60,  140,   39,   89,   41,  103,
   89,  103,   61,   42,   95,   81,   96,   97,  103,   62,
  104,  136,  105,  106,   60,  111,  134,  132,   82,  133,
  139,  135,  214,  113,   60,  114,  118,  143,  148,  197,
  119,  199,  136,  147,  138,  120,  137,  134,  132,  121,
  133,  139,  135,  122,   61,   42,  123,   63,  124,  125,
   83,   62,  154,   83,  151,  138,   60,  137,  141,  215,
  152,  157,  178,  180,  182,  140,  187,   83,   83,  189,
  224,  202,   65,   50,  207,   65,  204,   42,   50,   50,
  209,   50,   50,   50,  184,  211,  140,  220,  221,   65,
   65,    1,   42,  222,   61,   42,   50,    5,   50,   50,
   15,   62,   83,   27,   29,  136,   60,  223,   20,   38,
  134,  132,   19,  133,  139,  135,   45,   12,   13,   14,
   15,   16,   17,  104,   65,   87,    6,   50,  138,   45,
  137,  141,   45,  103,  103,  103,  103,  103,  103,  103,
   18,  103,  103,  103,  103,   20,  103,  103,  103,  103,
  103,  103,  103,  103,   31,   45,   45,  103,   37,  140,
   66,  217,  103,   59,   59,    0,  103,  103,  103,  103,
  103,  103,  103,  103,   59,    0,  103,  103,  103,   12,
   13,   14,   15,   16,   17,   47,    0,   48,   49,   50,
   51,    0,   52,   53,   54,   55,   56,   57,   58,   61,
    0,    0,    0,   59,    0,    0,   62,    0,   64,   60,
   60,   60,   65,    0,   66,   67,   68,   69,   70,   71,
   60,    0,   72,   73,   74,    0,   12,   13,   14,   15,
   16,   17,   47,    0,   48,   49,   50,   51,    0,   52,
   53,   54,   55,   56,   57,   58,    0,    0,    0,    0,
   59,    0,    0,    0,    0,   64,  128,  129,    0,   65,
    0,   66,   67,   68,   69,   70,   71,    0,    0,   72,
   73,   74,  109,  126,   47,    0,   48,  128,  129,  130,
  131,    0,    0,   54,  142,   56,   57,   58,    0,    0,
    0,    0,   59,    0,    0,    0,    0,   64,   12,   13,
   14,   15,   16,   17,   67,   68,   69,   70,   71,   65,
   65,    0,   73,    0,   50,   50,    0,    0,   50,   50,
   50,   50,    0,    0,   47,   50,   48,    0,    0,    0,
    0,    0,    0,   54,    0,   56,   57,   58,    0,    0,
    0,    0,   59,    0,    0,    0,    0,   64,    0,    0,
  128,  129,  130,  131,   67,   68,   69,   70,   71,   46,
    0,    0,   73,   46,   46,   46,   46,   46,   46,   46,
    0,   54,    0,   54,   54,   54,    0,    0,    0,    0,
   46,   46,   46,   46,   46,   46,    0,    0,   54,   54,
   54,    0,   54,   54,    0,   68,    0,    0,    0,   68,
   68,   68,   68,   68,    0,   68,   12,   13,   14,   15,
   16,   17,    0,   46,    0,   46,   68,   68,   68,    0,
   68,   68,    0,   54,    0,    0,  101,    0,    0,   47,
    0,   48,    0,    0,    0,    0,    0,    0,   54,    0,
   56,   57,   58,    0,    0,    0,    0,   59,    0,    0,
    0,   68,   64,    0,    0,    0,    0,    0,    0,   67,
   68,   69,   70,   71,   69,    0,    0,   73,   69,   69,
   69,   69,   69,   56,   69,    0,    0,   56,   56,   56,
   56,   56,    0,   56,    0,   69,   69,   69,    0,   69,
   69,    0,    0,    0,   56,   56,   56,    0,   56,   56,
   57,    0,    0,    0,   57,   57,   57,   57,   57,   58,
   57,    0,    0,   58,   58,   58,   58,   58,    0,   58,
   69,   57,   57,   57,    0,   57,   57,    0,   88,   56,
   58,   58,   58,    0,   58,   58,  136,    0,    0,    0,
  155,  134,  132,    0,  133,  139,  135,   88,    0,    0,
    0,    0,    0,    0,    0,    0,   57,    0,    0,  138,
   90,  137,  141,  136,    0,   58,    0,  179,  134,  132,
    0,  133,  139,  135,    0,    0,    0,    0,   88,   90,
    0,    0,    0,    0,    0,    0,  138,    0,  137,  141,
  140,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   46,   46,    0,    0,   46,   46,   46,   46,   54,   54,
   90,   46,   54,   54,   54,   54,    0,  140,    0,   54,
    0,    0,    0,  136,    0,    0,    0,  181,  134,  132,
    0,  133,  139,  135,    0,    0,   68,   68,    0,    0,
   68,   68,   68,   68,    0,  136,  138,   68,  137,  141,
  134,  132,  186,  133,  139,  135,    0,    0,    0,    0,
    0,   88,    0,   88,    0,    0,    0,    0,  138,    0,
  137,  141,    0,    0,    0,  136,    0,  140,    0,  188,
  134,  132,    0,  133,  139,  135,    0,    0,    0,    0,
   88,   88,    0,   90,    0,   90,    0,    0,  138,  140,
  137,  141,   88,    0,    0,   69,   69,    0,    0,   69,
   69,   69,   69,    0,   56,   56,   69,    0,   56,   56,
   56,   56,   90,   90,    0,   56,    0,    0,    0,  140,
    0,    0,    0,    0,   90,    0,    0,    0,    0,    0,
    0,   57,   57,    0,    0,   57,   57,   57,   57,    0,
   58,   58,   57,    0,   58,   58,   58,   58,    0,    0,
    0,   58,    0,    0,  136,    0,    0,    0,  190,  134,
  132,    0,  133,  139,  135,    0,    0,  126,  127,    0,
    0,  128,  129,  130,  131,    0,    0,  138,  142,  137,
  141,  136,    0,    0,    0,  191,  134,  132,    0,  133,
  139,  135,    0,    0,  126,  127,    0,    0,  128,  129,
  130,  131,    0,    0,  138,  142,  137,  141,  140,    0,
    0,    0,    0,  136,    0,    0,    0,    0,  134,  132,
    0,  133,  139,  135,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  140,  138,    0,  137,  141,
    0,    0,    0,    0,    0,   77,    0,    0,   77,    0,
    0,    0,    0,    0,  126,  127,    0,    0,  128,  129,
  130,  131,   77,   77,    0,  142,    0,  140,    0,  193,
    0,    0,    0,    0,    0,    0,  126,  127,    0,    0,
  128,  129,  130,  131,    0,    0,    0,  142,    0,    0,
    0,    0,    0,  136,    0,    0,    0,   77,  134,  132,
    0,  133,  139,  135,    0,    0,  126,  127,    0,    0,
  128,  129,  130,  131,  194,  136,  138,  142,  137,  141,
  134,  132,    0,  133,  139,  135,    0,  136,   78,    0,
    0,   78,  134,  132,    0,  133,  139,  135,  138,    0,
  137,  141,    0,    0,    0,   78,   78,  140,    0,  208,
  138,  136,  137,  141,    0,  213,  134,  132,    0,  133,
  139,  135,    0,    0,    0,    0,    0,    0,    0,  140,
    0,  200,   63,    0,  138,   63,  137,  141,    0,    0,
   78,  140,    0,    0,    0,    0,    0,    0,    0,   63,
   63,    0,    0,    0,   63,  126,  127,    0,    0,  128,
  129,  130,  131,    0,    0,  140,  142,    0,    0,  136,
    0,    0,    0,    0,  134,  132,    0,  133,  139,  135,
    0,    0,  126,  127,   63,    0,  128,  129,  130,  131,
    0,  227,  138,  142,  137,  141,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  126,  127,    0,    0,  128,  129,
  130,  131,  136,  140,    0,  142,    0,  134,  132,    0,
  133,  139,  135,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   77,   77,  228,  138,    0,  137,  141,  136,
    0,    0,    0,   77,  134,  132,    0,  133,  139,  135,
    0,   49,    0,    0,    0,    0,   49,   49,    0,   49,
   49,   49,  138,    0,  137,  141,  140,    0,    0,    0,
    0,    0,    0,  136,   49,    0,   49,   49,  134,  132,
    0,  133,  139,  135,  126,  127,    0,    0,  128,  129,
  130,  131,    0,  140,    0,  142,  138,    0,  137,  141,
    0,    0,    0,    0,    0,   49,  126,  127,    0,    0,
  128,  129,  130,  131,    0,   78,   78,  142,  126,  127,
    0,    0,  128,  129,  130,  131,   78,  140,    0,  142,
    0,    0,    0,   55,    0,   55,   55,   55,    0,    0,
    0,    0,  126,  127,    0,    0,  128,  129,  130,  131,
   55,   55,   55,  142,   55,   55,    0,    0,   64,   63,
   63,   64,    0,    0,    0,   63,   63,    0,    0,    0,
   63,   62,    0,    0,   62,   64,   64,    0,    0,    0,
   64,    0,    0,    0,    0,   55,    0,    0,   62,   62,
    0,   61,    0,   62,   61,    0,    0,    0,    0,    0,
  126,  127,    0,    0,  128,  129,  130,  131,   61,   61,
   64,  142,    0,   61,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   62,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   61,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  126,  127,    0,    0,  128,  129,  130,
  131,    0,    0,    0,  142,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  126,  127,    0,    0,  128,  129,  130,  131,    0,    0,
    0,  142,   49,   49,    0,    0,   49,   49,   49,   49,
    0,    0,    0,   49,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  128,  129,
  130,  131,    0,    0,    0,  142,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   55,   55,    0,    0,   55,   55,   55,   55,    0,   99,
    0,   55,    0,    0,    0,    0,    0,  107,  108,  110,
    0,    0,    0,    0,    0,   64,   64,  115,  116,    0,
  117,   64,   64,    0,    0,    0,   64,    0,   62,   62,
    0,    0,    0,    0,   62,   62,    0,    0,    0,   62,
    0,    0,  144,    0,  146,    0,    0,    0,   61,   61,
  149,    0,    0,  153,   61,   61,    0,    0,  156,   61,
  158,  159,    0,    0,    0,  149,    0,    0,    0,    0,
    0,    0,  161,  162,  163,  164,  165,  166,  167,  168,
  169,  170,  171,  172,  173,  174,    0,  175,  176,  177,
    0,    0,    0,    0,    0,  183,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  149,    0,  198,    0,    0,
    0,  201,    0,    0,  203,    0,  205,    0,    0,    0,
    0,  206,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  225,  226,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   96,   59,   46,  118,   59,   65,   40,   91,   91,   41,
   41,   45,   41,   91,   41,   44,   41,   41,   37,   44,
   44,  261,  277,   42,   43,   59,   45,   46,   47,   58,
   59,   37,   59,   58,   59,   11,   42,   39,   63,   41,
   46,   47,   18,   41,  264,   33,   44,   91,   41,  277,
   41,   44,   40,   44,  123,  277,   41,   45,  263,   44,
   58,   59,   41,  178,   93,   44,   40,   30,   93,   32,
   59,  276,   91,   58,   59,  125,   93,   40,   54,   58,
   59,   40,   44,  288,   63,   91,   41,   41,   41,  123,
   44,  125,   33,  123,   40,   93,   40,   40,   40,   40,
   40,   37,   40,   40,   45,   40,   42,   43,   93,   45,
   46,   47,  208,   40,   93,   40,   40,  277,   91,  179,
   59,  181,   37,   40,   60,   59,   62,   42,   43,   59,
   45,   46,   47,   59,   33,  123,   59,  125,   59,   61,
   41,   40,  277,   44,   41,   60,   45,   62,   63,  209,
   41,  291,   40,   59,   41,   91,   41,   58,   59,   40,
  220,  277,   41,   37,   41,   44,  123,   41,   42,   43,
  269,   45,   46,   47,   44,   41,   91,   41,   58,   58,
   59,    0,  123,   58,   33,   59,   60,   59,   62,   63,
  123,   40,   93,  277,  277,   37,   45,  125,   41,  277,
   42,   43,   41,   45,   46,   47,  277,  257,  258,  259,
  260,  261,  262,   59,   93,   41,    3,   91,   60,  277,
   62,   63,  277,  257,  258,  259,  260,  261,  262,  263,
  280,  265,  266,  267,  268,   11,  270,  271,  272,  273,
  274,  275,  276,  277,   93,  277,  277,  281,   32,   91,
  279,  212,  286,  278,  279,   -1,  290,  291,  292,  293,
  294,  295,  296,  297,  289,   -1,  300,  301,  302,  257,
  258,  259,  260,  261,  262,  263,   -1,  265,  266,  267,
  268,   -1,  270,  271,  272,  273,  274,  275,  276,   33,
   -1,   -1,   -1,  281,   -1,   -1,   40,   -1,  286,  278,
  279,   45,  290,   -1,  292,  293,  294,  295,  296,  297,
  289,   -1,  300,  301,  302,   -1,  257,  258,  259,  260,
  261,  262,  263,   -1,  265,  266,  267,  268,   -1,  270,
  271,  272,  273,  274,  275,  276,   -1,   -1,   -1,   -1,
  281,   -1,   -1,   -1,   -1,  286,  282,  283,   -1,  290,
   -1,  292,  293,  294,  295,  296,  297,   -1,   -1,  300,
  301,  302,  261,  278,  263,   -1,  265,  282,  283,  284,
  285,   -1,   -1,  272,  289,  274,  275,  276,   -1,   -1,
   -1,   -1,  281,   -1,   -1,   -1,   -1,  286,  257,  258,
  259,  260,  261,  262,  293,  294,  295,  296,  297,  278,
  279,   -1,  301,   -1,  278,  279,   -1,   -1,  282,  283,
  284,  285,   -1,   -1,  263,  289,  265,   -1,   -1,   -1,
   -1,   -1,   -1,  272,   -1,  274,  275,  276,   -1,   -1,
   -1,   -1,  281,   -1,   -1,   -1,   -1,  286,   -1,   -1,
  282,  283,  284,  285,  293,  294,  295,  296,  297,   37,
   -1,   -1,  301,   41,   42,   43,   44,   45,   46,   47,
   -1,   41,   -1,   43,   44,   45,   -1,   -1,   -1,   -1,
   58,   59,   60,   61,   62,   63,   -1,   -1,   58,   59,
   60,   -1,   62,   63,   -1,   37,   -1,   -1,   -1,   41,
   42,   43,   44,   45,   -1,   47,  257,  258,  259,  260,
  261,  262,   -1,   91,   -1,   93,   58,   59,   60,   -1,
   62,   63,   -1,   93,   -1,   -1,  277,   -1,   -1,  263,
   -1,  265,   -1,   -1,   -1,   -1,   -1,   -1,  272,   -1,
  274,  275,  276,   -1,   -1,   -1,   -1,  281,   -1,   -1,
   -1,   93,  286,   -1,   -1,   -1,   -1,   -1,   -1,  293,
  294,  295,  296,  297,   37,   -1,   -1,  301,   41,   42,
   43,   44,   45,   37,   47,   -1,   -1,   41,   42,   43,
   44,   45,   -1,   47,   -1,   58,   59,   60,   -1,   62,
   63,   -1,   -1,   -1,   58,   59,   60,   -1,   62,   63,
   37,   -1,   -1,   -1,   41,   42,   43,   44,   45,   37,
   47,   -1,   -1,   41,   42,   43,   44,   45,   -1,   47,
   93,   58,   59,   60,   -1,   62,   63,   -1,   46,   93,
   58,   59,   60,   -1,   62,   63,   37,   -1,   -1,   -1,
   41,   42,   43,   -1,   45,   46,   47,   65,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   93,   -1,   -1,   60,
   46,   62,   63,   37,   -1,   93,   -1,   41,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   96,   65,
   -1,   -1,   -1,   -1,   -1,   -1,   60,   -1,   62,   63,
   91,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  278,  279,   -1,   -1,  282,  283,  284,  285,  278,  279,
   96,  289,  282,  283,  284,  285,   -1,   91,   -1,  289,
   -1,   -1,   -1,   37,   -1,   -1,   -1,   41,   42,   43,
   -1,   45,   46,   47,   -1,   -1,  278,  279,   -1,   -1,
  282,  283,  284,  285,   -1,   37,   60,  289,   62,   63,
   42,   43,   44,   45,   46,   47,   -1,   -1,   -1,   -1,
   -1,  179,   -1,  181,   -1,   -1,   -1,   -1,   60,   -1,
   62,   63,   -1,   -1,   -1,   37,   -1,   91,   -1,   41,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,
  208,  209,   -1,  179,   -1,  181,   -1,   -1,   60,   91,
   62,   63,  220,   -1,   -1,  278,  279,   -1,   -1,  282,
  283,  284,  285,   -1,  278,  279,  289,   -1,  282,  283,
  284,  285,  208,  209,   -1,  289,   -1,   -1,   -1,   91,
   -1,   -1,   -1,   -1,  220,   -1,   -1,   -1,   -1,   -1,
   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,
  278,  279,  289,   -1,  282,  283,  284,  285,   -1,   -1,
   -1,  289,   -1,   -1,   37,   -1,   -1,   -1,   41,   42,
   43,   -1,   45,   46,   47,   -1,   -1,  278,  279,   -1,
   -1,  282,  283,  284,  285,   -1,   -1,   60,  289,   62,
   63,   37,   -1,   -1,   -1,   41,   42,   43,   -1,   45,
   46,   47,   -1,   -1,  278,  279,   -1,   -1,  282,  283,
  284,  285,   -1,   -1,   60,  289,   62,   63,   91,   -1,
   -1,   -1,   -1,   37,   -1,   -1,   -1,   -1,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   91,   60,   -1,   62,   63,
   -1,   -1,   -1,   -1,   -1,   41,   -1,   -1,   44,   -1,
   -1,   -1,   -1,   -1,  278,  279,   -1,   -1,  282,  283,
  284,  285,   58,   59,   -1,  289,   -1,   91,   -1,   93,
   -1,   -1,   -1,   -1,   -1,   -1,  278,  279,   -1,   -1,
  282,  283,  284,  285,   -1,   -1,   -1,  289,   -1,   -1,
   -1,   -1,   -1,   37,   -1,   -1,   -1,   93,   42,   43,
   -1,   45,   46,   47,   -1,   -1,  278,  279,   -1,   -1,
  282,  283,  284,  285,   58,   37,   60,  289,   62,   63,
   42,   43,   -1,   45,   46,   47,   -1,   37,   41,   -1,
   -1,   44,   42,   43,   -1,   45,   46,   47,   60,   -1,
   62,   63,   -1,   -1,   -1,   58,   59,   91,   -1,   59,
   60,   37,   62,   63,   -1,   41,   42,   43,   -1,   45,
   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   91,
   -1,   93,   41,   -1,   60,   44,   62,   63,   -1,   -1,
   93,   91,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   58,
   59,   -1,   -1,   -1,   63,  278,  279,   -1,   -1,  282,
  283,  284,  285,   -1,   -1,   91,  289,   -1,   -1,   37,
   -1,   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,
   -1,   -1,  278,  279,   93,   -1,  282,  283,  284,  285,
   -1,   59,   60,  289,   62,   63,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  278,  279,   -1,   -1,  282,  283,
  284,  285,   37,   91,   -1,  289,   -1,   42,   43,   -1,
   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  278,  279,   59,   60,   -1,   62,   63,   37,
   -1,   -1,   -1,  289,   42,   43,   -1,   45,   46,   47,
   -1,   37,   -1,   -1,   -1,   -1,   42,   43,   -1,   45,
   46,   47,   60,   -1,   62,   63,   91,   -1,   -1,   -1,
   -1,   -1,   -1,   37,   60,   -1,   62,   63,   42,   43,
   -1,   45,   46,   47,  278,  279,   -1,   -1,  282,  283,
  284,  285,   -1,   91,   -1,  289,   60,   -1,   62,   63,
   -1,   -1,   -1,   -1,   -1,   91,  278,  279,   -1,   -1,
  282,  283,  284,  285,   -1,  278,  279,  289,  278,  279,
   -1,   -1,  282,  283,  284,  285,  289,   91,   -1,  289,
   -1,   -1,   -1,   41,   -1,   43,   44,   45,   -1,   -1,
   -1,   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,
   58,   59,   60,  289,   62,   63,   -1,   -1,   41,  278,
  279,   44,   -1,   -1,   -1,  284,  285,   -1,   -1,   -1,
  289,   41,   -1,   -1,   44,   58,   59,   -1,   -1,   -1,
   63,   -1,   -1,   -1,   -1,   93,   -1,   -1,   58,   59,
   -1,   41,   -1,   63,   44,   -1,   -1,   -1,   -1,   -1,
  278,  279,   -1,   -1,  282,  283,  284,  285,   58,   59,
   93,  289,   -1,   63,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   93,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   93,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  278,  279,   -1,   -1,  282,  283,  284,
  285,   -1,   -1,   -1,  289,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,   -1,
   -1,  289,  278,  279,   -1,   -1,  282,  283,  284,  285,
   -1,   -1,   -1,  289,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  282,  283,
  284,  285,   -1,   -1,   -1,  289,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,   52,
   -1,  289,   -1,   -1,   -1,   -1,   -1,   60,   61,   62,
   -1,   -1,   -1,   -1,   -1,  278,  279,   70,   71,   -1,
   73,  284,  285,   -1,   -1,   -1,  289,   -1,  278,  279,
   -1,   -1,   -1,   -1,  284,  285,   -1,   -1,   -1,  289,
   -1,   -1,   95,   -1,   97,   -1,   -1,   -1,  278,  279,
  103,   -1,   -1,  106,  284,  285,   -1,   -1,  111,  289,
  113,  114,   -1,   -1,   -1,  118,   -1,   -1,   -1,   -1,
   -1,   -1,  125,  126,  127,  128,  129,  130,  131,  132,
  133,  134,  135,  136,  137,  138,   -1,  140,  141,  142,
   -1,   -1,   -1,   -1,   -1,  148,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  178,   -1,  180,   -1,   -1,
   -1,  184,   -1,   -1,  187,   -1,  189,   -1,   -1,   -1,
   -1,  194,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  221,  222,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=302;
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
"DCOPY","SCOPY","RE","IM","UMINUS","EMPTY","SwitchStmt","COMPCAST","PRINTCOMP",
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
"IfStmt : IF '(' Expr ')' Stmt ElseClause",
"ElseClause : ELSE Stmt",
"ElseClause :",
"ReturnStmt : RETURN Expr",
"ReturnStmt : RETURN",
"PrintStmt : PRINT '(' ExprList ')'",
"PrintCompStmt : PRINTCOMP '(' ExprList ')'",
};

//#line 524 "Parser.y"
    
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
//#line 777 "Parser.java"
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
//#line 60 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 66 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 70 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 80 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 86 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 90 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 94 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 98 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 102 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.COMPLEX, val_peek(0).loc);
                	}
break;
case 11:
//#line 106 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 12:
//#line 110 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 13:
//#line 116 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 14:
//#line 122 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 15:
//#line 126 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 16:
//#line 132 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 17:
//#line 136 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 18:
//#line 140 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 20:
//#line 148 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 21:
//#line 155 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 22:
//#line 159 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 23:
//#line 166 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 170 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 25:
//#line 176 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 26:
//#line 182 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 27:
//#line 186 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 28:
//#line 193 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 29:
//#line 198 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 41:
//#line 217 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 42:
//#line 221 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 43:
//#line 225 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 45:
//#line 232 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 46:
//#line 238 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 47:
//#line 245 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 48:
//#line 251 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 49:
//#line 260 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 53:
//#line 267 "Parser.y"
{
                		
                }
break;
case 54:
//#line 271 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 55:
//#line 275 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 56:
//#line 279 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 57:
//#line 283 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 287 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 291 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 295 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 299 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 303 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 307 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 311 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 315 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 319 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 67:
//#line 323 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 68:
//#line 327 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 69:
//#line 331 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 70:
//#line 335 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 71:
//#line 339 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 72:
//#line 343 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 73:
//#line 347 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 74:
//#line 351 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 75:
//#line 355 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 76:
//#line 359 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 77:
//#line 363 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 78:
//#line 367 "Parser.y"
{
                		yyval.expr = new Tree.Triple(Tree.COND, val_peek(4).expr, val_peek(2).expr, val_peek(0).expr, val_peek(3).loc);
                	}
break;
case 79:
//#line 371 "Parser.y"
{
                		yyval.expr = new Tree.Dcopy(val_peek(1).expr, val_peek(1).loc);
                	}
break;
case 80:
//#line 375 "Parser.y"
{
                		yyval.expr = new Tree.Scopy(val_peek(1).expr, val_peek(1).loc);
                	}
break;
case 81:
//#line 379 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.RE, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 82:
//#line 383 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.IM, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 83:
//#line 387 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.COMPCAST, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 84:
//#line 393 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 85:
//#line 397 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 86:
//#line 403 "Parser.y"
{
						yyval.expr = new Tree.Super(val_peek(0).loc);
					}
break;
case 88:
//#line 410 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 89:
//#line 417 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 90:
//#line 421 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 91:
//#line 428 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 92:
//#line 434 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 93:
//#line 440 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 94:
//#line 446 "Parser.y"
{
						yyval.stmt = new Tree.RepeatLoop(val_peek(4).stmt, val_peek(1).expr, val_peek(5).loc);
					}
break;
case 95:
//#line 452 "Parser.y"
{
						yyval.stmt = new Tree.Continue(val_peek(0).loc);
					}
break;
case 96:
//#line 458 "Parser.y"
{
						yyval.expr = new Tree.Switch(val_peek(5).expr, val_peek(2).caselist, val_peek(1).defa, val_peek(7).loc);
					}
break;
case 97:
//#line 464 "Parser.y"
{
                        yyval.caselist.add(val_peek(0).cas);
                    }
break;
case 98:
//#line 468 "Parser.y"
{
                        yyval = new SemValue();
                        yyval.caselist = new ArrayList<Tree.Case>();
                    }
break;
case 99:
//#line 475 "Parser.y"
{
 						yyval.cas = new Tree.Case(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
 					}
break;
case 100:
//#line 481 "Parser.y"
{
                        yyval.defa = new Tree.Default(val_peek(1).expr, val_peek(3).loc);
                    }
break;
case 101:
//#line 486 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 102:
//#line 492 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 103:
//#line 496 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 104:
//#line 502 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 105:
//#line 506 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 106:
//#line 512 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
case 107:
//#line 518 "Parser.y"
{
						yyval.stmt = new Tree.Printcomp(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1473 "Parser.java"
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
