package artifact01;

import java.util.Scanner;

public class Samplehello {
 public static void main(String[] args) {
	 	
	 System.out.println("Hello maven");
	 int ff = new Samplehello().TFC(5);
	System.out.println(ff);
 }
 
 public int TFC(int a) {
	 if (a==0 || a==1) {
		 return 1;
	 }
	 else {
	 return a * TFC(a-1);}
 }
 public void TPR(int n) {

	 
 }
 
}
