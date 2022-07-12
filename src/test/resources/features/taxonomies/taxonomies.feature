@Taxonomies
Feature: Taxonomies

  @GetAllTaxonomies @Smoke
  Scenario Outline: A user with proper role should be able to retrieve all taxonomies
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve all taxonomies
    Then response should be "<Status Line>"
    And response should be valid and have a body

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  @RetrieveATaxonomy @Smoke
  Scenario Outline: A user with proper role should be able to retrieve a taxonomy
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a taxonomy
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And name should be correct

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  @GetAllTaxonomiesAsSubscriber @Subscriber @Smoke
  Scenario Outline: A user with proper role should be able to retrieve all taxonomies
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve all taxonomies
    Then response should be "<Status Line>"
    And response should be valid and have a body
    Examples:
      | User Role  | Status Line     |
      | subscriber | HTTP/1.1 200 OK |

  @RetrieveATaxonomyAsSubscriber @Subscriber @Smoke
  Scenario Outline: A user with proper role should be able to retrieve a taxonomy
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a taxonomy
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And name should be correct

    Examples:
      | User Role  | Status Line     |
      | subscriber | HTTP/1.1 200 OK |
