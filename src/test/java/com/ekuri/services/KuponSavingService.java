package com.ekuri.services;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class KuponSavingService extends BaseTest{
    public Response couponSavingOrder(String token, String couponBody) {
        Response response = given(spec)
                .when()
                .contentType(ContentType.JSON)
                .accept("application/json, text/plain, */*")
                .header("authorization", "Bearer " + token)
                .body(couponBody)
                .post("core/coupon/save");
        response
                .then()
                .statusCode(200);
        return response;
    }

    public Response cancelSavingOrder(String token, int couponId) {
        Response response = given(spec)
                .when()
                .accept("application/json, text/plain, */*")
                .header("authorization", "Bearer " + token)
                .delete("core/coupon/cancel/" + couponId);
        response
                .then()
                .statusCode(200);
        return response;
    }
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
