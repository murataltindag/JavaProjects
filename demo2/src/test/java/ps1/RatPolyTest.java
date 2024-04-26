package ps1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** This class contains a set of test cases that can be used to 
    test the implementation of the RatPoly class.
    <p>
*/
public class RatPolyTest {
    // get a RatNum for an integer
    private RatNum num(int i) {
        return new RatNum(i);
    }

    //private RatNum nanNum = (new RatNum(1)).div(new RatNum(0));

    // convenient way to make a RatPoly
    private RatPoly poly(int coef, int expt) {
        return new RatPoly(coef, expt);
    }

    // Convenient way to make a quadratic polynomial, arguments
    // are just the coefficients, highest degree term to lowest
    private RatPoly quadPoly(int x2, int x1, int x0) {
        RatPoly ratPoly = new RatPoly(x2, 2);
        return ratPoly.add(poly(x1, 1)).add(poly(x0, 0));
    }

    // convenience for parse
    private RatPoly parse(String s) {
        return RatPoly.parse(s);
    }

    // convenience for zero RatPoly
    private RatPoly zero() {
        return new RatPoly();
    }

    // public RatPolyTest(String name) {
    //     super(name);
    // }

    // only toString is tested here
    private void eq(RatPoly p, String target) {
        String t = p.toString();
        assertEquals(target, t);
    }

    private void eq(RatPoly p, String target, String message) {
        String t = p.toString();
        assertEquals(target, t, message);
    }

    // parses s into p, and then checks that it is as anticipated
    // forall i, parse(s).coeff(anticipDegree - i) = anticipCoeffForExpts(i)
    //   (anticipDegree - i) means that we expect coeffs to be expressed
    //   corresponding to decreasing expts
    private void eqP(String s, int anticipDegree, RatNum[] anticipCoeffs) {
        RatPoly p = parse(s);
        assertTrue(p.degree() == anticipDegree);
        for (int i = 0; i <= anticipDegree; i++) {
            assertTrue(p.coeff(anticipDegree - i).equals(anticipCoeffs[i]),
                "wrong coeff; \n"
                + "anticipated: "
                + anticipCoeffs[i]
                + "; received: "
                + p.coeff(anticipDegree - i)
                + "\n"
                + " received: "
                + p
                + " anticipated:"
                + s
            );
        }
    }

    // added convenience: express coeffs as ints
    private void eqP(String s, int anticipDegree, int[] intCoeffs) {
        RatNum[] coeffs = new RatNum[intCoeffs.length];
        for (int i = 0; i < coeffs.length; i++) {
            coeffs[i] = num(intCoeffs[i]);
        }
        eqP(s, anticipDegree, coeffs);
    }

    // make sure that unparsing a parsed string yields the string itself
    private void asserttoStringWorks(String s) {
        assertEquals(s, parse(s).toString());
    }

    @Test
    public void testParseSimple() {
        eqP("0", 0, new int[] { 0 });
        eqP("x", 1, new int[] { 1, 0 });
        eqP("x^2", 2, new int[] { 1, 0, 0 });
    }

    @Test
    public void testParseMultTerms() {
        eqP("x^3+x^2", 3, new int[] { 1, 1, 0, 0 });
        eqP("x^3-x^2", 3, new int[] { 1, -1, 0, 0 });
        eqP("x^10+x^2", 10, new int[] { 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 });
    }

    @Test
    public void testParseLeadingNeg() {
        eqP("-x^2", 2, new int[] { -1, 0, 0 });
        eqP("-x^2+1", 2, new int[] { -1, 0, 1 });
        eqP("-x^2+x", 2, new int[] { -1, 1, 0 });
    }

    @Test
    public void testParseLeadingConstants() {
        eqP("10*x", 1, new int[] { 10, 0 });

        eqP("10*x^4+x^2", 4, new int[] { 10, 0, 1, 0, 0 });

        eqP("10*x^4+100*x^2", 4, new int[] { 10, 0, 100, 0, 0 });

        eqP("-10*x^4+100*x^2", 4, new int[] { -10, 0, 100, 0, 0 });
    }

