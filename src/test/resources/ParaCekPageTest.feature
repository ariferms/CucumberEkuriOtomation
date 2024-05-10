Feature: Para Cek servisine istek atilmasi

  Scenario: Para Cek servisine istek atilir
    Given Para Cek sayfasi icin gerekli parametreler olusturulur ve menu acilir
    When Para Cek butonuna tiklanir
    Then Para Cek servisi kontrol edilir