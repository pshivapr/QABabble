#Author: Shiva.Pasham@arm.com
#Date  : 03/08/2017
@Homepage

Feature: Assert Homepage Links
		As a user I should be able to navigate to respective page on chosen link.
 
Scenario Outline: User navigating homepage links
 		Given Meetup page exists
		When I click on link <link_text>
		Then I should be navigated to page with element <element> and assertion <assertionfile>
		
		  Examples: 
		  | link_text             | element                      | assertionfile       |
		  | Create a Meetup       | meetupBody                   | create_meetup       |
		  | Get the app						| meetupBody                   | get_app             |
		  | Sign up					    	| @class=view view--modalSnap  | sign_up             |
