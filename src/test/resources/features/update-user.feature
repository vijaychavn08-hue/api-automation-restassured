@users @update
Feature: Update and Delete User API
  As an API client
  I want to modify and remove user records
  So that user account data remains up to date

  @regression
  Scenario: Fully update a user with PUT request
    Given I have valid user payload with name "Updated Vijay", username "vijayupdated", email "updated@example.com"
    When I send a PUT request to "/users/1" with the user payload
    Then the API response status code should be 200
    And the response field "name" should be "Updated Vijay"
    And the response field "username" should be "vijayupdated"
    And the response field "email" should be "updated@example.com"

  @regression
  Scenario: Partially update a user with PATCH request
    Given I have a partial user update with field "name" as "Patched Name"
    When I send a PATCH request to "/users/1" with the partial update
    Then the API response status code should be 200
    And the response field "name" should be "Patched Name"

  @regression
  Scenario: Delete a user by ID
    When I send a DELETE request to "/users/1"
    Then the API response status code should be 200
