/**
 * 
 */
package com.apium_calc;

import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;
import static com.qmetry.qaf.automation.step.CommonStep.sendKeys;

import java.util.Iterator;

import org.apache.commons.configuration.event.ConfigurationListener;

import com.google.common.collect.ImmutableList;
import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.core.TestBaseProvider;
import com.qmetry.qaf.automation.step.QAFTestStep;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
/**
 * @author kumar.shanu
 */
public class StepsBackLog {

	/**
	 * Auto-generated code snippet by QMetry Automation Framework.
	 */
	@QAFTestStep(description = "I open calculator application")
	public void iOpenCalculatorApplication() {
	}

	/**
	 * Auto-generated code snippet by QMetry Automation Framework.
	 */
	@QAFTestStep(description = "I switch to device {0}")
	public void iSwitchToDevice(int deviceId) {
		if (deviceId == 1) {
			Iterator<ConfigurationListener> e =
					getBundle().getConfigurationListeners().iterator();
			while (e.hasNext()) {
				ConfigurationManager.getBundle().removeConfigurationListener(e.next());
				// e.remove();
			}
			getBundle().setProperty("remote.port",
					getBundle().getString("appium.capabilities.port"));
			TestBaseProvider.instance().get().setDriver("appiumDriver");
		} else {
			Iterator<ConfigurationListener> e =
					getBundle().getConfigurationListeners().iterator();
			while (e.hasNext()) {
				ConfigurationManager.getBundle().removeConfigurationListener(e.next());
				// e.remove();
			}
			getBundle().setProperty("remote.port",
					getBundle().getString("appium2.capabilities.port"));
			TestBaseProvider.instance().get().setDriver("appium2Driver");
		}

	}

	/**
	 * Auto-generated code snippet by QMetry Automation Framework.
	 */
	@QAFTestStep(description = "I Enter {0} and {1} With {2} into {3}")
	public void iEnterAndWithInto(String str0, String str1, String str2, String str3) {

		ImmutableList.Builder<TouchAction> actions;
		 MobileDriver driver1a;
		sendKeys(str0 + str1 + str2, str3);
	}

	/**
	 * Auto-generated code snippet by QMetry Automation Framework.
	 */
	@QAFTestStep(description = "longPress on {x} {y} coordinates for {duration} duration")
	public void longPressOnForDuration(int x, int y, int duration) {
		// TouchAction tap = new TouchAction(getDriver());
		// tap.longPress(x, y, duration).perform();

		MultiTouchAction multiTouch = new MultiTouchAction(getDriver());

		for (int i = 0; i < 1; i++) {
			TouchAction tap = new TouchAction(getDriver());
			multiTouch.add(tap.press(x, y).waitAction(duration).release());
		}
		multiTouch.perform();
	}
	@SuppressWarnings({"rawtypes"})
	private static AppiumDriver getDriver() {
		return (AppiumDriver) new WebDriverTestBase().getDriver().getUnderLayingDriver();
	}
	public static void main(String[] args) {
		System.out.println("abcs's".replaceAll("\\'", "\\\\'"));
	}
}
