Feature: Rezerve Ganyan kupon kontrolleri

  Scenario: Ganyan kupon turunde rezerve kupon oynanilir
    Given Rezerve Ganyan bahis turu icin uygun atlar secilir
    When Rezerve kupon oynanir
    Then Oynanan Rezerve Ganyan kuponu kontrol edilir
    And Oynanan Rezerve kupon iptal edilir
    And Silinen Rezerve kupon kontrol edilir