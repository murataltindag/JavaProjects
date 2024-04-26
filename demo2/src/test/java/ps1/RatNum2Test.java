package ps1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** This class contains a set of test cases that can be used to 
    test the implementation of the RatNum2 class.
    <p>
    RatNum2 is implemented for you, so it should already pass all the 
    tests in this suite.  This test is provided to give you examples
    of using the RatNum2 class, albeit in the context of a test driver.
*/
public class RatNum2Test{

    // naming convention used throughout class: spell out number in
    // variable as its constructive form.  Unary minus is notated with
    // the prefix "neg", and the solidus is notated with an 'I'
    // character.  Thus, "1 + 2/3" becomes one_plus_two_I_three

    // some simple base RatNum2s
    private RatNum2 zero = new RatNum2(0);
    private RatNum2 one = new RatNum2(1);
    private RatNum2 negOne = new RatNum2(-1);
    private RatNum2 two = new RatNum2(2);
    private RatNum2 three = new RatNum2(3);

    private RatNum2 one_I_two = new RatNum2(1, 2);
    private RatNum2 one_I_three = new RatNum2(1, 3);
    private RatNum2 one_I_four = new RatNum2(1, 4);
    private RatNum2 two_I_three = new RatNum2(2, 3);
    private RatNum2 three_I_four = new RatNum2(3, 4);

    private RatNum2 negOne_I_two = new RatNum2(-1, 2);

    // improper fraction
    private RatNum2 three_I_two = new RatNum2(3, 2);

    // NaNs
    private RatNum2 one_I_zero = new RatNum2(1, 0);
    private RatNum2 negOne_I_zero = new RatNum2(-1, 0);
    private RatNum2 hundred_I_zero = new RatNum2(100, 0);

    // ratnums: Set of varied ratnums (includes NaNs)
    // set is { 0, 1, -1, 2, 1/2, 3/2, 1/0, -1/0, 100/0 }
    private RatNum2[] ratnums = new RatNum2[] {
        zero, one, negOne, two, one_I_two, negOne_I_two, three_I_two,
        /* NaNs */ one_I_zero, negOne_I_zero, hundred_I_zero
    };

    // ratnans: Set of varied NaNs
    // set is { 1/0, -1/0, 100/0 }
    private RatNum2[] ratnans = new RatNum2[] {
        one_I_zero, negOne_I_zero, hundred_I_zero
    };

    // ratnanans: Set of varied non-NaN ratnums
    // set is ratnums - ratnans
    private RatNum2[] ratnanans = new RatNum2[] {
        zero, one, negOne, two, one_I_two, three_I_two
    };

    // public RatNum2Test(String name) { super(name); }

    // self-explanatory helper function
    private void eq( RatNum2 ratNum, String rep ) {
        assertEquals( rep, ratNum.toString() );
    }

    @Test
    public void testOneArgCtor() {
        eq( zero, "0" );

        eq( one, "1" );

        RatNum2 four = new RatNum2(4);
        eq( four, "4" );

        eq( negOne, "-1" );

        RatNum2 negFive = new RatNum2(-5);
        eq( negFive, "-5" );

        RatNum2 negZero = new RatNum2(-0);
        eq( negZero, "0" );
    }

    @Test
    public void testTwoArgCtor() {
        RatNum2 one_I_two = new RatNum2(1, 2);
        eq( one_I_two, "1/2" );

        RatNum2 two_I_one = new RatNum2(2, 1);
        eq( two_I_one, "2" );

        RatNum2 three_I_two = new RatNum2(3, 2);
        eq( three_I_two, "3/2" );

        RatNum2 negOne_I_thirteen = new RatNum2(-1, 13);
        eq( negOne_I_thirteen, "-1/13" );

        RatNum2 fiftyThree_I_seven = new RatNum2(53, 7);
        eq( fiftyThree_I_seven, "53/7" );

        RatNum2 zero_I_one = new RatNum2(0, 1);
        eq( zero_I_one, "0" );
    }

