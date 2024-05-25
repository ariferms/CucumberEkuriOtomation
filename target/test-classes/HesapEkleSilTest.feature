Feature: Hesap ekle, sil servis testleri

  Scenario: Hesap ekleme ve silme testleri yapilir
    Given Eklenecek hesap bilgileri girilir
    When Hesap ekleme islemi gerceklestirilir
    Then Eklenen hesap kontrol edilir
    And Hesap silinir
    And Silme isleminin gerceklestigi kontrol edilir
