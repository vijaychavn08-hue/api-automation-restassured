@users @create
Feature: Create User API
  As an API client
  I want to create new users via POST requests
  So that new user accounts are registered in the system

  @smoke @regression
  Scenario: Create a new user with standard details
    Given I have valid user payload with name "Vijay Chavan", username "vijaychavan", email "vijay@example.com"
    When I send a POST request to "/users" with the user payload
    Then the API response status code should be 201
    And the response field "name" should be "Vijay Chavan"
    And the response field "username" should be "vijaychavan"
    And the response field "email" should be "vijay@example.com"
    And the response field "id" should not be null

  @regression
  Scenario Outline: Create users with diverse profile datasets
    Given I have valid user payload with name "<name>", username "<username>", email "<email>"
    When I send a POST request to "/users" with the user payload
    Then the API response status code should be 201
    And the response field "name" should be "<name>"
    And the response field "email" should be "<email>"
    And the response field "id" should not be null

    Examples:
      | name           | username    | email                 |
      | Alice Johnson  | ajohnson    | alice@example.com     |
      | Bob Smith      | bsmith      | bob@example.com       |
      | Charlie Brown  | cbrown      | charlie@example.com   |
