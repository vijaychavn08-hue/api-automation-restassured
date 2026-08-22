@users
Feature: User API Operations
  As an API consumer
  I want to retrieve and validate user details
  So that I can ensure user services are functional and reliable

  @smoke @regression
  Scenario: Get all users successfully
    When I send a GET request to "/users"
    Then the API response status code should be 200
    And the response body should contain a non-empty list of users
    And the response header "Content-Type" should contain "application/json"

  @smoke @regression
  Scenario: Get user by valid ID with JSON schema validation
    When I send a GET request to "/users/1"
    Then the API response status code should be 200
    And the response should match the JSON schema "schemas/user-schema.json"
    And the response field "id" should be 1
    And the response field "name" should be "Leanne Graham"
    And the response field "email" should be "Sincere@april.biz"

  @regression
  Scenario Outline: Retrieve user details by various valid IDs
    When I send a GET request to "/users/<id>"
    Then the API response status code should be 200
    And the response field "id" should be <id>
    And the response field "username" should be "<username>"

    Examples:
      | id | username   |
      | 1  | Bret       |
      | 2  | Antonette  |
      | 3  | Samantha   |
