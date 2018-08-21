package com.apium_calc;
//resolves getBundle()
import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.commons.configuration.event.ConfigurationListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.core.TestBaseProvider;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

/*
 * Utility class that manages the starting and culling of Appium servers before and after tests,
 * respectively. It also provides functionality for switching between active Appium drivers
 * during tests as well as getting the serial number of the currently active driver's device.
 * Logcat dumping is also included in onFinish activities.
 */
public class MultiDeviceListener implements ISuiteListener
{
	// A list of all devices that are a part of the current test
	public static ArrayList<String> devices;
	
	// The index of the currently executing device in the devices list
	private static int currentDevice;
	
	// A list of all the Appium servers currently running
	public static ArrayList<AppiumDriverLocalService> services;
	
	/*
	 * Runs at the start of every test.
	 * Parses application.properties for sets of deviceNames and ports to start discrete Appium
	 * servers. Parses from 1-n in serial. If n fails, it will not attempt n+1.
	 */
	@Override
	public void onStart(ISuite suite)
	{
		//init services list
		services = new ArrayList<AppiumDriverLocalService>();
		devices = new ArrayList<String>();
		
		//init services for defined devices in application.properties
		int port = 0;
		getBundle().setThrowExceptionOnMissing(true);
		for(int device = 1;;device++)
		{
			try
			{
				if(device != 1)
					port = Integer.parseInt(getBundle().getString("appium" + device + ".capabilities.port"));
				else
					port = Integer.parseInt(getBundle().getString("appium.capabilities.port"));
			}
			catch(NoSuchElementException nsee) { break; } //assume done creating servers
			catch(NumberFormatException nfe)
			{
				System.out.println("[MultiDevice] Provided invalid port number for device #" + device + ".");
				break;
			}
			
			if(isAvailable(port))
			{
				//parse for device name. if not found, assume done creating servers
				String devName = "";
				try
				{
					if(device != 1)
						devName = getBundle().getString("appium" + device + ".capabilities.deviceName");
					else
						devName = getBundle().getString("appium.capabilities.deviceName");

				}
				catch(NoSuchElementException nsee)
				{
					System.out.println("[MultiDevice] Found port, but no device name for " + port + ".");
					break;
				} //break out of the loop
				
				try
				{
					initAppium(devName, port);
				}
				catch (Exception e)
				{
					System.out.println("[MultiDevice] Encountered a(n) " + e.getClass().getName() + " while starting Appium for " + devName
							+ " Port: " + port + ".");
					e.printStackTrace();
				}
			}
			else
				System.out.println("[MultiDevice] Did not start server on " + port + " because it is already in use.");
		}
		
		//print out list of created servers and their corresponding devices' names 
		System.out.println("[MultiDevice] Finished creating Appium Servers.");
		for(int i = 0; i < devices.size(); i++)
		{
			System.out.println("[MultiDevice] Device #" + (i + 1) + " name: " + devices.get(i));
			System.out.println("[MultiDevice] Running on " + services.get(i).getUrl());
		}
		
		//activate a default device if there is only one
		currentDevice = -1;
		if(devices.size() == 1)
		{
			activateDevice(devices.get(0));
		}
		
		//toggle this off, or stuff will go very, very wrong
		getBundle().setThrowExceptionOnMissing(false);
	}
	
	/*
	 * Get a device from the list of currently active devices and make its Appium server the
	 * primary executing server.
	 */
	public static void activateDevice(String deviceName)
	{
		//clear current configuration
		Iterator<ConfigurationListener> e = getBundle().getConfigurationListeners().iterator();
		while(e.hasNext())
			ConfigurationManager.getBundle().removeConfigurationListener(e.next());
		
		//set port to match new active device
		for(int i = 0; i <= devices.size(); i++)
		{
			String name = "";
			if(i != 0)
				name = getBundle().getString("appium" + (i + 1) + ".capabilities.deviceName");
			else
				name = getBundle().getString("appium.capabilities.deviceName");

			if(deviceName.equals(name))
			{
				if(i != 0)
					getBundle().setProperty("remote.port", getBundle().getString("appium" + (i + 1) + ".capabilities.port"));
				else
					getBundle().setProperty("remote.port", getBundle().getString("appium.capabilities.port"));

				
				if(i != 0)
					TestBaseProvider.instance().get().setDriver("appium" + (i + 1) + "Driver");
				else
					TestBaseProvider.instance().get().setDriver("appiumDriver");
				
				currentDevice = i;
				
				System.out.println("[MultiDevice] Successfully switched to " + deviceName + ".");
				return;
			}
		}
		System.out.println("[MultiDevice] Did not switch to " + deviceName + ". It could not be found.");
	}
	
