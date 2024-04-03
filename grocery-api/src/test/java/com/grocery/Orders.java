package com.grocery;

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

public class Orders {

    private HashMap<String, String> pathParams;
    private HashMap<String, String> headers;
    private ApiRequestHandler request;
    private String accessToken;
    private RequestSpecification requestSpec;
    private String endPoint;
    private ResponseSpecification responseSpec;
    private String bodyRequest;

    @BeforeTest
    public void setUp() {

        request = new ApiRequestHandler();

        accessToken = PropertiesInfo.getInstance().getAccessToken();

        headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", accessToken);

        request.setBaseUrl(String.format("%s", PropertiesInfo.getInstance().getBaseApi()));
        request.setHeaders(headers);

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(request.getBaseUrl())
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

        bodyRequest = "{\"cartId\": \"JLRA7IytSzARy7dxuYQb_\", " + "\"customerName\": \"Jorge Perez\"}";

        endPoint = "/orders";
        request.setEndpoint(endPoint);
    }

    @Test
    public void getCart(){

        var response = RestAssured.given()
                .spec(requestSpec)
                .log().all().when()
                .headers(request.getHeaders())
                .get(request.getEndpoint())
                .then()
                .spec(responseSpec).extract().response();

        System.out.println(response.getBody().asPrettyString());
    }

    @Test
    public void newOrder(){

        var response = RestAssured.given()
                .spec(requestSpec)
                .log().all().when()
                .headers(request.getHeaders())
                .body(request.getBodyRequest())
                .post(request.getEndpoint());

        Assert.assertEquals(response.statusCode(),201);

        System.out.println(response.getBody().asPrettyString());
    }

    @Test
    public void getOrder(){

        pathParams = new HashMap<String, String>();
        pathParams.put("orderId", "");

        var response = RestAssured.given()
                .spec(requestSpec)
                .log().all().when()
                .headers(request.getHeaders())
                .pathParams(pathParams)
                .post(request.getEndpoint());

        System.out.println(response.getBody().asPrettyString());
    }

    @Test
    public void updateOrder(){

        pathParams = new HashMap<String, String>();
        pathParams.put("orderId", "");

        String bodyRequest = "{\"customerName\": \"Jorge Perez\", " + "\"comment\": \"This is an update\"}";

        var response = RestAssured.given()
                .spec(requestSpec)
                .log().all().when()
                .headers(request.getHeaders())
                .pathParams(pathParams)
                .body(bodyRequest)
                .patch(request.getEndpoint());

        Assert.assertEquals(response.statusCode(),204);

        System.out.println(response.getBody().asPrettyString());
    }

    @Test
    public void deleteOrder (){

        pathParams = new HashMap<String, String>();
        pathParams.put("orderId", "");

        var response = RestAssured.given()
                .spec(requestSpec)
                .log().all().when()
                .headers(request.getHeaders())
                .pathParams(pathParams)
                .delete(request.getEndpoint());

        Assert.assertEquals(response.statusCode(),204);

        System.out.println(response.getBody().asPrettyString());
    }
}
