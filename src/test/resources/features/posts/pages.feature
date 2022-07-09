@Pages @Regression
Feature: Pages

  @GetAllPages @Smoke
  Scenario Outline: A user with proper role should be able to retrieve all pages
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve all pages
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And response should have proper amount of pages

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  @CreateAPage @Smoke
  Scenario Outline: A user with proper role should be able to create a page
    Given I am authorized with a user with "<User Role>" role
    When I make a request to create a page with the following query params
      | content                       | title                  | excerpt                  |
      | TestNG WordPress Page Content | TestNG WordPress Title | TestNG WordPress Excerpt |
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And page content should be correct
    And page title should be correct
    And page excerpt should be correct

    Examples:
      | User Role     | Status Line          |
      | administrator | HTTP/1.1 201 Created |

  @RetrieveAPage @Smoke
  Scenario Outline: A user with proper role should be able to retrieve a page
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a page
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And proper page id should be returned
    And page content should be correct
    And page title should be correct
    And page excerpt should be correct

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |
