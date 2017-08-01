package com.qababble.scenarios;

import java.lang.reflect.Method;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.codesp.framework.*;
import com.codesp.reporting.*;
import com.qababble.objects.QABabbleHomePage;

public class LoginSearchTest {
	
	private static Selenium page = new Selenium();
	private static QABabbleHomePage qababble = new QABabbleHomePage();
	EnvironmentProperties env = new EnvironmentProperties();
	private String testName;

	Reporter report = new Reporter();
	Timer timer = new Timer();
	
	@BeforeMethod
	public void printMethodName(Method method) {
		testName = method.getName();
		System.out.println(System.lineSeparator()+"***Running TestCase: "+testName);
	}
	
	public void stopTimer() throws Exception {
		long value = timer.stopAndGetTime();
		report.connectToInflux();
		report.addTag("TestName", testName);
		report.addTag("TestStatus", "PASS");
		report.addTag("Build", env.getParam("build"));
		report.writeToInfluxDB("QABabble", "TDemo", value);
	}
	
	@Test (priority=1)
	public void valid_login() throws Exception {
		qababble.setup();
		timer.startTimer(testName);
		page.ClickText("Log in");
		qababble.login("shivaprasad.pasham@gmail.com", "T3mpPa55w0rd");
		qababble.assert_login_pass(testName);
		stopTimer();
	}
	
	@Test (priority=2)
	public void search_meetup() throws Exception {
		String eventContainer = "@class=event-listing-container";
		page.WaitForElement(10, "mainKeywords");
		page.Input("mainKeywords", "planit");
		timer.startTimer(testName);
		page.ClickElement("@class=icon-search");
		page.WaitForElement(30,  "//*[@id='__pagerInitiator' and contains(@style, 'top: 6')]");
		page.WaitForElement("//*[@class='event-listing-container']//span[text()='QA Babble 5 - Test Automation in Focus - Sponsored by Planit']");
		stopTimer();
		qababble.assertTest(testName, eventContainer);
	}
	
	@Test (priority=3)
	public void logout() throws Exception {
		page.WaitForElement(10, "profileNav");
		page.ClickElement("profileNav");
		timer.startTimer(testName);
		page.ClickText("Log out");
		page.Assert("title", "Meet the new Meetup | Meetup");
		stopTimer();
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
		qababble.quit();
	}
	
}
