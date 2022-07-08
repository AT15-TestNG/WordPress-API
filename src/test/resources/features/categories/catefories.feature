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
    And name should be correct

    Examples:
      | User Role     | Status Line          |
      | administrator | HTTP/1.1 201 Created |

  @RetrieveACategory @Smoke @Test
  Scenario Outline: A user with proper role should be able to retrieve a post
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a category
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And proper category id should be returned
    And name should be correct

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
