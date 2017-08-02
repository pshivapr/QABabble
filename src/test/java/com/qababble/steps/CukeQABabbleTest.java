package com.qababble.steps;

import com.codesp.framework.*;
import com.codesp.reporting.*;
import com.qababble.objects.QABabbleHomePage;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CukeQABabbleTest {

	QABabbleHomePage qababble = new QABabbleHomePage();
	EnvironmentProperties env = new EnvironmentProperties();
	Selenium page = new Selenium();
	Reporter report = new Reporter();
	Timer timer = new Timer();
	
	public void stopTimer(String testName) throws Exception {
		long value = timer.stopAndGetTime();
		report.connectToInflux();
		report.addTag("TestName", testName);
		report.addTag("TestStatus", "PASS");
		report.addTag("Build", env.getParam("buildID"));
		report.writeToInfluxDB("QABabble", "TDemo", value);
	}
	
	@Given("^Meetup page exists$")
	public void meetup_page_exists() throws Exception {
		qababble.setup();
	}
	
	@When("^I click on link (.+)$")
	public void click_link(String linkText) throws Exception {
		page.ClickText(linkText);
	}
	
	@Then("^I should be navigated to page with title (.+) containing element (.+)$")
	public void display_page(String title, String element) throws Exception {
		page.Assert("title", title);
		page.Assert("element", element);
	}
	
	@Then("^I should be navigated to page with title (.+)$")
	public void display_page(String title) throws Exception {
		page.Assert("title", title);
		page.Quit();
	}
	
	@Then("^I should be navigated to page with element (.+) and assertion (.+)$")
	public void navigate_page_and_assert(String element, String filename) throws Exception {
		timer.startTimer(filename);
		qababble.assertTest(filename, element);
		stopTimer(filename);
	}
	
	@When("^I click on login button and input credentials email (.+) and pass (.+)$")
	public void click_login_and_input_credentials(String email, String pass) throws Exception {
		String Email, Pass;
		if(email.equals("null")) {
			Email = "";
		} else {
			Email = email;
		}
		if(pass.equals("null")) {
			Pass = "";
		} else {
			Pass = pass;
		}
		page.ClickText("Log in");
		page.Input("email", Email);
		page.Input("password", Pass);
	}
	
	@And("^I click on submit button$")
	public void click_submit_button() throws Exception {
		page.ClickElement("@value=Log in");
	}
	
	@Then("^I should get an error (.+)$")
	public void assert_login_failure(String assertFile) throws Exception {
		timer.startTimer(assertFile);
		qababble.assert_login_failure(assertFile);
		stopTimer(assertFile);
	}
	
	@Then("^I should be taken to homepage (.+)$")
	public void assert_login_pass(String assertFile) throws Exception {
		timer.startTimer(assertFile);
		qababble.assert_login_pass(assertFile);
		stopTimer(assertFile);		
	}
	
	@After
	public void quit() throws Exception {
		qababble.quit();
	}
	
}
