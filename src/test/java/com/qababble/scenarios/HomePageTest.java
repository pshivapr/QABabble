package com.qababble.scenarios;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.*;

import com.codesp.framework.Selenium;
import com.qababble.objects.QABabbleHomePage;

public class HomePageTest {
	Selenium page = new Selenium();
	QABabbleHomePage qababble = new QABabbleHomePage();
	
	String testName;
	
    @BeforeMethod
	public void printMethodName(Method method) {
		testName = method.getName();
		System.out.println(System.lineSeparator()+"***Running TestCase: "+testName);
	}
	
	@BeforeClass
	public void OpenBrowser() throws Exception {
		qababble.setup();
	}
	
	@Test
	public void create_meetup() throws Exception {
		page.WaitForElement(30, "@Create a Meetup");
		page.ClickText("Create a Meetup");
		page.WaitForPageToLoad(10);
		page.Assert("title", "Start a Meetup Today!");
		qababble.assertTest(testName, "meetupBody");		
	}
	
	@Test
	public void get_app() throws Exception {
		page.WaitForElement(30, "@Get the app");
		page.ClickText("Get the app");
		page.WaitForPageToLoad(10);
		page.Assert("title", "Meet the new Meetup | Meetup");
		qababble.assertTest(testName, "meetupBody");	
	}
	
	@Test
	public void sign_up() throws Exception {
		page.WaitForElement(30, "@Sign up");
		page.ClickText("Sign up");
		page.WaitForElement(10, "@class=view view--modalSnap");
		qababble.assertTest(testName, "@class=view view--modalSnap");	
	}
	
	@AfterMethod
	public void testSetup(ITestResult result) throws Exception {
	    if (result.getStatus() == ITestResult.FAILURE) {
	    	System.out.println(System.lineSeparator()+result.getName()+" -- FAILED!");
	    	page.takeScreenshot(result.getName()+"_FAILED_"+page.utils().getCurrentDateTime("HH_mm_ss"));
	    	qababble.screenCapture();
	    } else {
	    page.URL(qababble.qababble);
	    }
	}
	
	@AfterClass
	public void quit() throws Exception {
		page.Quit();
	}
}
