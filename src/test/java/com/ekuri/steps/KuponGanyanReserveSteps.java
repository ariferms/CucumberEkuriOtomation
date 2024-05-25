package com.ekuri.steps;

import com.ekuri.services.KuponGanyanReserveService;
import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KuponGanyanReserveSteps {
    KuponGanyanReserveService kuponGanyanReserveService = new KuponGanyanReserveService();
    List<String> availableHours = new ArrayList<>();
    String[] targetRaceInfo = new String[3];
    String token,
            couponBody,
            raceDate,
            hippodrome,
            targetRaceTime,
            betType = "Ganyan";
    JsonNode couponNode,
            runnersNode,
            couponNodeJson;
    int runnerSize,
            misli = 2,
            raceNo,
            cardId,
            couponId;
    boolean complete = false;
    Response couponReserveOrder,
            cancelReserveOrder;


    @Given("Rezerve Ganyan bahis turu icin uygun atlar secilir")
    public void getParameters() throws IOException {
        token = kuponGanyanReserveService.token();
        targetRaceInfo = kuponGanyanReserveService.getCorrectReserveRaceTimes();
        couponNodeJson = kuponGanyanReserveService.readJsonToFile("src/test/java/com/ekuri/requestJson/ganyanRequest.json");
        raceDate = kuponGanyanReserveService.getRaceDateReserve();
        runnersNode = kuponGanyanReserveService.readJsonToFile("src/test/java/com/ekuri/responseJson/runnersResponse.json");
        runnerSize = kuponGanyanReserveService.runnerSize(runnersNode);
        availableHours = kuponGanyanReserveService.avaiableLegList(runnerSize, runnersNode, misli, betType);

        // Oynanacak hipodrom, koşu ve saat bulunur
        hippodrome = targetRaceInfo[0];
        targetRaceTime = targetRaceInfo[1];
        raceNo = Integer.parseInt(targetRaceInfo[2]);
        cardId = Integer.parseInt(targetRaceInfo[3]);

        // Kupona maclar ve gerekli parametreler eklenir
        couponNode = kuponGanyanReserveService.couponRequestUpdate(availableHours, betType, couponNodeJson, misli, 0, raceDate, raceNo, hippodrome, cardId, complete);
        couponBody = couponNode.toString();
        System.out.println("Yeni Kupon Request: " + couponNode);
    }

    @When("Rezerve kupon oynanir")
    public void requestGanyanCoupon() {
        couponReserveOrder = kuponGanyanReserveService.couponReserveOrder(token, couponBody);

        couponId = couponReserveOrder.jsonPath().getInt("payload.id");
    }

    @Then("Oynanan Rezerve Ganyan kuponu kontrol edilir")
    public void couponKontrol() {
        Assertions.assertEquals(couponReserveOrder.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(couponReserveOrder.jsonPath().getString("payload.couponCode"), "");
        Assertions.assertEquals(couponReserveOrder.jsonPath().getBoolean("payload.isReservedCoupon"), true);
        Assertions.assertEquals(couponReserveOrder.jsonPath().getInt("payload.approveCode"), 1);
        Assertions.assertEquals(couponReserveOrder.jsonPath().getInt("payload.id"), couponId);
    }

    @Then("Oynanan Rezerve kupon iptal edilir")
    public void deleteCoupon() {
        cancelReserveOrder = kuponGanyanReserveService.cancelReserveOrder(token, couponId);
    }

    @Then("Silinen Rezerve kupon kontrol edilir")
    public void deleteCouponKotrol() {
        Assertions.assertEquals(cancelReserveOrder.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(cancelReserveOrder.jsonPath().getBoolean("payload.isCancellationSuccess"), true);
    }
}
