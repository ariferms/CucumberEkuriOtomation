Feature: Muhtemeller sayfasi servisleri

  Scenario: Muhtemeller sayfasindaki servislere istek atilir
    Given Muhtemeller sayfasi icin gerekli parametreler alinir
    When Muhtemeller sayfasina gidilir
    Then Sayafada istek atilan servisler kontrol edilir