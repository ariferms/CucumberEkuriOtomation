Feature: Kuponlarim kontrol

  @Login
  Scenario Outline: Kullanici login servis kontrolleri - <kuponChanger>
    Given Kuponlarim sayfasi icin gerekli parametreler toplanir
    And Kullanici kuponlarim sayfasindan <stateNumber> numarali, <kuponChanger> kupon menusunu secer
    When <stateNumber> menusune istek atilir
    Then Kuponlarim servislerinin kupon turleri kontrol edilir - <stateNumber>
    Examples:
      | kuponChanger | stateNumber |
      | Devam Eden   | 5           |
      | Rezerve      | 7           |
      | Kayitli      | 4           |
      | Kazanan      | 1           |
      | Kaybeden     | 2           |