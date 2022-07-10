@Statuses @Acceptance
Feature: Statuses

  @GetAllStatuses @Smoke
  Scenario Outline: A user with proper role should be able to retrieve all statuses
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve all statuses
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And response body has to have the following status
      | status 1 | status 2 | status 3 | status 4 | status 5 | status 6 |
      | publish  |  future  | draft    | pending  |  private |  trash   |

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
