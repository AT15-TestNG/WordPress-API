@Users @Negative
Feature: Users Negative Tests

  @GetUserByNonExistentId
  Scenario Outline: A user with proper role should receive "404 Not Found" when trying to get a user by non-existent id
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a user by non-existent Id
    Then response should be "<Status Line>"
      And response should be valid and have a body with the following fields
        | code   | message   |
        | <code> | <message> |

  Examples:
    | User Role     | Status Line            | code                 | message          |
    | administrator | HTTP/1.1 404 Not Found | rest_user_invalid_id | Invalid user ID. |


