package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createProduct_success_productAppearsInList(ChromeDriver driver) {
        // 1. Open Create Product page
        driver.get(baseUrl + "/product/create");

        // 2. Fill in product name
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.sendKeys("Sampo Selenium");

        // 3. Fill in product quantity
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.sendKeys("25");

        // 4. Submit form
        WebElement submitButton = driver.findElement(By.tagName("button"));
        submitButton.click();

        // 5. Verify redirected to product list
        String pageSource = driver.getPageSource();

        assertTrue(pageSource.contains("Sampo Selenium"));
        assertTrue(pageSource.contains("25"));
    }
}