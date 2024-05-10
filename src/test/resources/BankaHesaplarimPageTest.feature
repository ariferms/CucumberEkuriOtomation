Feature: Banka Hesaplarim servisine istek atilmasi

  Scenario: Banka Hesaplarim servisine istek atilir
    Given Banka Hesaplarim sayfasi icin gerekli parametreler olusturulur ve menu acilir
    When Banka Hesaplarim butonuna tiklanir
    Then Banka Hesaplarim servisi kontrol edilir