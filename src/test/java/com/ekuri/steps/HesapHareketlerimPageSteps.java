package com.ekuri.steps;

import com.ekuri.services.HesapHareketlerimPageService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

public class HesapHareketlerimPageSteps {
    HesapHareketlerimPageService hesapHareketlerimPageService = new HesapHareketlerimPageService();
    Response getTransactions,
            getWallet;
    String token;

    @Given("Hesap Hareketlerim sayfasi icin gerekli parametreler olusturulur ve menu acilir")
    public void clickMenu() {
        token = hesapHareketlerimPageService.token();
        System.out.println("Menü açılır...");
    }

    @When("Hesap Hareketlerim butonuna tiklanir")
    public void requestParaCekPage() {
        getTransactions = hesapHareketlerimPageService.getTransactions(token);
        getWallet = hesapHareketlerimPageService.getWallet(token);
    }

    @Then("Hesap Hareketlerim servisi kontrol edilir")
    public void paraCekPageKontrol() {
        Assertions.assertEquals(getTransactions.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(getWallet.jsonPath().getJsonObject("processStatus"), "Success");
    }
}
