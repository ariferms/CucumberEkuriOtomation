Feature: Para Yatir servisine istek atilmasi

  Scenario: Para Yatir servisine istek atilir
    Given Para Yatir sayfasi icin gerekli parametreler olusturulur ve menu acilir
    When Para Yatir butonuna tiklanir
    Then Para Yatir servisi kontrol edilir