Feature: AGF sayfasi servis kontrolleri

  Scenario: AGF sayfasindaki servisler kontrol edilir
    Given AGF servisleri icin gerekli parametreler toplanir
    When AGF sayfasina istek atilir
    Then AGF sayfasindaki servisler kontrol edilir