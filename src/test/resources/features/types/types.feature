@Types
Feature: Posts

  @GetAllTypes @Smoke
  Scenario Outline: A user with proper role should be able to retrieve all types
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve all types
    Then response should be "<Status Line>"
      And response should be valid and have a body
      And response should have proper amount of types

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | subscriber    | HTTP/1.1 200 OK |

  @GetTypesByName @Smoke
  Scenario Outline: A user with proper role should be able to retrieve a post type
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a post type
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And response should be a post type

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
      | subscriber    | HTTP/1.1 200 OK |