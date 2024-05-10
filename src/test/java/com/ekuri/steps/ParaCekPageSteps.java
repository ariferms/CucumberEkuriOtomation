package com.ekuri.steps;

import com.ekuri.services.ParaCekPageService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

public class ParaCekPageSteps {
    ParaCekPageService paraCekPageService = new ParaCekPageService();
    Response bankAccount,
            withdraw;
    String token;

    @Given("Para Cek sayfasi icin gerekli parametreler olusturulur ve menu acilir")
    public void clickMenu() {
        token = paraCekPageService.token();
        System.out.println("Menü açılır...");
    }
    @When("Para Cek butonuna tiklanir")
    public void requestParaCekPage(){
        bankAccount = paraCekPageService.bankAccount(token);
        withdraw = paraCekPageService.withdraw(token);
    }
    @Then("Para Cek servisi kontrol edilir")
    public void paraCekPageKontrol(){
        Assertions.assertEquals(bankAccount.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(withdraw.jsonPath().getJsonObject("processStatus"), "Success");
    }
}
