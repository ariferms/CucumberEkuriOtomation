Feature: Hesap Hareketlerim servisine istek atilmasi

  Scenario: Hesap Hareketlerim servisine istek atilir
    Given Hesap Hareketlerim sayfasi icin gerekli parametreler olusturulur ve menu acilir
    When Hesap Hareketlerim butonuna tiklanir
    Then Hesap Hareketlerim servisi kontrol edilir