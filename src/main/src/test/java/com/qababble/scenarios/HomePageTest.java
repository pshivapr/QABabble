package com.qababble.scenarios;

import java.lang.reflect.Method;

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
		page.ClickText("Create a Meetup");
		page.WaitForElement(10, "@Start a Meetup Today");
		page.Assert("title", "Start a Meetup Today!");
		qababble.assertTest(testName, "meetupBody");		
	}
	
	@Test
	public void get_app() throws Exception {
		page.ClickText("Get the app");
		page.WaitForElement(10, "@Meet the new Meetup");
		page.Assert("title", "Meet the new Meetup | Meetup");
		qababble.assertTest(testName, "meetupBody");	
	}
	
	@Test
	public void sign_up() throws Exception {
		page.ClickText("Sign up");
		page.WaitForElement("@class=view view--modalSnap");
		qababble.assertTest(testName, "@class=view view--modalSnap");	
	}
	
	@AfterMethod
	public void testSetup() throws Exception {
		page.URL(qababble.qababble);
	}
	
	@AfterClass
	public void quit() throws Exception {
		page.Quit();
	}
}
