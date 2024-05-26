Feature: Kupon kaydetme kontrolleri

  Scenario Outline: <kuponType> kupon turunde kupon kaydedilir
    Given Kaydedilecek olan, <kuponType> bahis turu icin uygun atlar ve <complete> turu secilir
    When Kupon kaydedilir
    Then Kaydedilen kupon kontrol edilir
    And Kaydedilen kupon silinir
    And Silinen kayıtlı kupon kontrol edilir

    Examples:
      | kuponType | complete |
      | Ganyan    | false    |
      | Plase     | false    |