    @Test
    public void testParseRationals() {
        eqP("1/2", 0, new RatNum[] { num(1).div(num(2))});
        eqP("1/2*x", 1, new RatNum[] { num(1).div(num(2)), num(0)});
        eqP("x+1/3", 1, new RatNum[] { num(1), num(1).div(num(3))});
        eqP(
            "1/2*x+1/3",
            1,
            new RatNum[] { num(1).div(num(2)), num(1).div(num(3))});
        eqP(
            "1/2*x+3/2",
            1,
            new RatNum[] { num(1).div(num(2)), num(3).div(num(2))});
        eqP(
            "1/2*x^3+3/2",
            3,
            new RatNum[] {
                num(1).div(num(2)),
                num(0),
                num(0),
                num(3).div(num(2))});
        eqP(
            "1/2*x^3+3/2*x^2+1",
            3,
            new RatNum[] {
                num(1).div(num(2)),
                num(3).div(num(2)),
                num(0),
                num(1)});
    }

    @Test
    public void testParseNaN() {
        assertTrue(parse("NaN").isNaN());
    }

    @Test
    public void testtoStringSimple() {
        asserttoStringWorks("0");
        asserttoStringWorks("x");
        asserttoStringWorks("x^2");
    }

    @Test
    public void testtoStringMultTerms() {
        asserttoStringWorks("x^3+x^2");
        asserttoStringWorks("x^3-x^2");
        asserttoStringWorks("x^100+x^2");
    }

    @Test
    public void testtoStringLeadingNeg() {
        asserttoStringWorks("-x^2");
        asserttoStringWorks("-x^2+1");
        asserttoStringWorks("-x^2+x");
    }

    @Test
    public void testtoStringLeadingConstants() {
        asserttoStringWorks("10*x");
        asserttoStringWorks("10*x^100+x^2");
        asserttoStringWorks("10*x^100+100*x^2");
        asserttoStringWorks("-10*x^100+100*x^2");
    }

    @Test
    public void testtoStringRationals() {
        asserttoStringWorks("1/2");
        asserttoStringWorks("1/2*x");
        asserttoStringWorks("x+1/3");
        asserttoStringWorks("1/2*x+1/3");
        asserttoStringWorks("1/2*x+3/2");
        asserttoStringWorks("1/2*x^10+3/2");
        asserttoStringWorks("1/2*x^10+3/2*x^2+1");
    }

    @Test
    public void testtoStringNaN() {
        asserttoStringWorks("NaN");
    }

    @Test
    public void testNoArgCtor() {
        eq(new RatPoly(), "0");
    }

    @Test
    public void testTwoArgCtor() {
        eq(poly(0, 0), "0");
        eq(poly(0, 1), "0");
        eq(poly(1, 0), "1");
        eq(poly(-1, 0), "-1");
        eq(poly(1, 1), "x");
        eq(poly(1, 2), "x^2");
        eq(poly(2, 2), "2*x^2");
        eq(poly(2, 3), "2*x^3");
        eq(poly(-2, 3), "-2*x^3");
        eq(poly(-1, 1), "-x");
        eq(poly(-1, 3), "-x^3");
    }

    @Test
    public void testDegree() {
        assertTrue(poly(1, 0).degree() == 0,"x^0 degree 0");
        assertTrue(poly(1, 1).degree() == 1, "x^1 degree 1");
        assertTrue(poly(1, 100).degree() == 100, "x^100 degree 100");
        assertTrue(poly(0, 100).degree() == 0, "0*x^100 degree 0");
        assertTrue(poly(0, 0).degree() == 0, "0*x^0 degree 0");
    }

    @Test
    public void testAdd() {
        RatPoly _XSqPlus2X = poly(1, 2).add(poly(1, 1)).add(poly(1, 1));
        RatPoly _2XSqPlusX = poly(1, 2).add(poly(1, 2)).add(poly(1, 1));

        eq(poly(1, 0).add(poly(1, 0)), "2");
        eq(poly(1, 0).add(poly(5, 0)), "6");
        eq(poly(1, 1).add(poly(1, 1)), "2*x");
        eq(poly(1, 2).add(poly(1, 2)), "2*x^2");
        eq(poly(1, 2).add(poly(1, 1)), "x^2+x");
        eq(_XSqPlus2X, "x^2+2*x");
        eq(_2XSqPlusX, "2*x^2+x");
        eq(poly(1, 3).add(poly(1, 1)), "x^3+x");
    }

    @Test
    public void testSub() {
        eq(poly(1, 1).sub(poly(1, 0)), "x-1");
        eq(poly(1, 1).add(poly(1, 0)), "x+1");
    }

