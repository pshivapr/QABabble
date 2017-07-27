package com.qababble.runners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/*
 * Test execution class for Contacts API features 
 */
	@RunWith(Cucumber.class)
	@CucumberOptions(
			plugin = { "html:target/reports/cucumber-html-report",
					   "json:target/reports/cucumber.json",
					   "junit:target/reports/cucumber-results.xml",
					   "pretty:target/reports/cucumber-pretty.txt"},
			features = {"src/test/resources/features/"},
	//		tags = {"@cS1, @cS4, @cS7, @cS10", "~@ignore"},
			//tags = {"@LeaveRequests", "~@ignore"},
				
			glue = {"scenarios", "com.arm.steps"}
			)
	
	public class CukesRunner {
	//	public static Logger log = Logger.getLogger(CucumberRunner.class.getName());	
		//public static Selenium page = new Selenium();
		//public static EnvironmentProperties env = new EnvironmentProperties();
		
		/*@Before
		public void i_launch_fiori_launchpad_url() throws Exception {
			page.Browser(env.setBrowser());
			page.MaxBrowser();
			page.Speed(5);
			page.URL(env.setBaseURL());
			page.ClickText("Cancel");
		}
		
		@After
		public void quit() throws Exception {
			page.Quit();
		}*/
		
    }
