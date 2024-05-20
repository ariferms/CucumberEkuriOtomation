package com.ekuri.steps;

import com.ekuri.services.KuponGanyanService;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KuponGanyanSteps {
    KuponGanyanService kuponGanyanService = new KuponGanyanService();
    String token,
            couponBody,
            couponCodeBody,
            raceDate,
            correctTime,
            hippodrome,
            targetRaceTime;
    String[] targetRaceInfo = new String[3];
    JsonNode couponNode,
            runnersNode;
    List<Integer> availableHours = new ArrayList<>();
    int couponPrice = 0,
            poolUnit = 1,
            runnerSize,
            misli = 2,
            raceNo;
    Response couponOrder,
            cancelOrder;

    @Test
    public void getParameters() throws IOException {
        token = kuponGanyanService.token();
        couponNode = kuponGanyanService.readJsonToFile("src/test/java/com/ekuri/requestJson/ganyanRequest.json");
        runnersNode = kuponGanyanService.readJsonToFile("src/test/java/com/ekuri/responseJson/runnersResponse.json");
        runnerSize = kuponGanyanService.runnerSize(runnersNode);
        raceDate = kuponGanyanService.getRaceDate();

        availableHours = kuponGanyanService.avaiableLegList(runnerSize, runnersNode, misli, poolUnit);

        // Oynanacak hipodrom, koşu ve saat bulunur
        targetRaceInfo = kuponGanyanService.getCorrectRaceTimes();
        hippodrome = targetRaceInfo[0];
        targetRaceTime = targetRaceInfo[1];
        raceNo = Integer.parseInt(targetRaceInfo[2]);

        // Kupona maclar ve gerekli parametreler eklenir
        couponNode = kuponGanyanService.couponRequestUpdate(availableHours, couponNode, misli, 0, raceDate, raceNo, hippodrome);
        System.out.println("Yeni Kupon Request: " + couponNode);


    }

}
