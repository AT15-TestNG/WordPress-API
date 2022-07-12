@Types @Negative @Regression
Feature: Types Negative Tests

  @GetTypeByNonExistingName
  Scenario Outline: A user with proper role should receive "404 Not Found" when trying to get a type by non-existent name
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a type by name "<Type Name>"
    Then response should be "<Status Line>"
      And response should be valid and have a body with the following fields
      | code   |  message  |
      | <Code> | <Message> |

    Examples:
      | User Role     | Type Name | Status Line            | Code              | Message            |
      | administrator | type      | HTTP/1.1 404 Not Found | rest_type_invalid | Invalid post type. |
      | subscriber    | type      | HTTP/1.1 404 Not Found | rest_type_invalid | Invalid post type. |