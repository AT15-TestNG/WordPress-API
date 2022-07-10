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

  @GetStatusByName @Smoke
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

  @GetAllStatusWithoutToken @Regression
  Scenario Outline: A user with proper role should not be able to retrieve all the statuses without authentication
    Given I make a request to retrieve all statuses without authentication
    Then response should be "<Status Line>"
      And response should be invalid and have a body
      And response body should contain the "<Error Message>"

    Examples:
      | Status Line               | Error Message               |
      | HTTP/1.1 401 Unauthorized | MISSING_AUTHORIZATION_HEADER|

  @GetStatusByInvalidName @Regression
  Scenario Outline: A user with proper role should not be able to retrieve a Status with an invalid status Name
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a status by its "<Status Name>"
    Then response should be "<Status Line>"
      And response should be invalid and have a body
      And response body should contain the "<Error Message>"

    Examples:
      | User Role     | Status Line            | Status Name | Error Message  |
      | administrator | HTTP/1.1 404 Not Found | invalidName | Invalid status.|


  @GetStatusByNameSubscriberUser @Regression
  Scenario Outline: A user with subscriber role should not be able to retrieve a Status with status name different than publish
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a status by its "<Status Name>"
    Then response should be "<Status Line>"
      And response should be invalid and have a body
      And response body should contain the "<Error Message>"

    Examples:
      | User Role     | Status Line            | Status Name | Error Message      |
      | subscriber    | HTTP/1.1 403 Forbidden | future      | Cannot view status.|
      | subscriber    | HTTP/1.1 403 Forbidden | draft       | Cannot view status.|
      | subscriber    | HTTP/1.1 403 Forbidden | pending     | Cannot view status.|
      | subscriber    | HTTP/1.1 403 Forbidden | private     | Cannot view status.|
      | subscriber    | HTTP/1.1 403 Forbidden | trash       | Cannot view status.|




