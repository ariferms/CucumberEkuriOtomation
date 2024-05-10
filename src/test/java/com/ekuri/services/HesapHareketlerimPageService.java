package com.ekuri.services;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class HesapHareketlerimPageService extends BaseTest{
    public Response getTransactions(String token){
        Response response = given(spec)
                .when()
                .header("authorization", "Bearer " + token)
                .get("balance/transaction/get-transactions");
        response
                .then()
                .statusCode(200);
        return response;
    }
    public Response getWallet(String token){
        Response response = given(spec)
                .when()
                .header("authorization", "Bearer " + token)
                .get("balance/wallet/get-wallet");
        response
                .then()
                .statusCode(200);
        return response;
    }
}
