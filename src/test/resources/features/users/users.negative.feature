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


