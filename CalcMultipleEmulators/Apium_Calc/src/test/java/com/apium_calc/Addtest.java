package com.apium_calc;

import org.testng.annotations.Test;

import com.qmetry.qaf.automation.ui.WebDriverTestCase;
import static com.qmetry.qaf.automation.step.CommonStep.*;

public class Addtest extends WebDriverTestCase {

	@Test(description = "Sample Adding in calculator")
	public void SampleAdd() {
		get("/");
		click("button.7");
		click("button.plus");
		click("button.5");
		click("button.equals");
		verifyText("result.ele", "12");	
	}

}
