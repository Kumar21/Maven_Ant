package com.java.test.annotation;

import org.testng.annotations.Test;

public class TestNGAttributes {
  @Test(priority=0)
  public void f() {
  }
  @Test(dependsOnMethods={"openBrowser"}) // will execute at last
  public void loginToFacebook() {
  }
  @Test(priority=5)
  public void s() {
  }
  @Test
  public void e() {
  }
  @Test(priority=6)
  public void h() {  
  }
  @Test(priority=false) // will not execute
  public void t() {
  }
  @Test
  public void k() {
  }
  
  @Test
  public void openBrowser() {
	  System.out.println("this method");
  }
}
