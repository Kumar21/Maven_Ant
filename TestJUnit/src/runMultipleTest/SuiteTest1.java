package runMultipleTest;
import static org.junit.Assert.*;

import org.junit.Test;	
public class SuiteTest1 {


    public String message = "Saurabh";													

    @Test(expected = ArithmeticException.class)					
    public void testJUnitMessage() {					

        System.out.println("Junit Message is printing ");					
    }		

    @Test		
    public void testJUnitHiMessage() {					
        message = "Hi!" + message;							
        System.out.println("Junit Hi Message is printing ");				
        System.out.println("Suite Test 2 is successful " + message);							
    }	
	
}
