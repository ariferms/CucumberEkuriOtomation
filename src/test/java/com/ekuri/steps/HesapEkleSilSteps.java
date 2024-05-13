package com.ekuri.steps;

import com.ekuri.services.BankaHesaplarimPageService;
import com.ekuri.services.HesapEkleSilService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HesapEkleSilSteps {
    HesapEkleSilService hesapEkleSilService = new HesapEkleSilService();
    BankaHesaplarimPageService bankaHesaplarimPageService = new BankaHesaplarimPageService();
    List<Integer> hesapList;
    String hesapEkleSilJson,
            bankAccountsJson,
            bankAccountPayload,
            token;
    JsonNode hesapEkleBody,
            hesapEkleResponse,
            bankAccountsResponse;
    int id,
            endAddBankAccount,
            payloadId;
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
        hesapEkleSilJson = createAccount.getBody().asString();
        hesapEkleSilService.writeJsonToFile(hesapEkleSilJson, "src/test/java/com/ekuri/responseJson/hesapEkleResponse.json");
        hesapEkleResponse = hesapEkleSilService.readJsonToFile("src/test/java/com/ekuri/responseJson/hesapEkleResponse.json");
        id = hesapEkleResponse.get("payload").get("id").asInt();

        // Banka hesaplari kontrol icin json olusturma
        bankAccount = bankaHesaplarimPageService.bankAccount(token);
        bankAccountsJson = bankAccount.getBody().asString();
        bankaHesaplarimPageService.writeJsonToFile(bankAccountsJson, "src/test/java/com/ekuri/responseJson/bankaHesaplariResponse.json");
        bankAccountsResponse = bankaHesaplarimPageService.readJsonToFile("src/test/java/com/ekuri/responseJson/bankaHesaplariResponse.json");
        bankAccountPayload = bankAccountsResponse.get("payload").toString();

        hesapList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode payloadNode = objectMapper.readTree(bankAccountPayload);
        for (JsonNode bankAccountId : payloadNode) {
            int bankId = bankAccountId.get("id").asInt();
            hesapList.add(bankId);
        }

        endAddBankAccount = Collections.max(hesapList);
        System.out.println("Hesaplar Listesi: " + hesapList);
        System.out.println("En Büyük Sayı: " + endAddBankAccount);
    }

    @Then("Eklenen hesap kontrol edilir")
    public void hesapEkleKontrol() {
        System.out.println("Kontrol edilir");
        int hesapListLength = hesapList.toArray().length;
        for (int i = 0; i < hesapListLength; i++) {
            int wantedId = Integer.parseInt(bankAccount.jsonPath().getString("payload[" + i + "].id"));
            if (wantedId == endAddBankAccount) {
                payloadId = i;
            }
        }
        // Banka hesaplarim servisinden kontroller yapilir
        Assertions.assertEquals(hesapEkleResponse.get("payload").get("id").asInt(), endAddBankAccount);
        Assertions.assertEquals(hesapEkleResponse.get("payload").get("iban").asText(), bankAccount.jsonPath().getString("payload[" + payloadId + "].iban"));
        Assertions.assertEquals(hesapEkleResponse.get("payload").get("name").asText(), bankAccount.jsonPath().getString("payload[" + payloadId + "].name"));
        Assertions.assertEquals(hesapEkleResponse.get("processStatus").asText(), "Success");
    }

    @Then("Hesap silinir")
    public void deleteAccount() {
        deleteToAccount = hesapEkleSilService.deleteToAccount(token, id);
    }

    @Then("Silme isleminin gerceklestigi kontrol edilir")
    public void hesapSilKontrol() {
        Assertions.assertEquals(deleteToAccount.jsonPath().getJsonObject("payload"), true);
    }
}
