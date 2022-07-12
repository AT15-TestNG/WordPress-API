@Posts @Negative @Regression
Feature: Pages Negative Tests

  @RetrieveAPostWithInvalidId
  Scenario Outline: A user with proper role should receive "404 Not Found" when trying to get a page by non-existent id
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a post with the ID "<Id>"
    Then response should be "<Status Line>"
    And response should be valid and have a body with the following fields
      | code   | message   |
      | <code> | <message> |

    Examples:
      | User Role     | Id       | Status Line            | code                 | message                                                 |
      | administrator | 12381274 | HTTP/1.1 404 Not Found | rest_post_invalid_id | Invalid post ID.                                        |
      | administrator | aHcDFa   | HTTP/1.1 404 Not Found | rest_no_route        | No route was found matching the URL and request method. |

  @DeleteAPostWithInvalidId
  Scenario Outline: A user with proper role should receive "404 Not Found" when trying to delete a page by non-existent id
    Given I am authorized with a user with "<User Role>" role
    When I make a request to delete a post with the ID "<Id>"
    Then response should be "<Status Line>"
    And response should be valid and have a body with the following fields
      | code   | message   |
      | <code> | <message> |

    Examples:
      | User Role     | Id       | Status Line            | code                 | message                                                 |
      | administrator | 12381274 | HTTP/1.1 404 Not Found | rest_post_invalid_id | Invalid post ID.                                        |
      | administrator | aHcDFa   | HTTP/1.1 404 Not Found | rest_no_route        | No route was found matching the URL and request method. |

  @CreateAPostWithoutTitleContentAndExcerpt
  Scenario Outline: A user with proper should receive "400 Bad Request" when trying to create a page with non-existent id
    Given I am authorized with a user with "<User Role>" role
    When I make a request to create a post with the following query params
      | content | title | excerpt |
      |         |       |         |
    Then response should be "<Status Line>"
    And response should be valid and have a body with the following fields
      | code   | message   |
      | <code> | <message> |

    Examples:
      | User Role     | Status Line              | code                 | message                                |
      | administrator | HTTP/1.1 400 Bad Request | empty_content        | Content, title, and excerpt are empty. |

  @UpdateAPostWithInvalidId
  Scenario Outline: A user with proper role should receive "404 Not Found" when trying to update a page with non-existent id
    Given I am authorized with a user with "<User Role>" role
    When I make a request to update a post with the following query params and the ID "<Id>"
      | content                          | title                          | excerpt                          |
      | TestNG WordPress Content Updated | TestNG WordPress Title Updated | TestNG WordPress Excerpt Updated |
    Then response should be "<Status Line>"
    And response should be valid and have a body with the following fields
      | code   | message   |
      | <code> | <message> |

    Examples:
      | User Role     | Id         | Status Line              | code                 | message          |
      | administrator | 1231244    | HTTP/1.1 404 Not Found   | rest_post_invalid_id | Invalid post ID. |