    @Test
    public void testTwoArgCtorOnNaN() {
        RatNum2 one_I_zero = new RatNum2(1, 0);
        eq( one_I_zero, "NaN" );

        RatNum2 two_I_zero = new RatNum2(2, 0);
        eq( two_I_zero, "NaN" );

        RatNum2 negOne_I_zero = new RatNum2(-1, 0);
        eq( negOne_I_zero, "NaN" );

        RatNum2 zero_I_zero = new RatNum2(0, 0);
        eq( zero_I_zero, "NaN" );

        RatNum2 negHundred_I_zero = new RatNum2(-100, 0);
        eq( negHundred_I_zero, "NaN" );
    }

    @Test
    public void testReduction() {
        RatNum2 negOne_I_negTwo = new RatNum2(-1, -2);
        eq( negOne_I_negTwo, "1/2" );

        RatNum2 two_I_four = new RatNum2(2, 4);
        eq( two_I_four, "1/2" );

        RatNum2 six_I_four = new RatNum2(6, 4);
        eq( six_I_four, "3/2" );

        RatNum2 twentySeven_I_thirteen = new RatNum2(27, 13);
        eq( twentySeven_I_thirteen, "27/13" );

        RatNum2 negHundred_I_negHundred = new RatNum2(-100, -100);
        eq( negHundred_I_negHundred, "1" );
    }

    // self-explanatory helper function
    private void approxEq(double d1, double d2) {
        assertTrue (Math.abs(d1 - d2) < .0000001);
    }

    @Test
    public void testApprox() {
        approxEq( zero.approx(), 0.0 );
        approxEq( one.approx(), 1.0 );
        approxEq( negOne.approx(), -1.0 );
        approxEq( two.approx(), 2.0 );
        approxEq( one_I_two.approx(), 0.5 );
        approxEq( two_I_three.approx(), 2./3. );
        approxEq( three_I_four.approx(), 0.75 );

        // cannot test that one_I_zero.approx() approxEq Double.NaN,
        // because it WON'T!!!  Instead, construct corresponding
        // instance of Double and use .equals(..) method
        // assertTrue( (new Double(Double.NaN)).
        //             equals
        //             (new Double(one_I_zero.approx())) );

        // use left-shift operator "<<" to create integer for 2^30
        RatNum2 one_I_twoToThirty = new RatNum2(1, (1<<30) );
        double quiteSmall = 1./Math.pow(2, 30);
        approxEq( one_I_twoToThirty.approx(), quiteSmall );
    }

    @Test
    public void testAddSimple() {
        eq( zero.add(zero), "0" );
        eq( zero.add(one), "1" );
        eq( one.add(zero), "1" );
        eq( one.add(one),  "2" );
        eq( one.add(negOne), "0" );
        eq( one.add(two), "3" );
        eq( two.add(two), "4" );
    }

    @Test
    public void testAddComplex() {
        eq( one_I_two.add(zero), "1/2" );
        eq( one_I_two.add(one), "3/2" );
        eq( one_I_two.add(one_I_two), "1" );
        eq( one_I_two.add(one_I_three), "5/6" );
        eq( one_I_two.add(negOne), "-1/2" );
        eq( one_I_two.add(two), "5/2" );
        eq( one_I_two.add(two_I_three), "7/6" );
        eq( one_I_two.add(three_I_four), "5/4" );

        eq( one_I_three.add(zero), "1/3" );
        eq( one_I_three.add(two_I_three), "1" );
        eq( one_I_three.add(three_I_four), "13/12" );
    }

    @Test
    public void testAddImproper() {
        eq( three_I_two.add(one_I_two), "2" );
        eq( three_I_two.add(one_I_three), "11/6" );
        eq( three_I_four.add(three_I_four), "3/2" );

        eq( three_I_two.add(three_I_two), "3" );
    }

