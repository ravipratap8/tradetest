@Automation
@API @Test2

 #This API requires access token, without that it will fail with unauthorized error
Feature: Create a new listing  

  Scenario: As a user create a listing of category 0002-0356-0032-2273- and verify the listing was created 
  
    Given "test2Url" to hit the api and test
    And create request body with input parameter listing id "0002-0356-0032-2273-"
    When we use "POST" method and send the API request
    Then verify the success response and ensure the list is created 
