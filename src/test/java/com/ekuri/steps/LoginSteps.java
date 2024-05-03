package com.ekuri.steps;

import com.ekuri.services.LoginService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

public class LoginSteps {
    LoginService loginService = new LoginService();
    String accessToken;
    boolean accessKontrol;
    Response token,
            userMe,
            getWallet;

    @Given("Kullanici TCKN ve sifre girisi yapar")
    public void customerInfo() {
        token = loginService.token();
        accessToken = token.jsonPath().getJsonObject("payload.accessToken").toString();
    }

    @When("Kullanici giris yap butonuna tiklar")
    public void callLoginRequest() {
        userMe = loginService.userMe(accessToken);
        getWallet = loginService.getWallet(accessToken);
    }

    @Then("Kullanici girisi kotrol edilir")
    public void loginResponseKontrol() {
        accessKontrol = false;
        if (token.jsonPath().getJsonObject("payload.accessToken") != null) {
            accessKontrol = true;
        }
        Assertions.assertTrue(accessKontrol);
        Assertions.assertEquals(token.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(userMe.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(getWallet.jsonPath().getJsonObject("processStatus"), "Success");
    }

}
