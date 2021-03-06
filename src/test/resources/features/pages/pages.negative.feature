@Pages @Negative @Regression
Feature: Pages Negative Tests

  @RetrieveAPageWithInvalidId
  Scenario Outline: A user with proper role should receive "404 Not Found" when trying to get a page by non-existent id
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a pages with the ID "<Id>"
    Then response should be "<Status Line>"
      And response should be valid and have a body with the following fields
        | code   | message   |
        | <code> | <message> |

    Examples:
      | User Role     | Id       | Status Line            | code                 | message                                                 |
      | administrator | 12381274 | HTTP/1.1 404 Not Found | rest_post_invalid_id | Invalid post ID.                                        |
      | administrator | aHcDFa   | HTTP/1.1 404 Not Found | rest_no_route        | No route was found matching the URL and request method. |

  @DeleteAPageWithInvalidId
  Scenario Outline: A user with proper role should receive "404 Not Found" when trying to get a page by non-existent id
    Given I am authorized with a user with "<User Role>" role
    When I make a request to delete a page with the ID "<Id>"
    Then response should be "<Status Line>"
      And response should be valid and have a body with the following fields
        | code   | message   |
        | <code> | <message> |

    Examples:
      | User Role     | Id       | Status Line            | code                 | message                                                 |
      | administrator | 12381274 | HTTP/1.1 404 Not Found | rest_post_invalid_id | Invalid post ID.                                        |
      | administrator | aHcDFa   | HTTP/1.1 404 Not Found | rest_no_route        | No route was found matching the URL and request method. |

  @UpdateAPageWithInvalidId
  Scenario Outline: When a user with proper role tries to get a page by non-existent id, it should receive "404 Not Found"
    Given I am authorized with a user with "<User Role>" role
    When I make a request to update a page with the ID "<Id>" and the following query parameters
      | content                          | title                          | excerpt                          |
      | TestNG WordPress Content Updated | TestNG WordPress Title Updated | TestNG WordPress Excerpt Updated |
    Then response should be "<Status Line>"
    And response should be valid and have a body with the following fields
      | code   | message   |
      | <code> | <message> |

    Examples:
      | User Role     | Id       | Status Line            | code                 | message                                                 |
      | administrator | 12381274 | HTTP/1.1 404 Not Found | rest_post_invalid_id | Invalid post ID.                                        |