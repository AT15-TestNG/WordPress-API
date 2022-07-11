@Users @Negative
Feature: Users Negative Tests

  @GetUserByNonExistentAndInvalidId
  Scenario Outline: A user with proper role should receive "404 Not Found" when trying to get a user by non-existent id
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a user with the Id "<Id>"
    Then response should be "<Status Line>"
      And response should be invalid and have a body with the following keys and values
        | status    | code   |  message  |
        | <Status>  | <Code> | <Message> |

  Examples:
    | User Role     | Id    | Status Line            | Status | Code                 | Message                                                 |
    | administrator | 12345 | HTTP/1.1 404 Not Found | 404    | rest_user_invalid_id | Invalid user ID.                                        |
    | administrator | abc   | HTTP/1.1 404 Not Found | 404    | rest_no_route        | No route was found matching the URL and request method. |

  @GetUsersWithInvalidToken
  Scenario Outline: A user without authorization should not be able to retrieve all users
    Given I am using a token with "<Token Value>"
    When I make a request to retrieve all users
    Then response should be "<Status Line>"
     And response should be invalid and have a body with the following fields
      | status    | error   | code   |  error_description  |
      | <Status>  | <Error> | <Code> | <Error description> |

    Examples:
      | Token Value           | Status Line               | Status | Error             | Code | Error description         |
      | invalid JWT signature | HTTP/1.1 401 Unauthorized | error  | INVALID_SIGNATURE | 401  | JWT Signature is invalid. |
      | incorrect JWT format  | HTTP/1.1 401 Unauthorized | error  | SEGMENT_FAULT     | 401  | Incorrect JWT Format.     |

  @CreateUserByIdAsSubscriber
  Scenario Outline: A subscriber user with authorization should not be able to create a user
    Given I am authorized with a user with "<User Role>" role
    When I make a request to create a user with the following query params
      | username      | email                  | password   | roles         |
      | admin_test    | administrate@email.com | admin_test | administrator |
    Then response should be "<Status Line>"
     And response should be invalid and have a body with the following keys and values
      | status    | code   |  message  |
      | <Status>  | <Code> | <Message> |

    Examples:
      | User Role     | Status Line            | Status | Code                    | Message                                         |
      | subscriber    | HTTP/1.1 403 Forbidden | 403    | rest_cannot_create_user | Sorry, you are not allowed to create new users. |

  @CreateUserWithMissingParameters
  Scenario Outline: A user with proper role should receive "400 Bad Request" when trying to create a user with missing parameters
    Given I am authorized with a user with "<User Role>" role
    When I make a request to create a user with the following query params
      | username      | email                  |
      | admin_test    | administrate@email.com |
    Then response should be "<Status Line>"
      And response should be invalid and have a body with the following keys and values
      | status    | code   |  message  |
      | <Status>  | <Code> | <Message> |

    Examples:
      | User Role     | Status Line              | Status | Code                        | Message                        |
      | administrator | HTTP/1.1 400 Bad Request | 400    | rest_missing_callback_param | Missing parameter(s): password |

  @UpdateUserByIdAsSubscriber
  Scenario Outline: A user without authorization should not be able to update a user by Id
    Given I am authorized with a user with "<User Role>" role
    When I make a request to update a user with the following query params
      | first_name      | last_name                | description        |
      | TESTNG Name     | TestNG Last Name Updated | TestNG description |
    Then response should be "<Status Line>"
      And response should be invalid and have a body with the following keys and values
      | status    | code   |  message  |
      | <Status>  | <Code> | <Message> |

    Examples:
      | User Role  | Status Line            | Status | Code             | Message                                       |
      | subscriber | HTTP/1.1 403 Forbidden | 403    | rest_cannot_edit | Sorry, you are not allowed to edit this user. |

  @DeleteAUserByNonExistentAndInvalidId
  Scenario Outline: A user with proper role should not be able to delete a User by non-existent or invalid Id
    Given I am authorized with a user with "<User Role>" role
    When I make a request to delete a user with the Id "<Id>"
    Then response should be "<Status Line>"
      And response should be invalid and have a body with the following keys and values
      | status    | code   |  message  |
      | <Status>  | <Code> | <Message> |

    Examples:
      | User Role     | Id    | Status Line            | Status | Code                 | Message                                                 |
      | administrator | 12345 | HTTP/1.1 404 Not Found | 404    | rest_user_invalid_id | Invalid user ID.                                        |
      | administrator | abc   | HTTP/1.1 404 Not Found | 404    | rest_no_route        | No route was found matching the URL and request method. |

  @DeleteAUserByIdWithMissingParameters
  Scenario Outline: A user with proper role should not be able to delete a User by Id with missing parameters
    Given I am authorized with a user with "<User Role>" role
    When I make a request to delete a user by Id with missing parameters
    Then response should be "<Status Line>"
      And response should be invalid and have a body with the following keys and values
      | status    | code   |  message  |
      | <Status>  | <Code> | <Message> |

    Examples:
      | User Role     | Status Line                  | Status | Code                     | Message                                                    |
      | administrator | HTTP/1.1 501 Not Implemented | 501    | rest_trash_not_supported | Users do not support trashing. Set 'force=true' to delete. |
