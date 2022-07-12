@Categories @Negative @Regression
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

  @DeleteCategoryError403
  Scenario Outline: A user with proper role should not be able to delete the default category in WordPress
    Given I am authorized with a user with "<User Role>" role
    When I make a request to delete the default category
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following values
      | code   |  message  |
      | <Code> | <Message> |
    Examples:
      | User Role     | Status Line            | Code               | Message                                         |
      | administrator | HTTP/1.1 403 Forbidden | rest_cannot_delete | Sorry, you are not allowed to delete this term. |

  @GetACategoryError404
  Scenario Outline: A user with proper role should not be able to get a category with incorrect endpoint
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a category using an invalid endpoint "<Endpoint>"
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following values
      | code   |  message  |
      | <Code> | <Message> |
    Examples:
      | User Role     | Status Line            | Code          | Message                                                 | Endpoint                               |
      | administrator | HTTP/1.1 404 Not Found | rest_no_route | No route was found matching the URL and request method. | /wp/v2/categories/non-existingEndpoint |

  @DeleteACategoryError501
  Scenario Outline: A user with proper role should not be able to get a category with incorrect endpoint
    Given I am authorized with a user with "<User Role>" role
    When I make a request to delete a category without using force=true
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following values
      | code   |  message  |
      | <Code> | <Message> |
    Examples:
      | User Role     | Status Line                  | Code                     | Message                                                    |
      | administrator | HTTP/1.1 501 Not Implemented | rest_trash_not_supported | Terms do not support trashing. Set 'force=true' to delete. |

  @GetACategoryError404Bug @Bug
  Scenario Outline: A user with proper role should not be able to get a category with incorrect endpoint
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a category using an invalid endpoint "<Endpoint>"
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following values
      | code   |  message  |
      | <Code> | <Message> |
    Examples:
      | User Role     | Status Line            | Code                     | Message              | Endpoint               |
      | administrator | HTTP/1.1 404 Not Found | rest_category_invalid_id | Invalid category ID. | /wp/v2/categories/7777 |