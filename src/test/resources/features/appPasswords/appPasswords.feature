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

  @GetAllAppPasswordsById @GetAllAppPasswordsById2 @Smoke
  Scenario: A user with proper role should be able to retrieve all te app passwords from the request user
    Given I am authorized with a user with "administrator" role
    When I make a request to retrieve all app passwords from the request user
    Then response should be "HTTP/1.1 200 OK"
      And response should be valid and have a body
      And item with the name of the app-password created should be retrieved

  @GetAppPasswordsByIdByUuid @GetAppPasswordsByIdByUuid2 @Smoke
  Scenario: A user with proper role should be able to retrieve an specific app passwords from the request user
    Given I am authorized with a user with "administrator" role
    When I make a request to retrieve an app passwords from the request user by its uuid
    Then response should be "HTTP/1.1 200 OK"
      And response should be valid and have a body
      And item with the name of the app-password created should be retrieved
      And item with the uuid of the app-password created should be retrieved





  @GetAllAppPasswordsById @GetAllAppPasswordsById2 @Smoke
  Scenario: A user with proper role should be able to retrieve all te app passwords from the request user
    Given I am authorized with a user with "administrator" role
    When I make a request to retrieve all app passwords from the request user
    Then response should be "HTTP/1.1 200 OK"
    And response should be valid and have a body
    And item with the name of the app-password created should be retrieved





