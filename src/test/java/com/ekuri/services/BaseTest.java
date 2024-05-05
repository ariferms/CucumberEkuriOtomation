package com.ekuri.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public class BaseTest {
    RequestSpecification spec;
    public BaseTest() {
        spec = new RequestSpecBuilder()
                .setBaseUri("https://api.ekuri.com/api/v1/")
                .addFilters(Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter()))
                .build();
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

    public void writeJsonToFile(String json, String filename) throws IOException {
        // JSON verisini bir dosyaya yazma
        File file = new File(filename);
        FileWriter writer = new FileWriter(file);
        writer.write(json);
        writer.close();
        System.out.println("Response JSON yazıldı: " + filename);
    }

    public JsonNode readJsonToFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File(filePath));
        return rootNode;
    }
}
