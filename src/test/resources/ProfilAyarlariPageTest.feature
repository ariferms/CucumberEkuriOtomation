Feature: Ayarlar sayfasi servis kontrolleri

  @e-posta_verify
  Scenario: E-Posta adresi guncelleme ve dogrulama
    Given E-Posta dogrulama icin gerekli parametreler elde edilir
    When E-Posta servisine istek atilir
    Then E-Posta servisi kontrol edilir

  @address_update
  Scenario: Adres guncelleme ve dogrulama
    Given Adres dogrulama icin gerekli parametreler elde edilir
    When Adres servisine istek atilir
    Then Adres servisi kontrol edilir

  @password_update
  Scenario: Sifre guncelleme ve dogrulama
    Given Sifre dogrulama icin gerekli parametreler elde edilir
    When Sifre servisine istek atilir
    Then Sifre servisi kontrol edilir