package mypack;

import static org.junit.Assert.*;

import org.junit.Test;

public class SampleJUnit {
	
	@Test
	public void testSetup() {
		String str = "completed JUnit setup";
		assertEquals("completed JUnit setup",str);
	}
	
	
}
