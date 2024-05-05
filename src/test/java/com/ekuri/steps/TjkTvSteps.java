package com.ekuri.steps;

import com.ekuri.services.TjkTvService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class TjkTvSteps {
    TjkTvService tjkTvService = new TjkTvService();
    Response tjkTv;
    String tjkTvJson;
    JsonNode rootNode;

    @Given("TJK TV'nin yer aldigi menu acilir")
    public void openMenu() {
        System.out.println("Menü açılır.");
    }

    @When("TJK TV butonuna tiklanir")
    public void callTjkTv() throws IOException {
        tjkTv = tjkTvService.tjkTv();
        tjkTvJson = tjkTv.getBody().asString();
        tjkTvService.writeJsonToFile(tjkTvJson, "src/test/java/com/ekuri/responseJson/tjkTvResponse.json");
    }

    @Then("TJK TV servisi kontrol edilir")
    public void tjkTvKontrol() throws IOException {
        rootNode = tjkTvService.readJsonToFile("src/test/java/com/ekuri/responseJson/tjkTvResponse.json");

        String tvTitle = rootNode.get("payload").get(0).get("title").asText();
        String YurtDisitvTitle = rootNode.get("payload").get(1).get("title").asText();
        String processStatus = rootNode.get("processStatus").asText();

        Assertions.assertEquals("TJK TV Canlı Yayın", tvTitle);
        Assertions.assertEquals("TJK TV Yurt Dışı Canlı Yayın", YurtDisitvTitle);
        Assertions.assertEquals("Success", processStatus);
    }
}
