#Author: Shiva.Pasham@arm.com
#Date  : 03/08/2017
@Loginpage

Feature: Login scenario
		As a user when I should be able to login with valid credentials or throw error on failure
 
Scenario Outline: invalid credentials
 		Given Meetup page exists
		When I click on login button and input credentials email <email> and pass <pass>
		And I click on submit button
		Then I should get an error <assertFile>
		
		  Examples: 
		  | email				                 | pass                      | assertFile                  |
		  | null                         | null                      | login_blank_credentials     |
		 # | invalid  				    	       | invalid									 | login_invalid_email_or_pass |		  
		  | fail@gmail.com 							 | null               			 | login_blank_pass            |
		  
Scenario Outline: valid credentials
 		Given Meetup page exists
		When I click on login button and input credentials email <email> and pass <pass>
		And I click on submit button
		Then I should be taken to homepage <assertFile>
		
		  Examples: 
		  | email				                 | pass                      | assertFile     |
		  | shivaprasad.pasham@gmail.com | T3mpPa55w0rd              | valid_login    | 
