package com.ekuri.services;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class KampanyalarPageService extends BaseTest{
    public Response getCampaigns(){
        Response response = given(spec)
                .when()
                .get("core/campaign/get-campaigns");
        response
                .then()
                .statusCode(200);
        return response;
    }
}
