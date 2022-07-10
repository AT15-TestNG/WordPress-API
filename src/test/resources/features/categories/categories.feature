@Categories
Feature: Categories

  @GetAllCategories @Smoke
  Scenario Outline: A user with proper role should be able to retrieve all categories
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve all categories
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And response should have proper amount of categories

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |


  @CreateACategory @Smoke
  Scenario Outline: A user with proper role should be able to create a category
    Given I am authorized with a user with "<User Role>" role
    When I make a request to create a category with the following query params
      | name                    |
      | TestNG Category Example |
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And proper name should be returned

    Examples:
      | User Role     | Status Line          |
      | administrator | HTTP/1.1 201 Created |

  @RetrieveACategory @Smoke
  Scenario Outline: A user with proper role should be able to retrieve a category
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a category
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And proper category id should be returned
    And proper name should be returned

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  @UpdateACategory @Smoke
  Scenario Outline: A user with proper role should be able to update a category
    Given I am authorized with a user with "<User Role>" role
    When I make a request to update a category with the following query params
      | name                    | description                 |
      | TestNG Category Updated | TestNG Category Description |
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And proper category id should be returned
    And proper name should be returned
    And proper description should be returned

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  @DeleteACategory @Smoke
  Scenario Outline: A user with proper role should be able to delete a category
    Given I am authorized with a user with "<User Role>" role
    When I make a request to delete a category
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And the category should be deleted

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  @GetAllCategoriesAsSubscriber @Subscriber
  Scenario Outline: A user with proper role should be able to retrieve all categories
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve all categories
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And response should have proper amount of categories

    Examples:
      | User Role  | Status Line     |
      | subscriber | HTTP/1.1 200 OK |

  @CreateACategoryAsSubscriber @Subscriber
  Scenario Outline: A user with proper role should be able to create a category
    Given I am authorized with a user with "<User Role>" role
    When I make a request to create a category with the following query params
      | name                               |
      | TestNG Category Example Subscriber |
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following values
      | code   |  message  |
      | <Code> | <Message> |

    Examples:
      | User Role  | Status Line            | Code               | Message                                                      |
      | subscriber | HTTP/1.1 403 Forbidden | rest_cannot_create | Sorry, you are not allowed to create terms in this taxonomy. |

  @RetrieveACategoryAsSubscriber @Subscriber
  Scenario Outline: A user with proper role should be able to retrieve a category
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a category
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And proper category id should be returned
    And proper name should be returned

    Examples:
      | User Role  | Status Line     |
      | subscriber | HTTP/1.1 200 OK |

  @UpdateACategoryAsSubscriber @Subscriber
  Scenario Outline: A user with proper role should be able to update a category
    Given I am authorized with a user with "<User Role>" role
    When I make a request to update a category with the following query params
      | name                               | description                            |
      | TestNG Category Subscriber Updated | TestNG Category Subscriber Description |
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following values
      | code   |  message  |
      | <Code> | <Message> |

    Examples:
      | User Role  | Status Line            | Code               | Message                                       |
      | subscriber | HTTP/1.1 403 Forbidden | rest_cannot_update | Sorry, you are not allowed to edit this term. |

  @DeleteACategoryAsSubscriber @Subscriber
  Scenario Outline: A user with proper role should be able to delete a category
    Given I am authorized with a user with "<User Role>" role
    When I make a request to delete a category
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following values
      | code   |  message  |
      | <Code> | <Message> |

    Examples:
      | User Role  | Status Line            | Code               | Message                                         |
      | subscriber | HTTP/1.1 403 Forbidden | rest_cannot_delete | Sorry, you are not allowed to delete this term. |
