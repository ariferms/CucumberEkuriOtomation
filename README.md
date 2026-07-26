<div align="center">
  <img src="ekuri.png" alt="Ekuri Logo" width="220"/>

  # CucumberEkuriOtomation

  **Ekuri.com API Test Otomasyon Projesi**

</div>

---

## 📋 Proje Hakkında

Bu proje, **[ekuri.com](https://ekuri.com)** platformunun API servislerini test etmek amacıyla geliştirilmiş bir **davranış odaklı test otomasyonu (BDD)** projesidir. Testler, iş gereksinimlerinin sade ve okunabilir bir dille (Gherkin) senaryolar halinde yazılmasına imkan tanıyan **Cucumber** framework'ü kullanılarak tasarlanmıştır.

Proje; API uç noktalarının doğruluğunu, yanıt kodlarını, veri bütünlüğünü ve servis davranışlarını otomatik olarak doğrulamayı hedefler. Böylece manuel test yükü azaltılır, regresyon testleri hızlı ve tekrarlanabilir hale gelir.

---

## 🛠️ Kullanılan Teknolojiler

| Katman | Teknoloji |
|---|---|
| **Programlama Dili** | Java |
| **Test Framework** | Cucumber (BDD / Gherkin) |
| **API Test Kütüphanesi** | RestAssured |
| **Test Runner** | Cucumber JVM (native runner) |
| **Bağımlılık Yönetimi / Build Aracı** | Maven (`pom.xml`) |
| **Raporlama & Görsel Bileşenler** | HTML, CSS (proje logosu ve stil dosyaları) |

**Dil dağılımı (repo genelinde):** Java, HTML (rapor/logo sayfaları) ve Gherkin (`.feature` senaryo dosyaları).

---

## 🗂️ Proje Haritası (Klasör Yapısı)

```
CucumberEkuriOtomation/
│
├── .idea/                 # IDE (IntelliJ) proje ayarları
│
├── src/test/              # Test kaynak kodları
│   ├── java/              # Step definitions, hooks, runner ve yardımcı sınıflar
│   └── resources/         # .feature dosyaları (Gherkin senaryoları)
│
├── target/                # Maven build çıktıları (derlenmiş sınıflar, raporlar)
│
├── pom.xml                # Maven proje tanımı ve bağımlılıklar (Cucumber, RestAssured vb.)
│
├── ekuri.png              # Proje logosu
├── ekuriLogo.html         # Logonun HTML üzerinden görüntülenmesi
├── ekuriStyle.css         # Logo/README için stil dosyası
│
└── README.md              # Proje dokümantasyonu (bu dosya)
```

---

## ⚙️ Kurulum ve Çalıştırma

### Gereksinimler
- Java JDK (8 veya üzeri)
- Maven
- İnternet bağlantısı (ekuri.com API'sine erişim için)

### Adımlar

```bash
# Depoyu klonlayın
git clone https://github.com/ariferms/CucumberEkuriOtomation.git

# Proje dizinine gidin
cd CucumberEkuriOtomation

# Bağımlılıkları indirin ve testleri çalıştırın
mvn clean test
```

Testler çalıştırıldıktan sonra sonuçlar `target/` klasörü altında (Cucumber/Surefire raporları) oluşur.

---

## 🧪 Test Yaklaşımı

- Senaryolar **Gherkin** sözdizimi ile (`Given / When / Then`) iş gereksinimlerini yansıtacak şekilde yazılır.
- API istekleri **RestAssured** ile gönderilir; durum kodu, response body, header ve iş kurallarına dair doğrulamalar (assertion) yapılır.
- Test çalıştırma, ayrı bir test runner kütüphanesine (JUnit/TestNG) ihtiyaç duymadan **Cucumber'ın kendi native runner yapısı** ile yönetilir.
- Yeni bir API senaryosu eklemek için: `.feature` dosyasına yeni senaryo yazılır → ilgili step definition Java sınıfına karşılık gelen adımlar implement edilir.

---

## 👤 Geliştirici

**Arif ERMİŞ** — [ariferms](https://github.com/ariferms)

---

<div align="center">
  <sub>Bu proje ekuri.com API servislerinin otomatik test edilmesi amacıyla geliştirilmiştir.</sub>
</div>
