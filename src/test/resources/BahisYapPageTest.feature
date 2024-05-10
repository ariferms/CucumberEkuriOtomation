Feature: Bahis yap sayfasindaki servislere istek atilmasi

  Scenario: Bahis yap sayfasi servis kontrolleri
    Given Gerekli parametreler olusturulur
    When Bahis yap sayfasındaki servislere istek atilir
    Then Bahis yap servisleri kontrol edilir