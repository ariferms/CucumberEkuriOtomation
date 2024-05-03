Feature: Kullanici login

 Scenario: Kullanici login olmasi servis kontrolleri
    Given Kullanici TCKN ve sifre girisi yapar
    When Kullanici giris yap butonuna tiklar
    Then Kullanici girisi kotrol edilir
