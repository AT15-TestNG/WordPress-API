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
      | subscriber    | HTTP/1.1 200 OK |

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

  @UpdateAPage @Smoke
  Scenario Outline: A user with proper role should be able to update a page
    Given I am authorized with a user with "<User Role>" role
    When I make a request to update a page with the following query params
      | content                          | title                          | excerpt                          |
      | TestNG WordPress Content Updated | TestNG WordPress Title Updated | TestNG WordPress Excerpt Updated |
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And proper page id should be returned
    And page content should be correct
    And page title should be correct
    And page excerpt should be correct

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  @DeleteAPage @Smoke
  Scenario Outline: A user with proper role should be able to delete a page
    Given I am authorized with a user with "<User Role>" role
    When I make a request to delete a page
    Then response should be "<Status Line>"
    And response should be valid and have a body
    And page should be deleted
    And proper page id should be returned

    Examples:
      | User Role     | Status Line     |
      | administrator | HTTP/1.1 200 OK |

  @CreateAPageAsSub
  Scenario Outline: A user with proper role should be able to create a page
    Given I am authorized with a user with "<User Role>" role
    When I make a request to create a page with the following query params
      | content                       | title                  | excerpt                  |
      | TestNG WordPress Page Content | TestNG WordPress Title | TestNG WordPress Excerpt |
    Then response should be "<Status Line>"

    Examples:
      | User Role     | Status Line            |
      | subscriber    | HTTP/1.1 403 Forbidden |

  @RetrieveAPageAsSub
  Scenario Outline: A user with proper role should be able to retrieve a page
    Given I am authorized with a user with "<User Role>" role
    When I make a request to retrieve a page
    Then response should be "<Status Line>"

    Examples:
      | User Role     | Status Line            |
      | subscriber    | HTTP/1.1 403 Forbidden |

  @UpdateAPageAsSub
  Scenario Outline: A user with proper role should be able to update a page
    Given I am authorized with a user with "<User Role>" role
    When I make a request to update a page with the following query params
      | content                          | title                          | excerpt                          |
      | TestNG WordPress Content Updated | TestNG WordPress Title Updated | TestNG WordPress Excerpt Updated |
    Then response should be "<Status Line>"

    Examples:
      | User Role     | Status Line            |
      | subscriber    | HTTP/1.1 403 Forbidden |

  @DeleteAPageAsSub
  Scenario Outline: A user with proper role should be able to delete a page
    Given I am authorized with a user with "<User Role>" role
    When I make a request to delete a page
    Then response should be "<Status Line>"

    Examples:
      | User Role     | Status Line            |
      | subscriber    | HTTP/1.1 403 Forbidden |