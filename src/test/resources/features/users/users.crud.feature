@Users
Feature: Users

  @GetAllUsers @Smoke
  Scenario Outline: A user with proper role should be able to retrieve all users
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve all users
    Then response should be "<Status Line>"
      And response should be valid and have a body
      And response should have proper amount of users

  Examples:
    | User Role     | Status Line     |
    | administrator | HTTP/1.1 200 OK |

  @CreateUser @Smoke
  Scenario Outline: A user with proper role should be able to create a user
    Given I am authorized with a user with "<User Role>" role
    When I make a request to create a user with the following query params
      | username      | email                  | password   | roles         |
      | admin_test    | administrate@email.com | admin_test | administrator |
    Then response should be "<Status Line>"
      And response should be valid and have a body
      And username should be correct
      And email should be correct
      And role should be correct

  Examples:
    | User Role     | Status Line          |
    | administrator | HTTP/1.1 201 Created |

  @RetrieveAUser @Smoke
  Scenario Outline: A user with proper role should be able to retrieve a User by Id
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a user by Id
    Then response should be "<Status Line>"
      And response should be valid and have a body
      And proper user id should be returned
      And name should be correct
      And description should be correct

  Examples:
    | User Role     | Status Line     |
    | administrator | HTTP/1.1 200 OK |

  @UpdateUser @Smoke
  Scenario Outline: A user with proper role should be able to update a User by Id
    Given I am authorized with a user with "<User Role>" role
    When I make a request to update a user with the following query params
      | first_name      | last_name                | description        |
      | TESTNG Name     | TestNG Last Name Updated | TestNG description |
    Then response should be "<Status Line>"
      And response should be valid and have a body
      And proper user id should be returned
      And First Name should be correct
      And Last Name should be correct
      And description should be correct

  Examples:
    | User Role     | Status Line     |
    | administrator | HTTP/1.1 200 OK |

  @DeleteAUser @Smoke
  Scenario Outline: A user with proper role should be able to delete a User by Id
    Given I am authorized with a user with "<User Role>" role
    When I make a request to delete a user by Id
    Then response should be "<Status Line>"
      And response should be valid and have a body
      And user should be deleted
      And proper deleted user id should be returned

  Examples:
    | User Role     | Status Line     |
    | administrator | HTTP/1.1 200 OK |

  @RetrieveMe @Smoke
  Scenario Outline: A user with proper role should be able to retrieve your own user
    Given An authorized user with "<User Role>" role
    When I make a request to retrieve my own user
    Then response should be "<Status Line>"
      And response should be valid and have a body
      And proper user id should be returned
      And name should be correct
      And description should be correct

  Examples:
    | User Role     | Status Line     |
    | administrator | HTTP/1.1 200 OK |

  @UpdateMe @Smoke
  Scenario Outline: A user with proper role should be able to update his own user
    Given An authorized user with "<User Role>" role
    When He makes a request to update his own user with the following query params
      | first_name      | last_name                | description        |
      | TESTNG Name     | TestNG Last Name Updated | TestNG description |
    Then response should be "<Status Line>"
      And response should be valid and have a body
      And proper user id should be returned
      And First Name should be correct
      And Last Name should be correct
      And description should be correct

  Examples:
    | User Role     | Status Line     |
    | administrator | HTTP/1.1 200 OK |

  @DeleteMe @Smoke
  Scenario Outline: A user with proper role should be able to delete his own user
    Given An authorized user with "<User Role>" role
    When He makes a request to delete his own user
    Then response should be "<Status Line>"
      And response should be valid and have a body
      And user should be deleted
      And proper deleted user id should be returned

  Examples:
    | User Role     | Status Line     |
    | administrator | HTTP/1.1 200 OK |

  @RetrieveMeAsSubscriber @Smoke
  Scenario Outline: A user with proper role should be able to retrieve your own user
    Given An authorized user with "<User Role>" role
    When I make a request to retrieve my own user
    Then response should be "<Status Line>"
      And response should be valid and have a body
      And proper user id should be returned
      And name should be correct
      And description should be correct

  Examples:
    | User Role     | Status Line     |
    | subscriber    | HTTP/1.1 200 OK |

  @UpdateMeAsSubscriber @Smoke
  Scenario Outline: A user with proper role should be able to update his own user
    Given An authorized user with "<User Role>" role
    When He makes a request to update his own user with the following query params
      | first_name      | last_name                | description        |
      | TESTNG Name     | TestNG Last Name Updated | TestNG description |
    Then response should be "<Status Line>"
      And response should be valid and have a body
      And proper user id should be returned
      And First Name should be correct
      And Last Name should be correct
      And description should be correct

  Examples:
    | User Role     | Status Line     |
    | subscriber    | HTTP/1.1 200 OK |