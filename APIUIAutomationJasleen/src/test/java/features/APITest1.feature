@Automation
@API @Test1
Feature: Retrieve list of charities 

  Scenario: As a user retrieve list of charities and verify that St John charity is present 
  
    Given "test1Url" to hit the api and test
    When we use "GET" method and send the API request
    Then verify the success response and validate the presences of "St John" charity 
