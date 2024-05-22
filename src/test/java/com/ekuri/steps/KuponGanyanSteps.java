package com.ekuri.steps;

import com.ekuri.services.KuponGanyanService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KuponGanyanSteps {
    KuponGanyanService kuponGanyanService = new KuponGanyanService();
    List<String> availableHours = new ArrayList<>();
    String[] targetRaceInfo = new String[3];
    String token,
            couponBody,
            couponCodeBody,
            couponCode,
            raceDate,
            hippodrome,
            targetRaceTime,
            betType = "Ganyan & Plase";
    JsonNode couponNode,
            runnersNode,
            couponCodeNode;
    int poolUnit = 1,
            runnerSize,
            misli = 2,
            raceNo,
            cardId;
    Response couponOrder,
            cancelOrder;

    @Given("Ganyan bahis turu icin uygun atlar secilir")
    public void getParameters() throws IOException {
        token = kuponGanyanService.token();
        kuponGanyanService.getCorrectRaceTimes();
        couponNode = kuponGanyanService.readJsonToFile("src/test/java/com/ekuri/requestJson/ganyanRequest.json");
        raceDate = kuponGanyanService.currentDate();
        runnersNode = kuponGanyanService.readJsonToFile("src/test/java/com/ekuri/responseJson/runnersResponse.json");
        runnerSize = kuponGanyanService.runnerSize(runnersNode);
        availableHours = kuponGanyanService.avaiableLegList(runnerSize, runnersNode, misli, betType);

        // Oynanacak hipodrom, koşu ve saat bulunur
        targetRaceInfo = kuponGanyanService.getCorrectRaceTimes();
        hippodrome = targetRaceInfo[0];
        targetRaceTime = targetRaceInfo[1];
        raceNo = Integer.parseInt(targetRaceInfo[2]);
        cardId = Integer.parseInt(targetRaceInfo[3]);

        // Kupona maclar ve gerekli parametreler eklenir
        couponNode = kuponGanyanService.couponRequestUpdate(availableHours, betType, couponNode, misli, 0, raceDate, raceNo, hippodrome, cardId);
        couponBody = couponNode.toString();
        System.out.println("Yeni Kupon Request: " + couponNode);
    }

    @When("Kupon oynanir")
    public void requestGanyanCoupon() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        couponOrder = kuponGanyanService.couponOrder(token, couponBody);
        File couponCodeFile = new File("src/test/java/com/ekuri/requestJson/couponCodeBody.json");
        couponCodeNode = objectMapper.readTree(couponCodeFile);

        couponCode = couponOrder.jsonPath().getString("payload.couponCode");
        ((ObjectNode) couponCodeNode).put("couponCode", couponCode);
        couponCodeBody = couponCodeNode.toString();
    }

    @Then("Oynanan Ganyan kuponu kontrol edilir")
    public void couponKontrol() {
        Assertions.assertEquals(couponOrder.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(couponOrder.jsonPath().getString("payload.couponCode"), couponCode);
        Assertions.assertEquals(couponOrder.jsonPath().getBoolean("payload.isReservedCoupon"), false);
        Assertions.assertEquals(couponOrder.jsonPath().getInt("payload.approveCode"), 1);
    }

    @Then("Oynanan kupon iptal edilir")
    public void deleteCoupon() {
        cancelOrder = kuponGanyanService.cancelOrder(token, couponCodeBody);
    }

    @Then("Silinen kupon kontrol edilir")
    public void deleteCouponKotrol() {
        Assertions.assertEquals(cancelOrder.jsonPath().getString("payload.id"), couponOrder.jsonPath().getString("payload.id"));
        Assertions.assertEquals(cancelOrder.jsonPath().getInt("payload.cancellationCode"), 2);
    }

}
