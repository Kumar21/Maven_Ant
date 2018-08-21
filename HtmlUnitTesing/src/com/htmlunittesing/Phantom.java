package com.htmlunittesing;
import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;	
public class Phantom {

		public static void main(String[] args) {
			try {
				File file = new File("C:/Program Files/phantomjs-2.1.1-windows/bin/phantomjs.exe");				
				System.setProperty("phantomjs.binary.path", file.getAbsolutePath());		
				WebDriver driver = new PhantomJSDriver();	
				driver.get("http://www.google.com");         
				WebElement element = driver.findElement(By.name("q"));	
				element.sendKeys("Infostretch");					
				element.submit();         			
				System.out.println("Page title is: " + driver.getTitle());		
				driver.quit();
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
}
