package com.grocery;

import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

public class Authentication {

    private HashMap<String, String> headers;
    private String baseUrl = "https://simple-grocery-store-api.glitch.me";

    @Test
    public void authentication(){

        headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");

        String bodyRequest = "{\"clientName\": \"Jorge Perez\", " + "\"clientEmail\": \"test22test@test.com\"}";

        String endPoint = "/api-clients";
        String requestURI = baseUrl + endPoint;



        var response = RestAssured.given().log().all().when()
                .headers(headers)
                .body(bodyRequest)
                .post(requestURI);

        Assert.assertEquals(response.statusCode(),201);

        System.out.println(response.getBody().asPrettyString());
    }
}
