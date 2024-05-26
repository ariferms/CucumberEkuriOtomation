Feature: Rezerve kupon kontrolleri

  Scenario Outline: <kuponType> kupon turunde rezerve kupon oynanilir
    Given Rezerve <kuponType> bahis turu icin uygun atlar ve <complete> turu secilir
    When Rezerve kupon oynanir
    Then Oynanan Rezerve kupon kontrol edilir
    And Oynanan Rezerve kupon iptal edilir
    And Silinen Rezerve kupon kontrol edilir

    Examples:
      | kuponType | complete |
      | Ganyan    | false    |
      | Plase     | false    |