Feature: Program sayfasindaki servis kontrolleri

  Scenario: Program sayfasinda servis kontrolleri yapilir
    Given Program sayfasi icin gerekli parametreler alinir
    When Program sayfasina istek atilir
    Then Program sayfasi servis kontrolleri yapilir