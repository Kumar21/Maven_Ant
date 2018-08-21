package artifact01;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestFindelement {

	WebDriver driver;
	public void invoke() {
		try {
			System.setProperty("webdriver.chrome.driver", "D:\\\\Selenium\\\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.get("https://www.google.co.in");
			driver.findElement(By.name("q")).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeWindow() {
		try {
			Thread.sleep(500);
			driver.quit();
			} catch (Exception e) {
			
		}
	}
	
	public static void main(String[] args) {
		TestFindelement tf = new TestFindelement();
		tf.invoke();
		tf.closeWindow();
	}
	
	
}
