@Comments @Negative @Regression
Feature: Comments Negative Tests

  @GetAllCommentsError401
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

  @CreateACommentError400
  Scenario Outline: A user with proper role should not be able to create a comment sending a bad request
    Given I am authorized with a user with "<User Role>" role
    When I make a request to create a comment with invalid "<Body Value>"
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following values
      | code   |  message  |
      | <Code> | <Message> |
    Examples:
      | User Role     | Body Value          | Status Line              | Code              | Message                   |
      | administrator | invalid JSON format | HTTP/1.1 400 Bad Request | rest_invalid_json | Invalid JSON body passed. |

  @CreateACommentWithoutPostError403
  Scenario Outline: A user with proper role should not be able to create a comment without a related post
    Given I am authorized with a user with "<User Role>" role
    When I make a request to create a comment without specifying any post
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following values
      | code   |  message  |
      | <Code> | <Message> |
    Examples:
      | User Role     | Status Line            | Code                         | Message                                                           |
      | administrator | HTTP/1.1 403 Forbidden | rest_comment_invalid_post_id | Sorry, you are not allowed to create this comment without a post. |

  @RetrieveACommentError404
  Scenario Outline: A user with proper role should not be able to get a comment using an incorrect endpoint
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a comment using an invalid endpoint "<Endpoint>"
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following values
      | code   |  message  |
      | <Code> | <Message> |
    Examples:
      | User Role     | Status Line            | Code                    | Message                                                 | Endpoint                             |
      | administrator | HTTP/1.1 404 Not Found | rest_no_route           | No route was found matching the URL and request method. | /wp/v2/comments/non-existingEndpoint |
      | administrator | HTTP/1.1 404 Not Found | rest_comment_invalid_id | Invalid comment ID.                                     | /wp/v2/comments/7777                 |

  @CreateACommentDuplicatedError409
  Scenario Outline: A user with proper role should not be able to create a duplicated comment
    Given I am authorized with a user with "<User Role>" role
    When I make a request to create a duplicated comment
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following values
      | code   |  message  |
      | <Code> | <Message> |
    Examples:
      | User Role     | Status Line           | Code              | Message                     |
      | administrator | HTTP/1.1 409 Conflict | comment_duplicate | Duplicate comment detected; |

  @DeleteATrashedCommentError410
  Scenario Outline: A user with proper role should not be able to delete a trashed comment without using force=true
    Given I am authorized with a user with "<User Role>" role
    When I make a request to delete a trashed comment without using force=true
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following values
      | code   |  message  |
      | <Code> | <Message> |
    Examples:
      | User Role     | Status Line       | Code                 | Message                               |
      | administrator | HTTP/1.1 410 Gone | rest_already_trashed | The comment has already been trashed. |