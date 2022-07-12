@Statuses @Smoke
Feature: Statuses

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

  Scenario Outline: A user with proper role should be able to retrieve a status by its status name
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a status by its "<Status Name>"
    Then response should be "<Status Line>"
      And response should be valid and have a body
      And response body has to have the "<Status Name>" sent in the request

    Examples:
      | User Role     | Status Line     | Status Name |
      | administrator | HTTP/1.1 200 OK | publish     |
      | administrator | HTTP/1.1 200 OK | future      |
      | administrator | HTTP/1.1 200 OK | draft       |
      | administrator | HTTP/1.1 200 OK | pending     |
      | administrator | HTTP/1.1 200 OK | private     |
      | administrator | HTTP/1.1 200 OK | trash       |






