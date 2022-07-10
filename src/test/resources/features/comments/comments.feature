@Comments
Feature: Comments

  @GetAllComments @Smoke
  Scenario Outline: A user with proper role should be able to retrieve all comments
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve all comments
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And response should have proper amount of comments

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  @CreateAComment @Smoke
  Scenario Outline: A user with proper role should be able to create a comment
    Given I am authorized with a user with "<User Role>" role
    When I make a request to create a comment with the following query params
      | post | author_name           | author_email            | content                |
      | 1    | A WordPress Commenter | wapuu@wordpress.example | TestNG Comment Example |
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And proper postID should be returned
    And proper author_name should be returned
    And proper content should be returned

    Examples:
      | User Role     | Status Line          |
      | administrator | HTTP/1.1 201 Created |

  @RetrieveAComment @Smoke
  Scenario Outline: A user with proper role should be able to retrieve a comment
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a comment
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And proper comment id should be returned
    And proper postID should be returned
    And proper author_name should be returned
    And proper content of a retrieve comment should be returned

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  @UpdateAComment @Smoke
  Scenario Outline: A user with proper role should be able to update a comment
    Given I am authorized with a user with "<User Role>" role
    When I make a request to update a comment with the following query params
      | author_name                 | content                |
      | A TestNG Commenter Updated  | TestNG Comment Updated |
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And proper comment id should be returned
    And proper author_name should be returned
    And proper content should be returned

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  @DeleteAComment @Smoke
  Scenario Outline: A user with proper role should be able to delete a comment
    Given I am authorized with a user with "<User Role>" role
    When I make a request to delete a comment
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And proper comment id should be returned
    And comment should be deleted

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |