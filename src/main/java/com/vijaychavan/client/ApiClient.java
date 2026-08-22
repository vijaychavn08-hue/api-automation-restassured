package com.vijaychavan.client;

import com.vijaychavan.config.ApiConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ApiClient {
    private static final Logger log = LoggerFactory.getLogger(ApiClient.class);

    protected RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ApiConfig.baseUrl())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.URI)
                .log(LogDetail.METHOD)
                .build();
    }

    protected RequestSpecification request() {
        return RestAssured.given().spec(getRequestSpec());
    }

    public Response get(String path) {
        log.info("Sending GET request to {}", path);
        return request().when().get(path);
    }

    public Response get(String path, Map<String, ?> queryParams) {
        log.info("Sending GET request to {} with query params: {}", path, queryParams);
        return request().queryParams(queryParams).when().get(path);
    }

    public Response post(String path, Object body) {
        log.info("Sending POST request to {}", path);
        return request().body(body).when().post(path);
    }

    public Response put(String path, Object body) {
        log.info("Sending PUT request to {}", path);
        return request().body(body).when().put(path);
    }

    public Response patch(String path, Object body) {
        log.info("Sending PATCH request to {}", path);
        return request().body(body).when().patch(path);
    }

    public Response delete(String path) {
        log.info("Sending DELETE request to {}", path);
        return request().when().delete(path);
    }
}
