package com.vijaychavan.tests;

import com.vijaychavan.client.ApiClient;
import com.vijaychavan.models.Address;
import com.vijaychavan.models.Company;
import com.vijaychavan.models.Geo;
import com.vijaychavan.models.User;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Epic("User Management API")
@Feature("User Endpoints")
public class UserApiTest {
    private final ApiClient api = new ApiClient();

    @Test(groups = {"smoke", "regression"})
    @Story("Retrieve all users")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that GET /users returns 200 OK and a non-empty list of users")
    public void getUsersReturnsSuccess() {
        Response response = api.get("/users");
        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 for GET /users");

        List<?> users = response.jsonPath().getList("$");
        Assert.assertNotNull(users, "User list should not be null");
        Assert.assertFalse(users.isEmpty(), "User list should not be empty");
    }

    @Test(groups = {"smoke", "regression"})
    @Story("Retrieve user by ID")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that GET /users/1 returns 200 OK with correct user data")
    public void getUserByIdReturnsExpectedUser() {
        Response response = api.get("/users/1");
        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 for GET /users/1");

        User user = response.as(User.class);
        Assert.assertEquals(user.getId(), Integer.valueOf(1), "Expected user ID to be 1");
        Assert.assertEquals(user.getName(), "Leanne Graham", "Expected name to be Leanne Graham");
        Assert.assertEquals(user.getUsername(), "Bret", "Expected username to be Bret");
        Assert.assertNotNull(user.getEmail(), "Expected email not to be null");
    }

    @Test(groups = {"regression"})
    @Story("Validate User JSON Schema")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validate that GET /users/1 strictly conforms to user-schema.json")
    public void getUserByIdValidatesJsonSchema() {
        Response response = api.get("/users/1");
        Assert.assertEquals(response.statusCode(), 200);
        response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));
    }

    @Test(groups = {"smoke", "regression"})
    @Story("Create new user")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that POST /users with User POJO creates user and returns 201 Created")
    public void postUserReturnsCreatedResponse() {
        Geo geo = new Geo("-37.3159", "81.1496");
        Address address = new Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874", geo);
        Company company = new Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets");
        User newUser = new User(null, "Vijay Chavan", "vijaychavan", "vijay@example.com", address, "1-770-736-8031 x56442", "vijaychavan.dev", company);

        Response response = api.post("/users", newUser);
        Assert.assertEquals(response.statusCode(), 201, "Expected HTTP 201 for POST /users");

        User createdUser = response.as(User.class);
        Assert.assertEquals(createdUser.getName(), "Vijay Chavan");
        Assert.assertEquals(createdUser.getUsername(), "vijaychavan");
        Assert.assertEquals(createdUser.getEmail(), "vijay@example.com");
        Assert.assertNotNull(createdUser.getId(), "Generated user ID should not be null");
    }

    @Test(groups = {"regression"})
    @Story("Update user via PUT")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that PUT /users/1 updates user details and returns 200 OK")
    public void putUserReturnsSuccess() {
        User updatedUser = new User("Vijay Updated", "vijayupdated", "updated@example.com");
        Response response = api.put("/users/1", updatedUser);
        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 for PUT /users/1");

        User responseUser = response.as(User.class);
        Assert.assertEquals(responseUser.getName(), "Vijay Updated");
        Assert.assertEquals(responseUser.getUsername(), "vijayupdated");
        Assert.assertEquals(responseUser.getEmail(), "updated@example.com");
    }

    @Test(groups = {"regression"})
    @Story("Partially update user via PATCH")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that PATCH /users/1 modifies targeted field and returns 200 OK")
    public void patchUserReturnsSuccess() {
        Map<String, Object> patchData = new HashMap<>();
        patchData.put("name", "Patched Name");
        patchData.put("email", "patched@example.com");

        Response response = api.patch("/users/1", patchData);
        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 for PATCH /users/1");
        Assert.assertEquals(response.jsonPath().getString("name"), "Patched Name");
        Assert.assertEquals(response.jsonPath().getString("email"), "patched@example.com");
    }

    @Test(groups = {"regression"})
    @Story("Delete user")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that DELETE /users/1 returns 200 OK")
    public void deleteUserReturnsSuccess() {
        Response response = api.delete("/users/1");
        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 for DELETE /users/1");
    }

    @Test(groups = {"regression"})
    @Story("Validate User Response Headers and Latency")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify content-type header and response latency threshold for GET /users")
    public void getUserResponseHeadersAndLatency() {
        Response response = api.get("/users");
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertTrue(response.getContentType().contains("application/json"), "Expected JSON content type");
        Assert.assertTrue(response.getTime() < 10000L, "Response time took longer than 10 seconds: " + response.getTime() + "ms");
    }
}
