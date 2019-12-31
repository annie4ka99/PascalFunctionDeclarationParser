package tests;

import org.junit.Test;
import parser.Parser;

import java.text.ParseException;

import static org.junit.Assert.fail;

public class Tests {

    private final Parser parser = new Parser();

    private void testCorrect(String expression) {
        test(expression, true);
    }

    private void testIncorrect(String expression) {
        test(expression, false);
    }

    private void test(String expression, boolean isCorrect) {
        try {
            System.out.println(expression);
            parser.parse(expression).show();
            if (!isCorrect) fail("incorrect expression parsing completed without errors");
        } catch (ParseException e) {
            for (int i = 0; i < e.getErrorOffset() - 1; ++i) {
                System.out.print(" ");
            }
            System.out.println("^");
            System.out.println(e.getMessage() + " before position " + e.getErrorOffset());
            if (isCorrect) fail("correct expression parsed completed with errors");
        }
        String testSep = "-------------------------------------------------------------------------------------";
        System.out.println(testSep);
    }

    @Test
    public void functionWithoutTypeTest() {
        testIncorrect("function f( var a, b : char; c : integer);");
    }

    @Test
    public void procedureWithTypeTest() {
        testIncorrect("procedure f( var a, b : char; c : integer): single;");
    }

    @Test
    public void wrongParametersDelimiterTest1() {
        testIncorrect("function f( var a, b : char, c : integer);");
    }

    @Test
    public void wrongParametersDelimiterTest2() {
        testIncorrect("function f( var a; b : char; c : integer);");
    }


    @Test
    public void wrongParametersDelimiterTest3() {
        testIncorrect("function f( var a : integer, b : char, c : integer);");
    }

    @Test
    public void wrongFunctionNameTest() {
        testIncorrect("function 1_f( var a : integer, b : char, c : integer);");
    }

    @Test
    public void wrongVariableNameTest() {
        testIncorrect("function f( var a : integer, b- : char, c : integer);");
    }

    @Test
    public void sameVariableNamesTest() {
        testIncorrect("procedure p(var a, b:char; a:boolean);");
    }


    @Test
    public void noSemicolonInTheEndTest() {
        testIncorrect("procedure p(var a, b:char; c:boolean)");
    }

    @Test
    public void unexpectedTokenTest() {
        testIncorrect("procedure p(var a, function:char; c:boolean)");
    }

    @Test
    public void simpleFunctionTest() {
        testCorrect("function f( var a, b : char; c : integer) : byte;");
    }

    @Test
    public void simpleProcedureTest() {
        testCorrect("procedure f( var a, b : char; c : integer);");
    }

    @Test
    public void noArgumentsTest() {
        testCorrect("procedure f();");
        testCorrect("function f():integer;");
    }

    @Test
    public void allTypesTest() {
        testCorrect("procedure p(bool:boolean; c:char; i:integer; b:byte; w:word; sh:short; "
                +"l:long; r:real; s:single; d:double; e:extended);");
    }

    @Test
    public void allTypesVarsTest() {
        testCorrect("procedure p(var bool1, bool2 :boolean; var c1, c2:char; var i1, i2:integer; "
                + "var b1, b2 :byte; var w1, w2:word; var sh1, sh2 :short; "
                +"var l1, l2:long; var r1, r2 :real; var s1, s2:single; var d1, d2:double; var e1, e2:extended);");
    }

    @Test
    public void lotsOfSpacesTest() {
        testCorrect("function   f       (\r      var   a ,  b       \r: char; c     : integer)\r : byte ;  ");
    }

    @Test
    public void rangeTypeTest() {
        testCorrect("function f (var a, b : 1..21; c : 14..23) : 0..5;");
    }

    @Test
    public void rangeTypeIncorrectTest1() {
        testIncorrect("function f (var a, b : 1aa21; c : 14..23):0..5;");
    }

    @Test
    public void rangeTypeIncorrectTest2() {
        testIncorrect("function f (var a, b : 1.21; c : 14..23):0..5;");
    }

    @Test
    public void rangeTypeIncorrectTest3() {
        testIncorrect("function f (var a, b : 1aa21; c : 14..23):00..5;");
    }

}
