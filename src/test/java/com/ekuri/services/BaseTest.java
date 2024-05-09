package com.ekuri.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static io.restassured.RestAssured.given;

public class BaseTest {
    RequestSpecification spec;
    public BaseTest() {
        spec = new RequestSpecBuilder()
                .setBaseUri("https://api.ekuri.com/api/v1/")
                .addFilters(Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter()))
                .build();
    }

    public Response token() {
        Response response = given(spec)
                .when()
                .formParam("Input", "14798329878")
                .formParam("Password", "asd387389")
                .formParam("client_id", "ekuri_web_client")
                .formParam("client_secret", "d3f44a6c-3da0-4822-bbc9-8cd41539e6b1")
                .formParam("grant_type", "multi_provider")
                .formParam("scope", "offline_access")
                .header("content-type", "application/x-www-form-urlencoded")
                .post("auth/connect/token");
        response
                .then()
                .statusCode(200);
        return response;
    }

    public String  currentDate(){
        LocalDate localDate = LocalDate.now();
        System.out.println("Tarih Formatı: " + localDate);

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime threeHoursAgo = localDateTime.minusHours(3);
        LocalDate justDate = threeHoursAgo.toLocalDate();
        String date = justDate.toString();
        return date;
    }

    public void writeJsonToFile(String json, String filePath) throws IOException {
        // JSON verisini bir dosyaya yazma
        File file = new File(filePath);
        FileWriter writer = new FileWriter(file);
        writer.write(json);
        writer.close();
        System.out.println("Response JSON yazıldı: " + filePath);
    }

    public JsonNode readJsonToFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File(filePath));
        return rootNode;
    }
}
