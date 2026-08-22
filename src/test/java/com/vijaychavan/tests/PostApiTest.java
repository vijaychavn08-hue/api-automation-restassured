package com.vijaychavan.tests;

import com.vijaychavan.client.ApiClient;
import com.vijaychavan.models.Post;
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

@Epic("Post Management API")
@Feature("Post Endpoints")
public class PostApiTest {
    private final ApiClient api = new ApiClient();

    @Test(groups = {"smoke", "regression"})
    @Story("Retrieve all posts")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that GET /posts returns 200 OK and a non-empty list of posts")
    public void getPostsReturnsSuccess() {
        Response response = api.get("/posts");
        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 for GET /posts");

        List<?> posts = response.jsonPath().getList("$");
        Assert.assertNotNull(posts, "Posts list should not be null");
        Assert.assertTrue(posts.size() >= 10, "Expected at least 10 posts, found " + posts.size());
    }

    @Test(groups = {"smoke", "regression"})
    @Story("Retrieve post by ID")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that GET /posts/1 returns 200 OK with matching Post POJO")
    public void getPostByIdReturnsExpectedPost() {
        Response response = api.get("/posts/1");
        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 for GET /posts/1");

        Post post = response.as(Post.class);
        Assert.assertEquals(post.getId(), Integer.valueOf(1), "Post ID should be 1");
        Assert.assertEquals(post.getUserId(), Integer.valueOf(1), "User ID should be 1");
        Assert.assertNotNull(post.getTitle(), "Title should not be null");
        Assert.assertNotNull(post.getBody(), "Body should not be null");
    }

    @Test(groups = {"regression"})
    @Story("Validate Post JSON Schema")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validate that GET /posts/1 conforms strictly to post-schema.json")
    public void getPostByIdValidatesJsonSchema() {
        Response response = api.get("/posts/1");
        Assert.assertEquals(response.statusCode(), 200);
        response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/post-schema.json"));
    }

    @Test(groups = {"regression"})
    @Story("Query posts by userId")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify query parameter filtering on GET /posts?userId=1")
    public void filterPostsByUserIdReturnsFilteredList() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("userId", 1);

        Response response = api.get("/posts", queryParams);
        Assert.assertEquals(response.statusCode(), 200);

        List<Integer> userIds = response.jsonPath().getList("userId", Integer.class);
        Assert.assertNotNull(userIds);
        Assert.assertFalse(userIds.isEmpty(), "Expected posts for userId 1");
        for (Integer uid : userIds) {
            Assert.assertEquals(uid.intValue(), 1, "All returned posts should belong to userId 1");
        }
    }

    @Test(groups = {"smoke", "regression"})
    @Story("Create new post")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify creating a new post with Post POJO returns 201 Created")
    public void createPostUsingPojoReturnsCreated() {
        Post newPost = new Post(1, "Automated Test Title", "Automated test body content for REST Assured framework.");
        Response response = api.post("/posts", newPost);
        Assert.assertEquals(response.statusCode(), 201, "Expected HTTP 201 for POST /posts");

        Post created = response.as(Post.class);
        Assert.assertEquals(created.getUserId(), Integer.valueOf(1));
        Assert.assertEquals(created.getTitle(), "Automated Test Title");
        Assert.assertEquals(created.getBody(), "Automated test body content for REST Assured framework.");
        Assert.assertNotNull(created.getId(), "Created post ID should be generated");
    }

    @Test(groups = {"regression"})
    @Story("Delete post")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify DELETE /posts/1 returns 200 OK")
    public void deletePostReturnsSuccess() {
        Response response = api.delete("/posts/1");
        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 for DELETE /posts/1");
    }
}
