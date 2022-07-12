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

  @GetATaxonomyError404 @Regression
  Scenario Outline: A user with proper role should not be able to get a taxonomy with non-existing identifier
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a taxonomy using an invalid identifier "<Non-existing-taxonomy>"
    Then response should be "<Status Line>"
    And response should be invalid and have a body with the following values
      | code   |  message  |
      | <Code> | <Message> |
    Examples:
      | User Role     | Status Line            | Code                  | Message           | Non-existing-taxonomy                    |
      | administrator | HTTP/1.1 404 Not Found | rest_taxonomy_invalid | Invalid taxonomy. | /wp/v2/taxonomies/non-existing-taxonomy |