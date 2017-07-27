package com.qababble.scenarios;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.*;

import com.codesp.framework.Selenium;
import com.qababble.objects.QABabbleHomePage;

public class LoginFailureTest {
	Selenium page = new Selenium();
	
	String testName;
	QABabbleHomePage qababble = new QABabbleHomePage();

	@BeforeClass
	public void setup() throws Exception {
		qababble.setup();
		page.ClickText("Log in");
		qababble.assertTest("login.txt", "paneLogin");
	}
	
	@BeforeMethod
	public void printMethodName(Method method) {
		testName = method.getName();
		System.out.println(System.lineSeparator()+"***Running TestCase: "+testName);
	}
	
	@Test
	public void login_blank_credentials() throws Exception {
		qababble.login("","");
		qababble.assert_login_failure(testName);
	}
	
	@Test
	public void login_blank_email() throws Exception {
		qababble.login("","somepass");
		qababble.assert_login_failure(testName);
	}
	
	@Test
	public void login_blank_pass() throws Exception {
		qababble.login("meetup@hello.com","");
		qababble.assert_login_failure(testName);
	}
	
	@Test
	public void login_invalid_email_or_pass() throws Exception {
		qababble.login("meetup@hello.com","test");
		qababble.assert_login_failure(testName);
	}
	
	@AfterMethod
	public void testSetup(ITestResult result) throws Exception {
	    if (result.getStatus() == ITestResult.FAILURE) {
	    	System.out.println(System.lineSeparator()+result.getName()+" -- FAILED!");
	    	page.takeScreenshot(result.getName()+"_FAILED_"+page.utils().getCurrentDateTime("HH_mm_ss"));
	    	qababble.screenCapture();
	    	page.Quit();
	    } 
	}
	
	@AfterClass
	public void quit() throws Exception {
		page.Quit();
	}
}
