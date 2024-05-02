Feature: Ana sayfa servislerine istek atilir

  Scenario: Ana sayfa servislerine sirasiyla istek atilir
    Given Ana sayfa icin gerekli degiskenler aliniyor
    When Ana sayfa servislerine istek atiliyor
    Then Servis kontrolleri yapiliyor
