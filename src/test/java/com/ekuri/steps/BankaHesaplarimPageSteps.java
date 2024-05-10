package com.ekuri.steps;

import com.ekuri.services.BankaHesaplarimPageService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

public class BankaHesaplarimPageSteps {
    BankaHesaplarimPageService bankaHesaplarimPageService = new BankaHesaplarimPageService();
    Response bankAccount;
    String token;
    @Given("Banka Hesaplarim sayfasi icin gerekli parametreler olusturulur ve menu acilir")
    public void clickMenu() {
        token = bankaHesaplarimPageService.token();
        System.out.println("Menü açılır...");
    }
    @When("Banka Hesaplarim butonuna tiklanir")
    public void requestParaCekPage(){
        bankAccount = bankaHesaplarimPageService.bankAccount(token);
    }
    @Then("Banka Hesaplarim servisi kontrol edilir")
    public void paraCekPageKontrol(){
        Assertions.assertEquals(bankAccount.jsonPath().getJsonObject("processStatus"), "Success");
    }
}
