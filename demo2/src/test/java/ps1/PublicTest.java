package ps1;

import org.junit.jupiter.api.Test;


/** PublicTest is a simple TestSuite that includes
    and runs all the tests in {@link RatNumTest}, 
    {@link RatPolyTest}, and {@link RatPolyStackTest}.
*/
public class PublicTest
{
    @Test
    public void testRatNum() {
        RatNumTest test = new RatNumTest();
        test.testAll();
    }

    @Test
    public void testRatPoly() {
        RatPolyTest test = new RatPolyTest();
        test.testAll();
    }

    @Test
    public void testRatPolyStack() {
        RatPolyStackTest test = new RatPolyStackTest();
        test.testAll();
    }

}
