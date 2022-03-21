@Automation
@UI @Test
Feature: Validate Listing car details 

  Scenario: As a user search and exsiting used listing car and validate their details 
  
    Given "UIweburl" of trademe website to launch them and browse
    And search the details of "2009 Suzuki Swift Sport - White" car
    Then validate the below details
    | NumberPlate | Kilometers    | Body      | Seats |
    | JEN240      | 137,596km     | Hatchback | 5     |
