Feature: Ganyan kupon kontrolleri

  Scenario: Ganya kupon turunde kupon oynanilir
    Given Ganyan bahis turu icin uygun atlar secilir
    When Kupon oynanir
    Then Oynanan Ganyan kuponu kontrol edilir
    And Oynanan kupon iptal edilir
    And Silinen kupon kontrol edilir