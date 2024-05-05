package com.ekuri.services;

import io.restassured.response.Response;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class TjkTvService extends BaseTest{
    public Response tjkTv(){
        Response response = given(spec)
                .when()
                .get("core/home/tjk-tv");
        response
                .then()
                .statusCode(200);
        return response;
    }
    public void writeJsonToFile(String json, String filename) throws IOException {
        // JSON verisini bir dosyaya yazma
        File file = new File(filename);
        FileWriter writer = new FileWriter(file);
        writer.write(json);
        writer.close();
        System.out.println("Response JSON yazıldı: " + filename);
    }
}
