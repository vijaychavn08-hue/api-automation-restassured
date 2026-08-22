package com.vijaychavan.steps;

import com.vijaychavan.client.ApiClient;
import com.vijaychavan.models.User;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;

public class ApiSteps {
    private final ApiClient api = new ApiClient();
    private Response response;
    private User userPayload;
    private Map<String, Object> partialPayload;

    @When("I request users")
    public void requestUsers() {
        response = api.get("/users");
    }

    @Then("the API response status is {int}")
    public void verifyStatusLegacy(int expected) {
        Assert.assertEquals(response.statusCode(), expected);
    }

    @When("I send a GET request to {string}")
    public void sendGetRequest(String endpoint) {
        response = api.get(endpoint);
    }

    @When("I send a GET request to {string} with query parameter {string} equal to {string}")
    public void sendGetRequestWithQueryParam(String endpoint, String paramKey, String paramValue) {
        Map<String, Object> params = new HashMap<>();
        params.put(paramKey, paramValue);
        response = api.get(endpoint, params);
    }

    @Then("the API response status code should be {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        Assert.assertEquals(response.statusCode(), expectedStatusCode, "Unexpected HTTP status code");
    }

    @Then("the response body should contain a non-empty list of users")
    public void verifyNonEmptyUserList() {
        List<?> list = response.jsonPath().getList("$");
        Assert.assertNotNull(list, "Response list is null");
        Assert.assertFalse(list.isEmpty(), "Expected non-empty list of users");
    }

    @Then("the response body should contain a list with at least {int} items")
    public void verifyListMinSize(int minSize) {
        List<?> list = response.jsonPath().getList("$");
        Assert.assertNotNull(list, "Response list is null");
        Assert.assertTrue(list.size() >= minSize, "Expected at least " + minSize + " items, but found " + list.size());
    }

    @Then("the response header {string} should contain {string}")
    public void verifyResponseHeader(String headerName, String expectedValue) {
        String headerVal = response.getHeader(headerName);
        Assert.assertNotNull(headerVal, "Header " + headerName + " not found");
        Assert.assertTrue(headerVal.contains(expectedValue),
                "Header " + headerName + " (" + headerVal + ") did not contain " + expectedValue);
    }

    @Then("the response should match the JSON schema {string}")
    public void verifyJsonSchema(String schemaClasspath) {
        response.then().assertThat().body(matchesJsonSchemaInClasspath(schemaClasspath));
    }

    @Then("the response field {string} should be {int}")
    public void verifyIntegerField(String jsonPath, int expectedValue) {
        int actual = response.jsonPath().getInt(jsonPath);
        Assert.assertEquals(actual, expectedValue, "Field " + jsonPath + " mismatch");
    }

    @Then("the response field {string} should be {string}")
    public void verifyStringField(String jsonPath, String expectedValue) {
        String actual = response.jsonPath().getString(jsonPath);
        Assert.assertEquals(actual, expectedValue, "Field " + jsonPath + " mismatch");
    }

    @Then("the response field {string} should not be null")
    public void verifyFieldNotNull(String jsonPath) {
        Object actual = response.jsonPath().get(jsonPath);
        Assert.assertNotNull(actual, "Field " + jsonPath + " should not be null");
    }

    @Then("all returned posts should belong to userId {int}")
    public void verifyAllPostsBelongToUserId(int expectedUserId) {
        List<Integer> userIds = response.jsonPath().getList("userId", Integer.class);
        Assert.assertNotNull(userIds, "userId list is null");
        Assert.assertFalse(userIds.isEmpty(), "No posts returned for userId " + expectedUserId);
        for (Integer uid : userIds) {
            Assert.assertEquals(uid.intValue(), expectedUserId, "Post found with mismatched userId");
        }
    }

    @Given("I have valid user payload with name {string}, username {string}, email {string}")
    public void prepareUserPayload(String name, String username, String email) {
        userPayload = new User(name, username, email);
    }

    @When("I send a POST request to {string} with the user payload")
    public void sendPostWithUserPayload(String endpoint) {
        response = api.post(endpoint, userPayload);
    }

    @When("I send a PUT request to {string} with the user payload")
    public void sendPutWithUserPayload(String endpoint) {
        response = api.put(endpoint, userPayload);
    }

    @Given("I have a partial user update with field {string} as {string}")
    public void preparePartialUpdate(String field, String value) {
        partialPayload = new HashMap<>();
        partialPayload.put(field, value);
    }

    @When("I send a PATCH request to {string} with the partial update")
    public void sendPatchWithPartialUpdate(String endpoint) {
        response = api.patch(endpoint, partialPayload);
    }

    @When("I send a DELETE request to {string}")
    public void sendDeleteRequest(String endpoint) {
        response = api.delete(endpoint);
    }
}
