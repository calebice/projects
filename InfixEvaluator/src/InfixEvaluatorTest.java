import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class InfixEvaluatorTest {



	@Test
	public void testExpression01() {
		assertEquals( 1, InfixEvaluator.evaluate("7 - 3 * 2") );

	}

	@Test
	public void testExpression02() {
		assertEquals( 8, InfixEvaluator.evaluate("( 7 - 3 ) * 2") );
	}

	@Test
	public void testExpression03() {
		assertEquals(47, InfixEvaluator.evaluate("3 ^ 2 * 5 + 2"));
	}

	@Test
	public void testExpression04() {
		assertEquals(-1, InfixEvaluator.evaluate("( 2 * 3 ) - ( 3 + 4 )"));
	}

	@Test
	public void testExpression05() {
		assertEquals(6, InfixEvaluator.evaluate("( 10 - ( 6 * 2 ) / 3 )"));
	}

	@Test
	public void testExpression06() {
		assertEquals(7, InfixEvaluator.evaluate("( 5 + 3 ^ 2 ) / 2"));
	}
	
	@Test
	public void testExpression07() {
		assertEquals(8, InfixEvaluator.evaluate("4 * ( 5 % 3 )"));
	}
	
	@Test(expected = SyntaxErrorException.class) 
	public void testTooManyOperators() { 
		InfixEvaluator.evaluate("( 4 * + 3 )");
	}
	
	@Test(expected = SyntaxErrorException.class)
	public void testNoSpacesBetween() {
		InfixEvaluator.evaluate("3-2+1-9");
	}
	
	@Test(expected = SyntaxErrorException.class)
	public void testNumbersBackToBack() {
		InfixEvaluator.evaluate("3 4 + 2");
	}
}
