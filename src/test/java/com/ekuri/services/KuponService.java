package com.ekuri.services;


import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class KuponService extends BaseTest {

    public Response couponOrder(String token, String couponBody) {
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

    public Response cancelOrder(String token, String couponCodeBody) {
        Response response = given(spec)
                .when()
                .contentType(ContentType.JSON)
                .accept("application/json, text/plain, */*")
                .header("authorization", "Bearer " + token)
                .body(couponCodeBody)
                .post("core/coupon/cancel-order");
        response
                .then()
                .statusCode(200);
        return response;
    }

}