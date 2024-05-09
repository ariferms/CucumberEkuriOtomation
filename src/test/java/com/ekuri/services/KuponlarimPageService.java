package com.ekuri.services;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class KuponlarimPageService extends BaseTest{
    public Response getCoupons(String token, int state){
        Response response = given(spec)
                .when()
                .header("authorization", "Bearer " + token)
                .get("core/coupon/get-coupons?state=" + state);
        response
                .then()
                .statusCode(200);
        return response;
    }
}
