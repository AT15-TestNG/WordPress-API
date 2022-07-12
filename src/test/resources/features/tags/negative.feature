@Tags @Negative @Regression
Feature: Tags Negative tests

  @GetATagError404
  Scenario Outline: A user with proper role should not be able to get a tag with incorrect id
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a tag using an invalid id "<id>"
    Then response should be "<Status Line>"
    And response should be invalid and have a body similar to the following values
      | code   |  message  |
      | <Code> | <Message> |
    Examples:
      | User Role     | Status Line            | Code          | Message                                                 | Id                               |
      | administrator | HTTP/1.1 404 Not Found | rest_no_route | No route was found matching the URL and request method. | /wp/v2/categories/non-existingId |

  @DeleteATagError501
  Scenario Outline: A user with proper role should not be able to delete a tag with non params
    Given I am authorized with a user with "<User Role>" role
    When I make a request to delete a tag without using force=true
    Then response should be "<Status Line>"
    And response should be invalid and have a body similar to the following values
      | code   |  message  |
      | <Code> | <Message> |
    Examples:
      | User Role     | Status Line                  | Code                     | Message                                                    |
      | administrator | HTTP/1.1 501 Not Implemented | rest_trash_not_supported | Terms do not support trashing. Set 'force=true' to delete. |

  @DeleteTagError404 @Bug
  Scenario Outline: A user with proper role should not be able to delete a non-existing tag in WordPress
    Given I am authorized with a user with "<User Role>" role
    When I make a request to delete a non-existing tag
    Then response should be "<Status Line>"
    And response should be invalid and have a body similar to the following values
      | code   |  message  |
      | <Code> | <Message> |
    Examples:
      | User Role     | Status Line            | Code              | Message            |
      | administrator | HTTP/1.1 404 Not Found | rest_tag_invalid | Tag does not exist. |

  @CreateATagAsSubscriber @Subscriber @Bug
  Scenario Outline: A user with proper role should be able to create a tag
    Given I am authorized with a user with "<User Role>" role
    When I make a request to create a tag with the following query params
      | name                               |
      | TestNG Tag Example Subscriber |
    Then response should be "<Status Line>"
    And response should be invalid and have a body similar to the following values
      | code   |  message  |
      | <Code> | <Message> |

    Examples:
      | User Role  | Status Line            | Code               | Message                                                      |
      | subscriber | HTTP/1.1 403 Forbidden | rest_cannot_create | Sorry, you are not allowed to create tags in this taxonomy. |

  @UpdateATagAsSubscriber @Subscriber @Bug
  Scenario Outline: A user with proper role should be able to update a tag
    Given I am authorized with a user with "<User Role>" role
    When I make a request to update a tag with the following query params
      | name                               | description                            |
      | TestNG Tag Subscriber Updated | TestNG Tag Subscriber Description |
    Then response should be "<Status Line>"
    And response should be invalid and have a body similar to the following values
      | code   |  message  |
      | <Code> | <Message> |

    Examples:
      | User Role  | Status Line            | Code               | Message                                       |
      | subscriber | HTTP/1.1 403 Forbidden | rest_cannot_update | Sorry, you are not allowed to edit this tag. |

  @DeleteATagAsSubscriber @Subscriber @Bug
  Scenario Outline: A user with proper role should be able to delete a tag
    Given I am authorized with a user with "<User Role>" role
    When I make a request to delete a tag
    Then response should be "<Status Line>"
    And response should be invalid and have a body similar to the following values
      | code   |  message  |
      | <Code> | <Message> |

    Examples:
      | User Role  | Status Line            | Code               | Message                                         |
      | subscriber | HTTP/1.1 403 Forbidden | rest_cannot_delete | Sorry, you are not allowed to delete this tag. |