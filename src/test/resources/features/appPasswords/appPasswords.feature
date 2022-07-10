@AppPasswords @Acceptance
Feature: App_Passwords

  @CreateAnAppPassword @Smoke
  Scenario: A user with proper role should be able to create an app password
    Given I am authorized with a user with "administrator" role
    When I make a request to create an app password with the following query params
      | name          |
      | uniqueAppName |
    Then response should be "HTTP/1.1 201 Created"
    And response should be valid and have a body
    And name attribute should be the same as the value delivered


