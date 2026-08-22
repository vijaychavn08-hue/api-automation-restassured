@posts
Feature: Post API Operations
  As an API consumer
  I want to fetch and query post details
  So that I can verify blog post management endpoints

  @smoke @regression
  Scenario: Get all posts successfully
    When I send a GET request to "/posts"
    Then the API response status code should be 200
    And the response body should contain a list with at least 10 items

  @smoke @regression
  Scenario: Get post by ID with JSON schema validation
    When I send a GET request to "/posts/1"
    Then the API response status code should be 200
    And the response should match the JSON schema "schemas/post-schema.json"
    And the response field "id" should be 1
    And the response field "userId" should be 1

  @regression
  Scenario Outline: Filter posts by userId query parameter
    When I send a GET request to "/posts" with query parameter "userId" equal to "<userId>"
    Then the API response status code should be 200
    And all returned posts should belong to userId <userId>

    Examples:
      | userId |
      | 1      |
      | 2      |
      | 3      |
