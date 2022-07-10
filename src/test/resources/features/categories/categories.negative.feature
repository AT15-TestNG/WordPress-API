@Categories @Negative
Feature: Categories Negative Tests

  @GetAllCategoriesError401
  Scenario Outline: A user without authorization should not be able to retrieve all categories
    Given I am using a token with "<Token Value>"
    When I make a request to retrieve all categories
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following keys and values
      | status    | error   | code   |  error_description  |
      | <Status>  | <Error> | <Code> | <Error description> |

    Examples:
      | Token Value           | Status Line               | Status | Error             | Code | Error description         |
      | invalid JWT signature | HTTP/1.1 401 Unauthorized | error  | INVALID_SIGNATURE | 401  | JWT Signature is invalid. |
      | incorrect JWT format  | HTTP/1.1 401 Unauthorized | error  | SEGMENT_FAULT     | 401  | Incorrect JWT Format.     |

  @CreateACategoryError400
  Scenario Outline: A user with proper role should not be able to create a category sending a bad request
    Given I am authorized with a user with "<User Role>" role
    When I make a request to create a category with invalid "<Body Value>"
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following values
      | code   |  message  |
      | <Code> | <Message> |
    Examples:
    | User Role     | Body Value                   | Status Line              | Code                        | Message                    |
    | administrator | invalid JSON format          | HTTP/1.1 400 Bad Request | rest_invalid_json           | Invalid JSON body passed.  |
    | administrator | JSON with missing parameters | HTTP/1.1 400 Bad Request | rest_missing_callback_param | Missing parameter(s): name |
