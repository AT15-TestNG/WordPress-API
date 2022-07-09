@Pages @Acceptance
Feature: Pages

  @GetAllPages @Smoke
  Scenario Outline: A user with proper role should be able to retrieve all pages
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve all pages
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And response should have proper amount of pages

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
