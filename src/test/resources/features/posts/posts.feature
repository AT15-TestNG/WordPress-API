@Posts
Feature: Posts

  @GetAllPosts @Smoke
  Scenario Outline: A user with proper role should be able to retrieve all posts
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve all posts
    Then response should be "<Status Line>"
      And response should be valid and have a body
      And response should have proper amount of posts

  Examples:
    | User Role     | Status Line     |
    | administrator | HTTP/1.1 200 OK |

  @CreateAPost @Smoke
  Scenario Outline: A user with proper role should be able to create a post
    Given I am authorized with a user with "<User Role>" role
    When I make a request to create a post with the following query params
      | content                       | title                  | excerpt                  |
      | TestNG WordPress Post Content | TestNG WordPress Title | TestNG WordPress Excerpt |
    Then response should be "<Status Line>"
      And response should be valid and have a body
      And content should be correct
      And title should be correct
      And excerpt should be correct

  Examples:
    | User Role     | Status Line          |
    | administrator | HTTP/1.1 201 Created |

  @RetrieveAPost @Smoke
  Scenario Outline: A user with proper role should be able to retrieve a post
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a post
    Then response should be "<Status Line>"
      And response should be valid and have a body
      And proper post id should be returned
      And content should be correct
      And title should be correct
      And excerpt should be correct

  Examples:
    | User Role     | Status Line     |
    | administrator | HTTP/1.1 200 OK |

  @UpdateAPost @Smoke
  Scenario Outline: A user with proper role should be able to update a post
    Given I am authorized with a user with "<User Role>" role
    When I make a request to update a post with the following query params
      | content                          | title                          | excerpt                          |
      | TestNG WordPress Content Updated | TestNG WordPress Title Updated | TestNG WordPress Excerpt Updated |
    Then response should be "<Status Line>"
      And response should be valid and have a body
      And proper post id should be returned
      And content should be correct
      And title should be correct
      And excerpt should be correct

  Examples:
    | User Role     | Status Line     |
    | administrator | HTTP/1.1 200 OK |

  @DeleteAPost @Smoke
  Scenario Outline: A user with proper role should be able to delete a post
    Given I am authorized with a user with "<User Role>" role
    When I make a request to delete a post
    Then response should be "<Status Line>"
      And response should be valid and have a body
      And post should be deleted
      And proper post id should be returned

  Examples:
    | User Role     | Status Line     |
    | administrator | HTTP/1.1 200 OK |