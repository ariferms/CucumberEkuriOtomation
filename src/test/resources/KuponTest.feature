Feature: Kupon kontrolleri

  Scenario Outline: <kuponType> kupon turunde kupon oynanilir
    Given <kuponType> bahis turu icin uygun <complete> turu ve atlar secilir
    When Kupon oynanir
    Then Oynanan kuponu kontrol edilir
    And Oynanan kupon iptal edilir
    And Silinen kupon kontrol edilir

    Examples:
      | kuponType | complete |
      | Ganyan    | false    |
      | Plase     | false    |