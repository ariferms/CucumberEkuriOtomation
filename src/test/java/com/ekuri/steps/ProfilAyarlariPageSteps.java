package com.ekuri.steps;

import com.ekuri.services.ProfilAyarlariPageService;
import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class ProfilAyarlariPageSteps {
    public ProfilAyarlariPageSteps() {
        token = profilAyarlariPageService.token();
    }

    ProfilAyarlariPageService profilAyarlariPageService = new ProfilAyarlariPageService();
    String token,
            addressJson,
            addressResponse,
            countriesResponse,
            passwordJson;
    Response sendEmailVerifyLink,
            updatePassword,
            counties,
            address;
    JsonNode addressNode,
            passwordNode;

    // E-posta
    @Given("E-Posta dogrulama icin gerekli parametreler elde edilir")
    public void epostaParameterInfo() {
        System.out.println("E-posta için gerekli parametreler toplanır...");
    }

    @When("E-Posta servisine istek atilir")
    public void requestEposta() {
        sendEmailVerifyLink = profilAyarlariPageService.sendEmailVerifyLink(token);
    }

    @Then("E-Posta servisi kontrol edilir")
    public void epostaKontrol() {
        Assertions.assertEquals(sendEmailVerifyLink.jsonPath().getJsonObject("payload"), true);
        System.out.println("E-posta doğrulama yapılmıştır...");
    }

    // Address
    @Given("Adres dogrulama icin gerekli parametreler elde edilir")
    public void addressParameterInfo() throws IOException {
        System.out.println("Adres için gerekli parametreler toplanır...");
        addressNode = profilAyarlariPageService.readJsonToFile("src/test/java/com/ekuri/requestJson/addressBody.json");
        addressJson = addressNode.toString();
    }

    @When("Adres servisine istek atilir")
    public void requestAddress() throws IOException {
        address = profilAyarlariPageService.address(token, addressJson);
        addressResponse = address.getBody().asString();
        profilAyarlariPageService.writeJsonToFile(addressResponse, "src/test/java/com/ekuri/responseJson/addressResponse.json");
        counties = profilAyarlariPageService.counties(token);
        countriesResponse = counties.getBody().asString();
        profilAyarlariPageService.writeJsonToFile(countriesResponse, "src/test/java/com/ekuri/responseJson/countiesResponse.json");
    }

    @Then("Adres servisi kontrol edilir")
    public void addressKontrol() {
        Assertions.assertEquals(address.jsonPath().getString("payload.cityId"), addressNode.get("cityId").asText());
        Assertions.assertEquals(address.jsonPath().getString("payload.countyId"), addressNode.get("countyId").asText());
        Assertions.assertEquals(address.jsonPath().getString("payload.address"), addressNode.get("address").asText());
        Assertions.assertEquals(counties.jsonPath().getJsonObject("processStatus"), "Success");
        System.out.println("E-posta doğrulama yapılmıştır...");
    }

    // Password
    @Given("Sifre dogrulama icin gerekli parametreler elde edilir")
    public void passwordParameterInfo() throws IOException {
        System.out.println("Şifre güncelleme için gerekli parametreler toplanır...");
        passwordNode = profilAyarlariPageService.readJsonToFile("src/test/java/com/ekuri/requestJson/updatePasswordBody.json");
        passwordJson = passwordNode.toString();
    }

    @When("Sifre servisine istek atilir")
    public void requestPassword() throws IOException {
        updatePassword = profilAyarlariPageService.updatePassword(token, passwordJson);
    }

    @Then("Sifre servisi kontrol edilir")
    public void passwordKontrol() {
        Assertions.assertEquals(updatePassword.jsonPath().getJsonObject("payload"), true);
        System.out.println("Şifre güncelleme yapılmıştır...");
    }

}
