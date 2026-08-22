package com.vijaychavan.tests;

import com.vijaychavan.client.ApiClient;
import com.vijaychavan.models.Comment;
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

@Epic("Comment Management API")
@Feature("Comment Endpoints")
public class CommentApiTest {
    private final ApiClient api = new ApiClient();

    @Test(groups = {"regression"})
    @Story("Retrieve comments filtered by postId")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that GET /comments?postId=1 returns only comments related to postId 1")
    public void getCommentsByPostIdReturnsFilteredComments() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("postId", 1);

        Response response = api.get("/comments", queryParams);
        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 for GET /comments?postId=1");

        List<Integer> postIds = response.jsonPath().getList("postId", Integer.class);
        Assert.assertNotNull(postIds);
        Assert.assertFalse(postIds.isEmpty(), "Comments should not be empty");
        for (Integer pid : postIds) {
            Assert.assertEquals(pid.intValue(), 1, "Expected each comment to belong to postId 1");
        }
    }

    @Test(groups = {"regression"})
    @Story("Validate Comment JSON Schema")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validate that GET /comments/1 conforms to comment-schema.json")
    public void getCommentByIdValidatesJsonSchema() {
        Response response = api.get("/comments/1");
        Assert.assertEquals(response.statusCode(), 200);
        response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/comment-schema.json"));
    }

    @Test(groups = {"regression"})
    @Story("Create new comment")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify creating a new comment with Comment POJO returns 201 Created")
    public void createCommentUsingPojoReturnsCreated() {
        Comment comment = new Comment(1, "Vijay Chavan", "vijay@example.com", "Great automation framework!");
        Response response = api.post("/comments", comment);
        Assert.assertEquals(response.statusCode(), 201, "Expected HTTP 201 for POST /comments");

        Comment created = response.as(Comment.class);
        Assert.assertEquals(created.getPostId(), Integer.valueOf(1));
        Assert.assertEquals(created.getName(), "Vijay Chavan");
        Assert.assertEquals(created.getEmail(), "vijay@example.com");
        Assert.assertNotNull(created.getId(), "Comment ID should not be null");
    }
}
