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
import java.util.List;
import java.util.Random;

public class Products {

    private HashMap<String, String> headers;
    private List<Integer> ids;
    public static int productId;
    public static int currentStock;
    private ApiRequestHandler request;
    private String accessToken;
    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;
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

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

        endPoint = "/products";
        request.setEndpoint(endPoint);
    }

    @Test
    public void getAllProducts (){

        var response = RestAssured.given()
                .spec(requestSpec)
                .log().all().when()
                .headers(request.getHeaders())
                .get(request.getEndpoint())
                .then()
                .spec(responseSpec).extract().response();

        System.out.println(response.getBody().asPrettyString());

        ids = response.jsonPath().getList("id");
        Random rand = new Random();
        int randomIndex = rand.nextInt(ids.size());
        productId = ids.get(randomIndex);
        System.out.println("productId: " + productId);
    }

    @Test
    public void getProduct (){

        int productId = this.productId;

        request.setEndpoint(endPoint,productId);

        var response = RestAssured.given()
                .spec(requestSpec)
                .log().all().when()
                .headers(request.getHeaders())
                .get(request.getEndpoint())
                .then()
                .spec(responseSpec).extract().response();

        System.out.println(response.getBody().asPrettyString());

        currentStock = response.jsonPath().getInt("current-stock");
        System.out.println("Current Stock: " + currentStock);
    }
}