	/*
	 * Get the name of the currently executing device
	 */
	public static String getCurrentDevice()
	{
		try
		{
			return devices.get(currentDevice);
		}
		catch (IndexOutOfBoundsException ioobe)
		{
			System.out.println("[MultiDevice] There is no currently active device.");
			ioobe.printStackTrace();
		}
		return "";
	}

	/*
	 * Collect the logcat logs on each device and dump them into the logs directory.
	 * Also close down all Appium servers and their respective processes.
	 */
	@Override
	public void onFinish(ISuite suite)
	{
		//collect logcat logs and close all known active Appium servers
		for(int i = 0 ; i < services.size(); i++)
		{
			collectLogs(devices.get(i));
			services.get(i).stop();
			System.out.println("[MultiDevice] Stopped " + devices.get(i) + "'s server.");
		}
		devices.clear();
				
		try
		{
			if(isWindows())
				Runtime.getRuntime().exec("taskkill /F /IM node.exe");
			else if(isMac())
				Runtime.getRuntime().exec("killall node");
		}
		catch (IOException ioe) { }
	}
	
	/*
	 *  Collects logcat logs for the given device s
	 */
	private void collectLogs(String s)
	{
		//build filepath
		String filepath = System.getProperty("user.dir") +
				File.separator + "logs" + File.separator +
				new SimpleDateFormat("yyyy_MM_dd_HH_mm").format(new Date()).toString() +
				"_" + s + "_log.txt";
		System.out.println("[MultiDevice] Attempting to dump logs to " + filepath);
		
		//try to collect logs
		//also create parsed logs
		try
		{
			//prepare user-given options
			String buffers = getBundle().getString("log.buffers");
			String bufferTag = buffers == "" ? "" : " -v ";
			String prefilters = getBundle().getString("log.filter.pre");
			
			//execute the dump to a file
			ProcessBuilder pb = new ProcessBuilder("adb", "-s", s, "logcat", "-d", bufferTag, buffers, prefilters);
			File dumpTarget = new File(filepath);
			pb.redirectOutput(dumpTarget);
			Process p = pb.start();
			
			//wait for the dump to end, then create a regex-parsed copy of the dump file
			p.waitFor();
//			LogFilterUtil.screen(dumpTarget, "_filtered", getBundle().getString("log.filter.post").split("\\s"));
		}
		catch (IOException ioe)
		{
			System.out.println("[MultiDevice] Failed to collect logs for " + s + ".");
			ioe.printStackTrace();
			return;
		}
		catch (InterruptedException ie)
		{
			System.out.println("[MultiDevice] Log collection was interrupted for " + s + ".");
			ie.printStackTrace();
		}
		System.out.println("[MultiDevice] Successfully collected logs for " + s + ".");
	}
	
	/*
	 *  Checks if a given port is available for use
	 */
	private boolean isAvailable(int port)
	{
		Socket s = null;
		try
		{
			s = new Socket("localhost", port);
			
			System.out.println("[MultiDevice] " + port + " is not available.");
			return false;
		}
		catch (IOException ioe)
		{
			System.out.println("[MultiDevice] " + port + " is available.");
			return true;
		}
		finally
		{
			if(s != null)
			{
				try { s.close(); }
				catch(IOException ioe)
				{
					throw new RuntimeException("Fatal Error in isAvailable(int)");
				}
			}
		}
	}
	
	/*
	 *  Start an Appium server instance with the given device on the given port
	 */
	private void initAppium(String devName, int port) throws Exception
	{
		//fill out arguments
		AppiumServiceBuilder builder = new AppiumServiceBuilder();
		
		String platform = isWindows() ? "windows" : isMac() ? "mac" : "";
		if(platform == "")
			throw new Exception("OS is neither Windows or Mac.");
		else
			builder.withAppiumJS(new File(getBundle().getString("appium.serverpath." + platform)));
		
		builder.withArgument(GeneralServerFlag.UIID, devName);
		builder.withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, (port + 1) +"");
		builder.withIPAddress("127.0.0.1").usingPort(port);
		
		//build service and start
		AppiumDriverLocalService adls = builder.build();
		services.add(adls);
		adls.start();
		
		System.out.println("[MultiDevice] Appium service started on localhost:" + port + " for " + devName + ".");
		
		//attempt to clear logcat logs
		devices.add(devName);
		try
		{
			Runtime.getRuntime().exec("adb -s " + devName + " logcat all -c");
		}
		catch(IOException ioe)
		{
			System.out.println("[MultiDevice] Failed to clear logs on " + devName + ".");
			ioe.printStackTrace();
			return;
		}
		System.out.println("[MultiDevice] Successfully cleared logs on " + devName + ".");
	}
	
	/*
	 *  Helper methods that check the currently executing OS
	 */
	private boolean isWindows()
	{
		return System.getProperty("os.name").toLowerCase().indexOf("win") >= 0;
	}
	private boolean isMac()
	{
		return System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0;
	}
	
}
