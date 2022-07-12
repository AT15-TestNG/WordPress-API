@Statuses @Negative @Regression
Feature:

  Scenario Outline: A user with proper role should not be able to retrieve all the statuses without authentication
    Given I make a request to retrieve all statuses without authentication
    Then response should be "<Status Line>"
    And response to the statuses request should be invalid and have a body
    And response body should contain the "<Error Message>"

    Examples:
      | Status Line               | Error Message               |
      | HTTP/1.1 401 Unauthorized | MISSING_AUTHORIZATION_HEADER|


  Scenario Outline: A user with proper role should not be able to retrieve a Status with an invalid status Name
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a status by its "<Status Name>"
    Then response should be "<Status Line>"
    And response to the statuses request should be invalid and have a body
    And response body should contain the "<Error Message>"

    Examples:
      | User Role     | Status Line            | Status Name | Error Message  |
      | administrator | HTTP/1.1 404 Not Found | invalidName | Invalid status.|


  @Before_CreateUserWithSubscriberRole @After_DeleteUserById @Subscriber
  Scenario Outline: A user with subscriber role should not be able to retrieve a Status with status name different than publish
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a status by its "<Status Name>"
    Then response should be "<Status Line>"
    And response to the statuses request should be invalid and have a body
    And response body should contain the "<Error Message>"

    Examples:
      | User Role     | Status Line            | Status Name | Error Message      |
      | subscriber2    | HTTP/1.1 403 Forbidden | future      | Cannot view status.|
      | subscriber2    | HTTP/1.1 403 Forbidden | draft       | Cannot view status.|
      | subscriber2   | HTTP/1.1 403 Forbidden | pending     | Cannot view status.|
      | subscriber2    | HTTP/1.1 403 Forbidden | private     | Cannot view status.|
      | subscriber2    | HTTP/1.1 403 Forbidden | trash       | Cannot view status.|