package com.qababble.scenarios;

import java.lang.reflect.Method;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.codesp.framework.*;
import com.codesp.reporting.*;
import com.qababble.objects.QABabbleHomePage;

public class LoginFailureTest {
	Selenium page = new Selenium();

	Reporter report = new Reporter();
	Timer timer = new Timer();
	
	String testName;
	QABabbleHomePage qababble = new QABabbleHomePage();
	EnvironmentProperties env = new EnvironmentProperties();

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
	
	public void stopTimer() throws Exception {
		long value = timer.stopAndGetTime();
		report.connectToInflux();
		report.addTag("TestName", testName);
		report.addTag("TestStatus", "PASS");
		report.addTag("Build", env.getParam("buildID"));
		report.writeToInfluxDB("QABabble", "TDemo", value);
	}
	
	@Test
	public void login_blank_credentials() throws Exception {
		qababble.login("","");
		timer.startTimer(testName);
		qababble.assert_login_failure(testName);
		stopTimer();
	}
	
	@Test
	public void login_blank_email() throws Exception {
		qababble.login("","somepass");
		timer.startTimer(testName);
		qababble.assert_login_failure(testName);
		stopTimer();
	}
	
	@Test
	public void login_blank_pass() throws Exception {
		qababble.login("meetup@hello.com","");
		timer.startTimer(testName);
		qababble.assert_login_failure(testName);
		stopTimer();
	}
	
	@Test
	public void login_invalid_email_or_pass() throws Exception {
		qababble.login("meetup@hello.com","test");
		timer.startTimer(testName);
		qababble.assert_login_failure(testName);
		stopTimer();
	}
	
	@AfterMethod
	public void testSetup(ITestResult result) throws Exception {
	    if (result.getStatus() == ITestResult.FAILURE) {
	    	System.out.println(System.lineSeparator()+result.getName()+" -- FAILED!");
	    	page.takeScreenshot(result.getName()+"_FAILED_"+page.utils().getCurrentDateTime("HH_mm_ss"));
	    	qababble.screenCapture();
	    	//page.Quit();
	    } 
	}
	
	@AfterClass
	public void quit() throws Exception {
		qababble.quit();
	}
}
