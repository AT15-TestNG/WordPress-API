@Tags
Feature: Tags

  @GetAllTags @Smoke
  Scenario Outline: A user with proper role should be able to retrieve all tags
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve all tags
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And response should have proper amount of tags

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  @CreateATag @Smoke
  Scenario Outline: A user with proper role should be able to create a tag
    Given I am authorized with a user with "<User Role>" role
    When I make a request to create a tag with the following query params
      | name               |
      | TestNG Tag Example |
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And proper tag name should be correct

    Examples:
      | User Role     | Status Line          |
      | administrator | HTTP/1.1 201 Created |


  @RetrieveATag @Smoke
  Scenario Outline: A user with proper role should be able to retrieve a tag
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a tag
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And proper tag id should be returned
    And proper tag name should be correct

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  @UpdateATag @Smoke
  Scenario Outline: A user with proper role should be able to update a tag
    Given I am authorized with a user with "<User Role>" role
    When I make a request to update a tag with the following query params
      | name               | description            |
      | TestNG Tag Updated | TestNG Tag Description |
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And proper tag id should be returned
    And proper tag name should be correct
    And proper description should be returned

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  @DeleteATag @Smoke
  Scenario Outline: A user with proper role should be able to delete a tag
    Given I am authorized with a user with "<User Role>" role
    When I make a request to delete a tag
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And the tag should be deleted

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  @GetAllTagsAsSubscriber @Subscriber
  Scenario Outline: A user with proper role should be able to retrieve all tags
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve all tags
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And response should have proper amount of tags

    Examples:
      | User Role  | Status Line     |
      | subscriber | HTTP/1.1 200 OK |

  @RetrieveATagAsSubscriber @Subscriber
  Scenario Outline: A user with proper role should be able to retrieve a tag
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a tag
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And proper tag id should be returned
    And proper tag name should be correct

    Examples:
      | User Role  | Status Line     |
      | subscriber | HTTP/1.1 200 OK |

  @UpdateATagAsSubscriber @Subscriber
  Scenario Outline: A user with proper role should be able to update a tag
    Given I am authorized with a user with "<User Role>" role
    When I make a request to update a tag with the following query params
      | name                               | description                            |
      | TestNG Tag Subscriber Updated | TestNG Tag Subscriber Description |
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following values
      | code   |  message  |
      | <Code> | <Message> |

    Examples:
      | User Role  | Status Line            | Code               | Message                                       |
      | subscriber | HTTP/1.1 403 Forbidden | rest_cannot_update | Sorry, you are not allowed to edit this term. |

  @DeleteATagAsSubscriber @Subscriber
  Scenario Outline: A user with proper role should be able to delete a tag
    Given I am authorized with a user with "<User Role>" role
    When I make a request to delete a tag
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following values
      | code   |  message  |
      | <Code> | <Message> |

    Examples:
      | User Role  | Status Line            | Code               | Message                                         |
      | subscriber | HTTP/1.1 403 Forbidden | rest_cannot_delete | Sorry, you are not allowed to delete this term. |