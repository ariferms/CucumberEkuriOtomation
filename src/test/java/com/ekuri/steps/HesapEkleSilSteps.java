package com.ekuri.steps;

import com.ekuri.services.BankaHesaplarimPageService;
import com.ekuri.services.HesapEkleSilService;
import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class HesapEkleSilSteps {
    HesapEkleSilService hesapEkleSilService = new HesapEkleSilService();
    BankaHesaplarimPageService bankaHesaplarimPageService = new BankaHesaplarimPageService();
    String token;
    JsonNode hesapEkleBody;
    JsonNode hesapEkleResponse;
    int id;
    Response createAccount,
            deleteToAccount,
            bankAccount;
    @Given("Eklenecek hesap bilgileri girilir")
    public void getAccountInfo() throws IOException {
        token = hesapEkleSilService.token();
        hesapEkleBody = hesapEkleSilService.readJsonToFile("src/test/java/com/ekuri/requestJson/hesapEkleBody.json");
    }
    @When("Hesap ekleme islemi gerceklestirilir")
    public void requestHesapEkle() throws IOException {
        createAccount = hesapEkleSilService.createAccount(token, hesapEkleBody.toString());
        hesapEkleSilService.writeJsonToFile(createAccount.toString(), "src/test/java/com/ekuri/responseJson/hesapEkleResponse.json");
        hesapEkleResponse = hesapEkleSilService.readJsonToFile("src/test/java/com/ekuri/responseJson/hesapEkleResponse.json");
        id = hesapEkleResponse.get("id").asInt();
        bankAccount = bankaHesaplarimPageService.bankAccount(token);
    }
    @Then("Eklenen hesap kontrol edilir")
    public void hesapEkleKontrol(){
        Assertions.assertEquals(hesapEkleResponse.get("id").asInt(), bankAccount.jsonPath().getInt("payload[0].id"));
        Assertions.assertEquals(hesapEkleResponse.get("iban").asText(), bankAccount.jsonPath().getString("payload[0].iban"));
        Assertions.assertEquals(hesapEkleResponse.get("name").asText(), bankAccount.jsonPath().getString("payload[0].name"));
        Assertions.assertEquals(hesapEkleResponse.get("processStatus").asText(), "Success");
    }
    @Then("Hesap silinir")
    public void deleteAccount(){
        deleteToAccount = hesapEkleSilService.deleteToAccount(token, id);
    }
    @Then("Silme isleminin gerceklestigi kontrol edilir")
    public void hesapSilKontrol(){
        Assertions.assertTrue(deleteToAccount.jsonPath().getJsonObject("payload"));
    }
}
