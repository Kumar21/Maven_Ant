/**
 * 
 */
package com.apium_calc;

import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;

import java.util.Iterator;

import org.apache.commons.configuration.event.ConfigurationListener;

import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.core.TestBaseProvider;
import com.qmetry.qaf.automation.step.NotYetImplementedException;
import com.qmetry.qaf.automation.step.QAFTestStep;

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

}
