@Tags @Negative
Feature: Tags Negative tests

  @DeleteTagError404 @Regression @Bug
  Scenario Outline: A user with proper role should not be able to delete a non-existing tag in WordPress
    Given I am authorized with a user with "<User Role>" role
    When I make a request to delete a non-existing tag
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following values
      | code   |  message  |
      | <Code> | <Message> |
    Examples:
      | User Role     | Status Line            | Code              | Message              |
      | administrator | HTTP/1.1 404 Not Found | rest_tag_invalid | Tag does not exist. |

  @GetATagError404 @Regression
  Scenario Outline: A user with proper role should not be able to get a tag with incorrect id
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a tag using an invalid id "<id>"
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following values
      | code   |  message  |
      | <Code> | <Message> |
    Examples:
      | User Role     | Status Line            | Code          | Message                                                 | Id                               |
      | administrator | HTTP/1.1 404 Not Found | rest_no_route | No route was found matching the URL and request method. | /wp/v2/categories/non-existingId |

  @DeleteATagError501 @Regression
  Scenario Outline: A user with proper role should not be able to delete a tag with non params
    Given I am authorized with a user with "<User Role>" role
    When I make a request to delete a tag without using force=true
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following values
      | code   |  message  |
      | <Code> | <Message> |
    Examples:
      | User Role     | Status Line                  | Code                     | Message                                                    |
      | administrator | HTTP/1.1 501 Not Implemented | rest_trash_not_supported | Terms do not support trashing. Set 'force=true' to delete. |