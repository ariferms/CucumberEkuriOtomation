Feature: Kullanici login

  Scenario Outline: Kullanici login servis kontrolleri - <loginOptions>
    Given Kullanici <loginValue> ve <password> girisi yapar
    When Kullanici giris yap butonuna tiklar
    Then Kullanici girisi kotrol edilir
    Examples:
      | loginOptions | loginValue            | password  |
      | TCKN         | 14798329878           | asd387389 |
      | E-posta      | arif.ermis@loodos.com | asd387389 |
      | Uye No       | 6973748               | asd387389 |