    @Test
    public void testMul() {
        eq(poly(0, 0).mul(poly(0, 0)), "0");
        eq(poly(1, 0).mul(poly(1, 0)), "1");
        eq(poly(1, 0).mul(poly(2, 0)), "2");
        eq(poly(2, 0).mul(poly(2, 0)), "4");
        eq(poly(1, 0).mul(poly(1, 1)), "x");
        eq(poly(1, 1).mul(poly(1, 1)), "x^2");
        eq(poly(1, 1).sub(poly(1, 0)).mul(poly(1, 1).add(poly(1, 0))), "x^2-1");
    }

    // helper for testOpsWithNaN
    private void testOpsWithNaN(RatPoly p) {
        RatPoly nan = RatPoly.parse("NaN");
        eq(p.add(nan), "NaN");
        eq(nan.add(p), "NaN");
        eq(p.sub(nan), "NaN");
        eq(nan.sub(p), "NaN");
        eq(p.mul(nan), "NaN");
        eq(nan.mul(p), "NaN");
        eq(p.div(nan), "NaN");
        eq(nan.div(p), "NaN");
    }

    @Test
    public void testOpsWithNaN() {
        testOpsWithNaN(poly(0, 0));
        testOpsWithNaN(poly(0, 1));
        testOpsWithNaN(poly(1, 0));
        testOpsWithNaN(poly(1, 1));
        testOpsWithNaN(poly(2, 0));
        testOpsWithNaN(poly(2, 1));
        testOpsWithNaN(poly(0, 2));
        testOpsWithNaN(poly(1, 2));
    }

    @Test
    public void testImmutabilityOfOperations() {
        // not the most thorough test possible, but hopefully will
        // catch the easy cases early on...
        RatPoly one = poly(1, 0);
        RatPoly two = poly(2, 0);

        one.degree();
        two.degree();
        eq(one, "1", "Degree mutates receiver!");
        eq(two, "2", "Degree mutates receiver!");

        one.coeff(0);
        two.coeff(0);
        eq(one, "1", "Coeff mutates receiver!");
        eq(two, "2", "Coeff mutates receiver!");

        one.isNaN();
        two.isNaN();
        eq(one, "1", "isNaN mutates receiver!");
        eq(two, "2", "isNaN mutates receiver!");

        one.eval(0.0);
        two.eval(0.0);
        eq(one, "1", "eval mutates receiver!");
        eq(two, "2", "eval mutates receiver!");

        one.negate();
        two.negate();
        eq(one, "1", "Negate mutates receiver!");
        eq(two, "2", "Negate mutates receiver!");

        one.add(two);
        eq(one, "1", "Add mutates receiver!");
        eq(two, "2", "Add mutates argument!");

        one.sub(two);
        eq(one, "1", "Sub mutates receiver!");
        eq(two, "2", "Sub mutates argument!");

        one.mul(two);
        eq(one, "1", "Mul mutates receiver!");
        eq(two, "2", "Mul mutates argument!");

        one.div(two);
        eq(one, "1", "Div mutates receiver!");
        eq(two, "2", "Div mutates argument!");
    }

    @Test
    public void testEval() {
        RatPoly zero = new RatPoly();
        RatPoly one = new RatPoly(1, 0);
        RatPoly _X = new RatPoly(1, 1);
        RatPoly _2X = new RatPoly(2, 1);
        RatPoly _XSq = new RatPoly(1, 2);

        assertEquals(0.0, zero.eval(0.0), 0.0001, " 0 at 0 ");
        assertEquals(0.0, zero.eval(1.0), 0.0001, " 0 at 1 ");
        assertEquals(0.0, zero.eval(2.0), 0.0001, " 0 at 2 ");
        assertEquals(1.0, one.eval(0.0), 0.0001, " 1 at 0 ");
        assertEquals(1.0, one.eval(1.0), 0.0001, " 1 at 1 ");
        assertEquals(1.0, one.eval(2.0), 0.0001, " 1 at 1 ");

        assertEquals(0.0, _X.eval(0.0), 0.0001, " x at 0 ");
        assertEquals(1.0, _X.eval(1.0), 0.0001, " x at 1 ");
        assertEquals(2.0, _X.eval(2.0), 0.0001, " x at 2 ");

        assertEquals(0.0, _2X.eval(0.0), 0.0001, " 2*x at 0 ");
        assertEquals(2.0, _2X.eval(1.0), 0.0001, " 2*x at 1 ");
        assertEquals(4.0, _2X.eval(2.0), 0.0001, " 2*x at 2 ");

        assertEquals(0.0, _XSq.eval(0.0), 0.0001, " x^2 at 0 ");
        assertEquals(1.0, _XSq.eval(1.0), 0.0001, " x^2 at 1 ");
        assertEquals(4.0, _XSq.eval(2.0), 0.0001, " x^2 at 2 ");

        RatPoly _XSq_minus_2X = _XSq.sub(_2X);

        assertEquals(0.0, _XSq_minus_2X.eval(0.0), 0.0001, " x^2-2*x at 0 ");
        assertEquals(-1.0, _XSq_minus_2X.eval(1.0), 0.0001, " x^2-2*x at 1 ");
        assertEquals(0.0, _XSq_minus_2X.eval(2.0), 0.0001, " x^2-2*x at 2 ");
        assertEquals(3.0, _XSq_minus_2X.eval(3.0), 0.0001, " x^2-2*x at 3 ");
    }

