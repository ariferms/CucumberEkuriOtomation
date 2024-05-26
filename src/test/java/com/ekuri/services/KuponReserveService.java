package com.ekuri.services;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class KuponReserveService extends BaseTest{
    public Response couponReserveOrder(String token, String couponBody) {
        Response response = given(spec)
                .when()
                .contentType(ContentType.JSON)
                .accept("application/json, text/plain, */*")
                .header("authorization", "Bearer " + token)
                .body(couponBody)
                .post("core/coupon/order");
        response
                .then()
                .statusCode(200);
        return response;
    }

    public Response cancelReserveOrder(String token, int couponId) {
        Response response = given(spec)
                .when()
                .accept("application/json, text/plain, */*")
                .header("authorization", "Bearer " + token)
                .post("core/coupon/cancel-reserve-order/" + couponId);
        response
                .then()
                .statusCode(200);
        return response;
    }
}