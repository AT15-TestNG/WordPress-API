@Users @Negative
Feature: Users Negative Tests

  @GetUserByNonExistentAndInvalidId
  Scenario Outline: A user with proper role should receive "404 Not Found" when trying to get a user by non-existent id
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a user with the Id "<Id>"
    Then response should be "<Status Line>"
      And response should be valid and have a body with the following fields
        | code   | message   |
        | <code> | <message> |

  Examples:
    | User Role     | Id    | Status Line            | code                 | message                                                 |
    | administrator | 12345 | HTTP/1.1 404 Not Found | rest_user_invalid_id | Invalid user ID.                                        |
    | administrator | abc   | HTTP/1.1 404 Not Found | rest_no_route        | No route was found matching the URL and request method. |

  @GetUsersWithInvalidToken @Test
  Scenario Outline: A user without authorization should not be able to retrieve all users
    Given I am using a token with "<Token Value>"
    When I make a request to retrieve all users
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following keys and values
      | status    | error   | code   |  error_description  |
      | <Status>  | <Error> | <Code> | <Error description> |

    Examples:
      | Token Value           | Status Line               | Status | Error             | Code | Error description         |
      | invalid JWT signature | HTTP/1.1 401 Unauthorized | error  | INVALID_SIGNATURE | 401  | JWT Signature is invalid. |
      | incorrect JWT format  | HTTP/1.1 401 Unauthorized | error  | SEGMENT_FAULT     | 401  | Incorrect JWT Format.     |
