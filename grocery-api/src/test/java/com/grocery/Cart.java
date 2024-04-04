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

public class Cart {

    private Map<String, String> headers;

    private HashMap<String, String> pathParams;

    private String cartId;

    private ApiRequestHandler request;

    //private String accessToken;

    private RequestSpecification requestSpec;

    private ResponseSpecification responseSpecGet;

    private ResponseSpecification responseSpecPost;

    private ResponseSpecification responseSpecPut;

    private String endPoint;

    private int itemId;
    @BeforeTest
    public void setUp() {

        request = new ApiRequestHandler();

        //accessToken = PropertiesInfo.getInstance().getAccessToken();

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

        responseSpecPut = new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .build();
    }
    @Test (priority = 1)
    public void createNewCart(){

        endPoint = "/carts";
        request.setEndpoint(endPoint);

        var response = RestAssured.given()
                .spec(requestSpec)
                .log().all().when()
                .headers(request.getHeaders())
                .post(request.getEndpoint())
                .then()
                .spec(responseSpecPost).extract().response();

        System.out.println(response.getBody().asPrettyString());

        cartId = response.jsonPath().getString("cartId");
        System.out.println("Cart Id: " + cartId);
    }
    @Test (priority = 2)
    public void getCart(){

        endPoint = "/carts/{cartId}";
        request.setEndpoint(endPoint);

        pathParams = new HashMap<>();
        pathParams.put("cartId", this.cartId);

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
    @Test (priority = 3)
    public void addItemToCart(){

        endPoint = "/carts/{cartId}/items";
        request.setEndpoint(endPoint);

        pathParams = new HashMap<>();
        pathParams.put("cartId", cartId);

        request.setPathParams(pathParams);

        String bodyRequest = "{\"productId\": 5477, \"quantity\": 1}";

        request.setBodyRequest(bodyRequest);

        var response = RestAssured.given()
                .spec(requestSpec)
                .log().all().when()
                .headers(request.getHeaders())
                .pathParams(pathParams)
                .body(bodyRequest)
                .post(request.getEndpoint())
                .then()
                .spec(responseSpecPost).extract().response();

        //Assert.assertEquals(response.statusCode(),201);

        System.out.println(response.getBody().asPrettyString());
    }
    @Test (priority = 4)
    public void getCartItems(){

        endPoint = "/carts/{cartId}/items";
        request.setEndpoint(endPoint);

        pathParams = new HashMap<String, String>();
        pathParams.put("cartId", this.cartId);

        request.setPathParams(pathParams);

        var response = RestAssured.given()
                .spec(requestSpec)
                .log().all().when()
                .headers(request.getHeaders())
                .pathParams(pathParams)
                .get(request.getEndpoint())
                .then()
                .spec(responseSpecGet).extract().response();

        System.out.println(response.getBody().asPrettyString());

        itemId = Integer.parseInt(response.jsonPath().getString("id"));
        System.out.println("Item Id: " + itemId);

    }
    @Test (priority = 5)
    public void modifyItemCart(){

        endPoint = "/carts/{cartId}/items/{itemId}";
        request.setEndpoint(endPoint);

        pathParams = new HashMap<>();
        pathParams.put("cartId", cartId);
        pathParams.put("itemId", String.valueOf(this.itemId));

        String bodyRequest = "{\"quantity\": 2}";
        request.setBodyRequest(bodyRequest);

        var response = RestAssured.given()
                .spec(requestSpec)
                .log().all().when()
                .headers(request.getHeaders())
                .pathParams(pathParams)
                .body(request.getBodyRequest())
                .patch(request.getEndpoint())
                .then()
                .spec(responseSpecPut).extract().response();

        //Assert.assertEquals(response.statusCode(),204);

        System.out.println(response.getBody().asPrettyString());
    }
    @Test (priority = 6)
    public void replaceItemCart(){

        endPoint = "/carts/{cartId}/items/{itemId}";
        request.setEndpoint(endPoint);

        pathParams = new HashMap<>();
        pathParams.put("cartId", this.cartId);
        pathParams.put("itemId", String.valueOf(this.itemId));
        request.setPathParams(pathParams);

        String bodyRequest = "{\"productId\": 3674, " + "\"quantity\": 1}";
        request.setBodyRequest(bodyRequest);

        var response = RestAssured.given()
                .spec(requestSpec)
                .log().all().when()
                .headers(request.getHeaders())
                .pathParams(pathParams)
                .body(request.getBodyRequest())
                .put(request.getEndpoint())
                .then()
                .spec(responseSpecPut).extract().response();

        //Assert.assertEquals(response.statusCode(),204);

        System.out.println(response.getBody().asPrettyString());
    }
    @Test (priority = 7)
    public void deleteItemCart(){

        endPoint = "/carts/{cartId}/items/{itemId}";
        request.setEndpoint(endPoint);

        pathParams = new HashMap<>();
        pathParams.put("cartId", this.cartId);
        pathParams.put("itemId", String.valueOf(this.itemId));
        request.setPathParams(pathParams);

        var response = RestAssured.given()
                .spec(requestSpec)
                .log().all().when()
                .headers(request.getHeaders())
                .pathParams(pathParams)
                .delete(request.getEndpoint())
                .then()
                .spec(responseSpecPut).extract().response();

        //Assert.assertEquals(response.statusCode(),204);

        System.out.println(response.getBody().asPrettyString());
    }
}
