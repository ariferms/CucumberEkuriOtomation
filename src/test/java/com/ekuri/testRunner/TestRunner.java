package com.ekuri.testRunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources", // Özellik dosyalarının yolu
        glue = "com.ekuri", // Adım tanımlamalarının paket yolu
        plugin = {"pretty", "html:target/cucumber-reports-ekuri.html"} // Raporlama formatı
)
public class TestRunner {
}
