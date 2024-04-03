package com.grocery;

import com.grocery.utils.PropertiesInfo;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Cart {

    private Map<String, String> headers;
    private HashMap<String, String> pathParams;
    private String cartId;
    private ApiRequestHandler request;
    private String accessToken;
    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpecGet;
    private ResponseSpecification responseSpecPost;
    private String endPoint;
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

        responseSpecGet = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

        responseSpecPost = new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .build();

        endPoint = "/carts";
        request.setEndpoint(endPoint);
    }
    @Test
    public void createNewCart(){

        var response = RestAssured.given()
                .spec(requestSpec)
                .log().all().when()
                .headers(request.getHeaders())
                .post(request.getEndpoint())
                .then()
                .spec(responseSpecPost).extract().response();

        System.out.println(response.getBody().asPrettyString());

        //cartId = response.jsonPath().getString("cartId");
       // System.out.println("Cart Id: " + cartId);
    }
    @Test
    public void getCart(){

        pathParams = new HashMap<>();
        pathParams.put("cartId", "xpYvLipBjzdMImoHczgvw");

        request.setPathParams(pathParams);

        var response = RestAssured.given()
                .spec(requestSpec)
                .log().all().when()
                .headers(request.getHeaders())
                .pathParams(request.getPathParams())
                .get(request.getEndpoint())
                .then()
                .spec(responseSpecGet).extract().response();

        System.out.println(response.getBody().asPrettyString());
    }
    @Test
    public void addItemToCart(){

        Random random = new Random();
        int randomQuantity = random.nextInt(Products.currentStock - 1) + 1;

        pathParams = new HashMap<String, String>();
        pathParams.put("cartId", cartId);

        String bodyRequest = "{\"productId\": \"" + Products.productId + "\", " + "\"quantity\": \"" + randomQuantity + "\"}";

        var response = RestAssured.given()
                .spec(requestSpec)
                .log().all().when()
                .headers(request.getHeaders())
                .pathParams(pathParams)
                .body(bodyRequest)
                .post(request.getEndpoint());

        //Assert.assertEquals(response.statusCode(),201);

        System.out.println(response.getBody().asPrettyString());
    }
    @Test
    public void getCartItems(){

        pathParams = new HashMap<String, String>();
        pathParams.put("cartId", cartId);

        var response = RestAssured.given()
                .spec(requestSpec)
                .log().all().when()
                .headers(request.getHeaders())
                .pathParams(pathParams)
                .get(request.getEndpoint())
                .then()
                .spec(responseSpecGet).extract().response();

        System.out.println(response.getBody().asPrettyString());
    }
    @Test
    public void modifyItemCart(){

        pathParams = new HashMap<String, String>();
        pathParams.put("cartId", cartId);
        pathParams.put("itemId", String.valueOf(272889091));

        String bodyRequest = "{\"quantity\": 8}";

        var response = RestAssured.given()
                .spec(requestSpec)
                .log().all().when()
                .headers(request.getHeaders())
                .pathParams(pathParams)
                .body(bodyRequest)
                .patch(request.getEndpoint())
                .then()
                .spec(responseSpecGet).extract().response();

        //Assert.assertEquals(response.statusCode(),204);

        System.out.println(response.getBody().asPrettyString());
    }
    @Test
    public void replaceItemCart(){

        pathParams = new HashMap<String, String>();
        pathParams.put("cartId", cartId);
        pathParams.put("itemId", String.valueOf(272889091));

        String bodyRequest = "{\"productId\": 3674, " + "\"quantity\": 1}";

        var response = RestAssured.given()
                .spec(requestSpec)
                .log().all().when()
                .headers(request.getHeaders())
                .pathParams(pathParams)
                .body(bodyRequest)
                .put(request.getEndpoint())
                .then()
                .spec(responseSpecGet).extract().response();

        //Assert.assertEquals(response.statusCode(),204);

        System.out.println(response.getBody().asPrettyString());
    }
    @Test
    public void deleteItemCart(){

        pathParams = new HashMap<String, String>();
        pathParams.put("cartId", cartId);
        pathParams.put("itemId", String.valueOf(272889091));

        var response = RestAssured.given()
                .spec(requestSpec)
                .log().all().when()
                .headers(request.getHeaders())
                .pathParams(pathParams)
                .delete(request.getEndpoint())
                .then()
                .spec(responseSpecGet).extract().response();

        //Assert.assertEquals(response.statusCode(),204);

        System.out.println(response.getBody().asPrettyString());
    }
}
