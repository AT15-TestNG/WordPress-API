@Comments
Feature: Comments

  @GetAllComments @Smoke
  Scenario Outline: A user with proper role should be able to retrieve all comments
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve all comments
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And response should have proper amount of comments

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |