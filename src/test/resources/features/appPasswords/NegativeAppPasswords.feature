@AppPasswords @Negative @Regression
Feature: App_Passwords Negative Tests

  @Before_CreateAnUniqueUserAdministrator @After_DeleteUserById
  Scenario: A user with proper role should not be able to retrieve all the app passwords from the request user without authentication
    Given I make a request to retrieve all app passwords from the request user without authentication
    Then response should be "HTTP/1.1 401 Unauthorized"
    And response to the app password request should be invalid and have a body
    And response body should contain the "MISSING_AUTHORIZATION_HEADER"

  @Regression
  Scenario: A user with proper role should not be able to create an app password by an invalid user id
    Given I am authorized with a user with "administrator" role
    When I make a request to create an app password with an invalid user id and the following query params
      | name          |
      | uniqueAppName |
    Then response should be "HTTP/1.1 404 Not Found"
    And response to the app password request should be invalid and have a body
    And response body should contain the "Invalid user ID."

  @Before_CreateAnUniqueUserAdministrator @After_DeleteUserById
  Scenario: A user with proper role should not be able to retrieve an app password by an invalid app password uuid
    Given I am authorized with a user with "administrator" role
    When I make a request to retrieve an app passwords from the request user by an non existent uuid
    Then response should be "HTTP/1.1 404 Not Found"
    And response to the app password request should be invalid and have a body
    And response body should contain the "Application password not found."

  @Before_CreateAnUniqueUserAdministrator @Before_CreateAnAppPasswordById @After_DeleteUserById
  Scenario: A user with proper role should not be able to create an app password with the same name than other app password
    Given I am authorized with a user with "administrator" role
    When I make a request to create an app password with the same name than the already app password created
    Then response should be "HTTP/1.1 409 Conflict"
    And response to the app password request should be invalid and have a body
    And response body should contain the "Each application name should be unique."

  @Before_CreateAnUniqueUserAdministrator @Before_CreateAnAppPasswordById @After_DeleteUserById @After_DeleteSubscriberUserById @Subscriber
  Scenario: A user with subscriber role should not be able to get all app password of any other user
    Given I create a user with a subscriber role for app password
    And I am authorized with a user with "subscriber" role
    When I make a request to retrieve all app passwords from the request user
    Then response should be "HTTP/1.1 403 Forbidden"
    And response to the app password request should be invalid and have a body
    And response body should contain the "Sorry, you are not allowed to list application passwords for this user."

  @Before_CreateAnUniqueUserAdministrator @Before_CreateAnAppPasswordById @After_DeleteUserById @After_DeleteSubscriberUserById @Subscriber
  Scenario: A user with subscriber role should not be able to delete all app password of any other user
    Given I create a user with a subscriber role for app password
    And I am authorized with a user with "subscriber" role
    When I make a request to delete all app passwords from the request user
    Then response should be "HTTP/1.1 403 Forbidden"
    And response to the app password request should be invalid and have a body
    And response body should contain the "Sorry, you are not allowed to delete application passwords for this user."