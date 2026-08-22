@negative @regression
Feature: Negative API Scenarios
  As an API consumer
  I want invalid API requests to be handled gracefully with appropriate error status codes
  So that error states are predictable and conform to REST standards

  @smoke @regression
  Scenario: Request non-existent user by ID returns 404
    When I send a GET request to "/users/999999"
    Then the API response status code should be 404

  @regression
  Scenario: Request non-existent post by ID returns 404
    When I send a GET request to "/posts/999999"
    Then the API response status code should be 404

  @regression
  Scenario: Request invalid non-existent endpoint returns 404
    When I send a GET request to "/invalid-resource-endpoint-xyz"
    Then the API response status code should be 404

  @regression
  Scenario Outline: Request non-existent IDs across endpoints returns 404
    When I send a GET request to "<endpoint>/<id>"
    Then the API response status code should be 404

    Examples:
      | endpoint  | id     |
      | /users    | 99999  |
      | /posts    | 88888  |
      | /comments | 77777  |
