Feature: Sifremi unuttum servis kontrolleri

  Scenario Outline: Sifremi unuttum e-posta ve cep numarasi ile yenileme islemleri yapilir - <passwordOptions>
    Given Sifremi unuttum sureci icin gerekli parametreler olusturulur
    When Sifremi unuttum <passwordOptions> servisine <type> istegi atilir
    Then <type> Type'i icin sifremi unuttum servisleri kotrol edilir

    Examples:
      | passwordOptions | type |
      | E-posta      | 1    |
      | Cep Numarasi | 2    |