    @Test
    public void testCoeff() {
        // coeff already gets some grunt testing in eqP; checking an interesting 
        // input here... 
        RatPoly _XSqPlus2X = poly(1, 2).add(poly(1, 1)).add(poly(1, 1));
        RatPoly _2XSqPlusX = poly(1, 2).add(poly(1, 2)).add(poly(1, 1));

        assertTrue(_XSqPlus2X.coeff(-1).equals(num(0)));
        assertTrue(_XSqPlus2X.coeff(-10).equals(num(0)));
        assertTrue(_2XSqPlusX.coeff(-1).equals(num(0)));
        assertTrue(_2XSqPlusX.coeff(-10).equals(num(0)));
        assertTrue(zero().coeff(-10).equals(num(0)));
        assertTrue(zero().coeff(-1).equals(num(0)));
    }

    @Test
    public void testDiv() {
        // 0/x = 0
        eq(poly(0, 1).div(poly(1, 1)), "0");

        // 2/1 = 2
        eq(poly(2, 0).div(poly(1, 0)), "2");

        // x/x = 1
        eq(poly(1, 1).div(poly(1, 1)), "1");

        // -x/x = -1
        eq(poly(-1, 1).div(poly(1, 1)), "-1");

        // x/-x = -1
        eq(poly(1, 1).div(poly(-1, 1)), "-1");

        // -x/-x = 1
        eq(poly(-1, 1).div(poly(-1, 1)), "1");

        // -x^2/x = -x
        eq(poly(-1, 2).div(poly(1, 1)), "-x");

        // x^100/x^1000 = 0
        eq(poly(1, 100).div(poly(1, 1000)), "0");

        // x^100/x = x^99
        eq(poly(1, 100).div(poly(1, 1)), "x^99");

        // x^99/x^98 = x
        eq(poly(1, 99).div(poly(1, 98)), "x");

        // x^10 / x = x^9 (r: 0)
        eq(poly(1, 10).div(poly(1, 1)), "x^9");

        // x^10 / x^3+x^2 = x^7-x^6+x^5-x^4+x^3-x^2+x-1  (r: -x^2)
        eq(
           poly(1, 10).div(poly(1, 3).add(poly(1, 2))),
           "x^7-x^6+x^5-x^4+x^3-x^2+x-1");

        // x^10 / x^3+x^2+x = x^7-x^6+x^4-x^3+x-1 (r: -x)
        eq(
           poly(1, 10).div(poly(1, 3).add(poly(1, 2).add(poly(1, 1)))),
           "x^7-x^6+x^4-x^3+x-1");

        // x^10+x^5 / x = x^9+x^4 (r: 0)
        eq(poly(1, 10).add(poly(1, 5)).div(poly(1, 1)), "x^9+x^4");

        // x^10+x^5 / x^3 = x^7+x^2 (r: 0)
        eq(poly(1, 10).add(poly(1, 5)).div(poly(1, 3)), "x^7+x^2");

        // x^10+x^5 / x^3+x+3 = x^7-x^5-3*x^4+x^3+7*x^2+8*x-10 (r: 29*x^2+14*x-30)
        eq(
           poly(1, 10).add(poly(1, 5)).div(
                                           poly(1, 3).add(poly(1, 1)).add(poly(3, 0))),
           "x^7-x^5-3*x^4+x^3+7*x^2+8*x-10");
    }

