@AppPasswords @Acceptance
Feature: App_Passwords

<<<<<<< HEAD
=======

>>>>>>> app-password
  @Before_CreateAnUniqueUserAdministrator @After_DeleteUserById @Smoke
  Scenario: A user with proper role should be able to create an app password
    Given I am authorized with a user with "administrator" role
    When I make a request to create an app password with the following query params
      | name          |
      | uniqueAppName |
    Then response should be "HTTP/1.1 201 Created"
      And response should be valid and have a body
      And name attribute should be the same as the value delivered

<<<<<<< HEAD
=======

>>>>>>> app-password
  @Before_CreateAnUniqueUserAdministrator @Before_CreateAnAppPasswordById @After_DeleteUserById @Smoke
  Scenario: A user with proper role should be able to retrieve all te app passwords from the request user
    Given I am authorized with a user with "administrator" role
    When I make a request to retrieve all app passwords from the request user
    Then response should be "HTTP/1.1 200 OK"
      And response should be valid and have a body
      And item with the name of the app-password created should be retrieved

<<<<<<< HEAD
=======

>>>>>>> app-password
  @Before_CreateAnUniqueUserAdministrator @Before_CreateAnAppPasswordById @After_DeleteUserById @Smoke
  Scenario: A user with proper role should be able to retrieve an specific app passwords from the request user
    Given I am authorized with a user with "administrator" role
    When I make a request to retrieve an app passwords from the request user by its uuid
    Then response should be "HTTP/1.1 200 OK"
      And response should be valid and have a body
      And item with the name of the app-password created should be retrieved
      And item with the uuid of the app-password created should be retrieved

  @Before_CreateAnUniqueUserAdministrator @Before_CreateAnAppPasswordById @After_DeleteUserById @Smoke
  Scenario: A user with proper role should be able to update an specific app passwords from the request user
    Given I am authorized with a user with "administrator" role
    When I make a request to update an app passwords from the request user by its uuid with the following parameters
      | name               |
      | newUniqueAppName   |
    Then response should be "HTTP/1.1 200 OK"
      And response should be valid and have a body
      And name attribute should be the same as the value delivered
      And item with the uuid of the app-password created should be retrieved

  @Before_CreateAnUniqueUserAdministrator @Before_CreateAnAppPasswordById @After_DeleteUserById @Smoke
  Scenario: A user with proper role should be able to delete an specific app passwords from the request user
    Given I am authorized with a user with "administrator" role
    When I make a request to delete an app passwords from the request user by its uuid
    Then response should be "HTTP/1.1 200 OK"
      And response should be valid and have a body
      And returned deleted attribute should be "true"
      And item with the uuid of the app-password created should be retrieved

  @Before_CreateAnUniqueUserAdministrator @Before_CreateAnAppPasswordById @After_DeleteUserById @Smoke
  Scenario: A user with proper role should be able to delete all app passwords from the request user
    Given I am authorized with a user with "administrator" role
    When I make a request to delete all app passwords from the request user
    Then response should be "HTTP/1.1 200 OK"
    And response should be valid and have a body
    And returned deleted attribute should be "true"

  @Before_CreateAnUniqueUserAdministrator @After_DeleteUserById @Regression
  Scenario: A user with proper role should not be able to retrieve all the app passwords from the request user without authentication
    Given I make a request to retrieve all app passwords from the request user without authentication
    Then response should be "HTTP/1.1 401 Unauthorized"
    And response should be invalid and have a body
    And response body should contain the "MISSING_AUTHORIZATION_HEADER"

  @Regression
  Scenario: A user with proper role should not be able to create an app password by an invalid user id
    Given I am authorized with a user with "administrator" role
    When I make a request to create an app password with an invalid user id and the following query params
      | name          |
      | uniqueAppName |
    Then response should be "HTTP/1.1 404 Not Found"
    And response should be invalid and have a body
    And response body should contain the "Invalid user ID."

  @Before_CreateAnUniqueUserAdministrator @After_DeleteUserById @Regression
  Scenario: A user with proper role should not be able to retrieve an app password by an invalid app password uuid
    Given I am authorized with a user with "administrator" role
    When I make a request to retrieve an app passwords from the request user by an non existent uuid
    Then response should be "HTTP/1.1 404 Not Found"
    And response should be invalid and have a body
    And response body should contain the "Application password not found."

  @Before_CreateAnUniqueUserAdministrator @Before_CreateAnAppPasswordById @After_DeleteUserById @Regression
  Scenario: A user with proper role should not be able to create an app password with the same name than other app password
    Given I am authorized with a user with "administrator" role
    When I make a request to create an app password with the same name than the already app password created
    Then response should be "HTTP/1.1 409 Conflict"
    And response should be invalid and have a body
    And response body should contain the "Each application name should be unique."

  @Before_CreateAnUniqueUserAdministrator @Before_CreateAnAppPasswordById @After_DeleteUserById @After_DeleteSubscriberUserById @Regression
  Scenario: A user with subscriber role should not be able to get all app password of any other user
    Given I create a user with a subscriber role
<<<<<<< HEAD
    And I am authorized with a user with "subscriber" role
    When I make a request to retrieve all app passwords from the request user
    Then response should be "HTTP/1.1 403 Forbidden"
    And response should be invalid and have a body
    And response body should contain the "Sorry, you are not allowed to list application passwords for this user."

  @Before_CreateAnUniqueUserAdministrator @Before_CreateAnAppPasswordById @After_DeleteUserById @After_DeleteSubscriberUserById @Regression
  Scenario: A user with subscriber role should not be able to delete all app password of any other user
    Given I create a user with a subscriber role
    And I am authorized with a user with "subscriber" role
    When I make a request to delete all app passwords from the request user
    Then response should be "HTTP/1.1 403 Forbidden"
    And response should be invalid and have a body
    And response body should contain the "Sorry, you are not allowed to delete application passwords for this user."
=======
      And I am authorized with a user with "subscriber" role
    When I make a request to retrieve all app passwords from the request user
    Then response should be "HTTP/1.1 403 Forbidden"
      And response should be invalid and have a body
      And response body should contain the "Sorry, you are not allowed to list application passwords for this user."

>>>>>>> app-password







