package com.htmlunittesing;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;	
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.JavascriptExecutor;

public class HtmlUnitTest {
	private static WebDriver driver;
	JavascriptExecutor jse;
		
	public void invokeDriver() {
			 driver = new HtmlUnitDriver();
			 driver.manage().deleteAllCookies();
			 driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			 driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS); }
				
	public void googleSearch() {
		    try {
				driver.get("http://www.google.com"); 
				WebElement element = driver.findElement(By.name("q"));	
				element.sendKeys("Infostretch");	
				element.submit();	       		
				System.out.println("Page title is: " + driver.getTitle());
			} catch (Exception e) {
				e.printStackTrace();
			}}
		
	
	public void sendQuerise() {
		try {
			driver.get("https://www.amazon.in");
			System.out.println("Title of the page "+driver.getTitle());
			driver.findElement(By.linkText("Today's Deals")).click();
			System.out.println(" Current URL "+ driver.getCurrentUrl());
			//System.out.println("Current page source is "+ driver.getPageSource());	
			}catch (Exception e) {
		}}
		
	public void closeDriver() {
			driver.quit();		}
	public static void main(String[] args) {
			HtmlUnitTest obj = new HtmlUnitTest();
			obj.invokeDriver();
			obj.sendQuerise();
			obj.closeDriver();
	}
}