    @Test
    public void testDivComplexI() {
        // (x+1)*(x+1) = x^2+2*x+1
        eq(
           poly(1, 2).add(poly(2, 1)).add(poly(1, 0)).div(
                                                          poly(1, 1).add(poly(1, 0))),
           "x+1");

        // (x-1)*(x+1) = x^2-1
        eq(poly(1, 2).add(poly(-1, 0)).div(poly(1, 1).add(poly(1, 0))), "x-1");
    }

    @Test
    public void testDivComplexII() {
        // x^8+2*x^6+8*x^5+2*x^4+17*x^3+11*x^2+8*x+3 =
        // (x^3+2*x+1) * (x^5+7*x^2+2*x+3)
        RatPoly large =
            poly(1, 8)
            .add(poly(2, 6))
            .add(poly(8, 5))
            .add(poly(2, 4))
            .add(poly(17, 3))
            .add(poly(11, 2))
            .add(poly(8, 1))
            .add(poly(3, 0));

        // x^3+2*x+1
        RatPoly sub1 = poly(1, 3).add(poly(2, 1)).add(poly(1, 0));
        // x^5+7*x^2+2*x+3
        RatPoly sub2 =
            poly(1, 5).add(poly(7, 2)).add(poly(2, 1)).add(poly(3, 0));

        // just a last minute typo check...
        eq(sub1.mul(sub2), large.toString());
        eq(sub2.mul(sub1), large.toString());

        eq(large.div(sub2), "x^3+2*x+1");
        eq(large.div(sub1), "x^5+7*x^2+2*x+3");
    }

    @Test
    public void testDivExamplesFromSpec() {
        // seperated this test case out because it has a dependency on
        // both "parse" and "div" functioning properly

        // example 1 from spec
        eq(parse("x^3-2*x+3").div(parse("3*x^2")), "1/3*x");
        // example 2 from spec
        eq(parse("x^2+2*x+15").div(parse("2*x^3")), "0");
    }

    @Test
    public void testDivExampleFromPset() {
        eq(
           parse("x^8+x^6+10*x^4+10*x^3+8*x^2+2*x+8").div(
                                                          parse("3*x^6+5*x^4+9*x^2+4*x+8")),
           "1/3*x^2-2/9");
    }

    private void assertIsNaNanswer(RatPoly nanAnswer) {
        eq(nanAnswer, "NaN");
    }

    @Test
    public void testDivByZero() {
        RatPoly nanAnswer;
        nanAnswer = poly(1, 0).div(zero());
        assertIsNaNanswer(nanAnswer);

        nanAnswer = poly(1, 1).div(zero());
        assertIsNaNanswer(nanAnswer);
    }

    @Test
    public void testDivByPolyWithNaN() {
        RatPoly nan_x2 = poly(1, 2).mul(poly(1, 1).div(zero()));
        RatPoly one_x1 = new RatPoly(1, 1);

        assertIsNaNanswer(nan_x2.div(one_x1));
        assertIsNaNanswer(one_x1.div(nan_x2));
        assertIsNaNanswer(nan_x2.div(zero()));
        assertIsNaNanswer(zero().div(nan_x2));
        assertIsNaNanswer(nan_x2.div(nan_x2));
    }

    @Test
    public void testIsNaN() {
        assertTrue(RatPoly.parse("NaN").isNaN());
        assertTrue(!RatPoly.parse("1").isNaN());
        assertTrue(!RatPoly.parse("1/2").isNaN());
        assertTrue(!RatPoly.parse("x+1").isNaN());
        assertTrue(!RatPoly.parse("x^2+x+1").isNaN());
    }

    @Test
    public void testDifferentiate() {
        eq(poly(1, 1).differentiate(), "1");
        eq(quadPoly(7, 5, 99).differentiate(), "14*x+5");
        eq(quadPoly(3, 2, 1).differentiate(), "6*x+2");
        eq(quadPoly(1, 0, 1).differentiate(), "2*x");	  
        assertIsNaNanswer(RatPoly.parse("NaN").differentiate());
    }

