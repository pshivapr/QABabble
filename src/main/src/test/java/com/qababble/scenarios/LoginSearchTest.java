package com.qababble.scenarios;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.*;

import com.codesp.framework.Selenium;
import com.qababble.objects.QABabbleHomePage;

public class LoginSearchTest {
	
	private static Selenium page = new Selenium();
	private static QABabbleHomePage qababble = new QABabbleHomePage();
	private String testName;
	
	@BeforeMethod
	public void printMethodName(Method method) {
		testName = method.getName();
		System.out.println(System.lineSeparator()+"***Running TestCase: "+testName);
	}
	
	@Test (priority=1)
	public void valid_login() throws Exception {
		qababble.setup();
		page.WaitForElement(10, "@Log in");
		page.ClickText("Log in");
		qababble.login("shivaprasad.pasham@gmail.com", "T3mpPa55w0rd");
		qababble.assert_login_pass(testName);
	}
	
	@Test (priority=2)
	public void search_meetup() throws Exception {
		String eventContainer = "@class=event-listing-container";
		page.WaitForElement(10, "mainKeywords");
		page.Input("mainKeywords", "planit");
		//page.keyPressReturn();
		page.ClickElement("@class=icon-search");
		page.WaitForElement(10,  "//*[@id='__pagerInitiator' and contains(@style, 'top: 63')]");
		page.WaitForElement("//*[@class='event-listing-container']//span[text()='QA Babble 5 - Test Automation in Focus - Sponsored by Planit']");
		qababble.assertTest(testName, eventContainer);
	}
	
	@Test (priority=3)
	public void logout() throws Exception {
		page.ClickElement("profileNav");
		page.ClickText("Log out");
		page.WaitForElement(10, "@Meet the new Meetup");
		page.Assert("title", "Meet the new Meetup | Meetup");
	}
	
	@AfterMethod
	public void testSetup(ITestResult result) throws Exception {
	    if (result.getStatus() == ITestResult.FAILURE) {
	    	System.out.println(System.lineSeparator()+result.getName()+" -- FAILED!");
	    	page.takeScreenshot(result.getName()+"_FAILED_"+page.utils().getCurrentDateTime("HH_mm_ss"));
	    	qababble.screenCapture();
	    } 
	}
	
	@AfterClass
	public void quit() throws Exception {
		page.Quit();
	}
	
}
