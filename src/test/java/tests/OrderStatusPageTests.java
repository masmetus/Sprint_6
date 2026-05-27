package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.OrderStatusPage;
import pages.components.HeaderComponent;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderStatusPageTests {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        driver.get("https://qa-scooter.education-services.ru");
    }

    @Test
    public void checkStatusFromOrderPage(){
        HeaderComponent headerComponent = new HeaderComponent(driver);

        headerComponent.clickStatusButton();
        headerComponent.setEnterOrderNumber("123321123312");
        headerComponent.clickCheckOrderButton();

        OrderStatusPage orderStatusPage = new OrderStatusPage(driver);
        boolean isNotFoundOrder = orderStatusPage.isNotFoundImgPresent();

        assertTrue(isNotFoundOrder);
    }



    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