    @Test
    public void testAddOnNaN() {
        // each test case (addend, augend) drawn from the set
        // ratnums x ratnans

        for(int i=0; i<ratnums.length; i++) {
            for(int j=0; j<ratnans.length; j++) {
                eq( ratnums[i].add(ratnans[j]), "NaN" );
                eq( ratnans[j].add(ratnums[i]), "NaN" );
            }
        }

    }

    @Test
    public void testAddTransitively() {
        eq( one.add(one).add(one), "3" );
        eq( one.add(one.add(one)), "3" );
        eq( zero.add(zero).add(zero), "0" );
        eq( zero.add(zero.add(zero)), "0" );
        eq( one.add(two).add(three), "6" );
        eq( one.add(two.add(three)), "6" );

        eq( one_I_three.add(one_I_three).add(one_I_three), "1" );
        eq( one_I_three.add(one_I_three.add(one_I_three)), "1" );

        eq( one_I_zero.add(one_I_zero).add(one_I_zero), "NaN" );
        eq( one_I_zero.add(one_I_zero.add(one_I_zero)), "NaN" );

        eq( one_I_two.add(one_I_three).add(one_I_four), "13/12" );
        eq( one_I_two.add(one_I_three.add(one_I_four)), "13/12" );
    }

    @Test
    public void testSubSimple() {
        eq( zero.sub(one), "-1");
        eq( zero.sub(zero), "0");
        eq( one.sub(zero), "1" );
        eq( one.sub(one), "0" );
        eq( two.sub(one), "1" );
        eq( one.sub(negOne), "2" );
        eq( one.sub(two), "-1" );
        eq( one.sub(three), "-2" );
    }

    @Test
    public void testSubComplex() {
        eq( one.sub(one_I_two), "1/2" );
        eq( one_I_two.sub(one), "-1/2" );
        eq( one_I_two.sub(zero), "1/2" );
        eq( one_I_two.sub(two_I_three), "-1/6" );
        eq( one_I_two.sub(three_I_four), "-1/4" );
    }

    @Test
    public void testSubImproper() {
        eq( three_I_two.sub(one_I_two), "1" );
        eq( three_I_two.sub(one_I_three), "7/6" );
    }

    @Test
    public void testSubOnNaN() {
        // analogous to testAddOnNaN()

        for(int i=0; i<ratnums.length; i++) {
            for( int j=0; j<ratnans.length; j++) {
                eq( ratnums[i].sub(ratnans[j]), "NaN" );
                eq( ratnans[j].sub(ratnums[i]), "NaN" );
            }
        }
    }

    @Test
    public void testSubTransitively() {
        // subtraction is not transitive; testing that operation is
        // correct when *applied transitivitely*, not that it obeys
        // the transitive property

        eq( one.sub(one).sub(one), "-1" );
        eq( one.sub(one.sub(one)), "1" );
        eq( zero.sub(zero).sub(zero), "0" );
        eq( zero.sub(zero.sub(zero)), "0" );
        eq( one.sub(two).sub(three), "-4" );
        eq( one.sub(two.sub(three)), "2" );

        eq( one_I_three.sub(one_I_three).sub(one_I_three), "-1/3" );
        eq( one_I_three.sub(one_I_three.sub(one_I_three)), "1/3" );

        eq( one_I_zero.sub(one_I_zero).sub(one_I_zero), "NaN" );
        eq( one_I_zero.sub(one_I_zero.sub(one_I_zero)), "NaN" );

        eq( one_I_two.sub(one_I_three).sub(one_I_four), "-1/12" );
        eq( one_I_two.sub(one_I_three.sub(one_I_four)), "5/12" );
    }

