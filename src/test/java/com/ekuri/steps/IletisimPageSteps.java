package com.ekuri.steps;

import com.ekuri.services.IletisimPageService;
import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class IletisimPageSteps {
    IletisimPageService iletisimPageService = new IletisimPageService();
    String permissionsBody,
            token;
    JsonNode permissionsNode;
    Response permissions;

    @Given("Iletisim servisi icin gerekli parameterler alinir")
    public void parameterInfo() throws IOException {
        token = iletisimPageService.token();
        permissionsNode = iletisimPageService.readJsonToFile("src/test/java/com/ekuri/requestJson/iletisimRequestBody.json");
        permissionsBody = permissionsNode.toString();
    }
    @When("Iletisim servisine, uygun iletisim tercihleri ile istek atilir")
    public void requestIletisim(){
        permissions = iletisimPageService.permissions(token, permissionsBody);
    }
    @Then("Iletisim tercihlerinin guncellendigi kontrol edilir")
    public void iletisimKontrol(){
        Assertions.assertEquals(permissions.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(permissions.jsonPath().getString("payload.smsPermission"), permissionsNode.get("smsPermission").asText());
        Assertions.assertEquals(permissions.jsonPath().getString("payload.emailPermission"), permissionsNode.get("emailPermission").asText());
        Assertions.assertEquals(permissions.jsonPath().getString("payload.callPermission"), permissionsNode.get("callPermission").asText());
    }
}
