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
    And proper author_email should be returned
    And proper content should be returned

    Examples:
      | User Role     | Status Line          |
      | administrator | HTTP/1.1 201 Created |