    @Test
    public void testMulProperties() {
        // zero property
        for(int i=0; i<ratnanans.length; i++) {
            eq( zero.mul(ratnanans[i]) , "0" );
            eq( ratnanans[i].mul(zero) , "0" );
        }

        // one property
        for(int i=0; i<ratnanans.length; i++) {
            eq( one.mul(ratnanans[i]) , ratnanans[i].toString() );
            eq( ratnanans[i].mul(one) , ratnanans[i].toString() );
        }

        // negOne property
        for(int i=0; i<ratnanans.length; i++) {
            eq( negOne.mul(ratnanans[i]) , ratnanans[i].negate().toString() );
            eq( ratnanans[i].mul(negOne) , ratnanans[i].negate().toString() );
        }
    }

    @Test
    public void testMulSimple() {
        eq( two.mul(two), "4" );
        eq( two.mul(three), "6" );
        eq( three.mul(two), "6" );
    }

    @Test
    public void testMulComplex() {
        eq( one_I_two.mul(two), "1" );
        eq( two.mul(one_I_two), "1" );
        eq( one_I_two.mul(one_I_two), "1/4" );
        eq( one_I_two.mul(one_I_three), "1/6" );
        eq( one_I_three.mul(one_I_two), "1/6" );
    }

    @Test
    public void testMulImproper() {
        eq( three_I_two.mul(one_I_two), "3/4" );
        eq( three_I_two.mul(one_I_three), "1/2" );
        eq( three_I_two.mul(three_I_four), "9/8" );
        eq( three_I_two.mul(three_I_two), "9/4" );
    }

    @Test
    public void testMulOnNaN() {
        // analogous to testAddOnNaN()

        for(int i=0; i<ratnums.length; i++) {
            for( int j=0; j<ratnans.length; j++) {
                eq( ratnums[i].mul(ratnans[j]), "NaN" );
                eq( ratnans[j].mul(ratnums[i]), "NaN" );
            }
        }
    }

    @Test
    public void testMulTransitively() {
        eq( one.mul(one).mul(one), "1" );
        eq( one.mul(one.mul(one)), "1" );
        eq( zero.mul(zero).mul(zero), "0" );
        eq( zero.mul(zero.mul(zero)), "0" );
        eq( one.mul(two).mul(three), "6" );
        eq( one.mul(two.mul(three)), "6" );

        eq( one_I_three.mul(one_I_three).mul(one_I_three), "1/27" );
        eq( one_I_three.mul(one_I_three.mul(one_I_three)), "1/27" );

        eq( one_I_zero.mul(one_I_zero).mul(one_I_zero), "NaN" );
        eq( one_I_zero.mul(one_I_zero.mul(one_I_zero)), "NaN" );

        eq( one_I_two.mul(one_I_three).mul(one_I_four), "1/24");
        eq( one_I_two.mul(one_I_three.mul(one_I_four)), "1/24");
    }

    @Test
    public void testDivSimple() {
        eq( zero.div(zero), "NaN" );
        eq( zero.div(one), "0" );
        eq( one.div(zero), "NaN" );
        eq( one.div(one), "1" );
        eq( one.div(negOne), "-1" );
        eq( one.div(two), "1/2" );
        eq( two.div(two), "1" );
    }

    @Test
    public void testDivComplex() {
        eq( one_I_two.div(zero), "NaN" );
        eq( one_I_two.div(one), "1/2" );
        eq( one_I_two.div(one_I_two), "1" );
        eq( one_I_two.div(one_I_three), "3/2" );
        eq( one_I_two.div(negOne), "-1/2" );
        eq( one_I_two.div(two), "1/4" );
        eq( one_I_two.div(two_I_three), "3/4" );
        eq( one_I_two.div(three_I_four), "2/3" );

        eq( one_I_three.div(zero), "NaN" );
        eq( one_I_three.div(two_I_three), "1/2" );
        eq( one_I_three.div(three_I_four), "4/9" );
    }

    @Test
    public void testDivImproper() {
        eq( three_I_two.div(one_I_two), "3" );
        eq( three_I_two.div(one_I_three), "9/2" );
        eq( three_I_two.div(three_I_two), "1" );
    }

