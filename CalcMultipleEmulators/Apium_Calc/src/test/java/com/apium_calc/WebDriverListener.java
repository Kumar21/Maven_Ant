package com.apium_calc;

import java.io.IOException;

import org.openqa.selenium.Capabilities;

import com.qmetry.qaf.automation.core.QAFTestBase;
import com.qmetry.qaf.automation.ui.webdriver.CommandTracker;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebDriver;
import com.qmetry.qaf.automation.ui.webdriver.QAFWebDriverCommandListener;
import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;

public class WebDriverListener implements QAFWebDriverCommandListener {

	Thread t1, t2;

	@Override
	public void beforeCommand(QAFExtendedWebDriver driver,
			CommandTracker commandHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCommand(QAFExtendedWebDriver driver, CommandTracker commandHandler) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onFailure(QAFExtendedWebDriver driver, CommandTracker commandHandler) {
		// TODO Auto-generated method stub

	}

	public void beforedfInitialize(Capabilities desiredCapabilities) {
		System.setProperty("ANDROID_HOME",
				"C:\\Users\\kumar.shanu\\AppData\\Local\\Android\\android-sdk");
		t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("Starting First Appium Server");
				Runtime rt = Runtime.getRuntime();
				try {
					rt.exec("cmd /c start " + System.getProperty("user.dir")
							+ "\\resources\\launchserver.bat "
							+ getBundle().getString("appium.capabilities.deviceName")
							+ " " + getBundle().getString("appium.capabilities.port"));
					System.out.println("First Appium Server Started");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t1.start();

		t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("Starting Second Appium Server");
				Runtime rt = Runtime.getRuntime();
				try {
					rt.exec("cmd /c start " + System.getProperty("user.dir")
							+ "\\resources\\launchserver.bat "
							+ getBundle().getString("appium2.capabilities.deviceName")
							+ " " + getBundle().getString("appium2.capabilities.port"));
					System.out.println("Second Appium Server Started");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t2.start();

		QAFTestBase.pause(10000);

	}

	@Override
	public void onInitialize(QAFExtendedWebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInitializationFailure(Capabilities desiredCapabilities, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeInitialize(Capabilities arg0) {
		// TODO Auto-generated method stub
		
	}

}
