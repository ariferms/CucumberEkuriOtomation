Feature: Iletisim sayfasi servis kontrolleri

  Scenario: Iletisim sayfasi servis kontrolleri yapilir
    Given Iletisim servisi icin gerekli parameterler alinir
    When Iletisim servisine, uygun iletisim tercihleri ile istek atilir
    Then Iletisim tercihlerinin guncellendigi kontrol edilir