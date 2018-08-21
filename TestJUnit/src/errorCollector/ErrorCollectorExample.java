package errorCollector;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import junit.framework.Assert;
public class ErrorCollectorExample {

	@Rule
	public ErrorCollector collector = new ErrorCollector();
	
	@Test
	public void example() {
		collector.addError(new Throwable("There is an error in the first line"));
		collector.addError(new Throwable("There is an error in the Second line"));
	
		System.out.println("Hello");
		try {
			Assert.assertTrue("A" == "B");
		} catch (Throwable t) {
			collector.addError(t);
		}
		System.out.println("World !!!");
	}
}
