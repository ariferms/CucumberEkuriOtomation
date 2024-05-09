Feature: Yazar yorumlari sayfasi servis kontrolleri

  Scenario: Yazar yorumlari sayfasindaki servisler kontrol edilir
    Given Yazar yorumlari sayfasi menude secilir
    When Yazar yorumlari sayfasina istek atilir
    Then Yazar yorumlari sayfasindaki servisler kontrol edilir