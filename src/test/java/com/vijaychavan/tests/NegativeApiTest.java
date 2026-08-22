package com.vijaychavan.tests;

import com.vijaychavan.client.ApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Negative API Testing")
@Feature("Error Handling and Validation")
public class NegativeApiTest {
    private final ApiClient api = new ApiClient();

    @Test(groups = {"smoke", "regression"})
    @Story("Retrieve non-existent user")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that GET /users/999999 returns HTTP 404 Not Found")
    public void getNonExistentUserReturns404() {
        Response response = api.get("/users/999999");
        Assert.assertEquals(response.statusCode(), 404, "Expected HTTP 404 for non-existent user");
    }

    @Test(groups = {"regression"})
    @Story("Retrieve non-existent post")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that GET /posts/999999 returns HTTP 404 Not Found")
    public void getNonExistentPostReturns404() {
        Response response = api.get("/posts/999999");
        Assert.assertEquals(response.statusCode(), 404, "Expected HTTP 404 for non-existent post");
    }

    @Test(groups = {"regression"})
    @Story("Request invalid resource endpoint")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that GET /invalid-endpoint-xyz returns HTTP 404 Not Found")
    public void getInvalidEndpointReturns404() {
        Response response = api.get("/invalid-endpoint-xyz");
        Assert.assertEquals(response.statusCode(), 404, "Expected HTTP 404 for non-existent endpoint");
    }

    @Test(groups = {"regression"})
    @Story("Retrieve non-existent comment")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that GET /comments/999999 returns HTTP 404 Not Found")
    public void getNonExistentCommentReturns404() {
        Response response = api.get("/comments/999999");
        Assert.assertEquals(response.statusCode(), 404, "Expected HTTP 404 for non-existent comment");
    }
}
