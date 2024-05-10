package com.ekuri.steps;

import com.ekuri.services.ParaYatirPageService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

public class ParaYatirPageSteps {
    ParaYatirPageService paraYatirPageService = new ParaYatirPageService();
    String token;
    Response depositInfo;

    @Given("Para Yatir sayfasi icin gerekli parametreler olusturulur ve menu acilir")
    public void clickMenu() {
        token = paraYatirPageService.token();
        System.out.println("Menü açılır...");
    }

    @When("Para Yatir butonuna tiklanir")
    public void requestParaYatirPage() {
        depositInfo = paraYatirPageService.depositInfo(token);
    }

    @Then("Para Yatir servisi kontrol edilir")
    public void paraYatirPageKontrol() {
        Assertions.assertEquals(depositInfo.jsonPath().getJsonObject("payload.accountName"), "T ŞANS OYUNLARI A.Ş.");
        Assertions.assertEquals(depositInfo.jsonPath().getJsonObject("payload.bankName"), "ZİRAAT BANKASI");
        Assertions.assertEquals(depositInfo.jsonPath().getJsonObject("payload.sortCode"), "2250");
        Assertions.assertEquals(depositInfo.jsonPath().getJsonObject("payload.accountNo"), "97788404-5001");
        Assertions.assertEquals(depositInfo.jsonPath().getJsonObject("payload.sortCode"), "2250");
        Assertions.assertEquals(depositInfo.jsonPath().getJsonObject("payload.iban"), "TR30 0001 0022 5097 7884 0450 01");
        Assertions.assertEquals(depositInfo.jsonPath().getJsonObject("processStatus"), "Success");
    }
}