    @Test
    public void testDivOnNaN() {
        // each test case (addend, augend) drawn from the set
        // ratnums x ratnans

        for(int i=0; i<ratnums.length; i++) {
            for(int j=0; j<ratnans.length; j++) {
                eq( ratnums[i].div(ratnans[j]), "NaN" );
                eq( ratnans[j].div(ratnums[i]), "NaN" );
            }
        }

    }

    @Test
    public void testDivTransitively() {
        // (same note as in testSubTransitively re: transitivity property)

        eq( one.div(one).div(one), "1" );
        eq( one.div(one.div(one)), "1" );
        eq( zero.div(zero).div(zero), "NaN" );
        eq( zero.div(zero.div(zero)), "NaN" );
        eq( one.div(two).div(three), "1/6" );
        eq( one.div(two.div(three)), "3/2" );

        eq( one_I_three.div(one_I_three).div(one_I_three), "3" );
        eq( one_I_three.div(one_I_three.div(one_I_three)), "1/3" );

        eq( one_I_zero.div(one_I_zero).div(one_I_zero), "NaN" );
        eq( one_I_zero.div(one_I_zero.div(one_I_zero)), "NaN" );

        eq( one_I_two.div(one_I_three).div(one_I_four), "6" );
        eq( one_I_two.div(one_I_three.div(one_I_four)), "3/8" );

    }

    @Test
    public void testNegate() {
        eq( zero.negate(), "0" );
        eq( one.negate() , "-1" );
        eq( negOne.negate(), "1" );
        eq( two.negate(), "-2" );
        eq( three.negate(), "-3" );

        eq( one_I_two.negate(), "-1/2" );
        eq( one_I_three.negate(), "-1/3" );
        eq( one_I_four.negate(), "-1/4" );
        eq( two_I_three.negate(), "-2/3" );
        eq( three_I_four.negate(), "-3/4" );

        eq( three_I_two.negate(), "-3/2" );

        eq( one_I_zero.negate(), "NaN" );
        eq( negOne_I_zero.negate(), "NaN" );
        eq( hundred_I_zero.negate(), "NaN" );
    }

    // helper function, "decode-and-check"
    private void decChk(String s, RatNum2 expected) {
        eq( RatNum2.parse(s), expected.toString() );
    }

    @Test
    public void testParse() {
        decChk("0", zero);

        decChk("1",   one );
        decChk("1/1", one );
        decChk("2/2", one );
        decChk("-1/-1", one );

        decChk("-1", negOne );
        decChk("1/-1", negOne );
        decChk("-3/3", negOne );

        decChk("2", two );
        decChk("2/1", two );
        decChk("-4/-2", two );

        decChk("1/2", one_I_two );
        decChk("2/4", one_I_two );

        decChk("3/2", three_I_two );
        decChk("-6/-4", three_I_two );

        decChk("NaN", one_I_zero );
        decChk("NaN", negOne_I_zero );
    }

    @Test
    public void testEqualsReflexive() {
        for (int i=0; i<ratnums.length; i++) {
            assertTrue(ratnums[i].equals(ratnums[i]));
        }
    }

    @Test
    public void testEquals() {
        assertTrue( one.equals(one) );
        assertTrue( one.add(one).equals(two) );
        assertTrue( one_I_two.equals( one.div(two) ) );
        assertTrue( three_I_two.equals( three.div(two) ) );

        assertTrue( one_I_zero.equals( one_I_zero ) );
        assertTrue( one_I_zero.equals( negOne_I_zero ) );
        assertTrue( one_I_zero.equals( hundred_I_zero ) );

        assertTrue( ! one.equals(zero) );
        assertTrue( ! zero.equals(one) );
        assertTrue( ! one.equals(two) );
        assertTrue( ! two.equals(one) );
        assertTrue( ! one.equals(negOne) );
        assertTrue( ! negOne.equals(one) );

        assertTrue( ! one.equals(one_I_two) );
        assertTrue( ! one_I_two.equals(one) );
        assertTrue( ! one.equals(three_I_two) );
        assertTrue( ! three_I_two.equals(one) );
    }

