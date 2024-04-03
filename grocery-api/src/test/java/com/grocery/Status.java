package com.grocery;

import com.grocery.utils.JsonPath;
import com.grocery.utils.PropertiesInfo;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;

public class Status {
    //Arrange
    //Act
    //Assert
    private HashMap<String, String> headers;
    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;
    private String endPoint;
    private String accessToken;
    private ApiRequestHandler request;

    @BeforeTest
    public void setUp() {

        request = new ApiRequestHandler();

        accessToken = PropertiesInfo.getInstance().getAccessToken();

        headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        request.setBaseUrl(String.format("%s", PropertiesInfo.getInstance().getBaseApi()));
        request.setHeaders(headers);

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(request.getBaseUrl())
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

        endPoint = "/status";
        request.setEndpoint(endPoint);
    }

    @Test
    public void getStatus() {

        var response = RestAssured.given()
                .spec(requestSpec)
                .log().all().when()
                .headers(request.getHeaders())
                .get(request.getEndpoint())
                .then()
                .spec(responseSpec).extract().response();

        String status = JsonPath.getResult(response.getBody().asPrettyString(), "$.status");

        Assert.assertEquals(status, "UP");

        System.out.println(response.getBody().asPrettyString());
    }
}
