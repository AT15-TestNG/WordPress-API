@Tags @Bug @Regression
  Feature: Tags Bug
    @DeleteTagError404
    Scenario Outline: A user with proper role should not be able to delete a non-existing tag in WordPress
      Given I am authorized with a user with "<User Role>" role
      When I make a request to delete a non-existing tag
      Then response should be "<Status Line>"
      And response should be invalid and have a body with the following values
        | code   |  message  |
        | <Code> | <Message> |
      Examples:
        | User Role     | Status Line            | Code              | Message              |
        | administrator | HTTP/1.1 404 Not Found | rest_tag_invalid | Tag does not exist. |