    private void assertGreater( RatNum2 larger, RatNum2 smaller ) {
        assertTrue( larger.compareTo(smaller) > 0 );
        assertTrue( smaller.compareTo(larger) < 0 );
    }

    @Test
    public void testCompareToReflexive() {
        // reflexivitiy
        for(int i=0; i<ratnums.length; i++) {
            assertTrue( ratnums[i].compareTo(ratnums[i]) == 0);
        }
    }

    @Test
    public void testCompareToNonFract() {
        assertGreater( one, zero );
        assertGreater( one, negOne );
        assertGreater( two, one );
        assertGreater( two, zero );
        assertGreater( zero, negOne );
    }

    @Test
    public void testCompareToFract() {
        assertGreater( one, one_I_two );
        assertGreater( two, one_I_three );
        assertGreater( one, two_I_three );
        assertGreater( two, two_I_three );
        assertGreater( one_I_two, zero );
        assertGreater( one_I_two, negOne );
        assertGreater( one_I_two, negOne_I_two );
        assertGreater( zero, negOne_I_two );
    }

    @Test
    public void testCompareToNaNs() {
        for(int i=0; i<ratnans.length; i++) {
            for(int j=0; j<ratnans.length; j++) {	    
                assertTrue( ratnans[i].compareTo(ratnans[j]) == 0);
            }
            for(int j=0; j<ratnanans.length; j++) {
                assertGreater( ratnans[i], ratnanans[j] );
            }
        }
    }

    private void assertPos( RatNum2 n ) {
        assertTrue( n.isPositive() );
        assertTrue( ! n.isNegative() );
    }
    private void assertNeg( RatNum2 n ) {
        assertTrue( n.isNegative() );
        assertTrue( ! n.isPositive() );
    }

    @Test
    public void testIsPosAndIsNeg() {
        assertTrue( ! zero.isPositive() );
        assertTrue( ! zero.isNegative() );

        assertPos(one);
        assertNeg(negOne);
        assertPos(two);
        assertPos(three);

        assertPos(one_I_two);
        assertPos(one_I_three);
        assertPos(one_I_four);
        assertPos(two_I_three);
        assertPos(three_I_four);

        assertNeg(negOne_I_two);

        assertPos(three_I_two);

        assertPos(one_I_zero);
        assertPos(negOne_I_zero); // non-intuitive; see spec
        assertPos(hundred_I_zero);
    }

    @Test
    public void testIsNaN() {
        for (int i=0; i<ratnans.length; i++) {
            assertTrue(ratnans[i].isNaN());
        }
        for (int i=0; i<ratnanans.length; i++) {
            assertTrue(!ratnanans[i].isNaN());
        }
    }


    public void testAll(){
        testOneArgCtor();
        testTwoArgCtor();
        testTwoArgCtorOnNaN();
        testReduction();
        testApprox();
        testAddSimple();
        testAddComplex();
        testAddImproper();
        testAddOnNaN();
        testAddTransitively();
        testSubSimple();
        testSubComplex();
        testSubImproper();
        testSubOnNaN();
        testSubTransitively();
        testMulProperties();
        testMulSimple();
        testMulComplex();
        testMulImproper();
        testMulOnNaN();
        testMulTransitively();
        testDivSimple();
        testDivComplex();
        testDivImproper();
        testDivOnNaN();
        testDivTransitively();
        testNegate();
        testParse();
        testEqualsReflexive();
        testEquals();
        testCompareToReflexive();
        testCompareToNonFract();
        testCompareToFract();
        testCompareToNaNs();
        testIsPosAndIsNeg();
        testIsNaN();
    }
}
