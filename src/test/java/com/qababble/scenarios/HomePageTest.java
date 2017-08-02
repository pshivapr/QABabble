package com.qababble.scenarios;

import java.lang.reflect.Method;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.codesp.framework.*;
import com.codesp.reporting.*;
import com.qababble.objects.QABabbleHomePage;

public class HomePageTest {
	Selenium page = new Selenium();
	QABabbleHomePage qababble = new QABabbleHomePage();
	EnvironmentProperties env = new EnvironmentProperties();
	Reporter report = new Reporter();
	Timer timer = new Timer();
	
	String testName;
	
    @BeforeMethod
	public void printMethodName(Method method) throws Exception {
		testName = method.getName();
		System.out.println(System.lineSeparator()+"***Running TestCase: "+testName);
	}
	
	@BeforeClass
	public void OpenBrowser() throws Exception {
		qababble.setup();
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
	public void create_meetup() throws Exception {
		page.WaitForElement(30, "@Create a Meetup");
		timer.startTimer(testName);
		page.ClickText("Create a Meetup");
		page.WaitForPageToLoad(10);
		page.Assert("title", "Start a Meetup Today!");
		qababble.assertTest(testName, "meetupBody");
		stopTimer();
	}
	
	@Test
	public void get_app() throws Exception {
		page.WaitForElement(30, "@Get the app");
		timer.startTimer(testName);
		page.ClickText("Get the app");
		page.WaitForPageToLoad(10);
		page.Assert("title", "Meet the new Meetup | Meetup");
		qababble.assertTest(testName, "meetupBody");
		stopTimer();
	}
	
	@Test
	public void sign_up() throws Exception {
		page.WaitForElement(30, "@Sign up");
		timer.startTimer(testName);
		page.ClickText("Sign up");
		page.WaitForElement(10, "@class=view view--modalSnap");
		qababble.assertTest(testName, "@class=view view--modalSnap");
		stopTimer();
	}
	
	@AfterMethod
	public void testSetup(ITestResult result) throws Exception {
	    if (result.getStatus() == ITestResult.FAILURE) {
	    	System.out.println(System.lineSeparator()+result.getName()+" -- FAILED!");
	    	page.takeScreenshot(result.getName()+"_FAILED_"+page.utils().getCurrentDateTime("HH_mm_ss"));
	    	qababble.screenCapture();
	    } try {
	    	page.URL(qababble.qababble);
	    } catch (AssertionError e) {
	    	
	    }
	}
	
	@AfterClass
	public void quit() throws Exception {
		qababble.quit();
	}
}
