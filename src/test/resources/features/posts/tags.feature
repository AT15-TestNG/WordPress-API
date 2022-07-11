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
      | name                    |
      | TestNG Tag Example |
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And name should be correct

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
    And name should be correct

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
    And name should be correct
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