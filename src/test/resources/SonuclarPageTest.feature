Feature: Sonuclar sayfasi servis kontrolleri

  Scenario: Sonuclar sayfasindaki servisler kontrol edilir
    Given Sonuclar servisleri icin gerekli parametreler toplanir
    When Sonuclar sayfasina istek atilir
    Then Sonuclar sayfasindaki servisler kontrol edilir