package com.qababble.runners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/*
 * Test execution class for Demo features 
 */
	@RunWith(Cucumber.class)
	@CucumberOptions(
			plugin = { "html:target/reports/cucumber-html-report",
					   "json:target/reports/cucumber.json",
					   "junit:target/reports/cucumber-results.xml",
					   "pretty:target/reports/cucumber-pretty.txt"},
			features = {"src/test/resources/features/"},
			//tags = {"@Homepage", "@Loginpage", "~@ignore"},
				
			glue = {"scenarios", "com.qababble.steps"}
			)
	
	public class CukesRunner {
		
    }