    @Test
    public void testAntiDifferentiate() {
        eq(poly(1, 0).antiDifferentiate(new RatNum(1)), "x+1");
        eq(poly(2, 1).antiDifferentiate(new RatNum(1)), "x^2+1");
        eq(quadPoly(0, 6, 2).antiDifferentiate(new RatNum(1)), "3*x^2+2*x+1");
        eq(quadPoly(4, 6, 2).antiDifferentiate(new RatNum(0)), "4/3*x^3+3*x^2+2*x");

        assertIsNaNanswer(RatPoly.parse("NaN").antiDifferentiate(new RatNum(1)));
        assertIsNaNanswer(poly(1, 0).antiDifferentiate(new RatNum(1, 0)));
    }

    @Test
    public void testIntegrate() {
        assertEquals(1.0, poly(1,0).integrate(0,1), 0.0001, "one from 0 to 1" );
        assertEquals(3.0, poly(2,1).integrate(1,-2), 0.0001, "2x from 1 to -2");
        assertEquals(369.33333333, quadPoly(7,6,2).integrate(1,5), 0.0001, "7*x^2+6*x+2 from 1 to 5");	
    }    

    public void testAll(){
        testParseSimple();
        testParseMultTerms();
        testParseLeadingNeg();
        testParseLeadingConstants();
        testParseRationals();
        testParseNaN();
        testtoStringSimple();
        testtoStringMultTerms();
        testtoStringLeadingNeg();
        testtoStringLeadingConstants();
        testtoStringRationals();
        testtoStringNaN();
        testNoArgCtor();
        testTwoArgCtor();
        testDegree();
        testAdd();
        testSub();
        testMul();
        testOpsWithNaN();
        testImmutabilityOfOperations();
        testCoeff();
        testDiv();
        testDivComplexI();
        testDivComplexII();
        testDivExamplesFromSpec();
        testDivExampleFromPset();
        testDivByZero();
        testDivByPolyWithNaN();
        testIsNaN();
        testDifferentiate();
        testAntiDifferentiate();
        testIntegrate();
    }

    // // Tell JUnit what order to run the tests in
    // public static Test suite() {
    //     TestSuite suite = new TestSuite();
    //     suite.addTest(new RatPolyTest("testParseSimple"));
    //     suite.addTest(new RatPolyTest("testParseMultTerms"));
    //     suite.addTest(new RatPolyTest("testParseLeadingNeg"));
    //     suite.addTest(new RatPolyTest("testParseLeadingConstants"));
    //     suite.addTest(new RatPolyTest("testParseRationals"));
    //     suite.addTest(new RatPolyTest("testParseNaN"));
    //     suite.addTest(new RatPolyTest("testtoStringSimple"));
    //     suite.addTest(new RatPolyTest("testtoStringMultTerms"));
    //     suite.addTest(new RatPolyTest("testtoStringLeadingNeg"));
    //     suite.addTest(new RatPolyTest("testtoStringLeadingConstants"));
    //     suite.addTest(new RatPolyTest("testtoStringRationals"));
    //     suite.addTest(new RatPolyTest("testtoStringNaN"));
    //     suite.addTest(new RatPolyTest("testNoArgCtor"));
    //     suite.addTest(new RatPolyTest("testTwoArgCtor"));
    //     suite.addTest(new RatPolyTest("testDegree"));
    //     suite.addTest(new RatPolyTest("testAdd"));
    //     suite.addTest(new RatPolyTest("testSub"));
    //     suite.addTest(new RatPolyTest("testMul"));
    //     suite.addTest(new RatPolyTest("testOpsWithNaN"));
    //     suite.addTest(new RatPolyTest("testCoeff"));
    //     suite.addTest(new RatPolyTest("testDiv"));
    //     suite.addTest(new RatPolyTest("testDivComplexI"));
    //     suite.addTest(new RatPolyTest("testDivComplexII"));
    //     suite.addTest(new RatPolyTest("testDivExamplesFromSpec"));
    //     suite.addTest(new RatPolyTest("testDivExampleFromPset"));
    //     suite.addTest(new RatPolyTest("testDivByZero"));
    //     suite.addTest(new RatPolyTest("testDivByPolyWithNaN"));
    //     suite.addTest(new RatPolyTest("testIsNaN"));
    //     suite.addTest(new RatPolyTest("testEval"));
    //     suite.addTest(new RatPolyTest("testImmutabilityOfOperations"));
    //     suite.addTest(new RatPolyTest("testDifferentiate"));
    //     suite.addTest(new RatPolyTest("testAntiDifferentiate"));
    //     suite.addTest(new RatPolyTest("testIntegrate"));
    //     return suite;
    // }
}
