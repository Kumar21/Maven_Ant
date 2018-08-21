package parameterizedTest;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ArithmaticTest {

		private int firstNumber;
		private int secondNumber;
		private int expectedResult;
		private Airthematic airthmatic;
	
		public ArithmaticTest(int firstNumber, int secondNumber, int expectedresul) {
			super();
			this.firstNumber = firstNumber;
			this.secondNumber= secondNumber;
			this.expectedResult = expectedresul;
		}
	@Before
	public void initialize() {
		airthmatic = new Airthematic();
	}
	
	@Parameterized.Parameters
	public static Collection input() {
		return Arrays.asList(new Object[][] {{1,2,3},{11,22,33},{10,9,19},{100,9,109}});
	}
	
	@Test
	public void testArithmatictest() {
		System.out.println("Sum iof the number is  : "+expectedResult);
		assertEquals(expectedResult,airthmatic.sum(firstNumber,secondNumber));
	}
}
