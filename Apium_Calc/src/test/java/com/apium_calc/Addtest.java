package com.apium_calc;

import org.testng.annotations.Test;

import com.qmetry.qaf.automation.data.MetaData;
import com.qmetry.qaf.automation.step.QAFTestStep;
import com.qmetry.qaf.automation.ui.WebDriverTestCase;
import static com.qmetry.qaf.automation.step.CommonStep.*;

public class Addtest extends WebDriverTestCase {

	@MetaData(value = "{'jiraId':'ISFWS-1422'}")
	@Test(description = "Sample Adding in calculator")
	public void SampleAdd() {
		get("/");
		click("button.7");
		click("button.plus");
		click("button.5");
		click("button.equals");
		verifyText("result.ele", "12");	
	}
 @QAFTestStep(description= "I entered first Operand {num1}")
 public void firstOperand(String num1){
	 sendKeys(num1, "classname=android.widget.EditText");
	// click("id=com.android.calculator2:id/digit7"); 
 }
 @QAFTestStep(description= "I select the operator {opr1}")
 public void selectOperator(String opr1){
	 sendKeys(opr1, "classname=android.widget.EditText");
	 //click("id=com.android.calculator2:id/digit7"); 
 }
 @QAFTestStep(description= "I entered second Operand {num1}")
 public void secondOperands(String num2){
	 sendKeys(num2, "classname=android.widget.EditText");
	// click("id=com.android.calculator2:id/digit7"); 
 }
 @QAFTestStep(description= "I select equal operator")
 public void equalOperator(){
	 click("button.equals"); 
 }
}
