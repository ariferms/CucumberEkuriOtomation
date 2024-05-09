Feature: Kampanyalar sayfasi servis kontrolleri

  Scenario: AGF sayfasindaki servisler kontrol edilir
    Given Kampanyalar sayfasi menude secilir
    When Kampanyalar sayfasina istek atilir
    Then Kampanyalar sayfasindaki servisler kontrol edilir