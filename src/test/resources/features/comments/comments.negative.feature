@Comments @Negative
Feature: Comments Negative Tests

  @GetAllCommentsError401 @Test
  Scenario Outline: A user without authorization should not be able to retrieve all comments
    Given I am using a token with "<Token Value>"
    When I make a request to retrieve all comments
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following keys and values
      | status    | error   | code   |  error_description  |
      | <Status>  | <Error> | <Code> | <Error description> |

    Examples:
      | Token Value           | Status Line               | Status | Error             | Code | Error description         |
      | invalid JWT signature | HTTP/1.1 401 Unauthorized | error  | INVALID_SIGNATURE | 401  | JWT Signature is invalid. |
      | incorrect JWT format  | HTTP/1.1 401 Unauthorized | error  | SEGMENT_FAULT     | 401  | Incorrect JWT Format.     |
